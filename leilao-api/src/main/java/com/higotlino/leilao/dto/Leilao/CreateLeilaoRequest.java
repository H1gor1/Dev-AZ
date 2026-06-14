package com.higotlino.leilao.dto.Leilao;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record CreateLeilaoRequest(
        int codigo,
        @NotNull @Length(max = 60) String descricao,
        @NotNull Long vendedorId,
        @NotNull LocalDateTime inicioPrevisto
) {}
