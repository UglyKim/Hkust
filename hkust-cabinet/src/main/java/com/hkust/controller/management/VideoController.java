package com.hkust.controller.management;

import com.hkust.dto.ApiResponse;
import com.hkust.dto.vo.VideoVO;
import com.hkust.service.VideoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "录像列表")
@RestController
@RequestMapping("/api/v1/manage/")
@Slf4j
public class VideoController {

    private VideoService videoService;

    public ApiResponse<List<VideoVO>> getVideoList() {
        return videoService.getVideoList();
    }

    @Autowired
    public void setVideoService(VideoService videoService) {
        this.videoService = videoService;
    }
}
