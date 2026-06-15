package com.higotlino.leilao.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "razao_social", nullable = false, length = 64)
    private String razaoSocial;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(length = 64)
    private String logradouro;

    @Column(length = 64)
    private String municipio;

    @Column(length = 10)
    private String numero;

    @Column(length = 64)
    private String complemento;

    @Column(length = 64)
    private String bairro;

    @Column(length = 8)
    private String cep;

    @Column(length = 32)
    private String telefone;

    @Column(nullable = false)
    private String email;

    @Column(length = 1000)
    private String site;

    @Column(nullable = false, unique = true, length = 20)
    private String usuario;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
