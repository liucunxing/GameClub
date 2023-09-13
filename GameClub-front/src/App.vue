<script lang="ts">
//配置页面刷新
import { provide, ref, nextTick } from "vue";
export default {
  setup() {
    const isRouteActive = ref(true);
    const reload = () => {
      isRouteActive.value = false;
      nextTick(() => {
        isRouteActive.value = true;
      });
    };
    provide("reload", reload);
    return {
      isRouteActive,
      reload,
    };
  },
};
</script>

<template>
  <router-view v-if="isRouteActive"/>
</template>


