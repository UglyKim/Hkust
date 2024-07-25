package com.hkust.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.CabinetAO;
import com.hkust.dto.vo.CabinetVO;
import com.hkust.entity.Cabinet;
import com.hkust.mapper.CabinetMapper;
import com.hkust.struct.structmapper.CabinetStructMapper;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CabinetService extends ServiceImpl<CabinetMapper, Cabinet> {

    private CabinetMapper cabinetMapper;

    public ApiResponse getAllCabinets() {
        List<Cabinet> cabinets = cabinetMapper.selectAll();
        CabinetVO cabinetVO = CabinetStructMapper.INSTANCE.toVO(cabinets.get(0));
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

    @Autowired
    public void setCabinetMapper(CabinetMapper cabinetMapper) {
        this.cabinetMapper = cabinetMapper;
    }
}
