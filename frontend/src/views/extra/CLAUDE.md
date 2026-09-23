# src/views/extra — õppematerjali kaust

See kaust on mõeldud õppenäidetena ja on disainitud nii, et seda saab tervikuna kopeerida ühest Vue 3 projektist teise minimaalse lisakonfiguratsiooniga.

## Kausta struktuur

```
src/views/extra/
├── CLAUDE.md                        # see fail
├── extra.routes.js                  # kõik extra marsruudid ühes kohas
├── ExtraView.vue                    # navigeerimisleht kõigi näidete vahel
│
├── _assets/                         # kohalikud pildid (ei sõltu src/assets/)
│   ├── logo.png
│   └── images/
│       └── atm.png
│
├── _components/                     # kohalikud komponendid (ei sõltu src/components/)
│   ├── CitiesDropdown.vue
│   ├── image/
│   │   ├── AtmImage.vue
│   │   └── ImageInput.vue
│   └── location/
│       ├── LocationForm.vue
│       ├── LocationNameInput.vue
│       ├── NumberOfAtmsInput.vue
│       └── TransactionTypesCheckbox.vue
│
├── _services/                       # kohalikud teenused (ei sõltu src/api-services/ ega src/navigation/)
│   ├── CityService.js
│   ├── LocationService.js
│   ├── TransactionTypeService.js
│   └── NavigationService.js
│
├── map-simple/                      # lihtne Leaflet kaardi näide
├── map-api-county/                  # maakonnad Overpass API kaudu
├── map-json-county/                 # maakonnad lokaalse JSON failiga
├── map-add-location/                # uue asukoha lisamine kaardil
├── map-atms/                        # ATM asukohtade kaart koos filtriga
└── timer/                           # taimer countdown näide
```

## Uude projekti kopeerimine

### 1. Kopeeri kaust

Kopeeri terve `src/views/extra/` kaust uude projekti samasse asukohta.

### 2. `src/router/index.js` — lisa üks import ja üks rida

```js
import { extraRoutes } from '@/views/extra/extra.routes.js'

const router = createRouter({
  routes: [
    // ... projekti enda marsruudid ...
    ...extraRoutes,
  ],
})
```

`_services/NavigationService.js` viitab marsruutide nimedele `errorRoute` ja `notAuthorizedRoute`.
Kui uues projektis on need nimed erinevad, muuda need `_services/NavigationService.js` failis ära.

### 3. `package.json` — kontrolli, et sõltuvused on olemas

Extra kaust vajab järgmisi npm pakette:

```json
"dependencies": {
  "leaflet": "^1.9.4",
  "@vue-leaflet/vue-leaflet": "^0.10.1",
  "osmtogeojson": "^3.0.0-beta.5",
  "axios": "^1.x"
}
```

Installi puuduvad paketid:

```sh
npm install leaflet @vue-leaflet/vue-leaflet osmtogeojson
```

### 4. `src/main.js` — lisa Leaflet CSS import

```js
import 'leaflet/dist/leaflet.css'
```

Ilma selleta on kaardid nähtavad, kuid visuaalselt katkised (puuduvad ikoonid, valed proportsioonid).

### 5. `src/App.vue` — lisa navigeerimislink (valikuline)

Lisa navbar'i link Extra vaatele, kui soovid sellele ligi pääseda navigatsioonist:

```html
<RouterLink class="nav-link" to="/extras">Extra asjad</RouterLink>
```

Ilma selleta on Extra vaade siiski kättesaadav otse URL-ilt `/extras`.

## Järelejäänud väline sõltuvus

`_services/NavigationService.js` impordib `@/router/index.js` — see on vältimatu, kuna Vue Router on projekti tasemel. Muuda vajadusel marsruutide nimesid selles failis.

## Keel

Selle faili (`CLAUDE.md`) sisu peab alati olema eestikeelne.
