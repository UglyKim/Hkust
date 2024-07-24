package com.hkust.controller.management;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.CabinetAO;
import com.hkust.dto.vo.CabinetVO;
import com.hkust.enums.CabinetStatEnum;
import com.hkust.enums.OpenModeEnum;
import com.hkust.service.CabinetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "柜子信息")
@RestController
@RequestMapping("/api/v1/cabinet")
@Slf4j
public class CabinetController {

    private CabinetService cabinetService;

    @Operation(summary = "柜子详细信息", description = "柜子详细信息")
    @PostMapping("/detail")
    public ApiResponse<CabinetVO> getCabinetInfo() {
        CabinetVO cabinetVO = cabinetService.getAllCabinets();
        Assert.notNull(cabinetVO, "Cabinets list should not be null");
        return ApiResponse.success(cabinetVO);
    }

    @Deprecated
    @Operation(summary = "柜子列表", description = "柜子列表")
    @PostMapping("/list")
    public ApiResponse getCabinetList() {
        return ApiResponse.success();
    }

    @Operation(summary = "添加柜子", description = "添加柜子")
    @PostMapping("/add")
    public ApiResponse addCabinet(@RequestBody CabinetAO cabinetAO) {
        log.info("received add cabinet_info:{}", StrUtil.toString(cabinetAO));
        return cabinetService.addCabinet(cabinetAO);
    }

    @Operation(summary = "柜门开启模式", description = "柜门开启模式")
    @PostMapping("/open-mode")
    public ApiResponse getOpenMode() {
        Map<String, String> openModeMap = Arrays.asList(OpenModeEnum.values()).stream().collect(
                Collectors.toMap(OpenModeEnum::getCode, OpenModeEnum::getDescription));
        return ApiResponse.success(openModeMap);
    }

    @Operation(summary = "智能柜状态", description = "智能柜状态")
    @PostMapping("/stat")
    public ApiResponse getCabinetStat() {
        Map<String, String> cabinetStatMap = Arrays.asList(CabinetStatEnum.values()).stream().collect(
                Collectors.toMap(CabinetStatEnum::getCode, CabinetStatEnum::getName));
        return ApiResponse.success(cabinetStatMap);
    }

    @Deprecated
    @Operation(summary = "删除柜子", description = "删除柜子")
    @PostMapping("/delete")
    public ApiResponse delCabinet() {

        return ApiResponse.success();
    }

    @Autowired
    public void setCabinetService(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }
}
