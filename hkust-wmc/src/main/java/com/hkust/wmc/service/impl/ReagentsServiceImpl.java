package com.hkust.wmc.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
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
import com.hkust.mapper.wmsc.WmsInOutRecordMapper;
import com.hkust.mapper.wmsc.WmsOptLogMapper;
import com.hkust.mapper.wmsc.WmsReagentsMapper;
import com.hkust.security.SecurityUtils;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import com.hkust.wmc.dto.PageResponse;
import com.hkust.wmc.dto.ao.InReagentsAO;
import com.hkust.wmc.dto.ao.AlterReagentsAO;
import com.hkust.wmc.dto.ao.ReagentsQueryAO;
import com.hkust.wmc.dto.vo.ReagentsStatisticsVO;
import com.hkust.wmc.dto.vo.ReagentsVO;
import com.hkust.wmc.service.ReagentsService;
import com.hkust.wmc.struct.structmapper.WmcReagentsStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ReagentsServiceImpl extends ServiceImpl<WmsReagentsMapper, WmsReagents> implements ReagentsService {

    private WmsReagentsMapper wmsReagentsMapper;

    private WmsInOutRecordMapper wmsInOutRecordMapper;

    private WmsOptLogMapper wmsOptLogMapper;

    private InOutRecordServiceImpl inOutRecordService;

    public ApiResponse<Void> alterReagents(AlterReagentsAO alterReagentsAO) {
        WmsReagents reagents = wmsReagentsMapper.selectById(alterReagentsAO.getReagentsId());
        if (ObjectUtil.isEmpty(reagents)) {
            return ApiResponse.failed(ReturnCode.REAGENTS_IS_NULL);
        }
        UpdateChainWrapper<WmsReagents> wrapper = new UpdateChainWrapper<>(wmsReagentsMapper);
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getBarcode())) {
            wrapper.set("barcode", alterReagentsAO.getBarcode());
        }
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getBrand())) {
            wrapper.set("brand", alterReagentsAO.getBrand());
        }
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getCasNo())) {
            wrapper.set("cas_no", alterReagentsAO.getCasNo());
        }
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getName())) {
            wrapper.set("name", alterReagentsAO.getName());
        }
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getEnName())) {
            wrapper.set("en_name", alterReagentsAO.getEnName());
        }
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getSpecification())) {
            wrapper.set("specification", alterReagentsAO.getStorageLocation());
        }
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getGhs())) {
            wrapper.set("ghs", alterReagentsAO.getGhs());
        }
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getPrice())) {
            wrapper.set("price", alterReagentsAO.getPrice());
        }
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getPhysicalState())) {
            wrapper.set("physical_state", alterReagentsAO.getPhysicalState());
        }
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getResearchGroup())) {
            wrapper.set("research_group", alterReagentsAO.getResearchGroup());
        }
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getSpecialStorageConditions())) {
            wrapper.set("special_storage_conditions", alterReagentsAO.getSpecialStorageConditions());
        }
        if (ObjectUtil.isNotEmpty(alterReagentsAO.getExpirationDate())) {
            wrapper.set("expiration_date", alterReagentsAO.getExpirationDate());
        }
        try {
            wrapper.update();
        } catch (Exception e) {
            log.error("update cabinet_info failed!");
            return ApiResponse.failed(ReturnCode.DB_UPDATE_ERROR);
        }
        return ApiResponse.success();
    }

    public ApiResponse<ReagentsVO> getReagentsDetail(String reagentsId) {
        WmsReagents reagents = wmsReagentsMapper.selectById(reagentsId);
        if (ObjectUtil.isEmpty(reagents)) {
            return ApiResponse.failed(ReturnCode.REAGENTS_IS_NULL);
        }
        ReagentsVO reagentsVO = WmcReagentsStructMapper.INSTANCE.reagentsToReagentsVO(reagents);
        if (reagents.getExpirationDate().compareTo(LocalDate.now()) >= 0) {
            reagentsVO.setIsExp(false);
        } else {
            reagentsVO.setIsExp(true);
        }
        log.info("return reagents is:{}", JSONUtil.toJsonPrettyStr(reagentsVO));
        return ApiResponse.success(reagentsVO);
    }

    public ApiResponse<ReagentsStatisticsVO> reagentsStat() {
        // 当前总库存
        QueryWrapper<WmsInOutRecord> wrapper = new QueryWrapper<>();
        Long count = wmsInOutRecordMapper.selectCount(wrapper);
        // 本月出库
        QueryWrapper<WmsReagents> wrapper_in = new QueryWrapper<>();
        LocalDate today = LocalDate.now(); // 当前日期
        LocalDate firstDayOfMonth = today.withDayOfMonth(1); // 当月第一天
        LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth()); // 当月最后一天
        wrapper_in.eq("type", OptTypeEnum.INBOUND.getCode());
        wrapper_in.between("opt_time", firstDayOfMonth, lastDayOfMonth);
        Long thisMonthInCount = wmsReagentsMapper.selectCount(wrapper_in);

        // 本月出库
        QueryWrapper<WmsReagents> wrapper_out = new QueryWrapper<>();
        wrapper_out.eq("type", OptTypeEnum.OUTBOUND.getCode());
        wrapper_out.between("opt_time", firstDayOfMonth, lastDayOfMonth);
        Long thisMonthOutCount = wmsReagentsMapper.selectCount(wrapper_out);

        ReagentsStatisticsVO vo = new ReagentsStatisticsVO();
        vo.setCount(count.intValue());
        vo.setInThisMonthTotal(thisMonthInCount.intValue());
        vo.setOutThisMonthTotal(thisMonthOutCount.intValue());
        return ApiResponse.success(vo);
    }

    public ApiResponse<String> addReagents(List<InReagentsAO> inReagentsAOList) {
        List<WmsReagents> inFailedReagentsList = new ArrayList<>();
        LocalDateTime currentDateTime = DateUtils.getCurrentDateTime();
        List<String> failedReagentsIDList = new ArrayList<>();
        for (InReagentsAO inReagentsAO : inReagentsAOList) {
            WmsReagents reagents = wmsReagentsMapper.selectById(inReagentsAO.getReagentsId());
            if (ObjectUtil.isEmpty(reagents)) {
                WmsReagents wmsReagents = WmcReagentsStructMapper.INSTANCE.reagentsAOToReagents(inReagentsAO);
                wmsReagents.setId(inReagentsAO.getReagentsId());
                wmsReagents.setCreateTime(currentDateTime);
                wmsReagents.setInOut(YNEnum.YES.getCode());
                wmsReagents.setCabinetId(inReagentsAO.getCabinetId());
                inFailedReagentsList.add(wmsReagents);
            } else {
                failedReagentsIDList.add(reagents.getId());
            }
        }
        if (CollUtil.isEmpty(inFailedReagentsList) && CollUtil.isNotEmpty(failedReagentsIDList)) {
            return ApiResponse.failed("试剂[" + String.join(",", failedReagentsIDList) + "]已经存在，请不要重复添加");
        }
        super.saveBatch(inFailedReagentsList);

        // 添加入库记录
        List<WmsInOutRecord> wmsInOutRecordList = new ArrayList<>();
        String recordId = UUIDUtils.generateUUIDWithoutHyphens();
        for (WmsReagents reagents : inFailedReagentsList) {
            User user = SecurityUtils.getCurrentUser();
            WmsInOutRecord wmsInOutRecord = new WmsInOutRecord();
            wmsInOutRecord.setId("I" + recordId);
            if (ObjectUtil.isNotEmpty(reagents.getGhs())) {
                wmsInOutRecord.setGhs(reagents.getGhs());
            }
            wmsInOutRecord.setReagentsName(reagents.getName());
            wmsInOutRecord.setReagentsId(reagents.getId());
            wmsInOutRecord.setOptTime(currentDateTime);
            wmsInOutRecord.setType(OptTypeEnum.INBOUND.getCode()); // 入库
            wmsInOutRecord.setSpecification(reagents.getSpecification()); //规格
            wmsInOutRecord.setCount(1);
            // 添加操作人
            wmsInOutRecord.setOperatorId(Optional.ofNullable(user).map(User::getStudentId).orElse(null));
            wmsInOutRecord.setOperator(Optional.ofNullable(user).map(User::getUsername).orElse(null));
            wmsInOutRecordList.add(wmsInOutRecord);
        }
        inOutRecordService.saveBatch(wmsInOutRecordList);

        // 添加日志
        WmsOptLog wmsOptLog = new WmsOptLog();
        wmsOptLog.setId(UUIDUtils.generateUUIDWithoutHyphens());
        User user = SecurityUtils.getCurrentUser();
        wmsOptLog.setOperatorId(Optional.ofNullable(user).map(User::getStudentId).orElse(null));
        wmsOptLog.setOperator(Optional.ofNullable(user).map(User::getUsername).orElse(null));
        wmsOptLog.setType(OptTypeEnum.INBOUND.getCode());
        wmsOptLog.setOptTime(currentDateTime);
        wmsOptLogMapper.insert(wmsOptLog);

        if (CollUtil.isEmpty(failedReagentsIDList)) {
            return ApiResponse.success();
        }
        return ApiResponse.success("试剂[" + String.join(",", failedReagentsIDList) + "]已经存在，请不要重复添加");
    }

    public ApiResponse<PageResponse<ReagentsVO>> getReagentsList(ReagentsQueryAO reagentsQueryAO) {
        Page<WmsReagents> page = new Page<>(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize());
        QueryWrapper<WmsReagents> wrapper = new QueryWrapper<>();
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
            ReagentsVO reagentsVO = WmcReagentsStructMapper.INSTANCE.reagentsToReagentsVO(reagents);
            if (reagents.getExpirationDate().compareTo(LocalDate.now()) >= 0) {
                reagentsVO.setIsExp(false);
            } else {
                reagentsVO.setIsExp(true);
            }
            reagentsVOList.add(reagentsVO);
        }
        PageResponse<ReagentsVO> pageResponse = new PageResponse<>(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize(), reagentsIPage.getTotal(), reagentsVOList);
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
    public void setWmsOptLogMapper(WmsOptLogMapper wmsOptLogMapper) {
        this.wmsOptLogMapper = wmsOptLogMapper;
    }

    @Autowired
    public void setInOutRecordService(InOutRecordServiceImpl inOutRecordService) {
        this.inOutRecordService = inOutRecordService;
    }
}
