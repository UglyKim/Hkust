package com.hkust.entity.wms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("wms_reagents")
public class WmsReagents {

    @TableId
    private String id;

    @TableField("cas_no")
    private String casNo;

    private String name;

    @TableField("en_name")
    private String enName;

    private String type;

    private String barcode;

    private String brand;

    @TableField("in_out")
    private String inOut;

    private String price;

    @TableField("storage_location")
    private String storageLocation;

    private String ghs;

    @TableField("research_group")
    private String researchGroup;

    private String creator;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("modified_by")
    private String modifiedBy;

    @TableField("modified_time")
    private LocalDateTime modifiedTime;

    private String state;

    /**
     * 规格
     */
    private String specification;

    /**
     * 物理状态
     */
    @TableField("physical_state")
    private String physicalState;

    /**
     * '特殊存储条件'
     */
    @TableField("special_storage_conditions")
    private String specialStorageConditions;

    /**
     * '危险和危害特性类别'
     */
    @TableField("hazard_classification")
    private String hazardClassification;

    /**
     * 到期日
     */
    @TableField("expiration_date")
    private LocalDate expirationDate;

    @TableField("cabinet_id")
    private String cabinetId;

    /**
     * 危险品属性 1 易燃 2 易爆
     */
    @TableField("hazard_props")
    private String hazardProps;
}

