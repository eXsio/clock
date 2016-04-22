package com.exsio.clock.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;
import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class JsonpResult implements Serializable {

    private static final long serialVersionUID = -1774943436972485830L;

    public enum ResultStatus {
        SUCCESS;
    }

    private ResultStatus status;

    private JsonpResult(ResultStatus resultStatus) {
        this.status = resultStatus;

    }

    public static JsonpResult success() {
        return new JsonpResult(ResultStatus.SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonpResult)) return false;
        JsonpResult that = (JsonpResult) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return status.name();
    }
}
