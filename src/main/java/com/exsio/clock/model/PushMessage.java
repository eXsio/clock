package com.exsio.clock.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by a043601 on 10/19/2015.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class PushMessage<T> implements Serializable {

    private static final long serialVersionUID = -3148136320675233632L;

    public static final String DEFAULT_TYPE = "DEFAULT";

    private String type = DEFAULT_TYPE;
    private final T object;
    private final UUID id = UUID.randomUUID();
    private final Long timestamp = new Date().getTime();
    private transient Class objectSerializationView;

    public PushMessage(T object) {
        this.object = object;
    }

    public PushMessage(String type, T object) {
        this.type = type;
        this.object = object;
    }

    public Class getObjectSerializationView() {
        return objectSerializationView;
    }

    public void setObjectSerializationView(Class objectSerializationView) {
        this.objectSerializationView = objectSerializationView;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("object", object)
                .add("type", type)
                .add("id", id)
                .add("timestamp", timestamp)
                .add("objectSerializationView", objectSerializationView)
                .toString();
    }
}
