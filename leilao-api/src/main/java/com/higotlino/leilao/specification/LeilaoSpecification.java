package com.higotlino.leilao.specification;

import com.higotlino.leilao.entity.Leilao;
import org.springframework.data.jpa.domain.Specification;

public class LeilaoSpecification {

    public static Specification<Leilao> codigoEquals(Integer value) {
        return (root, query, cb) -> value == null ? null
                : cb.equal(root.get("codigo"), value);
    }

    public static Specification<Leilao> descricaoContains(String value) {
        return (root, query, cb) -> value == null || value.isBlank() ? null
                : cb.like(cb.lower(root.get("descricao")), "%" + value.toLowerCase() + "%");
    }
}
