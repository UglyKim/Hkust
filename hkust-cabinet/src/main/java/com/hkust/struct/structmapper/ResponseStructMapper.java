package com.hkust.struct.structmapper;

import com.hkust.dto.ao.ReagentsAO;
import com.hkust.dto.vo.ReagentsVO;
import com.hkust.entity.Reagents;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResponseStructMapper {

    ResponseStructMapper INSTANCE = Mappers.getMapper(ResponseStructMapper.class);

    ReagentsVO toVO(Reagents reagents);

    Reagents toAO(ReagentsAO reagentsAO);
}
