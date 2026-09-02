<script>
import CitiesDropdown from '@/components/CitiesDropdown.vue'
import LocationNameInput from '@/components/LocationNameInput.vue'
import NumberOfAtmsInput from '@/components/NumberOfAtmsInput.vue'
import LocationMapInput from '@/components/LocationMapInput.vue'
import TransactionTypesCheckbox from '@/components/TransactionTypesCheckbox.vue'

export default {
  name: 'LocationForm',
  components: {
    NumberOfAtmsInput,
    LocationNameInput,
    CitiesDropdown,
    LocationMapInput,
    TransactionTypesCheckbox,
  },
  props: {
    cities: Array,
    location: Object,
  },
  emits: [
    'event-new-city-selected',
    'event-new-location-name-input',
    'event-new-number-of-atms-input',
    'event-new-location-map-input',
    'event-transaction-types-checkbox-updated',
  ],
}
</script>

<template>
  <div class="container text-center">
    <div class="row g-3 justify-content-center align-items-start">
      <div class="col-12 col-sm-6 col-lg-2">
        <CitiesDropdown
          :cities="cities"
          :city-id="location.cityId"
          @event-new-city-selected="$emit('event-new-city-selected', $event)"
        />
      </div>
      <div class="col-12 col-sm-6 col-lg-2">
        <LocationNameInput
          @event-new-location-name-input="$emit('event-new-location-name-input', $event)"
        />
      </div>
      <div class="col-12 col-sm-6 col-lg-2">
        <NumberOfAtmsInput
          @event-new-number-of-atms-input="$emit('event-new-number-of-atms-input', $event)"
        />
      </div>
      <div class="col-12 col-sm-6 col-lg-2">
        <LocationMapInput
          @event-new-location-map-input="$emit('event-new-location-map-input', $event)"
        />
      </div>
      <div class="col-12 col-sm-6 col-lg-2">
        <TransactionTypesCheckbox
          :transaction-types="location.transactionTypes"
          @event-transaction-types-checkbox-updated="
            $emit('event-transaction-types-checkbox-updated', $event)
          "
        />
      </div>
      <div class="col-12 col-sm-6 col-lg-2">pilt</div>
    </div>
  </div>
</template>
