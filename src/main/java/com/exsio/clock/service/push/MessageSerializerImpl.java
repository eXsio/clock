package com.exsio.clock.service.push;

import com.exsio.clock.exception.PushServiceRuntimeException;
import com.exsio.clock.model.PushMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
class MessageSerializerImpl implements MessageSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        MAPPER.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
    }

    @Override
    public String serialize(PushMessage message) {
        try {
            return message.getObjectSerializationView() != null ?
                    MAPPER.writerWithView(message.getObjectSerializationView()).writeValueAsString(message) : MAPPER.writeValueAsString(message);
        } catch (Exception e) {
            throw new PushServiceRuntimeException("Couldn't serialize payload to JSON", e);
        }
    }
}
