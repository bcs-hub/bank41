# Tehingutüüpide info — implementatsiooniplaan

**Taski fail:** [`Tehingutüüpide-info.md`](./Tehingutüüpide-info.md)
**Teenus:** `GET /api/atm/transaction-types`

## Kokkuvõte

Suurem osa vajalikust loogikast on koodis juba olemas (`TransactionType` entiteet, `TransactionTypeRepository`, `TransactionTypeMapper.toTransactionTypeDtos(...)`). Puudu on:

1. Uus meetod `TransactionTypeService`-s, mis tagastab kõik tehingutüübid DTO-dena.
2. Uus REST controller, mis avab endpointi `GET /api/atm/transaction-types`.
3. `TransactionTypeDto` ümbertõstmine jagatud `controller.common.dto` paketti (koos kõigi viidete uuendamisega) — praegu asub see `controller.location.dto`-s, kuigi seda kasutavad juba mitu domeeni (`location` ja nüüd ka `transactiontype`).

## Muudatused sammude kaupa

### 1. Tõsta `TransactionTypeDto` paketti `controller.common.dto`

Loo uus pakett `controller/common/dto/` ja liiguta fail sinna, muutes ainult package-deklaratsiooni:

```java
// enne: ee.bcs.bank.controller.location.dto.TransactionTypeDto
package ee.bcs.bank.controller.common.dto;
// ülejäänud klass jääb muutumatuks
```

Uus asukoht: `controller/common/dto/TransactionTypeDto.java`.

### 2. Uuenda kõiki `TransactionTypeDto` viiteid

Klass koliv teise paketti — lisa nendesse failidesse import `ee.bcs.bank.controller.common.dto.TransactionTypeDto` (varasem sama-paketi otsevaade enam ei kehti):

| Fail | Muudatus |
|---|---|
| `controller/location/dto/LocationDto.java` | lisa import |
| `controller/location/dto/LocationInfo.java` | lisa import |
| `persistence/transactiontype/TransactionTypeMapper.java` | uuenda import |
| `persistence/locationtransactiontypeview/LocationTransactionTypeViewMapper.java` | uuenda import |
| `service/LocationService.java` | uuenda import |

`src/main/generated/...MapperImpl.java` faile käsitsi ei muudeta — need genereeritakse `./gradlew compileJava` käigus MapStructi poolt uuesti, õige (uue) importiga.

### 3. Lisa `TransactionTypeService`-sse uus meetod

Järgi `CityService.findCities()` mustrit:

```java
public List<TransactionTypeDto> findTransactionTypes() {
    List<TransactionType> transactionTypes = transactionTypeRepository.findAll();
    List<TransactionTypeDto> transactionTypeDtos = transactionTypeMapper.toTransactionTypeDtos(transactionTypes);
    return transactionTypeDtos;
}
```

Selleks tuleb `TransactionTypeService`-le lisada ka `transactionTypeMapper` sõltuvus (konstruktoris, `@RequiredArgsConstructor` kaudu, sarnaselt `CityService`-le).

### 4. Loo uus controller `controller/transactiontype/TransactionTypeController.java`

Järgi `CityController` mustrit (ilma klassitasandi `@RequestMapping`-uta, täistee otse `@GetMapping`-ul):

```java
package ee.bcs.bank.controller.transactiontype;

@RestController
@RequiredArgsConstructor
public class TransactionTypeController {

    private final TransactionTypeService transactionTypeService;

    @GetMapping("/api/atm/transaction-types")
    @Operation(summary = "Tagastab kõik süsteemis defineeritud tehingutüübid")
    public List<TransactionTypeDto> findTransactionTypes() {
        List<TransactionTypeDto> transactionTypeDtos = transactionTypeService.findTransactionTypes();
        return transactionTypeDtos;
    }
}
```

Swagger `@ApiResponses` blokki eraldi lisada pole vaja (ainult 200-vastus, vt taski veaolukordade tabel — 500 on Springi vaikekäitumine, mida eraldi ei dokumenteerita, sarnaselt `CityController`-ile).

Eraldi DTO alampakki (`controller/transactiontype/dto/`) ei looda, kuna `TransactionTypeDto` on jagatud DTO ja elab nüüd `controller.common.dto`-s (vt samm 1).

### 5. Uuenda `backend/CLAUDE.md`

Kaks parandust:

1. **REST API tabel** (rida 91) — hetkel on seal ekslikult kirjas `GET /api/transaction-types` (implementeerimata endpoint), aga tegelik implementeeritav tee on `/api/atm/transaction-types`. Paranda rida vastavaks.
2. **Domeenikujunduse jaotis** (rea 54 lähedal, kus kirjeldatakse, et "igal domeenialasel on oma alampakk `controller/`-is koos DTOdega") — lisa lühike lause, mis dokumenteerib uue erandi: mitme domeeni vahel jagatud DTOd (nt `TransactionTypeDto`, mida kasutavad nii `location` kui `transactiontype`) elavad `controller/common/dto` paketis, mitte ühe domeeni omas.

## Loodavad/muudetavad failid

**Uued:**
- `controller/transactiontype/TransactionTypeController.java`
- `controller/common/dto/TransactionTypeDto.java` (liigutatud)

**Muudetavad:**
- `service/TransactionTypeService.java` (uus meetod + mapperi sõltuvus)
- `controller/location/dto/LocationDto.java` (import)
- `controller/location/dto/LocationInfo.java` (import)
- `persistence/transactiontype/TransactionTypeMapper.java` (import)
- `persistence/locationtransactiontypeview/LocationTransactionTypeViewMapper.java` (import)
- `service/LocationService.java` (import)
- `backend/CLAUDE.md` (REST API tabeli parandus + jagatud DTO konventsiooni dokumenteerimine)

**Kustutatavad:**
- `controller/location/dto/TransactionTypeDto.java` (vana asukoht)

## Testimine / vastuvõtukriteeriumide kontroll

| Vastuvõtukriteerium | Kuidas kontrollida |
|---|---|
| Endpoint `GET /api/atm/transaction-types` tagastab 200 | `./gradlew bootRun` käivitamise järel: `curl -i http://localhost:8080/api/atm/transaction-types` |
| Ei nõua sisendparameetreid | Sama curl-päring ilma parameetriteta peab õnnestuma |
| Response on JSON massiiv õigete väljadega | Kontrolli curl-vastuse JSON struktuuri (`transactionTypeId`, `transactionTypeName`, `isAvailable`) |
| `isAvailable` alati `false` | Kontrolli vastuses kõiki kolme elementi |
| Andmed vastavad `3_import.sql`-le | Võrdle vastust "raha sisse" / "raha välja" / "maksed" järjekorraga (id 1-3) |
| Tühja tabeli korral `200` + `[]` | Käsitsi (valikuline): kustuta ajutiselt `transaction_type` read testandmebaasis, kontrolli, et vastus on `200 []`, mitte 404 |
| Kasutab olemasolevat `TransactionTypeMapper.toTransactionTypeDtos(...)` | Koodiülevaatus — `TransactionTypeService.findTransactionTypes()` ei loo uut mapping-loogikat |
| Järgib kihistruktuuri ja nimetamiskonventsioone | Koodiülevaatus vs `backend/CLAUDE.md` (muutujanimed täistüübi järgi, `find`-prefiks otsingumeetodile) |
| Swagger dokumentatsioon lisatud | Ava `/swagger-ui.html`, kontrolli, et endpoint on nähtav koos kirjeldusega |
| Käsitsi testitud Swagger UI/curl kaudu | Tehtud eelnevate sammudega |

Peale koodimuudatusi käivita:

```bash
./gradlew compileJava   # genereerib MapStructi mapperid uuesti (uus TransactionTypeDto import)
./gradlew test          # veendu, et miski olemasolev ei katkenud (nt LocationService testid, kui neid on)
```
