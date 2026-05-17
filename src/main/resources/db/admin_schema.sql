-- 后台管理模块数据库表
-- 注意：密码会在项目首次启动时由 DataInitializer 自动升级为 BCrypt 加密

USE `edu-video`;

DROP TABLE IF EXISTS `admins`;

CREATE TABLE `admins` (
  `admin_id` INT NOT NULL AUTO_INCREMENT COMMENT '管理员 ID',
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `role` VARCHAR(20) DEFAULT 'admin' COMMENT '角色（super_admin:超级管理员，admin:普通管理员）',
  `status` TINYINT DEFAULT 1 COMMENT '状态（1:正常 0:禁用）',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`admin_id`),
  INDEX `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

INSERT INTO `admins` (`username`, `password`, `email`, `role`, `status`) VALUES
('admin', 'admin123', 'admin@example.com', 'super_admin', 1),
('manager', 'manager123', 'manager@example.com', 'admin', 1);
