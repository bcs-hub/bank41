# src/views/extra — õppenäidete kaust (Rain'i integratsioonijuhend)

## Taust ja eesmärk

See fail on mõeldud **Rainile endale**, mitte üldiseks projektidokumentatsiooniks. Rain on BCS
Koolituse / Vali IT programmi lektor, kes õpetab iga õpilasgrupiga (lend, nt "grupp 41") full-stack
arendust (Java Spring Boot + Vue 3) ühise pangaäpi (bank) õppeprojekti näitel. Iga grupp saab oma
lennunumbriga repo ja arendab backendi ning frontendi kursuse jooksul samm-sammult.

Kursuse lõpuosas tutvustab Rain õpilastele Leaflet kaardi võimalusi. Need näited elavad
`src/views/extra/` kaustas ja on tehtud nii, et terve kaust on tervikuna kopeeritav ühest
õpilasgrupi projektist teise, minimaalse lisatööga.

**Rain'i töövoog:**
1. Uus grupp alustab, saab oma repo.
2. Kursuse lõpus kopeerib Rain käsitsi eelmise grupi `src/views/extra/` kausta uue grupi
   frontend-projekti samasse asukohta.
3. Rain suunab Claude Code'i sellele failile (`frontend/src/views/extra/CLAUDE.md`) ja ootab, et AI
   teeks **iseseisvalt ära kogu integratsiooni**, et näited oleksid kohe brauseris demokõlblikud.

Iga grupp on kursuse käigus jõudnud backend-arendusega erinevasse punkti — mõnel grupil on kõik
`extra/` näidete vajalikud REST endpointid juba olemas, mõnel puudub üks-kaks. Integratsioon
koosneb seetõttu kahest erineva iseloomuga etapist (vt allpool): mehaaniline osa on iga kord
identne, backend-sõltuvuste kontroll nõuab iga kord konkreetse projekti koodibaasi uurimist.

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
│       ├── LocationCard.vue          # asukoha kuvamiskaart (kaardi popup, kaartide list)
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
├── map-location/                    # uue ATM asukoha lisamise vorm (kaart + LocationForm)
├── map-atms/                        # ATM asukohtade kaart koos filtriga ja detailinfoga (pilt, automaatide arv)
└── timer/                           # taimer countdown näide
```

## Integratsiooni etapp 1 — mehaaniline (iga kord identne)

Need sammud ei sõltu grupi projekti hetkeseisust — tee need alati, kontrollimata koodibaasi eraldi.

### 1. Kopeeri kaust

Kopeeri terve `src/views/extra/` kaust uude projekti samasse asukohta (see teeb Rain ise, käsitsi,
enne AI sekkumist).

### 2. `src/router/index.js` — lisa import ja route'id

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

### 3. `package.json` — kontrolli sõltuvusi

```json
"dependencies": {
  "leaflet": "^1.9.4",
  "@vue-leaflet/vue-leaflet": "^0.10.1",
  "osmtogeojson": "^3.0.0-beta.5",
  "axios": "^1.x"
}
```

Installi puuduvad paketid: `npm install leaflet @vue-leaflet/vue-leaflet osmtogeojson`

**WSL2 tähelepanek:** kui `npm run dev` annab vea puuduva natiivmooduli kohta (nt
`@rolldown/binding-linux-x64-gnu`), tähendab see, et `node_modules` on installitud Windowsi Node'iga.
Käivita `npm install` uuesti WSL2 seest (Claude Code terminalist), et Linuxi natiivmoodulid tekiksid.

### 4. `src/main.js` — Leaflet CSS import

```js
import 'leaflet/dist/leaflet.css'
```

Ilma selleta on kaardid nähtavad, kuid visuaalselt katkised (puuduvad ikoonid, valed proportsioonid).

### 5. `src/App.vue` — navigeerimislink

Lisa navbar'i link Extra vaatele:

```html
<RouterLink class="nav-link" to="/extras">Extra asjad</RouterLink>
```

Ilma selleta on Extra vaade siiski kättesaadav otse URL-ilt `/extras`.

## Integratsiooni etapp 2 — backend-sõltuvuste kontroll (grupiti erinev, nõuab uurimist)

See on iga integratsiooni juures uus töö, kuna grupid on backend-arendusega erineval tasemel.

### Muster

1. Ava iga fail `src/views/extra/_services/*.js` ja iga näite enda `services/*.js` fail (nt
   `map-atms/services/AtmsMapLocationService.js`) ja loetle kõik `axios` kutsed — need on
   `extra/` kausta REST-sõltuvused (URL, HTTP meetod, tagastatav kuju).
2. Kontrolli grupi backend-projektist (`backend/src/main/java/ee/bcs/bank/controller/...`), kas iga
   endpoint on juba olemas ja tagastab eeldatud kujuga andmed (sh kõik väljad, mida `extra/`
   komponendid kasutavad, nt pildid, automaatide arv).
3. Kui endpoint puudub või tagastab liiga vähe infot, loo/täienda see **backend/CLAUDE.md**
   konventsioone järgides: taaskasuta olemasolevaid `Controller`/`Service`/`Repository`/`Mapper`
   meetodeid ja mustreid, ära dubleeri loogikat. Kontrolli ka `docs/structure/backend-projekti-struktuur.md`.
4. Kui frontend-teenuse fail viitab valele/olematule URL-ile või valele teisele teenuse meetodile
   (nt kopeerimisviga, kus üks helper kutsub kogemata vana endpointi uue asemel), paranda see.

### Näide (2026. a sügis, `map-atms`)

`map-atms` näide eeldas `LocationService.js`-is meetodit `sendGetAtmLocationsDetail(cityId)`, mis
kutsub `/api/atm/locations/details` ja tagastab lisaks tavapärastele asukoha väljadele ka `cityId`,
`numberOfAtms` ja `imageData`. Grupi backend'is oli olemas ainult kergem `/api/atm/locations`
(`LocationInfo`, ilma pildita). Lahendus: lisati uus DTO `AtmLocationDetailDto`, uus
`LocationController` endpoint `GET /api/atm/locations/details` ning `LocationService`-sse uus
meetod `findAtmLocationDetails(cityId)`, mis taaskasutas olemasolevaid privaatseid meetodeid
(`findFilteredLocationsBy`, `validateAtLeastOneLocationExists`, `createTransactionTypeDtos`) ja
lisas ainult uue bulk-pildipäringu (`LocationImageRepository.findByLocationIn`), et vältida N+1
probleemi.

### Pärast koodimuudatusi

Käivita backend (`./gradlew bootRun`) ja frontend (`npm run dev`) ning kontrolli reaalse päringuga
(curl või brauser), et uued/muudetud endpointid tagastavad oodatud andmed enne, kui teatad
integratsiooni valmis olevaks.

## Järelejäänud väline sõltuvus

`_services/NavigationService.js` impordib `@/router/index.js` — see on vältimatu, kuna Vue Router on
projekti tasemel. Muuda vajadusel marsruutide nimesid selles failis.

## Keel

Selle faili (`CLAUDE.md`) sisu peab alati olema eestikeelne.
