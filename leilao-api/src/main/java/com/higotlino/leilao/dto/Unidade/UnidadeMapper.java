package com.higotlino.leilao.dto.Unidade;

import com.higotlino.leilao.entity.Unidade;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UnidadeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Unidade toEntity(CreateUnidadeRequest request);

    UnidadeResponse toResponse(Unidade unidade);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateUnidadeRequest request, @MappingTarget Unidade entity);

}
