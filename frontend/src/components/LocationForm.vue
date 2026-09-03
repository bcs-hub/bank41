<script>
import CitiesDropdown from '@/components/CitiesDropdown.vue'
import LocationNameInput from '@/components/LocationNameInput.vue'
import NumberOfAtmsInput from '@/components/NumberOfAtmsInput.vue'
import LocationMapInput from '@/components/LocationMapInput.vue'
import TransactionTypesCheckbox from '@/components/TransactionTypesCheckbox.vue'
import ImageInput from '@/components/image/ImageInput.vue'
import { embertest } from 'globals'

export default {
  name: 'LocationForm',
  computed: {
    embertest() {
      return embertest
    },
  },
  components: {
    ImageInput,
    TransactionTypesCheckbox,
    LocationMapInput,
    NumberOfAtmsInput,
    LocationNameInput,
    CitiesDropdown,
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
    'event-new-image-selected',
    'event-chosen-image-cleared',
  ],
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col col-2">
        <CitiesDropdown
          :cities="cities"
          @event-new-city-selected="$emit('event-new-city-selected', $event)"
        />
      </div>
      <div class="col col-3">
        <LocationNameInput
          @event-new-location-name-input="$emit('event-new-location-name-input', $event)"
        />
        <NumberOfAtmsInput
          @event-new-number-of-atms-input="$emit('event-new-number-of-atms-input', $event)"
        />
        <LocationMapInput
          @event-new-location-map-input="$emit('event-new-location-map-input', $event)"
        />

        <TransactionTypesCheckbox
          :transaction-types="location.transactionTypes"
          @event-transaction-types-checkbox-updated="
            $emit('event-transaction-types-checkbox-updated', $event)
          "
        />
      </div>
      <div class="col col-2">
        <img
          v-if="location.imageData === ''"
          src="@/assets/images/atm.png"
          class="img-thumbnail"
          alt="..."
        />
        <img :src="location.imageData" class="img-thumbnail" alt="..." />
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col col-4">
        <ImageInput
          @event-new-image-selected="$emit('event-new-image-selected', $event)"
          @event-chosen-image-cleared="$emit('event-chosen-image-cleared', $event)"
        />
      </div>
    </div>
  </div>
</template>
