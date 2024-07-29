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

    private static final long serialVersionUID = 3456098765433330109L;

    @Schema(required = true, description = "学员号")
    private String studentId;

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

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "是否启用")
    private String enabled;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}