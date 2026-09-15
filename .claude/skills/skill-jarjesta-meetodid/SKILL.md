---
name: skill-jarjesta-meetodid
description: Korrasta ühe faili meetodite järjekord vastavalt nende väljakutsumise hierarhiale (kutsuja enne kutsutavat). Kasuta, kui kasutaja ütleb "järjesta meetodid", "korrasta meetodite järjekord", "sorteeri meetodid väljakutsumise järgi" vms.
---

# Järjesta meetodid

Korrastab ühe faili meetodite järjekorra vastavalt nende väljakutsumise hierarhiale, nn "step-down" reegli järgi — meetod paikneb failis enne meetodeid, mida ta ise välja kutsub, nii et faili saab lugeda ülalt alla nagu lugu.

## Sammud

### 1. Küsi failitee
- Kui kasutaja pole faili teed juba oma sõnumis andnud:
  - Kui eelnevast vestlusest on selge, et jutt käib mingist konkreetsest klassist/failist (nt oldi äsja koos mingi klassiga tegevuses), ja arvad ära arvata, millist faili kasutaja silmas peab, küsi kasutajalt kinnitust selle faili kohta — kasutaja saab vastata "jah" või kirjutada ise õige failitee.
  - Vastasel juhul küsi otse, millise faili meetodid tuleb ümber järjestada.
- Kontrolli, et fail eksisteerib.

### 2. Loe fail ja tuvasta meetodid
- Loe fail läbi.
- Tuvasta kõik meetodid (avalikud, kaitstud, privaatsed) koos nende täieliku kehaga, annotatsioonide ja vahetult neile eelnevate kommentaaridega.
- Jäta väljad (fields), konstruktorid, sisemised klassid ja klassi definitsioon puutumata — ümber järjestad ainult meetodid.

### 3. Tuvasta väljakutsumise hierarhia
- Analüüsi iga meetodi keha ja tuvasta, milliseid teisi sama faili/klassi meetodeid ta välja kutsub.
- Moodusta väljakutsumispuu: avalikud sisenemispunktid (nt kontrolleri endpointid, teenuse avalikud meetodid) on juurtasemel, nende poolt kutsutavad abimeetodid järgmistel tasemetel.

### 4. Järjesta meetodid
- Rakenda "step-down" reegel: iga meetod paikneb enne meetodeid, mida ta ise väljakutsub (kutsuja enne kutsutavat).
- Avalike sisenemispunktide omavaheline algne järjekord säilita, välja arvatud juhul, kui üks avalik meetod kutsub teist avalikku meetodit — sel juhul kehtib kutsuja-enne-kutsutavat reegel ka nende vahel.
- Kui üht abimeetodit kutsuvad mitu erinevat ülemeetodit (jagatud ehk shared alameetod), paiguta see kõige faili lõppu, pärast kõiki meetodeid, millel on ainult üks väljakutsuja. Kui selliseid jagatud alameetodeid on mitu, järjesta need omavahel esimese kutsuja järgi failis (kõige varasema kutsujaga jagatud meetod enne teisi).
- Rekursiivsed või vastastikku kutsuvad meetodid (tsükkel väljakutsumispuus) jäta omavahel senisesse suhtelisse järjekorda.
- **Ära muuda meetodite sisu, signatuure ega loogikat** — muuda ainult nende järjekorda failis.

### 5. Kirjuta fail üle ja tee kokkuvõte
- Kirjuta ümberjärjestatud fail tagasi samasse asukohta.
- Näita kasutajale lühikest kokkuvõtet uuest meetodite järjekorrast (meetodite nimed vanas ja uues järjekorras).
