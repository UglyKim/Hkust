package com.hkust.wmsc.dto.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Schema(description = "查询试剂信息表单内容")
public class ReagentsQueryAO implements Serializable {

    private static final long serialVersionUID = 987755555990009989L;

    @Schema(description = "化学试剂名称")
    private String name;
}
