# Vue ülesande lahendamise flow

See dokument kirjeldab mõtlemisprotsessi — kuidas läheneda uue väljaga/komponendiga
seotud ülesandele selles projektis. Komponendi enda sisemise ülesehituse kohta vaata
[vue-komponendi-struktuur.md](./vue-komponendi-struktuur.md).

---

## 1. Kust andmed tulevad?

Kõigepealt otsusta: kas komponent vajab **backendist andmeid** (GET/POST) või on tegu
puhtalt kohaliku olekuga?

- Kui backend — loo `src/services/*.js` fail: `axios.get('/api/...')`, eksporditakse
  `export default { meetodiNimi() {...} }` mustris.
  Näited: `CityService.js`, `TransactionTypeService.js`.
- Kui backendist laadimist pole vaja (nt lihtne tekstiväli) — teenust pole vaja,
  ainult local state/props.

---

## 2. Otsusta kihistus (kolm rolli)

Projektis on läbiv kolmekihiline muster:

```
View (nt LocationView.vue)        — omab olekut (data), teeb API päringud
   ↓ props (cities, location)
Form (nt LocationForm.vue)        — "torujuhe": kogub väiksed komponendid kokku,
                                     edastab props alla, re-emit'ib sündmused üles
   ↓ props (cities, cityId / transactionTypes)
Väike komponent (nt CitiesDropdown.vue, TransactionTypesCheckbox.vue)
                                   — "loll" esituskomponent
```

Küsi endalt: **kes peaks andmeid omama ja kes ainult kuvama?** Reeglina omab andmeid
kõige ülemine view, sest tema teeb API päringud ja vajab kogu objekti (nt `location`)
kokkupanekuks.

---

## 3. Kavanda üks komponent

Iga uus väike komponent (nagu `LocationNameInput.vue`, `NumberOfAtmsInput.vue`) läbib
sama malli:

1. **`props`** — mida ta väljastpoolt vajab (nt `transactionTypes: Array`, `cityId: Number`)
2. **`emits`** — mida ta saadab üles, nimega `event-new-<väli>-input`
   (konsistentne nimemuster kogu projektis)
3. **`data()`** — ainult siis, kui komponent ise midagi haldab
4. **`methods`** — handlerid, mis loevad `$event`-i ja emit'ivad edasi
5. **`template`** — kui nimekiri, siis `v-for` + `:key`; kui väli võib muutuda,
   otsusta `v-model` (kahesuunaline) vs `:value`/`:checked` + käsitsi `@input`/`@change`
   (ühesuunaline, sina kontrollid ise, millal ja kuidas väärtus uueneb — nt kui väli
   peab olema ainult kuvamiseks, lisa `disabled` ja jäta muutmisloogika ära)

---

## 4. Ühenda kihid kokku

- Väike komponent emit'ib → vahekiht (`LocationForm`) kuulab
  `@event-new-x-input="$emit('event-new-x-input', $event)"` ja **saadab sama sündmuse
  lihtsalt edasi** (ei tee ise midagi muud, kui pole vaja)
- Tippview (`LocationView`) kuulab lõpuks `@event-new-x-input="location.x = $event"`
  ja uuendab oma olekut

See "kolme kihi läbiv re-emit" on projekti kõige läbivam muster.

---

## 5. Algandmete laadimine

Kui view vajab käivitudes andmeid (nt linnad või tehingutüübid):

- Meetod `getX()`, mis teeb päringu ja `.then()`-is kutsub `handleGetXResponse(response)`,
  mis paneb `this.x = response.data`
- Vearada: `.catch(() => NavigationService.navigateToErrorView())`
- Kutse `beforeMount()` sees (mitte `mounted`)

---

## 6. Küsimused, mida endalt küsida enne kirjutamist

1. Kas see väli/komponent vajab backendist andmeid? → teenus
2. Kas see komponent on korduvkasutatav mujal? → eraldi fail, mitte inline
3. Kas kasutaja saab seda muuta või on see ainult kuvamiseks? →
   `v-model` vs `:value`/`:checked` + `disabled`
4. Kes omab "tõe allikat" (source of truth)? Tavaliselt kõige ülemine view.
5. Kas sündmuse nimi järgib mustrit `event-new-<väli>-input`? Hoia järjekindlust.
