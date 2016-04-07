'use strict';

(function ($) {

    var started = false;
    var defaultTransport = 'long-polling';

    window.app = {

        initManagement: function () {
            fillMinutes();
            fillSeconds();
            subscribe(defaultTransport);
            $('#set').on('click', function () {
                set();
            });
            $('#startstop').on('click', function () {
                startStop();
            });
            $('#reset').on('click', function () {
                reset();
            });
        },
        initClient: function () {
            subscribe(defaultTransport);
        }
    };

    var subscribe = function (transport) {

        var urlTransport = urlParam('transport');
        if (typeof urlTransport !== undefined && urlTransport != null) {
            transport = urlTransport;
        }
        service.subscribe(transport, "clock", function (payload) {

                var timeInfo = payload.object;
                $("#counter").html(timeInfo.time.replace(" ", "&nbsp;"));
                setStarted(timeInfo.clockStarted);
                if (timeInfo.alert) {
                    $("#counter").addClass("alert-counter");
                } else {
                    $("#counter").removeClass("alert-counter");
                }

            },
            function () {
                $(".no-connection").hide();
            },
            function () {
                $(".no-connection").show();
                setTimeout(function () {
                    subscribe('long-polling')
                }, 1000);
            });
    };

    var fillMinutes = function () {
        for (var i = 0; i < 100; i++) {
            $('#minutes').append($('<option>', {
                value: i,
                text: formatWithLeadingZero(i)
            }));
        }
    };

    var fillSeconds = function () {
        for (var i = 0; i < 60; i++) {
            $('#seconds').append($('<option>', {
                value: i,
                text: formatWithLeadingZero(i)
            }));
        }
    };

    var startStop = function () {
        if (started) {
            setStarted(false);
            $.post("/clock/api/stop");

        } else {
            setStarted(true);
            $.post("/clock/api/start");

        }
    };

    var setStarted = function (val) {
        started = val;
        if (started) {
            $("#startstop").html("Stop");
        } else {
            $("#startstop").html("Start");
        }
    };

    var reset = function () {
        $.post("/clock/api/reset");
    };


    var set = function () {
        $.post("/clock/api/set/{minutes}/{seconds}"
                .replace("{minutes}", $("#minutes").val())
                .replace("{seconds}", $("#seconds").val())
        );
    };

    var urlParam = function (name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (results == null) {
            return null;
        }
        else {
            return results[1] || 0;
        }
    };

    var formatWithLeadingZero = function (subject) {
        return subject * 1 >= 10 ? subject : "0" + subject;
    };

})($);