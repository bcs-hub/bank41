<script>
import AlertDanger from '@/components/AlertDanger.vue'
import axios from 'axios'
import router from '@/router/index.js'

export default {
  name: 'LoginView',
  components: { AlertDanger },
  methods: {
    login() {
      console.log('Olen siin')
      this.resetMessage()
      if (this.allFieldsHaveInput()) {
        this.startSpinner()

        axios
          .post('/api/login', this.loginRequest)
          .then((response) => this.handleLoginResponse(response))
          .catch()
          .finally(() => this.stopSpinner())
      } else {
        this.errorMessage = 'Täida kõik väljad'
      }
    },

    allFieldsHaveInput() {
      return this.loginRequest.username.length > 0 && this.loginRequest.password.length > 0
    },

    handleLoginResponse(response) {
      this.loginResponse = response.data
      sessionStorage.setItem('userId', this.loginResponse.userId)
      sessionStorage.setItem('roleName', this.loginResponse.roleName)

      router.push({
        name: 'atmsRoute'
      })

      // Vastusest saadud roleName väärtus salvestatakse sessionStorage faili võtmega roleName
    },

    startSpinner() {
      this.showSpinner = true
    },

    stopSpinner() {
      this.showSpinner = false
    },

    resetMessage() {
      this.errorMessage = ''
    },
  },
  data() {
    return {
      errorMessage: '',
      showSpinner: false,

      loginRequest: {
        username: '',
        password: '',
      },

      loginResponse: {
        userId: 0,
        roleName: '',
      },
    }
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col col-6">
        <AlertDanger :error-message="errorMessage" />
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

        <button v-if="showSpinner" class="btn btn-primary" type="button" disabled>
          <span class="spinner-border spinner-border-sm" aria-hidden="true"></span>
          <span role="status">Login sisse...</span>
        </button>
        <button v-else @click="login" type="submit" class="btn btn-primary">Logi sisse</button>
      </div>
    </div>
  </div>
</template>
