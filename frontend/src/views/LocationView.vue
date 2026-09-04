<script>
import LocationForm from '@/components/location/LocationForm.vue'
import CityService from '@/services/CityService.js'
import NavigationService from '@/services/NavigationService.js'
import TransactionTypeService from '@/services/TransactionTypeService.js'
import AlertDanger from '@/components/alert/AlertDanger.vue'
import LocationService from '@/services/LocationService.js'
import AlertSuccess from '@/components/alert/AlertSuccess.vue'
import SessionStorageService from '@/services/SessionStorageService.js'

export default {
  name: 'LocationView',
  components: { AlertSuccess, AlertDanger, LocationForm },
  beforeMount() {
    if (SessionStorageService.userIsAdmin()) {
      this.getCities()
      this.getLocationTransactionTypes()
    } else {
      NavigationService.navigateToNotAuthorizedView()
    }
  },
  data() {
    return {
      successMessage: '',
      errorMessage: '',

      cities: [
        {
          cityId: 0,
          cityName: '',
        },
      ],

      imageData: {
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

      errorResponse: {
        message: '',
        errorCode: '',
      },
    }
  },
  methods: {
    addLocation() {
      this.resetSuccessMessage()
      this.resetErrorMessage()
      this.checkFormForErrors()

      if (this.errorMessageIsEmpty()) {
        LocationService.postAtmLocationRequest(this.location)
          .then(() => this.handleAddLocationResponse())
          .catch((error) => this.handleAddLocationErrorResponse(error))
      }
    },

    checkFormForErrors() {
      if (this.location.cityId === 0) {
        this.errorMessage = 'Vali linn'
      } else if (this.location.locationName === '') {
        this.errorMessage = 'Lisa asukoha nimi'
      } else if (this.location.numberOfAtms < 1) {
        this.errorMessage = 'Automaatide arv peab olema vähemalt 1'
      } else if (!this.transactionTypeIsSelected()) {
        this.errorMessage = 'Vali vähemalt üks ATM teenus'
      }
    },

    transactionTypeIsSelected() {
      for (let transactionType of this.location.transactionTypes) {
        if (transactionType.isAvailable) {
          return true
        }
      }

      return false
    },


    errorMessageIsEmpty() {
      return this.errorMessage === ''
    },

    handleAddLocationResponse() {
      this.successMessage = 'Pangaautomaadi asukoht "' + this.location.locationName + '" on lisatud'
      this.resetAllFields()
    },

    resetAllFields() {
      this.location.cityId = 0
      this.location.locationName = ''
      this.location.numberOfAtms = 1
      this.location.imageData = ''
      this.location.lat = 0.0
      this.location.lng = 0.0
      this.getLocationTransactionTypes()
    },

    handleAddLocationErrorResponse(error) {
      this.errorResponse = error.response.data

      if (
        error.response.status === 403 &&
        this.errorResponse.errorCode === 'LOCATION_UNAVAILABLE'
      ) {
        this.errorMessage = this.errorResponse.message
      } else {
        NavigationService.navigateToErrorView()
      }
    },

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
        .catch(() => NavigationService.navigateToErrorView())
        .finally()
    },

    handleGetLocationTransactionTypesResponse(response) {
      this.location.transactionTypes = response.data
    },

    setLocationLngLat(lngLat) {
      let resultArray = lngLat.split(', ')
      this.location.lat = Number(resultArray[0])
      this.location.lng = Number(resultArray[1])
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

    // todo: JÄRG ON SIIN

    resetSuccessMessage() {
      this.successMessage = ''
    },

    resetErrorMessage() {
      this.errorMessage = ''
    },
  },
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col col-5">
        <AlertSuccess :success-message="successMessage" />
        <AlertDanger :error-message="errorMessage" />
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
        <button @click="addLocation" class="btn btn-success" type="submit">Lisa</button>
      </div>
    </div>
  </div>
</template>
