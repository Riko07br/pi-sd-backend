package com.backend.projetointegrador.services;

import com.backend.projetointegrador.domain.dtos.InvestmentRequestDTO;
import com.backend.projetointegrador.domain.dtos.InvestmentResponseDTO;
import com.backend.projetointegrador.domain.entities.Account;
import com.backend.projetointegrador.domain.entities.Investment;
import com.backend.projetointegrador.domain.entities.Product;
import com.backend.projetointegrador.domain.mappers.InvestmentMapper;
import com.backend.projetointegrador.repositories.AccountRepository;
import com.backend.projetointegrador.repositories.InvestmentRepository;
import com.backend.projetointegrador.services.exceptions.InvalidArgsException;
import com.backend.projetointegrador.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final AccountRepository accountRepository;

    private final UserService userService;
    private final ProductService productService;

    public List<InvestmentResponseDTO> findAll() {
        return investmentRepository.findAll().stream()
                .peek(investment -> {
                    if (!investment.getIsSold()) {
                        investment.setSellPrice(estimateSellPrice(investment));
                    }
                })
                .map(entity -> InvestmentMapper.toResponseDTO(entity)).toList();
    }

    public InvestmentResponseDTO findById(Long id) {
        Investment investment = findEntityById(id);

        if (!investment.getIsSold()) {
            investment.setSellPrice(estimateSellPrice(investment));
        }

        return InvestmentMapper.toResponseDTO(investment);
    }

    public InvestmentResponseDTO create(InvestmentRequestDTO requestDTO, Authentication authentication) {
        Account account = userService.findEntityByEmail(authentication.getName()).getAccount();
        if (account == null) {
            throw new InvalidArgsException("User does not have an account");
        }

        Product product = productService.findEntityById(requestDTO.productId());

        Investment investment = new Investment(null, requestDTO.buyPrice(), account, product);
        account.subtractBalance(requestDTO.buyPrice());

        investment = investmentRepository.save(investment);
        accountRepository.save(account);

        return InvestmentMapper.toResponseDTO(investment);
    }

    public void delete(Long id) {
        try {
            investmentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(Investment.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public InvestmentResponseDTO sell(Long id) {
        Investment investment = findEntityById(id);
        if (investment.getIsSold()) {
            throw new InvalidArgsException("Investment already sold");
        }

        investment.setSellPrice(estimateSellPrice(investment));
        investment.setSellTime(Instant.now());
        investment.setIsSold(true);
        investment.getAccount().addBalance(investment.getSellPrice());
        investmentRepository.save(investment);
        accountRepository.save(investment.getAccount());

        return InvestmentMapper.toResponseDTO(investment);
    }

    Investment findEntityById(Long id) {
        return investmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Investment.class, id));
    }

    private Float estimateSellPrice(Investment investment) {
        Product product = investment.getProduct();
        long days = ChronoUnit.DAYS.between(investment.getBuyTime(), Instant.now());
        return investment.getBuyPrice() * (float) Math.pow(1f + product.getDailyYield(), days);
    }
}
