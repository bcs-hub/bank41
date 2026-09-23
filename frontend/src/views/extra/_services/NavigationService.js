import router from '@/router/index.js'

export default {
  navigateToErrorView() {
    // todo: peale arendust lülita sisse
    // router.push({ name: 'errorRoute' })
  },

  navigateToNotAuthorizedView() {
    router.push({ name: 'notAuthorizedRoute' })
  },
}
