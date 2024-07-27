package com.hkust.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.CabinetDoorAO;
import com.hkust.entity.Cabinet;
import com.hkust.entity.CabinetDoor;
import com.hkust.enums.StatEnum;
import com.hkust.mapper.CabinetDoorMapper;
import com.hkust.mapper.CabinetMapper;
import com.hkust.struct.structmapper.CabinetDoorStructMapper;
import com.hkust.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Slf4j
public class CabinetDoorService {

    private CabinetDoorMapper cabinetDoorMapper;

    private CabinetMapper cabinetMapper;

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

    public ApiResponse addCabinetDoor(@RequestBody CabinetDoorAO cabinetDoorAO) {
        Cabinet cabinet = cabinetMapper.selectById(cabinetDoorAO.getCabinetId());
        if (ObjectUtil.isEmpty(cabinet)) {
            return ApiResponse.failed(ReturnCode.CABINET_IS_NULL);
        }
        CabinetDoor cabinetDoor = CabinetDoorStructMapper.INSTANCE.cabinetDoorAOToCabinet(cabinetDoorAO);
        cabinetDoor.setDoorId(UUIDUtils.generateUUIDWithoutHyphens());
        cabinetDoor.setStat(StatEnum.NORMAL.getCode());
        cabinetDoor.setInQty(0);
        cabinetDoor.setOutQty(0);
        cabinetDoor.setReagentsQty(0);
        try {
            cabinetDoorMapper.insert(cabinetDoor);
        } catch (Exception e) {
            log.error("insert cabinet door failed!");
            return ApiResponse.failed(ReturnCode.DB_INSERT_ERROR);
        }
        return ApiResponse.success();
    }

    @Autowired
    public void setCabinetDoorMapper(CabinetDoorMapper cabinetDoorMapper) {
        this.cabinetDoorMapper = cabinetDoorMapper;
    }

    @Autowired
    public void setCabinetMapper(CabinetMapper cabinetMapper) {
        this.cabinetMapper = cabinetMapper;
    }
}
