import axios from 'axios'

const PreferValues = {
  admin: 'code-200, example=admin',
  error: 'code-403, example=error',
  rain: 'code-200, example=rain',
}

export default {
  postLoginRequest(loginRequest) {
    //  todo: Kustuta prefer osa ära kui enam stoplighti mock backi ei kasuta
    let preferValue
    if (loginRequest.username === 'admin') {
      preferValue = PreferValues.admin;
    } else if (loginRequest.username === 'error') {
      preferValue = PreferValues.error;
    } else {
      preferValue = PreferValues.rain;
    }

    return axios.post('/api/login', loginRequest, {
      headers: { Prefer: preferValue },
    })
  },
}
