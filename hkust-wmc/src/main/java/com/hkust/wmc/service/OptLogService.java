package com.hkust.wmc.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.wms.WmsOptLog;
import com.hkust.enums.OptTypeEnum;
import com.hkust.mapper.wmsc.WmsOptLogMapper;
import com.hkust.wmc.dto.PageResponse;
import com.hkust.wmc.dto.ao.OptLogQueryAO;
import com.hkust.wmc.dto.vo.OptLogVO;
import com.hkust.wmc.struct.structmapper.WmsOptLogStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OptLogService {

    private WmsOptLogMapper wmsOptLogMapper;

    public ApiResponse<PageResponse> getOptLogList(OptLogQueryAO optLogQueryAO) {
        Page<WmsOptLog> page = new Page<>(optLogQueryAO.getPageNum(), optLogQueryAO.getPageSize());

        QueryWrapper<WmsOptLog> wrapper = new QueryWrapper<>();
        wrapper.in("type", Arrays.asList(OptTypeEnum.LOGIN.getCode(), OptTypeEnum.LOGOUT.getCode()));
        if (ObjectUtil.isNotEmpty(optLogQueryAO.getOperator())) {
            wrapper.like("operator", optLogQueryAO.getOperator());
        }
        if (ObjectUtil.isNotEmpty(optLogQueryAO.getStartDate()) && ObjectUtil.isNotEmpty(optLogQueryAO.getEndDate())) {
            wrapper.ge("opt_time", optLogQueryAO.getStartDate());
            wrapper.le("opt_time", optLogQueryAO.getEndDate());
        }
        wrapper.orderByAsc("opt_time");
        Page<WmsOptLog> wmsOptLogPage = wmsOptLogMapper.selectPage(page, wrapper);
        List<WmsOptLog> wmsOptLogList = wmsOptLogPage.getRecords();
        if (CollUtil.isEmpty(wmsOptLogList)) {
            return ApiResponse.success();
        }
        List<OptLogVO> optLogVOList = new ArrayList<>();
        for (WmsOptLog wmsOptLog : wmsOptLogList) {
            OptLogVO optLogVO = WmsOptLogStructMapper.INSTANCE.wmsOptLogToOptLogVO(wmsOptLog);
            optLogVOList.add(optLogVO);
        }
        PageResponse pageResponse = new PageResponse(optLogQueryAO.getPageNum(), optLogQueryAO.getPageSize(), wmsOptLogPage.getTotal(), optLogVOList);
        return ApiResponse.success(pageResponse);
    }

    public ApiResponse<PageResponse> getReagentsOptLogList(OptLogQueryAO optLogQueryAO) {
        Page<WmsOptLog> page = new Page<>(optLogQueryAO.getPageNum(), optLogQueryAO.getPageSize());

        QueryWrapper<WmsOptLog> wrapper = new QueryWrapper<>();
        wrapper.notIn("type", Arrays.asList(OptTypeEnum.LOGIN.getCode(), OptTypeEnum.LOGOUT.getCode()));
        if (ObjectUtil.isNotEmpty(optLogQueryAO.getOperator())) {
            wrapper.like("operator", optLogQueryAO.getOperator());
        }
        if (ObjectUtil.isNotEmpty(optLogQueryAO.getStartDate()) && ObjectUtil.isNotEmpty(optLogQueryAO.getEndDate())) {
            wrapper.ge("opt_time", optLogQueryAO.getStartDate());
            wrapper.le("opt_time", optLogQueryAO.getEndDate());
        }
        wrapper.orderByAsc("opt_time");
        Page<WmsOptLog> wmsOptLogPage = wmsOptLogMapper.selectPage(page, wrapper);
        List<WmsOptLog> wmsOptLogList = wmsOptLogPage.getRecords();
        if (CollUtil.isEmpty(wmsOptLogList)) {
            return ApiResponse.success();
        }
        List<OptLogVO> optLogVOList = new ArrayList<>();
        for (WmsOptLog wmsOptLog : wmsOptLogList) {
            OptLogVO optLogVO = WmsOptLogStructMapper.INSTANCE.wmsOptLogToOptLogVO(wmsOptLog);
            optLogVOList.add(optLogVO);
        }
        PageResponse pageResponse = new PageResponse(optLogQueryAO.getPageNum(), optLogQueryAO.getPageSize(), wmsOptLogPage.getTotal(), optLogVOList);
        return ApiResponse.success(pageResponse);
    }

    @Autowired
    public void setWmsOptLogMapper(WmsOptLogMapper wmsOptLogMapper) {
        this.wmsOptLogMapper = wmsOptLogMapper;
    }
}
