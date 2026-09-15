---
name: skill-loo-backend-task-pdfist
description: Loo backend teenuse taski fail (MD) mockup PDF-i (nt Balsamiq wireframe) konkreetse lehekülje "API:" post-it märke põhjal ja salvesta docs/tasks/backend kausta koos lehekülje pildiga. Kasuta, kui kasutaja tahab teha backendi taski PDF-i/mockupi lehekülje pealt, mainib "API:" märget, või ütleb "loo backend task pdf-ist" vms.
---

# Loo backend task PDF-i lehekülje pealt

Loob ühe backend teenuse taski faili mockup-PDF-i (nt `docs/balsamic/bank_rest.pdf`) konkreetsel leheküljel oleva `API:`-märkega post-it noote põhjal, ja salvestab selle koos lehekülje pildiga kausta `docs/tasks/backend`.

## Kontekst, mida arvestada

- Kõik backend teenuste post-it noted mockup-PDF-ides on markeeritud sõnaga **"API:"**, millele järgneb HTTP meetod ja tee (nt `API: GET /api/atm/transaction-types`).
- Lehekülje pildid on tavaliselt juba ekstraktitud kausta `docs/balsamic/pdf-images/<leheküljenumber>.png`. Kui vastavat pilti ei ole, tuleb see PDF-ist genereerida (vt samm 3).
- Ühel leheküljel võib olla mitu `API:` märget (nt üks vaate avamiseks vajaliku info kohta, teine mõne alamkomponendi täitmiseks) — vali koos kasutajaga õige, kui see pole juba selge.

## Sammud

### 1. Küsi kasutajalt failinimi ja lehekülg

Kui kasutaja pole neid juba öelnud, küsi:

1. **PDF-i failinimi** — nt `bank_rest.pdf` (eeldatavalt kaustas `docs/balsamic/`)
2. **Lehekülje number**, millelt task luua

Oota vastust enne jätkamist.

### 2. Vaata lehekülje pilti

Kontrolli, kas pilt on juba olemas: `docs/balsamic/pdf-images/<leheküljenumber>.png`.

Kui pilt on olemas, loe see (Read tool) otse üle.

### 3. Kui pilti pole veel ekstraktitud, genereeri see

```bash
cd docs/balsamic
mkdir -p pdf-images
pdftoppm -png -r 150 -f <leheküljenumber> -l <leheküljenumber> <failinimi> pdf-images/tmp-page
mv pdf-images/tmp-page-*.png "pdf-images/<leheküljenumber>.png"
```

(`pdftoppm` lisab väljundfaili nimele lehenumbri, mis võib olla nullidega täidetud — seetõttu kasuta `mv` koos wildcardiga, et saada täpselt õige lõplik failinimi `<leheküljenumber>.png`.)

Loe seejärel pilt (Read tool) üle.

### 4. Leia õige "API:" post-it

Otsi pildilt kollast post-it märget, mis algab sõnaga `API:` ja sisaldab HTTP meetodit ning teed (nt `GET /api/atm/transaction-types`).

- Kui pildil on ainult üks `API:` märge, kasuta seda.
- Kui neid on mitu ja kasutaja pole täpsustanud, millist teenust silmas peetakse, **küsi kasutajalt**, millise kohta task luua.
- Loe post-iti sisu hoolikalt: seal on tavaliselt kirjas DTO klassi nimi, response näidis (staatuskood + JSON) ja "API teenuse lisainfo" ning "Veateated" read.

### 5. Uuri koodibaasi konteksti

Enne taski kirjutamist uuri, mis on juba olemas ja mis on ebakõlas:

- **Kas endpoint juba eksisteerib?** `grep -rn "<tee viimane osa>" backend/src --include=*.java`
- **Kas domeeni jaoks on juba olemas ehitusklotsid** (entity, repository, mapper, service, DTO, controller)? Vaata vastavaid `backend/src/main/java/ee/bcs/bank/{controller,service,persistence}` alampakke.
- **Kas dokumenteeritud tee (nt `backend/CLAUDE.md` REST tabelis) ühtib PDF-is näidatud teega?** Kui mitte, ei tohi ise otsustada, kumb kehtib — küsi kasutajalt (vt samm 7).
- Vaata sarnaste, juba olemasolevate kontrollerite/DTO-de stiili (nt `CityController`, `LocationController`), et task kirjeldaks lahendust projekti konventsioonidega kooskõlas (vt `backend/CLAUDE.md`: DTO vs entiteet, veakäsitlus, muutuja-/meetodinimetamine, repositooriumi meetodite nimetamine).

### 6. Leia seotud andmebaasi tabelid ja päris andmed

- Tuvasta, millised tabelid teenusega seotud on, ja too nende struktuur failist `docs/database/2_create.sql`.
- JSON näidistes (nii request kui response, kui neid on) kasuta **päris andmeid**, mis pärinevad kas PDF-i leheküljelt endalt (post-itis toodud näidis) või failist `docs/database/3_import.sql` — ära leiuta fiktiivseid väärtusi, kui reaalsed on saadaval.

### 7. Küsi kasutajalt, kui midagi on ebaselge

Ära oleta — kasuta `AskUserQuestion` tööriista, kui tekib mõni järgmistest olukordadest:

- PDF-is näidatud teenuse tee erineb koodis/dokumentatsioonis juba kirjeldatust.
- Post-itis pole veateateid kirjas (`Veateated: —`), aga ebaselge on, kas taski tuleks lisaks lisada üldised/tehnilised veajuhud (nt 500) või piirduda ainult sellega, mis post-itis kirjas.
- Leheküljel on mitu `API:` märget ja pole selge, millise kohta task luua.
- Muu oluline arhitektuuriline valik, mida post-it ega olemasolev kood üheselt ei määra (nt millisesse pakki uus DTO/controller peaks minema, kui olemasolev struktuur on ebajärjekindel).

Failinimetamise ja pildi lisamise osas (sammud 8-9) ära küsi — need on fikseeritud reeglid.

### 8. Koosta taski MD fail

Taski fail peab sisaldama järgmisi osasid, selles järjekorras:

1. **Pealkiri** (`# ...`) — teenuse sisu kokkuvõttev pealkiri (mitte URL)
2. **Teenus** — HTTP meetod + tee (nt `GET /api/atm/transaction-types`), viide allikale (PDF fail + lehekülg)
3. **Lehekülje pilt** — Markdown pilt, mis viitab taski kõrvale salvestatud pildile (vt samm 9)
4. **Sisendid** — path variable, request parameetrid, request body (kui neid pole, kirjuta selgelt, et sisendeid pole)
5. **Request body JSON näidis** (kui on request body)
6. **Response body JSON näidis** (kui on response body) — koos HTTP staatuskoodiga
7. **Eesmärk** — vabas vormis kirjeldus, mida teenusega üritatakse saavutada ja millises kasutajavoos see kasutusel on
8. **Seotud tabelid** — millised `docs/database/2_create.sql` tabelid teenusega seotud on, koos nende struktuuriga
9. **Olemasolevad ehitusklotsid** (kui koodis juba midagi domeeni jaoks eksisteerib) — loetle, mis on olemas ja mis puudu
10. **Veaolukorrad** — tabel kõigi võimalike vea-stsenaariumidega (HTTP staatuskood + response body), sh post-itis mainitud äriloogika veajuhud ja vajadusel üldised/tehnilised veajuhud (vt samm 7)
11. **Vastuvõtukriteeriumid** — checklist (`- [ ]`) kõigi kontrollitavate nõuetega

### 9. Faili- ja pildinimi

- **Failinimi = taski sisu pealkiri** (ilma piletikoodi prefiksita), nt `Tehingutüüpide-info.md`.
- Kopeeri lehekülje pilt (`docs/balsamic/pdf-images/<leheküljenumber>.png`) taski kõrvale samasse kausta, **sama nimega kui MD fail** (aga `.png` laiendiga), nt `Tehingutüüpide-info.png`. Kasuta tervet lehekülje pilti (ära kärbi).
- Salvesta mõlemad kausta `docs/tasks/backend/` (loo kaust, kui puudub).

### 10. Teavita kasutajat

Näita kasutajale:
- Faili asukoht (nii `.md` kui `.png`)
- Lühike kokkuvõte (2-3 lauset), mida task katab
- Kas tekkis mõni ebakõla/valikukoht, mis lahendati kasutajaga koos (vt samm 7), ja mille kasuks otsustati
- Küsi, kas midagi jäi puudu või vajab täiendamist

Suhtle kasutajaga eesti keeles.
