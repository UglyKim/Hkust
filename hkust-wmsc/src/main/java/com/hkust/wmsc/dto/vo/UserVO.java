package com.hkust.wmsc.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
//@AllArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = 9098765445365550909L;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "学号")
    private String studentId;


}
