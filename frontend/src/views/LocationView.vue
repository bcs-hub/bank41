<script>
import LocationForm from '@/components/LocationForm.vue'
import CityService from '@/services/CityService.js'
import NavigationService from '@/services/NavigationService.js'
import TransactionTypeService from '@/services/TransactionTypeService.js'
import TransactionTypesCheckbox from '@/views/TransactionTypesCheckbox.vue'

export default {
  name: 'LocationView',
  components: { TransactionTypesCheckbox, LocationForm },
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
        numberOfAtms: 1,
        imageData: '',
        lng: 0.0,
        lat: 0.0,
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
      CityService.getCitiesRequest()
        .then((response) => this.handleGetCitiesResponse(response))
        .catch(() => NavigationService.navigateToErrorView())
    },
    handleGetCitiesResponse(response) {
      this.cities = response.data
    },
    getLocationTransactionTypes() {
      TransactionTypeService.getTransactionTypesRequest()
        .then((response) => this.handleGetLocationTransactionTypesResponse(response))
        .catch()
    },
    handleGetLocationTransactionTypesResponse(response) {
      this.location.transactionTypes = response.data
    },
    getLocationLongLatValues(values) {
      const resultArray = values.split(', ')
      this.location.lat = Number(resultArray[0])
      this.location.lng = Number(resultArray[1])
    },
    updateLocationTransactionTypes(updatedCheckbox) {
      // let transactionType = this.location.transactionTypes.find(
      //   (tType) => tType.transactionTypeId === updatedCheckbox.transactionTypeId,
      // );
    },
  },
  beforeMount() {
    this.getCities()
    this.getLocationTransactionTypes()
  },
}
</script>

<template>
  <div class="container text-center row-gap-3 d-grid p-4">
    <div class="row">
      <div class="col">
        <h1>Lisa asukoht</h1>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col">
        <LocationForm
          :cities="cities"
          :location="location"
          @event-new-city-selected="location.cityId = $event"
          @event-new-location-name-input="location.locationName = $event"
          @event-new-number-of-atms-input="location.numberOfAtms = $event"
          @event-new-location-map-input="getLocationLongLatValues"
          @event-transaction-type-updated="updateLocationTransactionTypes"
          @event-new-image-selected="location.imageData = $event"
          @event-chosen-image-cleared="location.imageData = ''"
        />
      </div>
    </div>
    <div class="row justify-content-center gx-4">
      <div class="col col-12 d-flex gap-2 justify-content-center">
        <button type="button" class="btn btn-secondary">Tagasi</button>
        <button type="submit" class="btn btn-success">Lisa</button>
      </div>
    </div>
  </div>
</template>
