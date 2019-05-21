package edu.saddleback.microservices.order.util;

public class ErrorResponse extends Exception {
    private int status;
    private String error;
    private String code;

    public ErrorResponse(int status, String error, String code) {
        super(error);
        this.status = status;
        this.error = error;
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getCode() {
        return code;
    }
}

