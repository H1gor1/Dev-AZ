package com.higotlino.leilao.dto.Leilao;

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
        @Length(min = 1, max = 60, message = "Descricao deve ter entre 1 e 60 caracteres.")
        String descricao;
        LocalDateTime inicioPrevisto;
}
