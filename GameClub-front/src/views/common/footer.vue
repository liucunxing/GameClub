<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
</script>
<template>
    <div class="foot">
        <div class="context">Developer: 落魄的cv工程师&nbsp;&nbsp;Status:Creating.....</div>
    </div>  
</template>
<style scoped>
.foot{
  position: absolute;
  bottom: 0;
  min-height:6%;
  background-color:#000000;
  width:100%;
  text-align:center;
}
.context{
    margin-top:10px;
    font-size:12px;
}

</style>