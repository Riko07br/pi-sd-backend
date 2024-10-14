package com.backend.projetointegrador.domain.dtos;

import java.time.Instant;

public record TransactionResponseDTO(
        Long id,
        Float value,
        String type,
        Instant timestamp
) {
}
