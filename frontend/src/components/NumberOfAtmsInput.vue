<script>
export default {
  name: 'NumberofAtmsInput',
  emits: ['event-new-number-of-atms-input'],
}
</script>

<template>
  <div class="form-floating mb-3">
    <input
      @input="$emit('event-new-number-of-atms-input',  $event.target.value)"
      type="number"
      class="form-control"
      placeholder=""
    />
    <label for="floatingInput">Automaatide arv</label>
  </div>
</template>
