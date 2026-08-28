<script>
import AlertDanger from '@/components/AlertDanger.vue'

export default {
  name: 'LoginView',
  components: { AlertDanger },
  methods: {
    login() {
      this.resetMessage();

      if (this.allFieldsHaveInput()) {
        console.log("IF");
        //   proovime sisse logida. saadame backile sõnumi
      } else {
        console.log("else")
        this.errorMessage = 'Täida kõik väljad / vale kasutajanimi või parool'
      }
    },
    allFieldsHaveInput() {
      return this.loginRequest.username.length > 0 && this.loginRequest.password.length > 0
    },
    resetMessage() {
      this.errorMessage = ''
    },
  },
  data() {
    return {
      errorMessage: '',
      loginRequest: {
        username: 'test',
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
        <AlertDanger :message="errorMessage" />
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
        <button @click="login" type="submit" class="btn btn-primary">Logi sisse</button>
      </div>
    </div>
  </div>
</template>
