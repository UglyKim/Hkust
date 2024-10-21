package com.hkust.wmc.controller;

import com.hkust.dto.ApiResponse;
import com.hkust.enums.OptTypeEnum;
import com.hkust.utils.EnumToJsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "试剂")
@RestController
@RequestMapping("/wmc/v1/reagents")
@Slf4j
public class ReagentsController {

    @Operation(summary = "试剂列表")
    @PostMapping("/list")
    public ApiResponse getReagentsList() {
        return ApiResponse.success();
    }
}
