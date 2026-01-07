package com.wcl.gtc.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wcl.gtc.dto.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* =========================
       400 — VALIDATION ERROR
       ========================= */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrorResponse response = new ApiErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Validation failed");
        response.setErrors(errors);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /* =========================
       403 — UNAUTHORIZED / FORBIDDEN     related to security
       ========================= */
    // @ExceptionHandler(AccessDeniedException.class)
    // public ResponseEntity<ApiErrorResponse> handleAccessDenied(
    //         AccessDeniedException ex) {

    //     ApiErrorResponse response = new ApiErrorResponse();
    //     response.setStatus(HttpStatus.FORBIDDEN.value());
    //     response.setMessage("Access denied");
    //     response.setTimestamp(LocalDateTime.now());

    //     return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    // }

    /* =========================
       404 — RESOURCE NOT FOUND
       ========================= */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            RuntimeException ex) {

        ApiErrorResponse response = new ApiErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /* =========================
       409 — CONFLICT      on duplicate entries
       ========================= */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(
            ConflictException ex) {

        ApiErrorResponse response = new ApiErrorResponse();
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /* =========================
       500 — INTERNAL SERVER ERROR on unknown errors or system failures
       ========================= */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(
            Exception ex) {

        ApiErrorResponse response = new ApiErrorResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Something went wrong. Please contact support.");
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

