package com.higotlino.leilao.dto.Empresa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class EmpresaResponse{
        Long id;
        String razaoSocial;
        String cnpj;
        String logradouro;
        String municipio;
        String numero;
        String complemento;
        String bairro;
        String cep;
        String telefone;
        String email;
        String site;
        String usuario;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;

}
