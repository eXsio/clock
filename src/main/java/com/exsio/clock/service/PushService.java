package com.exsio.clock.service;

import com.exsio.clock.model.PushMessage;

public interface PushService {

    void push(String channel, PushMessage payload);
}
