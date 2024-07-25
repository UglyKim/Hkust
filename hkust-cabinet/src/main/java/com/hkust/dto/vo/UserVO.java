package com.hkust.dto.vo;

import com.hkust.enums.GenderEnum;
import com.hkust.utils.UUIDUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class UserVO {

    @Schema(required = true, description = "用户ID")
    private String userId;

    @Schema(required = true, description = "学号")
    private String studentId;

    @Schema(required = true, description = "用户名")
    private String username;

    @Schema(required = true, description = "真实姓名")
    private String realName;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "办公地点")
    private String officeLocation;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "固定电话")
    private String fixedTel;

    @Schema(description = "状态")
    private String stat;

//    @Schema(description = "是否启用")
//    private String password;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "性别")
    private GenderEnum gender;

    @Schema(description = "是否启用")
    private String enabled;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}