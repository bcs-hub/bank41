<script>
export default {
  name: 'AtmsView',
  methods: {
    showAlert() {
      alert('userId: ' + this.userId)
    },
  },
    beforeMount() {
      this.showAlert()
    },
  data() {
    return {
      userId: sessionStorage.getItem('userId'),
    }
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row">
      <div class="col">
        <h1>Pangaautomaadid</h1>
        <div>userId: {{ userId }}</div>
        <div>roleName: y</div>
      </div>
    </div>
  </div>
</template>
