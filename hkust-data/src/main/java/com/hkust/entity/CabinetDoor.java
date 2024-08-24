package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hkust_cabinet_door")
public class CabinetDoor {

    @TableId("door_id")
    private String doorId;

    @TableField("cabinet_id")
    private String cabinetId;

    private String name;

    private String type;

    private String room;

    private String stat;

    @TableField("reagents_qty")
    private int reagentsQty;

    @TableField("in_qty")
    private int inQty;

    @TableField("out_qty")
    private int outQty;

    private String location;


}
