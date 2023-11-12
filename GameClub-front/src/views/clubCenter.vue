<script setup lang="ts">
import { ref, reactive, onMounted, watchEffect } from "vue";
import { ElMessage } from "element-plus";
import { useRoute, useRouter } from "vue-router";
import Header from "@/views/common/header.vue";
import Footer from "@/views/common/footer.vue";
import axios from "../axios/axios";
const instance2 = axios.create({
  baseURL: "http://localhost:8180/", // 默认的基本URL
  timeout: 50000, // 请求超时时间
});
axios.interceptors.request.use(config => {
  config.headers.Authorization = localStorage.getItem("token"); // 添加请求头
  return config;
});
instance2.interceptors.request.use(config => {
  config.headers.Authorization = localStorage.getItem("token"); // 添加请求头
  return config;
});


const total = ref();
const textarea2 = ref();
const dialogCreateVisible = ref(false);
const fileInput = ref<HTMLInputElement | null>(null);
const circleUrl = ref('');
const avaterFile = ref<File | null>(null);
const ClubQueryDto = reactive({
  pageIndex: 1,
  pageSize: 8,
  clubName: "",
});
const CreateClubDto = reactive({
  clubName: "",
  description: "",
  iconUrl: "",
});
const clubListData = ref([]);
const handleCurrentChange = (val: number) => {
  ClubQueryDto.pageIndex = val;
  getCLubList();
};
const getCLubList = async () => {
  const res = await axios.post("http://localhost:8180/api/club/noAuth/getClubList", ClubQueryDto);
  clubListData.value = res.data.data;
  total.value = res.data.total;
};
const search = async () => {
  getCLubList();
};
const goStart = () => {
  ClubQueryDto.clubName = "";
};
const createClub = async () =>{
  const res = await instance2.post("api/club/createClub",CreateClubDto);
  alert(res.data.data);
};
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
    CreateClubDto.iconUrl = res.data.data;
    circleUrl.value = res.data.data;
  } catch (error) {
    console.error(error);
    // 错误处理逻辑
  }
};
const clickCreate = () => {};

onMounted(() => {
  getCLubList();
});
</script>
<template>
  <div class="clubCenter">
    <Header />
    <div class="ccBody">
      <el-dialog v-model="dialogCreateVisible" title="Create Club">
        <span class="label">请输入俱乐部名称</span>
        <el-input class="custom-input" v-model="CreateClubDto.clubName" /><br /><br />
        <span class="label">俱乐部详细信息</span>&nbsp;&nbsp;&nbsp;
        <el-input
          v-model="CreateClubDto.description"
          :autosize="{ minRows: 2, maxRows: 4 }"
          type="textarea"
          placeholder="Please input"/><br /><br />
          <span class="label">请上传俱乐部头像
            <el-avatar  shape="square" :size="50" :src="circleUrl" class="avatar" @click="handleAvatarClick">
                <span class="plus">+</span>
                  <input ref="fileInput" type="file" name="avater"  @change="handleFileChange" class="fileInput" style="display: none;"/>
              </el-avatar>
          </span><br /><br />
        <el-button type="primary" @click="createClub">创建</el-button>
        <el-button type="success" @click="goStart">重置</el-button>
      </el-dialog>
      <div class="sidebar">
        <el-button plain style="width:100%; border=none">俱乐部列表</el-button>
        <el-button
          plain
          style="width:100%; border=none"
          class="createButton"
          @click="dialogCreateVisible = true"
          >创建俱乐部</el-button
        >
      </div>
      <div class="msg">
        <div class="msgBody">
          <div class="search">
            <span class="label">请输入俱乐部名称</span>
            <el-input v-model="ClubQueryDto.clubName" class="custom-input" />
            <el-button type="primary" @click="search">搜索</el-button>
            <el-button type="success" @click="goStart">重置</el-button>
          </div>
          <hr />
          <el-table :data="clubListData" style="width: 100%">
            <el-table-column label="图标" width="180" v-slot="{ row }">
              <el-image style="width: 25%; height: 25%" :src="row.iconUrl" :fit="fit" />
            </el-table-column>
            <el-table-column prop="clubName" label="名称" width="180" />
            <el-table-column prop="orderCount" label="订单数" />
            <el-table-column prop="hotCount" label="热度" />
            <el-table-column prop="createTime" label="成立时间" />
          </el-table>
          <el-pagination
            class="pagination"
            v-model:current-page="ClubQueryDto.pageIndex"
            @current-change="handleCurrentChange"
            background
            layout="prev, pager, next"
            :page-size="ClubQueryDto.pageSize"
            :total="total"
          />
        </div>
      </div>
    </div>
    <Footer />
  </div>
</template>
<style scoped>
.clubCenter {
  height: 100vh;
}

.ccBody {
  display: flex;
  margin-left: 20%;
  margin-top: 10px;
  height: 92vh;
  width: 60%;
}
.sidebar {
  width: 15%;
  height: 50%;
  background-color: white;
}
.createButton {
  margin-left: 0px;
}
.msg {
  margin-left: 5px;
  width: 43%;
  height: 95%;
  flex: 1;
  display: flex;
  flex-direction: column;
}
.search {
  display: flex;
  align-items: center;
  height: 8%;
}
.msgBody {
  position: relative;
  background-color: white;
  width: 100%;
  height: 90%;
}
.pagination {
  position: absolute;
  right: 3px;
  bottom: 3px;
}
.custom-input {
  flex: 1;
  max-width: 20%;
  margin-right: 40%;
}
.label {
  color: black;
  size: 10px;
  margin-left: 10px;
  margin-right: 10px; /* 可根据需要调整 label 和 input 之间的间距 */
}
</style>
