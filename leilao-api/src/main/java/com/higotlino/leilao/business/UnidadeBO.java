package com.higotlino.leilao.business;

import com.higotlino.leilao.entity.Unidade;
import com.higotlino.leilao.exception.ResourceNotFoundException;
import com.higotlino.leilao.repository.UnidadeRepository;
import com.higotlino.leilao.specification.UnidadeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnidadeBO {

    private final UnidadeRepository unidadeRepository;

    @Transactional(readOnly = true)
    public Unidade getById(Long id) {
        return unidadeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Unidade não encontrada")
        );
    }

    @Transactional(readOnly = true)
    public Page<Unidade> paginate(String nome, Pageable pageable) {
        Specification<Unidade> spec = Specification
                .where(UnidadeSpecification.nomeContains(nome));

        return unidadeRepository.findAll(spec, pageable);
    }

    @Transactional
    public Unidade create(Unidade unidade) {
        return unidadeRepository.save(unidade);
    }

    @Transactional
    public Unidade update(Unidade unidade) {
        return unidadeRepository.save(unidade);
    }

    @Transactional
    public void delete(Long id) {
        unidadeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Unidade não encontrada")
        );
        unidadeRepository.deleteById(id);
    }
}
