package com.hkust.dto.vo;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonIncludeProperties({"videoId", "videoUrl"})
public class VideoVO implements Serializable {

    private static final long serialVersionUID = 7766868583003330109L;

    @Schema(required = true, description = "录像编号")
    private String videoId;

    @Schema(required = true, description = "查看地址")
    private String videoUrl;
}
