package com.hkust.dto.ao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hkust.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@JsonPropertyOrder({"username", "phone", "realName"})
public class UserInfoAO {

    private static final long serialVersionUID = 987468583005150707L;

    @Schema(required = true, description = "学员号")
    @NotNull
    private String studentId;

    @Schema(required = true, description = "用户名")
    private String username;

    @Schema(required = true, description = "密码")
    private String password;

    @Schema(required = true, description = "真实姓名")
    private String realName;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "办公地点")
    private String officeLocation;

    @Schema(required = true, description = "手机号")
    private String phone;

    @Schema(description = "固定电话")
    private String fixedTel;

    @Schema(description = "邮箱")
    private String email;

    @Schema(required = true, description = "性别")
    private GenderEnum gender;

}
