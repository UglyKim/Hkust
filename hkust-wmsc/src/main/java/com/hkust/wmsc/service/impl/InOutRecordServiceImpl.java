package com.hkust.wmsc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkust.entity.wms.WmsInOutRecord;
import com.hkust.mapper.wmsc.WmsInOutRecordMapper;
import com.hkust.wmsc.service.WmsInOutRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InOutRecordServiceImpl extends ServiceImpl<WmsInOutRecordMapper, WmsInOutRecord> implements WmsInOutRecordService {


}
