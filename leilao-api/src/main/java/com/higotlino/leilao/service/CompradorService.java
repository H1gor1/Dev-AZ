package com.higotlino.leilao.service;

import com.higotlino.leilao.entity.Comprador;
import com.higotlino.leilao.entity.Empresa;
import com.higotlino.leilao.entity.Leilao;
import com.higotlino.leilao.repository.CompradorRepository;
import com.higotlino.leilao.repository.EmpresaRepository;
import com.higotlino.leilao.repository.LeilaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompradorService {
    private final CompradorRepository compradorRepository;
    private final LeilaoRepository leilaoRepository;
    private final EmpresaRepository empresaRepository;

    @Transactional(readOnly = true)
    public List<Comprador> getByLeilaoId(Long leilaoId) {
        leilaoRepository.findById(leilaoId).orElseThrow(
                () -> new EntityNotFoundException("Leilao nao encontrado")
        );

        return compradorRepository.findByLeilaoId(leilaoId);
    }

    @Transactional(readOnly = true)
    public List<Comprador> getByEmpresaId(Long empresaId) {
        empresaRepository.findById(empresaId).orElseThrow(
                () -> new EntityNotFoundException("Empresa nao encontrada")
        );
        return compradorRepository.findByEmpresaId(empresaId);
    }

    @Transactional (readOnly = true)
    public Page<Comprador> paginate(Pageable pageable) {
        return compradorRepository.findAll(pageable);
    }

    @Transactional
    public Comprador create(Long leilaoId, Long empresaId) {
        Leilao leilao = leilaoRepository.findById(leilaoId).orElseThrow(
                () -> new EntityNotFoundException("Leilao nao encontrado")
        );
        Empresa empresa = empresaRepository.findById(empresaId).orElseThrow(
                () -> new EntityNotFoundException("Empresa nao encontrada")
        );

        Comprador comprador = new Comprador();
        comprador.setLeilao(leilao);
        comprador.setEmpresa(empresa);
        return compradorRepository.save(comprador);
    }

    @Transactional
    public void delete(Long empresaId, Long leilaoId) {
        compradorRepository.deleteByEmpresaIdAndLeilaoId(empresaId, leilaoId);
    }

}
