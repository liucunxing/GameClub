<script>
import { defineComponent, ref } from "vue";
//使用pinia，全局状态保存
import { mainStore } from "../store/index";
import { storeToRefs } from "pinia";
const store = mainStore();
const { userInfo } = storeToRefs(store);
import axios from "../axios/axios";
export default defineComponent({
  name: "LoginPage",
  setup() {
    const loginForm = ref(null);
    const loginData = ref({
      telNumber: "string",
      password: "string",
    });
    const login = () => {
      axios
        .post("api/user/login", loginData.value)
        .then((res) => {
          console.log(res);
          const token = res.data.data.token;
          //console.log(token);
          store.changeToken(token);
          const token2 = store.getToken();
          console.log(token2);
          console.log(store);
        })
        .catch((error) => {
          console.error(error);
        });
    };
    return {
      loginForm,
      loginData,
      login,
    };
  },
});
</script>
<template>
  <div class="login-container">
    <el-card>
      <el-form ref="loginForm" :model="loginData" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginData.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="loginData.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
  

  
<style>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: lightgray;
}
.el-card {
  width: 400px;
  margin: auto;
}
.el-form {
  margin-top: 40px;
}
.el-button {
  margin-top: 20px;
}
</style>
  