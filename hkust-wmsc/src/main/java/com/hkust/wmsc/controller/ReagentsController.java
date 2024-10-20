package com.hkust.wmsc.controller;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.wmsc.dto.ao.InReagentsAO;
import com.hkust.wmsc.dto.ao.OutReagentsAO;
import com.hkust.wmsc.dto.ao.ReagentsQueryAO;
import com.hkust.wmsc.service.impl.ReagentsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "试剂")
@RestController
@RequestMapping("/v1/reagents")
@Slf4j
public class ReagentsController {

    private ReagentsServiceImpl reagentsService;

    @Operation(summary = "试剂详细信息查询-基于试剂编号")
    @PostMapping("/info")
    public ApiResponse searchReagents(@RequestParam String reagentsId) {
        log.info("received id:{}", reagentsId);
        return reagentsService.findReagents(reagentsId);
    }

    @Operation(summary = "试剂列表查询")
    @PostMapping("/search")
    public ApiResponse searchReagents(@RequestBody ReagentsQueryAO reagentsQueryAO) {
        log.info("received form reagent params:{}", JSONUtil.toJsonPrettyStr(reagentsQueryAO));
        return reagentsService.findReagentsList(reagentsQueryAO);
    }

    @Operation(summary = "入库")
    @PostMapping("/inbound")
    public ApiResponse inboundReagents(@RequestBody List<InReagentsAO> inReagentsAOList) {
        Assert.notNull(inReagentsAOList);
        log.info("received reagent list:{}", JSONUtil.toJsonPrettyStr(inReagentsAOList));
        return reagentsService.inboundReagents(inReagentsAOList);
    }

    @Operation(summary = "出库")
    @PostMapping("/outbound")
    public ApiResponse outboundReagents(@RequestBody List<OutReagentsAO> outReagentsAOList) {
        Assert.notNull(outReagentsAOList);
        log.info("received reagent list:{}", JSONUtil.toJsonPrettyStr(outReagentsAOList));
        return reagentsService.outboundReagents(outReagentsAOList);
    }

    @Operation(summary = "查看出入库试剂详情")
    @PostMapping("/outbound/detail")
    public ApiResponse inOutboundDetail(@RequestParam String reagentsId) {
        log.info("received reagents ID:{}", reagentsId);
        return reagentsService.getInOutboundReagentsDetail(reagentsId);
    }

    @Operation(summary = "临期查询")
    @PostMapping("/expiration_list")
    public ApiResponse expirationList() {
        return null;
    }

    @Autowired
    public void setReagentsService(ReagentsServiceImpl reagentsService) {
        this.reagentsService = reagentsService;
    }
}
