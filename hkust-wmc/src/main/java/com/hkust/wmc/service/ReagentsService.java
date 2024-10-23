package com.hkust.wmc.service;

import com.hkust.dto.ApiResponse;
import com.hkust.entity.wms.WmsReagents;
import com.hkust.mapper.wmsc.WmsReagentsMapper;
import com.hkust.wmc.dto.PageResponse;
import com.hkust.wmc.dto.ao.ReagentsAO;
import com.hkust.wmc.dto.ao.ReagentsQueryAO;
import com.hkust.wmc.struct.structmapper.WmcReagentsStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReagentsService {

    private WmsReagentsMapper wmsReagentsMapper;

    public ApiResponse addReagents(ReagentsAO reagentsAO) {
        WmsReagents reagents = WmcReagentsStructMapper.INSTANCE.reagentsAOToReagents(reagentsAO);
        wmsReagentsMapper.insert(reagents);
        return ApiResponse.success();
    }

    public ApiResponse<PageResponse> getReagentsList(ReagentsQueryAO reagentsQueryAO) {

        return null;
    }

    @Autowired
    public void setWmsReagentsMapper(WmsReagentsMapper wmsReagentsMapper) {
        this.wmsReagentsMapper = wmsReagentsMapper;
    }
}
