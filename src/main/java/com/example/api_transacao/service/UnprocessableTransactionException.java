package com.example.api_transacao.service;


public class UnprocessableTransactionException extends RuntimeException {
    public UnprocessableTransactionException(String message) {
        super(message);
    }
}

