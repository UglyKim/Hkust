package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@TableName("hkust_video")
public class Video {

    @TableId(value = "video_id")
    private String videoId;

    private String url;

    @TableField("create_date")
    private LocalDate createDate;

    @TableField("update_date")
    private LocalDate updateDate;

    private String remark;
}
