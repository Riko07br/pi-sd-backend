package com.backend.projetointegrador.domain.dtos;

import java.time.LocalDate;

public record AccountRequestDTO(
        String name,
        String document,
        String telephone,
        LocalDate dateOfBirth
) {
}
