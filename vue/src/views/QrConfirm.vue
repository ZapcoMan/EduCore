<template>
  <div class="confirm-container">
    <!-- 登录成功遮罩提示 -->
    <div class="mask" v-if="showMask">
      <div class="mask-content">
        <el-icon color="#67c23a" size="50"><CircleCheckFilled /></el-icon>
        <p>登录成功，可关闭此页面</p>
<!--        <p class="countdown">页面将在 {{ countdown }} 秒后自动关闭</p>-->
      </div>
    </div>

    <!-- 页面动画 fade-in -->
    <transition name="fade-in">
      <div class="card">
        <h2 class="title">扫码登录确认</h2>

        <!-- 表单动画 zoom-in -->
        <transition name="zoom-in">
          <el-form :model="form" :rules="rules" ref="formRef" label-width="60px" size="large">
            <el-form-item label="账号" prop="username">
              <el-input v-model="form.username" placeholder="请输入账号" clearable>
                <template #prefix>
                  <el-icon><User /></el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password clearable>
                <template #prefix>
                  <el-icon><Lock /></el-icon>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item label="角色" prop="role">
              <el-select v-model="form.role" placeholder="请选择角色" style="width: 100%">
                <el-option label="管理员" value="ADMIN" />
                <el-option label="学生" value="USER" />
                <el-option label="老师" value="TEACHER" />
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" class="confirm-btn" @click="onSubmit" round block>
                确 认 登 录
              </el-button>
            </el-form-item>
          </el-form>
        </transition>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue"
import { useRoute } from "vue-router"
import request from "@/utils/request"
import { ElMessage } from "element-plus"
import { User, Lock, CircleCheckFilled } from "@element-plus/icons-vue"

const route = useRoute()
const uuid = route.query.uuid

const formRef = ref()
const form = reactive({
  username: '',
  password: '',
  role: 'ADMIN'
})

const rules = {
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, message: '账号最少3位字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
}

const showMask = ref(false)
const countdown = ref(3)

/**
 * 强制关闭页面或跳转
 */
const forceCloseOrRedirect = () => {
  try {
    // 尝试关闭页面（前提：该页面是用户手动打开的标签页或 window.open 创建）
    window.open('', '_self')
    window.close()
  } catch (e) {
    // 若关闭失败，执行跳转到固定成功页（你可以改为自己的提示页）
    window.location.href = '/login' // 确保该页面存在
  }
}

const onSubmit = () => {
  formRef.value.validate(async valid => {
    if (valid) {
      const res = await request.post('/qrcode/confirm', { ...form, uuid })
      if (res.code === 20000) {
        showMask.value = true
        ElMessage.success("确认成功")

        const timer = setInterval(() => {
          countdown.value--
          if (countdown.value === 0) {
            clearInterval(timer)
            forceCloseOrRedirect()
          }
        }, 1000)
      } else {
        ElMessage.error(res.message)
      }
    }
  })
}
</script>


<style scoped>
.confirm-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f2f6ff, #dce3ff);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 24px 12px;
  position: relative;
}

.card {
  width: 100%;
  max-width: 380px;
  background-color: #ffffffee;
  border-radius: 12px;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.12);
  padding: 28px 22px;
  box-sizing: border-box;
  position: relative;
  z-index: 2;
}

.title {
  text-align: center;
  margin-bottom: 26px;
  font-size: 22px;
  font-weight: 600;
  color: #2c3e50;
  letter-spacing: 1px;
}

.confirm-btn {
  width: 100%;
  background: linear-gradient(to right, #5a91ff, #376eff);
  font-weight: bold;
}

.mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.85);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 99;
}

.mask-content {
  text-align: center;
  font-size: 16px;
  color: #303133;
}

.mask-content p {
  margin: 8px 0;
}

.countdown {
  color: #666;
  font-size: 14px;
}

/* 动画定义 */
.fade-in-enter-active {
  animation: fadeIn 0.6s ease;
}
.zoom-in-enter-active {
  animation: zoomIn 0.4s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes zoomIn {
  from {
    opacity: 0;
    transform: scale(0.9);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}
</style>
