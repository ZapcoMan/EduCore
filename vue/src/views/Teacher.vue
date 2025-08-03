<template>
  <div>
    <!-- 查询 -->
    <div class="card" style="margin-bottom:5px">
      <el-input v-model="query.name" clearable placeholder="姓名" @clear="filter" style="width:260px;margin-right:5px"/>
      <el-button type="primary" @click="filter">查询</el-button>
      <el-button @click="reset">重置</el-button>
    </div>

    <!-- 操作 -->
    <div class="card" style="margin-bottom:5px">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="danger" @click="deleteBatch">批量删除</el-button>
    </div>

    <!-- 表格 -->
    <div class="card" style="margin-bottom:5px">
      <el-table :data="pageData" @selection-change="onSelect" style="width:100%" :header-cell-style="{ background:'#eaf4ff', color:'#333' }">
        <el-table-column type="selection" width="55"/>
        <el-table-column prop="name" label="姓名"/>
        <el-table-column prop="teacherNo" label="工号"/>
        <el-table-column prop="gender" label="性别"/>
        <el-table-column prop="title" label="职称"/>
        <el-table-column label="操作" width="120">
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
          v-model:current-page="pageNum" v-model:page-size="pageSize"
          :total="filtered.length" :page-sizes="[5,10,20]"
          layout="total, sizes, prev, pager, next, jumper"/>
    </div>

    <!-- 弹窗 -->
    <el-dialog title="教师信息" v-model="formVisible" width="30%" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px" style="padding:20px">
        <el-form-item label="姓名" prop="name"><el-input v-model="form.name"/></el-form-item>
        <el-form-item label="工号" prop="teacherNo"><el-input v-model="form.teacherNo"/></el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender"><el-option label="男" value="MALE"/><el-option label="女" value="FEMALE"/></el-select>
        </el-form-item>
        <el-form-item label="职称" prop="title"><el-input v-model="form.title"/></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="formVisible=false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import request from '@/utils/request'
import { ElMessage, ElMessageBox } from 'element-plus'

const all = ref([])
const filtered = ref([])
const pageNum = ref(1)
const pageSize = ref(5)
const query = reactive({ name: '' })

const pageData = computed(() => {
  const s = (pageNum.value - 1) * pageSize.value
  return filtered.value.slice(s, s + pageSize.value)
})

const formVisible = ref(false)
const formRef = ref()
const form = reactive({})
const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  teacherNo: [{ required: true, message: '请输入工号', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  title: [{ required: true, message: '请输入职称', trigger: 'blur' }],
}

const selected = ref([])

function load(){
  request.get('/teacher').then(res => {
    if(res.code===20000){
      all.value=res.data
      filtered.value=res.data
    } else ElMessage.error(res.message)
  })
}

function filter(){
  filtered.value = all.value.filter(i => i.name.includes(query.name))
  pageNum.value=1
}

function reset(){
  query.name = ''
  filter()
}

function handleAdd(){
  Object.assign(form,{})
  formVisible.value=true
}

function handleEdit(row){
  Object.assign(form,row)
  formVisible.value=true
}

function save(){
  formRef.value.validate(valid=>{
    if(!valid) return
    const method = form.id ? 'put' : 'post'
    request[method]('/teacher', form).then(res => {
      res.code===20000 ? (ElMessage.success('保存成功'), load(), formVisible.value=false) : ElMessage.error(res.message)
    })
  })
}

function del(id){
  ElMessageBox.confirm('删除该教师？','提示',{type:'warning'}).then(()=>{
    request.delete(`/teacher/${id}`).then(res => {
      res.code===20000 ? (ElMessage.success('删除成功'), load()) : ElMessage.error(res.message)
    })
  })
}

function onSelect(rows){ selected.value = rows }

function deleteBatch(){
  if(!selected.value.length){ return ElMessage.warning('未选择') }
  ElMessageBox.confirm('确定批量删除？','提示',{type:'warning'}).then(()=>{
    const ids = selected.value.map(i=>i.id)
    request.post('/teacher/batchDelete', ids).then(res=>{
      res.code===20000 ? (ElMessage.success('批量删除成功'), load()) : ElMessage.error(res.message)
    })
  })
}

onMounted(load)
</script>
