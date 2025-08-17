package com.baio.money_minder.exceptions;

public class UniqueFieldViolationException extends RuntimeException {
    private final String fieldName;

    public UniqueFieldViolationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
