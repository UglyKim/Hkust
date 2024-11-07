package com.hkust.wmsc.controller;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.enums.CabinetStateEnum;
import com.hkust.enums.OptTypeEnum;
import com.hkust.utils.EnumToJsonUtils;
import com.hkust.wmsc.dto.ao.EditCabinetAO;
import com.hkust.wmsc.service.CabinetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "智能仓储柜")
@RestController
@RequestMapping("/wmsc/v1/cabinet")
@Slf4j
public class CabinetController {

    private CabinetService cabinetService;

    @Operation(summary = "仓储智能柜使用状态")
    @PostMapping("/stat")
    public ApiResponse getInOutBoundType() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(CabinetStateEnum.class));
    }

    @Operation(summary = "编辑智能柜")
    @PostMapping("/edit")
    public ApiResponse alterCabinet(@Valid @RequestBody EditCabinetAO editCabinetAO) {
        log.info("received alter cabinet info:{}", JSONUtil.toJsonPrettyStr(editCabinetAO));
        return cabinetService.edieCabinet(editCabinetAO);
    }

    @Operation(summary = "智能柜列表")
    @PostMapping("/list")
    public ApiResponse getCabinetList() {
        return cabinetService.getCabinetList();
    }

    @Operation(summary = "智能柜详情")
    @PostMapping("/info")
    public ApiResponse getCabinetInfo(@Valid @RequestParam String cabinetId) {
        log.info("received cabinet ID:{}", cabinetId);
        return cabinetService.getCabinetDetail(cabinetId);
    }

    @Autowired
    public void setCabinetService(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }
}
