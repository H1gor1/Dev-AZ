package com.higotlino.leilao.business;

import com.higotlino.leilao.entity.Empresa;
import com.higotlino.leilao.entity.Leilao;
import com.higotlino.leilao.exception.ResourceNotFoundException;
import com.higotlino.leilao.repository.EmpresaRepository;
import com.higotlino.leilao.repository.LeilaoRepository;
import com.higotlino.leilao.specification.LeilaoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeilaoBO {
    private final LeilaoRepository leilaoRepository;
    private final EmpresaRepository empresaRepository;

    @Transactional(readOnly = true)
    public Leilao getById(Long id){
        return leilaoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Leilao não encontrado")
        );
    }

    @Transactional(readOnly = true)
    public Page<Leilao> paginate(Integer codigo, String descricao, Pageable pageable) {
        Specification<Leilao> spec = Specification
                .where(LeilaoSpecification.codigoEquals(codigo))
                .and(LeilaoSpecification.descricaoContains(descricao));

        return leilaoRepository.findAll(spec, pageable);
    }

    @Transactional
    public Leilao create(Leilao leilao, Long vendedorId) {
        Empresa vendedor = empresaRepository.findById(vendedorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado"));
        leilao.setVendedor(vendedor);
        return leilaoRepository.save(leilao);
    }

    @Transactional
    public Leilao update(Leilao leilao) {
        return leilaoRepository.save(leilao);
    }

    @Transactional
    public void delete(Long id) {
        leilaoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Leilao não encontrado")
        );
        leilaoRepository.deleteById(id);
    }
}
