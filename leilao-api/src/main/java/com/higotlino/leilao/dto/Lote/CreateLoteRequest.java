package com.higotlino.leilao.dto.Lote;

import jakarta.validation.constraints.NotNull;

public record CreateLoteRequest(
        int numeroLote,
        @NotNull String descricao,
        @NotNull double quantidade,
        double valorInicial,
        @NotNull Long unidadeId,
        @NotNull Long leilaoId
) {
}
