package com.hkust.structmapper;

import com.hkust.dto.vo.CabinetVO;
import com.hkust.dto.vo.EventVO;
import com.hkust.entity.Cabinet;
import com.hkust.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventStructMapper {

    EventStructMapper INSTANCE = Mappers.getMapper(EventStructMapper.class);

    EventVO toVO(Event event);
}
