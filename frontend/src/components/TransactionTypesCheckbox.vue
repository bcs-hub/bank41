<script>
export default {
  name: 'TransactionTypesCheckbox',
  props: {
    transactionTypes: Array,
  },
}
</script>

<template>
  <div class="mb-4">
    <div
      v-for="transactionType in transactionTypes"
      :key="transactionType.transactionTypeId"
      class="form-check form-switch text-start"
    >
      <input
        class="form-check-input"
        type="checkbox"
        role="switch"
        :checked="transactionType.isAvailable"
      />
      <label class="form-check-label">{{ transactionType.transactionTypeName }}</label>
    </div>
  </div>
</template>
