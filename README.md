# EduCore 教务管理系统

## 项目概述

> 本系统是一个基于 **Spring Boot + Vue** 构建的前后端分离的 EduCore 教务管理系统。后端使用 **Java 17+** 编写，采用 *
*Spring Boot** 框架；前端使用 **Vue 3 + Vite** 开发，UI 支持响应式布局；数据库使用 **MySQL**。

* 系统实现了完整的用户管理、成绩管理、通知公告、操作日志记录等功能，并引入了以下增强特性：
    - JWT 鉴权机制
    - 审计日志记录（通过注解与切面实现）
    - 文件上传支持（头像、Excel 成绩导入等）
    - 多角色权限控制（管理员、教师、学生）

---

## 目录

- [项目概述](#项目概述)
- [项目结构](#项目结构)
    - [后端 (`springboot`)](#后端-springboot)
    - [前端 ([vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\App.vue))](#前端-vue)
- [技术栈](#技术栈)
- [功能列表](#功能列表)
- [数据库设计](#数据库设计)
- [开发部署指南](#开发部署指南)
- [最近更新](#最近更新)

## 项目结构

~~~
EduCore/
├── .git/
├── .idea/
├── files/
├── springboot/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           ├── SpringbootApplication.java
│   │   │   │           ├── common/
│   │   │   │           │   ├── advice/
│   │   │   │           │   │   └── GlobalResponseAdvice.java
│   │   │   │           │   ├── annotation/
│   │   │   │           │   │   ├── AuditLogRecord.java
│   │   │   │           │   │   ├── DataScope.java
│   │   │   │           │   │   └── RequiresPermission.java
│   │   │   │           │   ├── config/
│   │   │   │           │   │   ├── CorsConfig.java
│   │   │   │           │   │   ├── JacksonConfig.java
│   │   │   │           │   │   ├── JwtProperties.java
│   │   │   │           │   │   ├── MyBatisConfig.java
│   │   │   │           │   │   ├── RedisConfig.java
│   │   │   │           │   │   └── SecurityConfig.java
│   │   │   │           │   ├── dto/
│   │   │   │           │   │   └── PageRequestDTO.java
│   │   │   │           │   ├── exception/
│   │   │   │           │   │   ├── CustomerException.java
│   │   │   │           │   │   └── GlobalExceptionHandler.java
│   │   │   │           │   ├── result/
│   │   │   │           │   │   ├── PageResult.java
│   │   │   │           │   │   ├── R.java
│   │   │   │           │   │   └── ResultCodeEnum.java
│   │   │   │           │   └── utils/
│   │   │   │           │       ├── JwtUtil.java
│   │   │   │           │       ├── MixUtils.java
│   │   │   │           │       └── TokenUtils.java
│   │   │   │           ├── core/
│   │   │   │           │   ├── controller/
│   │   │   │           │   │   └── BaseController.java
│   │   │   │           │   ├── dto/
│   │   │   │           │   ├── mapper/
│   │   │   │           │   │   └── BaseMapper.java
│   │   │   │           │   ├── service/
│   │   │   │           │   │   ├── BaseService.java
│   │   │   │           │   │   └── impl/
│   │   │   │           │   └── utils/
│   │   │   │           ├── enums/
│   │   │   │           │   └── RoleEnum.java
│   │   │   │           ├── log/
│   │   │   │           │   └── aspect/
│   │   │   │           │       └── AuditLogAspect.java
│   │   │   │           ├── modules/
│   │   │   │           │   └── system/
│   │   │   │           ├── security/
│   │   │   │           │   └── JwtAuthenticationFilter.java
│   │   │   │           └── strategy/
│   │   │   │               ├── Context/
│   │   │   │               │   └── RoleStrategyContext.java
│   │   │   │               ├── RoleStrategy.java
│   │   │   │               └── impl/
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       ├── mapper/
│   │   │       │   ├── AdminMapper.xml
│   │   │       │   ├── CourseMapper.xml
│   │   │       │   ├── ScoreMapper.xml
│   │   │       │   ├── StudentMapper.xml
│   │   │       │   ├── TeacherMapper.xml
│   │   │       │   └── UserMapper.xml
│   │   │       └── sql/
│   │   │           └── learnscore.sql
│   │   └── test/
│   ├── target/
│   └── pom.xml
└── vue/
    ├── node_modules/
    ├── public/
    ├── src/
    │   ├── api/
    │   │   └── user.js
    │   ├── assets/
    │   │   ├── css/
    │   │   │   ├── global.css
    │   │   │   └── index.scss
    │   │   └── images/
    │   ├── components/
    │   ├── router/
    │   │   └── index.js
    │   ├── utils/
    │   │   └── request.js
    │   ├── views/
    │   │   ├── 404.vue
    │   │   ├── Admin.vue
    │   │   ├── Course.vue
    │   │   ├── Home.vue
    │   │   ├── Login.vue
    │   │   ├── Manager.vue
    │   │   ├── Person.vue
    │   │   ├── QrConfirm.vue
    │   │   ├── Register.vue
    │   │   ├── Score.vue
    │   │   ├── Teacher.vue
    │   │   ├── UpdatePassword.vue
    │   │   └── User.vue
    │   ├── App.vue
    │   └── main.js
    ├── .env
    ├── .env.production
    ├── index.html
    ├── jsconfig.json
    ├── package-lock.json
    ├── package.json
    ├── vite.config.js
    └── vue.iml

~~~


---

### 后端 (`springboot`)

#### 主要模块

| 包名                           | 功能描述                                                                                                                                                                      |
|------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `common`                     | 通用类，包括返回结果封装、注解、配置、异常处理、工具类等                                                                                                                                              |
| `common.annotation`          | 自定义注解，如 [@AuditLogRecord](file://C:\Users\Administrator\Desktop\EduCore\springboot\src\main\java\com\example\common\annotation\AuditLogRecord.java#L15-L20) 用于标记需要记录日志的方法 |
| `common.config`              | 系统配置类，如跨域配置、拦截器、Jackson 序列化配置等                                                                                                                                            |
| `common.exception`           | 异常处理类，包括自定义异常和全局异常处理器                                                                                                                                                     |
| `common.utils`               | 工具类，如 [TokenUtils](file://C:\Users\Administrator\Desktop\EduCore\springboot\src\main\java\com\example\common\utils\TokenUtils.java#L21-L80) 处理 JWT 令牌                     |
| `enums`                      | 枚举类，如角色枚举等                                                                                                                                                                |
| `log.aspect`                 | 切面类，如 [AuditLogAspect](file://C:\Users\Administrator\Desktop\EduCore\springboot\src\main\java\com\example\log\aspect\AuditLogAspect.java#L23-L72) 实现操作日志记录逻辑              |
| `modules.system.controller`  | 控制器类，处理 HTTP 请求                                                                                                                                                           |
| `modules.system.dto`         | 数据传输对象，用于接收请求参数                                                                                                                                                           |
| `modules.system.entity`      | 实体类，与数据库表一一映射                                                                                                                                                             |
| `modules.system.mapper`      | MyBatis 映射接口，用于数据库操作                                                                                                                                                      |
| `modules.system.service`     | 业务逻辑接口及其实现类                                                                                                                                                               |
| `security`                   | 安全相关类，如 JWT 过滤器、鉴权逻辑                                                                                                                                                      |
| `strategy`                   | 策略模式实现的扩展逻辑                                                                                                                                                               |
| `strategy.Context`           | 策略上下文类                                                                                                                                                                    |
| `strategy.impl`              | 策略具体实现类                                                                                                                                                                   |
| [SpringbootApplication.java](file://C:\Users\Administrator\Desktop\EduCore\springboot\src\main\java\com\example\SpringbootApplication.java) | Spring Boot 启动类                                                                                                                                                           |

#### 配置文件

- [application.yml](file://C:\Users\Administrator\Desktop\EduCore\springboot\src\main\resources\application.yml)：Spring Boot 主配置文件
- [pom.xml](file://C:\Users\Administrator\Desktop\EduCore\springboot\pom.xml)：Maven 依赖配置文件

#### SQL 脚本

- `sql/learnscore.sql`：数据库初始化脚本

---

### 前端 ([vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\App.vue))

#### 主要模块

| 目录                     | 功能描述                                                                                                                                                                                                                                       |
|------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `src/api`              | API 接口封装，如 [user.js](file://C:\Users\Administrator\Desktop\EduCore\vue\src\api\user.js) 定义用户相关的请求                                                                                                                                                                                                             |
| `src/assets/css`       | 全局样式文件和 SCSS 变量                                                                                                                                                                                                                            |
| `src/router/index.js`  | Vue 路由配置                                                                                                                                                                                                                                   |
| `src/utils/request.js` | 封装 Axios 请求                                                                                                                                                                                                                                |
| `src/views/`           | 页面组件目录，包括： <br> - [Login.vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\views\Login.vue) 登录页 <br> - [Register.vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\views\Register.vue) 注册页 <br> - [Home.vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\views\Home.vue) 主页 <br> - [User.vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\views\User.vue) 用户管理 <br> - [Admin.vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\views\Admin.vue) 管理员管理 <br> - [UpdatePassword.vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\views\UpdatePassword.vue) 修改密码 <br> - [Person.vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\views\Person.vue) 个人信息 <br> - [Manager.vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\views\Manager.vue) 管理界面 <br> - [404.vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\views\404.vue) 错误页面 |
| [App.vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\App.vue)              | 根组件                                                                                                                                                                                                                                        |
| [main.js](file://C:\Users\Administrator\Desktop\EduCore\vue\src\main.js)              | Vue 入口文件                                                                                                                                                                                                                                   |
| [index.html](file://C:\Users\Administrator\Desktop\EduCore\vue\index.html)           | HTML 模板                                                                                                                                                                                                                                    |
| [package.json](file://C:\Users\Administrator\Desktop\EduCore\vue\package.json)         | npm 包配置                                                                                                                                                                                                                                    |
| [vite.config.js](file://C:\Users\Administrator\Desktop\EduCore\vue\vite.config.js)       | Vite 构建配置                                                                                                                                                                                                                                  |

## 技术栈

### 后端

- **Java 17+**
- **Spring Boot 3.4.X**
- **MyBatis**
- **JWT（JSON Web Token）**
- **MySQL**
- **Redis**
- **Lombok**
- **Swagger/OpenAPI** - API文档生成工具

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

数据库名称：`learnscore`

主要表：

- `user`：用户信息表
- `admin`：管理员信息表
- [account](file://C:\Users\Administrator\Desktop\EduCore\springboot\src\main\java\com\example\modules\system\dto\LoginResult.java#L7-L7)：账号信息表
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
5. 修改 [application.yml](file://C:\Users\Administrator\Desktop\EduCore\springboot\src\main\resources\application.yml) 中的数据库连接信息
6. 执行 `mvn spring-boot:run` 启动服务

### 前端部署

1. 安装 Node.js 18+
2. 进入 [vue](file://C:\Users\Administrator\Desktop\EduCore\vue\src\App.vue) 目录
3. 执行 `npm install`
4. 执行 `npm run dev` 启动开发服务器

---

## API文档

本项目使用 Swagger/OpenAPI 来生成 API 文档，便于开发者理解和使用系统的各种接口。

### 使用的技术

项目中使用了以下与 API 文档相关的依赖：

1. **SpringDoc OpenAPI** - 用于生成符合 OpenAPI 3.0 规范的 API 文档
2. **Swagger Annotations** - 用于在代码中添加 API 文档注解
3. **Springfox** - 另一个 Swagger 集成库（备用）

### 访问方式

启动项目后，可以通过以下 URL 访问 API 文档：

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### 文档特点

- 自动生成 API 文档，无需手动编写
- 支持在线测试 API 接口
- 提供详细的接口说明、参数说明和示例
- 各个控制器方法都使用了 `@ApiOperation` 注解来描述接口功能

---

## 最近更新

### 最新功能增强与修复

1. **系统整体优化**：
    - 完善了系统各模块的功能实现
    - 优化了代码结构和注释规范
    - 提升了系统的稳定性和可维护性

2. **API文档增强**：
    - 添加了完整的Swagger/OpenAPI集成支持
    - 提供了详细的API文档说明和访问方式
    - 支持在线测试系统接口功能

3. **代码质量提升**：
    - 统一了所有方法的注释风格，符合JavaDoc标准
    - 优化了变量命名规范，提高代码可读性
    - 确保所有重写方法都添加了@Override注解

4. **系统稳定性改进**：
    - 修复了Mapper接口中方法重载导致的Mapped Statements key冲突问题
    - 解决了因接口方法冲突导致的服务器启动失败问题
    - 优化了Spring Boot应用上下文初始化过程

### 新增文件和功能

1. **StudentMapper.java** 中添加了新的方法：
    - `void updateById(Long id);` - 根据学生ID更新学生信息

2. **AdminServiceImpl.java** 代码优化：
    - 修复了重复代码问题
    - 统一了方法注释格式
    - 规范了变量命名

3. **代码注释完善**：
    - 为StudentMapper中的updateById方法添加了完整的JavaDoc注释
    - 统一了所有方法的注释风格

4. **代码结构优化**：
    - 重构了AdminServiceImpl中的重复逻辑
    - 完善了所有接口方法的实现
    - 保证了代码的一致性和可维护性

### 进一步的代码改进

1. **目录结构更新**：
    - 更新了README.md中的项目目录结构，确保与实际项目结构一致

2. **代码质量提升**：
    - 修复了AdminServiceImpl中updateById方法的参数类型问题
    - 统一了所有方法的注释风格，确保符合JavaDoc标准
    - 优化了变量命名规范，如将InputPassWord改为inputPassword

3. **文档更新**：
    - 完善了README.md文档，包含了最新的项目结构和更新记录
    - 提供了更详细的模块说明和功能描述

4. **代码一致性改进**：
    - 确保所有重写方法都添加了@Override注解
    - 统一了所有服务实现类的代码风格
    - 优化了方法参数和返回值的注释说明

5. **API文档增强**：
    - 添加了关于Swagger/OpenAPI的详细说明
    - 介绍了API文档的访问方式和特点
    - 列出了项目中使用的相关技术依赖

### 最新修复和改进

1. **Mapper接口修复**：
    - 修复了AdminMapper中selectById方法的重复定义问题，删除了String类型参数的重载方法
    - 修复了UserMapper中selectById和deleteById方法的重复定义问题，统一使用Long类型参数

2. **MyBatis配置优化**：
    - 解决了由于Mapper接口中方法重载导致的Mapped Statements key冲突问题
    - 确保MyBatis能够正确解析和注册Mapper XML文件和注解

3. **系统稳定性提升**：
    - 修复了因Mapper接口方法冲突导致的Tomcat服务器启动失败问题
    - 解决了Spring Boot应用上下文初始化时的依赖注入异常

### 最新代码修复

1. **接口参数一致性修复**：
    - 修复了AdminService接口中selectById方法参数类型不一致的问题，统一使用Integer类型
    - 解决了NotificationController中delete方法与BaseController中同名方法返回值类型冲突的问题
    - 确保子类方法与父类方法签名保持一致，避免编译错误


