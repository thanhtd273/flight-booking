package com.group1.flight.booking.core;

public class APIResponse {
    private Integer statusCode;
    private String statusMessage;
    private String description;
    private Object data;
    private Long took;

    public APIResponse(ErrorCode errorCode, String description, Long took, Object data) {
        this.statusCode = errorCode.getValue();
        this.statusMessage = errorCode.getMessage();
        this.description = description;
        this.data = data;
        this.took = took;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public String getDescription() {
        return this.description;
    }

    public Object getData() {
        return this.data;
    }

    public Long getTook() {
        return this.took;
    }

    public void setStatusCode(final Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusMessage(final String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    public void setTook(final Long took) {
        this.took = took;
    }

    public APIResponse(final Integer statusCode, final String statusMessage, final String description, final Object data, final Long took) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.description = description;
        this.data = data;
        this.took = took;
    }

    public APIResponse() {
    }
}
