package com.higotlino.leilao.dto.Lote;

import com.higotlino.leilao.dto.Leilao.LeilaoMapper;
import com.higotlino.leilao.dto.Unidade.UnidadeMapper;
import com.higotlino.leilao.entity.Lote;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UnidadeMapper.class, LeilaoMapper.class})
public interface LoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "unidade", ignore = true)
    @Mapping(target = "leilao", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Lote toEntity(CreateLoteRequest request);

    LoteResponse toResponse(Lote lote);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "unidade", ignore = true)
    @Mapping(target = "leilao", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateLoteRequest request, @MappingTarget Lote entity);
}
