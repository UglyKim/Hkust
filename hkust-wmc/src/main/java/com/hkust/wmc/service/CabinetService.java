package com.hkust.wmc.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.User;
import com.hkust.entity.wms.WmsCabinet;
import com.hkust.enums.CabinetStateEnum;
import com.hkust.mapper.wmsc.WmsCabinetMapper;
import com.hkust.security.SecurityUtils;
import com.hkust.utils.UUIDUtils;
import com.hkust.wmc.dto.ao.CabinetAO;
import com.hkust.wmc.dto.ao.EditCabinetAO;
import com.hkust.wmc.dto.vo.CabinetVO;
import com.hkust.wmc.struct.structmapper.WmsCabinetStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CabinetService {

    private WmsCabinetMapper wmsCabinetMapper;

    public ApiResponse<Void> addCabinet(CabinetAO cabinetAO) {
        WmsCabinet wmsCabinet = WmsCabinetStructMapper.INSTANCE.cabinetAOToCabinet(cabinetAO);
        wmsCabinet.setId(UUIDUtils.generateUUIDWithoutHyphens());
        User user = SecurityUtils.getCurrentUser();
        wmsCabinet.setCreator(Optional.ofNullable(user).map(User::getStudentId).orElse(null));
        wmsCabinet.setState(CabinetStateEnum.ACTIVE.getCode());
        // 日期
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String strDateTime = now.format(formatter);
        LocalDateTime nowDateTime = LocalDateTime.parse(strDateTime, formatter);

        wmsCabinet.setCreateTime(nowDateTime);
        wmsCabinetMapper.insert(wmsCabinet);
        return ApiResponse.success();
    }

    public ApiResponse<Void> edieCabinet(EditCabinetAO editCabinetAO) {
        if (ObjectUtil.isEmpty(editCabinetAO.getCabinetId())) {
            return ApiResponse.failed(ReturnCode.CABINET_ID_NOT_NULL);
        }
        WmsCabinet wmsCabinet = wmsCabinetMapper.selectById(editCabinetAO.getCabinetId());
        if (ObjectUtil.isEmpty(wmsCabinet)) {
            return ApiResponse.failed(ReturnCode.CABINET_IS_NULL);
        }
        UpdateChainWrapper<WmsCabinet> wrapper = new UpdateChainWrapper<>(wmsCabinetMapper);
        wrapper.eq("id", editCabinetAO.getCabinetId());
        if (ObjectUtil.isNotEmpty(editCabinetAO.getBarcode())) {
            wrapper.set("barcode", editCabinetAO.getBarcode());
        }
        if (ObjectUtil.isNotEmpty(editCabinetAO.getBrand())) {
            wrapper.set("brand", editCabinetAO.getBrand());
        }
        if (ObjectUtil.isNotEmpty(editCabinetAO.getCapacity())) {
            wrapper.set("capacity", editCabinetAO.getCapacity());
        }
        if (ObjectUtil.isNotEmpty(editCabinetAO.getName())) {
            wrapper.set("name", editCabinetAO.getName());
        }
        if (ObjectUtil.isNotEmpty(editCabinetAO.getLayerCount())) {
            wrapper.set("layer_count", editCabinetAO.getLayerCount());
        }
        if (ObjectUtil.isNotEmpty(editCabinetAO.getState())) {
            wrapper.set("state", editCabinetAO.getState());
        }
        if (ObjectUtil.isNotEmpty(editCabinetAO.getStorageRoom())) {
            wrapper.set("storage_room", editCabinetAO.getStorageRoom());
        }
        if (ObjectUtil.isNotEmpty(editCabinetAO.getSpecification())) {
            wrapper.set("specification", editCabinetAO.getSpecification());
        }
        if (ObjectUtil.isNotEmpty(editCabinetAO.getRemark())) {
            wrapper.set("remark", editCabinetAO.getRemark());
        }
        if (ObjectUtil.isNotEmpty(editCabinetAO.getThresholdRatio())) {
            wrapper.set("threshold_ratio", editCabinetAO.getThresholdRatio());
        }
        try {
            wrapper.update();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("update cabinet_info failed!");
            return ApiResponse.failed(ReturnCode.DB_UPDATE_ERROR);
        }
        return ApiResponse.success();
    }

    public ApiResponse<List<CabinetVO>> getCabinetList() {
        QueryWrapper<WmsCabinet> wrapper = new QueryWrapper<>();
        List<WmsCabinet> cabinetList = wmsCabinetMapper.selectList(wrapper);
        if (CollUtil.isEmpty(cabinetList)) {
            return ApiResponse.success();
        }
        List<CabinetVO> cabinetVOList = new ArrayList<>();
        for (WmsCabinet wmsCabinet : cabinetList) {
            CabinetVO cabinetVO = WmsCabinetStructMapper.INSTANCE.cabinetToCabinetVO(wmsCabinet);
            String statValue = CabinetStateEnum.fromCode(wmsCabinet.getState());
            cabinetVO.setState(statValue);
            cabinetVOList.add(cabinetVO);
        }
        return ApiResponse.success(cabinetVOList);
    }

    @Autowired
    public void setWmsCabinetMapper(WmsCabinetMapper wmsCabinetMapper) {
        this.wmsCabinetMapper = wmsCabinetMapper;
    }
}
