<script>
import { RouterLink, RouterView } from 'vue-router'
import SessionStorageService from '@/services/SessionStorageService.js'
// import { PhSignOut } from '@phosphor-icons/vue'
import NavigationService from "@/services/NavigationService.js";

export default {
  name: 'App',
  components: { RouterLink, RouterView },
  data() {
    return {
      isLoggedIn: SessionStorageService.userIsLoggedIn(),
    }
  },
  methods: {
    executeLogOut() {
      sessionStorage.clear()
      this.isLoggedIn = false
      NavigationService.navigateToHomeView()
    },
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
          <button @click="executeLogOut" class="btn btn-primary" type="submit">Logi välja</button>
        </div>
        <div v-else>
          <RouterLink class="nav-link" to="/login">Sisse logimine</RouterLink>
        </div>
      </div>
    </div>
  </nav>
  <RouterView @event-user-logged-in="isLoggedIn = true" />
</template>
