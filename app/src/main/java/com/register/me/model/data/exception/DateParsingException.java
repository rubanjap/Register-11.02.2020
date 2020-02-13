package com.register.me.model.data.exception;


public class DateParsingException extends RuntimeException {
    public DateParsingException(long dateValue, Exception e) {
        super(String.valueOf(dateValue), e);
    }
}
