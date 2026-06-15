package com.higotlino.leilao.dto.Lote;

import com.higotlino.leilao.dto.Leilao.LeilaoResponse;
import com.higotlino.leilao.dto.Unidade.UnidadeResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class LoteResponse{
        Long id;
        int numeroLote;
        String descricao;
        double quantidade;
        double valorInicial;
        UnidadeResponse unidade;
        LeilaoResponse leilao;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
}
