<script>
import CitiesDropdown from '@/components/CitiesDropdown.vue'
import LocationNameInput from '@/components/LocationNameInput.vue'
import NumberofAtmsInput from '@/components/NumberOfAtmsInput.vue'

export default {
  name: 'LocationForm',
  components: { NumberofAtmsInput, LocationNameInput, CitiesDropdown },
  props: {
    cities: Array,
  },
  emits: ['event-new-city-selected', 'event-new-location-input', 'event-new-number-of-atms-input'],
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
          @event-new-location-name-input="$emit('event-new-location-input', $event)"/>
        <NumberofAtmsInput @eventNewNumberOfAtmsInput="$emit('event-new-number-of-atms-input')" />
      </div>
      <div class="col col-2">img placeholder</div>
    </div>
  </div>
</template>
