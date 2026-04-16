-- 创建课程表
DROP TABLE IF EXISTS video;
DROP TABLE IF EXISTS course;

CREATE TABLE IF NOT EXISTS course (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    cover_image VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建视频表
CREATE TABLE IF NOT EXISTS video (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    video_url VARCHAR(255) NOT NULL,
    duration VARCHAR(50),
    order_number INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);

-- 插入课程数据
INSERT INTO course (title, description, cover_image) VALUES
('Java编程入门', '从零开始学习Java编程，适合初学者的完整教程。', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=java%20programming%20course%20cover&image_size=square'),
('前端开发实战', 'HTML、CSS、JavaScript全掌握，构建现代网页应用。', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=frontend%20development%20course%20cover&image_size=square'),
('数据库设计与优化', '深入理解关系型数据库原理及SQL语言应用。', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=database%20design%20course%20cover&image_size=square'),
('Python数据分析', '使用Python进行数据处理和可视化分析。', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=python%20data%20analysis%20course%20cover&image_size=square'),
('机器学习基础', '了解机器学习基本概念和常用算法。', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=machine%20learning%20course%20cover&image_size=square'),
('移动应用开发', '使用React Native开发跨平台移动应用。', '/pictures/mobile_dev.png');

-- 插入视频数据
-- Java编程入门课程视频
INSERT INTO video (course_id, title, video_url, duration, order_number) VALUES
(1, 'Java概述', '/videos/coffee.mp4', '05:30', 1),
(1, '环境搭建', '/videos/coffee.mp4', '10:15', 2),
(1, '基本语法', '/videos/coffee.mp4', '15:45', 3),
(1, '面向对象编程', '/videos/coffee.mp4', '20:20', 4);

-- 前端开发实战课程视频
INSERT INTO video (course_id, title, video_url, duration, order_number) VALUES
(2, 'HTML基础', '/videos/coffee.mp4', '08:45', 1),
(2, 'CSS样式', '/videos/coffee.mp4', '12:30', 2),
(2, 'JavaScript基础', '/videos/coffee.mp4', '18:20', 3),
(2, '响应式设计', '/videos/coffee.mp4', '14:10', 4);

-- 数据库设计与优化课程视频
INSERT INTO video (course_id, title, video_url, duration, order_number) VALUES
(3, '数据库基础', '/videos/coffee.mp4', '10:20', 1),
(3, 'SQL语法', '/videos/coffee.mp4', '15:30', 2),
(3, '索引优化', '/videos/coffee.mp4', '12:45', 3),
(3, '事务处理', '/videos/coffee.mp4', '14:20', 4);

-- Python数据分析课程视频
INSERT INTO video (course_id, title, video_url, duration, order_number) VALUES
(4, 'Python基础', '/videos/coffee.mp4', '12:30', 1),
(4, 'NumPy库', '/videos/coffee.mp4', '14:45', 2),
(4, 'Pandas库', '/videos/coffee.mp4', '18:20', 3),
(4, '数据可视化', '/videos/coffee.mp4', '16:15', 4);

-- 机器学习基础课程视频
INSERT INTO video (course_id, title, video_url, duration, order_number) VALUES
(5, '机器学习概述', '/videos/coffee.mp4', '08:30', 1),
(5, '监督学习', '/videos/coffee.mp4', '15:45', 2),
(5, '无监督学习', '/videos/coffee.mp4', '12:20', 3),
(5, '模型评估', '/videos/coffee.mp4', '14:10', 4);

-- 移动应用开发课程视频
INSERT INTO video (course_id, title, video_url, duration, order_number) VALUES
(6, 'React Native简介', '/videos/coffee.mp4', '09:15', 1),
(6, '组件开发', '/videos/coffee.mp4', '16:30', 2),
(6, '状态管理', '/videos/coffee.mp4', '14:45', 3),
(6, '网络请求', '/videos/coffee.mp4', '12:20', 4);
