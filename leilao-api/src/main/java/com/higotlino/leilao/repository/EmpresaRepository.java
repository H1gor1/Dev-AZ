package com.higotlino.leilao.repository;

import com.higotlino.leilao.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsEmpresaByCnpj(String cnpj);
    boolean existsEmpresaByUsuario(String usuario);
}
