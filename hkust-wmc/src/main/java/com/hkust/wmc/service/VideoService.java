package com.hkust.wmc.service;

import com.hkust.dto.ApiResponse;
import com.hkust.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    private VideoMapper videoMapper;

    public ApiResponse getVideoList() {

        return null;
    }

    @Autowired
    public void setVideoMapper(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }
}
