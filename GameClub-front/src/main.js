
import axios from 'axios';

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
// 创建axios实例并配置
const instance = axios.create({
    baseURL: 'http://localhost:8864/', // 设置API的基本URL
    timeout: 5000, // 请求超时时间
  });
// const token = localStorage.getItem('token');
// axios.interceptors.request.use(config => {
//   config.headers.Authorization = 'token'; // 添加请求头
//   return config;
// });

const app = createApp(App)
app.config.globalProperties.$axios = instance;
app.use(ElementPlus)
app.use(createPinia())
app.use(router)

app.mount('#app')
