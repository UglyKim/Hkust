package com.hkust.wmc.controller;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.enums.OptTypeEnum;
import com.hkust.utils.EnumToJsonUtils;
import com.hkust.wmc.dto.ao.AddReagentsAO;
import com.hkust.wmc.dto.ao.AlterReagentsAO;
import com.hkust.wmc.dto.ao.ReagentsQueryAO;
import com.hkust.wmc.service.ReagentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "试剂管理")
@RestController
@RequestMapping("/wmc/v1/reagents")
@Slf4j
public class ReagentsController {

    private ReagentsService reagentsService;

    @Operation(summary = "试剂列表-顶部统计")
    @PostMapping("/statistics")
    public ApiResponse statistics() {
        return reagentsService.reagentsStat();
    }

    @Operation(summary = "出入库类型")
    @PostMapping("/inoutbound/type")
    public ApiResponse getInOutBoundType() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(OptTypeEnum.class));
    }

    @Operation(summary = "添加试剂")
    @PostMapping("/add")
    public ApiResponse addReagents(@RequestBody AddReagentsAO addReagentsAO) {
        log.info("received reagents info:{}", JSONUtil.toJsonPrettyStr(addReagentsAO));
        return reagentsService.addReagents(addReagentsAO);
    }

    @Operation(summary = "试剂列表")
    @PostMapping("/list")
    public ApiResponse getReagentsList(@RequestBody ReagentsQueryAO reagentsQueryAO) {
        log.info("received query params:{}", JSONUtil.toJsonPrettyStr(reagentsQueryAO));
        return reagentsService.getReagentsList(reagentsQueryAO);
    }

    @Operation(summary = "试剂详情")
    @PostMapping("/detail")
    public ApiResponse getReagentsInfo(@RequestParam String reagentsId) {
        log.info("received query reagentsID:{}", reagentsId);
        return reagentsService.getReagentsDetail(reagentsId);
    }

    @Operation(summary = "编辑试剂")
    @PostMapping("/alter")
    public ApiResponse alterReagents(@RequestBody AlterReagentsAO alterReagentsAO) {
        log.info("received query alterReagentsAO:{}", JSONUtil.toJsonPrettyStr(alterReagentsAO));
        return reagentsService.alterReagents(alterReagentsAO);
    }

    @Operation(summary = "试剂存取日志")
    @PostMapping("/log/list")
    public ApiResponse inOutboundList(@RequestBody ReagentsQueryAO reagentsQueryAO) {
        log.info("received query params:{}", JSONUtil.toJsonPrettyStr(reagentsQueryAO));
        return ApiResponse.success();
    }

    @Autowired
    public void setReagentsService(ReagentsService reagentsService) {
        this.reagentsService = reagentsService;
    }
}
