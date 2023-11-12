<script setup lang="ts">
import { ref, onMounted,watchEffect } from 'vue';
import { ElMessage } from 'element-plus';
import { useRoute, useRouter } from 'vue-router';
import Header from '@/views/common/header.vue'
import Footer from '@/views/common/footer.vue'
import axios from "../axios/axios";

const circleUrl = ref('');
const route = useRoute();
const router = useRouter();
const hotList = ref([]);
const banners = ref([]);

watchEffect(() => {
    console.log(banners.value); // 打印 hotList 变量的值
  });

const instance2 = axios.create({
  baseURL: 'http://localhost:8180/', // 默认的基本URL
  timeout: 5000, // 请求超时时间
});

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
const cliClub = () =>{
    alert('模块构建中，弹框俱乐部信息');
}
const GoUserCenter = ()=>{
    router.push('/userCenter');
}
const getHotList = async()=>{
    const res = await instance2.get("api/club/noAuth/getHotClub");
    hotList.value = res.data.data;
}
const getClubBanners = async()=>{
    const res = await instance2.get("api/club/noAuth/showClubBanners");
    banners.value = res.data.data;
}
onMounted(() => {
    circleUrl.value = localStorage.getItem('userAvater') || '';
    getHotList();
    getClubBanners();
});
</script>
<template>
    <div class="homePage">
        <Header />
        <div class="banner">
            <el-carousel height="40vh">
            <el-carousel-item v-for="item in banners" :key="item.uuid">
                <el-image style="width: 100%; height: 100%" :src="item.announcePic" :fit="fit" />
            </el-carousel-item>
            </el-carousel>
        </div> 
        <div class="ZC">
            <span style="font-size:25px;color:white">热门俱乐部</span>
            <el-divider style="margin-left:20%;width:60%">Hot Club</el-divider>
        </div>
        <div class="hotClubList">
            <ul class="listContent">
                <li v-for="item in hotList" :key="item.id" @click="cliClub">
                    <div class="itemContainer">
                        <el-image style="width: 70%; height: 70%" :src="item.iconUrl" :fit="fit" />
                        <div class="itemInfo">
                            <span class="itemName">{{ item.clubName }}</span>
                            <span class="itemDescription">{{ item.description }}</span>
                        </div>
                    </div>
                </li>
            </ul>
        </div>  
        <Footer /> 
    </div>
</template>
<style scoped>
.homePage{
    height:100vh;
}
.banner{
    margin-bottom:10px;
}
.ZC{
    text-align:center;
}
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
.hotClubList{
    margin-top:2%;
}
.listContent{
    list-style:none;
    width:80%;
    margin:0px auto;
}
.listContent li{
    width:calc(100% /3);
    float: left;
    margin-bottom:30px;
}
.itemContainer {
  display: flex; /* 使用 flex 布局 */
 margin-right:15px;
}


.itemName {
  color: white; /* 文字颜色设置为白色 */
  margin-left:10px;
}
.itemName:hover{
    color:#FAB702;
}
.itemDescription{
    margin-top:5px;
    color: #808080; /* 文字颜色设置为白色 */
    margin-left:10px;
    font-size:12px;
}
.itemInfo {
  display: flex;
  flex-direction: column;
  margin-left: 10px; /* 文字与图片之间的左边距 */
}

</style>