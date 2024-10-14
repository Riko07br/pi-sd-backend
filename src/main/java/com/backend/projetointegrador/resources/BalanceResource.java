package com.backend.projetointegrador.resources;

import com.backend.projetointegrador.domain.dtos.BalanceResponseDTO;
import com.backend.projetointegrador.services.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceResource {
    private final BalanceService balanceService;

    @GetMapping
    public ResponseEntity<BalanceResponseDTO> findByAccountUser(Authentication authentication) {
        BalanceResponseDTO responseDTO = balanceService.findByAccountUser(authentication);
        return ResponseEntity.ok().body(responseDTO);
    }
}
