package com.backend.projetointegrador.repositories;

import com.backend.projetointegrador.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}