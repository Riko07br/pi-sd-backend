package com.backend.projetointegrador.domain.dtos;

import java.time.Instant;

public record TransactionRequestDTO(
        float value,
        String type,
        Instant timestamp
) {
}
