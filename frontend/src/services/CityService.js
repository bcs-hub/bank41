import axios from "axios";

export default {
  getCitiesRequest() {
   return axios.get('/api/cities')
  },
}
