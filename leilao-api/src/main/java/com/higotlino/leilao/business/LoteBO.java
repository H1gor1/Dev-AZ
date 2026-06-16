package com.higotlino.leilao.business;

import com.higotlino.leilao.entity.Leilao;
import com.higotlino.leilao.entity.Lote;
import com.higotlino.leilao.entity.Unidade;
import com.higotlino.leilao.exception.ResourceNotFoundException;
import com.higotlino.leilao.repository.LeilaoRepository;
import com.higotlino.leilao.repository.LoteRepository;
import com.higotlino.leilao.repository.UnidadeRepository;
import com.higotlino.leilao.specification.LoteSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoteBO {

    private final LoteRepository loteRepository;
    private final UnidadeRepository unidadeRepository;
    private final LeilaoRepository leilaoRepository;

    @Transactional(readOnly = true)
    public Lote getById(Long id) {
        return loteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lote não encontrado")
        );
    }

    @Transactional(readOnly = true)
    public Page<Lote> paginate(String descricao, Integer numeroLote, Long leilaoId, Pageable pageable) {
        Specification<Lote> spec = Specification
                .where(LoteSpecification.descricaoContains(descricao))
                .and(LoteSpecification.numeroLoteEquals(numeroLote))
                .and(LoteSpecification.leilaoIdEquals(leilaoId));

        return loteRepository.findAll(spec, pageable);
    }

    @Transactional
    public Lote create(Lote lote, Long unidadeId, Long leilaoId) {
        Unidade unidade = unidadeRepository.findById(unidadeId)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade nao encontrada"));
        Leilao leilao = leilaoRepository.findById(leilaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Leilao nao encontrado"));
        lote.setUnidade(unidade);
        lote.setLeilao(leilao);
        return loteRepository.save(lote);
    }

    @Transactional
    public Lote update(Lote lote, Long unidadeId) {
        if (unidadeId != null) {
            Unidade unidade = unidadeRepository.findById(unidadeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Unidade nao encontrada"));
            lote.setUnidade(unidade);
        }
        return loteRepository.save(lote);
    }

    @Transactional
    public void delete(Long id) {
        loteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lote nao encontrado")
        );
        loteRepository.deleteById(id);
    }
}
