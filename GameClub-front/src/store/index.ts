import { defineStore } from 'pinia';
import { reactive, ref,computed } from "vue";
// 使用composition API模式定义store
export const useCounterStoreForSetup = defineStore('counterForSetup', () => {
  const token = ref(
    ''
  )
  const getToken = computed(()=>token);
  function setToken(value:string){
    token.value = value
  }

  return { token,getToken,setToken};
});
