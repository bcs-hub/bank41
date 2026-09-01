<script>
export default {
  name: 'CitiesDropdown',
  props: {

    cities: Array
  }
}
</script>

<template>
  <select class="form-select" aria-label="Default select example">
    <option :value="0">Kõik linnad</option>
    <option v-for="city in cities" :key="city.cityId" :value="city.cityId">{{city.cityName}}</option>
  </select>
</template>
