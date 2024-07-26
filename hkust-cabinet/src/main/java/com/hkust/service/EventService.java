package com.hkust.service;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.EventQueryAO;
import com.hkust.dto.vo.EventVO;
import com.hkust.entity.Event;
import com.hkust.enums.EventTypeEnum;
import com.hkust.mapper.EventMapper;
import com.hkust.struct.structmapper.EventStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private EventMapper eventMapper;

    /**
     * 指查询存取记录
     * @param eventQueryAO
     * @return
     */
    public ApiResponse<PageResponse> getEventList(EventQueryAO eventQueryAO) {
        int pageNum = 1;
        if (ObjUtil.isNotEmpty(eventQueryAO.getPageNum())) {
            pageNum = eventQueryAO.getPageNum();
        }
        Page<Event> page = new Page<>(pageNum, 20);
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        // TODO: 查询条件
        queryWrapper.eq("type", EventTypeEnum.GET.name()).or().eq("type", EventTypeEnum.RETURN.name()).orderByDesc("opt_date");

//        if (eventName != null && !eventName.isEmpty()) {
//            queryWrapper.like("event_name", eventName);
//        }
        IPage<Event> eventIPage = eventMapper.selectPage(page, queryWrapper);
        List<Event> eventList = eventIPage.getRecords();
        List<EventVO> eventVOList = new ArrayList<>();
        for (Event event : eventList) {
            EventVO eventVO = EventStructMapper.INSTANCE.toVO(event);
            eventVOList.add(eventVO);
        }

        PageResponse pageResponse = new PageResponse<EventVO>(eventQueryAO.getPageNum(), 20, eventIPage.getTotal(), eventVOList);
        return ApiResponse.success(pageResponse);
    }

    /**
     * 查询所有记录
     * @param eventQueryAO
     * @return
     */
    public ApiResponse<PageResponse> getAllEventList(EventQueryAO eventQueryAO) {
        int pageNum = 1;
        if (ObjUtil.isNotEmpty(eventQueryAO.getPageNum())) {
            pageNum = eventQueryAO.getPageNum();
        }
        Page<Event> page = new Page<>(pageNum, 20);
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        IPage<Event> eventIPage = eventMapper.selectPage(page, queryWrapper);
        List<Event> eventList = eventIPage.getRecords();
        List<EventVO> eventVOList = new ArrayList<>();
        for (Event event : eventList) {
            EventVO eventVO = EventStructMapper.INSTANCE.toVO(event);
//            eventVO.setUserName();
            eventVOList.add(eventVO);
        }

        PageResponse pageResponse = new PageResponse<EventVO>(eventQueryAO.getPageNum(), 20, eventIPage.getTotal(), eventVOList);
        return ApiResponse.success(pageResponse);
    }


    @Autowired
    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }
}
