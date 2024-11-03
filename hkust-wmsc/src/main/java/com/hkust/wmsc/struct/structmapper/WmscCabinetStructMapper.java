package com.hkust.wmsc.struct.structmapper;

import com.hkust.entity.wms.WmsCabinet;
import com.hkust.wmsc.dto.vo.CabinetVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmscCabinetStructMapper {


    WmscCabinetStructMapper INSTANCE = Mappers.getMapper(WmscCabinetStructMapper.class);

    @Mappings({
            @Mapping(target = "cabinetId", source = "id")
    })
    CabinetVO wmsCabinetToCabinetVO(WmsCabinet wmsCabinet);
}
