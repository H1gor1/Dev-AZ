package com.higotlino.leilao.business;

import com.higotlino.leilao.entity.Empresa;
import com.higotlino.leilao.exception.ResourceNotFoundException;
import com.higotlino.leilao.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmpresaBO {
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional (readOnly = true)
    public Empresa getById(Long id){
        return empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa nao encontrada"));
    }

    @Transactional (readOnly = true)
    public Page<Empresa> paginate(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    @Transactional
    public Empresa create(Empresa empresa) {
        empresa.setPassword(passwordEncoder.encode(empresa.getPassword()));
        if (empresaRepository.existsEmpresaByCnpj(empresa.getCnpj()))
            throw new DataIntegrityViolationException("Empresa ja cadastrada com este CNPJ");

        if (empresaRepository.existsEmpresaByUsuario(empresa.getUsuario()))
            throw new DataIntegrityViolationException("Empresa ja cadastrada com este usuario");

        return empresaRepository.save(empresa);
    }

    @Transactional
    public Empresa update(Empresa empresa) {
        if (empresaRepository.existsEmpresaByCnpj(empresa.getCnpj()))
            throw new DataIntegrityViolationException("Empresa ja cadastrada com este CNPJ");

        if (empresaRepository.existsEmpresaByUsuario(empresa.getUsuario()))
            throw new DataIntegrityViolationException("Empresa ja cadastrada com este usuario");

        return empresaRepository.save(empresa);
    }

    @Transactional
    public void delete(Long id) {
        if (empresaRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Empresa nao encontrada");
        empresaRepository.deleteById(id);
    }
}
