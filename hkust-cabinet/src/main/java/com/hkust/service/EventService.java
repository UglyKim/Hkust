package com.hkust.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.EventQueryAO;
import com.hkust.dto.vo.EventVO;
import com.hkust.entity.Event;
import com.hkust.mapper.EventMapper;
import com.hkust.structmapper.EventStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private EventMapper eventMapper;

    public ApiResponse<PageResponse> getEventList(EventQueryAO eventQueryAO) {
        Page<Event> page = new Page<>(eventQueryAO.getPageNum(), 20);
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        // TODO: 查询条件
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


    @Autowired
    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }
}
