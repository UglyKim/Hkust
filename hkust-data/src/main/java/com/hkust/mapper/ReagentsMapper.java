package com.hkust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkust.entity.Reagents;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReagentsMapper extends BaseMapper<Reagents> {


    Reagents selectByCabinetIdAndDoorId(String cabinetId, String doorId);

    Reagents selectByBarCode(String barCode);

    int selectCntByCabinet(String cabinetId);

    int selectCntByInCabinet(String cabinetId);

    int selectCntByOutCabinet(String cabinetId);

    int selectCntByCabinetDoor(String doorId);

    int selectCntByInCabinetDoor(String doorId);

    int selectCntByOutCabinetDoor(String doorId);
}
