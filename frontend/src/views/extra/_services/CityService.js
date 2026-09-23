import axios from 'axios'

export default {
  sendGetCitiesRequest() {
    return axios.get('/api/cities')
  },
}
