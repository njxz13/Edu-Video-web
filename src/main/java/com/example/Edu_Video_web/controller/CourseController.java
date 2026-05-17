package com.example.Edu_Video_web.controller;

import java.util.Map;

import com.example.Edu_Video_web.dto.ApiResponse;
import com.example.Edu_Video_web.entity.Course;
import com.example.Edu_Video_web.entity.Video;
import com.example.Edu_Video_web.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ApiResponse<?> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ApiResponse.success(Map.of("courses", courses));
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getCourseDetail(@PathVariable Integer id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return ApiResponse.error(404, "课程不存在");
        }
        List<Video> videos = courseService.getVideosByCourseId(id);
        return ApiResponse.success(Map.of("course", course, "videos", videos));
    }

    @GetMapping("/{courseId}/video/{videoId}")
    public ApiResponse<?> playVideo(@PathVariable Integer courseId,
                                     @PathVariable Integer videoId) {
        Course course = courseService.getCourseById(courseId);
        Video video = courseService.getVideoById(videoId);
        if (course == null) {
            return ApiResponse.error(404, "课程不存在");
        }
        if (video == null) {
            return ApiResponse.error(404, "视频不存在");
        }
        List<Video> videos = courseService.getVideosByCourseId(courseId);
        return ApiResponse.success(Map.of("course", course, "currentVideo", video, "videos", videos));
    }
}
