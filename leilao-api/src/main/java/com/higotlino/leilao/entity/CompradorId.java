package com.higotlino.leilao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CompradorId implements Serializable {

    @Column(name = "empresa_id")
    private Long empresaId;

    @Column(name = "leilao_id")
    private Long leilaoId;
}
