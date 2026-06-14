package com.higotlino.leilao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "razaoSocial", nullable = false, length = 64)
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
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;
}
