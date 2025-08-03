<template>
  <div>
    <!-- 查询区域 -->
    <div class="card" style="margin-bottom:5px">
      <el-input v-model="query.name" clearable placeholder="课程名" @clear="filter" style="width:260px;margin-right:5px"/>
      <el-button type="primary" @click="filter">查询</el-button>
      <el-button @click="reset">重置</el-button>
    </div>

    <!-- 教师视角：操作按钮 -->
    <div class="card" style="margin-bottom:5px" v-if="!isStudent">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="danger" @click="deleteBatch">批量删除</el-button>
    </div>

    <!-- 表格 -->
    <div class="card" style="margin-bottom:5px">
      <el-table :data="pageData" @selection-change="onSelect" style="width:100%" :header-cell-style="{ background:'#eaf4ff', color:'#333' }">
        <el-table-column type="selection" width="55" v-if="!isStudent"/>
        <el-table-column prop="name" label="课程名"/>
        <el-table-column prop="teacherName" label="所属教师"/>
        <el-table-column prop="description" label="描述"/>
        <el-table-column label="操作" width="120" v-if="!isStudent">
          <template #default="{row}">
            <el-button type="primary" icon="Edit" circle @click="handleEdit(row)"/>
            <el-button type="danger" icon="Delete" circle @click="del(row.id)"/>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="card">
      <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :total="filtered.length"
          :page-sizes="[5,10,20]"
          layout="total, sizes, prev, pager, next, jumper"
      />
    </div>

    <!-- 弹窗表单 -->
    <el-dialog title="课程信息" v-model="formVisible" width="30%" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px" style="padding:20px">
        <el-form-item label="课程名" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="教师" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择">
            <el-option v-for="t in teachers" :key="t.id" :label="t.name" :value="t.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="formVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import request from '@/utils/request'
import { ElMessage, ElMessageBox } from 'element-plus'

// 当前用户信息，用于判断角色
const data = reactive({
  user: JSON.parse(localStorage.getItem('account') || '{}')
})
const isStudent = data.user.role === 'USER'

// 数据区
const all = ref([])
const filtered = ref([])
const teachers = ref([])

const pageNum = ref(1)
const pageSize = ref(5)
const query = reactive({ name: '' })
const pageData = computed(() => filtered.value.slice((pageNum.value - 1) * pageSize.value, pageNum.value * pageSize.value))

// 表单区
const formVisible = ref(false)
const formRef = ref()
const form = reactive({})
const rules = {
  name: [{ required: true, message: '请输入课程名', trigger: 'blur' }],
  teacherId: [{ required: true, message: '请选择教师', trigger: 'change' }]
}
const selected = ref([])

// 初始化加载
function load() {
  request.get('/course').then(res => {
    if (res.code === 20000) {
      all.value = res.data
      filtered.value = res.data
    }
  })

  // 仅教师加载教师列表（学生无权限添加课程）
  if (!isStudent) {
    request.get('/teacher').then(res => {
      if (res.code === 20000) teachers.value = res.data
    })
  }
}

// 查询过滤
function filter() {
  filtered.value = all.value.filter(i => i.name.includes(query.name))
  pageNum.value = 1
}

// 重置查询
function reset() {
  query.name = ''
  filter()
}

// 打开新增弹窗
function handleAdd() {
  Object.assign(form, {})
  formVisible.value = true
}

// 打开编辑弹窗
function handleEdit(row) {
  Object.assign(form, row)
  formVisible.value = true
}

// 保存新增/编辑
function save() {
  formRef.value.validate(valid => {
    if (!valid) return
    const method = form.id ? 'put' : 'post'
    request[method]('/course', form).then(res => {
      if (res.code === 20000) {
        ElMessage.success('保存成功')
        formVisible.value = false
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  })
}

// 删除单条
function del(id) {
  ElMessageBox.confirm('删除该课程？', '提示', { type: 'warning' }).then(() => {
    request.delete(`/course/${id}`).then(res => {
      if (res.code === 20000) {
        ElMessage.success('删除成功')
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  })
}

// 批量选择
function onSelect(rows) {
  selected.value = rows
}

// 批量删除
function deleteBatch() {
  if (!selected.value.length) return ElMessage.warning('未选择')
  ElMessageBox.confirm('确定批量删除？', '提示', { type: 'warning' }).then(() => {
    const ids = selected.value.map(i => i.id)
    request.post('/course/batchDelete', ids).then(res => {
      if (res.code === 20000) {
        ElMessage.success('删除成功')
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  })
}

// 页面挂载加载数据
onMounted(load)
</script>
