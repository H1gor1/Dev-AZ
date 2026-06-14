package com.higotlino.leilao.dto.Empresa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record EmpresaUpdateRequest(
    @Length(max = 64) String razaoSocial,
    @Length(min = 14, max = 14) String cnpj,
    @Length(max = 64) String logradouro,
    @Length(max = 64) String municipio,
    @Length(max = 10) String numero,
    @Length(max = 64) String complemento,
    @Length(max = 64) String bairro,
    @Length(max = 8) String cep,
    @Length(max = 32) String telefone,
    @Email String email,
    @Length(max = 1000) String site,
    @Length(max = 20) String usuario
) {
}
