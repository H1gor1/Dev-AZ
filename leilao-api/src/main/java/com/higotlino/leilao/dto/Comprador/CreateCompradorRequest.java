package com.higotlino.leilao.dto.Comprador;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompradorRequest {
    @NotNull(message = "Id da empresa é necessário.")
    Long empresaId;
    @NotNull(message = "Id do leilão é necessário.")
    Long leilaoId;
}
