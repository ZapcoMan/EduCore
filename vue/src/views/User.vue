<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input clearable @clear="load" style="width: 260px; margin-right: 5px" v-model="data.username" placeholder="请输入账号查询" :prefix-icon="Search"></el-input>
      <el-input clearable @clear="load" style="width: 260px; margin-right: 5px" v-model="data.name" placeholder="请输入名称查询" :prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查 询</el-button>
      <el-button @click="reset">重 置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" @click="handleAdd">新 增</el-button>
      <el-button type="danger" @click="deleteBatch">批量删除</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" style="width: 100%" @selection-change="handleSelectionChange"
                :header-cell-style="{ color: '#333', backgroundColor: '#eaf4ff' }">
        <el-table-column type="selection" width="55" />
        <el-table-column label="头像">
          <template #default="scope">
            <el-image v-if="scope.row.avatar" :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]" :preview-teleported="true"
                      style="width: 40px; height: 40px; border-radius: 50%; display: block" />
          </template>
        </el-table-column>
        <el-table-column prop="username" label="账号" />
        <el-table-column prop="role" label="身份" />
        <el-table-column prop="name" label="真实姓名" />
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button type="primary" icon="Edit" circle @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" icon="Delete" circle @click="del(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination
          v-model:current-page="data.pageNum"
          v-model:page-size="data.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="[5, 10, 20]"
          :total="data.total"
          @current-change="load"
          @size-change="load"
      />
    </div>

    <el-dialog title="用户信息" v-model="data.formVisible" width="30%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="80px" style="padding: 20px 30px 10px 0">
        <el-form-item prop="username" label="账号">
          <el-input v-model="data.form.username" autocomplete="off" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item prop="role" label="身份">
          <el-input v-model="data.form.role" autocomplete="off" placeholder="请输入身份"/>
        </el-form-item>
        <el-form-item prop="name" label="真实姓名">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入真实姓名"/>
        </el-form-item>
        <el-form-item prop="avatar" label="头像">
          <el-upload
              action="http://localhost:9999/files/upload"
              :headers="{ token: data.user.token }"
              :on-success="handleFileSuccess"
              list-type="picture"
          >
            <el-button type="primary">上传头像</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">保 存</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  fetchUsers,
  addUser,
  updateUser,
  deleteUser,
  deleteUsers
} from '@/api/user'

const data = reactive({
  user: JSON.parse(localStorage.getItem('account') || '{}'),
  username: null,
  name: null,
  pageNum: 1,
  pageSize: 5,
  total: 0,
  tableData: [],
  formVisible: false,
  form: {},
  rules: {
    username: [{ required: true, message: '请填写账号', trigger: 'blur' }],
    role: [{ required: true, message: '请填写身份', trigger: 'blur' }],
    name: [{ required: true, message: '请填写真实姓名', trigger: 'blur' }]
  },
  rows: []
})

const formRef = ref()

const load = () => {
  fetchUsers({
    pageNum: data.pageNum,
    pageSize: data.pageSize,
    username: data.username,
    name: data.name
  }).then(res => {
    if (res.code === 20000) {
      data.tableData = res.dataMap.pageInfo.list
      data.total = res.dataMap.total
    } else {
      ElMessage.error(res.message)
    }
  })
}
load()

const reset = () => {
  data.username = null
  data.name = null
  load()
}

const handleAdd = () => {
  data.formVisible = true
  data.form = {}
}

const add = () => {
  formRef.value.validate(valid => {
    if (valid) {
      addUser(data.form).then(res => {
        if (res.code === 20000) {
          data.formVisible = false
          ElMessage.success('新增成功')
          load()
        } else {
          ElMessage.error(res.message)
        }
      })
    }
  })
}

const update = () => {
  formRef.value.validate(valid => {
    if (valid) {
      updateUser(data.form).then(res => {
        if (res.code === 20000) {
          data.formVisible = false
          ElMessage.success('修改成功')
          load()
        } else {
          ElMessage.error(res.message)
        }
      })
    }
  })
}

const save = () => {
  data.form.id ? update() : add()
}

const del = id => {
  ElMessageBox.confirm('删除后无法恢复，您确认删除吗？', '删除确认', { type: 'warning' })
      .then(() => {
        deleteUser(id).then(res => {
          if (res.code === 20000) {
            ElMessage.success('删除成功')
            load()
          } else {
            ElMessage.error(res.message)
          }
        })
      })
      .catch(() => {})
}

const handleSelectionChange = rows => {
  data.rows = rows
}

const deleteBatch = () => {
  if (data.rows.length === 0) {
    ElMessage.warning('请选择数据')
    return
  }
  ElMessageBox.confirm('删除后无法恢复，您确认删除吗？', '删除确认', { type: 'warning' })
      .then(() => {
        deleteUsers(data.rows).then(res => {
          if (res.code === 20000) {
            ElMessage.success('批量删除成功')
            load()
          } else {
            ElMessage.error(res.message)
          }
        })
      })
      .catch(() => {})
}

const handleEdit = row => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

const handleFileSuccess = res => {
  data.form.avatar = res.data
}
</script>
