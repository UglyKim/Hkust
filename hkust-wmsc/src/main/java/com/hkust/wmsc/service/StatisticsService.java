package com.hkust.wmsc.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.wms.WmsInOutRecord;
import com.hkust.mapper.wmsc.WmsInOutRecordMapper;
import com.hkust.mapper.wmsc.WmsReagentsMapper;
import com.hkust.wmsc.dto.PageResponse;
import com.hkust.wmsc.dto.ao.InOutboundAO;
import com.hkust.wmsc.dto.vo.InboundVO;
import com.hkust.wmsc.dto.vo.MainPageStaticsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StatisticsService {

    private WmsReagentsMapper reagentsMapper;

    private WmsInOutRecordMapper inOutRecordMapper;

    public ApiResponse totalStats() {
        QueryWrapper queryWrapper = new QueryWrapper();
        Long total = reagentsMapper.selectCount(queryWrapper);
        QueryWrapper<WmsInOutRecord> inWrapper = new QueryWrapper<>();
        inWrapper.eq("type", "in");
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 获取当前月的第一天
        LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay(); // 当月第一天的开始时间
        // 获取当前月的最后一天
        LocalDateTime endOfMonth = today.withDayOfMonth(today.lengthOfMonth()).atTime(23, 59, 59); // 当月最后一天的结束时间
        inWrapper.between("in_time", startOfMonth, endOfMonth);
        Long inThisMonthTotal = inOutRecordMapper.selectCount(inWrapper);

        QueryWrapper<WmsInOutRecord> outWrapper = new QueryWrapper<>();
        outWrapper.eq("type", "out");
        outWrapper.between("out_time", startOfMonth, endOfMonth);
        Long outThisMonthTotal = inOutRecordMapper.selectCount(outWrapper);

        MainPageStaticsVO mainPageStaticsVO = new MainPageStaticsVO();
        mainPageStaticsVO.setTotal(String.valueOf(total));
        mainPageStaticsVO.setInThisMonthTotal(String.valueOf(inThisMonthTotal));
        mainPageStaticsVO.setOutThisMonthTotal(String.valueOf(outThisMonthTotal));
        return ApiResponse.success(mainPageStaticsVO);
    }

    public ApiResponse<PageResponse> inboundRecord(InOutboundAO inOutboundAO) {
        // 总数
        QueryWrapper queryWrapper = new QueryWrapper();
        Long total = reagentsMapper.selectCount(queryWrapper);
        QueryWrapper<WmsInOutRecord> warpper = new QueryWrapper<>();

        String type = inOutboundAO.getType();
        if (type.equals("in")) {
            warpper.eq("type", "in");
            warpper.between("in_time", inOutboundAO.getStartDate(), inOutboundAO.getEndDate());
        } else {
            warpper.eq("type", "out");
            warpper.between("out_time", inOutboundAO.getStartDate(), inOutboundAO.getEndDate());
        }
        // 入库 - 时间区间
        Long inTotal = inOutRecordMapper.selectCount(warpper);


        //查询列表
        if (ObjectUtil.isNotEmpty(inOutboundAO.getName())) {
            warpper.like("reagents_name", inOutboundAO.getName());
        }
        warpper.groupBy("name", "specification");
        if (type.equals("in")) {
            warpper.orderByDesc("in_time");
        } else {
            warpper.orderByDesc("out_time");
        }
        Page<WmsInOutRecord> page = new Page(inOutboundAO.getPageNum(), inOutboundAO.getPageSize());
        IPage iPage = inOutRecordMapper.selectPage(page, warpper);
        List<WmsInOutRecord> records = page.getRecords();

        if (CollUtil.isEmpty(records)) {
            PageResponse pageResponse = new PageResponse(inOutboundAO.getPageNum(), inOutboundAO.getPageSize(), iPage.getTotal(), null);
            return ApiResponse.success(pageResponse);
        }
        List<InboundVO> inboundVOList = new ArrayList<>();
        for (WmsInOutRecord record : records) {
            InboundVO inboundVO = new InboundVO();
            inboundVO.setName(record.getReagentsName());
            inboundVO.setGHS(record.getGhs());
            inboundVO.setInTime(record.getInTime());
            inboundVO.setCount("1");
            inboundVOList.add(inboundVO);
        }

        PageResponse pageResponse = new PageResponse(inOutboundAO.getPageNum(), inOutboundAO.getPageSize(), iPage.getTotal(), inboundVOList);
        return ApiResponse.success(pageResponse);
    }


    @Autowired
    public void setReagentsMapper(WmsReagentsMapper reagentsMapper) {
        this.reagentsMapper = reagentsMapper;
    }

    @Autowired
    public void setInOutRecordMapper(WmsInOutRecordMapper inOutRecordMapper) {
        this.inOutRecordMapper = inOutRecordMapper;
    }
}
