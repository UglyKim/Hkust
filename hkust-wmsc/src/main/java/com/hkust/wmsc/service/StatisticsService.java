package com.hkust.wmsc.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.wms.WmsInOutRecord;
import com.hkust.entity.wms.WmsReagents;
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

    private WmsReagentsMapper wmsReagentsMapper;

    private WmsInOutRecordMapper wmsInOutRecordMapper;

    private InOutRecordServiceImpl inOutRecordService;

    public ApiResponse<MainPageStaticsVO> totalStats(String cabinetId) {
        // 试剂总数
        QueryWrapper<WmsReagents> wrapper = new QueryWrapper<>();
        wrapper.eq("cabinet_id", cabinetId);
        Long total = wmsReagentsMapper.selectCount(wrapper);
        Long inThisMonthTotal = inOutRecordService.getThisMonthInbound();
        Long outThisMonthTotal = inOutRecordService.getThisMonthOutbound();

        // return
        MainPageStaticsVO mainPageStaticsVO = new MainPageStaticsVO();
        mainPageStaticsVO.setTotal(total.intValue());
        mainPageStaticsVO.setInThisMonthTotal(inThisMonthTotal.intValue());
        mainPageStaticsVO.setOutThisMonthTotal(outThisMonthTotal.intValue());
        return ApiResponse.success(mainPageStaticsVO);
    }

    public ApiResponse<InOutBoundStatisticsVO> outboundRecordStats(String cabinetId) {
        // 总数
        QueryWrapper<WmsReagents> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cabinet_id", cabinetId);
        Long total = wmsReagentsMapper.selectCount(queryWrapper);
        // 本月入库总数
        Long inThisMonthTotal = inOutRecordService.getThisMonthOutbound();
        InOutBoundStatisticsVO vo = InOutBoundStatisticsVO.builder().totalCount(total.intValue()).inOutCount(inThisMonthTotal.intValue()).build();
        return ApiResponse.success(vo);
    }

    public ApiResponse<InOutBoundStatisticsVO> inboundRecordStats(String cabinetId) {
        // 总数
        QueryWrapper<WmsReagents> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cabinet_id", cabinetId);
        Long total = wmsReagentsMapper.selectCount(queryWrapper);
        // 本月入库总数
        Long inThisMonthTotal = inOutRecordService.getThisMonthInbound();
        InOutBoundStatisticsVO vo = InOutBoundStatisticsVO.builder().totalCount(total.intValue()).inOutCount(inThisMonthTotal.intValue()).build();
        return ApiResponse.success(vo);
    }

    /**
     * 出入库列表查询
     * @param reagentsQueryAO 查询
     * @return  ApiResponse<PageResponse>
     */
    public ApiResponse<PageResponse<InOutboundVO>> inOutBoundRecordList(ReagentsQueryAO reagentsQueryAO) {
        QueryWrapper<WmsInOutRecord> wrapper = new QueryWrapper<>();
        if (ObjUtil.isNotEmpty(reagentsQueryAO.getName())) {
            wrapper.like("reagents_name", reagentsQueryAO.getName());
        }
        wrapper.eq("type", reagentsQueryAO.getInOut());
        Page<WmsInOutRecord> page = new Page<>(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize());
        IPage<WmsInOutRecord> iPage = wmsInOutRecordMapper.selectPage(page, wrapper);
        List<WmsInOutRecord> records = iPage.getRecords();
        if (CollUtil.isEmpty(records)) {
            PageResponse<InOutboundVO> pageResponse = new PageResponse<>(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize(), iPage.getTotal(), null);
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
        PageResponse<InOutboundVO> pageResponse = new PageResponse<>(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize(), iPage.getTotal(), inOutboundVOList);
        return ApiResponse.success(pageResponse);
    }

    @Autowired
    public void setWmsReagentsMapper(WmsReagentsMapper wmsReagentsMapper) {
        this.wmsReagentsMapper = wmsReagentsMapper;
    }

    @Autowired
    public void setWmsInOutRecordMapper(WmsInOutRecordMapper wmsInOutRecordMapper) {
        this.wmsInOutRecordMapper = wmsInOutRecordMapper;
    }

    @Autowired
    public void setInOutRecordService(InOutRecordServiceImpl inOutRecordService) {
        this.inOutRecordService = inOutRecordService;
    }
}
