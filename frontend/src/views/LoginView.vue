<script>
export default {
  name: 'LoginView',
  methods: {

    login() {
      alert(this.username + " " + this.password)
    },

  },
  data() {
    return {
      username: '',
      password: ''
    }
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col col-3">
        <h2>Sisse logimine</h2>
        <div class="form-floating mb-3">
          <input v-model="username" type="text" class="form-control" placeholder="" />
          <label>Kasutajanimi</label>
        </div>
        <div class="form-floating mb-3">
          <input v-model="password" type="password" class="form-control" placeholder="" />
          <label>Parool</label>
        </div>

        <button @click="login" type="submit" class="btn btn-primary">Logi sisse</button>
      </div>
    </div>
  </div>
</template>
