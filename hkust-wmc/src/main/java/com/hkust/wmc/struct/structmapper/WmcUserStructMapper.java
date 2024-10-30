package com.hkust.wmc.struct.structmapper;

import com.hkust.entity.User;
import com.hkust.wmc.dto.ao.AddUserAO;
import com.hkust.wmc.dto.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WmcUserStructMapper {

    WmcUserStructMapper INSTANCE = Mappers.getMapper(WmcUserStructMapper.class);

    @Mappings({
            @Mapping(target = "roleList", ignore = true)
    })
    UserVO userToUserVO(User user);


//////    @Mappings({
////            @Mapping(target = "studentId", ignore = true)
//    })
    User userAOToUser(AddUserAO addUserAO);
}
