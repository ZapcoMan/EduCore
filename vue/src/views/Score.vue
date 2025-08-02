<template>
  <div>
    <!-- 查询条件 -->
    <div class="card" style="margin-bottom:5px">
      <el-input v-model="query.studentId" clearable placeholder="学号或学生ID" @clear="filter"
                style="width:260px;margin-right:5px" v-if="!isStudent"/>
      <el-input v-model="query.courseId" clearable placeholder="课程ID" @clear="filter"
                style="width:260px;margin-right:5px"/>
      <el-button type="primary" @click="filter">查询</el-button>
      <el-button @click="reset">重置</el-button>
    </div>

    <!-- 操作按钮 -->
    <div class="card" style="margin-bottom:5px" v-if="!isStudent">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="danger" @click="deleteBatch">批量删除</el-button>
    </div>

    <!-- 成绩表格 -->
    <div class="card" style="margin-bottom:5px">
      <el-table :data="pageData" @selection-change="onSelect" style="width:100%"
                :header-cell-style="{ background:'#eaf4ff', color:'#333' }">
        <el-table-column type="selection" width="55" v-if="!isStudent"/>
        <el-table-column prop="studentId" label="学生ID"/>
        <el-table-column prop="courseId" label="课程ID"/>
        <el-table-column prop="score" label="成绩"/>
        <el-table-column label="操作" width="120" v-if="!isStudent">
          <template #default="{row}">
            <el-button type="primary" icon="Edit" circle @click="handleEdit(row)"/>
            <el-button type="danger" icon="Delete" circle @click="del(row.id)"/>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页器 -->
    <div class="card">
      <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize"
                     :total="filtered.length" :page-sizes="[5,10,20]"
                     layout="total, sizes, prev, pager, next, jumper"/>
    </div>

    <!-- 表单弹窗 -->
    <el-dialog title="成绩信息" v-model="formVisible" width="30%" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px" style="padding:20px">
        <el-form-item label="学生ID" prop="studentId">
          <el-select v-model="form.studentId" placeholder="请选择" v-if="!isStudent">
            <el-option v-for="s in students" :key="s.id" :label="s.studentNo" :value="s.id"/>
          </el-select>
          <el-input v-else :value="form.studentId" disabled/>
        </el-form-item>

        <el-form-item label="课程ID" prop="courseId">
          <el-select v-model="form.courseId" placeholder="请选择">
            <el-option v-for="c in courses" :key="c.id" :label="c.name" :value="c.id"/>
          </el-select>
        </el-form-item>

        <el-form-item label="成绩" prop="score">
          <el-input-number v-model="form.score" :min="0" :max="100" :step="0.01"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="formVisible=false">取消</el-button>
        <el-button type="primary" @click="save" v-if="!isStudent">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, reactive, computed, onMounted} from 'vue'
import request from '@/utils/request'
import {ElMessage, ElMessageBox} from 'element-plus'

// 当前登录用户信息
const data = reactive({
  user: JSON.parse(localStorage.getItem('account') || "{}")
})
const isStudent = data.user.role === 'USER'

// 成绩数据、学生/课程信息
const all = ref([])
const filtered = ref([])
const students = ref([])
const courses = ref([])

// 分页信息
const pageNum = ref(1)
const pageSize = ref(5)

// 查询条件
const query = reactive({
  studentId: '',
  courseId: ''
})

// 当前页数据
const pageData = computed(() => {
  return filtered.value.slice((pageNum.value - 1) * pageSize.value + 0, (pageNum.value - 1) * pageSize.value + pageSize.value)
})

// 表单弹窗、选中行、表单字段
const formVisible = ref(false)
const formRef = ref()
const form = reactive({})
const selected = ref([])

// 表单校验规则
const rules = {
  studentId: [{required: true, message: '请选择学生', trigger: 'change'}],
  courseId: [{required: true, message: '请选择课程', trigger: 'change'}],
  score: [{required: true, message: '请输入成绩', trigger: 'blur'}]
}

// 初始化数据
function load() {
  request.get('/score').then(res => {
    if (res.code === 20000) {
      if (isStudent) {
        // 学生只能看到自己成绩
        filtered.value = res.data.filter(i => i.studentId === data.user.id)
      } else {
        all.value = res.data
        filtered.value = res.data
      }
    }
  })
  if (!isStudent) {
    request.get('/student').then(res => {
      if (res.code === 20000) students.value = res.data
    })
  }
  request.get('/course').then(res => {
    if (res.code === 20000) courses.value = res.data
  })
}

// 筛选功能
function filter() {
  if (isStudent) {
    filtered.value = filtered.value.filter(i =>
        (!query.courseId || i.courseId == query.courseId)
    )
  } else {
    filtered.value = all.value.filter(i =>
        (!query.studentId || i.studentId == query.studentId) &&
        (!query.courseId || i.courseId == query.courseId)
    )
  }
  pageNum.value = 1
}

// 重置查询
function reset() {
  query.studentId = ''
  query.courseId = ''
  filter()
}

// 新增
function handleAdd() {
  Object.assign(form, {})
  formVisible.value = true
}

// 编辑
function handleEdit(row) {
  Object.assign(form, row)
  formVisible.value = true
}

// 保存
function save() {
  formRef.value.validate(valid => {
    if (!valid) return
    const method = form.id ? 'put' : 'post'
    request[method]('/score', form).then(res => {
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

// 单个删除
function del(id) {
  ElMessageBox.confirm('删除该成绩？', '提示', {type: 'warning'}).then(() => {
    request.delete(`/score/${id}`).then(res => {
      if (res.code === 20000) {
        ElMessage.success('删除成功')
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  })
}

// 批量操作
function onSelect(rows) {
  selected.value = rows
}
function deleteBatch() {
  if (!selected.value.length) return ElMessage.warning('未选择')
  ElMessageBox.confirm('确定批量删除？', '提示', {type: 'warning'}).then(() => {
    const ids = selected.value.map(i => i.id)
    request.post('/score/batchDelete', ids).then(res => {
      if (res.code === 20000) {
        ElMessage.success('删除成功')
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  })
}

// 初始加载
onMounted(load)
</script>
