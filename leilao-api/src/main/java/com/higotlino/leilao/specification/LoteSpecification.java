package com.higotlino.leilao.specification;

import com.higotlino.leilao.entity.Lote;
import org.springframework.data.jpa.domain.Specification;

public class LoteSpecification {

    public static Specification<Lote> descricaoContains(String value) {
        return (root, query, cb) -> value == null || value.isBlank() ? null
                : cb.like(cb.lower(root.get("descricao")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Lote> numeroLoteEquals(Integer value) {
        return (root, query, cb) -> value == null ? null
                : cb.equal(root.get("numeroLote"), value);
    }

    public static Specification<Lote> leilaoIdEquals(Long value) {
        return (root, query, cb) -> value == null ? null
                : cb.equal(root.get("leilao").get("id"), value);
    }
}
