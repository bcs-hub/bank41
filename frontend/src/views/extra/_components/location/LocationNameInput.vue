<template>
  <div class="form-floating mb-3">
    <input
      :value="locationName"
      @input="$emit('event-location-name-updated', $event.target.value)"
      type="text"
      class="form-control"
      id="inputLocationName"
      placeholder="Asukoht"
    />
    <label for="inputLocationName">Asukoht</label>
  </div>
</template>

<script>
export default {
  name: 'LocationNameInput',
  props: {
    locationName: String,
  },
  emits: ['event-location-name-updated'],
}
</script>
