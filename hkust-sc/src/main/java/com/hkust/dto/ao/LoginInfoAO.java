package com.hkust.dto.ao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonPropertyOrder({"studentId", "password", "channel"})
public class LoginInfoAO implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

//    @Schema(required = true, description = "用户名")
//    private String username

    @Schema(required = true, description = "学员号")
    private String studentId;

    @Schema(required = true, description = "密码")
    private String password;

    @Schema(required = true, description = "渠道 - MC：管理控制台, SC:智能柜")
    private String channel;
}