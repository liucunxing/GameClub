<template>
  <div class="register-page">
    <div class="left-section">
      <img class="background-image" src="../assets/pic/pubg.jpg" alt="Background Image" />
    </div>
    <div class="right-section">
      <div class="register-login-box">
        <div class="toggle-button">
          <button :class="{ active: activeTab === 'login' }" @click="activeTab = 'login'">登录</button>
          <button :class="{ active: activeTab === 'register' }" @click="activeTab = 'register'">注册</button>
        </div>
        <form>
          <div class="input-row">
            <label for="telNumber">手机号：</label>
            <input type="tel" id="telNumber" class="input-field" v-model="telNumber" required />
          </div>

          <div class="input-row">&nbsp;&nbsp;
            <label for="password">密码：</label>&nbsp;
            <input type="password" id="password" class="input-field" v-model="password" required />
          </div>
        </form>
        <div v-if="showError" class="error-message">用户名和密码都不能为空</div>
        <div class="button-container">
          <button type="submit" @click="onSubmit">{{ activeTab === 'login' ? '登录' : '注册' }}</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import axios from "../axios/axios";
import { useRoute, useRouter } from 'vue-router';
axios.interceptors.request.use(config => {
  config.headers.Authorization = localStorage.getItem("token"); // 添加请求头
  return config;
});

    const route = useRoute();
    const router = useRouter();
    const activeTab = ref('login');
    const telNumber = ref('');
    const password = ref('');
    const showError = ref(false);
    const isButtonVisible = ref(false);
    const submitData = ref({
      age: 0,
      name: "string",
      password: "string",
      sex: true,
      telNumber: "string",
      userAvater: "string"
    });

    // const validateForm = () => {
    //   if (phone.value === '' || password.value === '') {
    //     showError.value = true;
    //   } else {
    //     showError.value = false;
    //     // 执行登录或注册逻辑
    //   }
    // }
  const onSubmit = async() => {
    submitData.value.password = password;
    submitData.value.telNumber = telNumber;
  if (activeTab.value === 'login') {
    // 发送登录请求的逻辑
    const res = await axios.post("api/user/login",submitData.value);
    console.log('发送登录请求');
    if(res.data.data.token){
      const getU = await axios.get("api/user/noAuth/getUser?telNumber="+telNumber.value);
      localStorage.setItem("userId",getU.data.data.id);
      localStorage.setItem("userAvater",getU.data.data.avaterUrl);
      localStorage.setItem('token', res.data.data.token);
      localStorage.setItem('userName',res.data.data.name);
      //设置定时器，登录后3600s后token过期，local清除token，timer是定时器唯一id，存入local，当后面点击logout时要将定时器清除，不然会有多定时器存在。当一次定时执行后，token不存在，定时器即将触发时会清除自身
      const timer = setInterval(() => {
        if(localStorage.getItem('token') != null){
          localStorage.removeItem("token");
          localStorage.removeItem("userAvater",getU.data.data.avaterUrl);
        }
        clearInterval(timer);
      }, 3600000);
      localStorage.setItem('timer',timer);
      ElMessage.success({
        message: '登录成功',
        offset: 300 // 设置高度偏移值，单位为像素
      });
      router.push('/homePage')
    }
  } else {
    // 发送注册请求的逻辑
    const res = await axios.post("api/user/register", submitData.value);
    console.log('发送注册请求');
    alert(res.data.msg);
  }
};

</script>

<style scoped>
.register-page {
  display: flex;
  height: 100vh;
}

.left-section {
  flex: 1;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.background-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.right-section {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.register-login-box {
  background-color:#ffffff;
  width: 350px;
  border: 1px solid gray;
  aspect-ratio: 1 / 1;
  display: flex;
  flex-direction: column;
}

.toggle-button {
  display: flex;
  margin-bottom: 10px;
}

.toggle-button button {
  flex: 1;
  padding: 8px;
  border: none;
  background-color: transparent;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.toggle-button button.active {
  background-color: #187bcd;
  color: white;
}

.toggle-button button:first-child:not(.active) {
  color: #999;
}

.toggle-button button:last-child:not(.active) {
  color: #999;
}

form {
  display: flex;
  flex-direction: column;
  margin-top:80px;
  margin-left:30px;
}

.input-row {
  color:black;
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

label {
  font-size: 16px;
  margin-right: 8px;
}

.input-field {
  padding: 6px;
  border: 1px solid gray;
}

.button-container {
  margin-top: auto;
  align-self: flex-end;
}

button {
  padding: 10px 20px;
  background: #187bcd;
  color: white;
  border: none;
  cursor: pointer;
}

button.active {
  background-color: #187bcd;
}
</style>
