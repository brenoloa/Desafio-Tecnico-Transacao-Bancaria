package com.example.api_transacao.service;

import com.example.api_transacao.dto.TransactionRequestDTO;
import com.example.api_transacao.repository.InMemoryTransactionRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionalServiceTest {

    @Test
    void registrarTransacao_quandoNoFuturo_lancaUnprocessable() {
        Clock fixed = Clock.fixed(Instant.parse("2020-01-01T00:00:00Z"), ZoneOffset.UTC);
        TransactionalService service = new TransactionalService(new InMemoryTransactionRepository(), fixed);

        TransactionRequestDTO request = new TransactionRequestDTO(
                new BigDecimal("10.00"),
                OffsetDateTime.parse("2020-01-01T00:00:01Z")
        );

        assertThrows(UnprocessableTransactionException.class, () -> service.registrarTransacao(request));
    }

    @Test
    void calcularEstatisticas_quandoSoAntigas_retornaZeros() {
        Clock fixed = Clock.fixed(Instant.parse("2020-01-01T00:01:00Z"), ZoneOffset.UTC);
        InMemoryTransactionRepository repo = new InMemoryTransactionRepository();
        TransactionalService service = new TransactionalService(repo, fixed);

        // 61 seconds old -> outside the window
        service.registrarTransacao(new TransactionRequestDTO(
                new BigDecimal("1.00"),
                OffsetDateTime.parse("2019-12-31T23:59:59Z")
        ));

        var stats = service.calcularEstatisticasUltimos60Segundos();
        assertEquals(0L, stats.getCount());
        assertEquals(new BigDecimal("0"), stats.getSum());
        assertEquals(new BigDecimal("0"), stats.getAvg());
        assertEquals(new BigDecimal("0"), stats.getMin());
        assertEquals(new BigDecimal("0"), stats.getMax());
    }
}

