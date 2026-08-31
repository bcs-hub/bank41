import axios from "axios";

export default {
  postLoginRequest(loginRequest) {


    // todo: Kustuta prefer osa ära, kui enam Stoplighti mock backi ei kasuta
    let preferValue

    if (loginRequest.username === 'admin') {
      preferValue = 'code=200, example=admin'
    } else if (loginRequest.username === 'error' ){
      preferValue = 'code=403, example=error'
    } else {
      preferValue = 'code=200, example=rain'
    }

    return axios.post('/api/login', loginRequest, {
      headers: {
        Prefer: preferValue
      }
    })
  },
}
