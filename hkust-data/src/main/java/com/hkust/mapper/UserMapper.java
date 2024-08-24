package com.hkust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    @Select("SELECT r.role_name FROM hkust_user_role ur INNER JOIN hkust_role r ON ur.role_id = r.role_id WHERE ur.student_id = #{studentId}")
    List<String> selectRolesByStudentId(String studentId);
}
