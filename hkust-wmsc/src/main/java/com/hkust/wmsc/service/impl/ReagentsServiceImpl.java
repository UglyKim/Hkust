package com.hkust.wmsc.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.User;
import com.hkust.entity.wms.WmsInOutRecord;
import com.hkust.entity.wms.WmsReagents;
import com.hkust.mapper.wmsc.WmsReagentsMapper;
import com.hkust.security.SecurityUtils;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import com.hkust.wmsc.dto.ao.InReagentsAO;
import com.hkust.wmsc.dto.ao.OutReagentsAO;
import com.hkust.wmsc.dto.ao.ReagentsQueryAO;
import com.hkust.wmsc.dto.vo.ReagentsVO;
import com.hkust.wmsc.service.ReagentsService;
import com.hkust.wmsc.struct.structmapper.WmscReagentsStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReagentsServiceImpl extends ServiceImpl<WmsReagentsMapper, WmsReagents> implements ReagentsService {

    private WmsReagentsMapper wmsReagentsMapper;

    private InOutRecordServiceImpl inOutRecordService;

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
            wmsReagents.setInout("in");
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
            wmsInOutRecord.setInTime(currentDateTime);
            wmsInOutRecord.setType("1"); // 入库
            wmsInOutRecord.setSpecification(reagents.getSpecification()); //规格
            // 添加操作人
            wmsInOutRecord.setOperator_id(user.getUserId());
            wmsInOutRecord.setOperator(user.getRealName());
            wmsInOutRecordList.add(wmsInOutRecord);
        }
        inOutRecordService.saveBatch(wmsInOutRecordList);
        // TODO: 记录日志
        return ApiResponse.success();
    }

    public ApiResponse outboundReagents(List<OutReagentsAO> outReagentsAOListList) {

        List<WmsReagents> wmsReagentsList = new ArrayList<>();
        LocalDateTime currentDateTime = DateUtils.getCurrentDateTime();

        for (OutReagentsAO outReagentsAO : outReagentsAOListList) {
            WmsReagents wmsReagents = WmscReagentsStructMapper.INSTANCE.OutReagentsAOToReagents(outReagentsAO);
            wmsReagents.setInout("out");
            wmsReagents.setUpdateTime(currentDateTime);
            wmsReagentsList.add(wmsReagents);
        }
        updateBatchById(wmsReagentsList);

        List<WmsInOutRecord> wmsInOutRecordList = new ArrayList<>();
        String recordId = UUIDUtils.generateUUIDWithoutHyphens();
        for (WmsReagents reagents : wmsReagentsList) {
            User user = SecurityUtils.getCurrentUser();
            WmsInOutRecord wmsInOutRecord = new WmsInOutRecord();
            wmsInOutRecord.setId("O" + recordId);
            wmsInOutRecord.setGhs(reagents.getGhs());
            wmsInOutRecord.setReagentsName(reagents.getName());
            wmsInOutRecord.setReagentsId(reagents.getId());
            wmsInOutRecord.setInTime(currentDateTime);
            wmsInOutRecord.setType("2"); // 出库
            wmsInOutRecord.setSpecification(reagents.getSpecification()); //规格
            // 添加操作人
            wmsInOutRecord.setOperator_id(user.getUserId());
            wmsInOutRecord.setOperator(user.getRealName());
            wmsInOutRecordList.add(wmsInOutRecord);
        }
        inOutRecordService.saveBatch(wmsInOutRecordList);
        // TODO: 记录日志
        return ApiResponse.success();

    }

    public ApiResponse getReagentsInfo(String reagentsId) {
        log.info("received reagentsId:{}", reagentsId);
        WmsReagents reagents = wmsReagentsMapper.selectById(reagentsId);
        Assert.notNull(reagents);

        return null;
    }

    @Autowired
    public void setWmsReagentsMapper1(WmsReagentsMapper wmsReagentsMapper1) {
        this.wmsReagentsMapper = wmsReagentsMapper1;
    }

    @Autowired
    public void setInOutRecordService(InOutRecordServiceImpl inOutRecordService) {
        this.inOutRecordService = inOutRecordService;
    }
}
