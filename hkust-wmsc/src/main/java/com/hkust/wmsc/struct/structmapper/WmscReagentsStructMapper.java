package com.hkust.wmsc.struct.structmapper;

import com.hkust.entity.wms.WmsReagents;
import com.hkust.wmsc.dto.ao.InReagentsAO;
import com.hkust.wmsc.dto.ao.OutReagentsAO;
import com.hkust.wmsc.dto.vo.ReagentsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmscReagentsStructMapper {

    WmscReagentsStructMapper INSTANCE = Mappers.getMapper(WmscReagentsStructMapper.class);

    WmsReagents InReagentsAOToReagents(InReagentsAO inReagentsAO);

    WmsReagents OutReagentsAOToReagents(OutReagentsAO outReagentsAO);

    ReagentsVO ReagentsToReagentsVO(WmsReagents wmsReagents);
}

