package com.example.Edu_Video_web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Edu_Video_web.entity.Video;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    @Select("SELECT * FROM videos WHERE video_id = #{videoId}")
    Video selectByVideoId(Integer videoId);

    // 兼容旧版本课程模块使用
    @Select("SELECT * FROM videos WHERE course_id = #{courseId} ORDER BY order_number ASC")
    List<Video> selectByCourseId(Integer courseId);

    @Select("SELECT * FROM videos WHERE uploader_id = #{uploaderId} ORDER BY create_time DESC")
    List<Video> selectByUploaderId(Integer uploaderId);

    @Select("SELECT * FROM videos WHERE status = 1 ORDER BY view_count DESC LIMIT #{limit}")
    List<Video> selectHotVideos(Integer limit);

    @Select("SELECT * FROM videos WHERE status = 1 ORDER BY create_time DESC LIMIT #{limit}")
    List<Video> selectLatestVideos(Integer limit);

    @Select("<script>" +
            "SELECT * FROM videos WHERE status = 1 " +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND (title LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%') OR tags LIKE CONCAT('%', #{keyword}, '%')) "
            +
            "</if>" +
            "<if test='categoryId != null'>" +
            "AND category_id = #{categoryId} " +
            "</if>" +
            "ORDER BY create_time DESC" +
            "</script>")
    List<Video> searchVideos(@Param("keyword") String keyword, @Param("categoryId") Integer categoryId);

    @Select("<script>" +
            "SELECT * FROM videos WHERE status = 1 " +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND (title LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%') OR tags LIKE CONCAT('%', #{keyword}, '%')) "
            +
            "</if>" +
            "<if test='categoryId != null'>" +
            "AND category_id = #{categoryId} " +
            "</if>" +
            "ORDER BY create_time DESC " +
            "LIMIT #{limit} OFFSET #{offset}" +
            "</script>")
    List<Video> searchVideosWithPagination(@Param("keyword") String keyword, @Param("categoryId") Integer categoryId,
                                           @Param("offset") int offset, @Param("limit") int limit);

    @Update("UPDATE videos SET view_count = view_count + 1 WHERE video_id = #{videoId}")
    int incrementViewCount(Integer videoId);

    @Insert("INSERT INTO videos(title, description, video_url, cover_url, uploader_id, uploader_name, " +
            "category_id, category_name, duration, tags, status, create_time) " +
            "VALUES(#{title}, #{description}, #{videoUrl}, #{coverUrl}, #{uploaderId}, #{uploaderName}, " +
            "#{categoryId}, #{categoryName}, #{duration}, #{tags}, 1, NOW())")
    int insertVideo(Video video);
}
