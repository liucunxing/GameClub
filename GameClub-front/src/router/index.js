import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/register.vue')
    },
   
    {
      path: '/homePage',
      name: 'homePage',
      component: () => import('../views/homePage.vue')
    },
    {
      path: '/userCenter',
      name: 'userCenter',
      component: () => import('../views/userCenter.vue')
    },
    {
      path: '/clubCenter',
      name: 'clubCenter',
      component: () => import('../views/clubCenter.vue')
    }
  ]
})

export default router
