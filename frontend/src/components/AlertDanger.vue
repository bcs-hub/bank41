<script>
export default {
  name: 'AlertDanger',
  props: {
    message: String,
  }
}
</script>

<template>
  <div v-if="message.length" class="alert alert-danger" role="alert">{{ message }}</div>
</template>
