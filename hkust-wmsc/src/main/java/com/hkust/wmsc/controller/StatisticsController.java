package com.hkust.wmsc.controller;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.wmsc.dto.ao.InOutboundAO;
import com.hkust.wmsc.dto.vo.MainPageStaticsVO;
import com.hkust.wmsc.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "统计")
@RestController
@RequestMapping("/v1/statistics")
@Slf4j
public class StatisticsController {

    private StatisticsService statisticsService;

    @Operation(summary = "首页-试剂统计")
    @PostMapping("/reagents/")
    public ApiResponse<MainPageStaticsVO> reagentsStatistics() {
        return statisticsService.totalStats();
    }

    @Operation(summary = "入库统计")
    @PostMapping("/inbound")
    public ApiResponse inboundStatistics(@RequestBody InOutboundAO inOutboundAO) {
        log.info("received inOutboundAO:{}", JSONUtil.toJsonPrettyStr(inOutboundAO));
        return statisticsService.inboundRecord(inOutboundAO);
    }

    @Operation(summary = "出库统计")
    @PostMapping("/outbound")
    public ApiResponse outboundStatistics(@RequestBody InOutboundAO inOutboundAO) {
        log.info("received inOutboundAO:{}", JSONUtil.toJsonPrettyStr(inOutboundAO));
        return statisticsService.inboundRecord(inOutboundAO);
    }

    @Operation(summary = "入库列表查询")
    @PostMapping("/inbound/list")
    public ApiResponse inboundListStatistics(){

        return null;
    }

    @Operation(summary = "出库列表查询")
    @PostMapping("/outbound/list")
    public ApiResponse outboundListStatistics(){

        return null;
    }

    @Autowired
    public void setStatisticsService(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }
}
