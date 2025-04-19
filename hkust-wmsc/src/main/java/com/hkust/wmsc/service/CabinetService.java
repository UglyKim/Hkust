package com.hkust.wmsc.service;

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
import com.hkust.utils.DateUtils;
import com.hkust.wmsc.dto.ao.EditCabinetAO;
import com.hkust.wmsc.dto.vo.CabinetVO;
import com.hkust.wmsc.struct.structmapper.WmscCabinetStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CabinetService {

    private WmsCabinetMapper wmsCabinetMapper;

    public ApiResponse<List<CabinetVO>> getCabinetList() {

        QueryWrapper<WmsCabinet> wrapper = new QueryWrapper<>();
        List<WmsCabinet> cabinetList = wmsCabinetMapper.selectList(wrapper);
        if (CollUtil.isEmpty(cabinetList)) {
            log.warn(ReturnCode.CABINET_IS_NULL.getMessage());
        }
        List<CabinetVO> cabinetVOList = new ArrayList<>();
        for (WmsCabinet wmsCabinet : cabinetList) {
            CabinetVO cabinetVO = WmscCabinetStructMapper.INSTANCE.wmsCabinetToCabinetVO(wmsCabinet);
            cabinetVO.setState(CabinetStateEnum.fromCode(wmsCabinet.getState()));
            cabinetVOList.add(cabinetVO);
        }
        return ApiResponse.success(cabinetVOList);
    }

    public ApiResponse<CabinetVO> getCabinetDetail(String cabinetId) {
        WmsCabinet wmsCabinet = wmsCabinetMapper.selectById(cabinetId);
        if (ObjectUtil.isEmpty(wmsCabinet)) {
            return ApiResponse.success(ReturnCode.CABINET_IS_NULL.getMessage());
        }
        CabinetVO cabinetVO = WmscCabinetStructMapper.INSTANCE.wmsCabinetToCabinetVO(wmsCabinet);
        cabinetVO.setState(CabinetStateEnum.fromCode(wmsCabinet.getState()));
        return ApiResponse.success(cabinetVO);
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
        wrapper.set("modified_time", DateUtils.getCurrentDate());
        User user = SecurityUtils.getCurrentUser();
        wrapper.set("modified_id", user.getStudentId());
        wrapper.set("modified_by", Optional.ofNullable(user).map(User::getUsername).orElse(null));
        wrapper.set("modified_time", DateUtils.getCurrentDateTime());
        try {
            wrapper.update();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("update cabinet_info failed!");
            return ApiResponse.failed(ReturnCode.DB_UPDATE_ERROR);
        }
        return ApiResponse.success();
    }

    @Autowired
    public void setWmsCabinetMapper(WmsCabinetMapper wmsCabinetMapper) {
        this.wmsCabinetMapper = wmsCabinetMapper;
    }
}
