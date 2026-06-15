package com.higotlino.leilao.dto.Leilao;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLeilaoRequest{
        int codigo;
        @NotNull(message = "Descricao e obrigatoria.")
        @Length(max = 60, message = "Descricao deve ter no maximo 60 caracteres.")
        String descricao;
        @NotNull(message = "Id do vendedor e obrigatorio.")
        Long vendedorId;
        @NotNull(message = "Inicio previsto e obrigatorio.")
        LocalDateTime inicioPrevisto;
}
