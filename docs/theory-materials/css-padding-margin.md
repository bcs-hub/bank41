# Padding ja Margin – Algajatele

## Mis see on?

Iga HTML element veebilehel on nagu **kast** — sellel kastil on sisu, ja selle sisu ümber saab lisada kahte tüüpi "tühja ruumi":

- **`padding`** (polstrid, sisemine vahe) — ruum **sisu ja kasti serva vahel**, kasti *sees*.
- **`margin`** (välisvahe) — ruum **kasti ja järgmise elemendi vahel**, kasti *väljas*.

Need on kaks kõige levinumat CSS-i mõistet, mida algajad omavahel segamini ajavad — sest mõlemad tekitavad "tühja ruumi", aga **täiesti erinevas kohas**.

### Päriselu analoogia: pildiraam seinal

Kujuta ette pilti raamis, mis ripub seinal.

- **Padding** on **paspartuu** — valge äärekartong pildi ja raami vahel. See on raami *sees* — muudab, kui palju ruumi on pildi ja raami serva vahel.
- **Margin** on **vahe raami ja järgmise pildi vahel** seinal. See on raami *väljas* — muudab, kui palju vaba seina jääb kahe pildi vahele.

Kui suurendad paspartuud (padding), jääb raam suuremaks, aga pilt ise ei muutu. Kui suurendad vahet piltide vahel (margin), lükkad pildid seinal kaugemale, aga raami enda suurus ei muutu.

## Box model — kast kasti sees

Iga HTML element koosneb neljast kihist, väljastpoolt sissepoole:

```
┌─────────────── margin (läbipaistev) ───────────────┐
│  ┌───────────── border (piirjoon) ───────────────┐  │
│  │  ┌─────────── padding (täidetud värviga) ───┐  │  │
│  │  │                                            │  │  │
│  │  │           SISU (tekst, pilt)              │  │  │
│  │  │                                            │  │  │
│  │  └────────────────────────────────────────────┘  │  │
│  └──────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────┘
```

Järjekord väljastpoolt sissepoole: **margin → border → padding → sisu**.

- **Margin** on alati läbipaistev — see on lihtsalt tühi ala, kust "paistab läbi" see, mis on elemendi taga (nt lehe taust).
- **Padding** on osa elemendist endast — kui elemendil on taustavärv, laieneb see värv ka padding'i alale.

See vahe on kõige lihtsam viis padding'it ja margin'it visuaalselt eristada: **kui annad elemendile taustavärvi, siis padding jääb selle värvi sisse, aga margin jääb värvist väljapoole.**

## Koodinäide

```html
<div class="kast">Tere, maailm!</div>
```

```css
.kast {
    background-color: lightblue;   /* taustavärv, et näha, kus kast lõpeb */
    padding: 20px;                  /* 20px tühja ruumi SISU ja kasti serva vahel */
    margin: 40px;                   /* 40px tühja ruumi kasti ja järgmise elemendi vahel */
    border: 2px solid darkblue;     /* piirjoon, mis eristab padding'ut margin'ist */
}
```

Tulemus: sisu ("Tere, maailm!") ei puutu kokku kasti äärega — selle ümber on 20px sinist tausta (padding). Väljaspool sinist tausta, enne kui algab järgmine element lehel, on 40px täiesti tühja, värvituta ruumi (margin).

### Kõik neli külge korraga

Padding'it ja margin'it saab määrata ka iga külje jaoks eraldi:

```css
.kast {
    padding-top: 10px;
    padding-right: 20px;
    padding-bottom: 10px;
    padding-left: 20px;
}
```

Lühem viis — kirjutada kõik neli väärtust ühele reale (järjekorras **üles, paremale, alla, vasakule** — kellaosuti liikumise suunas, alates ülevalt):

```css
.kast {
    padding: 10px 20px 10px 20px;
}
```

Kui üles/alla ja vasak/parem on samad, saab lühendada veelgi:

```css
.kast {
    padding: 10px 20px;   /* 10px üles ja alla, 20px vasakule ja paremale */
}
```

## Margin collapse — üllatus, mida tasub teada

Erinevalt padding'ust võivad **kaks vertikaalset margin'it "kokku sulada" (collapse)**, kui nad puutuvad kokku. Kui ühel elemendil on `margin-bottom: 30px` ja järgmisel elemendil `margin-top: 20px`, ei liida CSS neid kokku (50px) — vahe on ainult **suurem neist kahest** (30px).

```
Element A
  margin-bottom: 30px  ┐
                        ├── vahe on 30px, MITTE 50px!
Element B               │
  margin-top: 20px      ┘
```

See on üks levinumaid asju, mis algajaid segadusse ajab — padding'i puhul sellist "sulandumist" ei toimu, iga elemendi padding käitub täiesti iseseisvalt.

## Padding vs Margin — kõrvuti

| | Padding | Margin |
|---|---|---|
| Asukoht | Kasti **sees**, sisu ja serva vahel | Kasti **väljas**, kasti ja teiste elementide vahel |
| Kas taustavärv katab selle? | **Jah** — padding on osa elemendist | **Ei** — margin on alati läbipaistev |
| Mõjutab elemendi enda suurust | Jah — suurendab kasti "täidetust" seest | Ei — mõjutab ainult vahemaad teiste elementideni |
| Võib "kokku sulada" naaberelementidega | Ei | Jah (vertikaalsete marginite puhul) |
| Päriselu analoog | Paspartuu pildi ja raami vahel | Vahe raami ja järgmise pildi vahel seinal |

## Kokkuvõte

| Mõiste | Selgitus |
|---|---|
| `padding` | Tühi ruum sisu ja elemendi serva vahel, elemendi *sees* |
| `margin` | Tühi ruum elemendi ja naaberelementide vahel, elemendi *väljas* |
| Box model | Kihtide järjekord: margin → border → padding → sisu |
| Taustavärv | Katab padding'u, aga mitte kunagi margin'it |
| Margin collapse | Kaks vertikaalset marginit ei liideta, vaid võetakse suurem neist |
| Lühivorm (`padding: 10px 20px`) | Järjekord: üles, paremale, alla, vasakule (kellaosuti suund) |

## Järgmised sammud

Kui see teema oli huvitav, tasub edasi õppida:
- **`box-sizing: border-box`** — kuidas muuta, kas padding ja border lisanduvad elemendi laiusele juurde või arvestatakse selle sisse
- **Flexbox ja Grid** — kaasaegsed viisid elementide paigutamiseks, mis vähendavad vajadust margin'itega "käsitsi" positsioneerida
- **`gap` omadus** — flex/grid konteinerites asendab see tihti margin'i vajaduse elementide vahel
- **`auto` margin** — kuidas tsentreerida elementi horisontaalselt (`margin: 0 auto`)
