---
name: skill-transkript-oppematerjal
description: Loo algajasõbralik õppematerjal eesti keeles video transkriptist ja salvesta docs/transcript-materials kausta.
---

# Loo õppematerjal video transkriptist

Loe VTT transkriptifail läbi ja koosta sellest algajasõbralik õppematerjal eesti keeles. Salvesta `docs/transcript-materials` kausta.

## Steps

### 1. Küsi kolm andmepunkti

Küsi kasutajalt korraga kolm asja:

1. **Video link** — URL, kus videot vaadata saab
2. **Video parool** — parool video avamiseks (kui on)
3. **Transkriptifaili rada** — nt `docs/transcripts/GMT20260414-062427_v-1.vtt`

Oota vastust enne kui jätkad.

### 2. Tuleta failinime

Parsi failinimi transkriptifaili nimest:

- Failinime formaat: `GMT<AASTA><KUU><PÄEV>-<KELLAAEG>_v-<NR>.vtt`
- Näide: `GMT20260414-062427_v-1.vtt` → kuupäev `2026-04-14`, video number `1`
- Uue faili nimi: `<AASTA>-<KUU>-<PÄEV>-video-<NR>.md`
- Näide: `2026-04-14-video-1.md`

### 3. Loe transkriptifail

Loe VTT fail läbi (Read tööriist). VTT formaadis on read järgmiselt:

```
00:01:23.456 --> 00:01:27.890
See on räägitud tekst siin.
```

Ignoreeri formaadimärgendeid (`<c>`, `</c>`, `<00:01:23.456>` jms) — loe ainult puhast teksti ja ajatempleid.

### 4. Analüüsi sisu ja tuvasta teemad

Loe kogu transkript läbi ja:

- Tuvasta peamised teemad ja alateemad, millest räägitakse
- Märgi üles iga teema **algusaeg** (tunni:minuti:sekundi täpsusega, nt `1:23:45`)
- **Tuvasta task-numbrite mainimised** (nt FEB-4, BEB-7, OPETAJA-3 vms) — märgi nende täpne algusaeg
- Mõista teemade loogiline järjekord ja seosed

### 5. Otsi koodibaasist näiteid

Otsi `src/` kaustast konkreetseid koodinäiteid, mis seonduvad transkriptis mainitud teemadega:

- Kui videos räägitakse Vue komponentidest → otsi `.vue` faile
- Kui videos räägitakse Pinia store'idest → otsi `src/stores/`
- Kui videos räägitakse marsruutimisest → otsi `src/router/`
- Kasuta Grep ja Glob tööriistu

### 6. Koosta õppematerjal

#### Struktuuri nõuded:

**a) Päis dokumendi alguses** — video info:

```markdown
## Video info

| | |
|---|---|
| **Video** | [Vaata videot](https://link-siia) |
| **Parool** | `parool-siia` |
| **Transkript** | [GMT20260414-062427_v-1.vtt](../transcripts/GMT20260414-062427_v-1.vtt) |
```

**b) Sisukord päise järel** — hüperlingid + ajatemplid:

```markdown
## Sisukord

- [Teema 1 – Lühikirjeldus](#teema-1) — `0:05:30`
- [Teema 2 – Lühikirjeldus](#teema-2) — `0:42:10`
- [FEB-4 – Taski algus](#feb-4) — `1:03:22`  ← task-numbrid kindlasti märkida!
```

Hüperlingid kasutavad Markdown ankruid: `[Tekst](#ankur)` kus ankur on pealkiri väiketähtedes, tühikud asendatud `-`ga.

**b) Iga teema jaoks eraldi sektsioon:**

```markdown
## Teema nimi — `0:05:30` {#teema-1}

Lühike sissejuhatus — mis see on, miks oluline.

### Selgitus

...algajasõbralik selgitus...

### Skeem (kui sobib)

\`\`\`
ASCII skeem siia
\`\`\`

### Koodinäide

\`\`\`js
// Kommenteeritud näide
\`\`\`

### Päriselu analoogia

...võrdlus tuntud asjaga...
```

#### Stiilireeglid:

- Eesti keeles (tehnilised terminid inglise keeles, aga selgita neid)
- Lihtsad laused, ei eelda eelteadmisi
- Iga uus mõiste selgitatakse enne kasutamist
- Konkreetsed näited, mitte abstraktsed kirjeldused
- Skeemid ASCII-ga, kus aitavad mõistmisele kaasa
- Koodinäited — lihtsad, kommenteeritud, reaalsed (eelistatud koodibaasist)

### 7. Salvesta fail

Salvesta fail `docs/transcript-materials/<failinime>.md`.

Kui `docs/transcript-materials/` kaust puudub, loo see.

### 8. Teavita kasutajat

Näita kasutajale:
- Faili asukoht
- Lühike kokkuvõte (2-3 lauset), milliseid teemasid materjal katab
- Mitu teemat ja ajatemplit lisati sisukorda
- Küsi, kas midagi jäi puudu või vajab täiendamist