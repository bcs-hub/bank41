<script>
import CitiesDropdown from '@/components/CitiesDropdown.vue'
import LocationNameInput from '@/components/LocationNameInput.vue'
import LocationMapInput from '@/components/LocationMapInput.vue'
import TransactionTypesCheckbox from '@/components/TransactionTypesCheckbox.vue'
import NumberOfAtmsInput from '@/components/NumberOfAtmsInput.vue'
import ImageInput from '@/components/image/ImageInput.vue'

export default {
  name: 'LocationForm',
  components: {
    ImageInput,
    NumberOfAtmsInput,
    TransactionTypesCheckbox,
    LocationMapInput,
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
          :city-id="location.cityId"
          @event-new-city-selected="$emit('event-new-city-selected', $event)"
        />
      </div>
      <div class="col col-3">
        <LocationNameInput
          :location-name="location.locationName"
          @event-new-location-name-input="$emit('event-new-location-name-input', $event)"
        />
        <NumberOfAtmsInput
          :number-of-atms="location.numberOfAtms"
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
        <img v-if="location.imageData === ''" src="@/assets/images/atm.png" class="img-thumbnail" alt="Pangaautomaadi pilt">
        <img v-else :src="location.imageData" class="img-thumbnail" alt="Pangaautomaadi pilt">
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col col-4">
        <ImageInput @event-new-image-selected="$emit('event-new-image-selected', $event)"
        @event-chosen-image-cleared="$emit('event-chosen-image-cleared')"/>
      </div>
    </div>
  </div>
</template>
