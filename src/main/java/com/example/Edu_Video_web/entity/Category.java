package com.example.Edu_Video_web.entity;

import java.util.Date;

import lombok.Data;

/**
 * 视频分类实体类
 */
@Data
public class Category {
    private Integer categoryId; // 分类 ID
    private String categoryName; // 分类名称
    private String description; // 分类描述
    private Integer parentId; // 父分类 ID（0 表示一级分类）
    private Integer sortOrder; // 排序顺序
    private Date createTime; // 创建时间
}
