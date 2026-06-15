package com.higotlino.leilao.repository;

import com.higotlino.leilao.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>, JpaSpecificationExecutor<Empresa> {
    boolean existsEmpresaByCnpj(String cnpj);
    boolean existsEmpresaByUsuario(String usuario);
}
