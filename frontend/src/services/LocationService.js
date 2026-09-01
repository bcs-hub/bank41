import axios from 'axios'

export default {
  getLocationsRequest(cityId) {
    return axios.get('api/atm/locations', {
      params: {
        cityId: cityId,
      },
    })
  },
}
