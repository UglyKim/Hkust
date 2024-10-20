package com.hkust.wmsc.struct.structmapper;

import com.hkust.entity.wms.WmsInOutRecord;
import com.hkust.wmsc.dto.vo.WmsInOutRecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmsInOutRecordStructMapper {

    WmsInOutRecordStructMapper INSTANCE = Mappers.getMapper(WmsInOutRecordStructMapper.class);

    WmsInOutRecordVO WmsInOutRecordToWmsInOutRecordVO(WmsInOutRecord wmsInOutRecord );

}
