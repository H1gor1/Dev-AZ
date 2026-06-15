package com.higotlino.leilao.dto.Lote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoteRequest{
        int numeroLote;
        @NotNull(message = "Descricao e obrigatoria.")
        String descricao;
        @NotNull(message = "Quantidade e obrigatoria.")
        Double quantidade;
        double valorInicial;
        @NotNull(message = "Id da unidade e obrigatorio.")
        Long unidadeId;
        @NotNull(message = "Id do leilao e obrigatorio.")
        Long leilaoId;
}
