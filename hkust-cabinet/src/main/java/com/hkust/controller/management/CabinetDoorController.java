package com.hkust.controller.management;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.CabinetDoorAO;
import com.hkust.dto.vo.DoorVO;
import com.hkust.enums.DoorTypeEnum;
import com.hkust.service.CabinetDoorService;
import com.hkust.utils.EnumToJsonUtils;
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

    @Operation(summary = "柜门列表")
    @PostMapping("/door/list")
    public ApiResponse<List<DoorVO>> getCabinetInfo(@RequestParam String cabinetId) {
        log.info("received cabinet_id is:{}", cabinetId);
        return cabinetDoorService.getDoorList(cabinetId);
    }

    //    @Hidden
    @Operation(summary = "柜门详细信息")
    @PostMapping("/door/detail")
    public ApiResponse getDoorInfo(@RequestParam String doorId) {
        log.info("received door_id is:{}", doorId);
        return cabinetDoorService.getDoorDetail(doorId);
    }

    @Operation(summary = "柜门新增")
    @PostMapping("/door/add")
    public ApiResponse addDoor(@RequestBody CabinetDoorAO cabinetDoorAO) {
        log.info("received cabinet_door info:{}", JSONUtil.toJsonPrettyStr(cabinetDoorAO));
        return cabinetDoorService.addCabinetDoor(cabinetDoorAO);
    }

    @Operation(summary = "柜门更新")
    @PostMapping("/door/update")
    public ApiResponse updateDoor(@RequestBody CabinetDoorAO cabinetDoorAO) {
        log.info("received update door info:{}", JSONUtil.toJsonPrettyStr(cabinetDoorAO));
        return cabinetDoorService.updateCabinetDoor(cabinetDoorAO);

    }

    @Operation(summary = "柜门类型")
    @PostMapping("/door/type")
    public ApiResponse getDoorTypeList() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(DoorTypeEnum.class));
    }

    @Autowired
    public void setCabinetDoorService(CabinetDoorService cabinetDoorService) {
        this.cabinetDoorService = cabinetDoorService;
    }
}
