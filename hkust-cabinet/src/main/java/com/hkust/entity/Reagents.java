package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@TableName("hkust_reagents")
public class Reagents {

    @TableId("reagents_id")
    private String reagentsId;

    private String barcode;

    @TableField("cabinetNo")
    private String cabinet_no;

    private String name;

    private String qty;

    @TableField("bottle_weight")
    private String bottleWeight;

    @TableField("reagent_weight")
    private String reagentWeight;

    @TableField("expire_date")
    private LocalDate expireDate;

    @TableField("is_expire")
    private String isExpire;

    private String operator;
    @TableField("add_date")
    private LocalDate addDate;

    @TableField("update_date")
    private Local updateDate;

    private String remark;


}
