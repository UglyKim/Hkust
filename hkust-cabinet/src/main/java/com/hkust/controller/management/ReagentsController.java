package com.hkust.controller.management;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.ReagentsAO;
import com.hkust.dto.ao.ReturnReagentsAO;
import com.hkust.service.ReagentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "试剂")
@RestController
@RequestMapping("/api/v1/reagents")
@Slf4j
public class ReagentsController {

    private ReagentsService reagentsService;

    @Operation(summary = "添加试剂")
    @PostMapping("/add")
    public ApiResponse addReagents(@RequestBody ReagentsAO reagentsAO) {
        log.info("received reagents info:{}", JSONUtil.toJsonPrettyStr(reagentsAO));
        return reagentsService.addReagents(reagentsAO);
    }


    @Operation(summary = "试剂列表")
    @PostMapping("/list")
    public ApiResponse<PageResponse> getReagentsList(@RequestParam String cabinetId) {
        log.info("received cabinet_id is:{}", cabinetId);
        return reagentsService.getReagentsList(cabinetId);
    }

    @Operation(summary = "试剂存取记录")
    @PostMapping("/access/list")
    public ApiResponse<PageResponse> accessReagentsList(@RequestParam String doorId) {
        log.info("received door_id:{}", doorId);
        return reagentsService.getReagentsRecordList(doorId);
    }

    @Operation(summary = "取试剂")
    @PostMapping("/take")
    public ApiResponse<PageResponse> takeReagents(@RequestParam String barCode) {
        log.info("received barcode:{}", barCode);
        return reagentsService.takeReagents(barCode);
    }

    @Operation(summary = "还试剂")
    @PostMapping("/delete")
    public ApiResponse<PageResponse> returnReagents(@RequestBody ReturnReagentsAO returnReagentsAO) {
        log.info("received return reagents:{}", returnReagentsAO);
        return reagentsService.returnReagents(returnReagentsAO);
    }

    @Operation(summary = "归还试剂")
    @PostMapping("/return")
    public ApiResponse<PageResponse> delReagents(@RequestParam String barCode) {
        log.info("received barCode:{}", barCode);
        return reagentsService.delReagents(barCode);
    }

    @Autowired
    public void setReagentsService(ReagentsService reagentsService) {
        this.reagentsService = reagentsService;
    }
}
