package com.example.Edu_Video_web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Edu_Video_web.entity.Course;
import com.example.Edu_Video_web.mapper.CourseMapper;

import jakarta.servlet.http.HttpSession;

@Controller
public class homeController {

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        List<Course> courses = courseMapper.selectList(null);
        model.addAttribute("courses", courses);
        model.addAttribute("username", session.getAttribute("username"));
        return "home";
    }
}
