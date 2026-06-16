package com.higotlino.leilao.dto.Unidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUnidadeRequest{
        @NotBlank(message = "Nome e obrigatorio.")
        @Length(max = 128, message = "Nome deve ter no maximo 128 caracteres.")
        String nome;
}
