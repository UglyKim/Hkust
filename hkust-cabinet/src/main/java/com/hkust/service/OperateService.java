package com.hkust.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.OperationAO;
import com.hkust.entity.Event;
import com.hkust.mapper.EventMapper;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OperateService {

    private EventMapper eventMapper;

    public ApiResponse optSynchronize(OperationAO operationAO) {
        try {
            eventMapper.insert(getEvent(operationAO));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResponse.failed("操作数据库失败!");
        }
        return ApiResponse.success();
    }

    private Event getEvent(OperationAO operationAO) {
        Event event = new Event();
        event.setEventId(UUIDUtils.generateUUIDWithoutHyphens());
        event.setEventType(operationAO.getEventTypeCode());
        event.setCabinetId(operationAO.getCabinetId());
        event.setOptDate(DateUtils.getCurrentDate());
        return event;
    }

    @Autowired
    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }
}
