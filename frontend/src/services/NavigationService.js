import router from '@/router/index.js'

export default {
  navigateToHomeView() {
    router.push({ name: 'homeRoute' })
  },

  navigateToAtmsView() {
    router.push({ name: 'atmsRoute' })
  },

  navigateToErrorView() {
    router.push({ name: 'errorRoute' })
  },
  navigateToNotAuthorizedView() {
    router.push({ name: 'notAuthorizedRoute'})
  },
}
