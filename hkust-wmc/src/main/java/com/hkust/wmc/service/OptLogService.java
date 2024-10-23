package com.hkust.wmc.service;

import com.hkust.dto.ApiResponse;
import com.hkust.mapper.wmsc.WmsOptLogMapper;
import com.hkust.wmc.dto.PageResponse;
import com.hkust.wmc.dto.ao.OptLogQueryAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptLogService {

    private WmsOptLogMapper wmsOptLogMapper;

    public ApiResponse<PageResponse> getOptLogList(OptLogQueryAO optLogQueryAO){

        return null;
    }

    @Autowired
    public void setWmsOptLogMapper(WmsOptLogMapper wmsOptLogMapper) {
        this.wmsOptLogMapper = wmsOptLogMapper;
    }
}
