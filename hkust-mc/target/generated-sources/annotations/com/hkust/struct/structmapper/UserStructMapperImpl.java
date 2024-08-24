package com.hkust.struct.structmapper;

import com.hkust.dto.ao.UserInfoAO;
import com.hkust.dto.vo.UserVO;
import com.hkust.entity.User;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-25T00:14:21+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_391 (Oracle Corporation)"
)
@Component
public class UserStructMapperImpl implements UserStructMapper {

    @Override
    public UserVO UserToUserVO(User user) {
        if ( user == null ) {
            return null;
        }

        String arg0 = null;
        String arg1 = null;
        String arg2 = null;
        String arg3 = null;
        String arg4 = null;
        String arg5 = null;
        String arg6 = null;
        String arg7 = null;
        String arg8 = null;
        String arg9 = null;
        LocalDateTime arg10 = null;

        UserVO userVO = new UserVO( arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10 );

        userVO.setStudentId( user.getStudentId() );
        userVO.setRealName( user.getRealName() );
        userVO.setAddress( user.getAddress() );
        userVO.setOfficeLocation( user.getOfficeLocation() );
        userVO.setPhone( user.getPhone() );
        userVO.setFixedTel( user.getFixedTel() );
        userVO.setEmail( user.getEmail() );
        userVO.setGender( user.getGender() );
        if ( user.getEnabled() != null ) {
            userVO.setEnabled( String.valueOf( user.getEnabled() ) );
        }
        userVO.setCreateTime( user.getCreateTime() );

        return userVO;
    }

    @Override
    public User UserAOToUser(UserInfoAO userInfoAO) {
        if ( userInfoAO == null ) {
            return null;
        }

        User user = new User();

        user.setAdd_ch( userInfoAO.getAddCh() );
        user.setStudentId( userInfoAO.getStudentId() );
        user.setUsername( userInfoAO.getUsername() );
        user.setRealName( userInfoAO.getRealName() );
        user.setAddress( userInfoAO.getAddress() );
        user.setOfficeLocation( userInfoAO.getOfficeLocation() );
        user.setPhone( userInfoAO.getPhone() );
        user.setFixedTel( userInfoAO.getFixedTel() );
        user.setPassword( userInfoAO.getPassword() );
        user.setEmail( userInfoAO.getEmail() );
        if ( userInfoAO.getGender() != null ) {
            user.setGender( userInfoAO.getGender().name() );
        }

        return user;
    }
}
