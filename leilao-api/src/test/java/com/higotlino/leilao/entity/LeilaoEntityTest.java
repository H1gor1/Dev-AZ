package com.higotlino.leilao.entity;

import com.higotlino.leilao.repository.LeilaoRepository;
import com.higotlino.leilao.repository.LoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.liquibase.enabled=false"
})
class LeilaoEntityTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private LeilaoRepository leilaoRepository;

    @Autowired
    private LoteRepository loteRepository;

    private Empresa vendedor;
    private Unidade unidade;
    private Leilao leilao;

    @BeforeEach
    void setUp() {
        vendedor = em.persistAndFlush(Empresa.builder()
                .razaoSocial("Vendedor")
                .cnpj("12345678000199")
                .email("vendedor@teste.com")
                .usuario("vendedor")
                .password("123")
                .build());

        unidade = new Unidade();
        unidade.setNome("unidade");
        unidade = em.persistAndFlush(unidade);

        leilao = Leilao.builder()
                .codigo(1)
                .descricao("Leilao teste")
                .vendedor(vendedor)
                .inicioPrevisto(LocalDateTime.now().plusDays(7))
                .build();
        leilao = em.persistAndFlush(leilao);
    }

    @Test
    @DisplayName("@Formula total deve ser 0 quando leilao nao tem lotes")
    void totalShouldBeZeroWhenNoLotes() {
        em.clear();

        Leilao reloaded = em.find(Leilao.class, leilao.getId());

        assertEquals(0.0, reloaded.getTotal());
    }

    @Test
    @DisplayName("@Formula total deve calcular SUM(quantidade * valorInicial) dos lotes")
    void totalShouldSumLotes() {
        Lote lote1 = Lote.builder()
                .numeroLote(1).descricao("Lote 1")
                .quantidade(100.0).valorInicial(10.0)
                .unidade(unidade).leilao(leilao)
                .build();
        em.persistAndFlush(lote1);

        Lote lote2 = Lote.builder()
                .numeroLote(2).descricao("Lote 2")
                .quantidade(50.0).valorInicial(20.0)
                .unidade(unidade).leilao(leilao)
                .build();
        em.persistAndFlush(lote2);

        em.clear();

        Leilao reloaded = em.find(Leilao.class, leilao.getId());

        // (100*10) + (50*20) = 1000 + 1000 = 2000
        assertEquals(2000.0, reloaded.getTotal());
    }
}
