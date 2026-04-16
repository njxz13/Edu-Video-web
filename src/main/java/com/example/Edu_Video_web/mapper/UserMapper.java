package com.example.Edu_Video_web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Edu_Video_web.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

  @Select("SELECT * FROM users WHERE username = #{username}")
  User selectByUsername(String username);

  @Insert("INSERT INTO users(username, password, email, create_time) VALUES(#{username}, #{password}, #{email}, NOW())")
  int insertUser(User user);

  @Select("SELECT * FROM users WHERE email = #{email}")
  User selectByEmail(String email);

  @Update("UPDATE users SET username=#{username}, email=#{email}, avatar=#{avatar} WHERE user_id=#{userId}")
  int updateUser(User user);
}