package com.example.api_transacao.rest;

import com.example.api_transacao.service.UnprocessableTransactionException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UnprocessableTransactionException.class)
    public ResponseEntity<Void> handleUnprocessable(UnprocessableTransactionException ignored) {
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Void> handleValidation(MethodArgumentNotValidException ignored) {
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> handleBadJson(HttpMessageNotReadableException ignored) {
        return ResponseEntity.badRequest().build();
    }
}
