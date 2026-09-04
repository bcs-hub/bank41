<script>
export default {
  name: 'AlertSuccess',
  props: {
    successMessage: String,
  },
}
</script>

<template>
  <div v-if="successMessage.length" class="alert alert-success" role="alert">
    {{ successMessage }}
  </div>
</template>
