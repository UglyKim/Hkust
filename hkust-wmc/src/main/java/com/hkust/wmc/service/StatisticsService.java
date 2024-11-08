package com.hkust.wmc.service;


import com.hkust.dto.ApiResponse;
import com.hkust.mapper.wmsc.WmsInOutRecordMapper;
import com.hkust.wmc.dto.vo.StatisticsInOutBoundVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StatisticsService {

    private WmsInOutRecordMapper wmsInOutRecordMapper;

    public ApiResponse<List<StatisticsInOutBoundVO>> monthInoutBoundStatistics() {
        List<Map<Object, Object>> resultList = wmsInOutRecordMapper.selectInventorySummary();
        // 转换为 VO 列表
        List<StatisticsInOutBoundVO> statisticsList = StatisticsMapper.mapToStatisticsVOList(resultList);
        return ApiResponse.success(statisticsList);
    }

    @Autowired
    public void setWmsInOutRecordMapper(WmsInOutRecordMapper wmsInOutRecordMapper) {
        this.wmsInOutRecordMapper = wmsInOutRecordMapper;
    }
}
