package com.hkust.wmsc.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.wms.WmsInOutRecord;
import com.hkust.enums.OptTypeEnum;
import com.hkust.mapper.wmsc.WmsInOutRecordMapper;
import com.hkust.mapper.wmsc.WmsReagentsMapper;
import com.hkust.wmsc.dto.PageResponse;
import com.hkust.wmsc.dto.ao.ReagentsQueryAO;
import com.hkust.wmsc.dto.vo.InOutBoundStatisticsVO;
import com.hkust.wmsc.dto.vo.InOutboundVO;
import com.hkust.wmsc.dto.vo.MainPageStaticsVO;
import com.hkust.wmsc.service.impl.InOutRecordServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StatisticsService {

    private WmsReagentsMapper reagentsMapper;

    private WmsInOutRecordMapper inOutRecordMapper;

    private InOutRecordServiceImpl inOutRecordService;

    public ApiResponse totalStats(String cabinetId) {
        // 试剂总数
        QueryWrapper wrapper = new QueryWrapper();
        Long total = reagentsMapper.selectCount(wrapper);
        Long inThisMonthTotal = inOutRecordService.getThisMonthInbound();
        Long outThisMonthTotal = inOutRecordService.getThisMonthOutbound();

        // return
        MainPageStaticsVO mainPageStaticsVO = new MainPageStaticsVO();
        mainPageStaticsVO.setTotal(total.intValue());
        mainPageStaticsVO.setInThisMonthTotal(inThisMonthTotal.intValue());
        mainPageStaticsVO.setOutThisMonthTotal(outThisMonthTotal.intValue());
        return ApiResponse.success(mainPageStaticsVO);
    }

    public ApiResponse outboundRecordStats() {
        // 总数
        QueryWrapper queryWrapper = new QueryWrapper();
        Long total = reagentsMapper.selectCount(queryWrapper);
        // 本月入库总数
        Long inThisMonthTotal = inOutRecordService.getThisMonthOutbound();
        InOutBoundStatisticsVO vo = InOutBoundStatisticsVO.builder().totalCount(total.intValue()).inOutCount(inThisMonthTotal.intValue()).build();
        return ApiResponse.success(vo);
    }

    public ApiResponse inboundRecordStats() {
        // 总数
        QueryWrapper queryWrapper = new QueryWrapper();
        Long total = reagentsMapper.selectCount(queryWrapper);
        // 本月入库总数
        Long inThisMonthTotal = inOutRecordService.getThisMonthInbound();
        InOutBoundStatisticsVO vo = InOutBoundStatisticsVO.builder().totalCount(total.intValue()).inOutCount(inThisMonthTotal.intValue()).build();
        return ApiResponse.success(vo);
        /*
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
        List<InOutboundVO> inboundVOList = new ArrayList<>();
        for (WmsInOutRecord record : records) {
            InOutboundVO inboundVO = new InOutboundVO();
            inboundVO.setName(record.getReagentsName());
            inboundVO.setGHS(record.getGhs());
            inboundVO.setInTime(record.getOptTime());
            inboundVO.setCount("1");
            inboundVOList.add(inboundVO);
        }

        PageResponse pageResponse = new PageResponse(inOutboundAO.getPageNum(), inOutboundAO.getPageSize(), iPage.getTotal(), inboundVOList);
        */
    }

    /**
     * 出入库列表查询
     *
     * @param reagentsQueryAO
     * @return
     */
    public ApiResponse<PageResponse> inOutBoundRecordList(ReagentsQueryAO reagentsQueryAO) {
        QueryWrapper<WmsInOutRecord> wrapper = new QueryWrapper<>();
        if (ObjUtil.isNotEmpty(reagentsQueryAO.getName())) {
            wrapper.like("reagents_name", reagentsQueryAO.getName());
        }
        wrapper.eq("type", reagentsQueryAO.getType());
        Page<WmsInOutRecord> page = new Page(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize());
        IPage iPage = inOutRecordMapper.selectPage(page, wrapper);
        List<WmsInOutRecord> records = iPage.getRecords();
        if (CollUtil.isEmpty(records)) {
            PageResponse pageResponse = new PageResponse(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize(), iPage.getTotal(), null);
            return ApiResponse.success(pageResponse);
        }
        List<InOutboundVO> inOutboundVOList = new ArrayList<>();
        for (WmsInOutRecord record : records) {
            InOutboundVO inOutboundVO = new InOutboundVO();
            inOutboundVO.setName(record.getReagentsName());
            inOutboundVO.setGHS(record.getGhs());
            inOutboundVO.setInTime(record.getOptTime());
            inOutboundVO.setCount("1");
            inOutboundVOList.add(inOutboundVO);
        }
        PageResponse pageResponse = new PageResponse(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize(), iPage.getTotal(), inOutboundVOList);
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

    @Autowired
    public void setInOutRecordService(InOutRecordServiceImpl inOutRecordService) {
        this.inOutRecordService = inOutRecordService;
    }
}
