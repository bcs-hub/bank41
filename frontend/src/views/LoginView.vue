<script>
import AlertDanger from '@/components/AlertDanger.vue'
import LoginService from '@/services/LoginService.js'
import NavigationService from '@/services/NavigationService.js'

export default {
  name: 'LoginView',
  components: { AlertDanger },
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

      errorResponse: {
        message: '',
        errorCode: '',
      },
    }
  },
  methods: {
    login() {
      this.resetErrorMessage()
      if (this.allFieldsHaveInput()) {
        this.startSpinner()

        LoginService.postLoginRequest(this.loginRequest)
          .then((response) => this.handleLoginResponse(response))
          .catch((error) => this.handleLoginErrorResponse(error))
          .finally()
      } else {
        this.errorMessage = 'Täida kõik väljad'
      }
    },

    allFieldsHaveInput() {
      return this.loginRequest.username.length > 0 && this.loginRequest.password.length > 0
    },

    startSpinner() {
      this.showSpinner = true
    },

    stopSpinner() {
      this.showSpinner = false
    },

    resetErrorMessage() {
      this.errorMessage = ''
    },

    handleLoginResponse(response) {
      this.loginResponse = response.data
      this.saveLoginResponseInfoToSessionStorage()

      this.$emit('event-user-logged-in')
      NavigationService.navigateToAtmsView()
    },

    saveLoginResponseInfoToSessionStorage() {
      sessionStorage.setItem('userId', this.loginResponse.userId)
      sessionStorage.setItem('roleName', this.loginResponse.roleName)
    },

    handleLoginErrorResponse(error) {
      console.log("Olen siin")
      this.errorResponse = error.response.data

      if (this.errorResponse.errorCode === 'INCORRECT_CREDENTIALS') {
        this.errorMessage = this.errorResponse.message
      }
    },
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
