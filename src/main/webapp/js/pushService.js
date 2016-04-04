"use strict";

var service = {
    atmospheres: {}
};

var MESSAGE_DELIMITER = "||";


service.subscribe = function (channel, onMessageCallback, onOpenCallback, onErrorCallbsck) {

    var request = {
        url: '/api/push/subscribe/{channel}.push'.replace('{channel}', channel),
        transport: 'websocket',
        fallbackTransport: 'pong-polling',
        onOpen: function (resp) {
            console.log('Atmosphere connected to channel "' + channel + '" using "' + resp.transport + '"');
            onOpenCallback();
        },
        onClose: function (resp) {
            console.log('Atmosphere disconnected from channel "' + channel + '" using "' + resp.transport + '"');
            onErrorCallbsck();
        },
        onMessage: function (resp) {
            if (typeof onMessageCallback === 'function') {
                var messages = resp.responseBody.split(MESSAGE_DELIMITER);
                if (typeof messages === 'object') {
                    var payloadArray = toPayloadArray(messages);
                    for (var key in payloadArray) {
                        onMessageCallback(payloadArray[key], resp);
                    }
                } else {
                    console.error("Received malformed Atmosphere response, expected object, got " + typeof messages);
                }
            }
        },
        onError: function (resp) {
            console.error('Atmosphere connectivity error detected on channel "' + channel + '"');
            onErrorCallbsck();
            console.log(resp);
        }
    };

    var toPayloadArray = function (messages) {
        var payloadObj = {};
        for (var i = 0; i < messages.length - 1; i++) {
            var item = JSON.parse(messages[i].trim());
            payloadObj[item.id] = item;
        }
        var payloadArray = [];
        for(var key in payloadObj) {
            payloadArray.push(payloadObj[key]);
        }

        payloadArray.sort(function (a, b) {
            return a.timestamp - b.timestamp;
        });
        return payloadArray;
    }

    service.atmospheres[channel] = atmosphere.subscribe(request);
};

service.unsubscribe = function(channel, callback) {
    if(typeof service.atmospheres[channel].close !=='undefined') {
        service.atmospheres[channel].close();
        delete service.atmospheres[channel];
        callback();
    } else {
        console.info("can't unsubscribe from channel "+channel)
    }
}

