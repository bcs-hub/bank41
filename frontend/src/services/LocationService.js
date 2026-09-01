import axios from 'axios'

export default {
  getAtmLocations(cityId) {
    return axios.get('/api/atm/locations' + {
      params: {
        cityId: cityId
      }
    })


  },
}
