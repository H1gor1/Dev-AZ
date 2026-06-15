package com.higotlino.leilao.dto.Unidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeResponse{
        Long id;
        String nome;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
}
