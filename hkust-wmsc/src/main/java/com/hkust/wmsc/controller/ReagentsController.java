package com.hkust.wmsc.controller;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hkust.dto.ApiResponse;
import com.hkust.enums.HazardPropsEnum;
import com.hkust.enums.InOutEnumType;
import com.hkust.enums.PhysicalStateEnum;
import com.hkust.enums.ReagentsOptTypeEnum;
import com.hkust.utils.EnumToJsonUtils;
import com.hkust.wmsc.dto.PageResponse;
import com.hkust.wmsc.dto.ao.*;
import com.hkust.wmsc.dto.vo.ReagentsVO;
import com.hkust.wmsc.dto.vo.StocktakingVO;
import com.hkust.wmsc.dto.vo.WmsInOutRecordVO;
import com.hkust.wmsc.service.impl.ReagentsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "试剂")
@RestController
@RequestMapping("/wmsc/v1/reagents")
@Slf4j
@Validated
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
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(ReagentsOptTypeEnum.class));
    }

    @Operation(summary = "化学品危险属性类型")
    @PostMapping("/hazard_props")
    public ApiResponse<List<ObjectNode>> getHazardProps() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(HazardPropsEnum.class));
    }

    @Operation(summary = "试剂详细信息查询-基于试剂编号")
    @PostMapping("/info")
    public ApiResponse<ReagentsVO> getReagentsInfo(@Valid @RequestParam String reagentsId) {
        log.info("received id:{}", reagentsId);
        return reagentsService.findReagents(reagentsId);
    }

    @Operation(summary = "试剂列表查询")
    @PostMapping("/search")
    public ApiResponse<PageResponse<ReagentsVO>> getReagentsList(@Valid @RequestBody ReagentsQueryAO reagentsQueryAO) {
        log.info("received form reagent params:{}", JSONUtil.toJsonPrettyStr(reagentsQueryAO));
        return reagentsService.findReagentsList(reagentsQueryAO);
    }

    @Operation(summary = "入库")
    @PostMapping("/inbound")
    public ApiResponse<String> inboundReagents(@RequestBody @Valid List<InReagentsAO> inReagentsAOList) {
        log.info("received reagent list:{}", JSONUtil.toJsonPrettyStr(inReagentsAOList));
        return reagentsService.inboundReagents(inReagentsAOList);
    }

    @Operation(summary = "出库")
    @PostMapping("/outbound")
    public ApiResponse<String> outboundReagents(@Valid @RequestBody List<OutReagentsAO> outReagentsAOList) {
        log.info("received reagent list:{}", JSONUtil.toJsonPrettyStr(outReagentsAOList));
        return reagentsService.outboundReagents(outReagentsAOList);
    }

    @Operation(summary = "出入库日志查询")
    @PostMapping("/logs")
    public ApiResponse<List<WmsInOutRecordVO>> getLogList() {
        return reagentsService.getLogList();
    }

    @Operation(summary = "查看出入库试剂详情")
    @PostMapping("/outbound/detail")
    public ApiResponse<ReagentsVO> inOutboundDetail(@RequestParam String reagentsId) {
        log.info("received reagents ID:{}", reagentsId);
        return reagentsService.getInOutboundReagentsDetail(reagentsId);
    }

    @Operation(summary = "临期查询")
    @PostMapping("/expiration_list")
    public ApiResponse<PageResponse<ReagentsVO>> expirationList(@Valid @RequestBody ExpReagentsQueryAO expReagentsQueryAO) {
        log.info("received exp reagents query info:{}", JSONUtil.toJsonPrettyStr(expReagentsQueryAO));
        return reagentsService.getExpReagentsList(expReagentsQueryAO);
    }

    @Operation(summary = "盘点")
    @PostMapping("/stocktaking")
    public ApiResponse<PageResponse<StocktakingVO>> stocktakingReagents(@Valid @RequestBody StocktakingAO stocktakingAO) {
        log.info("received stocktaking query info:{}", JSONUtil.toJsonPrettyStr(stocktakingAO));
        return reagentsService.stocktakingReagents(stocktakingAO);
    }

    @Operation(summary = "盘点结果上传")
    @PostMapping("/stocktaking/result")
    public ApiResponse<Void> stocktakingRecordResult(@Valid @RequestBody List<StocktakingRecordResultAO> stocktakingRecordResultAOList) {
        log.info("received stocktaking query stocktakingRecordResultAOList:{}", JSONUtil.toJsonPrettyStr(stocktakingRecordResultAOList));
        return reagentsService.recordResult(stocktakingRecordResultAOList);
    }

    @Autowired
    public void setReagentsService(ReagentsServiceImpl reagentsService) {
        this.reagentsService = reagentsService;
    }
}
