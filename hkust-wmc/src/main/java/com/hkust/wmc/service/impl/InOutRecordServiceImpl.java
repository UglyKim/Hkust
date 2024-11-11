package com.hkust.wmc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkust.entity.wms.WmsInOutRecord;
import com.hkust.enums.OptTypeEnum;
import com.hkust.mapper.wmsc.WmsInOutRecordMapper;
import com.hkust.wmc.service.WmsInOutRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class InOutRecordServiceImpl extends ServiceImpl<WmsInOutRecordMapper, WmsInOutRecord> implements WmsInOutRecordService {

    private WmsInOutRecordMapper wmsInOutRecordMapper;

    public List<WmsInOutRecord> getLogs() {
        QueryWrapper<WmsInOutRecord> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("opt_time");
        return wmsInOutRecordMapper.selectList(wrapper);
    }

    /**
     * 当月总入库
     * @return count
     */
    public Long getThisMonthInbound() {
        QueryWrapper<WmsInOutRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("type", OptTypeEnum.INBOUND.getCode());
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 获取当前月的第一天
        LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay(); // 当月第一天的开始时间
        // 获取当前月的最后一天
        LocalDateTime endOfMonth = today.withDayOfMonth(today.lengthOfMonth()).atTime(23, 59, 59); // 当月最后一天的结束时间
        wrapper.between("opt_time", startOfMonth, endOfMonth);
        return wmsInOutRecordMapper.selectCount(wrapper);
    }

    /**
     * 当月总出库
     *
     * @return count
     */
    public Long getThisMonthOutbound() {
        QueryWrapper<WmsInOutRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("type", OptTypeEnum.OUTBOUND.getCode());
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 获取当前月的第一天
        LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay(); // 当月第一天的开始时间
        // 获取当前月的最后一天
        LocalDateTime endOfMonth = today.withDayOfMonth(today.lengthOfMonth()).atTime(23, 59, 59); // 当月最后一天的结束时间
        wrapper.between("opt_time", startOfMonth, endOfMonth);
        return wmsInOutRecordMapper.selectCount(wrapper);
    }

    @Autowired
    public void setWmsInOutRecordMapper(WmsInOutRecordMapper wmsInOutRecordMapper) {
        this.wmsInOutRecordMapper = wmsInOutRecordMapper;
    }
}
