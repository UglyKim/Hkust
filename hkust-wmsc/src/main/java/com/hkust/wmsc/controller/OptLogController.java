package com.hkust.wmsc.controller;


import com.hkust.dto.ApiResponse;
import com.hkust.enums.OptTypeEnum;
import com.hkust.utils.EnumToJsonUtils;
import com.hkust.wmsc.service.OptLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@Tag(name = "操作日志")
@RestController
@RequestMapping("/v1/opt")
@Slf4j
public class OptLogController {


    private OptLogService optLogService;

    @Operation(summary = "操作类型")
    @PostMapping("/type")
    public ApiResponse optTypeList() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(OptTypeEnum.class));
    }

    @Operation(summary = "操作日志详情")
    @PostMapping("/info")
    public ApiResponse optInfo(@RequestParam String optId) {
        log.info("received opt ID: {}", optId);
        return optLogService.optLogDetail(optId);
    }

    @Operation(summary = "操作日志列表")
    @PostMapping("/list")
    public ApiResponse optList() {
        return optLogService.optLostList();
    }

//    @Operation(summary = "操作日志查询")
//    @RequestMapping("/search")
//    public ApiResponse searchOptList() {
//        return null;
//    }

    @Autowired
    public void setOptLogService(OptLogService optLogService) {
        this.optLogService = optLogService;
    }
}
