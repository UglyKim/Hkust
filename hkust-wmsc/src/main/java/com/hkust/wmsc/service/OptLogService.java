package com.hkust.wmsc.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.wms.WmsOptLog;
import com.hkust.mapper.wmsc.WmsOptLogMapper;
import com.hkust.wmsc.dto.vo.CabinetVO;
import com.hkust.wmsc.dto.vo.WmsOptLogVO;
import com.hkust.wmsc.struct.structmapper.WmsCabinetStructMapper;
import com.hkust.wmsc.struct.structmapper.WmsOptLogStructMapper;
import com.nimbusds.jose.util.JSONObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ApiResponse optLostList() {
        QueryWrapper<WmsOptLog> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("opt_time");
        List<WmsOptLog> wmsOptLogList = wmsOptLogMapper.selectList(wrapper);

        List<WmsOptLogVO> wmsOptLogVOList = new ArrayList<>();
        if (CollUtil.isNotEmpty(wmsOptLogList)) {
            for (WmsOptLog wmsOptLog : wmsOptLogList) {
                WmsOptLogVO wmsOptLogVO = WmsOptLogStructMapper.INSTANCE.WmsOptLogToWmsOptLogVO(wmsOptLog);
                wmsOptLogVOList.add(wmsOptLogVO);
            }
        }
        log.info("operation log list:{}", JSONUtil.toJsonPrettyStr(wmsOptLogVOList));
        return ApiResponse.success(wmsOptLogList);
    }

    @Autowired
    public void setWmsOptLogMapper(WmsOptLogMapper wmsOptLogMapper) {
        this.wmsOptLogMapper = wmsOptLogMapper;
    }
}
