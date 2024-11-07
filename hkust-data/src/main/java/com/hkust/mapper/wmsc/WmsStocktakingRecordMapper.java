package com.hkust.mapper.wmsc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkust.entity.wms.WmsStocktakingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WmsStocktakingRecordMapper extends BaseMapper<WmsStocktakingRecordMapper> {

    void batchInsertStocktakingRecord(@Param("list") List<WmsStocktakingRecord> records);

}
