package com.higotlino.leilao.repository;

import com.higotlino.leilao.entity.Comprador;
import com.higotlino.leilao.entity.CompradorId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompradorRepository extends JpaRepository<Comprador, CompradorId> {

    @EntityGraph(attributePaths = {"empresa", "leilao"})
    List<Comprador> findAll();

    List<Comprador> findByLeilaoId(Long leilaoId);

    List<Comprador> findByEmpresaId(Long empresaId);

    void deleteByEmpresaIdAndLeilaoId(Long empresaId, Long leilaoId);

    boolean existsByEmpresaIdAndLeilaoId(Long empresaId, Long leilaoId);
}
