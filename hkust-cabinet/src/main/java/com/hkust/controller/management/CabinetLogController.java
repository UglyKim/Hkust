package com.hkust.controller.management;

import cn.hutool.core.util.StrUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.EventQueryAO;
import com.hkust.service.EventService;
import com.hkust.service.OperateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "日志")
@RestController
@RequestMapping("/api/v1/log")
@Slf4j
public class CabinetLogController {

    private EventService eventService;

    @Operation(summary = "日志列表")
    @PostMapping("/list")
    public ApiResponse getLogList(@RequestBody EventQueryAO eventQueryAO) {
        log.info("received event_query_info:{}", StrUtil.toString(eventQueryAO));
        return eventService.getAllEventList(eventQueryAO);
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
