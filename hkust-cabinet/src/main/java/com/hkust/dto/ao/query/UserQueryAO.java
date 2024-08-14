package com.hkust.dto.ao.query;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
public class UserQueryAO implements Serializable {

    private static final long serialVersionUID = 8926331009009330109L;

    @Schema(required = true, description = "页码")
    @NotNull
    @Min(value = 1)
    private int pageNum;

    @Schema(required = true, description = "显示条数")
    @NotNull
    @Min(value = 1)
    private int pageSize;

    @Schema(description = "学员号")
    private String studentId;

    @Schema(description = "学员姓名")
    private String realName;
}
