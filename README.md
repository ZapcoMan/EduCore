# EduCore 教务管理系统

## 项目概述

本系统是一个基于 **Spring Boot + Vue** 构建的前后端分离的 EduCore 教务管理系统。后端使用 **Java 17+** 编写，采用 **Spring Boot** 框架；前端使用 **Vue 3 + Vite** 开发，UI 支持响应式布局；数据库使用 **MySQL**。

系统实现了完整的用户管理、成绩管理、通知公告、操作日志记录等功能，并引入了以下增强特性：

- JWT 鉴权机制
- 审计日志记录（通过注解与切面实现）
- 文件上传支持（头像、Excel 成绩导入等）
- 多角色权限控制（管理员、教师、学生）

---

## 目录

- [项目概述](#项目概述)
- [项目结构](#项目结构)
    - [后端 (`springboot`)](#后端-springboot)
    - [前端 (`vue`)](#前端-vue)
- [技术栈](#技术栈)
- [功能列表](#功能列表)
- [数据库设计](#数据库设计)
- [开发部署指南](#开发部署指南)

## 项目结构


~~~
EduCore/
├── springboot/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/
│   │   │   │   ├── SpringbootApplication.java
│   │   │   │   ├── common/
│   │   │   │   │   ├── annotation/
│   │   │   │   │   │   └── AuditLogRecord.java
│   │   │   │   │   ├── config/
│   │   │   │   │   │   ├── CorsConfig.java
│   │   │   │   │   │   ├── JacksonConfig.java
│   │   │   │   │   │   ├── RedisConfig.java
│   │   │   │   │   │   └── SecurityConfig.java
│   │   │   │   │   ├── exception/
│   │   │   │   │   │   ├── CustomerException.java
│   │   │   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │   │   ├── result/
│   │   │   │   │   │   ├── R.java
│   │   │   │   │   │   └── ResultCodeEnum.java
│   │   │   │   │   └── utils/
│   │   │   │   │       ├── MixUtils.java
│   │   │   │   │       └── TokenUtils.java
│   │   │   │   ├── enums/
│   │   │   │   │   └── RoleEnum.java
│   │   │   │   ├── log/
│   │   │   │   │   └── aspect/
│   │   │   │   │       └── AuditLogAspect.java
│   │   │   │   ├── modules/
│   │   │   │   │   └── system/
│   │   │   │   │       ├── controller/
│   │   │   │   │       │   ├── AdminController.java
│   │   │   │   │       │   ├── AuditLogController.java
│   │   │   │   │       │   ├── CourseController.java
│   │   │   │   │       │   ├── FileController.java
│   │   │   │   │       │   ├── LoginQrCodeController.java
│   │   │   │   │       │   ├── NotificationController.java
│   │   │   │   │       │   ├── ScoreController.java
│   │   │   │   │       │   ├── StudentController.java
│   │   │   │   │       │   ├── TeacherController.java
│   │   │   │   │       │   ├── UserController.java
│   │   │   │   │       │   └── WebController.java
│   │   │   │   │       ├── dto/
│   │   │   │   │       │   └── ConfirmDto.java
│   │   │   │   │       ├── entity/
│   │   │   │   │       │   ├── Account.java
│   │   │   │   │       │   ├── Admin.java
│   │   │   │   │       │   ├── AuditLog.java
│   │   │   │   │       │   ├── Course.java
│   │   │   │   │       │   ├── Notification.java
│   │   │   │   │       │   ├── Score.java
│   │   │   │   │       │   ├── Student.java
│   │   │   │   │       │   ├── Teacher.java
│   │   │   │   │       │   └── User.java
│   │   │   │   │       ├── mapper/
│   │   │   │   │       │   ├── AdminMapper.java
│   │   │   │   │       │   ├── CourseMapper.java
│   │   │   │   │       │   ├── ScoreMapper.java
│   │   │   │   │       │   ├── StudentMapper.java
│   │   │   │   │       │   ├── TeacherMapper.java
│   │   │   │   │       │   ├── UserMapper.java
│   │   │   │   │       │   └── ... (其他Mapper)
│   │   │   │   │       └── service/
│   │   │   │   │           ├── AdminService.java
│   │   │   │   │           ├── CourseService.java
│   │   │   │   │           ├── ScoreService.java
│   │   │   │   │           ├── StudentService.java
│   │   │   │   │           ├── TeacherService.java
│   │   │   │   │           ├── UserService.java
│   │   │   │   │           └── impl/
│   │   │   │   │               ├── AdminServiceImpl.java
│   │   │   │   │               ├── CourseServiceImpl.java
│   │   │   │   │               ├── ScoreServiceImpl.java
│   │   │   │   │               ├── StudentServiceImpl.java
│   │   │   │   │               ├── TeacherServiceImpl.java
│   │   │   │   │               └── UserServiceImpl.java
│   │   │   │   ├── security/
│   │   │   │   │   └── JwtAuthenticationFilter.java
│   │   │   │   └── strategy/
│   │   │   │       ├── Context/
│   │   │   │       │   └── RoleStrategyContext.java
│   │   │   │       ├── RoleStrategy.java
│   │   │   │       └── impl/
│   │   │   │           ├── AdminStrategy.java
│   │   │   │           ├── TeacherStrategy.java
│   │   │   │           └── UserStrategy.java
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── mapper/
│   │   │           ├── AdminMapper.xml
│   │   │           ├── CourseMapper.xml
│   │   │           ├── ScoreMapper.xml
│   │   │           ├── StudentMapper.xml
│   │   │           ├── TeacherMapper.xml
│   │   │           └── UserMapper.xml
│   │   └── test/
│   ├── pom.xml
│   └── target/
├── vue/
│   ├── index.html
│   ├── package.json
│   ├── package-lock.json
│   ├── vite.config.js
│   ├── jsconfig.json
│   ├── .env
│   ├── .env.production
│   ├── public/
│   └── src/
│       ├── main.js
│       ├── App.vue
│       ├── api/
│       │   └── user.js
│       ├── assets/
│       │   ├── css/
│       │   │   ├── global.css
│       │   │   └── index.scss
│       │   └── images/
│       ├── components/
│       ├── router/
│       │   └── index.js
│       ├── utils/
│       │   └── request.js
│       └── views/
│           ├── 404.vue
│           ├── Admin.vue
│           ├── Course.vue
│           ├── Home.vue
│           ├── Login.vue
│           ├── Manager.vue
│           ├── Person.vue
│           ├── QrConfirm.vue
│           ├── Register.vue
│           ├── Score.vue
│           ├── Teacher.vue
│           ├── UpdatePassword.vue
│           └── User.vue
├── sql/
│   └── learnscore.sql
├── files/
├── .git/
├── .gitignore
├── .idea/
└── README.md
~~~

---

### 后端 (`springboot`)

#### 主要模块

| 包名                           | 功能描述                                                                                                                                                                    |
|------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `common`                     | 通用类，包括返回结果封装、注解、配置、异常处理、工具类等                                                                                                                                            |
| `common.annotation`          | 自定义注解，如 [@AuditLogRecord](file://C:\Users\Administrator\Desktop\grade-system\springboot\src\main\java\com\example\annotation\AuditLogRecord.java#L15-L20) 用于标记需要记录日志的方法 |
| `common.config`              | 系统配置类，如跨域配置、拦截器、Jackson 序列化配置等                                                                                                                                          |
| `common.exception`           | 异常处理类，包括自定义异常和全局异常处理器                                                                                                                                                   |
| `common.utils`               | 工具类，如 [TokenUtils](file://C:\Users\Administrator\Desktop\grade-system\springboot\src\main\java\com\example\utils\TokenUtils.java#L21-L80) 处理 JWT 令牌                     |
| `enums`                      | 枚举类，如角色枚举等                                                                                                                                                              |
| `log.aspect`                 | 切面类，如 [AuditLogAspect](file://C:\Users\Administrator\Desktop\grade-system\springboot\src\main\java\com\example\log\aspect\AuditLogAspect.java#L23-L72) 实现操作日志记录逻辑       |
| `modules.system.controller`  | 控制器类，处理 HTTP 请求                                                                                                                                                         |
| `modules.system.dto`         | 数据传输对象，用于接收请求参数                                                                                                                                                         |
| `modules.system.entity`      | 实体类，与数据库表一一映射                                                                                                                                                           |
| `modules.system.mapper`      | MyBatis 映射接口，用于数据库操作                                                                                                                                                    |
| `modules.system.service`     | 业务逻辑接口及其实现类                                                                                                                                                             |
| `security`                   | 安全相关类，如 JWT 过滤器、鉴权逻辑                                                                                                                                                    |
| `strategy`                   | 策略模式实现的扩展逻辑                                                                                                                                                             |
| `strategy.Context`           | 策略上下文类                                                                                                                                                                  |
| `strategy.impl`              | 策略具体实现类                                                                                                                                                                 |
| `SpringbootApplication.java` | Spring Boot 启动类                                                                                                                                                         |
|

#### 配置文件

- `application.yml`：Spring Boot 主配置文件
- `pom.xml`：Maven 依赖配置文件

#### SQL 脚本

- `sql/learnscore.sql`：数据库初始化脚本

---

### 前端 (`vue`)

#### 主要模块

| 目录                     | 功能描述                                                                                                                                                                                                                                       |
|------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `src/api`              | API 接口封装，如 `user.js` 定义用户相关的请求                                                                                                                                                                                                             |
| `src/assets/css`       | 全局样式文件和 SCSS 变量                                                                                                                                                                                                                            |
| `src/router/index.js`  | Vue 路由配置                                                                                                                                                                                                                                   |
| `src/utils/request.js` | 封装 Axios 请求                                                                                                                                                                                                                                |
| `src/views/`           | 页面组件目录，包括： <br> - `Login.vue` 登录页 <br> - `Register.vue` 注册页 <br> - `Home.vue` 主页 <br> - `User.vue` 用户管理 <br> - `Admin.vue` 管理员管理 <br> - `UpdatePassword.vue` 修改密码 <br> - `Person.vue` 个人信息 <br> - `Manager.vue` 管理界面 <br> - `404.vue` 错误页面 |
| `App.vue`              | 根组件                                                                                                                                                                                                                                        |
| `main.js`              | Vue 入口文件                                                                                                                                                                                                                                   |
| `index.html`           | HTML 模板                                                                                                                                                                                                                                    |
| `package.json`         | npm 包配置                                                                                                                                                                                                                                    |
| `vite.config.js`       | Vite 构建配置                                                                                                                                                                                                                                  |

---

## 技术栈

### 后端

- **Java 17+**
- **Spring Boot 3.4.X**
- **MyBatis**
- **JWT（JSON Web Token）**
- **MySQL**
- **Redis**
- **Lombok**

### 前端

- **Vue 3**
- **Vite**
- **Axios**
- **Element Plus**（UI 框架）
- **SCSS / CSS Modules**

---

## 功能列表

| 功能模块 | 描述                     |
|------|------------------------|
| 用户登录 | 使用 JWT 进行身份验证，支持多角色登录  |
| 成绩管理 | 支持查看、添加、修改、删除学生成绩      |
| 通知公告 | 管理员可以发布通知，用户可查看        |
| 日志记录 | 所有操作记录在审计日志中，可通过注解灵活控制 |
| 文件上传 | 支持头像上传、批量 Excel 导入成绩   |
| 权限控制 | 不同角色（如管理员、教师、学生）拥有不同权限 |

---

## 数据库设计

数据库名称：`grade_system`

主要表：

- `user`：用户信息表
- `admin`：管理员信息表
- `account`：账号信息表
- `notification`：通知公告表
- `audit_log`：审计日志表

详细字段请参考 `sql/learnscore.sql`。

---

## 开发部署指南

### 后端部署

1. 安装 JDK 17+
2. 安装 Maven
3. 安装 MySQL
4. 导入 `sql/learnscore.sql` 到数据库
5. 修改 `application.yml` 中的数据库连接信息
6. 执行 `mvn spring-boot:run` 启动服务

### 前端部署

1. 安装 Node.js 18+
2. 进入 `vue` 目录
3. 执行 `npm install`
4. 执行 `npm run dev` 启动开发服务器
