package com.hkust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkust.entity.Cabinet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CabinetMapper extends BaseMapper<Cabinet> {

    List<Cabinet> selectAll();
}
