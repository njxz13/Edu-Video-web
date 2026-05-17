package com.example.Edu_Video_web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import com.example.Edu_Video_web.dto.ApiResponse;
import com.example.Edu_Video_web.entity.Video;
import com.example.Edu_Video_web.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    @GetMapping
    public ApiResponse<?> getVideoList(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {
        List<Video> videos = videoService.getVideoList(categoryId, keyword, page, size);
        return ApiResponse.success(videos);
    }

    @GetMapping("/{videoId}")
    public ApiResponse<?> getVideoDetail(@PathVariable Integer videoId) {
        Video video = videoService.getVideoDetail(videoId);
        if (video == null) {
            return ApiResponse.error(404, "视频不存在");
        }
        List<Video> hotVideos = videoService.getHotVideos(5);
        return ApiResponse.success(Map.of("video", video, "hotVideos", hotVideos));
    }

    @PostMapping("/upload")
    public ApiResponse<?> uploadVideo(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") Integer categoryId,
            @RequestParam("tags") String tags,
            @RequestParam("videoFile") MultipartFile videoFile,
            @RequestParam(value = "coverFile", required = false) MultipartFile coverFile,
            HttpSession session) {

        String username = (String) session.getAttribute("username");
        Integer userId = (Integer) session.getAttribute("userId");
        if (username == null || userId == null) {
            return ApiResponse.error(401, "请先登录");
        }

        if (videoFile.isEmpty()) {
            return ApiResponse.error(400, "请选择要上传的视频文件");
        }

        String originalFilename = videoFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return ApiResponse.error(400, "无法获取文件名");
        }
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!isValidVideoFormat(fileExtension)) {
            return ApiResponse.error(400, "不支持的视频格式，请上传 MP4、AVI、MOV、WMV 或 FLV 格式");
        }

        if (videoFile.getSize() > 500 * 1024 * 1024) {
            return ApiResponse.error(400, "视频文件大小不能超过 500MB");
        }

        try {
            String videoDirPath = uploadDir + File.separator + "videos";
            String coverDirPath = uploadDir + File.separator + "covers";

            String videoFileName = UUID.randomUUID() + "." + fileExtension;
            File videoDir = new File(videoDirPath);
            if (!videoDir.exists() && !videoDir.mkdirs()) {
                return ApiResponse.error(500, "无法创建上传目录");
            }
            videoFile.transferTo(new File(videoDir, videoFileName));

            String coverUrl = "";
            if (coverFile != null && !coverFile.isEmpty()) {
                String coverOriginalName = coverFile.getOriginalFilename();
                String coverExt = "jpg";
                if (coverOriginalName != null && !coverOriginalName.isEmpty()) {
                    coverExt = coverOriginalName.substring(coverOriginalName.lastIndexOf(".") + 1);
                }
                String coverFileName = UUID.randomUUID() + "." + coverExt;
                File coverDir = new File(coverDirPath);
                if (!coverDir.exists() && !coverDir.mkdirs()) {
                    return ApiResponse.error(500, "无法创建封面上传目录");
                }
                coverFile.transferTo(new File(coverDir, coverFileName));
                coverUrl = "/uploads/covers/" + coverFileName;
            }

            Video video = videoService.uploadVideo(title, description, categoryId, tags,
                userId, username, "/uploads/videos/" + videoFileName, coverUrl);

            if (video != null) {
                return ApiResponse.success(Map.of("videoId", video.getVideoId()));
            }
            return ApiResponse.error(500, "视频上传失败，请重试");

        } catch (IOException e) {
            return ApiResponse.error(500, "上传过程中发生错误，请重试");
        }
    }

    private boolean isValidVideoFormat(String extension) {
        return "mp4".equals(extension) || "avi".equals(extension) ||
               "mov".equals(extension) || "wmv".equals(extension) || "flv".equals(extension);
    }
}
