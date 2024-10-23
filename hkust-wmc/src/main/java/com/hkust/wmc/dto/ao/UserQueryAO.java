package com.hkust.wmc.dto.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Schema(description = "用户列表查询")
public class UserQueryAO implements Serializable {

    private static final long serialVersionUID = 233333555998330109L;
}
