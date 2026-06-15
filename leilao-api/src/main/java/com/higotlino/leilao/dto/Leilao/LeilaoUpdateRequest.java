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
public class LeilaoUpdateRequest{
        Integer codigo;
        @Length(max = 60, message = "Descricao deve ter no maximo 60 caracteres.")
        String descricao;
        LocalDateTime inicioPrevisto;
}
