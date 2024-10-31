package com.hkust.wmc.service;

import com.hkust.enums.OptTypeEnum;
import com.hkust.wmc.dto.vo.StatisticsInOutBoundVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticsMapper {
    public static List<StatisticsInOutBoundVO> mapToStatisticsVOList(List<Map<Object, Object>> records) {
        List<StatisticsInOutBoundVO> result = new ArrayList<>();

        for (Map<Object, Object> record : records) {
            StatisticsInOutBoundVO stats = new StatisticsInOutBoundVO();

            // 从 Map 中提取 month、inboundCount 和 outboundCount
            if (record.containsKey("month")) {
                String monthStr = (String) record.get("month");
                stats.setMonth(monthStr);
            }

            if (record.containsKey("type") && record.containsKey("quantity")) {
                String type = (String) record.get("type");
                int quantity = ((Number) record.get("quantity")).intValue();

                if (OptTypeEnum.INBOUND.getCode().equals(type)) { // 入库
                    stats.setInboundCount(quantity);
                } else if (OptTypeEnum.OUTBOUND.getCode().equals(type)) { // 出库
                    stats.setOutboundCount(quantity);
                }
            }
            result.add(stats);
        }
        return result;
    }
}
