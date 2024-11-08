package com.hkust.wmsc.struct.structmapper;

import com.hkust.entity.wms.WmsOptLog;
import com.hkust.wmsc.dto.vo.WmsOptLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmsOptLogStructMapper {

    WmsOptLogStructMapper INSTANCE = Mappers.getMapper(WmsOptLogStructMapper.class);

    WmsOptLogVO WmsOptLogToWmsOptLogVO(WmsOptLog wmsOptLog);

//    OptLogVO wmsOptLogToOptLogVO(WmsOptLog wmsOptLog);

}
