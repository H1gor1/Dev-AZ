package com.higotlino.leilao.specification;

import com.higotlino.leilao.entity.Empresa;
import org.springframework.data.jpa.domain.Specification;

public class EmpresaSpecification {

    public static Specification<Empresa> razaoSocialContains(String value) {
        return (root, query, cb) -> value == null || value.isBlank() ? null
                : cb.like(cb.lower(root.get("razaoSocial")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Empresa> cnpjEquals(String value) {
        return (root, query, cb) -> value == null || value.isBlank() ? null
                : cb.equal(root.get("cnpj"), value);
    }

    public static Specification<Empresa> emailContains(String value) {
        return (root, query, cb) -> value == null || value.isBlank() ? null
                : cb.like(cb.lower(root.get("email")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Empresa> usuarioEquals(String value) {
        return (root, query, cb) -> value == null || value.isBlank() ? null
                : cb.equal(root.get("usuario"), value);
    }
}
