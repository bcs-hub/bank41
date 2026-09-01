<script>
import CityService from '@/services/CityService.js'
import CitiesDropdown from '@/components/CitiesDropdown.vue'

export default {
  name: 'AtmsView',
  components: { CitiesDropdown },
  data() {
    return {
      userId: sessionStorage.getItem('userId'),
      roleName: sessionStorage.getItem('roleName'),

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
        .catch()
        .finally()
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
    <div class="row mt-2">
      <div class="col mb-5">
        <h1>Pangaautomaadid</h1>
      </div>
    </div>
    <div class="row row">
      <div class="col col-2">
        <CitiesDropdown :cities="cities" />
      </div>
      <div class="col">Tabel</div>
    </div>
  </div>
</template>
