package com.backend.projetointegrador.resources;

import com.backend.projetointegrador.domain.queryParams.PaginationParams;
import com.backend.projetointegrador.domain.dtos.TransactionRequestDTO;
import com.backend.projetointegrador.domain.dtos.TransactionResponseDTO;
import com.backend.projetointegrador.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionResource {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> findAll(Authentication authentication, PaginationParams paginationParams) {
        Page<TransactionResponseDTO> transactions = transactionService.findAll(authentication, paginationParams);
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable Long id, Authentication authentication) {
        TransactionResponseDTO transaction = transactionService.findById(id);
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@RequestBody TransactionRequestDTO requestDTO,
                                                         Authentication authentication) {
        TransactionResponseDTO responseDTO = transactionService.create(requestDTO, authentication);
        return ResponseEntity.ok().body(responseDTO);
    }
}
