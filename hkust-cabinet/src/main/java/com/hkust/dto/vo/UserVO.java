package com.hkust.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {

    @Schema(required = true, description = "用户名")
    private String username;
}
