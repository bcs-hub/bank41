<script>
export default {
  name: 'LocationNameInput',
  emits: ['event-new-location-name-input'],
}
</script>

<template>
  <div class="form-floating mb-3">
    <input
      @input="$emit('event-new-location-name-input')"
      type="text"
      class="form-control"
      placeholder=""
    />
    <label for="floatingInput">Asukoht</label>
  </div>
</template>
