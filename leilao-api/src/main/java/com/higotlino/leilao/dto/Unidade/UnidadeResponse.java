package com.higotlino.leilao.dto.Unidade;

import java.time.LocalDateTime;

public record UnidadeResponse(
        Long id,
        String nome,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
