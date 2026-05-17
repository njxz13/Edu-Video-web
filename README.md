# Edu-Video-Web

> 教育学习视频平台 - 基于 Spring Boot 3 + MyBatis-Plus 构建的在线教育视频分享与学习系统

## 项目简介

Edu-Video-Web 是一个功能完善的教育视频平台，旨在为教师和学生提供高质量的在线教学和学习体验。平台支持视频上传、分类浏览、搜索、用户管理、后台内容审核等核心功能。

## 技术栈

### 后端
- **Java 17**
- **Spring Boot 3.5.11**
- **MyBatis-Plus 3.5.5**
- **MySQL 8.0+**
- **Redis** (缓存支持)
- **Lombok** (代码简化)
- **HikariCP** (连接池)

### 前端
- **Vue.js** (前端框架)
- **HTML5 / CSS3 / JavaScript**
- **响应式设计**

### 开发工具
- **Maven** (构建工具)
- **Git** (版本控制)
- **IntelliJ IDEA** (推荐 IDE)

## 功能模块

### 已实现功能

| 模块 | 功能 |
|------|------|
| **用户认证** | 用户注册、登录、注销、个人中心、信息编辑 |
| **视频管理** | 视频上传、列表展示、详情页面、搜索功能 |
| **课程管理** | 课程创建、展示、章节管理 |
| **后台管理** | 管理员认证、数据统计仪表盘、用户管理、内容审核 |

### 规划中功能

- [ ] 评论系统（发布、回复、点赞）
- [ ] 收藏功能
- [ ] 点赞功能
- [ ] 学习进度跟踪
- [ ] Redis 缓存优化
- [ ] 视频转码服务
- [ ] 密码加密升级 (BCrypt)
- [ ] 图形验证码

## 项目结构

```
Edu-Video-Web/
├── src/main/java/com/example/Edu_Video_web/
│   ├── controller/          # 控制器层
│   │   ├── AdminController      # 后台管理接口
│   │   ├── CourseController     # 课程管理接口
│   │   ├── VideoController      # 视频管理接口
│   │   ├── LoginController      # 用户认证接口
│   │   └── homeController       # 首页接口
│   ├── service/             # 业务逻辑层（预留）
│   ├── mapper/              # 数据访问层
│   │   ├── AdminMapper          # 管理员数据访问
│   │   ├── UserMapper           # 用户数据访问
│   │   ├── VideoMapper          # 视频数据访问
│   │   ├── CourseMapper         # 课程数据访问
│   ├── entity/              # 实体类
│   │   ├── Admin                # 管理员实体
│   │   ├── User                 # 用户实体
│   │   ├── Video                # 视频实体
│   │   ├── Course               # 课程实体
│   │   └── Category             # 分类实体
│   ├── config/              # 配置类
│   │   ├── WebConfig            # Web 配置
│   │   ├── CorsConfig           # 跨域配置
│   │   └── RedisConfig          # Redis 配置
│   └── EduVideoWebApplication.java  # 启动类
├── src/main/resources/
│   ├── application.properties     # 应用配置
│   ├── mapper/                    # MyBatis XML 映射文件
│   ├── static/                    # 静态资源
│   └── db/                        # SQL 脚本
├── pom.xml                    # Maven 依赖配置
└── README.md                  # 项目文档
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis

### 安装步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/your-username/Edu-Video-Web.git
   cd Edu-Video-Web
   ```

2. **创建数据库**
   ```sql
   CREATE DATABASE edu-video CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **配置数据库连接**
   
   编辑 `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/edu-video?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **配置 Redis**
   ```properties
   spring.data.redis.host=localhost
   spring.data.redis.port=6379
   spring.data.redis.password=your_redis_password
   ```

5. **导入数据库表结构**
   
   执行 `src/main/resources/db/` 目录下的 SQL 脚本创建数据表。

6. **构建项目**
   ```bash
   mvn clean install
   ```

7. **运行项目**
   ```bash
   mvn spring-boot:run
   ```
   
   或者打包后运行:
   ```bash
   mvn package
   java -jar target/Edu-Video-web-0.0.1-SNAPSHOT.jar
   ```

8. **访问应用**
   
   浏览器打开: `http://localhost:8080`

## 数据库设计

### 核心数据表

| 数据名 | 作用说明 | 归属 |
|--------|----------|------|
| `users` | 普通用户（学生/观众）账号信息，含用户名、密码、邮箱、头像、状态 | **用户模块** |
| `admins` | 管理员账号信息，含角色分级（super_admin / admin）、最后登录时间 | **管理员模块** |
| `course` | 课程信息，含标题、描述、封面图 | **课程模块** |
| `categories` | 视频分类，支持 parent_id 自引用实现多级树形分类 | **视频模块** |
| `videos` | 核心视频内容，含标题、URL、封面、上传者、分类、播放/点赞数、标签、状态 | **视频模块** |
| `video_comments` | 视频评论，支持 parent_id 实现楼中楼回复（待实现） | **视频模块** |

### 表关系说明

```
users ──┬── videos.uploader_id
         └── video_comments.user_id

categories ──┬── categories.parent_id（自引用层级）
              └── videos.category_id

course ── videos.course_id（旧版兼容）

videos ── video_comments.video_id
video_comments ── parent_id（自引用回复）
```

所有 SQL 脚本位于 `src/main/resources/db/`，按以下顺序执行：

1. `schema.sql` - 建 `users` 表
2. `admin_schema.sql` - 建 `admins` 表
3. `video_schema.sql` - 建 `categories`、`videos`、`video_comments` 表
4. `init.sql` - 建 `course` 表及初始数据

## API 接口

### 用户相关
- `POST /register` - 用户注册
- `POST /login` - 用户登录
- `GET /logout` - 用户注销
- `GET /profile` - 查看个人中心
- `POST /profile/update` - 编辑个人信息

### 视频相关
- `POST /video/upload` - 上传视频
- `GET /videos` - 获取视频列表
- `GET /video/{id}` - 获取视频详情
- `GET /search` - 搜索视频

### 课程相关
- `GET /courses` - 获取课程列表
- `GET /course/{id}` - 获取课程详情

### 后台管理
- `POST /admin/login` - 管理员登录
- `GET /admin/dashboard` - 数据统计仪表盘
- `GET /admin/users` - 用户管理列表
- `GET /admin/videos` - 内容审核管理

## 配置说明

### application.properties 主要配置项

```properties
# 应用名称和端口
spring.application.name=Edu-Video-web
server.port=8080

# MySQL 配置
spring.datasource.url=jdbc:mysql://localhost:3306/edu-video
spring.datasource.username=root
spring.datasource.password=1234

# Redis 配置
spring.data.redis.host=localhost
spring.data.redis.port=8989
spring.data.redis.password=1234

# MyBatis-Plus 配置
mybatis-plus.mapper-locations=classpath*:/mapper/**/*.xml
mybatis-plus.type-aliases-package=com.example.Edu_Video_web.entity
mybatis-plus.configuration.map-underscore-to-camel-case=true
```

## 部署指南

### 开发环境
```
用户请求 → Spring Boot 应用 → MySQL 数据库
                    → Redis 缓存
```

### 生产环境（推荐）
```
用户请求 → Nginx（反向代理/负载均衡）
        → Spring Boot 集群
        → MySQL 主从复制
        → Redis 缓存集群
        → OSS 对象存储（视频文件）
```

### 服务器配置建议

| 环境 | CPU | 内存 | 硬盘 | 带宽 |
|------|-----|------|------|------|
| 开发环境 | 2 核 | 4GB | 40GB | 1Mbps |
| 生产环境（初期） | 4 核 | 8GB | 100GB SSD | 5Mbps |
| 生产环境（中期） | 8 核 × 2 | 16GB × 2 | 200GB SSD | 10Mbps+ |

## 项目进度

| 阶段 | 状态 | 说明 |
|------|------|------|
| 基础框架搭建 | ✅ 已完成 | Spring Boot 初始化、MyBatis-Plus 集成 |
| 用户认证模块 | ✅ 已完成 | 注册、登录、个人中心 |
| 视频管理模块 | ✅ 已完成 | 上传、列表、详情、搜索 |
| 后台管理模块 | ✅ 已完成 | 管理员认证、仪表盘、用户/内容管理 |
| 课程管理模块 | ✅ 已完成 | 课程创建、展示 |
| 互动功能 | 规划中 | 评论、收藏、点赞 |
| 性能优化 | 规划中 | Redis 缓存、查询优化、CDN |
| 安全加固 | 规划中 | BCrypt 加密、验证码、防刷 |
| 部署上线 | 规划中 | 服务器配置、HTTPS、监控 |

## 常见问题

### Q: 启动时数据库连接失败?
A: 请检查 MySQL 服务是否运行，以及 `application.properties` 中的数据库连接配置是否正确。

### Q: Redis 连接失败?
A: 请确保 Redis 服务已启动，并检查端口和密码配置。如暂不需要 Redis 功能，可注释相关配置。

### Q: 视频上传失败?
A: 检查上传目录是否有写入权限，以及文件大小是否超过限制。

## 贡献指南

欢迎提交 Issue 和 Pull Request 来帮助改进项目！

1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

## 联系方式

- 项目维护者：开发团队
- 问题反馈：提交 GitHub Issue

---

**文档版本**: v1.0  
**最后更新**: 2026 年 5 月 3 日
