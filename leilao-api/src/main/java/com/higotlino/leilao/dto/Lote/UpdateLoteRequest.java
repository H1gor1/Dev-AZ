package com.higotlino.leilao.dto.Lote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLoteRequest{
    Integer numeroLote;
    @Length(min = 1, max = 60, message = "Descricao deve ter entre 1 e 60 caracteres.")
    String descricao;
    Double quantidade;
    @NotNull(message = "Valor inicial e obrigatorio.")
    Double valorInicial;
    Long unidadeId;
}
