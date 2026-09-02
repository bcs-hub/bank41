<script>
import LocationForm from '@/components/LocationForm.vue'
import CityService from '@/services/CityService.js'
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
    getLocationLongLatValues(values) {
      const resultArray = values.split(', ');
      this.location.lat = Number(resultArray[0]);
      this.location.lng = Number(resultArray[1]);
    },
  },
  beforeMount() {
    this.getCities()
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
          @event-new-city-selected="location.cityId = $event"
          @event-new-location-name-input="location.locationName = $event"
          @event-new-number-of-atms-input="location.numberOfAtms = $event"
          @event-new-location-map-input="getLocationLongLatValues"
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
