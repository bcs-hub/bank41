<script>
export default {
  name: 'CitiesDropdown',
  props: {
    cities: Array,
    cityId: {
      type: Number,
      default: 0,
    },
  },
  emits: ['event-new-city-selected'],
}
</script>

<template>
  <select
    :value="cityId"
    @change="$emit('event-new-city-selected', Number($event.target.value))"
    class="form-select"
    aria-label="Default select example"
  >
    <option :value="0">Kõik linnad</option>
    <option v-for="city in cities" :key="city.cityId" :value="city.cityId">
      {{ city.cityName }}
    </option>
  </select>
</template>
