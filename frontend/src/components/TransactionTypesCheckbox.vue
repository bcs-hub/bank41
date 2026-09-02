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
}
</script>

<template>
  <div class="text-start">
    <div
      v-for="transactionType in transactionTypes"
      :key="transactionType.transactionTypeId"
      class="form-check form-switch"
    >
      <input
        :checked="transactionType.isAvailable"
        @change="
          emitEventTransactionTypesCheckboxUpdated(
            transactionType.transactionTypeId,
            $event.target.checked,
          )
        "
        type="checkbox"
        class="form-check-input"
        :id="'transactionType' + transactionType.transactionTypeId"
      />
      <label class="form-check-label" :for="'transactionType' + transactionType.transactionTypeId">
        {{ transactionType.transactionTypeName }}
      </label>
    </div>
  </div>
</template>
