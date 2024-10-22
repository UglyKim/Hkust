package com.hkust.wmc.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "用户")
@Data
@AllArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = 8929099809998330109L;

    private String userId;

    private String studentId;

    private String username;

    private String realName;

    private String dept;

    private String position;

    private String address;

    private String officeLocation;

    private String phone;

    private String fixedTel;

//    @TableField("stat")
//    private String stat;

    private String password;

    private String email;

    private String gender;

    private Boolean enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String addCh;
}
