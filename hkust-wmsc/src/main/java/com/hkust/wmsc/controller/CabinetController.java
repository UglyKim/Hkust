package com.hkust.wmsc.controller;

import com.hkust.dto.ApiResponse;
import com.hkust.wmsc.service.CabinetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "智能柜")
@RestController
@RequestMapping("/v1/cabinet")
@Slf4j
public class CabinetController {

    private CabinetService cabinetService;

    @Operation(summary = "智能柜列表")
    @PostMapping("/list")
    public ApiResponse getCabinetList() {
        return cabinetService.getCabinetList();
    }

    @Operation(summary = "智能柜详情")
    @PostMapping("/info")
    public ApiResponse getCabinetInfo(@RequestParam String cabinetId) {
        log.info("received cabinet ID:{}", cabinetId);
        return cabinetService.getCabinetDetail(cabinetId);
    }

    @Autowired
    public void setCabinetService(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }
}
