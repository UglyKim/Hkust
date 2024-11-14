package com.hkust.wmsc.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = 9098765445365550909L;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "学号")
    private String studentId;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "部门")
    private String dept;

    @Schema(description = "办公地点")
    private String officeLocation;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "座机")
    private String fixedTel;

    @Schema(description = "邮箱")
    private String email;

    private Boolean enabled;

    private List<String> roleList;


}
