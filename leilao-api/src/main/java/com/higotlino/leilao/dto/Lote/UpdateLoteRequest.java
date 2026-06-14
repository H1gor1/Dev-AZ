package com.higotlino.leilao.dto.Lote;

public record UpdateLoteRequest(
    Integer numeroLote,
    String descricao,
    Double quantidade,
    Double valorInicial,
    Long unidadeId
) {}
