package com.higotlino.leilao.repository;

import com.higotlino.leilao.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UnidadeRepository extends JpaRepository<Unidade, Long>, JpaSpecificationExecutor<Unidade> {
}
