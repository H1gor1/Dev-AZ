package com.higotlino.leilao.repository;

import com.higotlino.leilao.entity.Leilao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeilaoRepository extends JpaRepository<Leilao, Long> {

    @EntityGraph(attributePaths = {"vendedor"})
    Page<Leilao> findAll(Pageable pageable);
}
