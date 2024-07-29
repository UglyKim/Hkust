package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("hkust_reagents")
public class Reagents {

    @TableId("reagents_id")
    private String reagentsId;

    @TableField("door_id")
    private String doorId;

    @TableField("barcode")
    private String barCode;

    @TableField("cabinet_id")
    private String cabinetId;

    private String name;

    private String qty;

    @TableField("bottle_weight")
    private String bottleWeight;

    @TableField("reagent_weight")
    private String reagentWeight;

    @TableField("expire_date")
    private LocalDate expireDate;

    @TableField("is_expire")
    private Boolean isExpire;

    private String operator;
    @TableField("add_date")
    private LocalDateTime addDate;

    @TableField("update_date")
    private LocalDateTime updateDate;

    @TableField("in_out")
    private String inOut;

    private String remark;


}
