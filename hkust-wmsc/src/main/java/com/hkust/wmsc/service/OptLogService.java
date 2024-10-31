package com.hkust.wmsc.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.wms.WmsOptLog;
import com.hkust.enums.OptTypeEnum;
import com.hkust.mapper.wmsc.WmsOptLogMapper;
import com.hkust.wmsc.dto.PageResponse;
import com.hkust.wmsc.dto.ao.OptLogQueryAO;
import com.hkust.wmsc.dto.vo.WmsOptLogVO;
import com.hkust.wmsc.struct.structmapper.WmsOptLogStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class OptLogService {

    private WmsOptLogMapper wmsOptLogMapper;

    public ApiResponse optLogDetail(String optId) {

        WmsOptLog wmsOptLog = wmsOptLogMapper.selectById(optId);
        WmsOptLogVO wmsOptLogVO = WmsOptLogStructMapper.INSTANCE.WmsOptLogToWmsOptLogVO(wmsOptLog);
        return ApiResponse.success(wmsOptLogVO);
    }

    public ApiResponse<PageResponse> getReagentsOptLogList(OptLogQueryAO optLogQueryAO) {
        Page<WmsOptLog> page = new Page<>(optLogQueryAO.getPageNum(), optLogQueryAO.getPageSize());

        QueryWrapper<WmsOptLog> wrapper = new QueryWrapper<>();
        wrapper.notIn("type", Arrays.asList(OptTypeEnum.LOGIN.getCode(), OptTypeEnum.LOGOUT.getCode()));
        if (ObjectUtil.isNotEmpty(optLogQueryAO.getOperator())) {
            wrapper.like("operator", optLogQueryAO.getOperator());
        }
        if (ObjectUtil.isNotEmpty(optLogQueryAO.getStartDate()) && ObjectUtil.isNotEmpty(optLogQueryAO.getEndDate())) {
            wrapper.apply("DATE(opt_time) >= {0}", optLogQueryAO.getStartDate());
            wrapper.apply("DATE(opt_time) >= {0}", optLogQueryAO.getEndDate());
        }
        wrapper.orderByAsc("opt_time");
        Page<WmsOptLog> wmsOptLogPage = wmsOptLogMapper.selectPage(page, wrapper);
        List<WmsOptLog> wmsOptLogList = wmsOptLogPage.getRecords();
        if (CollUtil.isEmpty(wmsOptLogList)) {
            return ApiResponse.success();
        }
        List<WmsOptLogVO> optLogVOList = new ArrayList<>();
        for (WmsOptLog wmsOptLog : wmsOptLogList) {
            WmsOptLogVO wmsOptLogVO = WmsOptLogStructMapper.INSTANCE.WmsOptLogToWmsOptLogVO(wmsOptLog);
            optLogVOList.add(wmsOptLogVO);
        }
        PageResponse pageResponse = new PageResponse(optLogQueryAO.getPageNum(), optLogQueryAO.getPageSize(), wmsOptLogPage.getTotal(), optLogVOList);
        return ApiResponse.success(pageResponse);
    }

    @Autowired
    public void setWmsOptLogMapper(WmsOptLogMapper wmsOptLogMapper) {
        this.wmsOptLogMapper = wmsOptLogMapper;
    }
}
