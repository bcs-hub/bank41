<script>
import AlertDanger from '@/components/AlertDanger.vue'

export default {
  name: 'LoginView',
  components: { AlertDanger },
  methods: {
    login() {
      this.resetMessage()

      if (this.allFieldsHaveInput()) {
        // proovime sisse logida. saadame backile sõnumile
      } else {
        this.message = 'Täida kõik väljad'
      }
    },

    resetMessage() {
      this.message = ''
    },

    allFieldsHaveInput() {
      return this.loginRequest.username.length > 0 && this.loginRequest.password.length > 0
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
      <div class="col col-6">
        <AlertDanger :message="message" />
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
