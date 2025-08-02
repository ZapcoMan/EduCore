<template>
  <div>
    <!-- 头部区域开始 -->
    <div style="height: 60px; display: flex;">
      <!-- LOGO区 -->
      <div
          style="width: 240px; display: flex; align-items: center; padding-left: 20px; background-color: var(--menu-bg)"
      >
        <img
            style="width: 40px; height: 40px; border-radius: 50%"
            src="@/assets/imgs/logo.png"
            alt="logo"
        />
        <span
            style="font-size: 20px; font-weight: bold; color: var(--text-color-light); margin-left: 5px"
        >毕业设计</span>
      </div>

      <!-- 标题区 -->
      <div class="header-section" style="flex: 1;">
        <p>脚手架</p>
      </div>


      <!-- 用户信息及下拉菜单 -->
      <div class="header-section" style="width: fit-content; padding-right: 20px;">
        <el-dropdown>
          <div style="display: flex; align-items: center; cursor: pointer;">
            <img
                v-if="data.user?.avatar"
                style="width: 40px; height: 40px; border-radius: 50%"
                :src="data.user?.avatar"
                alt="avatar"
            />
            <img
                v-else
                style="width: 40px; height: 40px; border-radius: 50%"
                src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
                alt="default avatar"
            />
            <span style="margin-left: 10px;">{{ data.user?.name }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="router.push('/manager/person')"
              >个人信息</el-dropdown-item
              >
              <el-dropdown-item @click="router.push('/manager/updatePassword')"
              >修改密码</el-dropdown-item
              >
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <!-- 头部区域结束 -->

    <!-- 下方区域开始 -->
    <div style="display: flex">
      <!-- 菜单区域开始 -->
      <div style="width: 240px;">
        <el-menu
            router
            :default-openeds="['1']"
            :default-active="router.currentRoute.value.path"
            style="min-height: calc(100vh - 60px); background-color: var(--menu-bg)"
        >
          <el-menu-item index="/manager/home">
            <el-icon><House /></el-icon>
            <span>首页</span>
          </el-menu-item>

          <el-sub-menu index="1" v-if="data.user.role === 'ADMIN'">
            <template #title>
              <el-icon><User /></el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/manager/admin">管理员信息</el-menu-item>
            <el-menu-item index="/manager/user">普通用户(学生)信息</el-menu-item>
            <el-menu-item index="/manager/teacher">教师管理</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="2">
            <template #title>
              <el-icon><location /></el-icon>
              <span>功能模块</span>
            </template>
            <el-menu-item
                index="/manager/course"
                v-if="data.user.role === 'TEACHER' || data.user.role === 'USER'">课程管理</el-menu-item>
            <el-menu-item index="/manager/score" v-if="data.user.role === 'TEACHER'">成绩管理</el-menu-item>
            <el-menu-item index="/manager/score" v-if="data.user.role === 'USER'">我的成绩</el-menu-item>

          </el-sub-menu>
        </el-menu>
      </div>
      <!-- 菜单区域结束 -->

      <!-- 数据渲染区域开始 -->
      <div style="flex: 1; width: 0; padding: 10px; background-color: var(--bg-color)">
        <RouterView @updateUser="updateUser" />
      </div>
      <!-- 数据渲染区域结束 -->
    </div>
    <!-- 下方区域结束 -->
  </div>
</template>

<script setup>
import router from "@/router/index.js";
import { reactive, ref, onMounted } from "vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem("account") || "{}"),
});

// 主题状态（Boolean，true=dark）
const themeMode = ref(localStorage.getItem("themeMode") === "dark");

// 切换主题函数
const toggleTheme = (val) => {
  const mode = val ? "dark" : "light";
  themeMode.value = val;
  localStorage.setItem("themeMode", mode);
  applyTheme(mode);
};

// 应用主题（设置 body class 及 CSS 变量）
const applyTheme = (mode) => {
  document.body.classList.remove("light", "dark");
  document.body.classList.add(mode);

  if (mode === "dark") {
    // 暗色主题颜色，保证菜单深蓝，背景深灰，文字浅色
    document.body.style.setProperty("--bg-color", "#121824");          // 整体背景：深蓝黑
    document.body.style.setProperty("--menu-bg", "#223059");          // 菜单背景：深蓝
    document.body.style.setProperty("--menu-hover", "#3a4a8a");       // 菜单悬浮：蓝紫
    document.body.style.setProperty("--menu-active", "#5a75d1");      // 菜单选中：亮蓝
    document.body.style.setProperty("--text-color", "#e6e8f0");       // 主文字色：亮灰白
    document.body.style.setProperty("--text-color-light", "#a3a7b7"); // 次文字色：中灰
    document.body.style.setProperty("--border-color", "#445175");     // 边框色：蓝灰
  } else {
    // 亮色主题颜色，保持整体风格统一但明亮
    document.body.style.setProperty("--bg-color", "#f9fbff");          // 整体背景：极浅蓝白
    document.body.style.setProperty("--menu-bg", "#3a456b");          // 菜单背景：深蓝
    document.body.style.setProperty("--menu-hover", "#7a9fff");       // 菜单悬浮：浅蓝
    document.body.style.setProperty("--menu-active", "#537bee");      // 菜单选中：标准蓝
    document.body.style.setProperty("--text-color", "#2e3440");       // 主文字色：深灰蓝
    document.body.style.setProperty("--text-color-light", "#7f8db0"); // 次文字色：中灰蓝
    document.body.style.setProperty("--border-color", "#d6ddee");     // 边框色：浅灰蓝
  }
};

// 初始化主题
onMounted(() => {
  const storedTheme = localStorage.getItem("themeMode") || "light";
  applyTheme(storedTheme);
  themeMode.value = storedTheme === "dark";
});

const logout = () => {
  localStorage.removeItem("account");
  location.href = "/login";
};

const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem("account") || "{}");
};
</script>

<style>
/* 全局过渡，切换平滑 */
body {
  transition: background-color 0.3s, color 0.3s;
}

/* 头部及菜单统一样式，使用 CSS 变量 */
.header-section {
  display: flex;
  align-items: center;
  padding-left: 20px;
  border-bottom: 1px solid var(--border-color);
  background-color: var(--menu-bg);
  color: var(--text-color-light);
}

.header-section p {
  font-size: larger;
  /* 继承 color */
}

/* Element Plus 菜单自定义 */
.el-menu {
  background-color: var(--menu-bg);
  border: none;
}

.el-sub-menu__title {
  background-color: var(--menu-bg);
  color: var(--text-color-light);
}

.el-menu-item {
  height: 50px;
  color: var(--text-color-light);
}

.el-menu .is-active {
  background-color: var(--menu-active);
  color: #fff;
}

.el-sub-menu__title:hover {
  background-color: var(--menu-bg);
}

.el-menu-item:not(.is-active):hover {
  background-color: var(--menu-hover);
  color: var(--text-color);
}

.el-dropdown {
  cursor: pointer;
}

.el-tooltip__trigger {
  outline: none;
}

.el-menu--inline .el-menu-item {
  padding-left: 48px !important;
}

/* 切换按钮动画 */
.el-switch {
  transition: transform 0.3s ease-in-out;
}

.el-switch:active {
  transform: scale(0.98);
}
</style>
