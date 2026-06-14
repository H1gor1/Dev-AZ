package com.higotlino.leilao.dto.Comprador;

import jakarta.validation.constraints.NotNull;

public record CreateCompradorRequest(
        @NotNull Long empresaId,
        @NotNull Long leilaoId
) {}
