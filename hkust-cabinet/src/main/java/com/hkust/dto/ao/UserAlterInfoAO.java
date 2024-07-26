package com.hkust.dto.ao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hkust.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
@JsonPropertyOrder({"username", "password", "realName"})
public class UserAlterInfoAO {

    private static final long serialVersionUID = 987876583005150707L;

    @Schema(description = "学员号")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "办公地点")
    private String officeLocation;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "固定电话")
    private String fixedTel;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "性别")
    private GenderEnum gender;
}
