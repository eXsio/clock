package com.exsio.clock.model;

import java.io.Serializable;

public class UpdateResult implements Serializable {

    public enum Status {
        OK,
        ERROR
    }

    private final Status status;

    private final String description;

    public UpdateResult(Status status) {
        this.status = status;
        this.description = null;
    }

    public UpdateResult(Status status, String description) {
        this.status = status;
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
