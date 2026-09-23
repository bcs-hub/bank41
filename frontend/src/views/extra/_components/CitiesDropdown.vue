<template>
  <select
    @change="$emit('event-new-city-selected', Number($event.target.value))"
    :value="selectedCityId"
    class="form-select"
    aria-label="Vali linn"
  >
    <option selected :disabled="firstOptionIsDisabled" :value="0">{{ firstOptionLabel }}</option>
    <option v-for="city in cities" :key="city.cityId" :value="city.cityId">
      {{ city.cityName }}
    </option>
  </select>
</template>

<script>
export default {
  name: 'CitiesDropdown',
  props: {
    cities: Array,
    selectedCityId: Number,
    firstOptionLabel: {
      type: String,
      default: '-- Kõik linnad --',
    },
    firstOptionIsDisabled: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['event-new-city-selected'],
}
</script>
