package com.example.Edu_Video_web.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("users") // 指定数据库表名
public class User {
  private String username;
  private String password;
  private Integer userId;
  private String email;
  private String avatar;
}
