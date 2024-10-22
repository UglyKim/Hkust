package com.hkust.wmc.struct.structmapper;

import com.hkust.entity.User;
import com.hkust.wmc.dto.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmcUserStructMapper {

    WmcUserStructMapper INSTANCE = Mappers.getMapper(WmcUserStructMapper.class);

    UserVO userToUserVO(User user);
}
