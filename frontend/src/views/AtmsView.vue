<script>
export default {
  name: 'AtmsView',
  methods: {

    showAlert() {
      alert('userId: ' + this.userId)

      this.$refs.useridShowBox
    },

  },
  beforeMount() {
    this.showAlert()
  },
  data() {
    return {
      userId: sessionStorage.getItem('userId'),
      roleName: sessionStorage.getItem('roleName'),
    }
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row">
      <div class="col">
        <h1>Pangaautomaadid</h1>
        <div ref="useridShowBox">userId: x</div>
        <div>roleName: y</div>
      </div>
    </div>
  </div>
</template>
