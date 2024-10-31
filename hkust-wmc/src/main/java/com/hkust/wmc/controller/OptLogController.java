package com.hkust.wmc.controller;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.enums.CabinetStateEnum;
import com.hkust.enums.OptTypeEnum;
import com.hkust.utils.EnumToJsonUtils;
import com.hkust.wmc.dto.ao.OptLogQueryAO;
import com.hkust.wmc.service.OptLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "日志管理")
@RestController
@RequestMapping("/wmc/v1/log")
@Slf4j
public class OptLogController {

    private OptLogService optLogService;

    @Deprecated
    @Operation(summary = "日志类型")
    @PostMapping("/type")
    public ApiResponse getCabinetType() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(OptTypeEnum.class));
    }

    @Operation(summary = "用户操作日志列表")
    @PostMapping("/opt_list")
    public ApiResponse getOptLogList(@RequestBody OptLogQueryAO optLogQueryAO) {
        log.info("received opt query info:{}", JSONUtil.toJsonPrettyStr(optLogQueryAO));
        return optLogService.getOptLogList(optLogQueryAO);
    }

    @Operation(summary = "试剂日志列表")
    @PostMapping("/inoutbount_list")
    public ApiResponse getReagentsOptLogList(@RequestBody OptLogQueryAO optLogQueryAO) {
        log.info("received opt query info:{}", JSONUtil.toJsonPrettyStr(optLogQueryAO));
        return optLogService.getReagentsOptLogList(optLogQueryAO);
    }

    @Autowired
    public void setOptLogService(OptLogService optLogService) {
        this.optLogService = optLogService;
    }
}
