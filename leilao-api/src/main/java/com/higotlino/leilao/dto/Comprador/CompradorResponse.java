package com.higotlino.leilao.dto.Comprador;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompradorResponse {
    private Long empresaId;
    private String razaoSocial;
    private Long leilaoId;
    private String descricaoLeilao;
}
