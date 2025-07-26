package com.pawelbugiel.wastenofood.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationExceptions(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String message = "Validation failed. Check input fields for details.";

        return new ApiError(status.value(), status.getReasonPhrase(), message, errors);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleProductNotFound(ProductNotFoundException ex) {
        String message = "Requested product not found.";

        return createApiError(HttpStatus.NOT_FOUND, message, null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Invalid parameter type. Field '%s' requires a different type.", ex.getName());

        return createApiError(HttpStatus.BAD_REQUEST, message, null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolation(ConstraintViolationException ex) {
        String message = "A field value violates a specific constraint for example regex pattern.";

        return createApiError(HttpStatus.BAD_REQUEST, message, null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "The resource could not be saved due to a conflict";

        return createApiError(HttpStatus.CONFLICT, message, null);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleUserAlreadyExists(UserAlreadyExistsException ex) {
        String message = "User with given email already exists.";

        return createApiError(HttpStatus.CONFLICT, message, null);
    }

    @ExceptionHandler(ProductQuantityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleProductQuantity(ProductQuantityException ex) {
        String message = "Product quantity exceeded max quantity.";

        return createApiError(HttpStatus.BAD_REQUEST, message, null);
    }

    private ApiError createApiError(HttpStatus httpStatus,
                                    String exceptionMessage,
                                    Map<String, String> validationErrors) {

        return new ApiError(httpStatus.value(), httpStatus.getReasonPhrase(), exceptionMessage, validationErrors);
    }
}