package com.hkust.wmsc.struct.structmapper;

import com.hkust.entity.User;
import com.hkust.wmsc.dto.vo.UserVO;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmsUserStructMapper {

    WmsUserStructMapper INSTANCE = Mappers.getMapper(WmsUserStructMapper.class);

    @Mappings({
            @Mapping(target = "userName", source = "username"),
            @Mapping(target = "roleList", ignore = true)
    })
    UserVO userToUserAO(User user);
}
