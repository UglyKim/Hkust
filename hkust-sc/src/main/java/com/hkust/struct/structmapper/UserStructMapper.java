package com.hkust.struct.structmapper;

import com.hkust.dto.ao.UserInfoAO;
import com.hkust.dto.vo.UserVO;
import com.hkust.entity.User;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserStructMapper {

    UserStructMapper INSTANCE = Mappers.getMapper(UserStructMapper.class);

    //    @Mapping(target = "", source = "accountNonExpired")
    UserVO UserToUserVO(User user);

//    UserVO toVO(User user);

    @Mapping(target = "add_ch", source = "addCh")
    User UserAOToUser(UserInfoAO userInfoAO);

}
