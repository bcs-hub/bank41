import axios from 'axios'

export default {
  postAtmLocationRequest(location) {
    return axios.post('/api/atm/location', location)
  },

  getAtmLocationRequest(locationId) {
    return axios.get(`/api/atm/location/${locationId}`)
  },

  getAtmLocationsRequest(cityId) {
    return axios.get('/api/atm/locations', {
      params: {
        cityId: cityId,
      },
    })
  },

  putAtmLocationRequest(locationId, location) {
    return axios.put(`/api/atm/location/${locationId}`, location)
  },
}
