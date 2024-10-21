package com.backend.projetointegrador.repositories;

import com.backend.projetointegrador.domain.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByBalanceAccountUserEmail(String email, Pageable pageable);
}
