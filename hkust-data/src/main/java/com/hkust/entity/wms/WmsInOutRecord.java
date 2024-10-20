package com.hkust.entity.wms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("wms_in_out_record")
public class WmsInOutRecord {

    @TableId("id")
    private String id;

    /**
     * 1入库 2出库
     */
    private String type;

    @TableField("reagents_id")
    private String reagentsId;

    @TableField("reagents_name")
    private String reagentsName;

    @TableField("in_time")
    private LocalDateTime inTime;

    /**
     * 规格
     */
    private String specification;

    private String ghs;

    @TableField("out_time")
    private LocalDateTime outTime;

    @TableField("operatorId")
    private String operator_id;

    private String operator;
}
