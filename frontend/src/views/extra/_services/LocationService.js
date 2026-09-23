import axios from 'axios'

export default {
  sendPostAtmLocation(location) {
    return axios.post('/api/atm/location', location)
  },

  sendPutAtmLocation(locationId, location) {
    return axios.put('/api/atm/location', location, {
      params: { locationId },
    })
  },

  sendDeleteAtmLocation(locationId) {
    return axios.delete('/api/atm/location', {
      params: { locationId },
    })
  },

  sendGetAtmLocation(locationId) {
    return axios.get('/api/atm/location', {
      params: { locationId },
    })
  },

  sendGetAtmLocations(cityId) {
    return axios.get('/api/atm/locations', {
      params: { cityId },
    })
  },

  sendGetAtmLocationsDetail(cityId = 0) {
    return axios.get('/api/atm/locations/detail', {
      params: { cityId },
    })
  },
}
