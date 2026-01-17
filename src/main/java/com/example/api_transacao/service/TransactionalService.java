package com.example.api_transacao.service;

import com.example.api_transacao.domain.Transaction;
import com.example.api_transacao.dto.TransactionRequestDTO;
import com.example.api_transacao.dto.TransactionStatisticsResponseDTO;
import com.example.api_transacao.repository.InMemoryTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionalService {

    private static final int SCALE = 2;

    private final InMemoryTransactionRepository repository;
    private final Clock clock;

    public void registrarTransacao(TransactionRequestDTO request) {
        OffsetDateTime now = OffsetDateTime.now(clock);
        if (request.getDataHora().isAfter(now)) {
            throw new UnprocessableTransactionException("Transacao no futuro");
        }

        repository.save(new Transaction(request.getValor(), request.getDataHora()));
    }

    public void limparTransacoes() {
        repository.clear();
    }

    public TransactionStatisticsResponseDTO calcularEstatisticasUltimos60Segundos() {
        OffsetDateTime now = OffsetDateTime.now(clock);
        OffsetDateTime cutoff = now.minusSeconds(60);

        List<Transaction> snapshot = repository.findAllSnapshot();

        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal min = null;
        BigDecimal max = null;
        long count = 0;

        for (Transaction t : snapshot) {
            OffsetDateTime dataHora = t.dataHora();
            if (dataHora.isBefore(cutoff) || dataHora.isAfter(now)) {
                continue;
            }

            BigDecimal valor = t.valor();
            sum = sum.add(valor);
            min = (min == null) ? valor : min.min(valor);
            max = (max == null) ? valor : max.max(valor);
            count++;
        }

        if (count == 0) {
            return new TransactionStatisticsResponseDTO(0L, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        BigDecimal avg = sum.divide(BigDecimal.valueOf(count), SCALE, RoundingMode.HALF_UP);

        return new TransactionStatisticsResponseDTO(
                count,
                sum.setScale(SCALE, RoundingMode.HALF_UP),
                avg,
                min.setScale(SCALE, RoundingMode.HALF_UP),
                max.setScale(SCALE, RoundingMode.HALF_UP)
        );
    }
}
