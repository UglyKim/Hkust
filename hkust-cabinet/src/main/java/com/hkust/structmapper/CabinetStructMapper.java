package com.hkust.structmapper;

import com.hkust.dto.ao.CabinetAO;
import com.hkust.dto.vo.CabinetVO;
import com.hkust.entity.Cabinet;
import com.hkust.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CabinetStructMapper {

    CabinetStructMapper INSTANCE = Mappers.getMapper(CabinetStructMapper.class);

    CabinetVO toVO(Cabinet cabinet);

    Cabinet cabinetAOToCabinet(CabinetAO cabinetAO);
}
