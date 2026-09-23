<template>
  <h5>Vali võimalikud ATM toimingud</h5>
  <div
    v-for="transactionType in transactionTypes"
    :key="transactionType.transactionTypeId"
    class="form-check"
  >
    <input
      :checked="transactionType.isAvailable"
      @input="$emit('event-transaction-type-checkbox-toggled', transactionType.transactionTypeId)"
      class="form-check-input"
      type="checkbox"
      :id="'transactionTypeId-' + transactionType.transactionTypeId"
    />
    <label class="form-check-label" :for="'transactionTypeId-' + transactionType.transactionTypeId">
      {{ transactionType.transactionTypeName }}
    </label>
  </div>
</template>

<script>
export default {
  name: 'TransactionTypesCheckbox',
  props: {
    transactionTypes: Array,
  },
  emits: ['event-transaction-type-checkbox-toggled'],
}
</script>
