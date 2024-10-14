package com.backend.projetointegrador.services;

import com.backend.projetointegrador.domain.dtos.TransactionRequestDTO;
import com.backend.projetointegrador.domain.dtos.TransactionResponseDTO;
import com.backend.projetointegrador.domain.entities.Balance;
import com.backend.projetointegrador.domain.entities.Transaction;
import com.backend.projetointegrador.domain.mappers.TransactionMapper;
import com.backend.projetointegrador.repositories.TransactionRepository;
import com.backend.projetointegrador.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final BalanceService balanceService;

    public List<TransactionResponseDTO> findAll(Authentication authentication) {
        return transactionRepository.findByBalanceAccountUserEmail(authentication.getName())
                .stream()
                .map(transaction -> TransactionMapper.toResponseDTO(transaction))
                .toList();
    }

    public TransactionResponseDTO findById(Long id) {
        Transaction transaction = findEntityById(id);
        return TransactionMapper.toResponseDTO(transaction);
    }

    public TransactionResponseDTO create(TransactionRequestDTO requestDTO, Authentication authentication) {
        Balance balance = balanceService.findEntityByAccountUserEmail(authentication.getName());

        switch (requestDTO.type()) {
            case "DEPOSIT":
            case "SELL_INVESTMENT":
                balance.addBalance(requestDTO.value());
                break;
            case "WITHDRAW":
            case "BUY_INVESTMENT":
                balance.subtractBalance(requestDTO.value());
                break;
            default:
                throw new IllegalArgumentException("Invalid operation");
        }

        Transaction transaction = transactionRepository.save(new Transaction(null, requestDTO.value(), requestDTO.type()));
        transaction.setBalance(balance);
        transactionRepository.save(transaction);
        return TransactionMapper.toResponseDTO(transaction);
    }

    Transaction findEntityById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Transaction.class, id));
    }
}
