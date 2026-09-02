<script>
import LocationForm from '@/components/LocationForm.vue'
import cityService from '@/services/CityService.js'
import TransactionTypeService from '@/services/TransactionTypeService.js'
import NavigationService from '@/services/NavigationService.js'

export default {
  name: 'LocationView',
  components: { LocationForm },
  data() {
    return {
      cities: [
        {
          cityId: 0,
          cityName: '',
        },
      ],

      location: {
        cityId: 0,
        locationName: '',
        numberOfAtms: 0,
        imageData: '',
        lat: 0.0,
        lng: 0.0,

        transactionTypes: [
          {
            transactionTypeId: 0,
            transactionTypeName: '',
            isAvailable: true,
          },
        ],
      },
    }
  },
  methods: {
    getCities() {
      cityService
        .getCitiesRequest()
        .then((response) => this.handleGetCitiesResponse(response))
        .catch(() => NavigationService.navigateToErrorView())
    },

    handleGetCitiesResponse(response) {
      this.cities = response.data
    },

    getTransactionTypes() {
      TransactionTypeService.getTransactionTypesRequest()
        .then((response) => this.handleGetTransactionTypesResponse(response))
        .catch(() => NavigationService.navigateToErrorView())
    },

    handleGetTransactionTypesResponse(response) {
      this.location.transactionTypes = response.data
    },
    handleTransactionTypeCheckboxUpdated(updatedCheckbox) {
      const transactionType = this.location.transactionTypes.find(
        (transactionType) =>
          transactionType.transactionTypeId === updatedCheckbox.transactionTypeId,
      )
      transactionType.isAvailable = updatedCheckbox.checked
    },

    setLocationLngLat(lngLat) {
      const resultArray = lngLat.split(',').map((value) => Number(value.trim()))
      this.location.lng = resultArray[0]
      this.location.lat = resultArray[1]
    },
  },

  beforeMount() {
    this.getCities()
    this.getTransactionTypes()
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col">
        <h1>Lisa asukoht</h1>
      </div>
    </div>
    <div class="row justify-content-center">
      <LocationForm
        :cities="cities"
        :location="location"
        @event-new-city-selected="location.cityId = $event"
        @event-new-location-name-input="location.locationName = $event"
        @event-new-number-of-atms-input="location.numberOfAtms = $event"
        @event-new-location-map-input="setLocationLngLat"
        @event-transaction-types-checkbox-updated="handleTransactionTypeCheckboxUpdated"
      />

      <div></div>
      <div class="row justify-content-center">
        <div class="col">
          <button class="btn btn-secondary" type="submit">Tagasi</button>
          <button class="btn btn-success" type="submit">Lisa</button>
        </div>
      </div>
    </div>
  </div>
</template>
