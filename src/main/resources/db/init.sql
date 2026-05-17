-- 创建课程表
DROP TABLE IF EXISTS course;

CREATE TABLE IF NOT EXISTS course (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    cover_image VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 视频数据直接写入 videos 表（由 video_schema.sql 创建）
-- 插入课程数据
INSERT INTO course (title, description, cover_image) VALUES
('Java编程入门', '从零开始学习Java编程，适合初学者的完整教程。', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=java%20programming%20course%20cover&image_size=square'),
('前端开发实战', 'HTML、CSS、JavaScript全掌握，构建现代网页应用。', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=frontend%20development%20course%20cover&image_size=square'),
('数据库设计与优化', '深入理解关系型数据库原理及SQL语言应用。', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=database%20design%20course%20cover&image_size=square'),
('Python数据分析', '使用Python进行数据处理和可视化分析。', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=python%20data%20analysis%20course%20cover&image_size=square'),
('机器学习基础', '了解机器学习基本概念和常用算法。', 'https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=machine%20learning%20course%20cover&image_size=square'),
('移动应用开发', '使用React Native开发跨平台移动应用。', '/pictures/mobile_dev.png');

-- 插入视频数据到 videos 表（与 video_schema.sql 中的 videos 表一致）
INSERT INTO videos (title, description, video_url, cover_url, uploader_id, uploader_name, course_id, order_number, category_id, category_name, duration, tags, status) VALUES
('Java概述', 'Java编程语言概述', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 1, 1, 7, 'Java 开发', 330, 'java,入门', 1),
('环境搭建', 'Java开发环境搭建', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 1, 2, 7, 'Java 开发', 615, 'java,环境搭建', 1),
('基本语法', 'Java基本语法', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 1, 3, 7, 'Java 开发', 945, 'java,语法基础', 1),
('面向对象编程', 'Java面向对象编程', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 1, 4, 7, 'Java 开发', 1220, 'java,面向对象', 1),

('HTML基础', 'HTML基础教程', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 2, 1, 9, '前端开发', 525, 'html,前端', 1),
('CSS样式', 'CSS样式设计', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 2, 2, 9, '前端开发', 750, 'css,前端', 1),
('JavaScript基础', 'JavaScript编程基础', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 2, 3, 9, '前端开发', 1100, 'javascript,前端', 1),
('响应式设计', '响应式网页设计', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 2, 4, 9, '前端开发', 850, '响应式,css,前端', 1),

('数据库基础', '关系型数据库基础', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 3, 1, 1, '编程开发', 620, '数据库,sql', 1),
('SQL语法', 'SQL查询语言语法', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 3, 2, 1, '编程开发', 930, 'sql,数据库', 1),
('索引优化', '数据库索引优化', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 3, 3, 1, '编程开发', 765, '索引,优化,数据库', 1),
('事务处理', '数据库事务管理', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 3, 4, 1, '编程开发', 860, '事务,数据库', 1),

('Python基础', 'Python编程基础', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 4, 1, 8, 'Python 开发', 750, 'python,基础', 1),
('NumPy库', 'NumPy科学计算库', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 4, 2, 8, 'Python 开发', 885, 'numpy,python', 1),
('Pandas库', 'Pandas数据分析库', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 4, 3, 8, 'Python 开发', 1100, 'pandas,python', 1),
('数据可视化', 'Python数据可视化', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 4, 4, 8, 'Python 开发', 975, '可视化,python', 1),

('机器学习概述', '机器学习概念简介', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 5, 1, 1, '编程开发', 510, '机器学习,ai', 1),
('监督学习', '监督学习算法', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 5, 2, 1, '编程开发', 945, '监督学习,机器学习', 1),
('无监督学习', '无监督学习算法', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 5, 3, 1, '编程开发', 740, '无监督学习,机器学习', 1),
('模型评估', '机器学习模型评估', '/uploads/videos/coffee.mp4', NULL, 1, 'admin', 5, 4, 1, '编程开发', 850, '模型评估,机器学习', 1),

('React Native简介', '跨平台开发框架介绍', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 6, 1, 1, '编程开发', 555, 'react native,移动开发', 1),
('组件开发', 'React Native组件', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 6, 2, 1, '编程开发', 990, 'react native,组件', 1),
('状态管理', 'React Native状态管理', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 6, 3, 1, '编程开发', 885, 'react native,状态管理', 1),
('网络请求', 'React Native网络请求', '/uploads/videos/coffee.mp4', NULL, 2, 'test', 6, 4, 1, '编程开发', 740, 'react native,网络请求', 1);
