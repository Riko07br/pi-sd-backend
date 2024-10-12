package com.backend.projetointegrador.domain.mappers;

import com.backend.projetointegrador.domain.dtos.AccountResponseDTO;
import com.backend.projetointegrador.domain.dtos.TransactionResponseDTO;
import com.backend.projetointegrador.domain.entities.Account;
import com.backend.projetointegrador.domain.entities.Transaction;

public class TransactionMapper {
    public static TransactionResponseDTO toResponseDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getValor(),
                transaction.getType(),
                transaction.getTimestamp()
        );
    }
}

