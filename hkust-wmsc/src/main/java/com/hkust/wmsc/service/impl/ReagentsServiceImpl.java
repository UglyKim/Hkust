package com.hkust.wmsc.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.hkust.wmsc.dto.ao.InReagentsAO;
import com.hkust.wmsc.dto.ao.OutReagentsAO;
import com.hkust.wmsc.dto.ao.ReagentsQueryAO;
import com.hkust.wmsc.dto.vo.ReagentsVO;
import com.hkust.wmsc.dto.vo.WmsInOutRecordVO;
import com.hkust.wmsc.dto.vo.WmsOptLogVO;
import com.hkust.wmsc.service.ReagentsService;
import com.hkust.wmsc.struct.structmapper.WmsInOutRecordStructMapper;
import com.hkust.wmsc.struct.structmapper.WmsOptLogStructMapper;
import com.hkust.wmsc.struct.structmapper.WmscReagentsStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ReagentsServiceImpl extends ServiceImpl<WmsReagentsMapper, WmsReagents> implements ReagentsService {

    private WmsReagentsMapper wmsReagentsMapper;

    private InOutRecordServiceImpl inOutRecordService;

    private WmsOptLogMapper wmsOptLogMapper;

    public ApiResponse findReagents(String reagentsId) {
        WmsReagents reagents = wmsReagentsMapper.selectById(reagentsId);
        if (ObjectUtil.isNotEmpty(reagents)) {
            return ApiResponse.failed("此试剂不存在");
        }
        ReagentsVO reagentsVO = WmscReagentsStructMapper.INSTANCE.ReagentsToReagentsVO(reagents);
        return ApiResponse.success(reagentsVO);
    }

    public ApiResponse findReagentsList(ReagentsQueryAO reagentsQueryAO) {
        QueryWrapper wrapper = new QueryWrapper();
        if (ObjectUtil.isNotEmpty(reagentsQueryAO.getName())) {
            wrapper.like("name", reagentsQueryAO.getName());
            wrapper.orderByAsc("expiration_date");
        }
        List<WmsReagents> wmsReagentsList = wmsReagentsMapper.selectList(wrapper);

        if (CollUtil.isEmpty(wmsReagentsList)) {
            return ApiResponse.success();
        }
        List<ReagentsVO> reagentsVOList = new ArrayList<>();
        for (WmsReagents reagents : wmsReagentsList) {
            ReagentsVO reagentsVO = WmscReagentsStructMapper.INSTANCE.ReagentsToReagentsVO(reagents);
            reagentsVOList.add(reagentsVO);
        }
        return ApiResponse.success(reagentsVOList);
    }

    public ApiResponse inboundReagents(List<InReagentsAO> inReagentsAOList) {
        List<WmsReagents> wmsReagentsList = new ArrayList<>();
        LocalDateTime currentDateTime = DateUtils.getCurrentDateTime();
        for (InReagentsAO inReagentsAO : inReagentsAOList) {
            WmsReagents wmsReagents = WmscReagentsStructMapper.INSTANCE.InReagentsAOToReagents(inReagentsAO);
            wmsReagents.setId(UUIDUtils.generateUUIDWithoutHyphens());
            wmsReagents.setCreateTime(currentDateTime);
            wmsReagents.setInOut(YNEnum.YES.getCode());
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
        wmsOptLog.setOpt_time(currentDateTime);
        wmsOptLogMapper.insert(wmsOptLog);

        return ApiResponse.success();
    }

    public ApiResponse outboundReagents(List<OutReagentsAO> outReagentsAOListList) {

        List<WmsReagents> wmsReagentsList = new ArrayList<>();
        LocalDateTime currentDateTime = DateUtils.getCurrentDateTime();

        for (OutReagentsAO outReagentsAO : outReagentsAOListList) {
            WmsReagents reagents = wmsReagentsMapper.selectById(outReagentsAO.getReagentsId());
            if (ObjectUtil.isNotEmpty(reagents)) {
                reagents.setInOut(YNEnum.NO.getCode());
                reagents.setUpdateTime(currentDateTime);
                wmsReagentsList.add(reagents);
            }
        }
        updateBatchById(wmsReagentsList);

        List<WmsInOutRecord> wmsInOutRecordList = new ArrayList<>();
        String recordId = UUIDUtils.generateUUIDWithoutHyphens();
        for (WmsReagents reagents : wmsReagentsList) {
            User user = SecurityUtils.getCurrentUser();
            WmsInOutRecord wmsInOutRecord = new WmsInOutRecord();
            wmsInOutRecord.setId("O" + recordId);
            if (ObjectUtil.isNotEmpty(reagents.getGhs())){
                wmsInOutRecord.setGhs(reagents.getGhs());
            }
            wmsInOutRecord.setReagentsName(reagents.getName());
            wmsInOutRecord.setReagentsId(reagents.getId());
            wmsInOutRecord.setOptTime(currentDateTime);
            wmsInOutRecord.setType(OptTypeEnum.OUTBOUND.getCode()); // 出库
            if (ObjectUtil.isNotEmpty(reagents.getSpecification())){
                wmsInOutRecord.setSpecification(reagents.getSpecification()); //规格
            }
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
        wmsOptLog.setOpt_time(currentDateTime);
        wmsOptLogMapper.insert(wmsOptLog);
        return ApiResponse.success();

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
