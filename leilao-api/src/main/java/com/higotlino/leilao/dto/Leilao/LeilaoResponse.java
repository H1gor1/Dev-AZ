package com.higotlino.leilao.dto.Leilao;

import com.higotlino.leilao.dto.Empresa.EmpresaResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeilaoResponse {
        Long id;
        int codigo;
        String descricao;
        EmpresaResponse vendedor;
        LocalDateTime inicioPrevisto;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
        Double total;
}
