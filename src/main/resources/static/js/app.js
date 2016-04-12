'use strict';

(function ($) {

    var started = false;
    var defaultTransport = 'long-polling';
    var lastMessageTimestamp = null;

    window.app = {

        initManagement: function () {
            fillMinutes();
            fillSeconds();
            this.initClient();
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
            setInitialState();
            initOversight();
        }
    };

    var subscribe = function (transport) {

        var urlTransport = urlParam('transport');
        if (typeof urlTransport !== undefined && urlTransport != null) {
            transport = urlTransport;
        }
        service.subscribe(transport, "clock", function (payload) {
                updateState(payload.object);
                lastMessageTimestamp = getCurrentTimestamp();
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

    var setInitialState = function() {
        $.when($.ajax("/clock/api/state")).then(function(data) {
            updateState(data);
        });
    };

    var initOversight = function() {
        var urlOversight = urlParam('oversight');
        if(typeof urlOversight !== 'undefined' && urlOversight== 'false' ) {
            console.log("oversight disabled");
        } else {
            setInterval(function() {performOversight(); }, 1500);
        }
    }

    var performOversight = function() {

        var now = getCurrentTimestamp();
        var lastMessageAndNowDiff = now - lastMessageTimestamp;
        if(
            (started && (lastMessageTimestamp == null || lastMessageAndNowDiff >=3 )) ||
            (!started && lastMessageTimestamp != null && lastMessageAndNowDiff >=60)
        ) {
            console.log("oversight alert because of started is "+started+" and last message received "+lastMessageAndNowDiff+" seconds ago");
            tryToRefresh();
        }
    }

    var tryToRefresh = function() {
        $.when($.ajax({url: "/clock/api/state",timeout:1000, async: false})).then(function(data, textStatus, jqXHR) {
            console.log("trying to refresh the application page")
            if(textStatus != "timeout" && jqXHR.status * 1 == 200) {
                window.location.reload();
            } else {
                console.log("can't refresh because of an unexpected error");
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            }
        }, function(eventData) {
            console.log("can't refresh because of an unexpected error");
            console.log(eventData);
        });
    }

    var getCurrentTimestamp = function() {
        return Math.floor(Date.now() / 1000);
    }

    var updateState = function(timeInfo) {
        $("#counter").html(timeInfo.time);
        $("#boundary").html(timeInfo.boundary);
        setStarted(timeInfo.clockStarted);
        if (timeInfo.alert) {
            $("#counter").addClass("alert-counter");
        } else {
            $("#counter").removeClass("alert-counter");
        }
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