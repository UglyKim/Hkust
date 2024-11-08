package com.hkust.wmsc.controller;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hkust.dto.ApiResponse;
import com.hkust.enums.CabinetStateEnum;
import com.hkust.utils.EnumToJsonUtils;
import com.hkust.wmsc.dto.ao.EditCabinetAO;
import com.hkust.wmsc.dto.vo.CabinetVO;
import com.hkust.wmsc.service.CabinetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "智能仓储柜")
@RestController
@RequestMapping("/wmsc/v1/cabinet")
@Slf4j
public class CabinetController {

    private CabinetService cabinetService;

    @Operation(summary = "仓储智能柜使用状态")
    @PostMapping("/stat")
    private ApiResponse<List<ObjectNode>> getInOutBoundType() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(CabinetStateEnum.class));
    }

    @Operation(summary = "编辑智能柜")
    @PostMapping("/edit")
    private ApiResponse<Void> alterCabinet(@Valid @RequestBody EditCabinetAO editCabinetAO) {
        log.info("received alter cabinet info:{}", JSONUtil.toJsonPrettyStr(editCabinetAO));
        return cabinetService.edieCabinet(editCabinetAO);
    }

    @Operation(summary = "智能柜列表")
    @PostMapping("/list")
    private ApiResponse<List<CabinetVO>> getCabinetList() {
        return cabinetService.getCabinetList();
    }

    @Operation(summary = "智能柜详情")
    @PostMapping("/info")
    private ApiResponse<CabinetVO> getCabinetInfo(@Valid @RequestParam String cabinetId) {
        log.info("received cabinet ID:{}", cabinetId);
        return cabinetService.getCabinetDetail(cabinetId);
    }

    @Autowired
    public void setCabinetService(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }
}
