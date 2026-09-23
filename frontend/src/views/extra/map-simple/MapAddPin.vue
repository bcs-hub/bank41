<template>
  <div class="container-fluid">
    <div class="row mb-3">
      <h1>Kaardile pin'i lisamine</h1>
    </div>
    <div class="row">

      <!-- Vasak: Kaart -->
      <div class="col-lg-8">
        <div class="map-wrapper">
          <l-map
            ref="mapRef"
            v-model:zoom="zoom"
            :center="center"
            @click="handleMapClick"
          >
            <l-tile-layer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />

            <l-marker v-if="pin" :lat-lng="pin">
              <l-tooltip :options="{ permanent: true, direction: 'top' }">
                {{ pin[0].toFixed(5) }}, {{ pin[1].toFixed(5) }}
              </l-tooltip>
            </l-marker>
          </l-map>
        </div>
      </div>

      <!-- Parem: Andmed -->
      <div class="col-lg-4">
        <div class="info-panel">

          <div class="mb-3">
            <label class="form-label">Zoom tase</label>
            <input type="text" class="form-control" :value="pin ? zoom : ''" readonly placeholder="--" />
          </div>

          <div class="mb-3">
            <label class="form-label">Laiuskraad</label>
            <input type="text" class="form-control" :value="pin ? pin[0].toFixed(6) : ''" readonly placeholder="--" />
          </div>

          <div class="mb-3">
            <label class="form-label">Pikkuskraad</label>
            <input type="text" class="form-control" :value="pin ? pin[1].toFixed(6) : ''" readonly placeholder="--" />
          </div>

          <div class="mb-3">
            <label class="form-label">OpenStreetMap link</label>
            <div class="input-group">
              <input type="text" class="form-control" :value="osmLink" readonly placeholder="--" />
              <a v-if="osmLink" :href="osmLink" target="_blank" class="btn btn-outline-secondary">Ava</a>
            </div>
          </div>

          <div class="mb-3">
            <label class="form-label">Google Maps link</label>
            <div class="input-group">
              <input type="text" class="form-control" :value="googleMapsLink" readonly placeholder="--" />
              <a v-if="googleMapsLink" :href="googleMapsLink" target="_blank" class="btn btn-outline-secondary">Ava</a>
            </div>
          </div>

          <button v-if="pin" class="btn btn-outline-danger w-100" @click="clearPin">Eemalda pin</button>
          <p v-else class="text-muted small mt-3">Kliki kaardil pin'i lisamiseks</p>

        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { LMap, LTileLayer, LMarker, LTooltip } from '@vue-leaflet/vue-leaflet'

export default {
  name: 'MapAddPin',
  components: { LMap, LTileLayer, LMarker, LTooltip },

  data() {
    return {
      zoom: 7,
      center: [58.7, 25.3],
      pin: null,
    }
  },

  computed: {
    osmLink() {
      if (!this.pin) return ''
      return `https://www.openstreetmap.org/#map=${this.zoom}/${this.pin[0].toFixed(3)}/${this.pin[1].toFixed(3)}`
    },

    googleMapsLink() {
      if (!this.pin) return ''
      return `https://www.google.com/maps/@${this.pin[0].toFixed(7)},${this.pin[1].toFixed(7)},${this.zoom}z`
    },
  },

  methods: {
    handleMapClick(e) {
      this.pin = [e.latlng.lat, e.latlng.lng]
    },

    clearPin() {
      this.pin = null
    },
  },
}
</script>

<style scoped>
.map-wrapper {
  height: calc(100vh - 140px);
  min-height: 400px;
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid #dee2e6;
}

.info-panel {
  padding: 16px;
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  height: calc(100vh - 140px);
  overflow-y: auto;
}

.form-control[readonly] {
  background-color: #fff;
  font-family: 'Courier New', monospace;
  font-size: 0.875rem;
}

@media (max-width: 992px) {
  .map-wrapper {
    height: 45vh;
    margin-bottom: 16px;
  }

  .info-panel {
    height: auto;
  }
}
</style>
