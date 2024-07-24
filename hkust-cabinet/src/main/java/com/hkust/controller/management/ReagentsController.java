package com.hkust.controller.management;

import com.hkust.dto.ApiResponse;
import com.hkust.dto.vo.ReagentsOptVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "试剂")
@RestController
@RequestMapping("/api/v1/reagents")
@Slf4j
public class ReagentsController {

    @Operation(summary = "试剂存取记录", description = "试剂存取记录")
    @PostMapping("/list")
    public ApiResponse<List<ReagentsOptVO>> getLogList(@RequestParam String doorId) {

        return ApiResponse.success();
    }
}
