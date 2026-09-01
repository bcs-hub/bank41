<script>
import CityService from "@/services/CityService.js";

export default {
  name: 'AtmsView',
  data() {
    return {
      userId: sessionStorage.getItem('userId'),
      roleName: sessionStorage.getItem('roleName'),

      cities: [
        {
          cityId: 0,
          cityName: ''
        }
      ]


    }
  },
  methods: {
    getCities() {

      CityService.getCitiesRequest()
          .then(response => this.handleGetCitiesResponse(response))
          .catch()
          .finally()
    },

    handleGetCitiesResponse(response) {
      this.cities = response.data
    }
  },
  beforeMount() {
    this.getCities()
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
      <div class="col col-2">
        <select class="form-select" aria-label="Default select example">
          <option value="0">Kõik linnad</option>
          <option value="2">X</option>
        </select>
      </div>

      <div class="col">asukohtade tabel (placeholder)</div>
    </div>
  </div>
</template>
