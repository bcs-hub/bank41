<script>
export default {
  name: 'TransactionTypesCheckbox',
  props: {
    transactionTypes: Array,
  },
  methods: {
    emitEventTransactionTypesCheckboxUpdated(transactionTypeId, checked) {
      let updatedCheckbox = {
        transactionTypeId: transactionTypeId,
        checked: checked,
      }
      this.$emit('event-transaction-types-checkbox-updated', updatedCheckbox)
    },
  },
  emits: ['event-transaction-types-checkbox-updated'],
}
</script>

<template>
  <div class="mb-4">
    <h5>ATM teenused</h5>
    <div
      v-for="transactionType in transactionTypes"
      :key="transactionType.transactionTypeId"
      class="form-check form-switch text-start"
    >
      <input
        @change="
          emitEventTransactionTypesCheckboxUpdated(
            transactionType.transactionTypeId,
            $event.target.checked,
          )
        "
        class="form-check-input"
        type="checkbox"
        role="switch"
        :checked="transactionType.isAvailable"
      />
      <label class="form-check-label">{{ transactionType.transactionTypeName }}</label>
    </div>
  </div>
</template>
