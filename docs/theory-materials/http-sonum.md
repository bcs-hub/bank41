# HTTP sõnum – Algajatele

## Mis see on?

Kui brauser laadib veebilehte või kui frontend rakendus (nt Vue) küsib backendilt andmeid, ei toimu see "maagiliselt" — brauser ja server räägivad omavahel kindla vormiga **kirjade** abil. Neid kirju nimetatakse **HTTP sõnumiteks** (*HTTP message*).

HTTP (*HyperText Transfer Protocol*) on kokkulepe selle kohta, **kuidas selline kiri peab välja nägema**, et mõlemad pooled — saatja ja saaja — sellest üheselt aru saaksid.

Iga HTTP suhtlus koosneb täpselt kahest sõnumist:

1. **Päring (request)** — klient (nt brauser) saadab serverile: "anna mulle see" või "salvesta see minu jaoks"
2. **Vastus (response)** — server vastab kliendile: "siin on see" või "vabandust, ei õnnestunud"

### Päriselu analoogia: tähitud kiri

Kujuta ette, et saadad postiga tähitud kirja:

- **Ümbrik** ütleb, kellele kiri läheb, kust see tuli, ja on peal mõned templid (nt "kiireloomuline", "tähitud") — see on **päis (header)**.
- **Kirja sisu** ise on see, mida sa tegelikult öelda tahad — see on **keha (body)**.

Server saab su kirja, loeb ümbrikult vajaliku info, avab ja loeb sisu, ning saadab sulle **vastuskirja** — millel on jälle oma ümbrik (päised) ja oma sisu (keha).

## HTTP sõnumi ehitus

Nii päringul kui vastusel on **sama üldine struktuur** — kolm osa:

```
┌─────────────────────────────────────────┐
│  1. ALGUSRIDA (start line)               │  ← mida tahetakse / mis juhtus
├─────────────────────────────────────────┤
│  2. PÄISED (headers)                     │  ← lisainfo sõnumi kohta
│     Key: Value                           │
│     Key: Value                           │
├─────────────────────────────────────────┤
│  (tühi rida — eraldab päised kehast)     │
├─────────────────────────────────────────┤
│  3. KEHA (body) — valikuline             │  ← tegelik andmesisu
└─────────────────────────────────────────┘
```

See kolmeosaline struktuur kehtib **mõlemale** suunale — nii kliendi päringule kui serveri vastusele. Erinevus on ainult algusreas.

## Päring (Request) — klient küsib

```
GET /api/kasutajad/42 HTTP/1.1
Host: minupank.ee
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Accept: application/json

(keha puudub, kuna GET ei saada tavaliselt andmeid kaasa)
```

Algusrida koosneb kolmest osast:

| Osa | Näide | Tähendus |
|---|---|---|
| **Meetod** | `GET` | Mida tahetakse teha (vt allpool) |
| **Tee (path)** | `/api/kasutajad/42` | Milliselt ressursilt/aadressilt |
| **Protokolli versioon** | `HTTP/1.1` | Millist HTTP versiooni kasutatakse |

### Levinumad HTTP meetodid

| Meetod | Analoogia | Kasutus |
|---|---|---|
| `GET` | "Näita mulle" | Andmete pärimine, ei muuda serveris midagi |
| `POST` | "Siin on uus asi" | Uue ressursi loomine (nt uue kasutaja registreerimine) |
| `PUT` | "Asenda see täielikult" | Olemasoleva ressursi täielik uuendamine |
| `PATCH` | "Muuda ainult see osa" | Olemasoleva ressursi osaline uuendamine |
| `DELETE` | "Kustuta see" | Ressursi kustutamine |

## Vastus (Response) — server vastab

```
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 58

{"id": 42, "nimi": "Mari Maasikas", "saldo": 128.50}
```

Algusrida (siin nimetatakse seda **staatusreaks**) koosneb samuti kolmest osast:

| Osa | Näide | Tähendus |
|---|---|---|
| **Protokolli versioon** | `HTTP/1.1` | Millist HTTP versiooni server kasutab |
| **Staatuskood** | `200` | Kolmekohaline number, mis ütleb, kuidas läks |
| **Staatustekst** | `OK` | Inimloetav selgitus koodile |

### Staatuskoodide "sajad" — kiire ülevaade

Staatuskoodi **esimene number** ütleb kohe, mis kategooriaga on tegu:

```
1xx  ℹ️  Info      — päring on vastu võetud, töö käib
2xx  ✅  Õnnestus   — kõik läks hästi
3xx  ↪️  Suunamine  — mine vaata mujalt
4xx  ❌  Kliendi viga — sina (klient) tegid midagi valesti
5xx  💥  Serveri viga — server läks katki, mitte sinu süü
```

Levinumad koodid, mida panga-äpi arendamisel ette tuleb:

| Kood | Tähendus | Millal tekib |
|---|---|---|
| `200 OK` | Kõik õnnestus | Tavaline edukas vastus |
| `201 Created` | Loodi uus ressurss | Pärast õnnestunud `POST`-i |
| `400 Bad Request` | Päring on vigaselt formeeritud | Nt puudub kohustuslik väli |
| `401 Unauthorized` | Pole sisse logitud / token puudub | Kasutaja pole autentitud |
| `403 Forbidden` | Sisse logitud, aga õigust pole | Nt tavakasutaja üritab admin-tegevust |
| `404 Not Found` | Ressurssi ei leitud | Nt vale kasutaja ID |
| `500 Internal Server Error` | Serveris läks midagi katki | Nt programmiviga backend'is |

## Päised (Headers) — lisainfo ümbrikul

Päised on **võti-väärtus paarid**, mis annavad sõnumi kohta lisainfot, ilma et need oleks osa tegelikust sisust.

```
Content-Type: application/json
Content-Length: 58
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

Levinumad päised:

| Päis | Kasutus |
|---|---|
| `Content-Type` | Ütleb, millises formaadis on keha (nt `application/json`, `text/html`) |
| `Content-Length` | Keha pikkus baitides |
| `Authorization` | Sisaldab autentimisinfot (nt tokenit) |
| `Accept` | Klient ütleb, millises formaadis ta vastust soovib |
| `Set-Cookie` | Server palub kliendil salvestada küpsise |

## Keha (Body) — tegelik sisu

Keha on **valikuline** — see puudub tihti `GET` päringutel (kuna ei saadeta midagi, vaid ainult küsitakse), aga on peaaegu alati olemas `POST`/`PUT`/`PATCH` päringutel ja enamikel vastustel.

Panga-äpi kontekstis on keha tavaliselt **JSON** formaadis:

```json
{
  "username": "mari",
  "password": "salajane123"
}
```

## Kogu suhtlus koos — näide sisselogimisest

```
KLIENT (brauser)                          SERVER (backend)
      │                                         │
      │  POST /api/login HTTP/1.1              │
      │  Content-Type: application/json         │
      │                                         │
      │  {"username":"mari","password":"..."}   │
      │ ──────────────────────────────────────▶ │
      │                                         │
      │                    (server kontrollib   │
      │                     kasutajanime/parooli)│
      │                                         │
      │  HTTP/1.1 200 OK                        │
      │  Content-Type: application/json          │
      │                                         │
      │  {"token":"eyJhbGciOiJIUzI1NiJ9..."}     │
      │ ◀────────────────────────────────────── │
      │                                         │
```

See on täpselt see, mis toimub, kui `LoginView.vue` kasutaja "Logi sisse" nuppu vajutab — Vue saadab `POST` päringu koos kasutajanime ja parooliga kehas, ning backend vastab kas edu (token) või veaga (nt `401 Unauthorized`).

## Kokkuvõte

| Mõiste | Selgitus |
|---|---|
| HTTP sõnum | Kokkuleppelise vormiga "kiri", mida klient ja server omavahel saadavad |
| Päring (Request) | Klient küsib serverilt midagi — sisaldab meetodit, teed, päiseid, valikulist keha |
| Vastus (Response) | Server vastab kliendile — sisaldab staatuskoodi, päiseid, valikulist keha |
| Algusrida | Päringul: meetod + tee. Vastusel: staatuskood + tekst |
| Päised | Võti-väärtus paarid lisainfoga (nt `Content-Type`, `Authorization`) |
| Keha | Tegelik andmesisu, tavaliselt JSON-formaadis |
| Staatuskoodid | `2xx` õnnestus, `4xx` kliendi viga, `5xx` serveri viga |

## Järgmised sammud

Kui see teema oli huvitav, tasub edasi õppida:
- **REST API põhimõtted** — kuidas meetodeid (`GET`/`POST`/`PUT`/`DELETE`) ja teid loogiliselt kokku panna
- **JSON formaat** — kuidas andmeid struktureeritult HTTP kehas saata
- **CORS** — miks brauser mõnikord blokeerib päringuid teisele domeenile
- **Axios** — kuidas frontend (Vue) päriselt HTTP päringuid saadab (`this.$axios.post(...)`)
- **Autentimine tokeniga (JWT)** — kuidas `Authorization` päist kasutatakse sisselogitud kasutaja tuvastamiseks
