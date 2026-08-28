<script>
export default {
  name: 'AlertDanger',
  props: {
    message: String,
  },
}
</script>

<template>
  <div>
    <div v-if="message !== ''" class="alert alert-danger" role="alert">{{ message }}</div>
  </div>
</template>
