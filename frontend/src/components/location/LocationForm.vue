<script>
import CitiesDropdown from '@/components/dropdown/CitiesDropdown.vue'
import LocationNameInput from '@/components/location/LocationNameInput.vue'
import NumberOfAtmsInput from '@/components/location/NumberOfAtmsInput.vue'
import LocationMapInput from '@/components/location/LocationMapInput.vue'
import TransactionTypesCheckbox from '@/components/location/TransactionTypesCheckbox.vue'
import ImageInput from '@/components/image/ImageInput.vue'

export default {
  name: 'LocationForm',
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
          :city-id="location.cityId"
          :cities="cities"
          @event-new-city-selected="$emit('event-new-city-selected', $event)"
        />
      </div>
      <div class="col col-3">
        <LocationNameInput :location-name="location.locationName"
          @event-new-location-name-input="$emit('event-new-location-name-input', $event)"
        />
        <NumberOfAtmsInput :number-of-atms="location.numberOfAtms"
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
          src="../../assets/images/atm.png"
          class="img-thumbnail"
          alt="panga automaadi pilt"
        />
        <img v-else :src="location.imageData" class="img-thumbnail" alt="panga automaadi pilt" />
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col col-4">
        <ImageInput
          @event-new-image-selected="$emit('event-new-image-selected', $event)"
          @event-chosen-image-cleared="$emit('event-chosen-image-cleared')"
        />
      </div>
    </div>
  </div>
</template>
