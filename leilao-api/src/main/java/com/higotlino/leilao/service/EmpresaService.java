package com.higotlino.leilao.service;

import com.higotlino.leilao.entity.Empresa;
import com.higotlino.leilao.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional (readOnly = true)
    public Empresa getById(Long id){
        return empresaRepository.findById(id).orElse(null);
    }

    @Transactional (readOnly = true)
    public Page<Empresa> paginate(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    @Transactional
    public Empresa create(Empresa empresa) {
        empresa.setPassword(passwordEncoder.encode(empresa.getPassword()));
        return empresaRepository.save(empresa);
    }

    @Transactional
    public Empresa update(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Transactional
    public void delete(Long id) {
        empresaRepository.deleteById(id);
    }
}
