package com.hkust.dto.ao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonPropertyOrder({"username", "password"})
public class LoginInfo implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @Schema(required = true, description = "用户名")
    private String username;

    @Schema(required = true, description = "密码")
    private String password;
}