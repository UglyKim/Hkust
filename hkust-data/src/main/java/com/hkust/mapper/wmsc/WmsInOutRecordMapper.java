package com.hkust.mapper.wmsc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkust.entity.wms.WmsInOutRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WmsInOutRecordMapper extends BaseMapper<WmsInOutRecord> {
    List<Map<Object, Object>> selectInventorySummary();
}
