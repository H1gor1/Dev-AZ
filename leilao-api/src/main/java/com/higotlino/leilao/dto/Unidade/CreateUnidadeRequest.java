package com.higotlino.leilao.dto.Unidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUnidadeRequest{
        @NotBlank(message = "Nome e obrigatorio.")
        String nome;
}
