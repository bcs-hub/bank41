import axios from 'axios'

export default {
  temp() {

    let user = new Object()
    user.username = "Rain123"


    let user2 = {
      username: "Rain",
      password: "12321321"
    }
    user2.username = 'Rain123'

  },

  getCitiesRequest() {
    return axios.get('/api/cities')
  },
}
