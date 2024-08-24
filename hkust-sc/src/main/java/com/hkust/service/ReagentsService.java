package com.hkust.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.AccessReagentsAO;
import com.hkust.dto.ao.ReagentsAO;
import com.hkust.dto.ao.ReturnReagentsAO;
import com.hkust.dto.ao.query.ReagentsQueryAO;
import com.hkust.dto.vo.InOutEnum;
import com.hkust.dto.vo.ReagentsOptVO;
import com.hkust.dto.vo.ReagentsVO;
import com.hkust.dto.vo.OptReagentsVO;
import com.hkust.entity.*;
import com.hkust.enums.EventTypeEnum;
import com.hkust.mapper.*;
import com.hkust.security.CustomUserDetails;
import com.hkust.struct.structmapper.ReagentsRecordStructMapper;
import com.hkust.struct.structmapper.ReagentsStructMapper;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReagentsService {

    private ReagentsMapper reagentsMapper;

    private ReagentsRecordMapper reagentsRecordMapper;

    private CabinetDoorMapper cabinetDoorMapper;

    private CabinetMapper cabinetMapper;

    private UserMapper userMapper;

    public ApiResponse<PageResponse> getReagentsList(ReagentsQueryAO reagentsQueryAO) {

        Page<Reagents> page = new Page<>(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize());

        QueryWrapper<Reagents> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("cabinet_id", reagentsQueryAO.getCabinetId());
        queryWrapper.eq("in_out", InOutEnum.IN.getCode());
        if (ObjectUtil.isNotEmpty(reagentsQueryAO.getDoorId())) {
            queryWrapper.eq("door_id", reagentsQueryAO.getDoorId());
        }

        if (ObjectUtil.isNotEmpty(reagentsQueryAO.getReagentsName())) {
            queryWrapper.like("name", reagentsQueryAO.getReagentsName());
        }
        if (ObjectUtil.isNotEmpty(reagentsQueryAO.getDueDays())) {
            LocalDate expire_date = DateUtils.getCurrentDate().plusDays(Integer.valueOf(reagentsQueryAO.getDueDays()));
            queryWrapper.le("expire_date", expire_date);
        }
        if (ObjectUtil.isNotEmpty(reagentsQueryAO.getRemainingPercent())) {
            queryWrapper.le("remaining_percent", reagentsQueryAO.getRemainingPercent());
        }

        IPage<Reagents> reagentsIPage = reagentsMapper.selectPage(page, queryWrapper);
        List<Reagents> reagentList = reagentsIPage.getRecords();
        List<ReagentsVO> reagentsVOList = new ArrayList<>();
        for (Reagents reagents : reagentList) {
            ReagentsVO reagentsVO = ReagentsStructMapper.INSTANCE.toReagentsVO(reagents);
            reagentsVOList.add(reagentsVO);
        }
        PageResponse pageResponse = new PageResponse(reagentsQueryAO.getPageNum(), reagentsQueryAO.getPageSize(), reagentsIPage.getTotal(), reagentsVOList);
        return ApiResponse.success(pageResponse);
    }

    public ApiResponse<PageResponse> getReagentsRecordList(AccessReagentsAO accessReagentsAO) {
        CabinetDoor cabinetDoor = cabinetDoorMapper.selectById(accessReagentsAO.getDoorId());
        if (ObjectUtil.isEmpty(cabinetDoor)) {
            return ApiResponse.failed(ReturnCode.CABINET_DOOR_IS_NULL);
        }

        Page<ReagentsRecord> page = new Page<>(accessReagentsAO.getPageNum(), accessReagentsAO.getPageSize());

        QueryWrapper<ReagentsRecord> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(accessReagentsAO.getCabinetId())) {
            queryWrapper.eq("cabinet_id", accessReagentsAO.getCabinetId());
        }
        if (ObjectUtil.isNotEmpty(accessReagentsAO.getDoorId())) {
            queryWrapper.eq("door_id", accessReagentsAO.getDoorId());
        }
        IPage<ReagentsRecord> reagentsRecordIPage = reagentsRecordMapper.selectPage(page, queryWrapper);
        List<ReagentsRecord> reagentsRecordList = reagentsRecordIPage.getRecords();

        List<ReagentsOptVO> reagentsOptVOList = new ArrayList<>();
        for (ReagentsRecord reagentsRecord : reagentsRecordList) {
            ReagentsOptVO reagentsOptVO = ReagentsRecordStructMapper.INSTANCE.toVO(reagentsRecord);
            User user = userMapper.selectByStudentId(reagentsRecord.getOptStudentId());
            reagentsOptVO.setOptUserName(user.getRealName());
            reagentsOptVOList.add(reagentsOptVO);
        }
        PageResponse pageResponse = new PageResponse(accessReagentsAO.getPageNum(), accessReagentsAO.getPageSize(), reagentsRecordIPage.getTotal(), reagentsRecordList);
        return ApiResponse.success(pageResponse);
    }

    public ApiResponse addReagents(ReagentsAO reagentsAO) {

        Cabinet cabinet = cabinetMapper.selectById(reagentsAO.getCabinetId());
        if (ObjectUtil.isEmpty(cabinet)) {
            return ApiResponse.failed(ReturnCode.CABINET_IS_NULL);
        }
        CabinetDoor cabinetDoor = cabinetDoorMapper.selectById(reagentsAO.getDoorId());
        if (ObjectUtil.isEmpty(cabinetDoor)) {
            return ApiResponse.failed(ReturnCode.CABINET_DOOR_IS_NULL);
        }
        if (reagentsAO.getExpireDate().isBefore(DateUtils.getCurrentDate())) {
            return ApiResponse.failed(ReturnCode.REAGENTS_EXPIRED);
        }
        Reagents reagents = ReagentsStructMapper.INSTANCE.toReagents(reagentsAO);
        reagents.setReagentsId(UUIDUtils.generateUUIDWithoutHyphens());
        reagents.setAddDate(DateUtils.getCurrentDateTime());
        reagents.setUpdateDate(DateUtils.getCurrentDateTime());
        reagents.setIsExpire(false);
        reagents.setInOut(InOutEnum.IN.getCode());
        reagents.setOriginalWeight(reagentsAO.getReagentWeight());
        reagents.setRemainingPercent(100);
        try {
            reagentsMapper.insert(reagents);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.success();
    }

    public ApiResponse delReagents(String barCode) {
        QueryWrapper<Reagents> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("barcode", barCode);
        try {
            reagentsMapper.delete(queryWrapper);
        } catch (Exception e) {
            return ApiResponse.failed(ReturnCode.REAGENTS_DELETE_FAILED);
        }
        return ApiResponse.success();
    }

    public ApiResponse takeReagents(String barCode) {
        // 获取操作人员名字
        String realName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        if (ObjectUtil.isEmpty(user)) {
            return ApiResponse.failed(ReturnCode.USER_IS_NULL);
        }
        realName = user.getRealName();
        Reagents reagents = reagentsMapper.selectByBarCode(barCode);
        if (ObjectUtil.isEmpty(reagents)) {
            return ApiResponse.failed(ReturnCode.REAGENTS_IS_NULL);
        }
        if (reagents.getInOut().equals(InOutEnum.OUT.getCode())) {
            return ApiResponse.failed(ReturnCode.REAGENTS_OUT);
        }

        LocalDateTime takeTime = DateUtils.getCurrentDateTime();
        // 添加操作记录到数据库
        this.insertRecord(user.getStudentId(), reagents, takeTime, EventTypeEnum.TAKE, reagents.getCabinetId());

        // 更新试剂状态为离柜
        reagents.setInOut(InOutEnum.OUT.getCode());
        reagentsMapper.updateById(reagents);

        //组装返回
        OptReagentsVO optReagentsVO = new OptReagentsVO();
        optReagentsVO.setRealName(realName);
        optReagentsVO.setTakeTime(takeTime);
        optReagentsVO.setOptType(EventTypeEnum.TAKE.getName());
        return ApiResponse.success(optReagentsVO);
    }


    public ApiResponse returnReagents(ReturnReagentsAO returnReagentsAO) {

        Reagents reagents = reagentsMapper.selectByBarCode(returnReagentsAO.getBarCode());
        if (ObjectUtil.isEmpty(reagents)) {
            return ApiResponse.failed(ReturnCode.REAGENTS_IS_NULL);
        }
        if (!returnReagentsAO.getDoorId().equals(reagents.getDoorId())) {
            return ApiResponse.failed(ReturnCode.REAGENTS_DOOR_MISMATCH);
        }
        if (!reagents.getInOut().equals(InOutEnum.OUT.getCode())) {
            return ApiResponse.failed(ReturnCode.REAGENTS_IN);
        }

        // 更新试剂信息
        reagents.setUpdateDate(DateUtils.getCurrentDateTime());
        reagents.setReagentWeight(returnReagentsAO.getReagentWeight());
        reagents.setBottleWeight(returnReagentsAO.getBottleWeight());
        String studentId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        if (ObjectUtil.isEmpty(user)) {
            return ApiResponse.failed(ReturnCode.USER_IS_NULL);
        }
        studentId = user.getStudentId();
        reagents.setOperator(studentId);
        reagents.setInOut(InOutEnum.IN.getCode());

        // 处理百分比
        double old = Double.parseDouble(reagents.getOriginalWeight());
        double current = Double.parseDouble(returnReagentsAO.getReagentWeight());
        BigDecimal remainingPercent = new BigDecimal(current / old);
        remainingPercent = remainingPercent.setScale(2, RoundingMode.HALF_UP);
        // 定义一个 BigDecimal 对象表示 100
        BigDecimal hundred = new BigDecimal("100");
        remainingPercent = remainingPercent.multiply(hundred);
        reagents.setRemainingPercent(remainingPercent.intValue());

        reagentsMapper.updateById(reagents);

        // 添加操作记录
        this.insertRecord(studentId, reagents, DateUtils.getCurrentDateTime(), EventTypeEnum.RETURN, returnReagentsAO.getDoorId());

        OptReagentsVO optReagentsVO = new OptReagentsVO();
        optReagentsVO.setRealName(user.getRealName());
        optReagentsVO.setTakeTime(DateUtils.getCurrentDateTime());
        optReagentsVO.setOptType(EventTypeEnum.TAKE.getName());

        return ApiResponse.success(optReagentsVO);
    }

    private void insertRecord(String studentId, Reagents reagents, LocalDateTime takeTime, EventTypeEnum eventTypeEnum, String cabinetId) {
        // 添加操作记录到数据库
        ReagentsRecord reagentsRecord = new ReagentsRecord();
        reagentsRecord.setRecordId(UUIDUtils.generateUUIDWithoutHyphens());
        reagentsRecord.setType(eventTypeEnum.getCode());
        reagentsRecord.setDoorId(reagents.getDoorId());
        reagentsRecord.setOptTime(takeTime);
        reagentsRecord.setOptStudentId(studentId);
        reagentsRecord.setBarCode(reagents.getBarCode());
        reagentsRecord.setCabinetId(cabinetId);
        reagentsRecordMapper.insert(reagentsRecord);
    }

    @Autowired
    public void setReagentsMapper(ReagentsMapper reagentsMapper) {
        this.reagentsMapper = reagentsMapper;
    }

    @Autowired
    public void setReagentsRecordMapper(ReagentsRecordMapper reagentsRecordMapper) {
        this.reagentsRecordMapper = reagentsRecordMapper;
    }

    private void getStudentId() {

    }

    @Autowired
    public void setCabinetDoorMapper(CabinetDoorMapper cabinetDoorMapper) {
        this.cabinetDoorMapper = cabinetDoorMapper;
    }

    @Autowired
    public void setCabinetMapper(CabinetMapper cabinetMapper) {
        this.cabinetMapper = cabinetMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
