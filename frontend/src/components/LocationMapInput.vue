<script>
export default {
  name: 'LocationMapInput',
  emits: ['event-new-location-map-input'],
}
</script>

<template>
  <div class="form-floating">
    <input
      @input="$emit('event-new-location-map-input', $event.target.value)"
      type="text"
      class="form-control"
      id="floatingInput"
      placeholder=""
    />
    <label for="floatingInput">Asukoha koordinaadid</label>
  </div>
</template>
