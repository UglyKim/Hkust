package com.hkust.controller.management;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.vo.CabinetVO;
import com.hkust.service.CabinetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "柜子信息")
@RestController
@RequestMapping("/api/v1/cabinet")
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
    public ApiResponse addCabinet() {

        return ApiResponse.success();
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
