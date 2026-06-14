package com.higotlino.leilao.dto.Empresa;

import java.time.LocalDateTime;

public record EmpresaResponse(
        Long id,
        String razaoSocial,
        String cnpj,
        String logradouro,
        String municipio,
        String numero,
        String complemento,
        String bairro,
        String cep,
        String telefone,
        String email,
        String site,
        String usuario,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
