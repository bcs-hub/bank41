import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import AtmsView from '@/views/AtmsView.vue'
import ErrorView from '@/views/ErrorView.vue'
import LoginView from '@/views/LoginView.vue'
import LocationView from '@/views/LocationView.vue'
import SessionStorageService from '@/services/SessionStorageService.js'

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
      name: 'atmsRoute',
      component: AtmsView,
    },
    {
      path: '/error',
      name: 'errorRoute',
      component: ErrorView,
    },
    {
      path: '/login',
      name: 'loginRoute',
      component: LoginView,
    },

    {
      beforeEnter: () => {
        if (!SessionStorageService.userIsAdmin()) {
          return '/'
        }
      },
      path: '/location',
      name: 'locationRoute',
      component: LocationView,
    },
  ],
})

export default router
