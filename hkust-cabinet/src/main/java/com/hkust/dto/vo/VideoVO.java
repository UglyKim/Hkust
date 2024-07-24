package com.hkust.dto.vo;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIncludeProperties({"videoId", "videoUrl"})
public class VideoVO {

    @Schema(required = true, description = "录像编号")
    private String videoId;

    @Schema(required = true, description = "查看地址")
    private String videoUrl;
}
