---
name: skill-loo-backend-taski-implementatsiooni-plaan
description: Loo backend teenuse taski (MD fail docs/tasks/backend kaustas) põhjal implementatsiooniplaani MD fail — uurib taski, olemasolevat koodibaasi ja backend/CLAUDE.md konventsioone ning kirjeldab sammude kaupa vajalikud koodimuudatused. Märksõnad: implementatsiooniplaan, implementation plan, taski plaan, backend plaan, arendusplaan, koodimuudatuste plaan. Kasuta, kui kasutaja tahab backend taski jaoks implementatsiooniplaani, ütleb "loo implementatsiooniplaan", "tee plaan selle taski jaoks" vms.
---

# Loo backend taski implementatsiooniplaan

Loob olemasoleva backend teenuse taski faili (`docs/tasks/backend/*.md`) põhjal implementatsiooniplaani MD faili, mis kirjeldab sammude kaupa, milliseid koodiosasid tuleb luua või muuta.

## Sammud

### 1. Küsi kasutajalt taski faili path

Kui kasutaja pole seda juba öelnud, küsi: **"Millise taski faili jaoks implementatsiooniplaan luua? (path, nt docs/tasks/backend/Tehingutüüpide-info.md)"**

Oota vastust enne jätkamist.

### 2. Tutvu taskiga

Loe taski fail täies mahus läbi — teenuse URL/meetod, sisendid, request/response JSON näidised, eesmärk, seotud tabelid, olemasolevad ehitusklotsid, veaolukorrad ja vastuvõtukriteeriumid.

### 3. Tutvu olemasoleva koodibaasiga

Otsi üles taskiga seotud koodiosad, enne kui plaani kirjutad:

- **Kas endpoint juba osaliselt eksisteerib?** `grep -rn "<tee viimane osa>" backend/src --include=*.java`
- **Kas domeeni jaoks (entity, repository, mapper, service, DTO, controller) on midagi juba olemas?** Vaata `backend/src/main/java/ee/bcs/bank/{controller,service,persistence}` alampakke.
- **Kas seotud/jagatud klassid (DTO-d, mapperid, teenused) on juba kasutuses mujal?** `grep -rln "<klassinimi>" backend/src` — kaardista kõik viitavad failid, mitte ainult otsene sihtklass, sest need vajavad plaanis mainimist (nt impordi uuendus, kui klass kolib teise paketti).
- Vaata sarnaste, juba olemasolevate kontrollerite/teenuste stiili (nt `CityController`/`CityService`), et plaan järgiks sama mustrit.

### 4. Vaata backend/CLAUDE.md juhiseid

Loe `backend/CLAUDE.md` läbi ja veendu, et plaan järgib seal kirjeldatud konventsioone: DTO vs entiteet, veakäsitlus (kohandatud erindid + `RestExceptionHandler`), muutuja-/meetodinimetamine (`getX()` vs `find...By(...)` vs `handle...`), entiteedi otsing ID järgi (`getValid<Entiteet>By(...)`), repositooriumi meetodi nimetamine.

**Jagatud DTO-de reegel:** kui plaanitav või olemasolev DTO on kasutusel rohkem kui ühe domeeni/kontrolleri poolt, peab see asuma eraldi paketis `controller.common.dto` — mitte ühe domeeni alampaketis (nt `controller.location.dto`) ega dubleerituna mitmes kohas. Kui taski koostamisel avastad, et taaskasutatav DTO asub praegu vales (domeeni-spetsiifilises) paketis, lisa plaani samm selle ümbertõstmiseks `controller.common.dto`-sse koos kõigi viitavate failide impordi uuendamisega (leitud sammus 3).

### 5. Järgi failistruktuuri juhist

Vaata `docs/backend/projekti-struktuur.md` ja veendu, et kõik plaanis loodavad/liigutatavad failid saavad õiged asukohad ja nimed vastavalt sealsele kaustastruktuurile (`controller/<domeen>/`, `controller/<domeen>/dto/` või `controller/common/dto/` jagatud DTOde jaoks, `service/`, `persistence/<entiteet>/`).

### 6. Koosta implementatsiooniplaani MD fail

Plaan peab sisaldama järgmisi osasid, selles järjekorras:

1. **Pealkiri** — taski pealkiri + "— implementatsiooniplaan", viide taski failile ja teenuse URL-ile
2. **Kokkuvõte** — lühidalt, mis on juba olemas ja mida päriselt juurde vaja teha
3. **Muudatused sammude kaupa** — nummerdatud sammud; iga sammu juures kirjeldus, mida teha, ja **lühike koodikatke** (nt meetodi signatuur, annotatsioon, klassi kere põhiosa) ainult keerulisemate/olulisemate kohtade juures — **mitte** täismahus valmis klasse
4. **Loodavad/muudetavad/kustutatavad failid** — selge loend, grupeeritud (Uued / Muudetavad / Kustutatavad), täisradadega
5. **Testimine / vastuvõtukriteeriumide kontroll** — tabel, kus iga taski vastuvõtukriteerium on seotud konkreetse kontrollisammuga (nt curl-käsk, koodiülevaatus, test), pluss vajalikud build/test käsud (`./gradlew compileJava`, `./gradlew test` jne)

### 7. Faili nimi ja asukoht

- Salvesta implementatsiooniplaan **samasse kausta, kus asub taski fail**.
- Failinimi = taski failinimi, millele on lisatud postfiks **`-IMPLEMENTATSIOON`** enne `.md` laiendit.
  Näide: taski fail `Tehingutüüpide-info.md` → plaan `Tehingutüüpide-info-IMPLEMENTATSIOON.md`.

### 8. Teavita kasutajat

Näita kasutajale:
- Faili asukoht
- Lühike kokkuvõte (2-3 lauset), mis on plaani põhisisu (nt mitu uut faili, kas mõni olemasolev klass kolib/muutub)
- Kas plaani koostamisel tuli ette olulisi arhitektuurseid valikuid (nt jagatud DTO ümbertõstmine), mis polnud taskis endas üheselt kirjas
- Küsi, kas midagi jäi puudu või vajab täiendamist

Suhtle kasutajaga eesti keeles.
