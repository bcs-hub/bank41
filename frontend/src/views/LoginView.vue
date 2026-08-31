<script>
import AlertDanger from '@/components/AlertDanger.vue'
import LoginService from '@/services/LoginService.js'
import NavigationService from '@/services/NavigationService.js'

export default {
  name: 'LoginView',
  components: { AlertDanger },
  methods: {
    login() {
      this.resetErrorMessage()

      if (this.allFieldsHaveInput()) {
        this.startSpinner()

        LoginService.postLoginRequest(this.loginRequest)
          .then((response) => this.handleLoginResponse(response))
          .catch()
          .finally(this.stopSpinner())
      } else {
        this.errorMessage = 'Täida kõik väljad / vale kasutajanimi või parool'
      }
    },
    allFieldsHaveInput() {
      return this.loginRequest.username.length > 0 && this.loginRequest.password.length > 0
    },
    saveLoginResponseInfoToSessionStorage() {
      sessionStorage.setItem('userId', this.loginResponse.userId)
      sessionStorage.setItem('roleName', this.loginResponse.roleName)
    },
    handleLoginResponse(response) {
      this.loginResponse = response.data
      this.saveLoginResponseInfoToSessionStorage()
      // parent update navbar

      this.$emit('event-user-logged-in');
      NavigationService.navigateToAtmsView()
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
  },
  data() {
    return {
      errorMessage: '',
      showSpinner: false,
      loginRequest: {
        username: 'test',
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
        <h2 class="mb-4">Sisse logimine</h2>
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
        <button v-if="showSpinner" type="submit" class="btn btn-primary" disabled>
          <span class="spinner-border spinner-border-sm" aria-hidden="true"></span>
          <span role="status">Login sisse...</span>
        </button>
        <button v-else @click="login" type="submit" class="btn btn-primary">Logi sisse</button>
      </div>
    </div>
  </div>
</template>
