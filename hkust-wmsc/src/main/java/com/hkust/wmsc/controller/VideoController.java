package com.hkust.wmsc.controller;

import com.hkust.constant.ReturnCode;
//import com.hkust.dto.ApiResponse;
import com.hkust.wmsc.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "录像")
@RestController
@RequestMapping("/wmsc/v1/video")
@Slf4j
public class VideoController {

    private VideoService videoService;

//    @Operation(
//            summary = "上传文件",
//            description = "上传一个文件并返回上传状态"
//    )
    @PostMapping("/upload")
    public com.hkust.dto.ApiResponse handleFileUpload(@RequestParam("file") MultipartFile file) {
        // 检查文件是否为空
        log.info("Received  file: {}", file.getOriginalFilename());
        if (file.isEmpty()) {
            return com.hkust.dto.ApiResponse.failed(ReturnCode.FILE_IS_EMPTY);
        }
        return videoService.videoUpload(file);
    }

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }
}
