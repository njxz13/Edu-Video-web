# Edu-Video-Web 前端 (Vue 3)

## 项目简介
这是教育视频网站的前端项目，使用 Vue 3 + Vite 构建。

## 技术栈
- Vue 3 (Composition API)
- Vue Router 4
- Pinia (状态管理)
- Axios (HTTP请求)
- Vite (构建工具)

## 项目结构
```
frontend/
├── src/
│   ├── api/           # API接口
│   │   ├── auth.js    # 认证相关
│   │   ├── video.js  # 视频相关
│   │   ├── course.js # 课程相关
│   │   └── admin.js  # 管理后台
│   ├── views/         # 页面组件
│   ├── router/        # 路由配置
│   ├── store/         # Pinia状态
│   ├── App.vue
│   └── main.js
├── vite.config.js
└── package.json
```

## 开发运行
```bash
npm install
npm run dev
```
前端运行在: http://localhost:3000

## 后端API
后端Spring Boot运行在: http://localhost:8080
API前缀: /api

## 主要功能
1. 用户认证 (登录/注册/个人信息)
2. 课程浏览和视频播放
3. 视频上传
4. 管理后台 (用户管理/视频管理/数据统计)

## 注意事项
- 需要后端Spring Boot项目同时运行
- 跨域已在后端配置 (CORS)
- 会话使用HttpSession，需要withCredentials: true
