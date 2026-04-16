package com.example.Edu_Video_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Edu_Video_web.entity.User;
import com.example.Edu_Video_web.mapper.UserMapper;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    /**
     * 自动注入UserMapper
     */
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes, HttpSession session) {
        User user = userMapper.selectByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", username); // 直接用username,是因为用户信息已经经过password验证过了,所以这里的username和user.getUsername()是同一个
            session.setAttribute("password", password);
            session.setAttribute("userId", user.getUserId());
            return "redirect:/home";
        } else {
            // 验证失败，返回错误信息
            redirectAttributes.addFlashAttribute("error", "用户名或密码错误");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String getProfile(HttpSession session, Model model) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        // 从 Session 获取用户 ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            // 使用 selectByUsername 代替 selectById
            String username = (String) session.getAttribute("username");
            User user = userMapper.selectByUsername(username);
            if (user != null) {
                model.addAttribute("user", user);
            }
        }

        model.addAttribute("username", session.getAttribute("username"));
        return "profile";
    }

    @GetMapping("/edit-profile")
    public String editProfilePage(HttpSession session, Model model) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        // 使用 username 查询用户信息
        String username = (String) session.getAttribute("username");
        User user = userMapper.selectByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "edit-profile";
    }

    @PostMapping("/edit-profile")
    public String editProfileSubmit(@RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String avatar,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        // 获取当前用户信息
        String currentUsername = (String) session.getAttribute("username");
        User currentUser = userMapper.selectByUsername(currentUsername);
        if (currentUser == null) {
            return "redirect:/login";
        }

        // 验证用户名是否已被其他用户使用
        User existingUser = userMapper.selectByUsername(username);
        if (existingUser != null && !existingUser.getUserId().equals(currentUser.getUserId())) {
            redirectAttributes.addFlashAttribute("error", "用户名已被使用");
            return "redirect:/edit-profile";
        }

        // 验证邮箱格式
        if (!isValidEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "邮箱格式不正确");
            return "redirect:/edit-profile";
        }

        // 验证邮箱是否已被其他用户使用
        User existingEmail = userMapper.selectByEmail(email);
        if (existingEmail != null && !existingEmail.getUserId().equals(currentUser.getUserId())) {
            redirectAttributes.addFlashAttribute("error", "该邮箱已被使用");
            return "redirect:/edit-profile";
        }

        // 更新用户信息
        User updateUser = new User();
        updateUser.setUserId(currentUser.getUserId());
        updateUser.setUsername(username);
        updateUser.setEmail(email);
        updateUser.setAvatar(avatar);

        int result = userMapper.updateUser(updateUser);
        if (result > 0) {
            // 更新 Session 中的用户名
            session.setAttribute("username", username);
            redirectAttributes.addFlashAttribute("success", "个人信息更新成功");
            return "redirect:/profile";
        } else {
            redirectAttributes.addFlashAttribute("error", "更新失败，请重试");
            return "redirect:/edit-profile";
        }
    }

    /**
     * 验证邮箱格式
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // 简单的邮箱格式验证正则表达式
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            RedirectAttributes redirectAttributes) {
        // 验证用户名是否已存在
        User existingUser = userMapper.selectByUsername(username);
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("error", "用户名已被使用");
            return "redirect:/register";
        }

        // 创建新用户
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // TODO: 实际项目中应该加密密码
        newUser.setEmail(email);

        // 插入数据库
        int result = userMapper.insertUser(newUser);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("success", "注册成功，请登录");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "注册失败，请重试");
            return "redirect:/register";
        }
    }

}