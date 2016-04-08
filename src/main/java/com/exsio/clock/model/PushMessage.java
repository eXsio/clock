package com.exsio.clock.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class PushMessage<T> implements Serializable {

    static final long serialVersionUID = 1L;

    private final T object;
    private final UUID id = UUID.randomUUID();
    private final Long timestamp = new Date().getTime();
    private transient Class objectSerializationView;

    public PushMessage(T object) {
        this.object = object;
    }

    public Class getObjectSerializationView() {
        return objectSerializationView;
    }

    public void setObjectSerializationView(Class objectSerializationView) {
        this.objectSerializationView = objectSerializationView;
    }

    public T getObject() {
        return object;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("object", object)
                .add("id", id)
                .add("timestamp", timestamp)
                .add("objectSerializationView", objectSerializationView)
                .toString();
    }
}
