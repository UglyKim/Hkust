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

    /**
     * 规格
     */
    private String specification;

    private String ghs;

    /**
     * 出入库时间
     */
    @TableField("opt_time")
    private LocalDateTime optTime;

    @TableField("operator_id")
    private String operatorId;

    private String operator;

    @TableField("cabinet_id")
    private String cabinetId;
}
