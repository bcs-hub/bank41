import axios from 'axios'

export default {
  getLocationsRequest(cityId) {

    let preferValue

    if (cityId === 0) {
      preferValue = 'code=200, example=0'
    } else if (cityId === 2 ){
      preferValue = 'code=200, example=2'
    } else if (cityId === 3 ){
      preferValue = 'code=200, example=3'
    } else {
      preferValue = 'code=404, example=1'
    }


    return axios.get('api/atm/locations', {
      params: {
        cityId: cityId,
      },
      headers: {
        Prefer: preferValue
      }
    })
  },
}
