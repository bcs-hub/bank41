<script>
export default {
  name: 'TransactionTypesCheckbox',
  props: {
    transactionTypes: Array
  },
  methods: {
    emitEventTransactionTypesCheckboxUpdated(transactionTypeId, checked) {
      let updatedCheckbox = {
        transactionTypeId: transactionTypeId,
        checked: checked,
      }

      this.$emit('event-transaction-type-updated', updatedCheckbox)
    },
  },
}
</script>

<template>
  <div
    v-for="transactionType in transactionTypes"
    :key="transactionType.transactionTypeId"
    class="mb-2"
  >
    <div class="form-check form-switch text-start">
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
