<script>
import LocationForm from '@/components/LocationForm.vue'
import CityService from '@/services/CityService.js'

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
        .then((response) => this.handleGetCitiesRequestResponse(response))
        .catch()
        .finally()
    },
    handleGetCitiesRequestResponse(response) {},
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
        <LocationForm />
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col justify-content-center">
        <button class="btn btn-secondary" type="submit">Tagasi</button>
        <button class="btn btn-success" type="submit">Lisa</button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
