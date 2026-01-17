package com.example.api_transacao.rest;

import com.example.api_transacao.dto.TransactionRequestDTO;
import com.example.api_transacao.service.TransactionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transacao")
public class TransactionalController {

    private final TransactionalService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registrarTransacao(@Valid @RequestBody TransactionRequestDTO request) {
        service.registrarTransacao(request);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> limparTransacoes() {
        service.limparTransacoes();
        return ResponseEntity.ok().build();
    }
}
