<template>
  <div class="login-container">
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
  </div>
</template>
  
<script>
import { defineComponent, ref } from "vue";
//使用pinia，全局状态保存
import { useStore } from '../store/index';
import { mainStore } from "../store/index";
import { storeToRefs } from "pinia";
const store = mainStore();
const { userInfo } = storeToRefs(store);

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
      /*loginForm.value.validate(async (valid) => {
        if (valid) {
          try {
            const response = await axios.post("api/user/login", loginData.value);
            const { token } = response.data;

            console.log(response);
            // 在Pinia store中保存token或其他用户信息
            store.changeToken(token)

            // 导航到其他页面
            // router.push('/dashboard');
          } catch (error) {
            console.error(error);
            // 处理登录失败的情况
          }
        } else {
          return false;
        }
      });*/
    };

    return {
      loginForm,
      loginData,
      login,
    };
  },
});
</script>
  
  <style>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
</style>
  