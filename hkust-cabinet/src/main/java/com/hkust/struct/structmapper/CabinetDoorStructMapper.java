package com.hkust.struct.structmapper;

import com.hkust.dto.ao.CabinetDoorAO;
import com.hkust.entity.CabinetDoor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CabinetDoorStructMapper {

    CabinetDoorStructMapper INSTANCE = Mappers.getMapper(CabinetDoorStructMapper.class);

    CabinetDoor cabinetDoorAOToCabinet(CabinetDoorAO cabinetDoorAO);
}
