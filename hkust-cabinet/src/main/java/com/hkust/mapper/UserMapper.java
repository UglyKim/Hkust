package com.hkust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkust.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> selectAll();

    User selectByUserName(String userName);

    User selectByStudentId(String studentId);

    User selectByStudentIdAndPasswd(@Param("studentId") String studentId, @Param("password") String password);
}
