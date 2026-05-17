package com.example.Edu_Video_web.controller;

import java.util.Map;

import com.example.Edu_Video_web.dto.ApiResponse;
import com.example.Edu_Video_web.entity.Admin;
import com.example.Edu_Video_web.entity.User;
import com.example.Edu_Video_web.entity.Video;
import com.example.Edu_Video_web.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ApiResponse<?> adminLogin(@RequestParam String username,
                                      @RequestParam String password,
                                      HttpSession session) {
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            session.setAttribute("adminId", admin.getAdminId());
            session.setAttribute("adminUsername", admin.getUsername());
            session.setAttribute("adminRole", admin.getRole());
            return ApiResponse.success(Map.of("username", admin.getUsername(), "role", admin.getRole()));
        }
        return ApiResponse.error(401, "用户名或密码错误");
    }

    @GetMapping("/dashboard")
    public ApiResponse<?> dashboard(HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return ApiResponse.error(401, "请先登录");
        }
        Long userCount = adminService.getUserCount();
        Long videoCount = adminService.getVideoCount();
        List<Video> hotVideos = adminService.getHotVideos(10);
        List<User> recentUsers = adminService.getRecentUsers(10);

        return ApiResponse.success(Map.of(
            "userCount", userCount,
            "videoCount", videoCount,
            "hotVideos", hotVideos,
            "recentUsers", recentUsers
        ));
    }

    @GetMapping("/users")
    public ApiResponse<?> getUserList(HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return ApiResponse.error(401, "请先登录");
        }
        return ApiResponse.success(adminService.getAllUsers());
    }

    @DeleteMapping("/user/{userId}")
    public ApiResponse<?> deleteUser(@PathVariable Integer userId, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return ApiResponse.error(401, "请先登录");
        }
        adminService.deleteUser(userId);
        return ApiResponse.success();
    }

    @GetMapping("/videos")
    public ApiResponse<?> getVideoList(HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return ApiResponse.error(401, "请先登录");
        }
        return ApiResponse.success(adminService.getAllVideos());
    }

    @PutMapping("/video/{videoId}/status")
    public ApiResponse<?> updateVideoStatus(@PathVariable Integer videoId,
                                             @RequestParam Integer status,
                                             HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return ApiResponse.error(401, "请先登录");
        }
        adminService.updateVideoStatus(videoId, status);
        return ApiResponse.success();
    }

    @DeleteMapping("/video/{videoId}")
    public ApiResponse<?> deleteVideo(@PathVariable Integer videoId, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return ApiResponse.error(401, "请先登录");
        }
        adminService.deleteVideo(videoId);
        return ApiResponse.success();
    }

    @PostMapping("/logout")
    public ApiResponse<?> adminLogout(HttpSession session) {
        session.removeAttribute("adminId");
        session.removeAttribute("adminUsername");
        session.removeAttribute("adminRole");
        return ApiResponse.success();
    }

    private boolean isAdminLoggedIn(HttpSession session) {
        return session.getAttribute("adminId") != null;
    }
}
