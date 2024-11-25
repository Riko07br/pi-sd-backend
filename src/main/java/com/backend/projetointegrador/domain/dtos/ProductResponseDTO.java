package com.backend.projetointegrador.domain.dtos;

public record ProductResponseDTO(
        Long id,
        String name,
        Float dailyYield,
        String description
) {
}
