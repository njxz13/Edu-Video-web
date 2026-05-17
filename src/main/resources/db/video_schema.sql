-- 视频管理模块数据库表

USE `edu-video`;

-- 创建视频分类表
DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
  `category_id` INT NOT NULL AUTO_INCREMENT COMMENT '分类 ID',
  `category_name` VARCHAR(100) NOT NULL COMMENT '分类名称',
  `description` TEXT COMMENT '分类描述',
  `parent_id` INT DEFAULT 0 COMMENT '父分类 ID（0 表示一级分类）',
  `sort_order` INT DEFAULT 0 COMMENT '排序顺序',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`category_id`),
  INDEX `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频分类表';

-- 插入示例分类数据
INSERT INTO `categories` (`category_name`, `description`, `parent_id`, `sort_order`) VALUES
('编程开发', '计算机编程相关教程', 0, 1),
('产品设计', '产品设计与管理课程', 0, 2),
('设计创意', '平面设计与创意设计', 0, 3),
('市场营销', '数字营销与运营推广', 0, 4),
('语言学习', '外语学习与考试辅导', 0, 5),
('职场技能', '职业发展与软技能提升', 0, 6),
('Java 开发', 'Java 编程语言学习', 1, 1),
('Python 开发', 'Python 编程语言学习', 1, 2),
('前端开发', 'Web 前端技术学习', 1, 3),
('UI 设计', '用户界面设计', 3, 1);

-- 创建视频表
DROP TABLE IF EXISTS `videos`;

CREATE TABLE `videos` (
  `video_id` INT NOT NULL AUTO_INCREMENT COMMENT '视频 ID',
  `title` VARCHAR(200) NOT NULL COMMENT '视频标题',
  `description` TEXT COMMENT '视频描述',
  `video_url` VARCHAR(500) NOT NULL COMMENT '视频文件 URL',
  `cover_url` VARCHAR(500) DEFAULT NULL COMMENT '封面图 URL',
  `uploader_id` INT NOT NULL COMMENT '上传者 ID',
  `uploader_name` VARCHAR(50) NOT NULL COMMENT '上传者姓名',
  `course_id` INT DEFAULT NULL COMMENT '所属课程 ID（兼容旧版）',
  `order_number` INT DEFAULT 0 COMMENT '排序号（兼容旧版）',
  `category_id` INT DEFAULT NULL COMMENT '分类 ID',
  `category_name` VARCHAR(100) DEFAULT NULL COMMENT '分类名称',
  `view_count` INT DEFAULT 0 COMMENT '播放次数',
  `like_count` INT DEFAULT 0 COMMENT '点赞数',
  `duration` INT DEFAULT 0 COMMENT '视频时长（秒）',
  `tags` VARCHAR(500) DEFAULT NULL COMMENT '标签（逗号分隔）',
  `status` TINYINT DEFAULT 1 COMMENT '状态（0:审核中 1:已发布 2:已下架）',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`video_id`),
  INDEX `idx_uploader_id` (`uploader_id`),
  INDEX `idx_category_id` (`category_id`),
  INDEX `idx_course_id` (`course_id`),
  INDEX `idx_status` (`status`),
  INDEX `idx_view_count` (`view_count`),
  INDEX `idx_create_time` (`create_time`),
  FULLTEXT INDEX `ft_title_description` (`title`, `description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频表';

-- 插入示例视频数据
INSERT INTO `videos` (`title`, `description`, `video_url`, `cover_url`, `uploader_id`, `uploader_name`, 
                      `category_id`, `category_name`, `duration`, `tags`, `view_count`, `like_count`, `status`) VALUES
('Java 入门教程 - 第一章：Java 简介', '本课程将带你快速入门 Java 编程语言，了解 Java 的历史、特点和应用场景', 
 '/uploads/videos/demo1.mp4', '/uploads/covers/demo1.jpg', 1, 'admin', 7, 'Java 开发', 600, 'java,入门,编程基础', 1580, 128, 1),
('Python 数据分析实战', '使用 Python 进行数据分析的完整教程，涵盖 NumPy、Pandas 等核心库的使用', 
 '/uploads/videos/demo2.mp4', '/uploads/covers/demo2.jpg', 1, 'admin', 8, 'Python 开发', 1800, 'python,数据分析,pandas', 2350, 198, 1),
('HTML5+CSS3 网页制作', '从零开始学习 HTML5 和 CSS3，制作精美的响应式网页', 
 '/uploads/videos/demo3.mp4', '/uploads/covers/demo3.jpg', 2, 'test', 9, '前端开发', 2400, 'html5,css3,前端,响应式', 3200, 256, 1);

-- 创建视频评论表（待实现）
DROP TABLE IF EXISTS `video_comments`;

CREATE TABLE `video_comments` (
  `comment_id` INT NOT NULL AUTO_INCREMENT COMMENT '评论 ID',
  `video_id` INT NOT NULL COMMENT '视频 ID',
  `user_id` INT NOT NULL COMMENT '用户 ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `parent_id` INT DEFAULT 0 COMMENT '父评论 ID（用于回复）',
  `like_count` INT DEFAULT 0 COMMENT '点赞数',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` TINYINT DEFAULT 1 COMMENT '状态（1:正常 0:隐藏）',
  PRIMARY KEY (`comment_id`),
  INDEX `idx_video_id` (`video_id`),
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频评论表';
