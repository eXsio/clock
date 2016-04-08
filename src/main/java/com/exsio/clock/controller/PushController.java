package com.exsio.clock.controller;

import com.exsio.clock.exception.PushServiceRuntimeException;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.BroadcasterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/push")
public class PushController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PushController.class);

    private final static long SUSPEND_TIME = -1L;

    private final static long CONNECTION_RETRY_SLEEP_TIME = 500L;

    private final BroadcasterFactory broadcasterFactory;

    @Value("${push.connection.max.retries:3}")
    private Long maxRetries;

    @Autowired
    public PushController(BroadcasterFactory broadcasterFactory) {
        this.broadcasterFactory = broadcasterFactory;
    }

    @RequestMapping(value = "/subscribe/{channel}.push", method = RequestMethod.GET)
    @ResponseBody
    public void subscribe(AtmosphereResource atmosphereResource, @PathVariable String channel) {
        suspendResource(atmosphereResource);
        registerResourceWithBroadcaster(atmosphereResource, channel);
    }

    private void registerResourceWithBroadcaster(AtmosphereResource atmosphereResource, @PathVariable String channel) {
        registerResourceForChannelAndScope(atmosphereResource, channel, 0L);

    }

    private void registerResourceForChannelAndScope(AtmosphereResource atmosphereResource, @PathVariable String channel, Long retries) {
        if (retries < maxRetries) {
            try {
                LOGGER.debug("Registering new subscriber for channel \"{}\"", channel);
                broadcasterFactory.lookup(channel, true).addAtmosphereResource(atmosphereResource);
            } catch (RuntimeException ex) {
                LOGGER.warn("Couldn't register new subscriber on channel \"{}\", reason: {}: {}. Retrying ({}/{})",
                        channel, ex.getClass().getName(), ex.getMessage(), retries + 1, maxRetries);
                LOGGER.trace("{}", ex);
                sleep();
                registerResourceForChannelAndScope(atmosphereResource, channel, retries + 1);
            }
        } else {
            throw new PushServiceRuntimeException(String.format("Couldn't register new subscriber on channel \"%s\". " +
                    "Push notifications may not be available to all clients.", channel));
        }
    }

    private void sleep() {
        try {
            Thread.sleep(CONNECTION_RETRY_SLEEP_TIME);
        } catch (InterruptedException inte) {
            LOGGER.error("{}", inte);
        }
    }

    private void suspendResource(AtmosphereResource atmosphereResource) {
        atmosphereResource.resumeOnBroadcast(isLongPolling(atmosphereResource)).suspend(SUSPEND_TIME);
    }

    private boolean isLongPolling(AtmosphereResource m) {
        return AtmosphereResource.TRANSPORT.LONG_POLLING.equals(m.transport());
    }

    void setMaxRetries(Long maxRetries) {
        this.maxRetries = maxRetries;
    }
}
