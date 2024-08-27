package com.hkust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkust.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectAll();

//    @Select("select * from hkust_role r inner join hkust_role_permissions rp on r.role_id = rp.role_id\n" +
//            "inner join hkust_permissions p on rp.permission_id = p.permission_id\n" +
//            "inner join hkust_url_permissions up on rp.permission_id = up.permission_id")
//    List<Role> selectRoles();
}
