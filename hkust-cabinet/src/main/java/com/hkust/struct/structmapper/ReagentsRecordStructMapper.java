package com.hkust.struct.structmapper;

import com.hkust.dto.vo.ReagentsOptVO;
import com.hkust.entity.ReagentsRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReagentsRecordStructMapper {

    ReagentsRecordStructMapper INSTANCE = Mappers.getMapper(ReagentsRecordStructMapper.class);

    ReagentsOptVO toVO(ReagentsRecord reagentsRecord);

}
