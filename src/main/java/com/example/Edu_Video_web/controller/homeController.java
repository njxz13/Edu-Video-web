package com.example.Edu_Video_web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Edu_Video_web.entity.Course;
import com.example.Edu_Video_web.mapper.CourseMapper;

import jakarta.servlet.http.HttpSession;

@RestController
public class homeController {

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping("/api/courses")
    public Map<String, Object> getCourses(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        if (session.getAttribute("username") == null) {
            result.put("code", 401);
            result.put("message", "未登录");
            return result;
        }
        List<Course> courses = courseMapper.selectList(null);
        result.put("code", 200);
        result.put("data", courses);
        result.put("username", session.getAttribute("username"));
        return result;
    }
}
