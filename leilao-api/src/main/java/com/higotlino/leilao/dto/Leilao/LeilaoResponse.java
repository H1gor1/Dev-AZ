package com.higotlino.leilao.dto.Leilao;

import com.higotlino.leilao.dto.Empresa.EmpresaResponse;

import java.time.LocalDateTime;

public record LeilaoResponse (
        Long id,
        int codigo,
        String descricao,
        EmpresaResponse vendedor,
        LocalDateTime inicioPrevisto,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
