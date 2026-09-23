<template>
  <div class="container-fluid">
    <div class="row align-items-center mb-3">
      <div class="col">
        <h1 class="mb-0">ATM asukohad</h1>
      </div>
    </div>

    <div v-if="errorMessage" class="alert alert-danger mb-3">{{ errorMessage }}</div>

    <div class="row">
      <!-- Veerg 1: Kaart -->
      <div class="col-lg-8 col-md-7">
        <div class="map-wrapper">
          <l-map
            ref="mapRef"
            :zoom="zoom"
            :center="center"
            :options="mapOptions"
            style="height: 100%; width: 100%;"
          >
            <l-tile-layer :url="tileUrl" :attribution="attribution" />

            <l-marker
              v-for="location in locationsWithCoords"
              :key="location.locationId"
              :lat-lng="[location.lat, location.lng]"
            >
              <l-tooltip>{{ location.locationName }}</l-tooltip>
              <l-popup :options="{ minWidth: 200 }">
                <LocationCard :location="location" />
              </l-popup>
            </l-marker>
          </l-map>
        </div>
      </div>

      <!-- Veerg 2: Andmed -->
      <div class="col-lg-4 col-md-5">
        <div class="data-panel">
          <div class="mb-3">
            <label class="form-label fw-semibold">Filtreeri linna järgi</label>
            <select
              class="form-select"
              :value="selectedCityId"
              @change="onCitySelected(Number($event.target.value))"
            >
              <option :value="0">-- Kõik linnad --</option>
              <option v-for="city in cities" :key="city.cityId" :value="city.cityId">
                {{ city.cityName }}
              </option>
            </select>
          </div>

          <div v-if="loading" class="text-center py-4">
            <div class="spinner-border text-primary" role="status">
              <span class="visually-hidden">Laadimine...</span>
            </div>
            <p class="mt-2 text-muted">Laadimine...</p>
          </div>

          <LocationCards
            v-else
            :locations="locations"
            :active-location-id="activeLocationId"
            @event-location-selected="onLocationSelected"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { LMap, LTileLayer, LMarker, LTooltip, LPopup } from '@vue-leaflet/vue-leaflet'
import LocationCards from './components/LocationCards.vue'
import LocationCard from './components/LocationCard.vue'
import AtmsMapLocationService from './services/AtmsMapLocationService.js'

export default {
  name: 'MapAtmsView',
  components: { LMap, LTileLayer, LMarker, LTooltip, LPopup, LocationCards, LocationCard },

  data() {
    return {
      zoom: 7,
      center: [58.7, 25.3],
      tileUrl: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution: '© OpenStreetMap contributors',
      mapOptions: { zoomControl: true, scrollWheelZoom: true },

      locations: [],
      cities: [],
      selectedCityId: 0,
      activeLocationId: null,
      loading: false,
      errorMessage: '',
    }
  },

  computed: {
    locationsWithCoords() {
      return this.locations.filter(l => l.lat && l.lng)
    },
  },

  mounted() {
    this.loadCities()
    this.loadLocations()
  },

  methods: {
    loadLocations() {
      this.loading = true
      this.errorMessage = ''
      AtmsMapLocationService.sendGetAtmLocationsDetail(this.selectedCityId)
        .then(response => {
          this.locations = response.data
        })
        .catch(() => {
          this.errorMessage = 'Asukohtade laadimine ebaõnnestus.'
        })
        .finally(() => {
          this.loading = false
        })
    },

    loadCities() {
      AtmsMapLocationService.sendGetCities()
        .then(response => {
          this.cities = response.data
        })
        .catch(() => {
          this.errorMessage = 'Linnade laadimine ebaõnnestus.'
        })
    },

    onCitySelected(cityId) {
      this.selectedCityId = cityId
      this.activeLocationId = null
      this.loadLocations()
    },

    onLocationSelected(location) {
      this.activeLocationId = location.locationId
      if (location.lat && location.lng && this.$refs.mapRef?.leafletObject) {
        this.$refs.mapRef.leafletObject.setView([location.lat, location.lng], 13)
      }
    },
  },
}
</script>

<style scoped>
.map-wrapper {
  height: calc(100vh - 120px);
  min-height: 400px;
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid #dee2e6;
}

.data-panel {
  height: calc(100vh - 120px);
  overflow-y: auto;
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  padding: 16px;
}

.data-panel::-webkit-scrollbar {
  width: 6px;
}

.data-panel::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.data-panel::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

@media (max-width: 768px) {
  .map-wrapper {
    height: 45vh;
    min-height: 280px;
    margin-bottom: 16px;
  }

  .data-panel {
    height: auto;
    max-height: 50vh;
  }
}
</style>
