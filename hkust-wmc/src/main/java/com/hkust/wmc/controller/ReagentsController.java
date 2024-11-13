package com.hkust.wmc.controller;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hkust.dto.ApiResponse;
import com.hkust.enums.InOutEnumType;
import com.hkust.enums.OptTypeEnum;
import com.hkust.enums.PhysicalStateEnum;
import com.hkust.utils.EnumToJsonUtils;
import com.hkust.wmc.dto.PageResponse;
import com.hkust.wmc.dto.ao.InReagentsAO;
import com.hkust.wmc.dto.ao.AlterReagentsAO;
import com.hkust.wmc.dto.ao.ReagentsQueryAO;
import com.hkust.wmc.dto.vo.ReagentsStatisticsVO;
import com.hkust.wmc.dto.vo.ReagentsVO;
import com.hkust.wmc.service.impl.ReagentsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "试剂")
@RestController
@RequestMapping("/wmc/v1/reagents")
@Slf4j
public class ReagentsController {

    private ReagentsServiceImpl reagentsService;

    @Operation(summary = "物理状态类型")
    @PostMapping("/physical_state")
    public ApiResponse<List<ObjectNode>> getPhysicalState() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(PhysicalStateEnum.class));
    }

    @Operation(summary = "是否在库类型")
    @PostMapping("/in_stock")
    public ApiResponse<List<ObjectNode>> getIsInOut() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(InOutEnumType.class));
    }

    @Operation(summary = "出入库类型")
    @PostMapping("/inoutbound/type")
    public ApiResponse<List<ObjectNode>> getInOutBoundType() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(OptTypeEnum.class));
    }

    @Operation(summary = "试剂列表-顶部统计")
    @PostMapping("/statistics")
    public ApiResponse<ReagentsStatisticsVO> statistics() {
        return reagentsService.reagentsStat();
    }

    @Operation(summary = "试剂列表")
    @PostMapping("/list")
    public ApiResponse<PageResponse<ReagentsVO>> getReagentsList(@Valid @RequestBody ReagentsQueryAO reagentsQueryAO) {
        log.info("received query params:{}", JSONUtil.toJsonPrettyStr(reagentsQueryAO));
        return reagentsService.getReagentsList(reagentsQueryAO);
    }

    @Operation(summary = "添加试剂")
    @PostMapping("/add")
    public ApiResponse<String> inboundReagents(@RequestBody @Valid List<InReagentsAO> inReagentsAOList) {
        log.info("received reagents info:{}", JSONUtil.toJsonPrettyStr(inReagentsAOList));
        return reagentsService.addReagents(inReagentsAOList);
    }

    @Operation(summary = "试剂详情")
    @PostMapping("/detail")
    public ApiResponse<ReagentsVO> getReagentsInfo(@RequestParam String reagentsId) {
        log.info("received query reagentsID:{}", reagentsId);
        return reagentsService.getReagentsDetail(reagentsId);
    }

    @Operation(summary = "编辑试剂")
    @PostMapping("/alter")
    public ApiResponse<Void> alterReagents(@RequestBody AlterReagentsAO alterReagentsAO) {
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
    public void setReagentsService(ReagentsServiceImpl reagentsService) {
        this.reagentsService = reagentsService;
    }
}
