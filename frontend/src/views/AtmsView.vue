<script>
import CityService from '@/services/CityService.js'
import CitiesDropdown from '@/components/CitiesDropdown.vue'
import LocationService from '@/services/LocationService.js'
import NavigationService from '@/services/NavigationService.js'

export default {
  name: 'AtmsView',
  components: { CitiesDropdown },
  data() {
    return {
      userId: sessionStorage.getItem('userId'),
      roleName: sessionStorage.getItem('roleName'),
      cityId: 0,

      cities: [
        {
          cityId: 0,
          cityName: '',
        },
      ],

      locations: [
        {
          locationId: 0,
          locationName: '',
          cityName: '',
          lng: 0,
          lat: 0,
          transactionTypes: [
            {
              transactionTypeId: 1,
              transactionTypeName: '',
              isAvailable: true,
            },
          ],
        },
      ],
    }
  },
  methods: {
    getCities() {
      CityService.getCitiesRequest()
        .then((response) => this.handleGetCitiesResponse(response))
        .catch(() => NavigationService.navigateToErrorView())
        .finally()
    },

    handleGetCitiesResponse(response) {
      this.cities = response.data
    },

    getLocations() {
      LocationService.getAtmLocations(this.cityId)
        .then((response) => this.handleGetLocationsResponse(response))
        .catch()
        .finally()
    },

    reloadLocationsTable(cityId) {
      this.cityId = cityId
      this.getLocations()
    },
    handleGetLocationsResponse(response) {
      this.locations = response.data
    },
  },
  beforeMount() {
    this.getCities()
    this.getLocations()
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row mb-4">
      <div class="col">
        <h1>Pangaautomaadid</h1>
      </div>
    </div>

    <div class="row">
      <div class="col col-2">
        <CitiesDropdown :cities="cities" @event-new-city-selected="reloadLocationsTable" />
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
            <tr v-for="location in locations" :key="location.locationId">
              <td>{{ location.cityName }}</td>
              <td>{{ location.locationName }}</td>
              <td>
                <div v-for="transactionType in location.transactionTypes" :key="transactionType.transactionTypeId">
                  <div v-if="transactionType.isAvailable">
                    {{transactionType.transactionTypeName}}
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
