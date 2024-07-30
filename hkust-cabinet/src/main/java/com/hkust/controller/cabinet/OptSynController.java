package com.hkust.controller.cabinet;

import cn.hutool.json.JSONUtil;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.EventQueryAO;
import com.hkust.dto.ao.OperationAO;
import com.hkust.enums.EventTypeEnum;
import com.hkust.service.EventService;
import com.hkust.service.OperateService;
import com.hkust.service.VideoService;
import com.hkust.utils.EnumToJsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "操作日志同步&录像上传")
@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class OptSynController {

    private VideoService videoService;

    private OperateService operateService;

    private EventService eventService;

    @Operation(summary = "操作日志同步", description = "不包含试剂存取操作")
    @PostMapping("/event/add")
    public ApiResponse eventSynUpload(@RequestBody OperationAO operationAO) {
        log.info("received operation_info:{}", JSONUtil.toJsonPrettyStr(operationAO));
        return operateService.optSynchronize(operationAO);
    }

    @Operation(summary = "Upload an MP4  file",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "multipart/form-data",
                    schema = @Schema(type = "string", format = "binary"))))
    @PostMapping("/video/upload")
    public ApiResponse handleFileUpload(@RequestParam("file") MultipartFile file) {
        log.info("Received  file: {}", file.getOriginalFilename());
        if (file.isEmpty()) {
            log.error("File is empty");
            return ApiResponse.failed(ReturnCode.FILE_IS_NULL);
        }
        return videoService.videoUpload(file);
    }

    @Operation(summary = "操作列表")
    @PostMapping("/event/list")
    public ApiResponse<PageResponse> getEventList(EventQueryAO eventQueryAO) {
        log.info("received event_query_info:{}", JSONUtil.toJsonPrettyStr(eventQueryAO));
        return eventService.getEventList(eventQueryAO);
    }

    @Operation(summary = "操作类型")
    @PostMapping("/event/type")
    public ApiResponse getEventType() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(EventTypeEnum.class));
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
