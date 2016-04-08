package com.exsio.clock.service.push;

import com.exsio.clock.model.PushMessage;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class PushServiceImpl implements PushService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PushServiceImpl.class);

    private final static String MESSAGE_DELIMITER = "||";

    private final BroadcasterFactory broadcasterFactory;

    private final MessageSerializer serializer;

    @Autowired
    public PushServiceImpl(BroadcasterFactory broadcasterFactory, MessageSerializer serializer) {
        this.broadcasterFactory = broadcasterFactory;
        this.serializer = serializer;
    }

    @Override
    public synchronized void push(String channel, PushMessage message) {
        Broadcaster broadcaster = broadcasterFactory.lookup(channel, true);
        doPush(channel, message, broadcaster);
    }

    private void doPush(String channel, PushMessage message, Broadcaster broadcaster) {
        String payload = getPayload(message);
        LOGGER.debug("Pushing data to the subscriber '{}' on channel '{}': {}", broadcaster.getID(), channel, payload);
        broadcaster.broadcast(payload);
    }

    private String getPayload(PushMessage message) {
        return serializer.serialize(message) + getMessageDelimiter();
    }

    private String getMessageDelimiter() {
        return MESSAGE_DELIMITER;
    }
}
