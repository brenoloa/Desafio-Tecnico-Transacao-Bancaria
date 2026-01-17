package com.example.api_transacao.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record Transaction(BigDecimal valor, OffsetDateTime dataHora) {
}

