package com.exsio.clock.service.push;

import com.exsio.clock.model.PushMessage;

public interface MessageSerializer {

    String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";

    String serialize(PushMessage message);
}
