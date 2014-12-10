package org.demis.familh.web.controller.exception;

public class RangeException extends Exception {

    private String reason;

    public RangeException(String reason) {
        this.reason = reason;
    }


    public String getReason() {
        return reason;
    }


}
