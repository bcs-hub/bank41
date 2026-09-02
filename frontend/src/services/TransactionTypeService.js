import axios from "axios";

export default {
  getTransactionTypesRequest() {
    return axios.get('/api/atm/transaction-types')
  },
}
