<script>
export default {
  name: 'AlertDanger',
  props: {
    errorMessage: String,
  },
}
</script>

<template>
  <div v-if="errorMessage.length" class="alert alert-danger" role="alert">{{ errorMessage }}</div>
</template>
