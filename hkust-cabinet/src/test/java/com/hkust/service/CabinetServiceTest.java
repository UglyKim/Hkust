package com.hkust.service;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hkust.HkustBaseTest;
import com.hkust.dto.vo.CabinetVO;
import com.hkust.entity.Cabinet;
import com.hkust.mapper.CabinetMapper;
import com.hkust.struct.structmapper.CabinetStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
class CabinetServiceTest extends HkustBaseTest {

    @Autowired
    private CabinetService cabinetService;

    @Autowired
    private CabinetMapper cabinetMapper;
    @Test
    void getAllCabinets() {
        CabinetVO cabinetVO = cabinetService.getAllCabinets();
        Assert.notNull(cabinetVO, "Cabinets list should not be null");
    }

    @Test
    void testMapper(){
        List<Cabinet> cabinets = cabinetMapper.selectAll();
        Cabinet cabinet = cabinets.get(0);
        log.info(cabinet.getName());
        log.info(cabinet.getCabinetAddr());
        CabinetVO cabinetVO = CabinetStructMapper.INSTANCE.toVO(cabinet);
        log.info(cabinetVO.getName());
        log.info(cabinetVO.getCabinetAddr());
    }
}