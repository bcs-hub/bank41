import axios from "axios";

export default {


  postLoginRequest(loginRequest) {
    return axios.post('/api/login', loginRequest)
  },



}
