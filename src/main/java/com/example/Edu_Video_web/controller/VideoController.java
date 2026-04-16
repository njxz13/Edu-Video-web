package com.example.Edu_Video_web.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Edu_Video_web.entity.Video;
import com.example.Edu_Video_web.mapper.VideoMapper;

import jakarta.servlet.http.HttpSession;

/**
 * 视频管理控制器
 */
@Controller
public class VideoController {

    @Autowired
    private VideoMapper videoMapper;

    // 视频上传目录
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/videos/";
    private static final String COVER_UPLOAD_DIR = "src/main/resources/static/uploads/covers/";

    /**
     * 视频列表页面
     */
    @GetMapping("/videos")
    public String videoList(@RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,
            Model model) {
        List<Video> videos;

        if (keyword != null || categoryId != null) {
            // 搜索或筛选
            videos = videoMapper.searchVideos(keyword, categoryId);
        } else {
            // 显示最新视频
            videos = videoMapper.selectLatestVideos(20);
        }

        model.addAttribute("videos", videos);
        return "video-list";
    }

    /**
     * 视频详情页面
     */
    @GetMapping("/video/{videoId}")
    public String videoDetail(@PathVariable Integer videoId, Model model) {
        Video video = videoMapper.selectByVideoId(videoId);

        if (video == null) {
            return "redirect:/videos";
        }

        // 增加播放次数
        videoMapper.incrementViewCount(videoId);

        // 获取热门视频推荐
        List<Video> hotVideos = videoMapper.selectHotVideos(5);

        model.addAttribute("video", video);
        model.addAttribute("hotVideos", hotVideos);

        return "video-detail";
    }

    /**
     * 显示上传页面
     */
    @GetMapping("/upload")
    public String uploadPage(Model model) {
        return "upload";
    }

    /**
     * 处理视频上传
     */
    @PostMapping("/upload")
    public String uploadVideo(@RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") Integer categoryId,
            @RequestParam("tags") String tags,
            @RequestParam("videoFile") MultipartFile videoFile,
            @RequestParam(value = "coverFile", required = false) MultipartFile coverFile,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            // 验证文件
            if (videoFile.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "请选择要上传的视频文件");
                return "redirect:/upload";
            }

            // 验证文件格式
            String originalFilename = videoFile.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            if (!isValidVideoFormat(fileExtension)) {
                redirectAttributes.addFlashAttribute("error", "不支持的视频格式，请上传 MP4、AVI 或 MOV 格式");
                return "redirect:/upload";
            }

            // 验证文件大小（限制 500MB）
            if (videoFile.getSize() > 500 * 1024 * 1024) {
                redirectAttributes.addFlashAttribute("error", "视频文件大小不能超过 500MB");
                return "redirect:/upload";
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

            // 获取上传者信息
            String username = (String) session.getAttribute("username");
            Integer userId = (Integer) session.getAttribute("userId");

            if (username == null || userId == null) {
                redirectAttributes.addFlashAttribute("error", "请先登录");
                return "redirect:/login";
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
            video.setStatus(1); // 直接发布

            // 保存到数据库
            int result = videoMapper.insertVideo(video);

            if (result > 0) {
                redirectAttributes.addFlashAttribute("success", "视频上传成功！");
                return "redirect:/video/" + video.getVideoId();
            } else {
                redirectAttributes.addFlashAttribute("error", "视频上传失败，请重试");
                return "redirect:/upload";
            }

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "上传过程中发生错误：" + e.getMessage());
            return "redirect:/upload";
        }
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
