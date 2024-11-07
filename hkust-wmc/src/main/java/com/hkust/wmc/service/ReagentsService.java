package com.hkust.wmc.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.User;
import com.hkust.entity.wms.WmsInOutRecord;
import com.hkust.entity.wms.WmsOptLog;
import com.hkust.entity.wms.WmsReagents;
import com.hkust.enums.OptTypeEnum;
import com.hkust.mapper.wmsc.WmsInOutRecordMapper;
import com.hkust.mapper.wmsc.WmsOptLogMapper;
import com.hkust.mapper.wmsc.WmsReagentsMapper;
import com.hkust.security.SecurityUtils;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import com.hkust.wmc.dto.PageResponse;
import com.hkust.wmc.dto.ao.AddReagentsAO;
import com.hkust.wmc.dto.ao.AlterReagentsAO;
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

    private WmsOptLogMapper wmsOptLogMapper;

    public ApiResponse alterReagents(AlterReagentsAO alterReagentsAO) {
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

    public ApiResponse getReagentsDetail(String reagentsId) {
        WmsReagents reagents = wmsReagentsMapper.selectById(reagentsId);
        if (ObjectUtil.isEmpty(reagents)) {
            return ApiResponse.failed(ReturnCode.REAGENTS_IS_NULL);
        }
        ReagentsVO reagentsVO = WmcReagentsStructMapper.INSTANCE.reagentsToReagentsVO(reagents);
        log.info("return reagents is:", JSONUtil.toJsonPrettyStr(reagentsVO));
        return ApiResponse.success(reagentsVO);
    }

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

    public ApiResponse addReagents(AddReagentsAO addReagentsAO) {
        WmsReagents reagents1 = wmsReagentsMapper.selectById(addReagentsAO.getReagentsId());
        if (ObjectUtil.isNotEmpty(reagents1)) {
            return ApiResponse.failed(ReturnCode.REAGENTS_IN);
        }
        WmsReagents reagents = WmcReagentsStructMapper.INSTANCE.reagentsAOToReagents(addReagentsAO);
        log.info("insert wms reagents:{}", JSONUtil.toJsonPrettyStr(reagents));
        wmsReagentsMapper.insert(reagents);

        // 添加入库记录
        User user = SecurityUtils.getCurrentUser();
        WmsInOutRecord wmsInOutRecord = new WmsInOutRecord();
        wmsInOutRecord.setId("I" + UUIDUtils.generateUUIDWithoutHyphens());
        wmsInOutRecord.setGhs(reagents.getGhs());
        wmsInOutRecord.setReagentsName(reagents.getName());
        wmsInOutRecord.setReagentsId(reagents.getId());
        wmsInOutRecord.setOptTime(DateUtils.getCurrentDateTime());
        wmsInOutRecord.setType(OptTypeEnum.INBOUND.getCode()); // 入库
        wmsInOutRecord.setSpecification(reagents.getSpecification()); //规格
        wmsInOutRecord.setCount(1);
        // 添加操作人
        wmsInOutRecord.setOperatorId(user.getStudentId());
        wmsInOutRecord.setOperator(user.getRealName());

        wmsInOutRecordMapper.insert(wmsInOutRecord);

        // 添加日志
        WmsOptLog wmsOptLog = new WmsOptLog();
        wmsOptLog.setId(UUIDUtils.generateUUIDWithoutHyphens());
        user = SecurityUtils.getCurrentUser();
        wmsOptLog.setOperatorId(user.getStudentId());
        wmsOptLog.setOperator(user.getUsername());
        wmsOptLog.setType(OptTypeEnum.INBOUND.getCode());
        wmsOptLog.setOptTime(DateUtils.getCurrentDateTime());
        wmsOptLogMapper.insert(wmsOptLog);

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

    @Autowired
    public void setWmsOptLogMapper(WmsOptLogMapper wmsOptLogMapper) {
        this.wmsOptLogMapper = wmsOptLogMapper;
    }
}
