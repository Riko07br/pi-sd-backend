package com.backend.projetointegrador.services;

import com.backend.projetointegrador.domain.dtos.BalanceResponseDTO;
import com.backend.projetointegrador.domain.entities.Balance;
import com.backend.projetointegrador.domain.mappers.BalanceMapper;
import com.backend.projetointegrador.repositories.BalanceRepository;
import com.backend.projetointegrador.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;

    public BalanceResponseDTO findByAccountUser(Authentication authentication) {
        Balance balance = findEntityByAccountUserEmail(authentication.getName());
        return BalanceMapper.toResponseDTO(balance);
    }

    Balance findEntityByAccountUserEmail(String email) {
        return balanceRepository.findByAccountUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Balance.class, "User email: " + email + " not found"));
    }

}
