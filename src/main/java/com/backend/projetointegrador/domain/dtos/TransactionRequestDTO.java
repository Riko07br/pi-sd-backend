package com.backend.projetointegrador.domain.dtos;

public record TransactionRequestDTO(
        Float value,
        String type,
        String description
) {
}
