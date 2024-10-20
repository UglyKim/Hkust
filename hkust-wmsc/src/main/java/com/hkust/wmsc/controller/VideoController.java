package com.hkust.wmsc.controller;

import com.hkust.wmsc.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/v1/video")
@Slf4j
public class VideoController {

    private VideoService videoService;

    @Operation(summary = "Upload an MP4  file",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "multipart/form-data",
                    schema = @Schema(type = "string", format = "binary"))))
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        log.info("Received  file: {}", file.getOriginalFilename());
        return null;
//        if (file.isEmpty()) {
//            log.error("File is empty");
//            return ApiResponse.failed(ReturnCode.FILE_IS_NULL);
//        }
//        return videoService.videoUpload(file);
    }

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }
}
