package com.hkust.struct.structmapper;

import com.hkust.dto.ao.UserInfoAO;
import com.hkust.dto.vo.UserVO;
import com.hkust.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserStructMapper {

    UserStructMapper INSTANCE = Mappers.getMapper(UserStructMapper.class);

    //    @Mapping(target = "", source = "accountNonExpired")
    UserVO toVO(User user);

//    UserVO toVO(org.springframework.security.core.userdetails.User user);

    User UserAOToUser(UserInfoAO userInfoAO);
}
