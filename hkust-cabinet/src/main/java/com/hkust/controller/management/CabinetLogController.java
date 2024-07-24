package com.hkust.controller.management;

import com.hkust.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "日志")
@RestController
@RequestMapping("/api/v1/log")
public class CabinetLogController {

    @Operation(summary = "日志列表", description = "日志列表")
    @PostMapping("/list")
    public ApiResponse getLogList() {

        return ApiResponse.success();
    }
}
