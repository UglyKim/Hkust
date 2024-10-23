package com.hkust.mapper.wmsc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkust.entity.wms.WmsReagents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface WmsReagentsMapper extends BaseMapper<WmsReagents> {

    List<Map<String, Object>> selectReagentsGroupedByNameAndSpecification(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("limit") int limit,
            @Param("offset") int offset
    );
}
