<script>
export default {
  name: 'NumberOfAtmsInput',
  emits: ['event-new-number-of-atms-input'],
}
</script>

<template>
  <div class="form-floating mb-3">
    <input
      @input="$emit('event-new-number-of-atms-input', $event.target.value)"
      type="number"
      class="form-control"
      placeholder=""
      min="1"
    />
    <label for="floatingInput">Automaatide arv</label>
  </div>
</template>
