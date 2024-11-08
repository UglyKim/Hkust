package com.hkust.wmc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Schema(description = "用户列表查询")
@AllArgsConstructor
public class UserQueryAO implements Serializable {

    private static final long serialVersionUID = 233333555998330109L;

    @Schema(description = "页码")
    @Min(value = 1, message = "页码必须大于0")
    private int pageNum;

    @Schema(description = "显示条数")
    @Min(value = 1, message = "每页显示条数必须大于0")
    private int pageSize;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "查询起始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;

    @Schema(description = "查询结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;
}
