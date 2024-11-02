package com.hkust.wmsc.service;

import cn.hutool.core.date.DateUtil;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.Video;
import com.hkust.mapper.VideoMapper;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;

@Service
@Slf4j
public class VideoService {

    private VideoMapper videoMapper;

    @Value("${uplaod.dir}")
    private String uploadDir;

    @Value(("${uplaod.server_address}"))
    private String serverAddress;


    public ApiResponse videoUpload(MultipartFile file) {

        if (file.isEmpty()) {
            return ApiResponse.failed(ReturnCode.FILE_IS_EMPTY);
        }
        if (!file.getContentType().equals("video/mp4")) {
            return ApiResponse.failed(ReturnCode.FILE_NOT_MP4);
        }

        // 判断录像根目录是否存在，不存在则创建
//        String uploadDir = "/Users/kim/work/uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 每天创建一个日期为名称的文件夹
        LocalDate today = LocalDate.now();
        String folderName = today.toString(); // 格式为 "YYYY-MM-DD"
        // 指定文件夹路径（当前目录）
        Path folderPath = Paths.get(uploadDir + folderName);
        try {
            // 检查文件夹是否存在
            if (!Files.exists(folderPath)) {
                // 文件夹不存在，创建文件夹
                Files.createDirectory(folderPath);
                log.info("文件夹创建成功: " + folderPath.toAbsolutePath());
            } else {
                // 文件夹已存在
                log.info("文件夹已存在: " + folderPath.toAbsolutePath());
            }
        } catch (IOException e) {
            log.error("创建文件夹失败: " + e.getMessage());
        }


        // 保存文件到指定文件夹
        try {
            // 构建文件保存路径
            String newFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = folderPath.resolve(newFileName);
            file.transferTo(filePath.toFile());
            log.info(filePath.toString());
            String url = serverAddress + folderName + "/" + newFileName;
            log.info("video url is:{}", url);
            this.insertVideo(url);
            return ApiResponse.success();
        } catch (IOException e) {
            return ApiResponse.failed(ReturnCode.FILE_UPLOAD_FAILED);
        }
    }

    private void insertVideo(String url) {
        Video video = new Video();
        video.setVideoId(UUIDUtils.generateUUIDWithoutHyphens());
        video.setCreateDate(DateUtils.getCurrentDateTime());
        video.setUpdateDate(DateUtils.getCurrentDateTime());
        // TODO: 需要设置
        video.setUrl(url);
        videoMapper.insert(video);
    }

    @Autowired
    public void setVideoMapper(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }
}
