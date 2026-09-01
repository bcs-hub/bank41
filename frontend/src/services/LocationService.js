import axios from 'axios'

export default {

  getAtmLocations(cityId) {
    // todo: Kustuta prefer osa ära, kui enam Stoplighti mock backi ei kasuta
    let preferValue
    if (cityId === 0) {
      preferValue = 'code=200, example=1'
    } else if (cityId === 2){
      preferValue = 'code=200, example=2'
    } else if (cityId === 3){
      preferValue = 'code=403, example=3'
    } else {
      preferValue = 'code=200, example=1'
    }
    return axios.get('/api/atm/locations', {
      headers: {
        Prefer: preferValue
    },
      params: {
        cityId: cityId

      }
    })


  },
}
