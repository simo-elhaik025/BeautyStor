package com.beautystor.common;

import com.beautystor.exception.ProductImageException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionResponse {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex) {
        List<ApiResponse.ErrorItem> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new ApiResponse.ErrorItem(fe.getField() + " " + fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleMalformedJson(HttpMessageNotReadableException ex) {
        String message = "Malformed JSON request";
        List<ApiResponse.ErrorItem> errors = List.of(new ApiResponse.ErrorItem(message));
        return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<ApiResponse<?>> handleNoResourceFound(NoResourceFoundException ex) {
        List<ApiResponse.ErrorItem> errors = List.of(new ApiResponse.ErrorItem("Resource not found"));
        return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<ApiResponse<?>> handleEntityNotFound(RuntimeException ex) {
        List<ApiResponse.ErrorItem> errors = List.of(new ApiResponse.ErrorItem(ex.getMessage() == null ? "Resource not found" : ex.getMessage()));
        return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgument(IllegalArgumentException ex) {
        List<ApiResponse.ErrorItem> errors = List.of(new ApiResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductImageException.class)
    public ResponseEntity<ApiResponse<?>> handleProductImageException(ProductImageException ex) {
        List<ApiResponse.ErrorItem> errors = List.of(new ApiResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrity(DataIntegrityViolationException ex) {
        List<ApiResponse.ErrorItem> errors = List.of(new ApiResponse.ErrorItem("Database constraint violation"));
        return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAll(Exception ex) {
        List<ApiResponse.ErrorItem> errors = List.of(new ApiResponse.ErrorItem("Internal server error"));
        return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
