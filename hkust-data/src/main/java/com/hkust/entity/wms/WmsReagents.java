package com.hkust.entity.wms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("wms_reagents")
public class WmsReagents {

    private String id;

    @TableField("cas_no")
    private String casNo;

    private String name;

    @TableField("en_name")
    private String enName;

    private String type;

    private String barcode;

    private String brand;

    private String inout;

    private String price;

    @TableField("storage_location")
    private String storageLocation;

    private String  ghs;

    @TableField("research_group")
    private String researchGroup;

    private String creator;

    @TableField("create_time")
    private LocalDateTime createTime;

    private String modifier;

    @TableField("update_time")
   private LocalDateTime updateTime;

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
     *'特殊存储条件'
     */
    @TableField("special_storage_conditions")
    private String specialStorageConditions;

    /**
     *'危险和危害特性类别'
     */
    @TableField("hazard_classification")
    private String hazardClassification;

    /**
     * 到期日
     */
    @TableField("expiration_date")
    private LocalDate expirationDate;
}

