package com.higotlino.leilao.dto.Unidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUnidadeRequest{
        @Length(min = 1, max = 128, message = "Nome deve ter entre 1 e 128 caracteres.")
        String nome;
}
