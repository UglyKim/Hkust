package com.hkust.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.ReagentsAO;
import com.hkust.dto.ao.ReturnReagentsAO;
import com.hkust.dto.vo.ReagentsOptVO;
import com.hkust.dto.vo.ReagentsVO;
import com.hkust.dto.vo.TakeReagentsVO;
import com.hkust.entity.*;
import com.hkust.enums.ReagentsOptTypeEnum;
import com.hkust.mapper.CabinetDoorMapper;
import com.hkust.mapper.CabinetMapper;
import com.hkust.mapper.ReagentsMapper;
import com.hkust.mapper.ReagentsRecordMapper;
import com.hkust.security.CustomUserDetails;
import com.hkust.struct.structmapper.ReagentsRecordStructMapper;
import com.hkust.struct.structmapper.ReagentsStructMapper;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReagentsService {

    private ReagentsMapper reagentsMapper;

    private ReagentsRecordMapper reagentsRecordMapper;

    private CabinetDoorMapper cabinetDoorMapper;

    private CabinetMapper cabinetMapper;

    public ApiResponse<PageResponse> getReagentsList(String cabinetId) {

        Page<Reagents> page = new Page<>(1, 20);

        QueryWrapper<Reagents> queryWrapper = new QueryWrapper<>();
        IPage<Reagents> reagentsIPage = reagentsMapper.selectPage(page, queryWrapper);
        List<Reagents> reagentList = reagentsIPage.getRecords();
        List<ReagentsVO> reagentsVOList = new ArrayList<>();
        for (Reagents reagents : reagentList) {
            ReagentsVO reagentsVO = ReagentsStructMapper.INSTANCE.toReagentsVO(reagents);
            reagentsVOList.add(reagentsVO);
        }
        PageResponse pageResponse = new PageResponse(1, 20, reagentsIPage.getTotal(), reagentsVOList);
        return ApiResponse.success(pageResponse);
    }

    public ApiResponse<PageResponse> getReagentsRecordList(String cabinetId) {
        Cabinet cabinet = cabinetMapper.selectById(cabinetId);
        if (ObjectUtil.isEmpty(cabinet)) {
            return ApiResponse.failed(ReturnCode.CABINET_IS_NULL);
        }
        Page<ReagentsRecord> page = new Page<>(1, 20);

        QueryWrapper<ReagentsRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cabinet_id", cabinet);
        IPage<ReagentsRecord> reagentsRecordIPage = reagentsRecordMapper.selectPage(page, queryWrapper);
        List<ReagentsRecord> reagentsRecordList = reagentsRecordIPage.getRecords();

        List<ReagentsOptVO> reagentsOptVOList = new ArrayList<>();
        for (ReagentsRecord reagentsRecord : reagentsRecordList) {
            ReagentsOptVO reagentsOptVO = ReagentsRecordStructMapper.INSTANCE.toVO(reagentsRecord);
            reagentsOptVOList.add(reagentsOptVO);
        }
        PageResponse pageResponse = new PageResponse(1, 20, reagentsRecordIPage.getTotal(), reagentsRecordList);
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

        LocalDateTime takeTime = DateUtils.getCurrentDateTime();
        // 添加操作记录到数据库
        this.insertRecord(user.getStudentId(), reagents, takeTime, ReagentsOptTypeEnum.TAKE);

        //组装返回
        TakeReagentsVO takeReagentsVO = new TakeReagentsVO();
        takeReagentsVO.setRealName(realName);
        takeReagentsVO.setTakeTime(takeTime);
        return ApiResponse.success(takeReagentsVO);
    }


    public ApiResponse returnReagents(ReturnReagentsAO returnReagentsAO) {
        Reagents reagents = reagentsMapper.selectByBarCode(returnReagentsAO.getBarCode());
        if (ObjectUtil.isEmpty(reagents)) {
            return ApiResponse.failed(ReturnCode.REAGENTS_IS_NULL);
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
        reagentsMapper.updateById(reagents);

        // 添加操作记录
        this.insertRecord(studentId, reagents, DateUtils.getCurrentDateTime(), ReagentsOptTypeEnum.RETURN);

        return ApiResponse.success();
    }

    private void insertRecord(String studentId, Reagents reagents, LocalDateTime takeTime, ReagentsOptTypeEnum optTypeEnum) {
        // 添加操作记录到数据库
        ReagentsRecord reagentsRecord = new ReagentsRecord();
        reagentsRecord.setRecordId(UUIDUtils.generateUUIDWithoutHyphens());
        reagentsRecord.setType(optTypeEnum.getCode());
        reagentsRecord.setDoorId(reagents.getDoorId());
        reagentsRecord.setOptTime(takeTime);
        reagentsRecord.setOptStudentId(studentId);
        reagentsRecord.setBarCode(reagents.getBarCode());
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
}
