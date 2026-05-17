package com.example.Edu_Video_web.controller;

import com.example.Edu_Video_web.dto.ApiResponse;
import com.example.Edu_Video_web.entity.User;
import com.example.Edu_Video_web.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestParam String username,
                                 @RequestParam String password,
                                 HttpSession session) {
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("username", username);
            session.setAttribute("userId", user.getUserId());
            return ApiResponse.success(new LoginVO(username, user.getUserId()));
        }
        return ApiResponse.error(401, "用户名或密码错误");
    }

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestParam String username,
                                    @RequestParam String password,
                                    @RequestParam String email) {
        User existing = userService.getByUsername(username);
        if (existing != null) {
            return ApiResponse.error(400, "用户名已被使用");
        }
        User user = userService.register(username, password, email);
        if (user != null) {
            return ApiResponse.success();
        }
        return ApiResponse.error(500, "注册失败，请重试");
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpSession session) {
        session.invalidate();
        return ApiResponse.success();
    }

    @GetMapping("/profile")
    public ApiResponse<?> getProfile(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ApiResponse.error(401, "未登录");
        }
        User user = userService.getByUsername(username);
        if (user != null) {
            return ApiResponse.success(user);
        }
        return ApiResponse.error(404, "用户不存在");
    }

    @PutMapping("/profile")
    public ApiResponse<?> updateProfile(@RequestParam String username,
                                         @RequestParam String email,
                                         @RequestParam(required = false) String avatar,
                                         HttpSession session) {
        String currentUsername = (String) session.getAttribute("username");
        Integer userId = (Integer) session.getAttribute("userId");
        if (currentUsername == null || userId == null) {
            return ApiResponse.error(401, "未登录");
        }

        User currentUser = userService.getByUsername(currentUsername);
        if (currentUser == null) {
            return ApiResponse.error(404, "用户不存在");
        }

        User existingUser = userService.getByUsername(username);
        if (existingUser != null && !existingUser.getUserId().equals(currentUser.getUserId())) {
            return ApiResponse.error(400, "用户名已被使用");
        }

        if (!userService.isEmailValid(email)) {
            return ApiResponse.error(400, "邮箱格式不正确");
        }

        User existingEmail = userService.getByEmail(email);
        if (existingEmail != null && !existingEmail.getUserId().equals(currentUser.getUserId())) {
            return ApiResponse.error(400, "该邮箱已被使用");
        }

        User updated = userService.updateProfile(userId, username, email, avatar);
        if (updated != null) {
            session.setAttribute("username", username);
            return ApiResponse.success();
        }
        return ApiResponse.error(500, "更新失败，请重试");
    }

    private record LoginVO(String username, Integer userId) {}
}
