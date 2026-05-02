package com.example.Edu_Video_web.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.Edu_Video_web.entity.Video;
import com.example.Edu_Video_web.mapper.VideoMapper;

import jakarta.servlet.http.HttpSession;

/**
 * 视频管理控制器
 */
@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoMapper videoMapper;

    // 视频上传目录
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/videos/";
    private static final String COVER_UPLOAD_DIR = "src/main/resources/static/uploads/covers/";

    /**
     * 获取视频列表
     */
    @GetMapping
    public Map<String, Object> getVideoList(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword) {
        Map<String, Object> result = new HashMap<>();
        List<Video> videos;

        if (keyword != null || categoryId != null) {
            videos = videoMapper.searchVideos(keyword, categoryId);
        } else {
            videos = videoMapper.selectLatestVideos(20);
        }

        result.put("code", 200);
        result.put("data", videos);
        return result;
    }

    /**
     * 获取视频详情
     */
    @GetMapping("/{videoId}")
    public Map<String, Object> getVideoDetail(@PathVariable Integer videoId) {
        Map<String, Object> result = new HashMap<>();
        Video video = videoMapper.selectByVideoId(videoId);

        if (video == null) {
            result.put("code", 404);
            result.put("message", "视频不存在");
            return result;
        }

        // 增加播放次数
        videoMapper.incrementViewCount(videoId);

        // 获取热门视频推荐
        List<Video> hotVideos = videoMapper.selectHotVideos(5);

        result.put("code", 200);
        result.put("data", video);
        result.put("hotVideos", hotVideos);
        return result;
    }

    /**
     * 上传视频
     */
    @PostMapping("/upload")
    public Map<String, Object> uploadVideo(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") Integer categoryId,
            @RequestParam("tags") String tags,
            @RequestParam("videoFile") MultipartFile videoFile,
            @RequestParam(value = "coverFile", required = false) MultipartFile coverFile,
            HttpSession session) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 验证登录
            String username = (String) session.getAttribute("username");
            Integer userId = (Integer) session.getAttribute("userId");

            if (username == null || userId == null) {
                result.put("code", 401);
                result.put("message", "请先登录");
                return result;
            }

            // 验证文件
            if (videoFile.isEmpty()) {
                result.put("code", 400);
                result.put("message", "请选择要上传的视频文件");
                return result;
            }

            // 验证文件格式
            String originalFilename = videoFile.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            if (!isValidVideoFormat(fileExtension)) {
                result.put("code", 400);
                result.put("message", "不支持的视频格式，请上传 MP4、AVI 或 MOV 格式");
                return result;
            }

            // 验证文件大小（限制 500MB）
            if (videoFile.getSize() > 500 * 1024 * 1024) {
                result.put("code", 400);
                result.put("message", "视频文件大小不能超过 500MB");
                return result;
            }

            // 生成唯一文件名
            String videoFileName = UUID.randomUUID().toString() + "." + fileExtension;
            String videoFilePath = UPLOAD_DIR + videoFileName;

            // 确保目录存在
            File videoDir = new File(UPLOAD_DIR);
            if (!videoDir.exists()) {
                videoDir.mkdirs();
            }

            // 保存视频文件
            videoFile.transferTo(new File(videoFilePath));

            // 处理封面图
            String coverUrl = "";
            if (coverFile != null && !coverFile.isEmpty()) {
                String coverFileName = UUID.randomUUID().toString() + "." +
                        coverFile.getOriginalFilename().substring(coverFile.getOriginalFilename().lastIndexOf(".") + 1);
                String coverFilePath = COVER_UPLOAD_DIR + coverFileName;

                File coverDir = new File(COVER_UPLOAD_DIR);
                if (!coverDir.exists()) {
                    coverDir.mkdirs();
                }

                coverFile.transferTo(new File(coverFilePath));
                coverUrl = "/uploads/covers/" + coverFileName;
            }

            // 创建视频对象
            Video video = new Video();
            video.setTitle(title);
            video.setDescription(description);
            video.setVideoUrl("/uploads/videos/" + videoFileName);
            video.setCoverUrl(coverUrl);
            video.setUploaderId(userId);
            video.setUploaderName(username);
            video.setCategoryId(categoryId);
            video.setTags(tags);
            video.setViewCount(0);
            video.setLikeCount(0);
            video.setStatus(1);

            // 保存到数据库
            int res = videoMapper.insertVideo(video);

            if (res > 0) {
                result.put("code", 200);
                result.put("message", "视频上传成功！");
                result.put("videoId", video.getVideoId());
            } else {
                result.put("code", 500);
                result.put("message", "视频上传失败，请重试");
            }

        } catch (IOException e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "上传过程中发生错误：" + e.getMessage());
        }
        return result;
    }

    /**
     * 验证视频格式
     */
    private boolean isValidVideoFormat(String extension) {
        return "mp4".equals(extension) ||
                "avi".equals(extension) ||
                "mov".equals(extension) ||
                "wmv".equals(extension) ||
                "flv".equals(extension);
    }
}
