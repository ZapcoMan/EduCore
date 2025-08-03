import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite' // 自动导入vue中的组件
import Components from 'unplugin-vue-components/vite' //自动导入ui-组件 比如 element-plus等
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers' // 对应组件库引入 ，AntDesignVueResolver

// https://vite.dev/config/
export default defineConfig({
  server:{
    host: '0.0.0.0',
    port: 5173,
  },
  define: {
    __BASE_API__: JSON.stringify(process.env.VITE_BASE_API)
  },
  plugins: [
    vue(),
    //element-plus按需导入
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [
        // 配置elementPlus采用sass样式配置系统
        ElementPlusResolver({importStyle:"sass"})
      ],
    }),
  ],
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@use "@/assets/css/index.scss" as *;`,
      },
    },
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})
