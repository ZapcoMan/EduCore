import axios from "axios";
import { ElMessage } from "element-plus";
import router from "../router"; // 确保你有 router 引入

let baseURL = 'http://192.168.1.9:9991';

const request = axios.create({
    baseURL: baseURL,
    timeout: 30000
});

// request 拦截器
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let user = JSON.parse(localStorage.getItem('account') || '{}');
    config.headers['token'] = user.token;
    return config;
}, error => {
    return Promise.reject(error);
});

// response 拦截器
request.interceptors.response.use(
    response => {
        // === 🔁 自动续签处理 ===
        const newToken = response.headers['renew-token'];
        if (newToken) {
            const user = JSON.parse(localStorage.getItem('account') || '{}');
            user.token = newToken;
            localStorage.setItem('account', JSON.stringify(user));
        }

        let res = response.data;
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res;
        }

        if (res.code === '401') {
            ElMessage.error(res.msg);
            router.push('/login');
        } else {
            return res;
        }
    },
    error => {
        if (error.response?.status === 404) {
            ElMessage.error('未找到请求接口');
        } else if (error.response?.status === 500) {
            ElMessage.error('系统异常，请查看后端控制台报错');
        } else {
            console.error(error.message);
        }
        return Promise.reject(error);
    }
);

export default request;
