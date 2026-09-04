<script>
export default {
  name: 'NumberOfAtmsInput',
  emits: ['event-new-number-of-atms-input'],
  props: {
    numberOfAtms: Number,
  },
}
</script>

<template>
  <div class="form-floating mb-2">
    <input
      @input="$emit('event-new-number-of-atms-input', $event.target.value)"
      type="number"
      class="form-control"
      id="floatingInput"
      placeholder=""
      min="1"
      :value="numberOfAtms"
    />
    <label for="floatingInput">Automaatide arv</label>
  </div>
</template>
