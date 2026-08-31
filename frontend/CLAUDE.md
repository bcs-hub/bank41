# CLAUDE.md

See fail annab juhiseid Claude Code'ile (claude.ai/code) selle repositooriumi koodiga töötamisel.

## Käsud

```sh
npm install        # Installi sõltuvused
npm run dev        # Arendusserver aadressil http://localhost:8081
npm run build      # Tootmisbuild
npm run lint       # Käivita oxlint ja eslint (mõlemad --fix lipuga)
npm run format     # Prettieri formaatimine src/ kaustas
```

## Arhitektuur

See on Vue 3 + Vite frontend pangaäpile (Vali-IT grupiprojekt).

**Stack:** Vue 3 (Composition API), Vue Router 5, Pinia, Bootstrap 5, Axios, Phosphor Icons

**Sisenemispunkt:** `index.html` on hetkel Vue app kommenteeritud välja ja näitab lihtsat HTML õppeharjutust. Vue app (`src/main.js` → `src/App.vue`) käivitatakse eraldi, kuid pole veel `index.html`-ga ühendatud.

**API proksi:** Vite suunab `/api` päringud Stoplight mock-serverisse (`https://stoplight.io/mocks/valiit/myproject/1968960041`). Kohalik backendivahetaja (`http://localhost:8080`) on `vite.config.js`-is kommenteeritud välja.

**Globaalne axios:** Axios on registreeritud `app.config.globalProperties.$axios`-na — komponentides kasuta `this.$axios` (Options API) või inject via `getCurrentInstance` (Composition API).

**Olekuhaldus:** Pinia store'id asuvad `src/stores/` kaustas. Hetkel on olemas ainult näidis `counter` store.

**Marsruutimine:** Marsruudid on defineeritud `src/router/index.js`-is. `/about` laaditakse laisklaadimisega.

**Tee alias:** `@` viitab `src/` kaustale.

## Koodistiil

Prettieri seadistus: ilma semikooloniteta, ülakomad, 100-märgiline reavaheline laius. ESLint käivitab esmalt oxlinti, seejärel eslint-plugin-vue (olulised reeglid), Prettieri formaatimine on ESLintist välja jäetud.

## Keel

Selle faili (`CLAUDE.md`) sisu peab alati olema eestikeelne.

## Dokumentatsioon

Kogu dokumentatsioon asub `docs/` kausta alakaustades. Kõigi dokumentatsioonifailide sisu peab alati olema eestikeelne.
