package com.higotlino.leilao.dto.Comprador;

public record CompradorResponse(
        Long empresaId,
        String razaoSocial,
        Long leilaoId,
        String descricaoLeilao
) {
}
