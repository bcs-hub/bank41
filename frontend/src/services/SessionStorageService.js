export default {
  userIsLoggedIn() {
    return sessionStorage.getItem('userId') !== null
  },

  userIsAdmin() {
    return sessionStorage.getItem('roleName') === 'admin'
  },
}
