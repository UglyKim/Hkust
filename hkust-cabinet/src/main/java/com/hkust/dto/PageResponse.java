package com.hkust.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResponse<T> {

    @Schema(description = "当前页码", required = true)
    private int pageNum;

    @Schema(description = "每页显示条数", required = true)
    private int pageSize;

    @Schema(description = "总数", required = true)
    private long total;

    @Schema(description = "总页数", required = true)
    private int totalPages;

    @Schema(description = "数据", required = true)
    private List<T> records;

    public PageResponse(int pageNum, int pageSize, long total, List<T> records) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }
}
