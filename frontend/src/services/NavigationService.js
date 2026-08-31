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
}
