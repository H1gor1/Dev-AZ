package com.higotlino.leilao.dto.Lote;

import com.higotlino.leilao.dto.Leilao.LeilaoResponse;
import com.higotlino.leilao.dto.Unidade.UnidadeResponse;
import com.higotlino.leilao.entity.Unidade;

import java.time.LocalDateTime;

public record LoteResponse(
        Long id,
        int numeroLote,
        String descricao,
        double quantidade,
        double valorInicial,
        UnidadeResponse unidade,
        LeilaoResponse leilao,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
