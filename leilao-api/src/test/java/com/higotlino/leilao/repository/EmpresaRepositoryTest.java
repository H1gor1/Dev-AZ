package com.higotlino.leilao.repository;

import com.higotlino.leilao.entity.Empresa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.liquibase.enabled=false"
})
class EmpresaRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private EmpresaRepository repository;

    @Test
    @DisplayName("existsEmpresaByCnpj retorna true quando CNPJ ja foi salvo")
    void existsByCnpjShouldReturnTrue() {
        Empresa empresa = Empresa.builder()
                .razaoSocial("Teste")
                .cnpj("12345678000199")
                .email("teste@teste.com")
                .usuario("teste")
                .password("123")
                .build();
        em.persistAndFlush(empresa);

        boolean exists = repository.existsEmpresaByCnpj("12345678000199");

        assertTrue(exists);
    }

    @Test
    @DisplayName("existsEmpresaByCnpj retorna false quando CNPJ nao existe")
    void existsByCnpjShouldReturnFalse() {
        boolean exists = repository.existsEmpresaByCnpj("00000000000000");
        assertFalse(exists);
    }
}
