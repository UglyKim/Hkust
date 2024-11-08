package com.hkust.wmsc.controller;

import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.wmsc.service.VideoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "录像")
@RestController
@RequestMapping("/wmsc/v1/video")
@Slf4j
public class VideoController {

    private VideoService videoService;

    @PostMapping("/upload")
    public ApiResponse<Void> handleFileUpload(@RequestParam("file") MultipartFile file) {
        // 检查文件是否为空
        log.info("Received  file: {}", file.getOriginalFilename());
        if (file.isEmpty()) {
            return ApiResponse.failed(ReturnCode.FILE_IS_EMPTY);
        }
        return videoService.videoUpload(file);
    }

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }
}
