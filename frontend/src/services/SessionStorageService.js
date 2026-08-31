export default {
  userIsLoggedIn() {
    return sessionStorage.getItem('userId') !== null
  }
}
