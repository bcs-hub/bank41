<script>
import CityService from '@/services/CityService.js'
import CitiesDropdown from '@/components/CitiesDropdown.vue'

export default {
  name: 'AtmsView',
  components: { CitiesDropdown },
  data() {
    return {
      userId: sessionStorage.getItem('userId'),
      cities: [
        {
          cityId: 0,
          cityName: '',
        },
      ],
    }
  },
  methods: {
    getCitites() {
      CityService.getCitiesRequest()
        .then((response) => this.handleGetCitiesResponse(response))
        .catch()
        .finally()
    },
    handleGetCitiesResponse(response) {
      this.cities = response.data
    },
    alertWithCityId(cityId) {
      console.log(cityId);
      alert(cityId)
    },
  },
  beforeMount() {
    this.getCitites()
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row">
      <div class="col">
        <h1>Pangaautomaadid</h1>
      </div>
    </div>
    <div class="row">
      <div class="col col-4">
        <CitiesDropdown :cities="cities" @event-new-city-selected="alertWithCityId" />
      </div>
      <div class="col">placeholder tabel</div>
    </div>
  </div>
</template>
