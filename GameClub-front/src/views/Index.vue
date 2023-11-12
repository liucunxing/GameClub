<script setup lang="ts">
import { reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { storeToRefs } from 'pinia';
import { useCounterStoreForSetup } from '../store/index';
// composition API模式
const counterStoreForSetup = useCounterStoreForSetup();
// 确保解构确保后的state具有响应式，要使用storeToRefs方法
const { token,getToken } = storeToRefs(counterStoreForSetup);
const { setToken } = counterStoreForSetup;

import axios from "../axios/axios";
const ruleFormRef = ref<FormInstance>();

const checkEmail = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("请输入邮箱地址"));
  }

  // 使用正则表达式匹配常见的邮箱格式
  const emailRegex = /^(?:[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4},?)+$/;

  if (!emailRegex.test(value)) {
    callback(new Error("请输入有效的邮箱地址"));
  } else {
    callback();
  }
};

const validatePassword = (rule: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("请输入密码"));
  } else {
    callback();
  }
};
const validateTelNumber = (rule: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("请输入账户"));
  } else {
    callback();
  }
};

const ruleForm = reactive({
  username: "",
  password: "",
  email: "",
  telNumber: "",
  checkPass: "",
  age: "",
});

const rules = reactive<FormRules<typeof ruleForm>>({
  password: [{ validator: validatePassword, trigger: "blur" }],
  telNumber: [{ validator: validateTelNumber, trigger: "blur" }],
  email: [{ validator: checkEmail, trigger: "blur" }],
});
const loginData = ref({
  telNumber: "1",
  password: "root",
});
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid) {
      loginData.value.telNumber = ruleForm.telNumber;
      loginData.value.password = ruleForm.password;
      login(loginData);
      console.log("submit!");
    } else {
      console.log("error submit!");
      return false;
    }
  });
};

const login = async  (data: object) => {
  try {
    const res = await axios.post("api/user/login", loginData.value);
    console.log(res);
    const token = res.data.data.token;
    setToken(token);
    console.log(token);
    const token3 = token;
    console.log(token3);
  } catch (error) {
    console.error(error);
  }
};

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};
</script>
<template>
  <div class="parent-container">
<div class="center-content">
        <el-card style="width: 480px;">
          <template #header>
            <div class="card-header">
              <span>欢迎来到GameClub游戏俱乐部</span>
              <span>请登录！</span>
            </div>
          </template>
          <el-form
            ref="ruleFormRef"
            :model="ruleForm"
            label-position="left"
            status-icon
            :rules="rules"
            label-width="80px"
            class="demo-ruleForm"
            require-asterisk-position="right"
          >
            <el-form-item required label="账号：" prop="telNumber">
              <el-input v-model.number="ruleForm.telNumber" />
            </el-form-item>
            <!--<el-form-item  label="手机：" prop="email">
              <el-input v-model.number="ruleForm.phone" />
            </el-form-item>-->
            <el-form-item required label="密码：" prop="password">
              <el-input
                v-model="ruleForm.password"
                type="password"
                autocomplete="off"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm(ruleFormRef)"
                >Submit</el-button
              >
              <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
  </div>
      
</template>
  
<style scoped>

.parent-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
}

.center-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}


.text {
  font-size: 14px;
}

</style>
  