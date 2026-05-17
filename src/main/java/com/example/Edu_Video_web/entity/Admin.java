package com.example.Edu_Video_web.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 管理员实体类
 */
@Data
@TableName("admins") // 指定数据库表名
public class Admin {
    @TableId
    private Integer adminId; // 管理员 ID
    private String username; // 用户名
    private String password; // 密码
    private String email; // 邮箱
    private String role; // 角色（super_admin:超级管理员，admin:普通管理员）
    private Integer status; // 状态（1:正常 0:禁用）
    private Date createTime; // 创建时间
    private Date lastLoginTime; // 最后登录时间
}
