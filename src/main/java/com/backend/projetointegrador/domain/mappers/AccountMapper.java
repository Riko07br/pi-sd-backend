package com.backend.projetointegrador.domain.mappers;

import com.backend.projetointegrador.domain.dtos.AccountResponseDTO;
import com.backend.projetointegrador.domain.entities.Account;

public class AccountMapper {
    public static AccountResponseDTO toResponseDTO(Account account) {
        return new AccountResponseDTO(account.getId(),
                account.getName(),
                account.getDocument(),
                account.getTelephone(),
                account.getDateOfBirth(),
                account.getBalance().getAmount(),
                UserMapper.toResponseDTO(account.getUser())
        );
    }
}
