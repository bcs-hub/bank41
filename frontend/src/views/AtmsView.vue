<script>
import CityService from '@/services/CityService.js'
import CitiesDropdown from '@/components/CitiesDropdown.vue'
import LocationService from '@/services/LocationService.js'
import NavigationService from '@/services/NavigationService.js'
import AlertDanger from '@/components/AlertDanger.vue'
import LocationsTable from '@/components/LocationsTable.vue'

export default {
  name: 'AtmsView',
  components: { LocationsTable, AlertDanger, CitiesDropdown },
  data() {
    return {
      userId: sessionStorage.getItem('userId'),
      roleName: sessionStorage.getItem('roleName'),
      cityId: 0,
      errorMessage: '',

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
              transactionTypeId: 0,
              transactionTypeName: '',
              isAvailable: true,
            },
          ],
        },
      ],
      errorResponse: {
        message: '',
        errorCode: '',
      },
    }
  },
  methods: {
    getCities() {
      CityService.getCitiesRequest()
        .then((response) => this.handleGetCitiesResponse(response))
        .catch(() => NavigationService.navigateToErrorView())
        .finally()
    },

    getLocations() {
      this.errorMessage = ''
      LocationService.getLocationsRequest(this.cityId)
        .then((response) => this.handleGetLocationsResponse(response))
        .catch((error) => this.handleGetLocationsErrorResponse(error))
        .finally()
    },

    handleGetCitiesResponse(response) {
      this.cities = response.data
    },

    reloadLocationsTable(cityId) {
      this.cityId = cityId
      this.getLocations()
    },
    handleGetLocationsResponse(response) {
      this.locations = response.data
    },
    handleGetLocationsErrorResponse(error) {
      this.errorResponse = error.response.data
      if (error.response.status === 404 && this.errorResponse.errorCode === 'NO_LOCATION_FOUND') {
        this.errorMessage = this.errorResponse.message
        this.locations = []
      }
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
    <div class="row justify-content-center mb-4">
      <div class="col col-5">
        <h1>Pangaautomaadid</h1>
        <AlertDanger :error-message="errorMessage" />
      </div>
    </div>

    <div class="row">
      <div class="col col-2">
        <CitiesDropdown :cities="cities" @event-new-city-selected="reloadLocationsTable" />
      </div>

      <div class="col">
        <LocationsTable :locations="locations" />
      </div>
    </div>
  </div>
</template>
