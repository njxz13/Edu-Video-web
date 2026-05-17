package com.example.Edu_Video_web.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("categories")
public class Category {
    private Integer categoryId;
    private String categoryName;
    private String description;
    private Integer parentId;
    private Integer sortOrder;
    private Date createTime;
}
