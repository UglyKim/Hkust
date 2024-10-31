package com.hkust.wmc.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.wms.WmsReagents;
import com.hkust.enums.OptTypeEnum;
import com.hkust.mapper.wmsc.WmsInOutRecordMapper;
import com.hkust.mapper.wmsc.WmsReagentsMapper;
import com.hkust.utils.DateUtils;
import com.hkust.wmc.dto.PageResponse;
import com.hkust.wmc.dto.ao.ReagentsAO;
import com.hkust.wmc.dto.ao.ReagentsQueryAO;
import com.hkust.wmc.dto.vo.ReagentsStatisticsVO;
import com.hkust.wmc.dto.vo.ReagentsVO;
import com.hkust.wmc.struct.structmapper.WmcReagentsStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReagentsService {

    private WmsReagentsMapper wmsReagentsMapper;

    private WmsInOutRecordMapper wmsInOutRecordMapper;

    public ApiResponse reagentsStat() {
        // 当前总库存
        QueryWrapper wrapper = new QueryWrapper();
        Long count = wmsInOutRecordMapper.selectCount(wrapper);
        // 本月出库
        QueryWrapper wrapper_in = new QueryWrapper();
        LocalDate today = LocalDate.now(); // 当前日期
        LocalDate firstDayOfMonth = today.withDayOfMonth(1); // 当月第一天
        LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth()); // 当月最后一天
        wrapper_in.eq("type", OptTypeEnum.INBOUND.getCode());
        wrapper_in.between("opt_time", firstDayOfMonth, lastDayOfMonth);
        Long thisMonthInCount = wmsReagentsMapper.selectCount(wrapper);

        // 本月出库
        QueryWrapper wrapper_out = new QueryWrapper();
        wrapper_out.eq("type", OptTypeEnum.OUTBOUND.getCode());
        wrapper_out.between("opt_time", firstDayOfMonth, lastDayOfMonth);
        Long thisMonthOutCount = wmsReagentsMapper.selectCount(wrapper);

        ReagentsStatisticsVO vo = new ReagentsStatisticsVO();
        vo.setCount(count.intValue());
        vo.setInThisMonthTotal(thisMonthInCount.intValue());
        vo.setOutThisMonthTotal(thisMonthOutCount.intValue());
        return ApiResponse.success(vo);
    }

    public ApiResponse addReagents(ReagentsAO reagentsAO) {
        WmsReagents reagents = WmcReagentsStructMapper.INSTANCE.reagentsAOToReagents(reagentsAO);
        log.info("insert wms reagents:{}", JSONUtil.toJsonPrettyStr(reagents));
        wmsReagentsMapper.insert(reagents);
        return ApiResponse.success();
    }

    public ApiResponse<PageResponse> getReagentsList(ReagentsQueryAO reagentsQueryAO) {
        Page<WmsReagents> page = new Page<>(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize());
        QueryWrapper wrapper = new QueryWrapper();
        if (ObjectUtil.isNotEmpty(reagentsQueryAO.getName())) {
            wrapper.like("name", reagentsQueryAO.getName());
        }
        if (ObjectUtil.isNotEmpty(reagentsQueryAO.getCabinetId())) {
            wrapper.eq("cabinetId", reagentsQueryAO.getCabinetId());
        }
        if (ObjectUtil.isNotEmpty(reagentsQueryAO.getOperator())) {
            wrapper.eq("creator", reagentsQueryAO.getOperator());
        }
        if (ObjectUtil.isNotEmpty(reagentsQueryAO.getStartDate()) && ObjectUtil.isNotEmpty(reagentsQueryAO.getEndDate())) {
            wrapper.ge("create_time", reagentsQueryAO.getStartDate());
            wrapper.le("create_time", reagentsQueryAO.getEndDate());
        }
        wrapper.orderByAsc("expiration_date");
        IPage<WmsReagents> reagentsIPage = wmsReagentsMapper.selectPage(page, wrapper);
        if (CollUtil.isEmpty(reagentsIPage.getRecords())) {
            return ApiResponse.success();
        }
        List<WmsReagents> wmsReagentsList = reagentsIPage.getRecords();
        List<ReagentsVO> reagentsVOList = new ArrayList<>();
        for (WmsReagents reagents : wmsReagentsList) {
            LocalDate expirationDate = reagents.getExpirationDate();
            LocalDate currentDate = DateUtils.getCurrentDate();
            long dayBetween = ChronoUnit.DAYS.between(expirationDate, currentDate);
            ReagentsVO reagentsVO = WmcReagentsStructMapper.INSTANCE.reagentsToReagentsVO(reagents);
//            reagentsVO.setIsExp(dayBetween < 30 ? true : false);
            reagentsVOList.add(reagentsVO);
        }
        PageResponse pageResponse = new PageResponse(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize(), reagentsIPage.getTotal(), reagentsVOList);
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
}
