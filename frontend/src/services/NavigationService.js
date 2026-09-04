import router from '@/router/index.js'

export default {
  navigateToHomeView() {
    router.push({ name: 'homeRoute' })
  },

  navigateToAtmsView(successMessage) {
    router.push({ name: 'atmsRoute', query: successMessage ? { successMessage } : {} })
  },

  navigateToErrorView() {
    router.push({ name: 'errorRoute' })
  },
}

