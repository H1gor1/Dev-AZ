package com.higotlino.leilao.specification;

import com.higotlino.leilao.entity.Unidade;
import org.springframework.data.jpa.domain.Specification;

public class UnidadeSpecification {

    public static Specification<Unidade> nomeContains(String value) {
        return (root, query, cb) -> value == null || value.isBlank() ? null
                : cb.like(cb.lower(root.get("nome")), "%" + value.toLowerCase() + "%");
    }
}
