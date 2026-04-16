package com.example.Edu_Video_web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@Controller
public class AdminController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VideoMapper videoMapper;

    /**
     * 管理员登录页面
     */
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin-login";
    }

    /**
     * 处理管理员登录
     */
    @PostMapping("/admin/login")
    public String adminLoginSubmit(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Admin admin = adminMapper.selectByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            if (admin.getStatus() == 0) {
                redirectAttributes.addFlashAttribute("error", "账号已被禁用");
                return "redirect:/admin/login";
            }

            session.setAttribute("adminId", admin.getAdminId());
            session.setAttribute("adminUsername", admin.getUsername());
            session.setAttribute("adminRole", admin.getRole());
            return "redirect:/admin/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "用户名或密码错误");
            return "redirect:/admin/login";
        }
    }

    /**
     * 管理后台首页（数据统计）
     */
    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        // 统计用户数量
        Long userCount = userMapper.selectCount(null);

        // 统计视频数量
        Long videoCount = videoMapper.selectCount(null);

        // 获取热门视频
        List<Video> hotVideos = videoMapper.selectHotVideos(10);

        // 获取最新用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("LIMIT 10");
        List<User> recentUsers = userMapper.selectList(queryWrapper);

        model.addAttribute("userCount", userCount);
        model.addAttribute("videoCount", videoCount);
        model.addAttribute("hotVideos", hotVideos);
        model.addAttribute("recentUsers", recentUsers);

        return "admin-dashboard";
    }

    /**
     * 用户管理列表
     */
    @GetMapping("/admin/users")
    public String userList(Model model) {
        List<User> users = userMapper.selectList(null);
        model.addAttribute("users", users);
        return "admin-users";
    }

    /**
     * 禁用/启用用户账号
     */
    @PostMapping("/admin/user/{userId}/status")
    public String updateUserStatus(@PathVariable Integer userId,
            @RequestParam Integer status,
            RedirectAttributes redirectAttributes) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            // 这里需要添加 status 字段到 User 实体类
            // user.setStatus(status);
            // userMapper.updateById(user);
            redirectAttributes.addFlashAttribute("success", "用户状态更新成功");
        } else {
            redirectAttributes.addFlashAttribute("error", "用户不存在");
        }
        return "redirect:/admin/users";
    }

    /**
     * 删除用户
     */
    @PostMapping("/admin/user/{userId}/delete")
    public String deleteUser(@PathVariable Integer userId,
            RedirectAttributes redirectAttributes) {
        userMapper.deleteById(userId);
        redirectAttributes.addFlashAttribute("success", "用户删除成功");
        return "redirect:/admin/users";
    }

    /**
     * 视频管理列表
     */
    @GetMapping("/admin/videos")
    public String videoList(Model model) {
        List<Video> videos = videoMapper.selectList(null);
        model.addAttribute("videos", videos);
        return "admin-videos";
    }

    /**
     * 下架/上架视频
     */
    @PostMapping("/admin/video/{videoId}/status")
    public String updateVideoStatus(@PathVariable Integer videoId,
            @RequestParam Integer status,
            RedirectAttributes redirectAttributes) {
        Video video = videoMapper.selectByVideoId(videoId);
        if (video != null) {
            video.setStatus(status);
            videoMapper.updateById(video);
            redirectAttributes.addFlashAttribute("success", "视频状态更新成功");
        } else {
            redirectAttributes.addFlashAttribute("error", "视频不存在");
        }
        return "redirect:/admin/videos";
    }

    /**
     * 删除视频
     */
    @PostMapping("/admin/video/{videoId}/delete")
    public String deleteVideo(@PathVariable Integer videoId,
            RedirectAttributes redirectAttributes) {
        videoMapper.deleteById(videoId);
        redirectAttributes.addFlashAttribute("success", "视频删除成功");
        return "redirect:/admin/videos";
    }

    /**
     * 管理员退出登录
     */
    @GetMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        session.removeAttribute("adminId");
        session.removeAttribute("adminUsername");
        session.removeAttribute("adminRole");
        return "redirect:/admin/login";
    }
}
