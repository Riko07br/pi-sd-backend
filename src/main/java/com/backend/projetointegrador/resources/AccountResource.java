package com.backend.projetointegrador.resources;

import com.backend.projetointegrador.domain.dtos.AccountRequestDTO;
import com.backend.projetointegrador.domain.dtos.AccountResponseDTO;
import com.backend.projetointegrador.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountResource {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Page<AccountResponseDTO>> findAll(Pageable pageable) {
        Page<AccountResponseDTO> accounts = accountService.findAll(pageable);
        return ResponseEntity.ok().body(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(@PathVariable Long id) {
        AccountResponseDTO responseDTO = accountService.findById(id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@RequestBody AccountRequestDTO accountRequestDTO, Authentication authentication) throws URISyntaxException {
        AccountResponseDTO responseDTO = accountService.create(accountRequestDTO, authentication);
        return ResponseEntity.created(new URI("/accounts/" + responseDTO.id())).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> update(@PathVariable Long id, @RequestBody AccountRequestDTO requestDTO) {
        AccountResponseDTO responseDTO = accountService.update(id, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

}