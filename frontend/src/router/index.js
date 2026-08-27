import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import AtmsView from '@/views/AtmsView.vue'
import ErrorView from '@/views/ErrorView.vue'

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
    {
      path: '/error',
      name: 'error',
      component: ErrorView,
    },
  ],
})

export default router
