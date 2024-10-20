package com.hkust.wmsc.controller;


import com.hkust.dto.ApiResponse;
import com.hkust.wmsc.service.OptLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "操作日志")
@RestController
@RequestMapping("/v1/wmsc/opt")
public class OptLogController {


    private OptLogService optLogService;

    @Operation(summary = "操作日志详情")
    @PostMapping("/info")
    public ApiResponse optInfo(@RequestParam String optId) {
        return null;
    }

    @Operation(summary = "操作日志列表")
    @PostMapping("/list")
    public ApiResponse optList() {

        return null;
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
