'use strict';

(function ($) {

    var interval = null;
    var lastSetting = "&nbsp;00:00";
    var alert = false;

    window.app = {

        initManagement: function () {
            fillMinutes();
            fillSeconds();
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
            subscribe();
        }
    };

    var subscribe = function () {
        var transport = 'websocket';
        var urlTransport = urlParam('transport');
        if (typeof urlTransport !== undefined && urlTransport != null) {
            transport = urlTransport;
        }
        service.subscribe(transport, "clock", function (payload) {
                console.info(payload);
                if (typeof payload.type !== "undefined" && payload.type == "CLOCK") {
                    var object = payload.object;
                    $("#counter").html(object.clock);
                    if (object.alert) {
                        $("#counter").addClass("alert-counter");
                    } else {
                        $("#counter").removeClass("alert-counter");
                    }
                }
            },
            function () {
                $(".no-connection").hide();
            },
            function () {
                $(".no-connection").show();
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
        if (interval == null) {
            interval = setInterval(function () {
                clockStep();
            }, 1000);
            $("#startstop").text("Stop");
        } else {
            clearInterval(interval);
            $("#startstop").text("Start");
            interval = null;
        }
    };

    var clockStep = function () {
        var setting = $("#counter").html().split(":");
        var newSetting = setting;
        var minutes = setting[0].trim().replace("-", "").replace("&nbsp;", "") * 1;
        var seconds = setting[1] * 1;
        if (alert || (minutes == 0 && seconds == 0)) {
            setAlert(true);
            newSetting = forwardClock(minutes, seconds);
        } else {
            newSetting = rewindClock(minutes, seconds);
        }
        $("#counter").html(newSetting);
        updateClients(newSetting);
    };

    var updateClients = function (setting) {

        $.ajax({
            type: "POST",
            url: "/clock/update",
            data: JSON.stringify({
                clock: setting,
                alert: alert
            }),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        });
    };

    var forwardClock = function (minutes, seconds) {
        seconds++;
        if (seconds >= 60) {
            minutes++;
            seconds = 0;
        }
        return timeToString(minutes, seconds);
    };

    var rewindClock = function (minutes, seconds) {
        seconds--;
        if (seconds < 0) {
            minutes--;
            seconds = 59;
        }
        return timeToString(minutes, seconds);
    };

    var timeToString = function (minutes, seconds) {
        var prefix = alert ? "-" : "&nbsp;";
        return prefix + formatWithLeadingZero(minutes) + ":" + formatWithLeadingZero(seconds);
    };

    var formatWithLeadingZero = function (subject) {
        return subject * 1 >= 10 ? subject : "0" + subject;
    };

    var reset = function () {
        setAlert(false);
        $("#counter").html(lastSetting);
        updateClients(lastSetting);
    };

    var setAlert = function (isAlert) {
        alert = isAlert;
        if (isAlert) {
            $("#counter").addClass("alert-counter");
        } else {
            $("#counter").removeClass("alert-counter");
        }
    };

    var set = function () {
        setAlert(false);
        var minutes = $("#minutes").val();
        var seconds = $("#seconds").val();
        if (isNumber(minutes) && isNumber(seconds)) {
            var setting = timeToString(minutes, seconds);
            $("#counter").html(setting);
            lastSetting = setting;
            updateClients(setting)
        } else {
            window.alert("wpisz poprawne wartości!");
        }
    };

    var isNumber = function (n) {
        return /^-?[\d.]+(?:e-?\d+)?$/.test(n);
    };

    var urlParam = function (name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (results == null) {
            return null;
        }
        else {
            return results[1] || 0;
        }
    }
})($);