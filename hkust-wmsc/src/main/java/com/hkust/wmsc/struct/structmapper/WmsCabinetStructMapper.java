package com.hkust.wmsc.struct.structmapper;

import com.hkust.entity.wms.WmsCabinet;
import com.hkust.wmsc.dto.vo.CabinetVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmsCabinetStructMapper {

    WmsCabinetStructMapper INSTANCE = Mappers.getMapper(WmsCabinetStructMapper.class);

    CabinetVO wmsCabinetToCabinetVO(WmsCabinet wmsCabinet);
}
