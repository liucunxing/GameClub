<script setup lang="ts">
import { reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { mainStore } from "../store/index";
import { storeToRefs } from "pinia";
const store = mainStore();
const { userInfo } = storeToRefs(store);
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
const validateUsername = (rule: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("请输入账户"));
  } else {
    callback();
  }
};

const ruleForm = reactive({
  name: "",
  password: "",
  email: "",
  phone: "",
  checkPass: "",
  age: "",
});

const rules = reactive<FormRules<typeof ruleForm>>({
  password: [{ validator: validatePassword, trigger: "blur" }],
  name: [{ validator: validateUsername, trigger: "blur" }],
  email: [{ validator: checkEmail, trigger: "blur" }],
});
const loginData = ref({
  name: "",
  telNumber: "",
  password: "string",
});
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid) {
      loginData.value.name = ruleForm.name;
      loginData.value.telNumber = ruleForm.phone;
      loginData.value.password = ruleForm.password;
      login(loginData);
      console.log("submit!");
    } else {
      console.log("error submit!");
      return false;
    }
  });
};

const login = (data: object) => {
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

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};
</script>
<template>
  <el-row :gutter="10" style="width: 100%">
    <el-col
      style="width: 100%; display: flex; justify-content: center"
      :offset="10"
      :xs="4"
      :sm="8"
      :md="12"
      :lg="24"
      :xl="24"
    >
      <div class="center-content">
        <el-card style="width: 480px">
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
            <el-form-item required label="账号：" prop="name">
              <el-input v-model.number="ruleForm.name" />
            </el-form-item>
            <el-form-item  label="手机：" prop="email">
              <el-input v-model.number="ruleForm.phone" />
            </el-form-item>
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
    </el-col>
  </el-row>
</template>
  
<style scoped>
.el-col {
  border-radius: 4px;
}

.center-content {
  /* 添加居中内容的样式 */
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%; /* 使内容垂直居中 */
  width: auto;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}
</style>
  