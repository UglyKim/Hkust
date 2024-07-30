package com.hkust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkust.entity.CabinetDoor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CabinetDoorMapper extends BaseMapper<CabinetDoor> {

    List<CabinetDoor> selectListByStat(String satat);
}
