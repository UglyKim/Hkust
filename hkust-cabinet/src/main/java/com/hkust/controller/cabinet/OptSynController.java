package com.hkust.controller.cabinet;

import cn.hutool.core.util.StrUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.EventQueryAO;
import com.hkust.dto.ao.OperationAO;
import com.hkust.enums.EventTypeEnum;
import com.hkust.service.EventService;
import com.hkust.service.OperateService;
import com.hkust.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "操作日志同步&录像上传")
@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class OptSynController {

    private VideoService videoService;

    private OperateService operateService;

    private EventService eventService;

    @Operation(summary = "操作日志同步", description = "操作日志同步")
    @PostMapping("/event/add")
    public ApiResponse eventSynUpload(@RequestBody OperationAO operationAO) {
        log.info("received operation_info:{}", StrUtil.toString(operationAO));
        return operateService.optSynchronize(operationAO);
    }

    @Operation(summary = "操作录像上传", description = "操作录像上传")
    @PostMapping("/video/upload")
    public ApiResponse handleFileUpload(
            @RequestParam("file")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "multipart/form-data",
                    schema = @Schema(type = "string", format = "binary")))
                    MultipartFile file) {
        log.info("file size:{} bytes", file.getSize());
        return videoService.videoUpload(file);
    }

    @Operation(summary = "操作类型", description = "操作类型")
    @PostMapping("/event/type")
    public ApiResponse getEventType() {
        Map<String, String> eventTypeMap = Arrays.asList(EventTypeEnum.values()).stream().collect(
                Collectors.toMap(EventTypeEnum::getCode, EventTypeEnum::getOpt));
        return ApiResponse.success(eventTypeMap);
    }

    @Operation(summary = "操作列表", description = "操作列表")
    @PostMapping("/event/list")
    public ApiResponse<PageResponse> getEventList(EventQueryAO eventQueryAO) {
        log.info("received event_query_info:{}", StrUtil.toString(eventQueryAO));
        return eventService.getEventList(eventQueryAO);
    }

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }

    @Autowired
    public void setOperateService(OperateService operateService) {
        this.operateService = operateService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
