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
      message: 'Täida kõik väljad',
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
      <div class="col col-6">
        <h4>Sisselogimine</h4>

         <div v-if="true" class="alert-danger" role="alert"> {{message}}</div>

        <div class="form-floating mb-3">
          <input v-model="loginRequest.username" type="text" class="form-control" placeholder="" />
          <label>Kasutajanimi</label>
        </div>
        <div class="form-floating">
          <input
            v-model="loginRequest.password"
            type="password"
            class="form-control"
            placeholder=""
          />
          <label>Parool</label>
        </div>
        <button type="submit" class="btn btn-primary">Logi sisse</button>
      </div>
    </div>
  </div>
</template>
