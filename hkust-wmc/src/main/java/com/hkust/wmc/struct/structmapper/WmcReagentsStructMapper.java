package com.hkust.wmc.struct.structmapper;

import com.hkust.entity.wms.WmsReagents;
import com.hkust.wmc.dto.ao.ReagentsAO;
import com.hkust.wmc.dto.vo.ReagentsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmcReagentsStructMapper {

    WmcReagentsStructMapper INSTANCE = Mappers.getMapper(WmcReagentsStructMapper.class);

    WmsReagents reagentsAOToReagents(ReagentsAO reagentsAO);

    ReagentsVO reagentsToReagentsVO(WmsReagents reagents);
}
