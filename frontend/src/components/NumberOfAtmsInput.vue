<script>
export default {
  name: 'NumberOfAtmsInput',
  emits: ['event-new-number-of-atms-input'],
}
</script>

<template>
  <div class="form-floating">
    <input
      @input="$emit('event-new-number-of-atms-input', $event.target.value)"
      type="number"
      class="form-control"
      id="floatingInput"
      placeholder=""
      min="1"
    />
    <label for="floatingInput">Asukoht</label>
  </div>
</template>
