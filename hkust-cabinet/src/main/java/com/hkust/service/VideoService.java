package com.hkust.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.Video;
import com.hkust.mapper.VideoMapper;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class VideoService extends ServiceImpl<VideoMapper, Video> {

    private VideoMapper videoMapper;

    public ApiResponse getVideoList() {
        QueryWrapper<Video> wrapper = new QueryWrapper<Video>();
        List<Video> videos = videoMapper.selectList(wrapper);
        return ApiResponse.success(videos);
    }

    public ApiResponse videoUpload(MultipartFile file) {

        if (file.isEmpty()) {
            return ApiResponse.failed(ReturnCode.FILE_IS_EMPTY);
        }
        if (!file.getContentType().equals("video/mp4")) {
            return ApiResponse.failed(ReturnCode.FILE_NOT_MP4);
        }

        String uploadDir = "uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            File dest = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(dest);
            // TODO: 插库
            this.insertVideo();
            return ApiResponse.success();
        } catch (IOException e) {
            return ApiResponse.failed(ReturnCode.FILE_UPLOAD_FAILED);
        }
    }

    private void insertVideo() {
        Video video = new Video();
        video.setVideoId(UUIDUtils.generateUUIDWithoutHyphens());
        video.setCreateDate(DateUtils.getCurrentDate());
        video.setUpdateDate(DateUtils.getCurrentDate());
        // TODO: 需要设置
        video.setUrl("");
        videoMapper.insert(video);
    }

    @Autowired
    public void setVideoMapper(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }
}
