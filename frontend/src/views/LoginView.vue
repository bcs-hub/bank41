<script>
export default {
  name: 'LoginView',

  methods: {
    login() {
      if (this.loginRequest.username === '' || this.loginRequest.password === '') {
        alert('Täida kõik väljad')
      }
    },
  },

  data() {
    return {
      message: '',

      loginRequest: {
        username: '',
        password: '',
      },
    }
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col">
        <div v-if="message" class="alert alert-danger" role="alert">{{ message }}</div>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col col-3">
        <h1>Sisse logimine</h1>

        <div class="form-floating mb-3">
          <input
            v-model="loginRequest.username"
            type="text"
            class="form-control"
            id="floatingInput"
            placeholder="Kasutajanimi"
          />
          <label for="floatingInput">Kasutajanimi</label>
        </div>

        <div class="form-floating mb-4">
          <input
            v-model="loginRequest.password"
            type="password"
            class="form-control"
            id="floatingPassword"
            placeholder="Parool"
          />
          <label for="floatingPassword">Parool</label>
        </div>

        <button @click="login" type="button" class="btn btn-primary d-block mx-auto">
          Logi sisse
        </button>
      </div>
    </div>
  </div>
</template>
