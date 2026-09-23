<template>
  <div class="mb-3">
    <LocationNameInput
      :location-name="location.locationName"
      @event-location-name-updated="$emit('event-location-name-updated', $event)"
    />
    <NumberOfAtmsInput
      :number-of-atms="location.numberOfAtms"
      @event-number-of-atms-updated="$emit('event-number-of-atms-updated', $event)"
    />
    <TransactionTypesCheckbox
      :transaction-types="location.transactionTypes"
      @event-transaction-type-checkbox-toggled="$emit('event-transaction-type-checkbox-toggled', $event)"
    />
  </div>
</template>

<script>
import LocationNameInput from './LocationNameInput.vue'
import NumberOfAtmsInput from './NumberOfAtmsInput.vue'
import TransactionTypesCheckbox from './TransactionTypesCheckbox.vue'

export default {
  name: 'LocationForm',
  components: { TransactionTypesCheckbox, NumberOfAtmsInput, LocationNameInput },
  props: {
    location: {},
  },
  emits: [
    'event-location-name-updated',
    'event-number-of-atms-updated',
    'event-transaction-type-checkbox-toggled',
  ],
}
</script>
