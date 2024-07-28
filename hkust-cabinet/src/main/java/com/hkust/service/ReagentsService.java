package com.hkust.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.ReagentsAO;
import com.hkust.dto.vo.ReagentsOptVO;
import com.hkust.dto.vo.ReagentsVO;
import com.hkust.entity.Cabinet;
import com.hkust.entity.CabinetDoor;
import com.hkust.entity.Reagents;
import com.hkust.entity.ReagentsRecord;
import com.hkust.mapper.CabinetDoorMapper;
import com.hkust.mapper.CabinetMapper;
import com.hkust.mapper.ReagentsMapper;
import com.hkust.mapper.ReagentsRecordMapper;
import com.hkust.struct.structmapper.ReagentsRecordStructMapper;
import com.hkust.struct.structmapper.ReagentsStructMapper;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReagentsService {

    private ReagentsMapper reagentsMapper;

    private ReagentsRecordMapper reagentsRecordMapper;

    private CabinetDoorMapper cabinetDoorMapper;

    private CabinetMapper cabinetMapper;
    private QueryWrapper<Reagents> queryWrapper;

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

    @Autowired
    public void setReagentsMapper(ReagentsMapper reagentsMapper) {
        this.reagentsMapper = reagentsMapper;
    }

    @Autowired
    public void setReagentsRecordMapper(ReagentsRecordMapper reagentsRecordMapper) {
        this.reagentsRecordMapper = reagentsRecordMapper;
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
