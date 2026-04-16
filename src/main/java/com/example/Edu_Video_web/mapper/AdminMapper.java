package com.example.Edu_Video_web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Edu_Video_web.entity.Admin;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("SELECT * FROM admins WHERE username = #{username}")
    Admin selectByUsername(String username);

    @Select("SELECT * FROM admins WHERE admin_id = #{adminId}")
    Admin selectByAdminId(Integer adminId);
}
