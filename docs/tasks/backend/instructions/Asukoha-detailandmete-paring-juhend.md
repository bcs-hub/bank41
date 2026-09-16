# Juhend: GET /api/atm/locations/{locationId}

**Taski fail:** `Asukoha-detailandmete-paring.md`
**Kontroller:** `LocationController.java`
**Implementeerimise voog:** RestController → Service → Repository → Service → Mapper → RestController

---

## Sissejuhatus

Selle endpointi eesmärk on tagastada ühe konkreetse pangaautomaadi asukoha kõik detailandmed — asukoha muutmisvormi eeltäitmiseks. Päring käib läbi kõiki nelja kihti: kontroller võtab vastu `locationId` path variable'i, service kogub kokku asukoha põhiandmed, pildi (kui on) ja kõik süsteemi tehingutüübid koos nende saadavuse infoga selles asukohas, mapper teisendab tulemuse DTO-ks. Selle harjutuse käigus harjutad olemasoleva kontrolleri/service klassi laiendamist (mitte uue loomist), mitme allika andmete kokkukogumist ühte DTO-sse, ja projekti `getValid<Entiteet>By` veakäsitlusmustri kasutamist.

**Oluline:** `LocationController` ja `LocationService` on juba olemas — sa **lisad uue meetodi**, mitte ei loo uut klassi.

---

## Samm 1 — RestController

### Mida teha?

`LocationController.java` juba eksisteerib (`backend/src/main/java/ee/bcs/bank/controller/location/`) — ava see fail ja lisa sinna uus meetod, ülejäänud kahe olemasoleva `@GetMapping` meetodi kõrvale.

Alusta meetodist **ilma mappingannotatsioonideta** — see aitab kõigepealt loogika paika saada:

```java
public void meetodiNimi(SisendTüüp parameetriNimi) {
    // tühi meetod esialgu
}
```

> **Mõtle:** Mis on selle meetodi hea nimi? Vaata, kuidas olemasolevad meetodid `LocationController`-is nimetatud on (`findAtmLocations`, `findAtmLocationsV2`) — see endpoint tagastab **ühe** asukoha detailandmed, mitte listi. Nimi peaks seda peegeldama.

Seejärel lisa:
1. **Mappingannotatsioon** — `@GetMapping`, tee on `/atm/locations/{locationId}`
2. **Parameetrite annotatsioon** — `@PathVariable` `locationId` jaoks (Integer)
3. **Swagger annotatsioonid** — `@Operation` ja `@ApiResponses` — vaata taskifailist "Veaolukorrad" tabelit 404 ja 500 kirjelduste jaoks; vaata ka `addLocation` meetodit samas failis, kuidas `ApiError` schema `@Content`-is kasutatakse

### Service klassi ettevalmistus

`LocationService` on samuti juba olemas (`backend/src/main/java/ee/bcs/bank/service/LocationService.java`). Lisa kontrolleri klassi (kui pole juba olemas) service muutuja — vaata, kas see on juba klassiväljana olemas, kuna teised meetodid kasutavad seda.

Kutsu service meetodit välja (esialgne tühi väljakutse):

```java
public void meetodiNimi(Integer locationId) {
    locationService.meetodiNimi(locationId);
}
```

> **IntelliJ vihje:** Kui `locationService.meetodiNimi(...)` on punasega alla joonitud,
> vajuta **Alt+Enter** punasel joonel → vali **"Create method in LocationService"**.
> IntelliJ loob automaatselt vastava meetodi service klassi!

---

## Samm 2 — Service

### Mida teha?

Ava `LocationService.java` ja mine äsja loodud meetodisse. Sisse tuleb üks väärtus (`locationId`) ja väljundiks peab lõpuks olema `AtmLocationDetailDto` — aga selleni jõuad mitme vahepealse sammu kaudu, kuna andmed tulevad mitmest tabelist (`location`, `location_image`, `transaction_type`, `location_transaction_type`).

### Repository ühenduse loomine — asukoha leidmine

Kõigepealt on vaja leida asukoht `locationId` järgi. Vaata `backend/CLAUDE.md`-st **"Entiteedi otsing ID järgi"** konventsiooni — kuidas `CityService.getValidCity` seda teeb `findById()` + `orElseThrow`-ga. Sinu jaoks kehtib sama muster, aga `Location`-i jaoks: meetod peaks kandma nime `getValidLocationBy(Integer locationId)` ja kasutama `PrimaryKeyNotFoundException`-it (vaata, kuidas `CityService` seda viskab — sama kaks-parameetriga konstruktorit).

> **Mõtle:** Kas selline meetod juba `LocationService`-s eksisteerib? Taskifail viitab "analoogsele meetodile" — otsi enne, kui loo uus.

`LocationRepository` juba laiendab `JpaRepository<Location, Integer>`-t, nii et `findById()` on juba olemas — sulle ei lähe uut repository meetodit siin vaja.

---

## Samm 3 — Repository (tehingutüüpide saadavus)

### Mida teha?

Taskifail nõuab, et `transactionTypes` massiiv sisaldaks **kõiki** süsteemi tehingutüüpe, igaühe juures `isAvailable` väärtus vastavalt sellele, kas `location_transaction_type` tabelis on kirje selle asukoha+tehingutüübi kombinatsiooni jaoks.

Vaata `LocationService`-s juba olemasolevat `createTransactionTypeDtos(Integer locationId)` meetodit (kasutatakse `findAtmLocations` voos) — see teeb täpselt sama asja, mida sina praegu vajad. Kas saad seda taaskasutada, või on tarvis kohandada?

`TransactionTypeRepository.findAll()` ja `LocationTransactionTypeRepository.locationTransactionTypeExistsBy(locationId, transactionTypeId)` on mõlemad juba olemas — vaata neid `persistence/transactiontype/` ja `persistence/locationtransasctiontype/` kaustadest.

> **Rusikareegel:** Kui sarnane loogika juba service klassis eksisteerib, ei loo uut — taaskasuta või ekstrakteeri jagatud helper meetodiks.

### Repository ühenduse loomine — pilt

Taskifail kirjeldab ka `imageData` käitumist: kui asukohal on `location_image` kirje, tagastatakse selle sisu, muidu `null`. Vaata `LocationImageRepository`-t (`persistence/locationimage/`) — millist meetodit saad kasutada, et leida asukoha pilt (kui on)?

> **Mõtle:** Kuna pilt on valikuline (1:0..1 seos), mis tagastustüüp repository meetodile sobib — `Optional<LocationImage>` või midagi muud?

---

## Samm 4 — tagasi Service'i (väljundi teisendamine)

### Mida teha?

Nüüd on käes kõik osad: `Location` entiteet, tehingutüüpide DTO-de list koos `isAvailable` väärtustega, ja valikuline pilt. Need tuleb kokku panna üheks `AtmLocationDetailDto`-ks.

### DTO klass

Mõtle: kas `AtmLocationDetailDto` juba olemas on?
- Vaata kaustast: `backend/src/main/java/ee/bcs/bank/controller/location/dto/`

**Kui DTO puudub** → loo see. Vaata taskifaili JSON näidist väljade jaoks: `locationId`, `cityId`, `locationName`, `numberOfAtms`, `imageData`, `lng`, `lat`, `transactionTypes` (List<TransactionTypeDto>). Võid kasutada JPA Buddy abi (paremklõps `Location` entity klassil → New → DTO) või luua käsitsi — vaata olemasolevat `LocationInfo` klassi eeskujuks, kuna see on struktuurilt sarnane.

### Mapper

Mõtle: kas olemasolev `LocationMapper` sobib laiendamiseks, või on parem eraldi meetod? `imageData` teisendamiseks vaata taskifailis mainitud `StringBytesConverter.bytesToString` — see on juba kasutuses `LocationImageMapper`-is vastupidises suunas.

> **Tähelepanu `backend/CLAUDE.md` konventsioonile:** Kuna DTO pannakse kokku mitmest allikast (entity + eraldi päritud transactionTypes list + valikuline pilt), ei pruugi see olla puhas ühe-entity-`@Mapping` teisendus. Kaalu, kas osa kokkupanekust kuulub service meetodisse (`handle`-prefiksiga abimeetodisse, kuna see muteerib/täidab DTO-d tingimuslikult), mitte mapperisse.

### Service meetodi lõpetamine

Kutsu mapper(id) välja service meetodis ja kombineeri tulemus lõplikuks `AtmLocationDetailDto`-ks.

> **IntelliJ vihje:** Kui meetodi tagastustüüp on veel `void`, aga sa juba kirjutad `return`-lauset, vajuta **Alt+Enter** punase joone peal — IntelliJ parandab tagastustüübi automaatselt!

---

## Samm 5 — tagasi RestController'isse

### Mida teha?

Service meetod on nüüd valmis ja tagastab `AtmLocationDetailDto`. Täienda kontrolleri meetodit — lisa `return` lause ja muuda tagastustüüp `void`-ilt `AtmLocationDetailDto`-le.

```java
public AtmLocationDetailDto meetodiNimi(@PathVariable Integer locationId) {
    return locationService.meetodiNimi(locationId);
}
```

---

## Samm 6 — kood ilusaks (refactor)

### Make it work → Make it beautiful

Kui kood töötab, vaata service meetod üle:
- Kas peameetod on lühike ja loetav — kutsub selgelt nimetatud helper meetodeid?
- Kas iga helper meetod võtab parameetriks ainult seda, mida tegelikult vajab (mitte kogu objekti, kui üks väli piisaks)?
- Kas meetodite nimetamine järgib `backend/CLAUDE.md` konventsiooni — `get...` kindla tagastuse jaoks, `handle...` tingimusliku/muteeriva loogika jaoks?

**Extract Method IntelliJ'ga:** Märgi service meetodis koodilõik, mida soovid eraldada helper meetodiks → paremklõps → Refactor → Extract Method.

### Meetodite järjekord

Kontrolli meetodite järjekorda: `public` meetodid enne, `private` pärast, väljakutsumise hierarhia järgi (peameetod üleval, helper meetodid all) — vaata, kuidas `LocationService`-s see juba on tehtud, ja järgi sama mustrit.

---

## Kokkuvõte ja kontrollnimekiri

Enne kui pead koodi valmis, kontrolli läbi:

- [ ] `LocationController`-isse on lisatud uus `@GetMapping("/atm/locations/{locationId}")` meetod
- [ ] Meetodil on `@Operation` ja `@ApiResponses` (200, 404, 500) annotatsioonid
- [ ] `LocationService`-sse on lisatud vastav meetod, mis kasutab `getValidLocationBy` mustrit (`findById` + `orElseThrow` + `PrimaryKeyNotFoundException`)
- [ ] Kõik neli andmeallikat on kaetud: `location` (põhiandmed), `city` (cityId), `location_image` (valikuline pilt), `transaction_type` + `location_transaction_type` (kõik tüübid + saadavus)
- [ ] `AtmLocationDetailDto` sisaldab kõiki taskifailis nõutud välju
- [ ] `imageData` on `null`, kui pilti pole, muidu string kujul sisu
- [ ] 404 juhtum viskab `PrimaryKeyNotFoundException`-i õige `errorCode`-iga (`PRIMARY_KEY_NOT_FOUND`)
- [ ] Meetodite järjekord service klassis: `public` enne, `private` pärast, väljakutsumise hierarhia järgi
- [ ] Kood kompileerub ja Swagger UI kaudu on endpoint nähtav
- [ ] Automaattestid on kirjutatud: õnnestunud päring (koos `isAvailable` õigsuse kontrolliga) ja 404 juhtum

---

> **Järgmine samm:** Testi endpointi Swagger UI kaudu (`http://localhost:8080/swagger-ui/index.html`)
> ja kontrolli, et vastus vastab taskifailist leitud näidisandmetele (`locationId=1`, "Sikupilli Prisma").
