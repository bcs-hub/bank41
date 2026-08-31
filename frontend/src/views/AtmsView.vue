<script>
export default {
  name: 'AtmsView',
  methods: {

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
