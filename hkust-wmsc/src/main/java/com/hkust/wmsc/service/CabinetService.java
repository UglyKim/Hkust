package com.hkust.wmsc.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.wms.WmsCabinet;
import com.hkust.mapper.wmsc.WmsCabinetMapper;
import com.hkust.wmsc.dto.vo.CabinetVO;
import com.hkust.wmsc.struct.structmapper.WmscCabinetStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CabinetService {

    private WmsCabinetMapper wmsCabinetMapper;

    public ApiResponse getCabinetList() {

        QueryWrapper<WmsCabinet> wrapper = new QueryWrapper();
        List<WmsCabinet> cabinetList = wmsCabinetMapper.selectList(wrapper);
        if (CollUtil.isEmpty(cabinetList)) {
            log.warn(ReturnCode.CABINET_IS_NULL.getMessage());
        }
        List<CabinetVO> cabinetVOList = new ArrayList<>();
        for (WmsCabinet wmsCabinet : cabinetList) {
            CabinetVO cabinetVO = WmscCabinetStructMapper.INSTANCE.wmsCabinetToCabinetVO(wmsCabinet);
            cabinetVOList.add(cabinetVO);
        }
        return ApiResponse.success(cabinetVOList);
    }

    public ApiResponse getCabinetDetail(String cabinetId) {
        WmsCabinet wmsCabinet = wmsCabinetMapper.selectById(cabinetId);
        if (ObjectUtil.isEmpty(wmsCabinet)) {
            return ApiResponse.success(ReturnCode.CABINET_IS_NULL);
        }
        CabinetVO cabinetVO = WmscCabinetStructMapper.INSTANCE.wmsCabinetToCabinetVO(wmsCabinet);
        return ApiResponse.success(cabinetVO);
    }

    @Autowired
    public void setWmsCabinetMapper(WmsCabinetMapper wmsCabinetMapper) {
        this.wmsCabinetMapper = wmsCabinetMapper;
    }
}
