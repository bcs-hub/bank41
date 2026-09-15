---
name: skill-jarjesta-meetodid
description: Järjestab faili meetodid ümber vastavalt nende väljakutsumise hierarhiale (kes keda kutsub), säilitades sisu muutumatuna. Kasuta, kui kasutaja ütleb "Järjesta meetodid", "Korrasta meetodite järjekord", "Sorteeri meetodid väljakutsumise järgi" vms.
---

# Järjesta meetodid

Korrastab ühe faili meetodite järjekorra vastavalt nende väljakutsumise hierarhiale, ilma sisu sisuliselt muutmata.

## Sammud

### 1. Küsi faili path
- Kui kasutaja ei ole faili teed juba öelnud, küsi seda.
- **Erand:** kui kasutaja ei ole konkreetset faili/klassi maininud, aga eelneva vestluse kontekstist (nt on jutt käinud mingist klassist/failist) arvad ära teadvat, millist faili silmas peetakse — ära eelda automaatselt. Küsi kasutajalt kinnitust (paku oma oletatud faili nimi/path välja), enne kui muudatusi tegema hakkad.
- Kontrolli, et fail eksisteerib, enne jätkamist.

### 2. Loe fail läbi
- Loe kogu faili sisu ja tuvasta kõik meetodid (sh konstruktorid), nende praegune järjekord, javadoc/kommentaarid ja annotatsioonid, mis nende ees seisavad.

### 3. Tuvasta väljakutsumise hierarhia
- Analüüsi, milline meetod millist teist sama faili meetodit kutsub (`this.meetod(...)` või otsene viide sama klassi meetodile).
- Ehita mõttes puu/graaf: avaliku API meetodid (nt kontrolleri endpointid, avalikud service meetodid) on tipus, nende poolt kutsutud privaatsed/abimeetodid on allpool.

### 4. Järjesta meetodid step-down reegli järgi
- Kasuta "step-down" põhimõtet (nagu ajaleheartikkel ülalt alla loetav): kõrgema taseme/avalik meetod paikneb enne meetodeid, mida ta ise kutsub.
- **Jagatud meetodid** — meetodid, mida kutsub mitu erinevat ülemmeetodit — paiguta faili kõige lõppu (pärast kõiki teisi meetodeid), kuna neid ei saa üheselt ühe kutsuja alla paigutada.
- Meetodid, mida üldse ei kutsuta failisiseselt (nt puhtad avalikud API meetodid), jäta oma loomuliku järjekorra alusel eespoole.
- Konstruktorid jäävad reeglina kõige ette, vahetult pärast välju.
- **Ära muuda meetodite sisu, signatuuri ega loogikat** — ainult nende järjekorda failis. Javadoc/kommentaarid ja annotatsioonid liiguvad koos oma meetodiga.

### 5. Kirjuta fail tagasi
- Rakenda uus järjekord failis, säilitades korrektse vormistuse (tühikud/reavahed meetodite vahel).

### 6. Kokkuvõte
- Näita kasutajale, milline oli meetodite vana järjekord ja milline on uus järjekord (lühike nimekiri).
