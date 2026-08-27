<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3 mb-3">
    <RouterLink class="navbar-brand" to="/">
      <div class="rounded p-2">Bank</div>
    </RouterLink>
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
        <RouterLink class="nav-link" to="/">
          <div class="rounded p-2">Kodu</div>
        </RouterLink>
      </div>
      <div class="navbar-nav">
        <RouterLink class="nav-link" to="/atms">
          <div class="rounded p-2">Pangaautomaadid</div>
        </RouterLink>
      </div>
    </div>
  </nav>

  <RouterView />
</template>

<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>
