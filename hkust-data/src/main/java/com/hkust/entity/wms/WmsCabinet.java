package com.hkust.entity.wms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@TableName("wms_cabinet")
public class WmsCabinet {

    @TableId("id")
    private String id;

    /**
     * 使用状态
     */
    private String state;

    /**
     * 安全柜类型
     */
    private String type;

    private String name;

    private String barcode;

    /**
     * 审核状态
     */
    private String review_state;

    /**
     * 存放房间
     */
    @TableField("storage_room")
    private String storageRoom;

    private String brand;

    /**
     * 规格
     */
    private String specification;

    /**
     * 层数
     */
    @TableField("layer_count")
    private int layerCount;

    /**
     * 创建人
     */
    private String creator;

    @TableField("create_time")
    private LocalDateTime createTime;

    private String auditor;

    @TableField("audit_time")
    private LocalDateTime auditTime;

    /**
     * 材料
     */
    private String material;

    /**
     * '阈值比例'
     */
    @TableField("threshold_ratio")
    private String thresholdRatio;

    /**
     * 楼栋
     */
    @TableField("teaching_building")
    private String teachingBuilding;
    /**
     * 容量
     */
    private String capacity;

    /**
     * 废液桶名称
     */
    @TableField("waste_container_name")
    private String wasteContainerName;

    /**
     * 盛庄废液类型
     */
    @TableField("waste_liquids_type")
    private String wasteLiquidsType;

}
