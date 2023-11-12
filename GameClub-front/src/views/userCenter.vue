<script setup lang="ts">
import { ref } from 'vue';
import axios from '../axios/axios';
import type { FormInstance, FormRules } from 'element-plus';
import Header from '@/views/common/header.vue'
axios.interceptors.request.use(config => {
  config.headers.Authorization = localStorage.getItem("token"); // 添加请求头
  return config;
});

const username = ref('');
const age = ref();
const telNumber = ref('');
const sex = ref('');
const avaterFile = ref<File | null>(null);
const fileInput = ref<HTMLInputElement | null>(null);
const circleUrl = ref('');

const handleFileChange = (event: Event) => {
const input = event.target as HTMLInputElement;
  if (input.files && input.files.length) {
    avaterFile.value = input.files[0];
    submitAvater(); // 选择完头像后自动触发上传操作
    input.value = ''; // 清空选择文件的输入框，便于下次选择文件
  }
};
const handleAvatarClick = () => {
  // 触发选择文件按钮的点击操作
  if (fileInput.value) {
    fileInput.value.click();
  }
};
const submitAvater = async () => {
  if (!avaterFile.value) {
    return;
  }
  const formData = new FormData(); // 在每次点击上传按钮时创建新的 formData 对象存储multipartfile文件
  formData.append('file', avaterFile.value); //@RequestPart("file") MultipartFile multipartFile
  try {
    const res = await axios.post("api/user/noAuth/testUpload", formData);
    circleUrl.value = res.data.data;
    debugger;
  } catch (error) {
    console.error(error);
    // 错误处理逻辑
  }
};

</script>
<template>
    <div class="userCenter">
        <Header />
        <div class="ucBody">
            <div class="sidebar">
                <el-button plain style="width:100%; border=none">个人资料</el-button>
            </div>
            <div class="msg">
                <div class="msgHead">
                    <br>
                    &nbsp;&nbsp;<el-avatar :size="50" :src="circleUrl" class="avatar" @click="handleAvatarClick">
                        <span class="plus">+</span>
                        <input ref="fileInput" type="file" name="avater"  @change="handleFileChange" class="fileInput" style="display: none;"/>
                    </el-avatar>
                </div>
                <div class="msgBody">
                    <h3>&nbsp;&nbsp;基本信息</h3>
                    <hr color="#f5f5f5">
                    <table class="infoTable">
                        <tr>
                            <td class="label">&nbsp;&nbsp;用户昵称：</td>
                            <td><input type="text" class="input" placeholder="请输入用户昵称" v-model="username">{{username}}</td>
                        </tr>
                        <tr>
                            <td class="label">&nbsp;&nbsp;年龄：</td>
                            <td><input type="number" class="input" placeholder="请输入年龄" v-model="age">{{age}}</td>
                        </tr>
                        <tr>
                            <td class="label">&nbsp;&nbsp;性别：</td>
                            <td>
                                <select class="input" v-model="sex">{{sex}}
                                <option value="male">男</option>
                                <option value="female">女</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="label">&nbsp;&nbsp;手机号：</td>
                            <td><input type="tel" class="input" placeholder="请输入手机号" v-model="telNumber">{{telNumber}}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped>
.userCenter{
    height:100vh;
}
.ucBody{
    display:flex;
    margin-left:20%;
    margin-top:10px;
    height:92vh;
    width:60%;
}
.sidebar{
    width:15%;
    height:50%;
    flex:0 0 15%;
    display:flex;
    background-color:white;
    
}
.msg{
    margin-left:5px;
    width:43%;
    height:95%;
    flex:1;
    display:flex;
    flex-direction:column;

}
.msgHead{
    background-color:white;
    width:100%;
    height:20%;

}
.avatar {
  position: relative;
  overflow: hidden;
  display: inline-block;
}

.avatar .plus {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 24px;
  color: #fff;
  cursor: pointer;
}


.msgBody{
    margin-top:5px;
    background-color:white;
    width:100%;
    height:80%;

}
.infoTable {
  width: 100%;
}

.label {
  color: #666;
  width:100px;
}

.input {
  width: 200px;
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

tr {
  line-height: 45px; /* 调整行间距 */
}
</style>