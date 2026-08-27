import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import AtmsView from '@/views/AtmsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'homeRoute',
      component: HomeView,
    },
    {
      path: '/atms',
      name: 'atms',
      component: AtmsView,
    },
  ],
})

export default router
