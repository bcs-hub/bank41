<script>
export default {
  name: 'LocationNameInput',
  emits: ['event-new-location-name-input'],
}
</script>

<template>
  <div class="form-floating mb-2">
    <input
      @input="$emit('event-new-location-name-input', $event.target.value)"
      type="email"
      class="form-control"
      id="floatingInput"
      placeholder=""
    />
    <label for="floatingInput">Asukoht</label>
  </div>
</template>
