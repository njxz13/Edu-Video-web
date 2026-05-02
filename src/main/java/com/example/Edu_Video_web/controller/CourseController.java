package com.example.Edu_Video_web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Edu_Video_web.entity.Course;
import com.example.Edu_Video_web.entity.Video;
import com.example.Edu_Video_web.mapper.CourseMapper;
import com.example.Edu_Video_web.mapper.VideoMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private VideoMapper videoMapper;

    // 获取所有课程
    @GetMapping
    public Map<String, Object> getAllCourses(HttpSession session) {
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

    // 课程详情
    @GetMapping("/{id}")
    public Map<String, Object> getCourseDetail(@PathVariable Integer id, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        if (session.getAttribute("username") == null) {
            result.put("code", 401);
            result.put("message", "未登录");
            return result;
        }

        Course course = courseMapper.selectById(id);
        List<Video> videos = videoMapper.selectByCourseId(id);

        result.put("code", 200);
        result.put("course", course);
        result.put("videos", videos);
        return result;
    }

    // 视频播放信息
    @GetMapping("/{courseId}/video/{videoId}")
    public Map<String, Object> playVideo(@PathVariable Integer courseId,
            @PathVariable Integer videoId,
            HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        if (session.getAttribute("username") == null) {
            result.put("code", 401);
            result.put("message", "未登录");
            return result;
        }

        Course course = courseMapper.selectById(courseId);
        Video video = videoMapper.selectById(videoId);
        List<Video> videos = videoMapper.selectByCourseId(courseId);

        result.put("code", 200);
        result.put("course", course);
        result.put("currentVideo", video);
        result.put("videos", videos);
        return result;
    }
}
