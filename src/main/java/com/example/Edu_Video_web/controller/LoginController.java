package com.example.Edu_Video_web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Edu_Video_web.entity.User;
import com.example.Edu_Video_web.mapper.UserMapper;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String username,
            @RequestParam String password,
            HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = userMapper.selectByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", username);
            session.setAttribute("userId", user.getUserId());
            result.put("code", 200);
            result.put("message", "登录成功");
            result.put("username", username);
            result.put("userId", user.getUserId());
        } else {
            result.put("code", 401);
            result.put("message", "用户名或密码错误");
        }
        return result;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        session.invalidate();
        result.put("code", 200);
        result.put("message", "退出成功");
        return result;
    }

    @GetMapping("/profile")
    public Map<String, Object> getProfile(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        if (session.getAttribute("username") == null) {
            result.put("code", 401);
            result.put("message", "未登录");
            return result;
        }

        String username = (String) session.getAttribute("username");
        User user = userMapper.selectByUsername(username);
        if (user != null) {
            result.put("code", 200);
            result.put("data", user);
        } else {
            result.put("code", 404);
            result.put("message", "用户不存在");
        }
        return result;
    }

    @PutMapping("/profile")
    public Map<String, Object> updateProfile(@RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String avatar,
            HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        if (session.getAttribute("username") == null) {
            result.put("code", 401);
            result.put("message", "未登录");
            return result;
        }

        String currentUsername = (String) session.getAttribute("username");
        User currentUser = userMapper.selectByUsername(currentUsername);
        if (currentUser == null) {
            result.put("code", 404);
            result.put("message", "用户不存在");
            return result;
        }

        User existingUser = userMapper.selectByUsername(username);
        if (existingUser != null && !existingUser.getUserId().equals(currentUser.getUserId())) {
            result.put("code", 400);
            result.put("message", "用户名已被使用");
            return result;
        }

        if (!isValidEmail(email)) {
            result.put("code", 400);
            result.put("message", "邮箱格式不正确");
            return result;
        }

        User existingEmail = userMapper.selectByEmail(email);
        if (existingEmail != null && !existingEmail.getUserId().equals(currentUser.getUserId())) {
            result.put("code", 400);
            result.put("message", "该邮箱已被使用");
            return result;
        }

        User updateUser = new User();
        updateUser.setUserId(currentUser.getUserId());
        updateUser.setUsername(username);
        updateUser.setEmail(email);
        updateUser.setAvatar(avatar);

        int res = userMapper.updateUser(updateUser);
        if (res > 0) {
            session.setAttribute("username", username);
            result.put("code", 200);
            result.put("message", "个人信息更新成功");
        } else {
            result.put("code", 500);
            result.put("message", "更新失败，请重试");
        }
        return result;
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String email) {
        Map<String, Object> result = new HashMap<>();

        User existingUser = userMapper.selectByUsername(username);
        if (existingUser != null) {
            result.put("code", 400);
            result.put("message", "用户名已被使用");
            return result;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);

        int res = userMapper.insertUser(newUser);
        if (res > 0) {
            result.put("code", 200);
            result.put("message", "注册成功，请登录");
        } else {
            result.put("code", 500);
            result.put("message", "注册失败，请重试");
        }
        return result;
    }
}
