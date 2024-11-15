package com.hkust.wmc.controller;

import com.hkust.dto.ApiResponse;
import com.hkust.entity.Video;
import com.hkust.wmc.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "录像")
@RestController
@RequestMapping("/wmc/v1/video")
@Slf4j
public class VideoController {

    private VideoService videoService;

    @Operation(description = "查询录像列表")
    @PostMapping("/list")
    public ApiResponse<List<Video>> getVideoList() {
        return videoService.getVideoList();
    }

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }
}