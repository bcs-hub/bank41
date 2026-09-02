<script>
export default {
  name: 'LocationNameInput',
  emits: ['event-new-location-name-input'],
}
</script>

<template>
  <div class="form-floating mb-3">
    <input
      @input="$emit('event-new-location-name-input', $event.target.value)"
      type="text"
      class="form-control"
      placeholder=""
    />
    <label for="floatingInput">Asukoha nimi</label>
  </div>
</template>
