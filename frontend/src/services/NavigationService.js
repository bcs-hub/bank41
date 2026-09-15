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
    // todo: kommenteeri sisse tagasi kui lahendus on valmis
    // router.push({
    //   name: 'errorRoute',
    // })
  },
  navigateToNotAuthorizedView() {
    router.push({
      name: 'notAuthorizedRoute',
    })
  },
}
