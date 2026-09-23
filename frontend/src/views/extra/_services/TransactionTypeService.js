import axios from 'axios'

export default {
  sendGetTransactionTypesRequest() {
    return axios.get('/api/atm/transaction-types')
  },
}
