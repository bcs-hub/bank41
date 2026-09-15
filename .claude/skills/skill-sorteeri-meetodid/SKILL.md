---
name: skill-sorteeri-meetodid
description: Korrastab ühe faili meetodite järjekorra vastavalt nende väljakutsumise hierarhiale, nii et iga meetod paikneb enne meetodeid, mida ta kutsub. Kasuta, kui kasutaja ütleb "järjesta meetodid", "sorteeri meetodid", "korrasta meetodite järjekord" või tahab, et meetodid oleks failis loogilises väljakutsumisjärjekorras.
---

Küsi kasutajalt, kui ta ei ole seda juba öelnud, millise faili meetodid tuleb järjestada (faili path). Kui kasutaja ei ole konkreetset faili maininud, aga eelneva vestluse kontekstist (nt on juttu olnud mingist kindlast klassist) arvad ära teadvat, millist faili silmas peetakse, siis ära eelda — küsi kasutajalt kinnitust, pakkudes välja oma oletatud faili pathi, millele kasutaja saab vastata kas "jah" või kirjutada ise õige faili path.

Loe fail läbi ja tuvasta selles klassis/failis defineeritud meetodid ning kaardista, milline meetod millist teist sama faili meetodit kutsub (väljakutsumishierarhia).

Järjesta meetodid ülalt-alla reegli (step-down rule) järgi:

- Sisenemispunktid (nt avalikud meetodid, mida väljastpoolt klassi kutsutakse, või muul viisil selgelt kõrgeima taseme meetodid) jäävad kõige ettepoole, säilitades nende omavahelise algse järjekorra.
- Kohe iga meetodi järel peavad järgnema meetodid, mida ta otse kutsub — samas järjekorras, nagu ta neid kutsub. Sama loogika kehti rekursiivselt ka nende kutsutavate meetodite kohta.
- Kui meetodit kutsutakse mitmest kohast, paiguta ta ainult esimese (kõrgeima taseme) väljakutsuja järele — ära meetodit dubleeri.
- Meetodid, mida sama failis üldse ei kutsuta (nt kasutamata jäänud abimeetodid), jäta lõppu, algses omavahelises järjekorras.
- Väljad, konstruktorid, static plokid, sisemised klassid ja imporditud sõltuvused jäta oma praegustesse kohtadesse — liiguta ainult meetodeid.
- Meetodiga seotud kommentaarid/javadoc liiguvad koos meetodiga.

Ümberjärjestus ei tohi muuta koodi käitumist ega meetodite sisu — ainult nende järjekorda failis. Pärast muudatuse tegemist kompileeri või käivita asjakohased testid, veendumaks, et miski ei katkenud.

Näita kasutajale lühidalt uut meetodite järjekorda.

Suhtle kasutajaga eesti keeles.
