import axios from 'axios'

export default {
  getAtmLocations(cityId) {
    // todo: Kustuta prefer osa ära, kui enam Stolight mock back ei kasuta
    let preferValue

    if (cityId === 0) {
      preferValue = 'code=200, example=0'
    } else if (cityId === 2) {
      preferValue = 'code=200, example=2'
    } else if (cityId === 3) {
      preferValue = 'code=200, example=3'
    } else {
      preferValue = 'code=404, example=1'
    }

    return axios.get('/api/atm/locations', {
      headers: {
        Prefer: preferValue,
      },
      params: {
        cityId: cityId,
      },
    })
  },
}
