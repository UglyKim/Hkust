package com.hkust.controller.management;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.CabinetAO;
import com.hkust.dto.vo.CabinetVO;
import com.hkust.enums.OpenModeEnum;
import com.hkust.enums.StatEnum;
import com.hkust.service.CabinetService;
import com.hkust.utils.EnumToJsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "柜子信息")
@RestController
@RequestMapping("/api/v1/cabinet")
@Slf4j
public class CabinetController {

    private CabinetService cabinetService;

    @Operation(summary = "柜子详细信息")
    @PostMapping("/detail")
    public ApiResponse<CabinetVO> getCabinetInfo(@RequestParam String cabinetId) {
        log.info("received cabinet ID:{}", cabinetId);
        return cabinetService.getCabinetDetails(cabinetId);
    }

    @Operation(summary = "柜子列表")
    @PostMapping("/list")
    public ApiResponse getCabinetList() {
        return cabinetService.getCabinetList();
    }

    @Operation(summary = "添加柜子")
    @PostMapping("/add")
    public ApiResponse addCabinet(@RequestBody CabinetAO cabinetAO) {
        log.info("received add cabinet_info:{}", JSONUtil.toJsonPrettyStr(cabinetAO));
        return cabinetService.addCabinet(cabinetAO);
    }

    @Operation(summary = "智能柜更新")
    @PostMapping("/update")
    public ApiResponse updateCabinet(@RequestBody CabinetAO cabinetAO) {
        log.info("received add cabinet_info:{}", JSONUtil.toJsonPrettyStr(cabinetAO));
        return cabinetService.updateCabinet(cabinetAO);
    }

    @Deprecated
    @Operation(summary = "删除智能柜")
    @PostMapping("/delete")
    public ApiResponse delCabinet() {
        return ApiResponse.success();
    }

    @Operation(summary = "智能柜状态")
    @PostMapping("/stat")
    public ApiResponse getCabinetStat() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(StatEnum.class));
    }

    @Operation(summary = "柜门开启模式")
    @PostMapping("/open-mode")
    public ApiResponse getOpenMode() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(OpenModeEnum.class));
    }

    @Operation(summary = "统计")
    @PostMapping("/statistics")
    public ApiResponse reagentStatistics() {

        return ApiResponse.success();
    }

    @Autowired
    public void setCabinetService(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }
}
