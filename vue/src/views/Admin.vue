<template>
  <div>
    <el-table
        :data="data.tableData"
        style="width: 100%"
        :header-cell-style="{ color: 'var(--text-color-light)', backgroundColor: 'var(--menu-bg)' }"
    >
      <el-table-column label="头像">
        <template #default="scope">
          <el-image
              v-if="scope.row.avatar"
              :src="scope.row.avatar"
              :preview-src-list="[scope.row.avatar]"
              :preview-teleported="true"
              style="width: 40px; height: 40px; border-radius: 50%; display: block"
          />
        </template>
      </el-table-column>
      <el-table-column prop="username" label="账号" />
      <el-table-column prop="role" label="身份" />
      <el-table-column prop="name" label="真实姓名" />
      <el-table-column label="操作" width="100">
        <template #default="scope">
          <el-button type="primary" icon="Edit" circle @click="handleEdit(scope.row)" />
          <el-button type="danger" icon="Delete" circle @click="del(scope.row.id)" />
        </template>
      </el-table-column>
    </el-table>

    <!-- ✅ 分页条组件 -->
    <el-pagination
        style="margin-top: 20px; text-align: right"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :current-page="data.pageNum"
        :page-size="data.pageSize"
        :total="data.total"
        :page-sizes="[5, 10, 20, 50]"
        @current-change="handlePageChange"
        @size-change="handleSizeChange"
    />
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import request from '@/utils/request.js';
import { ElMessage } from 'element-plus';

const data = reactive({
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
    name: [{ required: true, message: '请填写真实姓名', trigger: 'blur' }],
  },
});

const load = () => {
  request.get('/admin/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      username: data.username,
      name: data.name,
    },
  }).then(res => {
    if (res.code === 20000) {
      // 🔧 修正字段名
      data.tableData = res.data.list;
      data.total = res.data.total;
    } else {
      ElMessage.error(res.message);
    }
  });
};


// 翻页处理
const handlePageChange = (page) => {
  data.pageNum = page;
  load();
};

// 每页条数变更
const handleSizeChange = (size) => {
  data.pageSize = size;
  data.pageNum = 1;
  load();
};

// 初始加载
load();
</script>

<style scoped>
.el-table {
  background-color: var(--bg-color);
}

.el-button--primary {
  background-color: var(--menu-bg);
  border-color: var(--menu-bg);
}

.el-button--danger {
  background-color: #f56c6c;
  border-color: #f56c6c;
}

.card {
  background-color: var(--bg-color);
  color: var(--text-color);
}

.el-table th,
.el-table td {
  color: var(--text-color-light);
}
</style>
