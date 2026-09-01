<script>
import CityService from '@/services/CityService.js'
import CitiesDropdown from '@/components/CitiesDropdown.vue'
import AlertDanger from '@/components/AlertDanger.vue'
import LocationService from '@/services/LocationService.js'
import navigationService from '@/services/NavigationService.js'
import LocationsTable from '@/components/LocationsTable.vue'

export default {
  name: 'AtmsView',

  components: {
    LocationsTable,
    CitiesDropdown,
    AlertDanger,
  },

  data() {
    return {
      userId: sessionStorage.getItem('userId'),
      roleName: sessionStorage.getItem('roleName'),

      cityId: 0,

      errorMessage: '',

      errorResponse: {
        message: '',
        errorCode: '',
      },

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
        .catch(() => navigationService.navigateToErrorView())
        .finally()
    },

    handleGetCitiesResponse(response) {
      this.cities = response.data
    },

    reloadLocationsTable(cityId) {
      this.cityId = cityId
      this.getLocations()
    },

    alertWithCityId(cityId) {
      alert('cityId: ' + cityId)
    },

    //LSe-location service
    //action---> abimeetod
    getLocations() {
      this.errorMessage = ''

      LocationService.getAtmLocations(this.cityId)
        .then((response) => this.handleGetLocationsResponse(response))
        .catch((error) => this.handleGetLocationsErrorResponse(error))
        .finally()
    },

    resetErrorMessage() {
      this.errorMessage = ''
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
    <div class="row mb-4">
      <div class="col">
        <h1>Pangaautomaadid</h1>
      </div>
    </div>

    <div class="row justify-content-center">
      <div class="col col-6">
        <AlertDanger :error-message="errorMessage" />
      </div>
    </div>

    <div class="row">
      <div class="col col-2">
        <CitiesDropdown
          :cities="cities"
          :city-id="cityId"
          @event-new-city-selected="reloadLocationsTable"
        />
      </div>

      <LocationsTable :locations="locations" />
    </div>
  </div>
</template>
