package com.higotlino.leilao.dto.Empresa;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class EmpresaUpdateRequest{
    @Length(max = 64, message = "Razao social deve ter no maximo 64 caracteres.")
    String razaoSocial;
    @Length(min = 14, max = 14, message = "CNPJ deve ter exatamente 14 caracteres.")
    String cnpj;
    @Length(max = 64, message = "Logradouro deve ter no maximo 64 caracteres.")
    String logradouro;
    @Length(max = 64, message = "Municipio deve ter no maximo 64 caracteres.")
    String municipio;
    @Length(max = 10, message = "Numero deve ter no maximo 10 caracteres.")
    String numero;
    @Length(max = 64, message = "Complemento deve ter no maximo 64 caracteres.")
    String complemento;
    @Length(max = 64, message = "Bairro deve ter no maximo 64 caracteres.")
    String bairro;
    @Length(max = 8, message = "CEP deve ter no maximo 8 caracteres.")
    String cep;
    @Length(max = 32, message = "Telefone deve ter no maximo 32 caracteres.")
    String telefone;
    @Email(message = "Email invalido.")
    String email;
    @Length(max = 1000, message = "Site deve ter no maximo 1000 caracteres.")
    String site;
    @Length(max = 20, message = "Usuario deve ter no maximo 20 caracteres.")
    String usuario;
}
