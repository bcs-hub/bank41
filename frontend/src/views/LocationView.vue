<script>
import LocationForm from '@/components/LocationForm.vue'
import CityService from '@/services/CityService.js'
import NavigationService from '@/services/NavigationService.js'
import TransactionTypeService from '@/services/TransactionTypeService.js'

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
        numberOfAtms: 1,
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
      CityService.getCitiesRequest()
        .then((response) => this.handleGetCitiesResponse(response))
        .catch(() => NavigationService.navigateToErrorView())
    },

    handleGetCitiesResponse(response) {
      this.cities = response.data
    },

    setLocationLngLat(lngLat) {
      let resultArray = lngLat.split(', ')
      this.location.lat = Number(resultArray[0])
      this.location.lng = Number(resultArray[1])
    },

    getLocationTransactionTypes() {
      TransactionTypeService.getTransactionTypesRequest()
        .then((response) => this.handleGetLocationTransactionTypesResponse(response))
        .catch(() => NavigationService.navigateToErrorView())
        .finally()
    },

    handleGetLocationTransactionTypesResponse(response) {
      this.location.transactionTypes = response.data
    },

    updateLocationTransactionTypesIsAvailableValue(updatedCheckbox) {
      // otsib ülesse target objekti arrayst
      let transactionType = this.location.transactionTypes.find(
        (transactionType) =>
          transactionType.transactionTypeId === updatedCheckbox.transactionTypeId,
      )

      // kui objekt on olemas (ei ole null)
      if (transactionType) {
        // siis muuda selle objekti 'isAvailable' -> nii  nagu on 'updatedCheckbox.checked' väärtus
        transactionType.isAvailable = updatedCheckbox.checked
      }
    },
  },
  beforeMount() {
    this.getCities()
    this.getLocationTransactionTypes()
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
      <div class="col">
        <LocationForm
          :cities="cities"
          :location="location"
          @event-new-city-selected="location.cityId = $event"
          @event-new-location-name-input="location.locationName = $event"
          @event-new-number-of-atms-input="location.numberOfAtms = $event"
          @event-new-location-map-input="setLocationLngLat"
          @event-transaction-types-checkbox-updated="updateLocationTransactionTypesIsAvailableValue"
          @event-new-image-selected="location.imageData = $event"
          @event-chosen-image-cleared="location.imageData = ''"
        />
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col">
        <button class="btn btn-secondary me-3" type="submit">Tagasi</button>
        <button class="btn btn-success" type="submit">Lisa</button>
      </div>
    </div>
  </div>
</template>
