package com.higotlino.leilao.dto.Unidade;

import jakarta.validation.constraints.NotNull;

public record CreateUnidadeRequest(
        @NotNull String nome
) {
}
