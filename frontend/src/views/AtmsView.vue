<script>
import CityService from '@/services/CityService.js'
import CitiesDropdown from '@/components/dropdown/CitiesDropdown.vue'
import LocationService from '@/services/LocationService.js'
import NavigationService from '@/services/NavigationService.js'
import AlertDanger from '@/components/alert/AlertDanger.vue'
import LocationsTable from '@/components/location/LocationsTable.vue'
import LocationInfoModal from '@/components/modal/LocationInfoModal.vue'

export default {
  name: 'AtmsView',
  components: { LocationInfoModal, LocationsTable, AlertDanger, CitiesDropdown },
  beforeMount() {
    this.getCities()
    this.getLocations()
  },
  data() {
    return {
      locationInfoModalIsOpen: false,
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

      location: {
        locationId: 0,
        cityId: 0,
        locationName: '',
        numberOfAtms: 0,
        imageData: '',
        lng: 0,
        lat: 0,
        transactionTypes: [
          {
            transactionTypeId: 0,
            transactionTypeName: '',
            isAvailable: false
          }
        ]
      },

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

    handleOpenLocationInfoModal(locationId) {
      LocationService.getAtmLocationRequest(locationId)
          .then(response => this.handleGetLocationResponse(response))
          .catch()
    },

    handleGetLocationResponse(response) {
      this.location = response.data
      this.locationInfoModalIsOpen = true


    }
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center mb-4">
      <div class="col col-5">
        <LocationInfoModal :location-info-modal-is-open="locationInfoModalIsOpen"
                           :location="location"
                           @event-location-info-modal-closed="locationInfoModalIsOpen = false"
        />

        <h1>Pangaautomaadid</h1>
        <AlertDanger :error-message="errorMessage" />
      </div>
    </div>

    <div class="row justify-content-center">
      <div class="col col-2">
        <CitiesDropdown :cities="cities" @event-new-city-selected="reloadLocationsTable" />
      </div>

      <div class="col col-5">
        <LocationsTable :locations="locations" @event-location-name-click="handleOpenLocationInfoModal" />
      </div>
    </div>
  </div>
</template>
