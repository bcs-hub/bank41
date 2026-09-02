<script>
import CitiesDropdown from '@/components/CitiesDropdown.vue'
import LocationNameInput from '@/components/LocationNameInput.vue'
import NumberOfAtmsInput from '@/components/NumberOfAtmsInput.vue'
import LocationMapInput from '@/components/LocationMapInput.vue'

export default {
  name: 'LocationForm',
  components: { LocationMapInput, NumberOfAtmsInput, LocationNameInput, CitiesDropdown },
  props: {
    cities: Array,
  },
  emits: [
    'event-new-city-selected',
    'event-new-location-name-input',
    'event-new-number-of-atms-input',
    'event-new-location-map-input',
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
        <LocationMapInput @event-new-location-map-input="$emit('event-new-location-map-input', $event)" />
      </div>
      <div class="col col-2">pilt</div>
    </div>
  </div>
</template>
