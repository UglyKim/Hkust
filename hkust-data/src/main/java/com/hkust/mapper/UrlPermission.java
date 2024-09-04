package com.hkust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkust.entity.PermDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UrlPermission extends BaseMapper<PermDetail> {

}
