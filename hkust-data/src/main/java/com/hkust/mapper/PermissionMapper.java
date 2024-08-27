package com.hkust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkust.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
