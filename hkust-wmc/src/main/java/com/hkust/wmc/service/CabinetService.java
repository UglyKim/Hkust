package com.hkust.wmc.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.User;
import com.hkust.entity.wms.WmsCabinet;
import com.hkust.enums.CabinetStateEnum;
import com.hkust.mapper.wmsc.WmsCabinetMapper;
import com.hkust.security.SecurityUtils;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import com.hkust.wmc.dto.ao.CabinetAO;
import com.hkust.wmc.dto.vo.CabinetVO;
import com.hkust.wmc.struct.structmapper.WmsCabinetStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CabinetService {

    private WmsCabinetMapper wmsCabinetMapper;

    public ApiResponse addCabinet(CabinetAO cabinetAO) {
        WmsCabinet wmsCabinet = WmsCabinetStructMapper.INSTANCE.cabinetAOToCabinet(cabinetAO);
        wmsCabinet.setId(UUIDUtils.generateUUIDWithoutHyphens());
        User user = SecurityUtils.getCurrentUser();
        wmsCabinet.setCreator(user.getUsername());
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

    public ApiResponse getCabinetList() {
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
