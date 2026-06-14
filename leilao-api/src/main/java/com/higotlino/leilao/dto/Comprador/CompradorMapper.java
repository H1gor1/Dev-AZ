package com.higotlino.leilao.dto.Comprador;

import com.higotlino.leilao.entity.Comprador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompradorMapper {

    @Mapping(target = "empresaId", source = "empresa.id")
    @Mapping(target = "razaoSocial", source = "empresa.razaoSocial")
    @Mapping(target = "leilaoId", source = "leilao.id")
    @Mapping(target = "descricaoLeilao", source = "leilao.descricao")
    CompradorResponse toResponse(Comprador entity);
}
