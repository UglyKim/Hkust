package com.hkust.wmc.struct.structmapper;

import com.hkust.entity.wms.WmsOptLog;
import com.hkust.wmc.dto.vo.OptLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmsOptLogStructMapper {

    WmsOptLogStructMapper INSTANCE = Mappers.getMapper(WmsOptLogStructMapper.class);

    OptLogVO wmsOptLogToOptLogVO(WmsOptLog wmsOptLog);

}
