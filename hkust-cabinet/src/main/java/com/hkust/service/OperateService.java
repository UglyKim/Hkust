package com.hkust.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.OperationAO;
import com.hkust.entity.Event;
import com.hkust.entity.User;
import com.hkust.mapper.EventMapper;
import com.hkust.security.CustomUserDetails;
import com.hkust.struct.structmapper.EventStructMapper;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OperateService {

    private EventMapper eventMapper;

    public ApiResponse optSynchronize(OperationAO operationAO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        if (ObjectUtil.isEmpty(user)) {
            return ApiResponse.failed(ReturnCode.USER_IS_NULL);
        }
//        try {
        Event event = EventStructMapper.INSTANCE.eventAOToEvent(operationAO);
        event.setEventId(UUIDUtils.generateUUIDWithoutHyphens());
        event.setOptDate(DateUtils.getCurrentDateTime());
        event.setOptStudentId(user.getStudentId());
        eventMapper.insert(event);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return ApiResponse.failed("操作数据库失败!");
//        }
        return ApiResponse.success();
    }

    private Event getEvent(OperationAO operationAO) {
        Event event = new Event();
        event.setEventId(UUIDUtils.generateUUIDWithoutHyphens());
        event.setEventType(operationAO.getEventType());
        event.setCabinetId(operationAO.getCabinetId());
        event.setOptDate(DateUtils.getCurrentDateTime());
        return event;
    }

    @Autowired
    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }
}
