---
name: skill-loo-backend-task
description: Loo backend teenuse taski fail (Markdown) mock veebilehe PDF-i (Balsamiq mockup) konkreetse lehekülje põhjal ja salvesta docs/tasks/backend kausta. Kasuta, kui kasutaja ütleb "loo backend task", "tee task pdf-i pealt", "loo taski fail mockupist" vms.
---

# Loo backend task PDF mockupi põhjal

Loeb Balsamiq mockup-PDF-i ühe lehekülje, tuvastab sealt kirjeldatud backend teenuse ("API:" märgendiga plokid), uurib koodibaasi ja andmebaasi skeemi ning koostab selle põhjal täieliku taski Markdown-faili kausta `docs/tasks/backend`.

## Sammud

### 1. Küsi PDF failinimi ja lehekülje number

Kui kasutaja pole neid juba oma sõnumis andnud, küsi korraga:

1. **PDF faili nimi** — nt `bank_rest.pdf` (eeldatavalt kaustas `docs/balsamiq/`)
2. **Lehekülje number** — mida taski jaoks kasutada

Oota vastust enne kui jätkad.

### 2. Loe PDF-i vastav lehekülg

- Loe PDF-i antud lehekülg (Read tööriist, `pages` parameeter).
- Otsi lehelt kõiki plokke, mis on markeeritud sõnaga **"API:"** — need kirjeldavad backend teenuseid (nt `API: GET /api/atm/transaction-types`).
- Kui leiad rohkem kui ühe "API:" ploki samalt leheküljelt, loetle need kasutajale ja küsi, millise teenuse jaoks taski luua (üks fail = üks teenus).
- Kui ühtegi "API:" plokki ei leidu, teavita kasutajat ja küsi täpsustust (nt vale leheküljenumber).

### 3. Leia lehekülje pilt

- Kontrolli, kas kaustas `docs/balsamiq/pdf-images/<lehekülje number>.png` on vastav pilt juba olemas.
- Kui pilti pole, teavita kasutajat ja küsi, kas ta soovib, et see genereeritaks PDF-ist (nt `pdftoppm` abil), enne kui jätkad.

### 4. Analüüsi API plokk

Tuvasta valitud "API:" plokist:

- **Teenuse tee ja HTTP meetod** (nt `GET /api/atm/transaction-types`)
- **Sisendid** — path variable, query parameeter, request body (kui neid pole, märgi selgelt "puuduvad")
- **Request body näidis**, kui on
- **Response body näidis**, kui on (koos DTO klassi nimega, kui see on mockupil kirjas)
- **"API teenuse lisainfo"** tekst — kirjeldab teenuse eesmärki/käitumist
- **"Veateated"** tekst — kui on kirjas konkreetseid veaolukordi, muidu "—"

### 5. Uuri koodibaasi konteksti

- Otsi `backend/src/main/java`-st, kas kirjeldatud teenus, selle domeeni controller, service, DTO või repository juba (osaliselt) eksisteerivad (Grep/Glob).
- Kontrolli `backend/CLAUDE.md` REST API tabelit — kas seal on sama teenus dokumenteeritud, ja kas tee/nimetus ühtib PDF-iga.
- Kui leiad lahknevusi (nt tee erineb dokumentatsioonist, olemasolev DTO nimi erineb PDF-i omast) või olemasolevat sarnast koodi, mida saaks taskis eeskujuks tuua, märgi need üles järgmise sammu jaoks.

### 6. Uuri andmebaasi skeemi ja näidisandmeid

- Leia teenusega seotud tabeli(te) struktuur failist `docs/database/2_create.sql`.
- Leia sobivad näidisandmed failist `docs/database/3_import.sql` (kasuta neid JSON näidistes, kooskõlas PDF-il nähtava sisuga).
- Kui teenus on seotud mitme tabeliga (nt many-to-many seosetabel), otsusta ja vajadusel küsi kasutajalt, kas seosetabel kuulub selle taski konteksti või on eraldiseisev.

### 7. Küsi kasutajalt lahtiste kohtade osas

Kui sammudes 5–6 tekkis valikukohti, mida ei saa üheselt PDF-ist ega koodist tuletada — nt:

- Tee erineb `backend/CLAUDE.md` dokumentatsioonist vs PDF-ist
- DTO nimi — kasutada olemasolevat klassi vs luua uus PDF-i nimega
- Kas ja kuidas kajastada veaolukordi, kui PDF ise vigu ei kirjelda
- Ettepanek taski faili pealkirja/failinime kohta

küsi kasutajalt (AskUserQuestion) enne faili loomist — ära oleta vaikimisi valikuid selliste lahknevuste puhul.

### 8. Koosta taski fail

Taski Markdown-fail peab sisaldama järgmisi osi (selles järjekorras):

1. **Pealkiri** — lühike äriotstarbeline pealkiri eesti keeles + teenuse URL sulgudes, nt `# Tehingutüüpide loetelu (GET /api/atm/transaction-types)`
2. **Teenus** — HTTP meetod + tee
3. **Sisendid** — path variable / query parameeter / request body, või "puuduvad"
4. **Request body näidis** (JSON), kui on
5. **Response body näidis** (JSON), kui on — kasuta samm 6 näidisandmeid
6. **Ekraanipilt** — viide kõrvale salvestatud pildile koos allikaviitega (PDF fail + lehekülg)
7. **Eesmärk** — mida teenusega üritatakse saavutada, tuginedes "API teenuse lisainfo" tekstile ja laiemale kontekstile (mis vaadet/komponenti teenus toidab)
8. **Seotud andmebaasi tabelid** — tabeli(te) struktuur (SQL) + asjakohased näidisandmed tabelina
9. **Vealolukorrad** — tabel: olukord / HTTP staatus / response body näidis. Kui PDF ei kirjelda konkreetseid vigu, kajasta vähemalt üldist serveri viga (500) koos põhjendusega, miks rohkem valideerimisvigu ei teki
10. **Vastuvõtukriteeriumid** — checkbox-loeteluna (`- [ ]`), sh nii funktsionaalsed nõuded (staatuskoodid, väljad, andmed) kui koodikonventsioonidele vastavus (DTO/controller/service asukoht ja nimetus, olemasoleva sarnase koodi eeskujul, Swagger dokumentatsioon, testid)

Kirjuta kogu sisu eesti keeles.

### 9. Vali failinimi

- Failinimi tuletatakse taski pealkirjast: väiketähed, sidekriipsud, täpitähed transliteeritud (ä→a, ö→o, ü→u, õ→o).
- Nt pealkiri "Tehingutüüpide loetelu" → failinimi `tehingutuupide-loetelu.md`.

### 10. Salvesta failid

- Kui kaust `docs/tasks/backend` puudub, loo see.
- Salvesta taski fail: `docs/tasks/backend/<failinimi>.md`.
- Kopeeri sammus 3 leitud/genereeritud lehekülje pilt kõrvale: `docs/tasks/backend/<failinimi>.png`, ja viita sellele taski failis suhtelise teega (`./<failinimi>.png`).

### 11. Teavita kasutajat

Näita kasutajale:
- Taski faili ja pildi asukoht
- Lühike kokkuvõte (2-3 lauset) teenuse eesmärgist
- Kokkuvõte olulisematest otsustest/eeldustest, mis tehti sammudes 5–7 (nt kasutatud tee, DTO nimi)
- Küsimus, kas midagi jäi puudu või vajab täiendamist
