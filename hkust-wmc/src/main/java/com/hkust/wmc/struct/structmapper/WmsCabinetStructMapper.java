package com.hkust.wmc.struct.structmapper;

import com.hkust.entity.wms.WmsCabinet;
import com.hkust.wmc.dto.ao.CabinetAO;
import com.hkust.wmc.dto.vo.CabinetVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmsCabinetStructMapper {

    WmsCabinetStructMapper INSTANCE = Mappers.getMapper(WmsCabinetStructMapper.class);

    @Mappings({
            @Mapping(target = "cabinetId", source = "id")
    })
    CabinetVO cabinetToCabinetVO(WmsCabinet wmsCabinet);

    WmsCabinet cabinetAOToCabinet(CabinetAO cabinetAO);
}
