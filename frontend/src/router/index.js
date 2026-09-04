import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import ErrorView from '@/views/ErrorView.vue'
import AtmsView from '@/views/AtmsView.vue'
import LoginView from '@/views/LoginView.vue'
import LocationView from '@/views/LocationView.vue'
import NotAuthorizedView from '@/views/NotAuthorizedView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {
      path: '/',
      name: 'homeRoute',
      component: HomeView,
    },
    {
      path: '/error',
      name: 'errorRoute',
      component: ErrorView,
    },
    {
      path: '/atms',
      name: 'atmsRoute',
      component: AtmsView,
    },
    {
      path: '/login',
      name: 'loginRoute',
      component: LoginView,
    },
    {
      path: '/location',
      name: 'locationRoute',
      component: LocationView,
    },
    {
      path: '/not-authorized',
      name: 'notAuthorizedRoute',
      component: NotAuthorizedView,
    },
  ],
})

export default router
