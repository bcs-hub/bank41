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

    alertWithCityId(cityId) {
      alert('cityId: ' + cityId)
    },
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
        <CitiesDropdown :cities="cities" @event-new-city-selected="alertWithCityId" />
      </div>

      <div class="col">
<!-- todo  SIIN ON ASUKOHTADE TABEL     -->
        <table class="table table-dark table-hover">
          <thead>
            <tr>
              <th scope="col">Linn</th>
              <th scope="col">Asukoht</th>
              <th scope="col">Teenused</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Tallinn</td>
              <td>Sikupilli Prisma</td>
              <td>
                <ul>
                  <li>raha sisse</li>
                  <li>raha välja</li>
                </ul>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
