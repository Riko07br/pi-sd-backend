package com.backend.projetointegrador.services;

import com.backend.projetointegrador.domain.queryParams.PaginationParams;
import com.backend.projetointegrador.domain.dtos.AccountRequestDTO;
import com.backend.projetointegrador.domain.dtos.AccountResponseDTO;
import com.backend.projetointegrador.domain.entities.Account;
import com.backend.projetointegrador.domain.entities.User;
import com.backend.projetointegrador.domain.mappers.AccountMapper;
import com.backend.projetointegrador.repositories.AccountRepository;
import com.backend.projetointegrador.services.exceptions.InvalidArgsException;
import com.backend.projetointegrador.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    private final UserService userService;

    public Page<AccountResponseDTO> findAll(PaginationParams paginationParams) {
        Pageable pageable = PageRequest.of(paginationParams.getPage(), paginationParams.getSize());
        return accountRepository.findAll(pageable).map(AccountMapper::toResponseDTO);
    }

    public AccountResponseDTO findById(Long id) {
        Account account = findEntityById(id);
        return AccountMapper.toResponseDTO(account);
    }

    public AccountResponseDTO create(AccountRequestDTO dto, Authentication authentication) {
        User user = userService.findEntityByEmail(authentication.getName());
        if (user.getAccount() != null) {
            throw new InvalidArgsException("User already has an account");
        }
        Account account = new Account(null,
                dto.name(),
                dto.document(),
                dto.telephone(),
                dto.dateOfBirth(),
                0f,
                user);

        return AccountMapper.toResponseDTO(accountRepository.save(account));
    }

    public AccountResponseDTO update(Long id, AccountRequestDTO dto) {
        Account account = findEntityById(id);

        account.setName(dto.name());
        account.setDocument(dto.document());
        account.setTelephone(dto.telephone());
        account.setDateOfBirth(dto.dateOfBirth());
        return AccountMapper.toResponseDTO(accountRepository.save(account));
    }

    public void delete(Long id) {
        try {
            accountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(Account.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    Account findEntityById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Account.class, id));
    }

}
