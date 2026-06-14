package com.higotlino.leilao.repository;

import com.higotlino.leilao.entity.Lote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    @EntityGraph(attributePaths = {"unidade", "leilao", "leilao.vendedor"})
    Page<Lote> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"unidade", "leilao", "leilao.vendedor"})
    Optional<Lote> findById(Long id);
}
