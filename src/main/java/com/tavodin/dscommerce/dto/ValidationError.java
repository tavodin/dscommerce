package com.tavodin.dscommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String errors, String path) {
        super(timestamp, status, errors, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String fieldMessage) {
        errors.removeIf(x -> x.getField().equals(fieldName));
        errors.add(new FieldMessage(fieldName, fieldMessage));
    }
}
