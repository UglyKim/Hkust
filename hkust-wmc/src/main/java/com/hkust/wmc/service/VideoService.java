package com.hkust.wmc.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.Video;
import com.hkust.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    private VideoMapper videoMapper;

    public ApiResponse<List<Video>> getVideoList() {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("create_date");
        List<Video> videoList = videoMapper.selectList(wrapper);
        return ApiResponse.success(videoList);
    }

    @Autowired
    public void setVideoMapper(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }
}