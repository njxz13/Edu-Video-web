package com.example.Edu_Video_web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Edu_Video_web.entity.Course;
import com.example.Edu_Video_web.entity.Video;
import com.example.Edu_Video_web.mapper.CourseMapper;
import com.example.Edu_Video_web.mapper.VideoMapper;

import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private VideoMapper videoMapper;

    // 获取所有课程（供首页使用）
    @GetMapping("/courses")
    public String getAllCourses(Model model, HttpSession session) {
        List<Course> courses = courseMapper.selectList(null);
        model.addAttribute("courses", courses);
        model.addAttribute("username", session.getAttribute("username"));
        return "home";
    }

    // 课程详情页
    @GetMapping("/course/{id}")
    public String getCourseDetail(@PathVariable Integer id, Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        Course course = courseMapper.selectById(id);
        List<Video> videos = videoMapper.selectByCourseId(id);

        model.addAttribute("course", course);
        model.addAttribute("videos", videos);
        model.addAttribute("username", session.getAttribute("username"));

        return "course-detail";
    }

    // 视频播放页
    @GetMapping("/course/{courseId}/video/{videoId}")
    public String playVideo(@PathVariable Integer courseId, @PathVariable Integer videoId, Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        Course course = courseMapper.selectById(courseId);
        Video video = videoMapper.selectById(videoId);
        List<Video> videos = videoMapper.selectByCourseId(courseId);

        model.addAttribute("course", course);
        model.addAttribute("currentVideo", video);
        model.addAttribute("videos", videos);
        model.addAttribute("username", session.getAttribute("username"));

        return "video-player";
    }
}
