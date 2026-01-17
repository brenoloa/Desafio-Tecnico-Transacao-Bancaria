package com.example.api_transacao.repository;

import com.example.api_transacao.domain.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class InMemoryTransactionRepository {

    private final Queue<Transaction> transactions = new ConcurrentLinkedQueue<>();

    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    public void clear() {
        transactions.clear();
    }

    /**
     * Snapshot of current transactions to avoid holding locks while processing.
     */
    public List<Transaction> findAllSnapshot() {
        return new ArrayList<>(transactions);
    }
}

