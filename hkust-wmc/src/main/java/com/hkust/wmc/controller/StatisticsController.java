package com.hkust.wmc.controller;

import com.hkust.dto.ApiResponse;
import com.hkust.wmc.dto.vo.StatisticsInOutBoundVO;
import com.hkust.wmc.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "首页")
@RestController
@RequestMapping("/wmc/v1/statistics")
@Slf4j
public class StatisticsController {

    private StatisticsService statisticsService;

    @Operation(summary = "月度出入库统计")
    @PostMapping("/inoutbound")
    public ApiResponse<List<StatisticsInOutBoundVO>> monthInOutBoundStat() {
        return statisticsService.monthInoutBoundStatistics();
    }

    @Autowired
    public void setStatisticsService(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }
}
