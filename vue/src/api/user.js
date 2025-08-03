import request from "@/utils/request.js";


// 分页查询用户
export const fetchUsers = (params) => {
    return request.get('/user/selectPage', { params })
}

// 新增用户
export const addUser = (data) => {
    return request.post('/user/add', data)
}

// 修改用户
export const updateUser = (data) => {
    return request.put('/user/update', data)
}

// 删除单个用户
export const deleteUser = (id) => {
    return request.delete(`/user/delete/${id}`)
}

// 批量删除用户
export const deleteUsers = (rows) => {
    return request.delete('/user/deleteBatch', { data: rows })
}

export const register = (data) =>{
    // 定义一个 data
    data = {
        username: data.username,
        password: data.password,
        newPassword: data.newPassword,
        role: "USER",

    }
    return request.post('/register', data)
}