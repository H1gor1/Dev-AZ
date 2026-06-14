package com.higotlino.leilao.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comprador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comprador {

    @EmbeddedId
    private CompradorId id;

    @MapsId("empresaId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @MapsId("leilaoId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leilao_id")
    private Leilao leilao;
}
