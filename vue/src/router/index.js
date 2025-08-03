import {createRouter, createWebHistory} from 'vue-router'

// 创建一个路由实例
const router = createRouter({
    // 使用HTML5的history API来维护应用的历史记录
    history: createWebHistory(import.meta.env.BASE_URL),
    // 定义应用的路由配置
    routes: [
        // 根路径重定向到管理主页
        {path: '/', redirect: '/login'},
        // 管理员相关路由配置
        {
            path: '/manager', component: () => import('../views/Manager.vue'),
            children: [
                // 主页路由
                {path: 'home', meta: {name: '主页'}, component: () => import('../views/Home.vue'),},
                // 管理员信息路由
                {path: 'admin', meta: {name: '管理员信息'}, component: () => import('../views/Admin.vue')},
                // 用户（学生）信息路由
                {path: 'user', meta: {name: '用户(学生)信息'}, component: () => import('../views/User.vue')},
                // 个人信息路由
                {path: 'person', meta: {name: '个人信息'}, component: () => import('../views/Person.vue'),},
                // 修改密码路由
                {
                    path: 'updatePassword',
                    meta: {name: '修改密码'},
                    component: () => import('../views/UpdatePassword.vue'),
                },
                // 教师
                {path: 'teacher', meta: {name: '教师管理'}, component: () => import('../views/Teacher.vue')},
                {path: 'course', meta: {name: '课程管理'}, component: () => import('../views/Course.vue')},
                {path: 'score', meta: {name: '成绩管理'}, component: () => import('../views/Score.vue')},


            ]
        },
        // 登录路由配置
        {path: '/login', component: () => import('../views/Login.vue')},
        // 注册路由配置
        {path: '/register', component: () => import('../views/Register.vue')},
        // 404页面路由配置
        {path: '/notfound', component: () => import('../views/404.vue'),},
        // 捕获所有未匹配到的路径并重定向到404页面
        {path: '/:pathMatch(.*)', redirect: '/notFound'},
        {path: "/qr-confirm", name: "QrConfirm", component: () => import("@/views/QrConfirm.vue"),},
    ],
})

// 导出路由实例供其他模块使用
export default router

