package com.exsio.clock.model;

import java.io.Serializable;

public class JsonpResult implements Serializable {

    private static final long serialVersionUID = -1774943436972485830L;

    public enum ResultStatus {
        SUCCESS;
    }

    private ResultStatus status;

    private JsonpResult(ResultStatus resultStatus) {
        this.status = resultStatus;

    }

    public ResultStatus getStatus() {
        return status;
    }

    public static JsonpResult success() {
        return new JsonpResult(ResultStatus.SUCCESS);
    }
}
