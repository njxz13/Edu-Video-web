package com.example.Edu_Video_web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("course")
public class Course {
    private Integer id;
    private String title;
    private String description;
    private String coverImage;
    private LocalDateTime createdAt;
}
