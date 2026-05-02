package com.example.Edu_Video_web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Edu_Video_web.entity.Admin;
import com.example.Edu_Video_web.entity.User;
import com.example.Edu_Video_web.entity.Video;
import com.example.Edu_Video_web.mapper.AdminMapper;
import com.example.Edu_Video_web.mapper.UserMapper;
import com.example.Edu_Video_web.mapper.VideoMapper;

import jakarta.servlet.http.HttpSession;

/**
 * 后台管理控制器
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VideoMapper videoMapper;

    /**
     * 处理管理员登录
     */
    @PostMapping("/login")
    public Map<String, Object> adminLogin(@RequestParam String username,
            @RequestParam String password,
            HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Admin admin = adminMapper.selectByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            if (admin.getStatus() == 0) {
                result.put("code", 403);
                result.put("message", "账号已被禁用");
                return result;
            }
            session.setAttribute("adminId", admin.getAdminId());
            session.setAttribute("adminUsername", admin.getUsername());
            session.setAttribute("adminRole", admin.getRole());
            result.put("code", 200);
            result.put("message", "登录成功");
        } else {
            result.put("code", 401);
            result.put("message", "用户名或密码错误");
        }
        return result;
    }

    /**
     * 管理后台数据统计
     */
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        Map<String, Object> result = new HashMap<>();
        Long userCount = userMapper.selectCount(null);
        Long videoCount = videoMapper.selectCount(null);
        List<Video> hotVideos = videoMapper.selectHotVideos(10);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("LIMIT 10");
        List<User> recentUsers = userMapper.selectList(queryWrapper);

        result.put("code", 200);
        result.put("userCount", userCount);
        result.put("videoCount", videoCount);
        result.put("hotVideos", hotVideos);
        result.put("recentUsers", recentUsers);
        return result;
    }

    /**
     * 用户管理列表
     */
    @GetMapping("/users")
    public Map<String, Object> getUserList() {
        Map<String, Object> result = new HashMap<>();
        List<User> users = userMapper.selectList(null);
        result.put("code", 200);
        result.put("data", users);
        return result;
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/user/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Integer userId) {
        Map<String, Object> result = new HashMap<>();
        userMapper.deleteById(userId);
        result.put("code", 200);
        result.put("message", "用户删除成功");
        return result;
    }

    /**
     * 视频管理列表
     */
    @GetMapping("/videos")
    public Map<String, Object> getVideoList() {
        Map<String, Object> result = new HashMap<>();
        List<Video> videos = videoMapper.selectList(null);
        result.put("code", 200);
        result.put("data", videos);
        return result;
    }

    /**
     * 更新视频状态
     */
    @PutMapping("/video/{videoId}/status")
    public Map<String, Object> updateVideoStatus(@PathVariable Integer videoId,
            @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        Video video = videoMapper.selectByVideoId(videoId);
        if (video != null) {
            video.setStatus(status);
            videoMapper.updateById(video);
            result.put("code", 200);
            result.put("message", "视频状态更新成功");
        } else {
            result.put("code", 404);
            result.put("message", "视频不存在");
        }
        return result;
    }

    /**
     * 删除视频
     */
    @DeleteMapping("/video/{videoId}")
    public Map<String, Object> deleteVideo(@PathVariable Integer videoId) {
        Map<String, Object> result = new HashMap<>();
        videoMapper.deleteById(videoId);
        result.put("code", 200);
        result.put("message", "视频删除成功");
        return result;
    }

    /**
     * 管理员退出登录
     */
    @PostMapping("/logout")
    public Map<String, Object> adminLogout(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        session.removeAttribute("adminId");
        session.removeAttribute("adminUsername");
        session.removeAttribute("adminRole");
        result.put("code", 200);
        result.put("message", "退出成功");
        return result;
    }
}
