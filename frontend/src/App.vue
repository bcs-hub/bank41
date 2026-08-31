<script>
import { RouterLink, RouterView } from 'vue-router'

export default {
  name: 'App',
  components: { RouterLink, RouterView },
  data() {
    return {
      isLoggedIn: false,
    }
  },
  beforeMount() {
    let userId = sessionStorage.getItem('userId')

    if ((userId === null)) {
      this.isLoggedIn = false
    } else {
      this.isLoggedIn = true
    }
  },
}
</script>

<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3 mb-3">
    <RouterLink class="navbar-brand" to="/">Bank</RouterLink>
    <button
      class="navbar-toggler"
      type="button"
      data-bs-toggle="collapse"
      data-bs-target="#navMenu"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-center" id="navMenu">
      <div class="navbar-nav">
        <RouterLink class="nav-link" to="/">Kodu</RouterLink>
        <RouterLink class="nav-link" to="/atms">Pangaautomaadid</RouterLink>

        <div v-if="isLoggedIn">
          <RouterLink class="nav-link" to="/">Logi välja</RouterLink>
        </div>
        <div v-else>
          <RouterLink class="nav-link" to="/login">Sisse logimine</RouterLink>
        </div>
      </div>
    </div>
  </nav>
  <RouterView />
</template>
