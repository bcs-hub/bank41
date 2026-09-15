# Tehingutüüpide nimekirja päring — implementatsiooni plaan

**Seotud task:** `docs/tasks/backend/Tehingutuupide-nimekirja-paring.md`

## Hetkeseis (mis on juba olemas)

Suur osa vajalikust ketist on juba koodibaasis olemas — teine funktsionaalsus (asukoha lisamise vorm) kasutab neid samu klasse juba ära:

- `backend/src/main/java/ee/bcs/bank/persistence/transactiontype/TransactionType.java` — JPA entiteet tabelile `transaction_type` (väljad `id`, `name`), täielikult olemas.
- `backend/src/main/java/ee/bcs/bank/persistence/transactiontype/TransactionTypeRepository.java` — Spring Data repository (`JpaRepository<TransactionType, Integer>`), täielikult olemas. `findAll()` on juba päritud, midagi lisada pole vaja.
- `backend/src/main/java/ee/bcs/bank/persistence/transactiontype/TransactionTypeMapper.java` — MapStruct mapper, millel juba on `toTransactionTypeDtos(List<TransactionType>)` meetod, mis mäpib `id → transactionTypeId`, `name → transactionTypeName` ja seab konstandi `isAvailable = false`. See vastab täpselt taski nõutud response struktuurile, muudatust ei vaja.
- `backend/src/main/java/ee/bcs/bank/controller/common/dto/TransactionTypeDto.java` — DTO klass väljadega `transactionTypeId`, `transactionTypeName`, `isAvailable`, täielikult vastab taski JSON näidisele. **Tõstetud** paketist `controller/location/dto/` paketti `controller/common/dto/`, kuna DTO on jagatud mitme ressursi vahel (kasutusel nii `location`, `transactiontype` kui `locationtransactiontypeview` mapperites) — vt backend/CLAUDE.md reeglit "Jagatud DTO-d". Kõik importid (`LocationService`, `LocationDto`, `LocationInfo`, `TransactionTypeMapper`, `LocationTransactionTypeViewMapper`) on juba uuendatud, projekt kompileerub.
- `backend/src/main/java/ee/bcs/bank/service/TransactionTypeService.java` — olemas, aga sisaldab ainult `getValidTransactionType(Integer transactionTypeId)` meetodit (ühe kirje leidmine ID järgi). **Nimekirja tagastav meetod (`findAll` tüüpi) puudub.**

**Puudub täielikult:**

- REST kontroller `transaction-types` (või `atm/transaction-types`) endpointi jaoks — sellist kontrollerit koodibaasis ei ole.
- Automaattestid — `backend/src/test/java/` sisaldab ainult tühja `BankApplicationTests.java`, ühtegi service- ega kontrolleritesti pole kusagil.

**Eeskujuks sobiv analoogne, juba valmis teenus:** `GET /api/cities` — `CityController` + `CityService.findCities()` (vt `backend/src/main/java/ee/bcs/bank/controller/city/CityController.java` ja `backend/src/main/java/ee/bcs/bank/service/CityService.java`). See on struktuurilt praktiliselt identne teenus (sisenditeta GET, tagastab kõik kirjed DTO-dena) ja tuleks järgida sama mustrina.

## Puuduv/muudetav

1. `TransactionTypeService` — lisada nimekirja tagastav meetod.
2. Uus `TransactionTypeController` — REST endpoint.
3. Testid — service ühiktest ja kontrolleri integratsioonitest.

## Sammud

Persistence kiht (entiteet, repository, mapper) on juba täielikult olemas ja muudatust ei vaja — vt "Hetkeseis".

1. **Lisa service meetod nimekirja tagastamiseks** — fail: `backend/src/main/java/ee/bcs/bank/service/TransactionTypeService.java`
   - Lisa `TransactionTypeMapper` sõltuvus (`private final TransactionTypeMapper transactionTypeMapper;`), kuna praegu service seda ei kasuta.
   - Lisa meetod `findTransactionTypes()`, mis järgib `CityService.findCities()` mustrit üks-ühele:
     ```java
     public List<TransactionTypeDto> findTransactionTypes() {
         List<TransactionType> transactionTypes = transactionTypeRepository.findAll();
         List<TransactionTypeDto> transactionTypeDtos = transactionTypeMapper.toTransactionTypeDtos(transactionTypes);
         return transactionTypeDtos;
     }
     ```
   - Meetodi nimi järgib backend/CLAUDE.md reeglit `getX()` kindla tagastuse jaoks — kuna siin pole tingimuslikku loogikat ega DTO muteerimist, sobib `findX()` stiil (vrd `CityService.findCities()`), mitte `handleX()`.
   - `getValidTransactionType(Integer transactionTypeId)` meetodit ei muudeta — see jääb kasutusele `LocationService`-s.

2. **Loo uus kontroller** — fail: `backend/src/main/java/ee/bcs/bank/controller/transactiontype/TransactionTypeController.java`
   - Uus pakett `controller/transactiontype/`, järgides `docs/backend/projekti-struktuur.md` struktuuri (`controller/<ressurss>/SomeController.java`).
   - DTO klassi `TransactionTypeDto` ei pea kopeerima ega taasluua — kasuta olemasolevat importi paketist `controller.common.dto` (jagatud DTO, vt "Hetkeseis").
   - Endpoint:
     ```java
     @RestController
     @RequiredArgsConstructor
     public class TransactionTypeController {

         private final TransactionTypeService transactionTypeService;

         @GetMapping("/api/atm/transaction-types")
         @Operation(summary = "Leiab süsteemist kõik tehingutüübid")
         public List<TransactionTypeDto> findTransactionTypes() {
             List<TransactionTypeDto> transactionTypeDtos = transactionTypeService.findTransactionTypes();
             return transactionTypeDtos;
         }
     }
     ```
   - Struktuur, annotatsioonid (`@RestController`, `@RequiredArgsConstructor`, `@Operation`) ja meetodi ülesehitus järgivad täpselt `CityController` eeskuju.

## Veakäsitlus

Kuna teenusel pole sisendeid (ei path variable't, query parameetrit ega request body't), ei ole vaja lisada ühtegi kohandatud erindi viset ega uut `Error` enumi väärtust. Ainuke taskis kirjeldatud veaolukord on ootamatu serveripoolne viga (500) — selle katab Springi vaikimisi käsitlus (`RestExceptionHandler` ei sekku, kui erindit pole eraldi püütud), tulemuseks on Springi standardne 500 vastus. Eraldi koodi selleks kirjutada pole vaja.

Kui andmebaasis pole ühtegi `transaction_type` kirjet, tagastab `findAll()` tühja listi ja mapper tühja listi DTO-sid — see on käsitletud loomulikult, ilma erikoodi vajaduseta (vastab taski nõudele tagastada `200 OK` ja `[]`).

## Testid

**Service ühiktest** — uus fail: `backend/src/test/java/ee/bcs/bank/service/TransactionTypeServiceTest.java`
- Testi `findTransactionTypes()`:
  - Juhtum: repository tagastab mitu `TransactionType` kirjet → service tagastab vastava arvu `TransactionTypeDto` objekte õigete väärtustega (`transactionTypeId`, `transactionTypeName`, `isAvailable = false`).
  - Juhtum: repository tagastab tühja listi → service tagastab tühja listi.
  - Mocki `TransactionTypeRepository` ja kasuta reaalset (Spring genereeritud) `TransactionTypeMapper` implementatsiooni või mocki ka mapperit, olenevalt projektis kasutatavast testimisstiilist (kuna teisi teste koodibaasis pole, vali Mockito + JUnit 5, mis on `build.gradle`s tõenäoliselt juba sõltuvusena olemas — kontrolli üle).

**Kontrolleri integratsioonitest** — uus fail: `backend/src/test/java/ee/bcs/bank/controller/transactiontype/TransactionTypeControllerTest.java`
- `@SpringBootTest` + `MockMvc` (või `@WebMvcTest`), testi:
  - `GET /api/atm/transaction-types` tagastab `200 OK` ja JSON massiivi õigete väljadega.
  - Kui andmebaasis (test-andmebaas / test-fixture) pole tehingutüüpe, tagastatakse `200 OK` ja `[]`.
- Kuna projektis pole veel ühtegi kontrolleritesti eeskujuks, otsusta koos meeskonnaga, kas kasutada täielikku Spring konteksti reaalse test-andmebaasiga (vastavalt `docs/database/` skriptidele) või H2/mockitud repository — vt "Avatud küsimused".

## Lahendatud küsimused

1. **API tee:** `backend/CLAUDE.md`-st on REST API teenuste loetelu (sh vana `/api/transaction-types` kirje) täielikult eemaldatud — üksikute teenuste kirjeldused elavad nüüd taski failides (`docs/tasks/backend/`). Seega vastuolu ei ole: ainuke ametlik tee on taskis kirjeldatud `GET /api/atm/transaction-types`.
2. **DTO paketi asukoht:** otsustatud ja teostatud — `TransactionTypeDto` on jagatud DTO ning asub paketis `controller/common/dto/`. Vt "Hetkeseis" ja backend/CLAUDE.md reeglit "Jagatud DTO-d".

## Avatud küsimused

1. **Testimisstrateegia:** kuna koodibaasis pole veel ühtegi näidistesti (service ega controller tasandil), tuleb kokku leppida testimismuster (Mockito unit-testid vs täieliku Spring konteksti integratsioonitestid reaalse/test-andmebaasiga) enne testide kirjutamist, et järgnevad taskid saaksid sama mustrit korrata.

---
🤖 Generated with [Claude Code](https://claude.com/claude-code)
