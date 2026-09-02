<script>
import LocationForm from '@/components/LocationForm.vue'
import CityService from '@/services/CityService.js'
import NavigationService from '@/services/NavigationService.js'

export default {
  name: 'LocationView',
  components: { LocationForm },
  data() {
    return {
      cities: [
        {
          cityId: 0,
          cityName: '',
        },
      ],
    }
  },
  methods: {
    getCities() {
      CityService.getCitiesRequest()
        .then((response) => this.handleGetCitiesResponse(response))
        .catch(() => NavigationService.navigateToErrorView())
    },

    handleGetCitiesResponse(response) {
      this.cities = response.data
    },
  },
  beforeMount() {
    this.getCities()
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col">
        <h1>Lisa asukoht</h1>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col">
        <LocationForm :cities="cities"/>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col">
        <button class="btn btn-secondary" type="submit">Tagasi</button>
        <button class="btn btn-success" type="submit">Lisa</button>
      </div>
    </div>
  </div>
</template>
