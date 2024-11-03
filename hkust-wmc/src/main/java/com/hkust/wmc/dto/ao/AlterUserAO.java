package com.hkust.wmc.dto.ao;

import com.hkust.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Schema(description = "修改用户信息")
public class AlterUserAO implements Serializable {

    private static final long serialVersionUID = 892099900008888669L;

    @Schema(description = "学员号")
    @NotNull(message = "请输入学号")
    private String studentId;

    @Schema(description = "用户名")
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

    @Schema(description = "职位")
    private String position;
}
