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
    <div v-if="location.lat || location.lng" class="row g-2 mb-3">
      <div class="col-6">
        <label class="form-label small">Laiuskraad:</label>
        <input type="text" class="form-control form-control-sm" :value="location.lat ? location.lat.toFixed(6) : ''" readonly>
      </div>
      <div class="col-6">
        <label class="form-label small">Pikkuskraad:</label>
        <input type="text" class="form-control form-control-sm" :value="location.lng ? location.lng.toFixed(6) : ''" readonly>
      </div>
    </div>
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
