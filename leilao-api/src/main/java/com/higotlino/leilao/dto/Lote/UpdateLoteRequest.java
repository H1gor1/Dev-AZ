package com.higotlino.leilao.dto.Lote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLoteRequest{
    Integer numeroLote;
    String descricao;
    Double quantidade;
    Double valorInicial;
    Long unidadeId;
}
