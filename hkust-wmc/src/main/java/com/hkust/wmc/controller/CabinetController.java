package com.hkust.wmc.controller;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.enums.CabinetStateEnum;
import com.hkust.utils.EnumToJsonUtils;
import com.hkust.wmc.dto.ao.CabinetAO;
import com.hkust.wmc.service.CabinetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "仓储智能柜管理")
@RestController
@RequestMapping("/wmc/v1/cabinet")
@Slf4j
public class CabinetController {

    private CabinetService cabinetService;

    @Operation(summary = "仓储智能柜使用状态")
    @PostMapping("/stat")
    public ApiResponse getCabinetType() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(CabinetStateEnum.class));
    }

    @Operation(summary = "添加仓储智能柜")
    @PostMapping("/add")
    public ApiResponse addCabinet(@RequestBody CabinetAO cabinetAO) {
        log.info("received cabinet info:{}", JSONUtil.toJsonPrettyStr(cabinetAO));
        cabinetService.addCabinet(cabinetAO);
        return ApiResponse.success();
    }

    @Operation(summary = "仓储智能柜列表")
    @PostMapping("/list")
    public ApiResponse getCabinetList() {
        return cabinetService.getCabinetList();
    }

    @Autowired
    public void setCabinetService(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }
}
