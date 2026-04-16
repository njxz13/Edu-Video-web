package com.example.Edu_Video_web.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 视频实体类
 */
@Data
@TableName("videos") // 指定数据库表名
public class Video {
    private Integer videoId; // 视频 ID
    private String title; // 标题
    private String description; // 描述
    private String videoUrl; // 视频文件 URL
    private String coverUrl; // 封面图 URL
    private Integer uploaderId; // 上传者 ID
    private String uploaderName; // 上传者姓名
    private Integer courseId; // 所属课程 ID（兼容旧版）
    private Integer orderNumber; // 排序号（兼容旧版）
    private Integer categoryId; // 分类 ID
    private String categoryName; // 分类名称
    private Integer viewCount; // 播放次数
    private Integer likeCount; // 点赞数
    private Integer duration; // 视频时长（秒）
    private String tags; // 标签（逗号分隔）
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private Integer status; // 状态（0:审核中 1:已发布 2:已下架）
}
