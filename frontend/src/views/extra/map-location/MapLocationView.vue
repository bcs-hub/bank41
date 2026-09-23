<template>
  <div class="container-fluid">
    <div class="row">
      <h1 class="mb-3">ATM asukoha lisamine</h1>
    </div>

    <div v-if="successMessage" class="alert alert-success mb-3">{{ successMessage }}</div>
    <div v-if="errorMessage" class="alert alert-danger mb-3" style="white-space: pre-line">{{ errorMessage }}</div>

    <div class="row">
      <!-- Veerg 1: Kaart -->
      <div class="col-lg-5 col-md-6">
        <div class="panel-wrapper">
          <h5 class="panel-title">Kaart</h5>
          <p class="text-muted small mb-2">Kliki kaardil asukoha koordinaatide valimiseks</p>

          <!-- Kaardi juhtnupud -->
          <div class="mb-3">
            <h6 class="mb-2">Kaardi juhtnupud</h6>
            <div class="d-flex gap-2 flex-wrap">
              <button type="button" class="btn btn-sm btn-outline-primary" @click="resetView">
                Lähtesta kaart
              </button>
              <button type="button" class="btn btn-sm btn-outline-secondary" @click="toggleLabels">
                {{ showLabels ? 'Peida sildid' : 'Näita silte' }}
              </button>
              <button type="button" class="btn btn-sm btn-outline-info" @click="toggleCounties">
                {{ showCounties ? 'Peida maakonnad' : 'Näita maakondi' }}
              </button>
            </div>
          </div>

          <div class="map-wrapper">
            <l-map
              ref="mapRef"
              :zoom="zoom"
              :center="center"
              :bounds="mapBounds"
              :options="mapOptions"
              style="height: 100%; width: 100%;"
              @click="handleMapClick"
            >
              <l-tile-layer :url="tileUrl" :attribution="attribution"></l-tile-layer>

              <l-geo-json
                v-if="showCounties && countyData"
                :key="countyData.features.length"
                :geojson="countyData"
                :options="computedGeoJsonOptions"
              ></l-geo-json>

              <l-marker
                v-if="showLabels && countyLabels.length > 0"
                v-for="county in countyLabels"
                :key="county.name"
                :lat-lng="county.center"
                :options="{ opacity: 0, icon: invisibleIcon }"
              >
                <l-tooltip :options="{ permanent: true, direction: 'center', className: 'county-label' }">
                  {{ county.name }}
                </l-tooltip>
              </l-marker>

              <l-marker v-if="clickPin" :lat-lng="clickPin">
                <l-popup>
                  <div>
                    <strong>Valitud asukoht</strong><br>
                    Lat: {{ clickPin[0].toFixed(6) }}<br>
                    Lng: {{ clickPin[1].toFixed(6) }}
                  </div>
                </l-popup>
              </l-marker>
            </l-map>
          </div>
        </div>
      </div>

      <!-- Veerg 2: Andmete vorm -->
      <div class="col-lg-4 col-md-6">
        <div class="panel-wrapper">
          <h5 class="panel-title">Andmed</h5>

          <div class="mb-3">
            <label class="form-label">Linn</label>
            <CitiesDropdown
              :cities="cities"
              :selected-city-id="location.cityId"
              first-option-label="Vali linn"
              :first-option-is-disabled="true"
              @event-new-city-selected="location.cityId = $event"
            />
          </div>

          <LocationForm
            :location="location"
            @event-location-name-updated="location.locationName = $event"
            @event-number-of-atms-updated="location.numberOfAtms = $event"
            @event-transaction-type-checkbox-toggled="handleTransactionTypeCheckboxToggle"
          />

          <div class="d-flex gap-2 mt-3">
            <button @click="addLocation" type="button" class="btn btn-outline-success">Lisa asukoht</button>
            <button @click="resetForm" type="button" class="btn btn-outline-secondary">Tühjenda</button>
          </div>
        </div>
      </div>

      <!-- Veerg 3: Pilt -->
      <div class="col-lg-3 col-md-12">
        <div class="panel-wrapper">
          <h5 class="panel-title">Pilt</h5>

          <div class="mb-3">
            <AtmImage :image-data="location.imageData" />
          </div>

          <ImageInput
            ref="imageInputRef"
            :reset-file-input="resetImageInput"
            @event-new-image-selected="location.imageData = $event"
            @event-reset-image-select-complete="resetImageInput = false"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { LGeoJson, LMap, LMarker, LPopup, LTileLayer, LTooltip } from '@vue-leaflet/vue-leaflet'
import geoJsonData from '../map-json-county/geo-Json-Data.json'
import CitiesDropdown from '../_components/CitiesDropdown.vue'
import LocationForm from '../_components/location/LocationForm.vue'
import AtmImage from '../_components/image/AtmImage.vue'
import ImageInput from '../_components/image/ImageInput.vue'
import CityService from '../_services/CityService.js'
import LocationService from '../_services/LocationService.js'
import TransactionTypeService from '../_services/TransactionTypeService.js'
import NavigationService from '../_services/NavigationService.js'

export default {
  name: 'MapLocationView',
  components: { LMap, LTileLayer, LGeoJson, LMarker, LPopup, LTooltip, CitiesDropdown, LocationForm, AtmImage, ImageInput },

  data() {
    return {
      // Kaardi seaded
      zoom: 7,
      center: [58.7, 25.3],
      tileUrl: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution: '© OpenStreetMap contributors',
      mapOptions: { zoomControl: true, scrollWheelZoom: true },
      countyData: null,
      countyLabels: [],
      showLabels: false,
      showCounties: false,
      clickPin: null,

      // Vormi andmed
      location: {
        cityId: 0,
        locationName: '',
        numberOfAtms: 1,
        imageData: '',
        lng: null,
        lat: null,
        transactionTypes: [],
      },

      cities: [],
      resetImageInput: false,
      successMessage: '',
      errorMessage: '',
    }
  },

  computed: {
    mapBounds() {
      if (!this.countyData || !this.countyData.features || this.countyData.features.length === 0) {
        return null
      }
      try {
        const group = new L.featureGroup()
        this.countyData.features.forEach(feature => {
          group.addLayer(L.geoJSON(feature))
        })
        return group.getBounds()
      } catch (e) {
        return null
      }
    },

    invisibleIcon() {
      return L.divIcon({ html: '', iconSize: [0, 0], iconAnchor: [0, 0], className: 'invisible-marker' })
    },

    computedGeoJsonOptions() {
      return {
        onEachFeature: (feature, layer) => {
          try {
            const defaultStyle = { fillColor: '#3498db', weight: 2, opacity: 1, color: '#2c3e50', dashArray: '', fillOpacity: 0.3 }
            if (layer && typeof layer.setStyle === 'function') {
              layer.setStyle(defaultStyle)
              layer._originalStyle = defaultStyle
            }
            if (layer && typeof layer.on === 'function') {
              layer.on({ mouseover: this.highlightFeature, mouseout: this.resetHighlight })
            }
            const name = this.getCountyDisplayName(feature.properties)
            if (name && layer && typeof layer.bindTooltip === 'function') {
              layer.bindTooltip(name, { permanent: false, direction: 'auto' })
            }
          } catch (e) {
            console.warn('Viga kihi seadistamisel:', e)
          }
        }
      }
    }
  },

  mounted() {
    this.loadCountyData()
    this.getCities()
    this.getTransactionTypes()
  },

  methods: {
    loadCountyData() {
      try {
        this.countyData = geoJsonData
        this.generateCountyLabels()
      } catch (e) {
        console.error('Viga maakonna andmete laadimisel:', e)
      }
    },

    generateCountyLabels() {
      if (!this.countyData || !this.countyData.features) return
      this.countyLabels = this.countyData.features
        .map(feature => ({
          name: this.getCountyDisplayName(feature.properties),
          center: this.getFeatureCenter(feature.geometry)
        }))
        .filter(l => l.name && l.center)
    },

    getFeatureCenter(geometry) {
      if (!geometry || !geometry.coordinates) return null
      try {
        let coords = []
        if (geometry.type === 'Polygon') {
          coords = geometry.coordinates[0]
        } else if (geometry.type === 'MultiPolygon') {
          let largest = geometry.coordinates[0]
          let largestArea = 0
          geometry.coordinates.forEach(polygon => {
            if (polygon[0].length > largestArea) {
              largestArea = polygon[0].length
              largest = polygon
            }
          })
          coords = largest[0]
        }
        if (!coords.length) return null
        let lats = 0, lngs = 0
        coords.forEach(c => { lngs += c[0]; lats += c[1] })
        return [lats / coords.length, lngs / coords.length]
      } catch (e) {
        return null
      }
    },

    getCountyDisplayName(properties) {
      if (!properties) return 'Tundmatu'
      return properties['name:et'] || properties['name:en'] || properties.name || properties.official_name || 'Maakond'
    },

    highlightFeature(e) {
      const layer = e.target
      if (layer && typeof layer.setStyle === 'function') {
        layer.setStyle({ fillColor: '#3498db', weight: 10, color: '#e74c3c', dashArray: '', fillOpacity: 0.1 })
        if (typeof layer.bringToFront === 'function') layer.bringToFront()
      }
    },

    resetHighlight(e) {
      const layer = e.target
      if (layer && typeof layer.setStyle === 'function' && layer._originalStyle) {
        try { layer.setStyle(layer._originalStyle) } catch (err) { console.warn(err) }
      }
    },

    handleMapClick(e) {
      if (!e.latlng) return
      this.location.lat = e.latlng.lat
      this.location.lng = e.latlng.lng
      this.clickPin = [e.latlng.lat, e.latlng.lng]
    },

    resetView() {
      this.clickPin = null
      this.location.lat = null
      this.location.lng = null
      this.showLabels = false
      this.showCounties = false
      if (this.$refs.mapRef && this.$refs.mapRef.leafletObject) {
        this.$refs.mapRef.leafletObject.setView([58.7, 25.3], 7)
      }
    },

    toggleLabels() {
      this.showLabels = !this.showLabels
    },

    toggleCounties() {
      this.showCounties = !this.showCounties
    },

    getCities() {
      CityService.sendGetCitiesRequest()
        .then(response => (this.cities = response.data))
        .catch(() => NavigationService.navigateToErrorView())
    },

    getTransactionTypes() {
      TransactionTypeService.sendGetTransactionTypesRequest()
        .then(response => (this.location.transactionTypes = response.data))
        .catch(() => NavigationService.navigateToErrorView())
    },

    handleTransactionTypeCheckboxToggle(transactionTypeId) {
      this.location.transactionTypes = this.location.transactionTypes.map(t =>
        t.transactionTypeId === transactionTypeId ? { ...t, isAvailable: !t.isAvailable } : t
      )
    },

    validateForm() {
      const errors = []
      if (!this.location.cityId) errors.push('Vali linn')
      if (!this.location.locationName) errors.push('Täida asukoha nimi')
      if (this.location.numberOfAtms < 1) errors.push('Vali vähemalt 1 pangaautomaat')
      if (!this.location.transactionTypes.some(t => t.isAvailable)) errors.push('Vali vähemalt 1 ATM toiming')
      if (!this.location.lat || !this.location.lng) errors.push('Kliki kaardil asukoha valimiseks')
      return errors
    },

    addLocation() {
      this.successMessage = ''
      this.errorMessage = ''

      const errors = this.validateForm()
      if (errors.length) {
        this.errorMessage = errors.join('\n')
        return
      }

      LocationService.sendPostAtmLocation(this.location)
        .then(() => {
          this.successMessage = 'Asukoht "' + this.location.locationName + '" on edukalt lisatud!'
          this.resetForm()
        })
        .catch(error => {
          const status = error.response?.status
          const data = error.response?.data
          if (status === 403 && data?.errorCode === 333) {
            this.errorMessage = data.message
          } else {
            NavigationService.navigateToErrorView()
          }
        })
    },

    resetForm() {
      this.location.cityId = 0
      this.location.locationName = ''
      this.location.numberOfAtms = 1
      this.location.imageData = ''
      this.location.lat = null
      this.location.lng = null
      this.clickPin = null
      this.resetImageInput = true
      this.getTransactionTypes()
    }
  }
}
</script>

<style scoped>
.panel-wrapper {
  padding: 16px;
  height: calc(100vh - 100px);
  overflow-y: auto;
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  margin-bottom: 16px;
}

.panel-title {
  font-weight: 600;
  margin-bottom: 12px;
  padding-bottom: 8px;
  border-bottom: 2px solid #dee2e6;
}

.map-wrapper {
  height: calc(100vh - 320px);
  min-height: 350px;
  border-radius: 6px;
  overflow: hidden;
  border: 1px solid #dee2e6;
}

:deep(.county-label) {
  background: rgba(255, 255, 255, 0.9) !important;
  border: 1px solid #ddd !important;
  border-radius: 4px !important;
  font-weight: 600 !important;
  font-size: 11px !important;
  padding: 3px 6px !important;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2) !important;
  color: #2c3e50 !important;
}

:deep(.county-label::before) {
  display: none !important;
}

.invisible-marker {
  background: none !important;
  border: none !important;
}

.form-label.small {
  font-size: 0.75rem;
  font-weight: 600;
  color: #6c757d;
}

@media (max-width: 992px) {
  .panel-wrapper {
    height: auto;
    max-height: 60vh;
  }

  .map-wrapper {
    height: 40vh;
    min-height: 300px;
  }
}
</style>
