<template>
  <div class="container text-center">
    <div class="row mb-3">
      <h1>Vaata kaarti</h1>
    </div>
    <div class="row">
      <div class="col">
        <div style="height: 75vh; width: 50vw">
          <l-map v-model:zoom="zoom" :center="[58.7, 25.3]" @move="log('move')">
            <l-tile-layer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />

            <!-- todo: pin, that can be dragged
              Leaflet fires three “drag”-related events on a draggable marker:
                • movestart when you first grab it
                • move continuously while you drag
                • moveend once you drop it

              In this case, @moveend will fire when you finish dragging the marker,
              passing the Leaflet event object as the first parameter to `updateDraggableMarker`,
              which then captures and stores its new coordinates.
            -->
            <l-marker
              :lat-lng="[draggableMarker.latitude, draggableMarker.longitude]"
              draggable
              @moveend="updateDraggableMarker"
            >
              <l-tooltip>Drag me!</l-tooltip>
            </l-marker>

            <!-- todo: Custom Vue icon marker
              This marker uses a custom image icon via <l-icon>.
              - `:icon-url` points to your `Png` asset.
              - `:icon-size` controls its displayed width/height.
              No events are bound here—this is a static, styled pin.
            -->
            <l-marker :lat-lng="[customIconMarker.latitude, customIconMarker.longitude]" draggable>
              <l-tooltip>Custom Icon variant. You can add custom image for pin</l-tooltip>
              <l-icon
                :icon-url="customIconMarker.iconPngURl"
                :icon-size="[customIconMarker.iconWidth, customIconMarker.iconHeight]"
              />
            </l-marker>

            <!-- pin with popup on click
              Leaflet emits a `click` event on markers by default.
              Vue-Leaflet re-emits this so you can open popups or run methods.
              Here, clicking the pin opens the <l-popup> and renders your
              <LocationCard> component, passing in `locationInfo`.
            -->
            <l-marker :lat-lng="[locationInfo.latitude, locationInfo.longitude]">
              <l-tooltip> Clickable variant. You can click on this pin </l-tooltip>
              <l-popup :options="{ minWidth: 200 }">
                <LocationCard :location="locationInfo" />
              </l-popup>
            </l-marker>

            <!--  Joon: Hiiumaa, Saaremaa, Pärnu    -->
            <l-polyline
              :lat-lngs="[
                [58.948, 22.591],
                [58.254, 22.489],
                [58.386, 24.495],
              ]"
              color="green"
            ></l-polyline>

            <!--   Large Octagon shape near Tartu -->
            <l-polygon
              :lat-lngs="[
                [58.408, 26.731],
                [58.398, 26.761],
                [58.368, 26.771],
                [58.338, 26.761],
                [58.328, 26.731],
                [58.338, 26.701],
                [58.368, 26.691],
                [58.398, 26.701],
              ]"
              color="#41b782"
              :fill="true"
              :fillOpacity="0.1"
              fillColor="#41b982"
            />

            <!--   Large rectangle shape near Viljandi -->
            <l-rectangle
              :lat-lngs="[
                [58.334, 25.509],
                [58.343, 25.728],
                [58.241, 25.89],
                [58.235, 25.658],
              ]"
              :fill="true"
              color="#35495d"
            />
          </l-map>
        </div>
      </div>
      <div class="col">
        <h3>lisa sõltuvused (package.json)</h3>
        <textarea v-model="dependencies" rows="4" cols="40" />
        <h3>lisa CSS import (main.js)</h3>
        <textarea v-model="imports" rows="4" cols="40" />
      </div>
    </div>
  </div>
</template>
<script>
import {
  LControlLayers,
  LIcon,
  LMap,
  LMarker,
  LPolygon,
  LPolyline,
  LPopup,
  LRectangle,
  LTileLayer,
  LTooltip,
} from '@vue-leaflet/vue-leaflet'

import logoUrl from '../_assets/logo.png'
import AtmImage from '../_components/image/AtmImage.vue'
import ImageInput from '../_components/image/ImageInput.vue'
import LocationCard from '@/views/extra/_components/location/LocationCard.vue'

export default {
  components: {
    LocationCard,
    ImageInput,
    AtmImage,
    LMap,
    LIcon,
    LTileLayer,
    LMarker,
    LControlLayers,
    LTooltip,
    LPopup,
    LPolyline,
    LPolygon,
    LRectangle,
  },
  data() {
    return {
      iconPngURl: logoUrl,
      zoom: 7,
      iconWidth: 25,
      iconHeight: 35,

      draggableMarker: {
        latitude: 59.437,
        longitude: 24.754,
      },

      customIconMarker: {
        latitude: 58.945,
        longitude: 24.794,
        iconWidth: 25,
        iconHeight: 35,
        iconPngURl: logoUrl,
      },

      locationInfo: {
        locationName: 'Paide Maksimarket',
        latitude: 58.885,
        longitude: 25.557,
        numberOfAtms: 1,
        imageData: '',
        transactionTypes: [
          {
            transactionTypeId: 1,
            transactionTypeName: 'Sularaha sisse',
            isAvailable: true,
          },
          {
            transactionTypeId: 2,
            transactionTypeName: 'Sularaha välja',
            isAvailable: true,
          },
          {
            transactionTypeId: 3,
            transactionTypeName: 'Maksed',
            isAvailable: false,
          },
        ],
      },

      dependencies:
        '  "dependencies": {\n' +
        '    "@vue-leaflet/vue-leaflet": "^0.10.1",\n' +
        '    "leaflet": "^1.9.4"\n' +
        '  }',
      imports: '// leafleti css kujundused\n' + 'import "leaflet/dist/leaflet.css";\n',
    }
  },
  methods: {
    updateDraggableMarker(event) {
      // `event` is the Leaflet event object
      const { lat, lng } = event.target.getLatLng()
      this.draggableMarker.latitude = lat
      this.draggableMarker.longitude = lng
      console.log(`Draggable pin moved to: ${lat}, ${lng}`)
    },

    log(a) {
      console.log(a)
    },
  },
}
</script>
