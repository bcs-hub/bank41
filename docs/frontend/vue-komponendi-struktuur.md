# Vue komponendi struktuur (Options API)

See fail kirjeldab meie projektis kasutatavat Vue 3 komponendi ülesehitust Options API-ga.

---

## Üldine järjekord `<script>` sees

```js
export default {
  // 1. Komponendi nimi
  name: 'KomponendNimi',

  // 2. Alamkomponendid
  components: { Komponent1, Komponent2 },

  // 3. Props — sisendandmed vanemkomponendist
  props: {
    propNimi: {
      type: String,
      default: '',
    },
  },

  // 4. Emits — sündmused, mida komponent saadab välja
  emits: ['event-midagi-juhtus'],

  // 5. Data — komponendi reaktiivne sisemine olek
  data() {
    return {
      muutuja: '',
    }
  },

  // 6. Computed — arvutatud väärtused (sõltuvad data või props väljadest)
  computed: {
    arvutatudVäärtus() {
      return this.muutuja.toUpperCase()
    },
  },

  // 7. Methods — funktsioonid ja sündmuste käsitlejad
  methods: {
    teeMiddagi() {
      this.$emit('event-midagi-juhtus', this.muutuja)
    },
  },

  // 8. Lifecycle hook — beforeMount käivitub enne HTML-i renderdamist
  beforeMount() {
    this.laeAndmed()
  },
}
```

---

## Props kirjutamise reeglid

Lihtsatel propidel piisab tüübist:

```js
props: {
  locationName: String,
  selectedCityId: Number,
  isOpen: Boolean,
}
```

Kui on vaja vaikeväärtust või kohustuslikku välja:

```js
props: {
  isOpen: {
    type: Boolean,
    default: false,
  },
  firstOptionLabel: {
    type: String,
    default: '-- Kõik linnad --',
  },
}
```

---

## Emits ja sündmuste nimetamine

Kõik väljalähtuvad sündmused kirjutatakse `emits` massiivi.  
Sündmuse nimi algab alati **`event-`** eesliitega:

```js
emits: ['event-modal-closed', 'event-location-deleted', 'event-new-city-selected']
```

---

## Data — algväärtuste struktuur

`data()` tagastab alati objekti. Keerukamad andmed (API vastused) kirjutatakse välja koos tühja struktuuriga, et Vue saaks reaktiivsuse seadistada:

```js
data() {
  return {
    successMessage: '',
    errorMessage: '',
    selectedCityId: 0,

    location: {
      cityId: 0,
      locationName: '',
      numberOfAtms: 1,
      imageData: '',
      transactionTypes: [
        {
          transactionTypeId: 0,
          transactionTypeName: '',
          isAvailable: false,
        },
      ],
    },

    errorResponse: {
      message: '',
      errorCode: 0,
    },
  }
},
```

---

## Methods — API päringute muster

API päringud käivad `.then()` / `.catch()` / `.finally()` ahelana.  
Iga päringu vastus suunatakse eraldi `handle`-meetodisse:

```js
methods: {
  getLocations() {
    LocationService.sendGetAtmLocations(this.selectedCityId)
      .then((response) => this.handleGetLocationsResponse(response.data))
      .catch((error) => this.handleGetLocationsError(error))
      .finally()
  },

  handleGetLocationsResponse(locations) {
    this.locations = locations
  },

  handleGetLocationsError(error) {
    const statusCode = error.response.status
    this.errorResponse = error.response.data

    if (statusCode === 404 && this.errorResponse.errorCode === 222) {
      this.errorMessage = this.errorResponse.message
      this.locations = []
    } else {
      NavigationService.navigateToErrorView()
    }
  },
},
```

---

## Lifecycle hook

Andmete laadimine käib `beforeMount` sees (mitte `mounted`):

```js
beforeMount() {
  this.successMessage = this.$route.query.successMessage ?? ''
  this.getCities()
  this.getLocations()
},
```

---

## Template — sündmuste ja propide sidumine

**Propid** antakse alla `:`-ga (lühivorm `v-bind:`):
```html
<CitiesDropdown :cities="cities" :selected-city-id="selectedCityId" />
```

**Sündmused** kuulatakse `@`-ga (lühivorm `v-on:`):
```html
<LocationsTable @event-location-deleted="handleLocationDeleted" />
```

Lihtsad sündmused võib kirjutada otse template'i:
```html
@event-modal-closed="isInfoModalOpen = false"
@event-new-city-selected="location.cityId = $event"
```

---

## Täielik näidiskomponent

```vue
<template>
  <div class="container">
    <AlertError :error-message="errorMessage" />

    <CitiesDropdown
      :cities="cities"
      :selected-city-id="selectedCityId"
      @event-new-city-selected="handleCitySelected"
    />

    <button @click="save" class="btn btn-success">Salvesta</button>
  </div>
</template>

<script>
import AlertError from '@/components/alerts/AlertError.vue'
import CitiesDropdown from '@/components/CitiesDropdown.vue'
import CityService from '@/api-services/CityService.js'
import NavigationService from '@/navigation/NavigationService.js'

export default {
  name: 'NäidisView',
  components: { AlertError, CitiesDropdown },
  props: {
    startCityId: {
      type: Number,
      default: 0,
    },
  },
  emits: ['event-saved'],
  data() {
    return {
      errorMessage: '',
      selectedCityId: 0,
      cities: [],
    }
  },
  computed: {
    hasCity() {
      return this.selectedCityId !== 0
    },
  },
  methods: {
    getCities() {
      CityService.sendGetCitiesRequest()
        .then((response) => (this.cities = response.data))
        .catch(() => NavigationService.navigateToErrorView())
        .finally()
    },

    handleCitySelected(cityId) {
      this.selectedCityId = cityId
    },

    save() {
      if (!this.hasCity) {
        this.errorMessage = 'Vali linn'
        return
      }
      this.$emit('event-saved', this.selectedCityId)
    },
  },
  beforeMount() {
    this.selectedCityId = this.startCityId
    this.getCities()
  },
}
</script>
```
