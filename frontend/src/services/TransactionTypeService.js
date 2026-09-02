import axios from 'axios'

// export default - teeb selle objekti teistele failidele kättesaadavaks, Et teised failid saaksid seda kasutada
// Axios on JavaScripti teek, mis on ehitatud brauseri enda fetch API peale ja teeb HTTP päringute tegemise mugavamaks.
export default {
  getTransactionTypesRequest() {
    return axios.get('/api/atm/transaction-types')
  },
}
//request config" — objekt, mille saab axios.get(url, config) teise argumendina kaasa anda (nt headers, params, timeout)
