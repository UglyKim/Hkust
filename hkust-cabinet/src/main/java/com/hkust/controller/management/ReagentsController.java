package com.hkust.controller.management;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.ReagentsAO;
import com.hkust.dto.ao.ReturnReagentsAO;
import com.hkust.dto.ao.query.ReagentsQueryAO;
import com.hkust.dto.vo.InOutEnum;
import com.hkust.service.ReagentsService;
import com.hkust.utils.EnumToJsonUtils;
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
    public ApiResponse<PageResponse> getReagentsList(@RequestBody ReagentsQueryAO reagentsQueryAO) {
        log.info("received ReagentsQueryAO is:{}", JSONUtil.toJsonPrettyStr(reagentsQueryAO));
        return reagentsService.getReagentsList(reagentsQueryAO);
    }

    @Deprecated
    @Operation(summary = "试剂列表-条件查询")
    @PostMapping("/list/condition")
    public ApiResponse<PageResponse> getReagentsListByCondition(@RequestParam String doorId) {
        log.info("received doorId is:{}", doorId);
        return null;
    }

    @Operation(summary = "试剂存取记录")
    @PostMapping("/access/list")
    public ApiResponse<PageResponse> accessReagentsList(@RequestParam String doorId) {
        log.info("received door_id:{}", doorId);
        return reagentsService.getReagentsRecordList(doorId);
    }

    @Operation(summary = "取试剂")
    @PostMapping("/take")
    public ApiResponse takeReagents(@RequestParam String barCode) {
        log.info("received barcode:{}", barCode);
        return reagentsService.takeReagents(barCode);
    }

    @Operation(summary = "还试剂")
    @PostMapping("/return")
    public ApiResponse returnReagents(@RequestBody ReturnReagentsAO returnReagentsAO) {
        log.info("received return reagents:{}", returnReagentsAO);
        return reagentsService.returnReagents(returnReagentsAO);
    }

    @Operation(summary = "删除试剂")
    @PostMapping("/delete")
    public ApiResponse delReagents(@RequestParam String barCode) {
        log.info("received barCode:{}", barCode);
        return reagentsService.delReagents(barCode);
    }

    @Operation(summary = "是否在柜 0:在柜 1:离柜")
    @PostMapping("/inout")
    public ApiResponse getGender() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(InOutEnum.class));
    }

    @Autowired
    public void setReagentsService(ReagentsService reagentsService) {
        this.reagentsService = reagentsService;
    }
}
