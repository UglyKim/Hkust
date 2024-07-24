package com.hkust.service;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkust.dto.vo.CabinetVO;
import com.hkust.entity.Cabinet;
import com.hkust.mapper.CabinetMapper;
import com.hkust.structmapper.CabinetStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CabinetService extends ServiceImpl<CabinetMapper, Cabinet> {

    @Autowired
    private CabinetMapper cabinetMapper;

    public CabinetVO getAllCabinets() {
        List<Cabinet> cabinets = cabinetMapper.selectAll();
        CabinetVO cabinetVO = CabinetStructMapper.INSTANCE.toVO(cabinets.get(0));
        return cabinetVO;
    }
}
