package com.pawelbugiel.wastenofood.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public record ApiError(
        int httpStatusCode,
        String httpErrorType,
        String exceptionMessage,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        Map<String, String> validationErrors
) {
}
