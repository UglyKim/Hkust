package com.hkust.wmc.struct.structmapper;

import com.hkust.entity.wms.WmsReagents;
import com.hkust.wmc.dto.ao.AddReagentsAO;
import com.hkust.wmc.dto.vo.ReagentsVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmcReagentsStructMapper {

    WmcReagentsStructMapper INSTANCE = Mappers.getMapper(WmcReagentsStructMapper.class);

    @Mappings({
            @Mapping(target = "reagentsId", source = "id")
    })
    ReagentsVO reagentsToReagentsVO(WmsReagents reagents);


    @Mappings({
            @Mapping(target = "id", source = "reagentsId")
    })
    WmsReagents reagentsAOToReagents(AddReagentsAO addReagentsAO);
}
