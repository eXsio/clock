'use strict';

(function ($) {

    var started = false;
    var defaultTransport = 'long-polling';
    var lastMessageTimestamp = null;
    var translations = null;

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
            translate();
        }
    };

    var subscribe = function (transport) {

        var urlTransport = urlParam('transport');
        if (typeof urlTransport !== undefined && urlTransport != null) {
            transport = urlTransport;
        }
        service.subscribe(getUrl('/clock/api/push/subscribe/{channel}.push'.replace('{channel}', "clock")), transport, "clock", function (payload) {
                lastMessageTimestamp = getCurrentTimestamp();
                updateState(payload.object);
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

    var setInitialState = function () {
        $.when($.ajax({url: getUrl("/clock/api/state"), jsonp: "callback", dataType: "jsonp"})).then(function (data) {
            updateState(data);
        });
    };

    var initOversight = function () {
        var urlOversight = urlParam('oversight');
        if (typeof urlOversight !== 'undefined' && urlOversight == 'false') {
            console.log("oversight disabled");
        } else {
            setInterval(function () {
                performOversight();
            }, 1500);
        }
    };

    var performOversight = function () {

        var now = getCurrentTimestamp();
        var lastMessageAndNowDiff = now - lastMessageTimestamp;
        if (started && lastMessageTimestamp != null && lastMessageAndNowDiff >= 3) {
            console.log("oversight alert because of started is " + started + " and last message received " + lastMessageAndNowDiff + " seconds ago");
            tryToRefresh();
        }
    };

    var tryToRefresh = function () {
        $.when($.ajax({
            url: getUrl("/clock/api/state"),
            timeout: 1000,
            async: false,
            jsonp: "callback",
            dataType: "jsonp"
        })).then(function (data, textStatus, jqXHR) {
            console.log("trying to refresh the application page")
            if (textStatus != "timeout" && jqXHR.status * 1 == 200) {
                window.location.reload();
            } else {
                console.log("can't refresh because of an unexpected error");
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            }
        }, function (eventData) {
            console.log("can't refresh because of an unexpected error");
            console.log(eventData);
        });
    };

    var getCurrentTimestamp = function () {
        return Math.floor(Date.now() / 1000);
    };

    var updateState = function (timeInfo) {

        $("#counter").html(timeInfo.time);
        $("#boundary").html(timeInfo.boundary);
        adjustFontSizes(timeInfo);
        setStarted(timeInfo.clockStarted);
        setAlertClasses(timeInfo);
    };

    var adjustFontSizes = function (timeInfo) {
        if (window.innerHeight > window.innerWidth) {
            if (timeInfo.time.length == 6) {
                $("#counter").css('font-size', '24vw');
            } else {
                $("#counter").css('font-size', '27vw');
            }
            if (timeInfo.boundary.length == 6) {
                $("#boundary-desc").css('font-size', '3vw');
                $("#boundary").css('font-size', '5vw');
            } else {
                $("#boundary-desc").css('font-size', '4vw');
                $("#boundary").css('font-size', '6vw');
            }
        } else {
            if (timeInfo.time.length == 6) {
                $("#counter").css('font-size', '30vw');
            } else {
                $("#counter").css('font-size', '37vw');
            }
            if (timeInfo.boundary.length == 6) {
                $("#boundary-desc").css('font-size', '7vw');
                $("#boundary").css('font-size', '9vw');
            } else {
                $("#boundary-desc").css('font-size', '8vw');
                $("#boundary").css('font-size', '10vw');
            }
        }
    };

    var setAlertClasses = function (timeInfo) {
        if (timeInfo.alert) {
            $("#counter").addClass("alert-counter");
            if ($("#boundary-wrapper").hasClass("alert-counter")) {
                $("#boundary-wrapper").removeClass("alert-counter");
            } else {
                $("#boundary-wrapper").addClass("alert-counter");
            }
        } else {
            $("#counter").removeClass("alert-counter");
            $("#boundary-wrapper").removeClass("alert-counter");
        }
    };

    var fillMinutes = function () {
        for (var i = 0; i < 1000; i++) {
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
            $.ajax({url: getUrl("/clock/api/stop"), jsonp: "callback", dataType: "jsonp"});

        } else {
            setStarted(true);
            $.ajax({url: getUrl("/clock/api/start"), jsonp: "callback", dataType: "jsonp"});

        }
    };

    var setStarted = function (val) {
        started = val;
        if (started) {
            $("#startstop").html(translations != null ? translations["controls.form.stop"] : 'controls.form.stop');
        } else {
            lastMessageTimestamp = null;
            $("#boundary-wrapper").removeClass("alert-counter");
            $("#startstop").html(translations != null ? translations["controls.form.start"] : 'controls.form.start');
        }
    };

    var reset = function () {
        $.ajax({url: getUrl("/clock/api/reset"), jsonp: "callback", dataType: "jsonp"});
    };


    var set = function () {
        $.ajax({
            url: getUrl("/clock/api/set/{minutes}/{seconds}")
                .replace("{minutes}", $("#minutes").val())
                .replace("{seconds}", $("#seconds").val())
            , jsonp: "callback", dataType: "jsonp"
        });
    };

    var translate = function () {
        var language = getLanguage();
        var userLanguage = urlParam("lang");
        if (typeof userLanguage !== 'undefined' && userLanguage != null) {
            language = userLanguage;
        }
        $.when($.ajax({
            url: getUrl("/clock//i18n/{lang}".replace("{lang}", language)),
            jsonp: "callback",
            dataType: "jsonp"
        })).then(function (data) {
            console.info("loaded translations for language: " + language);
            translations = data;
            $(".translatable").each(function (subject) {
                $(this).html(data[$(this).html()]);
            });
        });

    }

    var getLanguage = function () {
        var language = window.navigator.userLanguage || window.navigator.language;
        return language.split("-")[0];
    }

    var getUrl = function (path) {
        var server = urlParam('server');
        if (typeof server !== 'undefined' && server != null) {
            return 'http://' + server + path;
        } else {
            return path;
        }
    }

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