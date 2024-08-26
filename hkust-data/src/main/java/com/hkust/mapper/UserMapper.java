package com.hkust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkust.entity.Role;
import com.hkust.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> selectAll();

    User selectByUserName(String userName);

    User selectByStudentId(String studentId);

    User selectByStudentIdAndPasswd(@Param("studentId") String studentId, @Param("password") String password);

//    ArrayList<String> selectRolesByStudentId(String studentId);

    //    @Select("SELECT r.* FROM hkust_user_role ur INNER JOIN hkust_role r ON ur.role_id = r.role_id WHERE ur.student_id = #{studentId}")
    @Select("select * from hkust_user u \n" +
            "inner join hkust_user_role ur on u.student_id=ur.student_id \n" +
            "inner join hkust_role r on ur.role_id = r.role_id \n" +
            "inner join hkust_role_permissions rp on r.role_id=rp.role_id\n" +
            "inner join hkust_permissions p on rp.permission_id = p.permission_id\n" +
            "where u.student_id = #{studentId}")
    User selectUserDetailByStudentId(String studentId);
}
