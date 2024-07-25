package com.hkust.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.ReagentsAO;
import com.hkust.dto.vo.ReagentsOptVO;
import com.hkust.dto.vo.ReagentsVO;
import com.hkust.entity.Reagents;
import com.hkust.entity.ReagentsRecord;
import com.hkust.mapper.ReagentsMapper;
import com.hkust.mapper.ReagentsRecordMapper;
import com.hkust.struct.structmapper.ReagentsRecordStructMapper;
import com.hkust.struct.structmapper.ResponseStructMapper;
import com.hkust.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReagentsService {

    private ReagentsMapper reagentsMapper;

    private ReagentsRecordMapper reagentsRecordMapper;

    public ApiResponse<PageResponse> getReagentsList() {

        Page<Reagents> page = new Page<>(1, 20);

        QueryWrapper<Reagents> queryWrapper = new QueryWrapper<>();
        IPage<Reagents> reagentsIPage = reagentsMapper.selectPage(page, queryWrapper);
        List<Reagents> reagentList = reagentsIPage.getRecords();
        List<ReagentsVO> reagentsVOList = new ArrayList<>();
        for (Reagents reagents : reagentList) {
            ReagentsVO reagentsVO = ResponseStructMapper.INSTANCE.toVO(reagents);
            reagentsVOList.add(reagentsVO);
        }
        PageResponse pageResponse = new PageResponse(1, 20, reagentsIPage.getTotal(), reagentsVOList);
        return ApiResponse.success(pageResponse);
    }

    public ApiResponse<PageResponse> getReagentsRecordList(String doorId) {
        Page<ReagentsRecord> page = new Page<>(1, 20);

        QueryWrapper<ReagentsRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("door_id", doorId);
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
        Reagents reagents = ResponseStructMapper.INSTANCE.toAO(reagentsAO);
        reagents.setReagentsId(UUIDUtils.generateUUIDWithoutHyphens());
        try {
            reagentsMapper.insert(reagents);
            return ApiResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("");
        }
    }

    @Autowired
    public void setReagentsMapper(ReagentsMapper reagentsMapper) {
        this.reagentsMapper = reagentsMapper;
    }

    @Autowired
    public void setReagentsRecordMapper(ReagentsRecordMapper reagentsRecordMapper) {
        this.reagentsRecordMapper = reagentsRecordMapper;
    }
}
