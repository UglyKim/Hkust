package com.hkust.wmc.controller;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hkust.dto.ApiResponse;
import com.hkust.enums.CabinetStateEnum;
import com.hkust.utils.EnumToJsonUtils;
import com.hkust.wmc.dto.ao.CabinetAO;
import com.hkust.wmc.dto.ao.EditCabinetAO;
import com.hkust.wmc.dto.vo.CabinetVO;
import com.hkust.wmc.service.CabinetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "智能仓储柜")
@RestController
@RequestMapping("/wmc/v1/cabinet")
@Slf4j
public class CabinetController {

    private CabinetService cabinetService;

    @Operation(summary = "仓储智能柜使用状态")
    @PostMapping("/stat")
    private ApiResponse<List<ObjectNode>> getCabinetType() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(CabinetStateEnum.class));
    }

    @Operation(summary = "添加仓储智能柜")
    @PostMapping("/add")
    private ApiResponse<Valid> addCabinet(@RequestBody CabinetAO cabinetAO) {
        log.info("received cabinet info:{}", JSONUtil.toJsonPrettyStr(cabinetAO));
        cabinetService.addCabinet(cabinetAO);
        return ApiResponse.success();
    }

    @Operation(summary = "仓储智能柜列表")
    @PostMapping("/list")
    private ApiResponse<List<CabinetVO>> getCabinetList() {
        return cabinetService.getCabinetList();
    }

    @Operation(summary = "编辑智能柜")
    @PostMapping("/edit")
    private ApiResponse<Void> editCabinet(@Valid @RequestBody EditCabinetAO editCabinetAO) {
        log.info("received alter cabinet info:{}", JSONUtil.toJsonPrettyStr(editCabinetAO));
        return cabinetService.edieCabinet(editCabinetAO);
    }

    @Autowired
    public void setCabinetService(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }
}
