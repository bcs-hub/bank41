import LocationService from '../../_services/LocationService.js'
import CityService from '../../_services/CityService.js'

export default {
  sendGetAtmLocationsDetail(cityId = 0) {
    return LocationService.sendGetAtmLocations(cityId)
  },

  sendGetCities() {
    return CityService.sendGetCitiesRequest()
  },
}
