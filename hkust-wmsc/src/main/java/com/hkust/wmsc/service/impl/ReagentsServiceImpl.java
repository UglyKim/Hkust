package com.hkust.wmsc.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.User;
import com.hkust.entity.wms.WmsInOutRecord;
import com.hkust.entity.wms.WmsOptLog;
import com.hkust.entity.wms.WmsReagents;
import com.hkust.enums.OptTypeEnum;
import com.hkust.enums.YNEnum;
import com.hkust.mapper.wmsc.WmsOptLogMapper;
import com.hkust.mapper.wmsc.WmsReagentsMapper;
import com.hkust.security.SecurityUtils;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import com.hkust.wmsc.dto.PageResponse;
import com.hkust.wmsc.dto.ao.*;
import com.hkust.wmsc.dto.vo.ReagentsVO;
import com.hkust.wmsc.dto.vo.StocktakingVO;
import com.hkust.wmsc.dto.vo.WmsInOutRecordVO;
import com.hkust.wmsc.service.ReagentsService;
import com.hkust.wmsc.struct.structmapper.WmsInOutRecordStructMapper;
import com.hkust.wmsc.struct.structmapper.WmscReagentsStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReagentsServiceImpl extends ServiceImpl<WmsReagentsMapper, WmsReagents> implements ReagentsService {

    private WmsReagentsMapper wmsReagentsMapper;

    private InOutRecordServiceImpl inOutRecordService;

    private WmsOptLogMapper wmsOptLogMapper;

    public ApiResponse findReagents(String reagentsId) {
        WmsReagents reagents = wmsReagentsMapper.selectById(reagentsId);
        if (ObjectUtil.isNotEmpty(reagents)) {
            return ApiResponse.failed(ReturnCode.REAGENTS_IS_NULL);
        }
        ReagentsVO reagentsVO = WmscReagentsStructMapper.INSTANCE.ReagentsToReagentsVO(reagents);
        return ApiResponse.success(reagentsVO);
    }

    public ApiResponse<PageResponse> findReagentsList(ReagentsQueryAO reagentsQueryAO) {

        Page<WmsReagents> page = new Page<>(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize());
        QueryWrapper wrapper = new QueryWrapper();
        if (ObjectUtil.isNotEmpty(reagentsQueryAO.getName())) {
            wrapper.like("name", reagentsQueryAO.getName());
        }
        wrapper.gt("expiration_date", DateUtils.getCurrentDate());
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
            ReagentsVO reagentsVO = WmscReagentsStructMapper.INSTANCE.ReagentsToReagentsVO(reagents);
            reagentsVO.setIsExp(dayBetween < 30 ? true : false);
            reagentsVOList.add(reagentsVO);
        }
        // 重新排序
        reagentsVOList.sort(Comparator.comparing((ReagentsVO vo) -> !vo.getIsExp()
        ).thenComparing(vo -> vo.getExpirationDate()));
        PageResponse pageResponse = new PageResponse(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize(), reagentsIPage.getTotal(), reagentsVOList);
        return ApiResponse.success(pageResponse);
    }

    public ApiResponse inboundReagents(List<InReagentsAO> inReagentsAOList) {
        List<WmsReagents> wmsReagentsList = new ArrayList<>();
        LocalDateTime currentDateTime = DateUtils.getCurrentDateTime();
        List<String> failedReagentsList = new ArrayList<>();
        for (InReagentsAO inReagentsAO : inReagentsAOList) {
            WmsReagents reagents = wmsReagentsMapper.selectById(inReagentsAO.getReagentsId());
            if (ObjectUtil.isNotEmpty(reagents)) {
                failedReagentsList.add(reagents.getId());
                continue;
            }
            WmsReagents wmsReagents = WmscReagentsStructMapper.INSTANCE.InReagentsAOToReagents(inReagentsAO);
            wmsReagents.setId(inReagentsAO.getReagentsId());
            wmsReagents.setCreateTime(currentDateTime);
            wmsReagents.setInOut(YNEnum.YES.getCode());
            wmsReagents.setCabinetId(inReagentsAO.getCabinetId());
            wmsReagentsList.add(wmsReagents);
        }
        super.saveBatch(wmsReagentsList);

        List<WmsInOutRecord> wmsInOutRecordList = new ArrayList<>();
        String recordId = UUIDUtils.generateUUIDWithoutHyphens();
        for (WmsReagents reagents : wmsReagentsList) {
            User user = SecurityUtils.getCurrentUser();
            WmsInOutRecord wmsInOutRecord = new WmsInOutRecord();
            wmsInOutRecord.setId("I" + recordId);
            wmsInOutRecord.setGhs(reagents.getGhs());
            wmsInOutRecord.setReagentsName(reagents.getName());
            wmsInOutRecord.setReagentsId(reagents.getId());
            wmsInOutRecord.setOptTime(currentDateTime);
            wmsInOutRecord.setType(OptTypeEnum.INBOUND.getCode()); // 入库
            wmsInOutRecord.setSpecification(reagents.getSpecification()); //规格
            wmsInOutRecord.setCount(1);
            // 添加操作人
            wmsInOutRecord.setOperatorId(user.getUserId());
            wmsInOutRecord.setOperator(user.getRealName());
            wmsInOutRecordList.add(wmsInOutRecord);
        }
        inOutRecordService.saveBatch(wmsInOutRecordList);
        // 添加日志
        WmsOptLog wmsOptLog = new WmsOptLog();
        wmsOptLog.setId(UUIDUtils.generateUUIDWithoutHyphens());
        User user = SecurityUtils.getCurrentUser();
        wmsOptLog.setOperatorId(user.getUserId());
        wmsOptLog.setOperator(user.getRealName());
        wmsOptLog.setType(OptTypeEnum.INBOUND.getCode());
        wmsOptLog.setOptTime(currentDateTime);
        wmsOptLogMapper.insert(wmsOptLog);

        return ApiResponse.success(failedReagentsList);
    }

    public ApiResponse outboundReagents(List<OutReagentsAO> outReagentsAOListList) {
        List<String> outFailedReagentsList = new ArrayList<>();
        List<WmsReagents> wmsReagentsList = new ArrayList<>();
        LocalDateTime currentDateTime = DateUtils.getCurrentDateTime();
        for (OutReagentsAO outReagentsAO : outReagentsAOListList) {
            WmsReagents reagents = wmsReagentsMapper.selectById(outReagentsAO.getReagentsId());
            if (ObjectUtil.isNotEmpty(reagents) && reagents.getInOut() == YNEnum.NO.getCode()) {
                reagents.setInOut(YNEnum.NO.getCode());
                reagents.setUpdateTime(currentDateTime);
                wmsReagentsList.add(reagents);
            } else {
                outFailedReagentsList.add(reagents.getId());
            }
        }
        updateBatchById(wmsReagentsList);

        List<WmsInOutRecord> wmsInOutRecordList = new ArrayList<>();
        String recordId = UUIDUtils.generateUUIDWithoutHyphens();
        for (WmsReagents reagents : wmsReagentsList) {
            User user = SecurityUtils.getCurrentUser();
            WmsInOutRecord wmsInOutRecord = new WmsInOutRecord();
            wmsInOutRecord.setId("O" + recordId);
            if (ObjectUtil.isNotEmpty(reagents.getGhs())) {
                wmsInOutRecord.setGhs(reagents.getGhs());
            }
            wmsInOutRecord.setReagentsName(reagents.getName());
            wmsInOutRecord.setReagentsId(reagents.getId());
            wmsInOutRecord.setOptTime(currentDateTime);
            wmsInOutRecord.setType(OptTypeEnum.OUTBOUND.getCode()); // 出库
            if (ObjectUtil.isNotEmpty(reagents.getSpecification())) {
                wmsInOutRecord.setSpecification(reagents.getSpecification()); //规格
            }
            wmsInOutRecord.setCabinetId(reagents.getCabinetId());
            // 添加操作人
            wmsInOutRecord.setOperatorId(user.getUserId());
            wmsInOutRecord.setOperator(user.getRealName());
            wmsInOutRecordList.add(wmsInOutRecord);
        }
        inOutRecordService.saveBatch(wmsInOutRecordList);

        // 添加日志
        WmsOptLog wmsOptLog = new WmsOptLog();
        wmsOptLog.setId(UUIDUtils.generateUUIDWithoutHyphens());
        User user = SecurityUtils.getCurrentUser();
        wmsOptLog.setOperatorId(user.getUserId());
        wmsOptLog.setOperator(user.getRealName());
        wmsOptLog.setType(OptTypeEnum.OUTBOUND.getCode());
        wmsOptLog.setOptTime(currentDateTime);
        wmsOptLogMapper.insert(wmsOptLog);

        return ApiResponse.success(outFailedReagentsList);

    }

    public ApiResponse getInOutboundReagentsDetail(String reagentsId) {
        WmsReagents reagents = wmsReagentsMapper.selectById(reagentsId);
        Assert.notNull(reagents);
        ReagentsVO reagentsVO = WmscReagentsStructMapper.INSTANCE.ReagentsToReagentsVO(reagents);
        return ApiResponse.success(reagentsVO);
    }

    public ApiResponse getLogList() {
        List<WmsInOutRecord> optLogList = inOutRecordService.getLogs();
        if (CollUtil.isNotEmpty(optLogList)) {
            List<WmsInOutRecordVO> wmsOptLogVOList = new ArrayList<>();
            for (WmsInOutRecord wmsInOutRecord : optLogList) {
                WmsInOutRecordVO wmsInOutRecordVO = WmsInOutRecordStructMapper.INSTANCE.WmsInOutRecordToWmsInOutRecordVO(wmsInOutRecord);
                wmsOptLogVOList.add(wmsInOutRecordVO);
            }
            return ApiResponse.success(wmsOptLogVOList);
        }
        return ApiResponse.success();
    }

    public ApiResponse<PageResponse> getExpReagentsList(ExpReagentsQueryAO expReagentsQueryAO) {
        QueryWrapper<WmsReagents> wrapper = new QueryWrapper<>();
        LocalDate conditionDate = DateUtils.getCurrentDate().plusDays(30);
        wrapper.le("expiration_date", conditionDate);
        wrapper.orderByAsc("expiration_date");
        Page page = new Page(expReagentsQueryAO.getPageNum(), expReagentsQueryAO.getPageSize());
        IPage iPage = wmsReagentsMapper.selectPage(page, wrapper);
        if (CollUtil.isEmpty(iPage.getRecords())) {
            return ApiResponse.success();
        }
        List<ReagentsVO> reagentsVOList = new ArrayList<>();
        List<WmsReagents> wmsReagentsList = iPage.getRecords();
        for (WmsReagents reagents : wmsReagentsList) {
            ReagentsVO reagentsVO = WmscReagentsStructMapper.INSTANCE.ReagentsToReagentsVO(reagents);
            reagentsVOList.add(reagentsVO);
        }
        PageResponse pageResponse = new PageResponse(expReagentsQueryAO.getPageNum(), expReagentsQueryAO.getPageSize(), iPage.getTotal(), reagentsVOList);
        return ApiResponse.success(pageResponse);
    }

    public ApiResponse<PageResponse> stocktakingReagents(StocktakingAO stocktakingAO) {

        Long total = wmsReagentsMapper.selectCount(new QueryWrapper<>());
        LocalDate startDate = stocktakingAO.getStartDate();
        LocalDate endDate = stocktakingAO.getEndDate(); // 当前时间
        int limit = stocktakingAO.getPageSize(); // 每页记录数
        int offset = stocktakingAO.getPageSize() * (stocktakingAO.getPageNum() - 1); // 起始位置

        List<Map<String, Object>> list = wmsReagentsMapper.selectReagentsGroupedByNameAndSpecification(startDate, endDate, limit, offset);
        if (CollUtil.isEmpty(list)) {
            return ApiResponse.success();
        }
        List<StocktakingVO> stocktakingVOList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            StocktakingVO vo = new StocktakingVO();
            String name = (String) map.get("name");
            String specification = (String) map.get("specification");
            Integer totalCount = (Integer) map.get("total_count"); // 确保类型匹配
            System.out.println("Name: " + name + ", Specification: " + specification + ", Total Count: " + totalCount);
            vo.setName(name);
            vo.setSpecification(specification);
            vo.setTotalCount(String.valueOf(totalCount));
            stocktakingVOList.add(vo);
        }
        PageResponse pageResponse = new PageResponse(stocktakingAO.getPageNum(), stocktakingAO.getPageSize(), total, stocktakingVOList);
        return ApiResponse.success(pageResponse);
    }

    @Autowired
    public void setWmsReagentsMapper1(WmsReagentsMapper wmsReagentsMapper1) {
        this.wmsReagentsMapper = wmsReagentsMapper1;
    }

    @Autowired
    public void setInOutRecordService(InOutRecordServiceImpl inOutRecordService) {
        this.inOutRecordService = inOutRecordService;
    }

    @Autowired
    public void setWmsOptLogMapper(WmsOptLogMapper wmsOptLogMapper) {
        this.wmsOptLogMapper = wmsOptLogMapper;
    }

    @Autowired
    public void setWmsReagentsMapper(WmsReagentsMapper wmsReagentsMapper) {
        this.wmsReagentsMapper = wmsReagentsMapper;
    }
}
