import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import UserView from '@/views/UserView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: MainView
    },
    {
      path: '/user/:page',
      name: 'user',
      component: UserView
    },
  ]
})

export default router
