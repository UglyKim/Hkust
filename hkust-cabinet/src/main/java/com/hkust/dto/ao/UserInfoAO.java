package com.hkust.dto.ao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"username", "phone", "email"})
public class UserInfo {

    private static final long serialVersionUID = 5926468583005150707L;

    @Schema(required = true)
    private String username;

    @Schema
    private String email;

    @Schema(required = true)
    private String phone;
}
