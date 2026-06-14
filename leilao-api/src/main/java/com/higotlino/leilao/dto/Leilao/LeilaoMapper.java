package com.higotlino.leilao.dto.Leilao;

import com.higotlino.leilao.dto.Empresa.EmpresaMapper;
import com.higotlino.leilao.entity.Leilao;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface LeilaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vendedor", ignore = true)
    @Mapping(target = "lotes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Leilao toEntity(CreateLeilaoRequest request);

    LeilaoResponse toResponse(Leilao entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vendedor", ignore = true)
    @Mapping(target = "lotes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(LeilaoUpdateRequest request, @MappingTarget Leilao entity);
}
