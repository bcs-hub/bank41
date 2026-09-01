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
      errorMessage: '',
      userId: sessionStorage.getItem('userId'),
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
    getCitites() {
      CityService.getCitiesRequest()
        .then((response) => this.handleGetCitiesResponse(response))
        .catch(() => NavigationService.navigateToErrorView())
        .finally()
    },
    getLocations() {
      this.errorMessage = ''
      LocationService.getAtmLocationsRequest(this.cityId)
        .then((response) => this.handleGetLocationsResponse(response))
        .catch((error) => this.handleGetLocationsErrorResponse(error))
        .finally()
    },
    reloadLocationsTable(cityId) {
      this.cityId = cityId
      this.getLocations()
    },
    handleGetCitiesResponse(response) {
      this.cities = response.data
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
    this.getCitites()
    this.getLocations()
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col col-8">
        <h1 class="mt-2">Pangaautomaadid</h1>
        <AlertDanger :errorMessage="errorMessage" />
      </div>
    </div>
    <div class="row mt-2 justify-content-center">
      <div class="col col-4">
        <CitiesDropdown :cities="cities" @event-new-city-selected="reloadLocationsTable" />
      </div>
      <div class="col col-5">
        <LocationsTable :locations="locations" />
      </div>
    </div>
  </div>
</template>
