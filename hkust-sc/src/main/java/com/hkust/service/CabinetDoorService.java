package com.hkust.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.CabinetDoorAO;
import com.hkust.dto.vo.CabinetDoorVO;
import com.hkust.entity.Cabinet;
import com.hkust.entity.CabinetDoor;
import com.hkust.enums.DoorTypeEnum;
import com.hkust.enums.StatEnum;
import com.hkust.mapper.CabinetDoorMapper;
import com.hkust.mapper.CabinetMapper;
import com.hkust.struct.structmapper.CabinetDoorStructMapper;
import com.hkust.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CabinetDoorService {

    private CabinetDoorMapper cabinetDoorMapper;

    private CabinetMapper cabinetMapper;

    public ApiResponse getDoorList(String cabinetId) {
        Cabinet cabinet = cabinetMapper.selectById(cabinetId);
        if (ObjectUtil.isEmpty(cabinet)) {
            return ApiResponse.failed(ReturnCode.CABINET_IS_NULL);
        }
        QueryWrapper<CabinetDoor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cabinet_id", cabinetId);
        List<CabinetDoor> cabinetDoorList = cabinetDoorMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(cabinetDoorList)) {
            return ApiResponse.failed(ReturnCode.CABINET_DOOR_LIST_IS_NULL);
        }

        List<CabinetDoorVO> cabinetDoorVOList = new ArrayList<>();
        for (CabinetDoor cabinetDoor : cabinetDoorList) {
            cabinetDoor.setType(DoorTypeEnum.fromCode(cabinetDoor.getType()));
            cabinetDoor.setStat(StatEnum.fromCode(cabinetDoor.getStat()));
            CabinetDoorVO cabinetDoorVO = CabinetDoorStructMapper.INSTANCE.cabinetDoorToCabinetDoorVO(cabinetDoor);
            cabinetDoorVOList.add(cabinetDoorVO);
        }
        return ApiResponse.success(cabinetDoorList);
    }

    public ApiResponse getDoorDetail(String doorId) {
        CabinetDoor cabinetDoor = cabinetDoorMapper.selectById(doorId);
        if (ObjectUtil.isEmpty(cabinetDoor)) {
            return ApiResponse.failed(ReturnCode.CABINET_DOOR_IS_NOT_NULL);
        }
        CabinetDoorVO cabinetDoorVO = CabinetDoorStructMapper.INSTANCE.cabinetDoorToCabinetDoorVO(cabinetDoor);
        cabinetDoorVO.setType(DoorTypeEnum.fromCode(cabinetDoor.getType()));
        cabinetDoorVO.setStat(StatEnum.fromCode(cabinetDoor.getStat()));
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

    public ApiResponse updateCabinetDoor(CabinetDoorAO cabinetDoorAO) {
        CabinetDoor cabinetDoor = cabinetDoorMapper.selectById(cabinetDoorAO.getDoorId());
        if (ObjectUtil.isEmpty(cabinetDoor)) {
            return ApiResponse.failed(ReturnCode.CABINET_DOOR_IS_NULL);
        }
        UpdateChainWrapper<CabinetDoor> chainWrapper = new UpdateChainWrapper<>(cabinetDoorMapper);
        chainWrapper.eq("door_id", cabinetDoor.getDoorId());
        if (ObjectUtil.isNotEmpty(cabinetDoorAO.getRoom())) {
            chainWrapper.set("room", cabinetDoorAO.getRoom());
        }
        if (ObjectUtil.isNotEmpty(cabinetDoorAO.getLocation())) {
            chainWrapper.set("location", cabinetDoorAO.getLocation());
        }
        if (ObjectUtil.isNotEmpty(cabinetDoorAO.getInQty())) {
            chainWrapper.set("inQty", cabinetDoorAO.getInQty());
        }
        if (ObjectUtil.isNotEmpty(cabinetDoorAO.getOutQty())) {
            chainWrapper.set("outQty", cabinetDoorAO.getOutQty());
        }
        if (ObjectUtil.isNotEmpty(cabinetDoorAO.getReagentsQty())) {
            chainWrapper.set("reagentsQty", cabinetDoorAO.getReagentsQty());
        }
        if (ObjectUtil.isNotEmpty(cabinetDoorAO.getStat())) {
            chainWrapper.set("stat", cabinetDoorAO.getStat());
        }
        if (ObjectUtil.isNotEmpty(cabinetDoorAO.getType())) {
            chainWrapper.set("type", cabinetDoorAO.getType());
        }
        try {
            chainWrapper.update();
        } catch (Exception e) {
            log.error("update cabinet_door_info failed!");
            return ApiResponse.failed(ReturnCode.DB_UPDATE_ERROR);
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
