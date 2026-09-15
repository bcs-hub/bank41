# STEP-6 – Pangaautomaadid, tabel

Eesmärk on koostada promptiga mock-pildi põhjal uus `.md` taskifail.

[Ava REST mock-piltide kaust](../../mock/png/rest/)

Kasuta sisendina:

- pilti `STEP-6 (Pangautomaadid, tabel)-KÕIK.png`, millel on ka REST-teenuse andmed;
- faili [`docs/architecture/openAPI.json`](../../architecture/openAPI.json), et kontrollida
  teenuse `GET /api/atm/locations` lepingut.

Palu Claude Code'il võrrelda pildil olevat infot OpenAPI lepinguga ning luua nende põhjal
selge Markdown-vormingus taskifail. Task peab kirjeldama soovitud kasutajaliidest,
REST-päringut ja vastuvõtukriteeriume.
