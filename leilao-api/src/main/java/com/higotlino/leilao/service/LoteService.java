package com.higotlino.leilao.service;

import com.higotlino.leilao.entity.Leilao;
import com.higotlino.leilao.entity.Lote;
import com.higotlino.leilao.entity.Unidade;
import com.higotlino.leilao.repository.LeilaoRepository;
import com.higotlino.leilao.repository.LoteRepository;
import com.higotlino.leilao.repository.UnidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;
    private final UnidadeRepository unidadeRepository;
    private final LeilaoRepository leilaoRepository;

    @Transactional(readOnly = true)
    public Lote getById(Long id) {
        return loteRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<Lote> paginate(Pageable pageable) {
        return loteRepository.findAll(pageable);
    }

    @Transactional
    public Lote create(Lote lote, Long unidadeId, Long leilaoId) {
        Unidade unidade = unidadeRepository.findById(unidadeId)
                .orElseThrow(() -> new EntityNotFoundException("Unidade nao encontrada"));
        Leilao leilao = leilaoRepository.findById(leilaoId)
                .orElseThrow(() -> new EntityNotFoundException("Leilao nao encontrado"));
        lote.setUnidade(unidade);
        lote.setLeilao(leilao);
        return loteRepository.save(lote);
    }

    @Transactional
    public Lote update(Lote lote, Long unidadeId) {
        if (unidadeId != null) {
            Unidade unidade = unidadeRepository.findById(unidadeId)
                    .orElseThrow(() -> new EntityNotFoundException("Unidade nao encontrada"));
            lote.setUnidade(unidade);
        }
        return loteRepository.save(lote);
    }

    @Transactional
    public void delete(Long id) {
        loteRepository.deleteById(id);
    }
}
