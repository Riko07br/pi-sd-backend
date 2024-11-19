package com.backend.projetointegrador.domain.dtos;

public record ProductRequestDTO(
        String name,
        Float dailyYield,
        String description
) {
}
