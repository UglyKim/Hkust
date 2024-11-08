package com.hkust.wmc.dto.ao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hkust.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@JsonPropertyOrder({"username", "phone", "realName"})
@AllArgsConstructor
public class AddUserAO implements Serializable {

    private static final long serialVersionUID = 665538583005150707L;

    @Schema(required = true, description = "学员号")
    @NotNull(message = "请填写学号")
    private String studentId;

    @Schema(required = true, description = "用户名")
    @NotNull(message = "请填写用户名")
    private String username;

    @Schema(required = true, description = "密码")
    @NotNull(message = "请输入密码")
    private String password;

    @Schema(required = true, description = "真实姓名")
    private String realName;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "办公地点")
    private String officeLocation;

    @Schema(description = "部门")
    private String dept;

    @Schema(description = "职位")
    private String position;

    @Schema(required = true, description = "手机号")
    @NotNull(message = "请输入手机号")
    private String phone;

    @Schema(description = "固定电话")
    private String fixedTel;

    @Schema(description = "邮箱")
    private String email;

    @Schema(required = true, description = "性别")
    private GenderEnum gender;

    @Schema(required = true, description = "渠道 MC:管理控制台 SC:仓储智能柜")
    private String addCh;

    @Schema(required = true, description = "角色")
    @NotNull(message = "请选择角色")
    private String roleId;

}
