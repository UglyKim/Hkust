package com.hkust.controller.management;

import cn.hutool.core.util.StrUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.ReagentsAO;
import com.hkust.dto.vo.ReagentsOptVO;
import com.hkust.service.ReagentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "试剂")
@RestController
@RequestMapping("/api/v1/reagents")
@Slf4j
public class ReagentsController {

    private ReagentsService reagentsService;

    public ApiResponse addReagents(@RequestBody ReagentsAO reagentsAO) {
        log.info("received reagents info:{}", StrUtil.toString(reagentsAO));
        return reagentsService.addReagents(reagentsAO);
    }

    @Operation(summary = "试剂列表", description = "试剂列表")
    @PostMapping("/list")
    public ApiResponse<PageResponse> getReagentsList() {
        return reagentsService.getReagentsList();
    }

    @Operation(summary = "试剂存取记录", description = "试剂存取记录")
    @PostMapping("/access/list")
    public ApiResponse<PageResponse> accessReagentsList(@RequestParam String doorId) {
        log.info("received door_id:{}", doorId);
        return reagentsService.getReagentsRecordList(doorId);
    }

    @Autowired
    public void setReagentsService(ReagentsService reagentsService) {
        this.reagentsService = reagentsService;
    }
}
