package com.hkust.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.CabinetDoorAO;
import com.hkust.entity.CabinetDoor;
import com.hkust.mapper.CabinetDoorMapper;
import com.hkust.struct.structmapper.CabinetDoorStructMapper;
import com.hkust.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CabinetDoorService {

    private CabinetDoorMapper cabinetDoorMapper;

    public ApiResponse getDoorList(String cabinetId) {
        QueryWrapper<CabinetDoor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cabinet_id", cabinetId);
        List<CabinetDoor> cabinetDoorList = cabinetDoorMapper.selectList(queryWrapper);
        return ApiResponse.success(cabinetDoorList);
    }

    public ApiResponse getDoorDetail(String doorId) {
        CabinetDoor cabinetDoor = cabinetDoorMapper.selectById(doorId);
        return ApiResponse.success(cabinetDoor);
    }

    public ApiResponse addCabinetDoor(CabinetDoorAO cabinetDoorAO) {
        CabinetDoor cabinetDoor = CabinetDoorStructMapper.INSTANCE.cabinetDoorAOToCabinet(cabinetDoorAO);
        cabinetDoor.setDoorId(UUIDUtils.generateUUIDWithoutHyphens());
        cabinetDoorMapper.insert(cabinetDoor);
        return ApiResponse.success();
    }

    @Autowired
    public void setCabinetDoorMapper(CabinetDoorMapper cabinetDoorMapper) {
        this.cabinetDoorMapper = cabinetDoorMapper;
    }
}
