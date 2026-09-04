<script>
import CityService from '@/services/CityService.js'
import CitiesDropdown from '@/components/CitiesDropdown.vue'
import LocationService from '@/services/LocationService.js'
import NavigationService from '@/services/NavigationService.js'
import AlertDanger from '@/components/AlertDanger.vue'
import LocationsTable from '@/components/LocationsTable.vue'
import BaseModal from '@/components/BaseModal.vue'
import { PhProhibit } from '@phosphor-icons/vue'

export default {
  name: 'AtmsView',
  components: { PhProhibit, BaseModal, LocationsTable, AlertDanger, CitiesDropdown },
  beforeMount() {
    this.getCities()
    this.getLocations()
  },
  data() {
    return {
      errorMessage: '',
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

    handleGetCitiesResponse(response) {
      this.cities = response.data
    },

    reloadLocationsTable(cityId) {
      this.cityId = cityId
      this.getLocations()
    },

    getLocations() {
      this.errorMessage = ''
      LocationService.getAtmLocationsRequest(this.cityId)
        .then((response) => this.handleGetLocationsResponse(response))
        .catch((error) => this.handleGetLocationsErrorResponse(error))
        .finally()
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
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center mb-4">
      <div class="col col-5">
        <BaseModal :is-open="true">
          <template #title> <PhProhibit :size="32" /></template>

          <template #body><cities-dropdown /></template>

          <template #button></template>
        </BaseModal>
        <h1>Pangaautomaadid</h1>
        <AlertDanger :error-message="errorMessage" />
      </div>
    </div>

    <div class="row justify-content-center">
      <div class="col col-2">
        <CitiesDropdown :cities="cities" @event-new-city-selected="reloadLocationsTable" />
      </div>

      <div class="col col-5">
        <!-- todo  SIIN ON ASUKOHTADE TABEL     -->
        <LocationsTable :locations="locations" />
      </div>
    </div>
  </div>
</template>
