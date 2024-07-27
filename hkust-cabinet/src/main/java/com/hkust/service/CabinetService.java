package com.hkust.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.CabinetAO;
import com.hkust.dto.vo.CabinetVO;
import com.hkust.entity.Cabinet;
import com.hkust.enums.OpenModeEnum;
import com.hkust.mapper.CabinetMapper;
import com.hkust.struct.structmapper.CabinetStructMapper;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CabinetService extends ServiceImpl<CabinetMapper, Cabinet> {

    private CabinetMapper cabinetMapper;

    public ApiResponse getCabinetList() {
        List<Cabinet> cabinetList = cabinetMapper.selectAll();
        List cabinetVOList = new ArrayList();
        for (Cabinet cabinet : cabinetList) {
            CabinetVO cabinetVO = CabinetStructMapper.INSTANCE.toVO(cabinet);
            cabinetVO.setOpenMode(OpenModeEnum.fromCode(cabinet.getOpenMode()));
            cabinetVOList.add(cabinetVO);
        }
        return ApiResponse.success(cabinetVOList);
    }

    public ApiResponse getCabinetDetails(String cabinetId) {
        Cabinet cabinet = cabinetMapper.selectById(cabinetId);
        if (ObjectUtil.isEmpty(cabinet)) {
            return ApiResponse.failed(ReturnCode.CABINET_IS_NULL);
        }
        log.info("selected cabinet is:{}", JSONUtil.toJsonPrettyStr(cabinet));
        CabinetVO cabinetVO = CabinetStructMapper.INSTANCE.toVO(cabinet);
        cabinetVO.setOpenMode(OpenModeEnum.fromCode(cabinet.getOpenMode()));
        return ApiResponse.success(cabinetVO);
    }

    public ApiResponse addCabinet(CabinetAO cabinetAO) {
        try {
            Cabinet cabinet = CabinetStructMapper.INSTANCE.cabinetAOToCabinet(cabinetAO);
            cabinet.setCabinetId(UUIDUtils.generateUUIDWithoutHyphens());
            cabinet.setStat(true);
            cabinet.setCreateTime(DateUtils.getCurrentDateTime());
            cabinetMapper.insert(cabinet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.success();
    }

    public ApiResponse updateCabinet(CabinetAO cabinetAO) {
        if (StrUtil.isEmpty(cabinetAO.getCabinetId())) {
            return ApiResponse.failed(ReturnCode.CABINET_ID_NOT_NULL);
        }
        Cabinet cabinet = cabinetMapper.selectById(cabinetAO.getCabinetId());
        if (ObjectUtil.isEmpty(cabinet)) {
            return ApiResponse.failed(ReturnCode.CABINET_IS_NULL);
        }
        UpdateChainWrapper<Cabinet> chainWrapper = new UpdateChainWrapper<>(cabinetMapper);
        chainWrapper.eq("cabinet_id", cabinet.getCabinetId());
        if (ObjectUtil.isNotEmpty(cabinetAO.getCabinetAddr())) {
            chainWrapper.set("cabinet_addr", cabinetAO.getCabinetAddr());
        }
        if (ObjectUtil.isNotEmpty(cabinetAO.getLocation())) {
            chainWrapper.set("location", cabinetAO.getLocation());
        }
        if (ObjectUtil.isNotEmpty(cabinetAO.getName())) {
            chainWrapper.set("name", cabinetAO.getName());
        }
        if (ObjectUtil.isNotEmpty(cabinetAO.getOpenMode())) {
            chainWrapper.set("open_mode", cabinetAO.getOpenMode());
        }
        if (ObjectUtil.isNotEmpty(cabinetAO.getRemark())) {
            chainWrapper.set("remark", cabinetAO.getRemark());
        }
        try {
            chainWrapper.update();
        } catch (Exception e) {
            log.error("update cabinet_info failed!");
            return ApiResponse.failed(ReturnCode.DB_UPDATE_ERROR);
        }
        return ApiResponse.success();
    }

    @Autowired
    public void setCabinetMapper(CabinetMapper cabinetMapper) {
        this.cabinetMapper = cabinetMapper;
    }
}
