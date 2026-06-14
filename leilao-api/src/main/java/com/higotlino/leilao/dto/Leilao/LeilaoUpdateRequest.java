package com.higotlino.leilao.dto.Leilao;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record LeilaoUpdateRequest(
        Integer codigo,
        @Length(max = 60) String descricao,
        LocalDateTime inicioPrevisto
) {}
