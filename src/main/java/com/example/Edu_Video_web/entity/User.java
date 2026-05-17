package com.example.Edu_Video_web.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import java.util.Date;

@Data
@TableName("users")
public class User {
  @TableId
  private Integer userId;
  private String username;
  private String password;
  private String email;
  private String avatar;
  private Integer status;
  private Date createTime;
  private Date updateTime;
}
