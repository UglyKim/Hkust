package com.hkust.controller.management;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.vo.DoorVO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "柜门信息")
@RestController
@RequestMapping("/api/v1/cabinet")
@Slf4j
public class CabinetDoorManageController {

    @Operation(summary = "柜门列表", description = "柜门列表")
    @PostMapping("/door/list")
    public ApiResponse<List<DoorVO>> getCabinetInfo(@RequestParam String cabinetId) {
        Assert.notNull(cabinetId, "Cabinets list should not be null");
        log.info("received cabinetId is:{}", cabinetId);
        return ApiResponse.success();
    }

    @Hidden
    @Operation(summary = "柜门详细信息", description = "柜门详细信息")
    @PostMapping("/door/info")
    public ApiResponse getDoorInfo(@RequestParam String doorId) {
        return ApiResponse.success();
    }

    @Operation(summary = "柜门新增", description = "柜门新增")
    @PostMapping("/door/add")
    public ApiResponse addDoor(@RequestParam String cabinetId) {

        return ApiResponse.success();
    }

}
