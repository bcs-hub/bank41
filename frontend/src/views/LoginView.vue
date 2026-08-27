<script>
export default {
  name: 'LoginView',
  methods: {
    login() {

      if (this.loginRequest.username === '' || this.loginRequest.password === '') {

        alert('Täida kõik välja')
      }
    },
  },
  data() {
    return {
      message: 'Täida kõik välja',


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


        <div v-if="true" class="alert alert-danger" role="alert"> {{message}}</div>




      </div>

    </div>

    <div class="row justify-content-center">
      <div class="col col-3">
        <h2>Sisse logimine</h2>

        <div class="form-floating mb-3">
          <input v-model="loginRequest.username" type="text" class="form-control" placeholder="" />
          <label>Kasutajanimi</label>
        </div>
        <div class="form-floating mb-3">
          <input
            v-model="loginRequest.password"
            type="password"
            class="form-control"
            placeholder=""
          />
          <label>Parool</label>
        </div>

        <button @click="login" type="submit" class="btn btn-primary">Logi sisse</button>
      </div>
    </div>
  </div>
</template>
