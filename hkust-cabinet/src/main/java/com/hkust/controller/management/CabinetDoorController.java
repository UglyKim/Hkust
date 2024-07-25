package com.hkust.controller.management;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.CabinetDoorAO;
import com.hkust.dto.vo.DoorVO;
import com.hkust.service.CabinetDoorService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "柜门信息")
@RestController
@RequestMapping("/api/v1/cabinet")
@Slf4j
public class CabinetDoorController {

    private CabinetDoorService cabinetDoorService;

    @Operation(summary = "柜门列表", description = "柜门列表")
    @PostMapping("/door/list")
    public ApiResponse<List<DoorVO>> getCabinetInfo(@RequestParam String cabinetId) {
        log.info("received cabinet_id is:{}", cabinetId);
        return cabinetDoorService.getDoorList(cabinetId);
    }

    @Hidden
    @Operation(summary = "柜门详细信息", description = "柜门详细信息")
    @PostMapping("/door/info")
    public ApiResponse getDoorInfo(@RequestParam String doorId) {
        log.info("received door_id is:{}", doorId);
        return cabinetDoorService.getDoorDetail(doorId);
    }

    @Operation(summary = "柜门新增", description = "柜门新增")
    @PostMapping("/door/add")
    public ApiResponse addDoor(@RequestBody CabinetDoorAO cabinetDoorAO) {
        log.info("received cabinet_door info:{}", StrUtil.toString(cabinetDoorAO));
        return cabinetDoorService.addCabinetDoor(cabinetDoorAO);
    }

    @Autowired
    public void setCabinetDoorService(CabinetDoorService cabinetDoorService) {
        this.cabinetDoorService = cabinetDoorService;
    }
}
