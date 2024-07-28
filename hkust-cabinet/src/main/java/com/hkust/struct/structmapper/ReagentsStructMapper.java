package com.hkust.struct.structmapper;

import com.hkust.dto.ao.ReagentsAO;
import com.hkust.dto.vo.ReagentsVO;
import com.hkust.entity.Reagents;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReagentsStructMapper {

    ReagentsStructMapper INSTANCE = Mappers.getMapper(ReagentsStructMapper.class);

    ReagentsVO toReagentsVO(Reagents reagents);

    Reagents toReagents(ReagentsAO reagentsAO);
}
