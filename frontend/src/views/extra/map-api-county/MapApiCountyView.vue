<template>
  <div class="container-fluid">
    <div class="row">
      <h1 class="mb-3">Kaart API</h1>
    </div>
    <div class="row">
      <!-- Left Column: Controls and Information -->
      <div class="col-lg-4 col-md-5">
        <div class="controls-panel">
          <!-- Header -->
          <div class="mb-4">
            <h4>Estonian Counties Map</h4>
            <p class="text-muted mb-0">Interactive map of Estonian administrative regions</p>
          </div>

          <!-- Selected County Input -->
          <div class="mb-4">
            <label for="selectedCountyInput" class="form-label">Selected County:</label>
            <input
                id="selectedCountyInput"
                type="text"
                class="form-control mb-2"
                :value="selectedCountyDisplayText"
                readonly
                placeholder="Click a county to select"
            >
            
            <!-- Coordinates Display -->
            <div class="row g-2 mb-2">
              <div class="col-6">
                <label for="latitudeInput" class="form-label small">Click Latitude:</label>
                <input
                    id="latitudeInput"
                    type="text"
                    class="form-control form-control-sm"
                    :value="clickLatitude"
                    readonly
                    placeholder="--"
                >
              </div>
              <div class="col-6">
                <label for="longitudeInput" class="form-label small">Click Longitude:</label>
                <input
                    id="longitudeInput"
                    type="text"
                    class="form-control form-control-sm"
                    :value="clickLongitude"
                    readonly
                    placeholder="--"
                >
              </div>
            </div>
            
            <small class="text-muted">{{ countyCountText }}</small>
          </div>

          <!-- Control Buttons -->
          <div class="mb-4">
            <h6 class="mb-3">Map Controls</h6>
            <div class="d-grid gap-2">
              <button
                  type="button"
                  class="btn btn-outline-primary"
                  @click="resetView"
                  :disabled="loading"
              >
                <i class="fas fa-home me-2"></i>Reset View
              </button>
              <button
                  type="button"
                  class="btn btn-outline-secondary"
                  @click="toggleLabels"
                  :disabled="loading"
              >
                <i class="fas fa-tag me-2"></i>{{ showLabels ? 'Hide Labels' : 'Show Labels' }}
              </button>
              <button
                  type="button"
                  class="btn btn-outline-info"
                  @click="toggleCounties"
                  :disabled="loading"
              >
                <i class="fas fa-map me-2"></i>{{ showCounties ? 'Hide Counties' : 'Show Counties' }}
              </button>

              <!-- Save Response Toggle -->
              <div class="form-check form-switch d-flex align-items-center justify-content-center py-2">
                <input
                  class="form-check-input me-2"
                  type="checkbox"
                  id="saveResponseToggle"
                  v-model="saveResponseAsJson"
                >
                <label class="form-check-label" for="saveResponseToggle">
                  <i class="fas fa-download me-2"></i>Save response as JSON
                </label>
              </div>

              <button
                  type="button"
                  class="btn btn-outline-success"
                  @click="loadCountyData"
                  :disabled="loading"
              >
                <span v-if="loading" class="spinner-border spinner-border-sm me-2" role="status"></span>
                <i v-else class="fas fa-sync-alt me-2"></i>
                {{ loading ? 'Loading...' : 'Reload Data' }}
              </button>
            </div>
          </div>

          <!-- Loading Status -->
          <div v-if="loading" class="mb-4">
            <div class="card border-info">
              <div class="card-body text-center">
                <div class="spinner-border text-primary mb-2" role="status">
                  <span class="visually-hidden">Loading...</span>
                </div>
                <p class="card-text mb-1">{{ loadingMessage }}</p>
                <small class="text-muted">This may take a few moments...</small>
              </div>
            </div>
          </div>

          <!-- Error Message -->
          <div v-if="error" class="alert alert-danger mb-4">
            <div class="d-flex align-items-start">
              <i class="fas fa-exclamation-triangle me-2 mt-1"></i>
              <div class="flex-grow-1">
                <strong>Error:</strong> {{ error }}
                <button type="button" class="btn btn-sm btn-outline-danger mt-2 d-block" @click="loadCountyData">
                  <i class="fas fa-redo me-1"></i>Try Again
                </button>
              </div>
            </div>
          </div>

          <!-- Data Source Info -->
          <div v-if="countyData && !loading" class="alert alert-info mb-4">
            <h6 class="alert-heading">
              <i class="fas fa-info-circle me-2"></i>Data Information
            </h6>
            <p class="mb-1">
              <strong>Counties loaded:</strong> {{ countyData.features.length }}
            </p>
            <p class="mb-1">
              <strong>Source:</strong> OpenStreetMap via Overpass API
            </p>
            <p class="mb-0">
              <strong>Last updated:</strong> {{ lastUpdated }}
            </p>
          </div>

          <!-- Selected County Details -->
          <div v-if="selectedCounty" class="mb-4">
            <div class="card border-primary">
              <div class="card-header bg-primary text-white">
                <h6 class="card-title mb-0">
                  <i class="fas fa-map-marker-alt me-2"></i>County Details
                </h6>
              </div>
              <div class="card-body">
                <h5 class="card-title">{{ getCountyDisplayName(selectedCounty.properties) }}</h5>
                <dl class="row mb-3">
                  <dt class="col-sm-6">Administrative Level:</dt>
                  <dd class="col-sm-6">{{ selectedCounty.properties.admin_level || 'N/A' }}</dd>

                  <dt class="col-sm-6">ISO Code:</dt>
                  <dd class="col-sm-6">{{ selectedCounty.properties['ISO3166-2'] || 'N/A' }}</dd>

                  <dt class="col-sm-6">Type:</dt>
                  <dd class="col-sm-6">{{ selectedCounty.properties.type || 'N/A' }}</dd>
                </dl>
                <button
                    type="button"
                    class="btn btn-sm btn-secondary"
                    @click="selectedCounty = null"
                >
                  <i class="fas fa-times me-1"></i>Close
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Right Column: Map -->
      <div class="col-lg-8 col-md-7">
        <div class="map-wrapper">
          <l-map
              ref="mapRef"
              :zoom="zoom"
              :center="center"
              :bounds="mapBounds"
              :options="mapOptions"
              style="height: 100vh; width: 100%;"
              @click="handleMapClick"
          >
            <l-tile-layer
                :url="tileUrl"
                :attribution="attribution"
            ></l-tile-layer>

            <!-- County polygons -->
            <l-geo-json
                v-if="showCounties && countyData && countyData.features.length > 0"
                :key="countyData.features.length"
                :geojson="countyData"
                :options="computedGeoJsonOptions"
            ></l-geo-json>

            <!-- County labels -->
            <l-marker
                v-if="showLabels && countyLabels.length > 0"
                v-for="county in countyLabels"
                :key="county.name"
                :lat-lng="county.center"
                :options="{ opacity: 0 }"
            >
              <l-tooltip
                  :options="{ permanent: true, direction: 'center', className: 'county-label' }"
              >
                {{ county.name }}
              </l-tooltip>
            </l-marker>

            <!-- Click Pin Marker -->
            <l-marker
                v-if="clickPin"
                :lat-lng="clickPin.coordinates"
            >
              <l-popup :options="{ closeButton: true, autoClose: false }">
                <div class="click-pin-popup">
                  <h6><i class="fas fa-map-pin me-2"></i>{{ clickPin.countyName }}</h6>
                  <p class="mb-1"><strong>Coordinates:</strong></p>
                  <p class="mb-1">Lat: {{ clickPin.coordinates[0].toFixed(6) }}</p>
                  <p class="mb-1">Lng: {{ clickPin.coordinates[1].toFixed(6) }}</p>
                  <button
                    class="btn btn-sm btn-outline-danger mt-2"
                    @click="removePin"
                  >
                    <i class="fas fa-times me-1"></i>Remove Pin
                  </button>
                </div>
              </l-popup>
              <l-tooltip :options="{ permanent: false, direction: 'top' }">
                Click location in {{ clickPin.countyName }}
              </l-tooltip>
            </l-marker>
          </l-map>
        </div>
      </div>
    </div>
    <div class="row justify-content-center">
      <h1 class="m-4">Lisa vajalikud sõltuvused</h1>
      <div class="col">
        <h3>lisa sõltuvused (package.json)</h3>
        <textarea v-model="dependencies" rows="5" cols="40"/>
      </div>
      <div class="col">
        <h3>lisa CSS import (main.js)</h3>
        <textarea v-model="imports" rows="4" cols="40"/>
      </div>
    </div>
  </div>
</template>

<script>
import {LGeoJson, LMap, LMarker, LPopup, LTileLayer, LTooltip} from '@vue-leaflet/vue-leaflet'
import osmtogeojson from 'osmtogeojson'
import axios from 'axios'

export default {
  name: 'MapApiCountyView',
  components: {
    LMap,
    LTileLayer,
    LGeoJson,
    LMarker,
    LPopup,
    LTooltip
  },

  computed: {
    /**
     * Calculates map bounds based on loaded county data
     * Automatically fits map view to show all counties
     * @returns {Object|null} Leaflet bounds object or null if no data
     */
    mapBounds() {
      if (!this.countyData || !this.countyData.features || this.countyData.features.length === 0) {
        return null
      }

      try {
        const group = new L.featureGroup()
        this.countyData.features.forEach(feature => {
          const layer = L.geoJSON(feature)
          group.addLayer(layer)
        })
        return group.getBounds()
      } catch (e) {
        console.warn('Could not calculate map bounds:', e)
        return null
      }
    },

    /**
     * Display text for the selected county input field
     * Shows the county name if one is selected, empty string otherwise
     * @returns {string} Selected county name or empty string
     */
    selectedCountyDisplayText() {
      if (!this.selectedCounty || !this.selectedCounty.properties) {
        return ''
      }
      return this.getCountyDisplayName(this.selectedCounty.properties)
    },

    /**
     * Text showing the total number of loaded counties
     * @returns {string} County count information
     */
    countyCountText() {
      if (!this.countyData || !this.countyData.features) {
        return 'No counties loaded'
      }
      const count = this.countyData.features.length
      return `${count} ${count === 1 ? 'county' : 'counties'} loaded`
    },

    /**
     * Latitude coordinate of the actual click location
     * @returns {string} Formatted latitude value or empty string
     */
    clickLatitude() {
      if (!this.clickCoordinates || this.clickCoordinates.length < 2) {
        return ''
      }
      return this.clickCoordinates[0].toFixed(6) // latitude, 6 decimal places
    },

    /**
     * Longitude coordinate of the actual click location
     * @returns {string} Formatted longitude value or empty string
     */
    clickLongitude() {
      if (!this.clickCoordinates || this.clickCoordinates.length < 2) {
        return ''
      }
      return this.clickCoordinates[1].toFixed(6) // longitude, 6 decimal places
    },

    /**
     * Computed GeoJSON options with proper 'this' context for event handlers
     * @returns {Object} GeoJSON options object with styling and event handlers
     */
    computedGeoJsonOptions() {
      return {
        style: (feature) => {
          try {
            return {
              fillColor: this.getCountyColor(feature),
              weight: 2,
              opacity: 1,
              color: '#2c3e50',
              dashArray: '',
              fillOpacity: 0.3
            }
          } catch (error) {
            console.warn('Error applying style to feature:', error)
            // Return default style if there's an error
            return {
              fillColor: '#3498db',
              weight: 2,
              opacity: 1,
              color: '#2c3e50',
              dashArray: '',
              fillOpacity: 0.3
            }
          }
        },
        // Official Leaflet callback - called automatically for each county polygon
        onEachFeature: (feature, layer) => {
          try {
            // Force apply custom styling immediately to ensure consistency
            if (layer && typeof layer.setStyle === 'function') {
              const customStyle = {
                fillColor: this.getCountyColor(feature),
                weight: 2,
                opacity: 1,
                color: '#2c3e50',
                dashArray: '',
                fillOpacity: 0.3
              }
              layer.setStyle(customStyle)
            }

            // Store the original style on the layer for consistent reset
            if (layer && feature) {
              layer._originalStyle = {
                fillColor: this.getCountyColor(feature),
                weight: 2,
                opacity: 1,
                color: '#2c3e50',
                dashArray: '',
                fillOpacity: 0.3
              }
            }

            // Check if layer has the required methods before adding event listeners
            if (layer && typeof layer.on === 'function') {
              layer.on({
                mouseover: this.highlightFeature,
                mouseout: this.resetHighlight,
                click: this.selectCounty
              });
            }

            // Add tooltip
            const name = this.getCountyDisplayName(feature.properties)
            if (name && layer && typeof layer.bindTooltip === 'function') {
              layer.bindTooltip(name, {
                permanent: false,
                direction: 'auto'
              });
            }
          } catch (error) {
            console.warn('Error setting up feature events:', error)
          }
        }
      }
    }
  },

  data() {
    return {
      // === MAP CONFIGURATION ===
      zoom: 7,
      center: [58.7, 25.3], // Geographic center of Estonia
      tileUrl: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      attribution: '© OpenStreetMap contributors',
      mapOptions: {
        zoomControl: true,
        scrollWheelZoom: true
      },

      // === COUNTY DATA ===
      countyData: null,        // Main GeoJSON FeatureCollection
      countyLabels: [],        // Array of label objects with name and center
      selectedCounty: null,    // Currently selected county feature
      lastUpdated: null,       // Timestamp of last successful data load
      clickCoordinates: null,  // [lat, lng] of the actual click location
      clickPin: null,          // Pin marker data for click location

      // === UI STATE ===
      loading: false,
      error: null,
      loadingMessage: 'Fetching county data...',
      showLabels: true,
      showCounties: true,
      saveResponseAsJson: false,

      // === API CONFIGURATION ===
      overpassUrl: 'https://overpass-api.de/api/interpreter',

      dependencies: '  "dependencies": {\n' +
          '    "@vue-leaflet/vue-leaflet": "^0.10.1",\n' +
          '    "leaflet": "^1.9.4",\n' +
          '    "osmtogeojson": "^3.0.0-beta.5"\n' +
          '  }',
      imports: '// leafleti css kujundused\n' +
          'import "leaflet/dist/leaflet.css";\n'

    }
  },

  mounted() {
    this.loadCountyData()
  },

  methods: {
    /**
     * Constructs Overpass API query for Estonian counties
     * Targets only administrative boundaries at level 6
     * @returns {string} Formatted Overpass QL query string
     */
    constructOverpassQuery() {
      return `
        [out:json][timeout:25];
        (
          relation["admin_level"="6"]["boundary"="administrative"]
            ["name"~"maa$|County|maakond"]
            (bbox:57.5,21.5,59.7,28.5);
        );
        out geom;
      `
    },

    /**
     * Main method to load county data from OpenStreetMap
     * Orchestrates the entire data fetching and processing pipeline
     */
    async loadCountyData() {
      this.loading = true
      this.error = null
      this.loadingMessage = 'Fetching county data from OpenStreetMap...'

      const overpassQuery = this.constructOverpassQuery()
      this.loadingMessage = 'Sending request to Overpass API...'

      axios.post(this.overpassUrl, overpassQuery, {
        headers: {
          'Content-Type': 'text/plain'
        },
        timeout: 30000 // 30 second timeout
      })
          .then(this.handleOverpassResponse)
          .catch(this.handleOverpassErrorResponse)
          .finally(() => {
            this.loading = false
          })
    },

    /**
     * Exports GeoJSON data as a downloadable file
     * Creates a blob and triggers automatic download
     * @param {Object} geoJsonData - The GeoJSON data to export
     */
    exportGeoJsonDataAsFile(geoJsonData) {
      const dataStr = JSON.stringify(geoJsonData, null, 2)
      const dataBlob = new Blob([dataStr], {type: 'application/json'})
      const link = document.createElement('a')
      link.href = URL.createObjectURL(dataBlob)
      link.download = 'geo-Json-Data.json'
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
    },

    /**
     * Validates initial GeoJSON data from API response
     * @param {Object} geoJsonData - Converted GeoJSON data
     * @throws {Error} If no features are found
     */
    validateGeoJsonData(geoJsonData) {

      if (!geoJsonData.features || geoJsonData.features.length === 0) {
        throw new Error('No administrative boundary data received from the API')
      }
    },

    /**
     * Filters GeoJSON features to identify Estonian counties
     * Uses multiple criteria: name patterns, administrative level, geometry type
     * @param {Object} geoJsonData - The complete GeoJSON dataset
     * @returns {Array} Array of features representing Estonian counties
     */
    filterEstonianCounties(geoJsonData) {
      const candidateFeatures = geoJsonData.features.filter(feature => {
        const props = feature.properties || {}


        // Exclude obvious non-Estonian features by ISO code
        if (props['ISO3166-2'] && (props['ISO3166-2'].startsWith('FI-') ||
            props['ISO3166-2'].startsWith('LV-') ||
            props['ISO3166-2'].startsWith('LT-'))) {
          return false
        }

        // Check for Estonian county name patterns
        const name = props.name || props['name:et'] || props['name:en'] || ''
        const isEstonianCounty = name.match(/maa$/i) || // ends with 'maa'
            name.match(/county$/i) || // ends with 'County'
            name.match(/maakond/i) || // contains 'maakond'
            ['Harjumaa', 'Hiiumaa', 'Ida-Virumaa', 'Jõgevamaa', 'Järvamaa',
              'Läänemaa', 'Lääne-Virumaa', 'Põlvamaa', 'Pärnumaa', 'Raplamaa',
              'Saaremaa', 'Tartumaa', 'Valgamaa', 'Viljandimaa', 'Võrumaa'].includes(name)

        // Check for valid administrative/geographic types
        const validType = props.type === 'multipolygon' ||
            props.boundary === 'administrative' ||
            props.place === 'island' ||
            props.place === 'region'

        // Ensure valid polygon geometry
        const validGeometry = feature.geometry &&
            (feature.geometry.type === 'Polygon' ||
                feature.geometry.type === 'MultiPolygon')

        const isCandidate = validGeometry && validType && isEstonianCounty


        return isCandidate
      })


      if (candidateFeatures.length === 0) {
        throw new Error(`No Estonian county candidates found. Total features received: ${geoJsonData.features.length}`)
      }

      return candidateFeatures
    },

    /**
     * Handles successful response from Overpass API
     * Converts OSM data to GeoJSON, filters counties, and updates component state
     * @param {Object} response - Axios response object from Overpass API
     */
    handleOverpassResponse(response) {
      this.loadingMessage = 'Converting OSM data to GeoJSON...'

      // Convert OSM data to GeoJSON format
      const geoJsonData = osmtogeojson(response.data)

      // Conditionally export GeoJSON data as file based on toggle state
      if (this.saveResponseAsJson) {
        this.exportGeoJsonDataAsFile(geoJsonData)
      }


      // Validate that we received usable data
      this.validateGeoJsonData(geoJsonData)

      // Filter for Estonian counties specifically
      const validFeatures = this.filterEstonianCounties(geoJsonData)

      // Update component state with filtered county data
      this.countyData = {
        type: "FeatureCollection",
        features: validFeatures
      }

      // Generate labels and finalize loading
      this.loadingMessage = 'Generating county labels...'
      this.generateCountyLabels()
      this.lastUpdated = new Date().toLocaleString()
    },

    /**
     * Handles errors from Overpass API requests
     * Provides user-friendly error messages based on error type
     * @param {Object} error - Axios error object
     */
    handleOverpassErrorResponse(error) {
      console.error('Error loading county data:', error)

      if (error.code === 'ECONNABORTED' || error.message.includes('timeout')) {
        this.error = 'Request timed out. The Overpass API might be busy. Please try again.'
      } else if (error.response && error.response.status === 429) {
        this.error = 'Too many requests. Please wait a moment and try again.'
      } else if (error.response && error.response.status >= 500) {
        this.error = 'Server error from Overpass API. Please try again later.'
      } else {
        this.error = error.message || 'Failed to load county data. Please try again.'
      }
    },

    /**
     * Builds a list of county labels for the map.
     *
     * Step by step:
     * 1. If there is no county data or no features inside it, stop and do nothing.
     * 2. Go through every feature (map):
     *    - Get the county's display name from its properties.
     *    - Get the county's center point from its geometry.
     *    - Return a simple object { name, center }.
     * 3. Keep only the objects that have both a name and a center (filter).
     *
     * The final result is stored in this.countyLabels as an array like:
     * [
     *   { name: "County A", center: [x, y] },
     *   { name: "County B", center: [x, y] }
     * ]
     */
    generateCountyLabels() {
      // 1. if invalid, exit early                       ↓↓↓
      if (!this.countyData || !this.countyData.features) return

      //  2. Go through every feature (map):
      this.countyLabels = this.countyData.features.map(feature => {

        // - Get the county's display name from its properties.
        // - Get the county's center point from its geometry.
        const name = this.getCountyDisplayName(feature.properties)
        const center = this.getFeatureCenter(feature.geometry)

        return {
          name: name,
          center: center
        }
        // 3. Keep only the objects that have both a name and a center (filter).
      }).filter(label => label.name && label.center)
      /** The final result is stored in this.countyLabels as an array like:
       *      [
       *        { name: "County A", center: [x, y] },
       *        { name: "County B", center: [x, y] }
       *      ]
       */

    },

    /**
     * Calculates the geometric center (centroid) of a polygon or multipolygon
     * For multipolygons, uses the largest polygon as the basis for calculation
     * @param {Object} geometry - GeoJSON geometry object (Polygon or MultiPolygon)
     * @returns {Array|null} [latitude, longitude] coordinates or null if calculation fails
     */
    getFeatureCenter(geometry) {
      if (!geometry || !geometry.coordinates) return null

      try {
        let coords = []

        if (geometry.type === 'Polygon') {
          // Use exterior ring coordinates for simple polygon
          coords = geometry.coordinates[0]
        } else if (geometry.type === 'MultiPolygon') {
          // Find the largest polygon within the multipolygon
          let largest = geometry.coordinates[0]
          let largestArea = 0

          geometry.coordinates.forEach(polygon => {
            const area = polygon[0].length // Simple approximation by coordinate count
            if (area > largestArea) {
              largestArea = area
              largest = polygon
            }
          })
          coords = largest[0] // Use exterior ring of largest polygon
        }

        if (coords.length === 0) return null

        // Calculate arithmetic centroid (average of all coordinates)
        let lats = 0, lngs = 0
        coords.forEach(coord => {
          lngs += coord[0] // longitude
          lats += coord[1] // latitude
        })

        return [lats / coords.length, lngs / coords.length]

      } catch (e) {
        console.warn('Error calculating feature center:', e)
        return null
      }
    },

    /**
     * Extracts the best available display name from county properties
     * Prioritizes Estonian names, then English names, then Default names, then fallbacks
     * @param {Object} properties - GeoJSON feature properties object
     * @returns {string} The display name for the county
     */
    getCountyDisplayName(properties) {
      if (!properties) return 'Unknown'

      // Try different name fields in order of preference
      return properties['name:et'] ||     // Estonian name (preferred)
          properties['name:en'] ||        // English name
          properties.name ||              // Default name
          properties.official_name ||     // Official name
          `County ${properties['@id'] || ''}` // Fallback with ID
    },

    /**
     * Generates a consistent color for each county based on its name
     * Uses a string hash to ensure same counties always get same colors
     * @param {Object} feature - GeoJSON feature object
     * @returns {string} Hex color code
     */
    getCountyColor(feature) {
      const colors = ['#3498db', '#e74c3c', '#2ecc71', '#f39c12', '#9b59b6', '#1abc9c', '#34495e']
      const name = this.getCountyDisplayName(feature.properties)

      // Create hash from county name for consistent color assignment
      const hash = name.split('').reduce((a, b) => {
        a = ((a << 5) - a) + b.charCodeAt(0)
        return a & a
      }, 0)

      return colors[Math.abs(hash) % colors.length]
    },

    // === LEAFLET EVENT HANDLERS ===

    /**
     * Handles mouse hover over county features
     * Applies highlight styling and brings feature to front
     * @param {Object} e - Leaflet mouse event
     */
    highlightFeature(e) {
      const layer = e.target
      
      // Check if layer has the required methods before calling them
      if (layer && typeof layer.setStyle === 'function') {
        layer.setStyle({
          weight: 4,
          color: '#e74c3c',
          dashArray: '',
          fillOpacity: 0.9
        })
        
        if (typeof layer.bringToFront === 'function') {
          layer.bringToFront()
        }
      }
    },

    /**
     * Handles mouse leave from county features
     * Resets styling to original state
     * @param {Object} e - Leaflet mouse event
     */
    resetHighlight(e) {
      const layer = e.target

      // Check if layer has the required methods and stored original style
      if (layer && typeof layer.setStyle === 'function' && layer._originalStyle) {
        try {
          layer.setStyle(layer._originalStyle)
        } catch (error) {
          console.warn('Error resetting layer style:', error)
        }
      }
    },

    /**
     * Handles click on county features
     * Updates selectedCounty state, captures coordinates, places pin, and zooms in
     * @param {Object} e - Leaflet mouse event
     */
    selectCounty(e) {
      try {
        if (e.target && e.target.feature) {
          this.selectedCounty = e.target.feature
        }
        
        // Capture the exact click coordinates from the Leaflet event
        if (e.latlng) {
          this.clickCoordinates = [e.latlng.lat, e.latlng.lng]
          
          // Place pin at click location
          this.placePin(e.latlng, e.target.feature)
          
          // Zoom to the clicked county
          this.zoomToCounty(e.target.feature)
        }
      } catch (error) {
        console.warn('Error selecting county:', error)
      }
    },

    // === PIN AND ZOOM HANDLERS ===

    /**
     * Places a pin marker at the clicked location
     * @param {Object} latlng - Leaflet LatLng object with lat and lng properties
     * @param {Object} feature - GeoJSON feature that was clicked
     */
    placePin(latlng, feature) {
      const countyName = this.getCountyDisplayName(feature.properties)
      
      
      this.clickPin = {
        coordinates: [latlng.lat, latlng.lng],
        countyName: countyName
      }
      
    },

    /**
     * Creates a custom red pin icon for click markers
     * @returns {Object} Leaflet DivIcon
     */
    createPinIcon() {
      return L.divIcon({
        html: `
          <div style="
            background-color: #e74c3c;
            width: 16px;
            height: 16px;
            border-radius: 50%;
            border: 3px solid white;
            box-shadow: 0 2px 6px rgba(0,0,0,0.3);
            position: relative;
          ">
            <div style="
              position: absolute;
              bottom: -8px;
              left: 50%;
              transform: translateX(-50%);
              width: 0;
              height: 0;
              border-left: 6px solid transparent;
              border-right: 6px solid transparent;
              border-top: 8px solid #e74c3c;
            "></div>
          </div>
        `,
        iconSize: [22, 30],
        iconAnchor: [11, 30],
        popupAnchor: [0, -30],
        className: 'custom-pin-marker'
      })
    },

    /**
     * Zooms the map to fit the clicked county bounds
     * @param {Object} feature - GeoJSON feature to zoom to
     */
    zoomToCounty(feature) {
      if (!feature || !feature.geometry) return

      try {
        if (this.$refs.mapRef && this.$refs.mapRef.leafletObject) {
          const map = this.$refs.mapRef.leafletObject
          
          // Create a temporary layer to get bounds
          const tempLayer = L.geoJSON(feature)
          const bounds = tempLayer.getBounds()
          
          // Zoom to the county with some padding
          map.fitBounds(bounds, {
            padding: [20, 20],
            maxZoom: 10 // Prevent zooming in too much for small counties
          })
        }
      } catch (error) {
        console.warn('Error zooming to county:', error)
      }
    },

    /**
     * Removes the click pin from the map
     */
    removePin() {
      this.clickPin = null
    },

    // === UI CONTROL HANDLERS ===

    /**
     * Resets map view to initial state
     * Clears selected county, click coordinates, pin and returns to default zoom/center
     */
    resetView() {
      // Clear selection, coordinates, and pin
      this.selectedCounty = null
      this.clickCoordinates = null
      this.clickPin = null
      
      // Reset map view using Leaflet map instance
      if (this.$refs.mapRef && this.$refs.mapRef.leafletObject) {
        const map = this.$refs.mapRef.leafletObject
        map.setView([58.7, 25.3], 7) // Set center and zoom directly on map
      } else {
        // Fallback to data properties if map ref is not available
        this.zoom = 7
        this.center = [58.7, 25.3]
      }
    },

    /**
     * Toggles visibility of permanent county labels
     */
    toggleLabels() {
      this.showLabels = !this.showLabels
    },

    /**
     * Toggles visibility of county polygons
     */
    toggleCounties() {
      this.showCounties = !this.showCounties
    },

    /**
     * Handles map click events when counties might be hidden
     * Uses point-in-polygon detection to identify which county was clicked
     * @param {Object} e - Leaflet click event
     */
    handleMapClick(e) {
      if (!e.latlng || !this.countyData || !this.countyData.features) return

      // Find which county contains the clicked point
      const clickedCounty = this.findCountyAtPoint(e.latlng)

      if (clickedCounty) {
        // Set selected county
        this.selectedCounty = clickedCounty

        // Capture click coordinates
        this.clickCoordinates = [e.latlng.lat, e.latlng.lng]

        // Place pin at click location
        this.placePin(e.latlng, clickedCounty)

        // Zoom to the clicked county
        this.zoomToCounty(clickedCounty)
      }
    },

    /**
     * Finds which county contains a given point using point-in-polygon detection
     * @param {Object} latlng - Leaflet LatLng object with lat and lng properties
     * @returns {Object|null} GeoJSON feature of the county containing the point, or null
     */
    findCountyAtPoint(latlng) {
      if (!this.countyData || !this.countyData.features) return null

      const point = [latlng.lng, latlng.lat] // GeoJSON uses [lng, lat] format

      for (const feature of this.countyData.features) {
        if (this.isPointInFeature(point, feature)) {
          return feature
        }
      }
      return null
    },

    /**
     * Checks if a point is inside a GeoJSON feature (polygon or multipolygon)
     * Uses ray casting algorithm for point-in-polygon detection
     * @param {Array} point - [longitude, latitude] coordinates
     * @param {Object} feature - GeoJSON feature with geometry
     * @returns {boolean} True if point is inside the feature
     */
    isPointInFeature(point, feature) {
      if (!feature || !feature.geometry) return false

      const geometry = feature.geometry

      if (geometry.type === 'Polygon') {
        return this.isPointInPolygon(point, geometry.coordinates[0])
      } else if (geometry.type === 'MultiPolygon') {
        // Check if point is in any of the polygons
        return geometry.coordinates.some(polygon =>
          this.isPointInPolygon(point, polygon[0])
        )
      }

      return false
    },

    /**
     * Ray casting algorithm to determine if a point is inside a polygon
     * @param {Array} point - [longitude, latitude] coordinates
     * @param {Array} polygon - Array of [longitude, latitude] coordinates forming the polygon
     * @returns {boolean} True if point is inside the polygon
     */
    isPointInPolygon(point, polygon) {
      const x = point[0], y = point[1]
      let inside = false

      for (let i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
        const xi = polygon[i][0], yi = polygon[i][1]
        const xj = polygon[j][0], yj = polygon[j][1]

        if (((yi > y) !== (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) {
          inside = !inside
        }
      }

      return inside
    }
  }
}
</script>

<style scoped>
/* Controls Panel Styling */
.controls-panel {
  padding: 20px;
  height: 100vh;
  overflow-y: auto;
  background-color: #f8f9fa;
  border-right: 1px solid #dee2e6;
}

/* Map Wrapper */
.map-wrapper {
  position: relative;
  height: 100vh;
  overflow: hidden;
}

/* Enhanced Button Styling */
.d-grid .btn {
  border-radius: 6px;
  font-weight: 500;
  transition: all 0.2s ease;
}

.d-grid .btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* Enhanced Card Styling */
.card {
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.2s ease;
}

.card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

/* Alert Styling */
.alert {
  border-radius: 8px;
  border: none;
}

.alert-info {
  background-color: #e7f3ff;
  color: #0c5460;
}

.alert-danger {
  background-color: #ffeaa7;
  color: #721c24;
}

/* Form Control Enhancement */
.form-control[readonly] {
  background-color: #f8f9fa;
  border-color: #dee2e6;
}

/* Coordinate Input Styling */
.form-control-sm[readonly] {
  background-color: #f1f3f4;
  font-family: 'Courier New', monospace;
  font-size: 0.8rem;
  text-align: center;
}

.form-label.small {
  font-size: 0.75rem;
  font-weight: 600;
  color: #6c757d;
  margin-bottom: 0.25rem;
}

/* Description List Styling */
.dl-horizontal dt {
  font-weight: 600;
  color: #495057;
}

.dl-horizontal dd {
  color: #6c757d;
}

/* Global styles for county labels */
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

/* Responsive Design */
@media (max-width: 992px) {
  .controls-panel {
    height: auto;
    max-height: 50vh;
    border-right: none;
    border-bottom: 1px solid #dee2e6;
  }

  .map-wrapper {
    height: 50vh;
  }
}

@media (max-width: 768px) {
  .controls-panel {
    padding: 15px;
    max-height: 60vh;
  }

  .map-wrapper {
    height: 40vh;
    min-height: 300px;
  }

  .d-grid .btn {
    padding: 10px;
    font-size: 0.9rem;
  }

  .card-body {
    padding: 1rem;
  }

  .alert {
    padding: 0.75rem;
  }
}

/* Custom Pin Icon and Popup Styling */
.custom-pin-marker {
  background: none !important;
  border: none !important;
  box-shadow: none !important;
}

.custom-pin-marker div {
  pointer-events: none;
}

.click-pin-popup {
  text-align: center;
  min-width: 180px;
}

.click-pin-popup h6 {
  color: #2c3e50;
  margin-bottom: 10px;
  border-bottom: 1px solid #dee2e6;
  padding-bottom: 5px;
}

.click-pin-popup p {
  font-size: 0.85rem;
  color: #6c757d;
}

/* Leaflet Popup Enhancements */
:deep(.leaflet-popup-content-wrapper) {
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

:deep(.leaflet-popup-tip) {
  box-shadow: none;
}

/* Custom Scrollbar for Controls Panel */
.controls-panel::-webkit-scrollbar {
  width: 6px;
}

.controls-panel::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.controls-panel::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

.controls-panel::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}
</style>