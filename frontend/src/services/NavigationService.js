import router from '@/router/index.js'

export default {
  navigateToAtmsView() {
    router.push({
      name: 'atmsRoute',
    })
  },
  navigateToHomeView() {
    router.push({
      name: 'homeRoute',
    })
  },
  navigateToErrorView() {
    router.push({
      name: 'errorRoute',
    })
  },
  navigateToNotAuthorizedView() {
    router.push({
      name: 'notAuthorizedRoute',
    })
  },
}
