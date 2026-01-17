package com.example.api_transacao.rest;

import com.example.api_transacao.dto.TransactionStatisticsResponseDTO;
import com.example.api_transacao.service.TransactionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StatisticsController {

    private final TransactionalService service;

    @GetMapping(path = "/estatistica", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionStatisticsResponseDTO> obterEstatisticas() {
        return ResponseEntity.ok(service.calcularEstatisticasUltimos60Segundos());
    }
}

