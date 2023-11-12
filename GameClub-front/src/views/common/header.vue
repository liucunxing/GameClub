<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { useRoute, useRouter } from 'vue-router';
const circleUrl = ref('');
const route = useRoute();
const router = useRouter();
import axios from "../../axios/axios";
const logout = async() => {
    await axios.get("api/user/logout?id="+localStorage.getItem('userId'));
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    localStorage.removeItem("userAvater");
    localStorage.removeItem('token');
    localStorage.removeItem('userName');
    clearInterval(localStorage.getItem('timer'));
    location.reload();
}
const GoUserCenter = ()=>{
    router.push('/userCenter');
}
const goClubCenter = ()=>{
    router.push('/clubCenter');
}
const goHomePage = ()=>{
    router.push('/homePage');
}
onMounted(() => {
    circleUrl.value = localStorage.getItem('userAvater') || '';
  });
</script>
<template>
        <div class="head">
            <div class="selectH">
                <button class="headB" @click="goHomePage">·首页</button>&nbsp;&nbsp;&nbsp;&nbsp;
                <button class="headB" @click="goClubCenter">·俱乐部中心</button>&nbsp;&nbsp;&nbsp;&nbsp;
                <button class="headB">·活动中心</button>&nbsp;&nbsp;&nbsp;&nbsp;
                <button class="headB">·最新公告</button>&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
            <div class="flex-item">
                <el-dropdown>
                    <span class="el-dropdown-link">
                        <el-avatar :size="size" :src="circleUrl"></el-avatar>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item @click="GoUserCenter">个人中心</el-dropdown-item>
                            <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                            <el-dropdown-item disabled>Action 4</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>        
        </div>  
</template>
<style scoped>
.head {
  display: flex;
  align-items: center; /* 将内容垂直居中 */
  min-height: 6%;
  background-color: #000000;
}

.selectH {
  display: flex;
  margin: 0 auto; /* 将 selectH 元素水平居中 */
}

.flex-item {
  margin-right: 20px; /* 控制头像与其他内容之间的间距 */
  display: flex;
}
.headB{
  font-size: 16px; /* 调整字体大小 */
  border: none; /* 去掉边框 */
  background-color: transparent; /* 去掉背景色 */
  padding: 0; /* 调整内边距 */
  color:white;
}
.headB:hover {
    color:#FAB702; /* 鼠标移到按钮文字时，文字变色 */
}
</style>