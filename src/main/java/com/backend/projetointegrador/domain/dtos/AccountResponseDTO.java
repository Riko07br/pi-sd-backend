package com.backend.projetointegrador.domain.dtos;

import java.time.LocalDate;

public record AccountResponseDTO(
        Long id,
        String name,
        String document,
        String telephone,
        LocalDate dateOfBirth,
        Float balance,
        UserResponseDTO user
) {
}
