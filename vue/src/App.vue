<template>
  <div id="app">
    <!-- 主题切换开关 -->
      <el-switch
          v-model="themeMode"
          active-text="🌙"
          inactive-text="☀️"
          active-color="#409EFF"
          inactive-color="#F56C6C"
          @change="toggleTheme"
          style="position: fixed; top: 13px; right: 180px;"
      />
    <router-view />
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue';

const themeMode = ref(localStorage.getItem('themeMode') || 'light');

// 切换主题函数
const toggleTheme = (val) => {
  const mode = val ? 'dark' : 'light';
  themeMode.value = val;
  localStorage.setItem('themeMode', mode);
  applyTheme(mode);
};

// 应用主题（设置全局CSS变量）
const applyTheme = (mode) => {
  if (mode === 'dark') {
    document.body.style.setProperty('--menu-bg', '#2e3a59');
    document.body.style.setProperty('--bg-color', '#121824');
    document.body.style.setProperty('--text-color', '#e6e8f0');
    document.body.style.setProperty('--text-color-light', '#a3a7b7');
    document.body.style.setProperty('--border-color', '#445175');
  } else {
    document.body.style.setProperty('--menu-bg', '#3a456b');
    document.body.style.setProperty('--bg-color', '#f9fbff');
    document.body.style.setProperty('--text-color', '#1b0ca9');
    document.body.style.setProperty('--text-color-light', '#7f8db0');
    document.body.style.setProperty('--border-color', '#d6ddee');
  }
};

// 初始化时应用主题
onMounted(() => {
  const storedTheme = localStorage.getItem('themeMode') || 'light';
  themeMode.value = storedTheme === 'dark';
  applyTheme(storedTheme);
});
</script>

<style>
body {
  transition: background-color 0.3s, color 0.3s;
}

#app {
  min-height: 100vh;
  background-color: var(--bg-color);
  color: var(--text-color);
}
</style>
