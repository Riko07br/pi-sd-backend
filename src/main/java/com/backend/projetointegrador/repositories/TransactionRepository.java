package com.backend.projetointegrador.repositories;


import com.backend.projetointegrador.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //Não sei como puxar as todas as transações
    List<Transaction> findAllByType(String type);
}

