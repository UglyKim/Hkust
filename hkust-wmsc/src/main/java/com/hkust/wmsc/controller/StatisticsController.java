package com.hkust.wmsc.controller;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.wmsc.dto.ao.InOutboundAO;
import com.hkust.wmsc.dto.ao.ReagentsQueryAO;
import com.hkust.wmsc.dto.vo.MainPageStaticsVO;
import com.hkust.wmsc.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "统计")
@RestController
@RequestMapping("/wmsc/v1/statistics")
@Slf4j
public class StatisticsController {

    private StatisticsService statisticsService;

    @Operation(summary = "首页-试剂统计")
    @PostMapping("/reagents")
    public ApiResponse reagentsStatistics(@RequestParam String cabinetId) {
        return statisticsService.totalStats(cabinetId);
    }

    @Operation(summary = "入库统计")
    @PostMapping("/inbound")
    public ApiResponse inboundStatistics() {
        return statisticsService.inboundRecordStats();
    }

    @Operation(summary = "出库统计")
    @PostMapping("/outbound")
    public ApiResponse outboundStatistics() {
        return statisticsService.outboundRecordStats();
    }

    @Operation(summary = "入库列表查询")
    @PostMapping("/inbound/list")
    public ApiResponse inboundListStatistics(@RequestBody ReagentsQueryAO reagentsQueryAO) {
        log.info("received query params:{}", JSONUtil.toJsonPrettyStr(reagentsQueryAO));
        return statisticsService.inOutBoundRecordList(reagentsQueryAO);
    }

    @Operation(summary = "出库列表查询")
    @PostMapping("/outbound/list")
    public ApiResponse outboundListStatistics(@RequestBody ReagentsQueryAO reagentsQueryAO) {
        log.info("received query params:{}", JSONUtil.toJsonPrettyStr(reagentsQueryAO));
        return statisticsService.inOutBoundRecordList(reagentsQueryAO);
    }

    @Autowired
    public void setStatisticsService(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }
}
