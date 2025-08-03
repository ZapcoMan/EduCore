<template>
  <div class="card" style="width:50%">
    <div style="font-size: 15px">个人中心</div>
    <el-form ref="formRef" :model="data.user" label-width="80px" style="padding: 20px 30px 10px 0">
      <el-form-item prop="username" label="账号">
        <el-input v-model="data.user.username" autocomplete="off" placeholder="请输入账号"/>
      </el-form-item>
      <el-form-item prop="role" label="身份">
        <el-input v-model="data.user.role" autocomplete="off" placeholder="请输入身份"/>
      </el-form-item>
      <el-form-item prop="name" label="真实姓名">
        <el-input v-model="data.user.name" autocomplete="off" placeholder="请输入真实姓名"/>
      </el-form-item>
      <el-form-item prop="avatar" label="头像">
                <el-upload
                    action="http://localhost:9991/files/upload"
                    :headers="{ token: data.user.token }"
                    :on-success="handleFileSuccess"
                    list-type="picture">
          <el-button type="primary">上传头像</el-button>
        </el-upload>

      </el-form-item>
    </el-form>
    <div style="text-align: center">
      <el-button type="primary" style="padding: 18px 35px" @click="update">保存</el-button>
    </div>
  </div>
</template>

<script setup>

import {reactive} from "vue";
import {ElMessage} from "element-plus";
import request from "@/utils/request.js";

const data = reactive({
  user: JSON.parse(localStorage.getItem('account') || "{}")
})

const handleFileSuccess = (res) => {
  data.user.avatar = res.data
}

const emit = defineEmits(['updateUser'])

const update = () => {
  let url
  if (data.user.role === 'ADMIN') {
    url = '/admin/update'
  }
  if (data.user.role === 'USER') {
    url = '/user/update'
  }

  request.put(url, data.user).then(res => {
    if (res.code === 20000) {
      ElMessage.success('更新成功')
      localStorage.setItem("account", JSON.stringify(data.user))
      emit('updateUser')
    }
  })
}

const upload = (params) => {
  const formData = new FormData();
  formData.append('file', params.file);

  request.post('/files/upload', formData).then(res => {
    if (res.code === 20000) {
      ElMessage.success('上传成功');
      params.onSuccess(res); // 手动触发 success 回调
    } else {
      params.onError(); // 触发错误回调
    }
  }).catch(() => {
    params.onError(); // 出错时也触发错误回调
  });
}


</script>

<style scoped>

</style>