import ExtraView from './ExtraView.vue'
import MapAddPin from './map-simple/MapAddPin.vue'
import MapView from './map-simple/MapView.vue'
import MapApiCountyView from './map-api-county/MapApiCountyView.vue'
import MapJsonCountyView from './map-json-county/MapJsonCountyView.vue'
import MapJsonCountyPlainView from './map-json-county/MapJsonCountyPlainView.vue'
import MapLocationView from './map-location/MapLocationView.vue'
import MapAtmsView from './map-atms/MapAtmsView.vue'
import TimerCountdownView from './timer/TimerCountdownView.vue'

export const extraRoutes = [
  {
    path: '/extras',
    name: 'extrasRoute',
    component: ExtraView,
  },
  // Basic kaardid
  {
    path: '/map-add-pin',
    name: 'mapAddPinRoute',
    component: MapAddPin,
  },
  {
    path: '/map-simple',
    name: 'mapSimpleRoute',
    component: MapView,
  },
  // Maakonnaga kaardid
  {
    path: '/map-api-county',
    name: 'mapApiCountyRoute',
    component: MapApiCountyView,
  },
  {
    path: '/map-json-county',
    name: 'mapJsonCountyRoute',
    component: MapJsonCountyView,
  },
  {
    path: '/map-json-county-plain',
    name: 'mapJsonCountyPlainRoute',
    component: MapJsonCountyPlainView,
  },
  // ATM asukoha kaardid
  {
    path: '/map-location',
    name: 'mapLocationRoute',
    component: MapLocationView,
  },
  {
    path: '/map-atms',
    name: 'mapAtmsRoute',
    component: MapAtmsView,
  },
  // Muud
  {
    path: '/timer',
    name: 'mapTimerRoute',
    component: TimerCountdownView,
  },
]
