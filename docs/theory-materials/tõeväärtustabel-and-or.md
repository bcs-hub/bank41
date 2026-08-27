# Tõeväärtustabel, AND ja OR – Algajatele

## Mis see on?

Programmeerimises tuleb pidevalt teha **otsuseid**: kas näidata kasutajale viga, kas lubada tal edasi minna, kas kaks tingimust kehtivad korraga. Selliste otsuste tegemiseks kasutatakse **loogikaoperaatoreid** — kõige levinumad neist on **AND** (ja) ning **OR** (või).

Need operaatorid töötavad **tõeväärtustega** (`boolean`) — väärtustega, mis saavad olla ainult **`true`** (tõene) või **`false`** (väär). Ei ole kolmandat varianti — kas midagi kehtib või ei kehti.

**Tõeväärtustabel** on lihtsalt tabel, mis näitab **kõiki võimalikke sisendite kombinatsioone** ja mis tulemuse iga kombinatsioon annab. See on nagu retsept: "kui sisse tuleb see ja see, siis tuleb välja see."

### Päriselu analoogia: uks kahe lukuga

Kujuta ette ust, millel on **kaks lukku**: alumine ja ülemine.

- Kui ust saab avada ainult siis, **kui mõlemad lukud on lahti** — see on **AND** loogika.
- Kui ust saab avada siis, **kui vähemalt üks lukkudest on lahti** — see on **OR** loogika.

Sama uks, aga täiesti erinev käitumine sõltuvalt sellest, kas kasutad AND-i või OR-i.

## Tõeväärtused: true ja false

Enne kui vaatame AND-i ja OR-it, tuleb selgeks teha, mis on `true` ja `false`.

```javascript
let onSooja = true;   // väide on tõene
let sajab = false;    // väide on väär
```

Tõeväärtus tekib tavaliselt **võrdluse** tulemusena:

```javascript
let vanus = 20;

console.log(vanus > 18);   // true, sest 20 on suurem kui 18
console.log(vanus === 30); // false, sest 20 ei ole 30
```

| Võrdlusmärk | Tähendus |
|---|---|
| `===` | Kas väärtused on täpselt võrdsed |
| `!==` | Kas väärtused EI ole võrdsed |
| `>` | Suurem kui |
| `<` | Väiksem kui |
| `>=` | Suurem või võrdne |
| `<=` | Väiksem või võrdne |

## AND (`&&`) — mõlemad peavad kehtima

**AND** operaator (JavaScriptis `&&`, Javas samuti `&&`) tagastab `true` ainult siis, **kui mõlemad pooled on tõesed**. Kui kas või üks pool on väär, on kogu tulemus `false`.

### Tõeväärtustabel: AND

| A | B | A AND B |
|---|---|---|
| true | true | **true** |
| true | false | false |
| false | true | false |
| false | false | false |

Ainult **üks rida neljast** annab tõese tulemuse — see, kus mõlemad on tõesed.

### Skeem

```
A: true ──┐
          ├──[AND]──> true  (ainult siis, kui mõlemad "true")
B: true ──┘

A: true ──┐
          ├──[AND]──> false (kuna B on false)
B: false ─┘
```

### Koodinäide

```javascript
let vanus = 20;
let onLuba = true;

if (vanus >= 18 && onLuba === true) {
    console.log("Sisenemine lubatud");
} else {
    console.log("Sisenemine keelatud");
}
// Väljund: "Sisenemine lubatud"
// Mõlemad tingimused on tõesed, seega AND annab true
```

Kui kas või üks tingimustest ei kehti (nt vanus on 15), kukub kogu tingimus läbi:

```javascript
let vanus = 15;
let onLuba = true;

if (vanus >= 18 && onLuba === true) {
    console.log("Sisenemine lubatud");
} else {
    console.log("Sisenemine keelatud");
}
// Väljund: "Sisenemine keelatud"
// vanus >= 18 on false, seega AND annab false, olenemata teisest poolest
```

### Päriselu analoogia: AND

Selleks, et saaksid autoga sõita, on vaja **korraga**:
- juhiluba **JA**
- kütust paagis

Kui puudub kas või üks neist, ei sõida auto — mõlemad tingimused peavad kehtima korraga, täpselt nagu AND-i puhul.

## OR (`||`) — piisab, kui üks kehtib

**OR** operaator (JavaScriptis `||`) tagastab `true`, **kui vähemalt üks pool on tõene**. Tulemus on `false` ainult siis, kui **mõlemad** pooled on väärad.

### Tõeväärtustabel: OR

| A | B | A OR B |
|---|---|---|
| true | true | **true** |
| true | false | **true** |
| false | true | **true** |
| false | false | false |

Ainult **üks rida neljast** annab väära tulemuse — see, kus mõlemad on väärad. Ülejäänud kolmel juhul piisab, et kas või üks pool kehtib.

### Skeem

```
A: true ──┐
          ├──[OR]──> true  (piisab, et vähemalt üks on "true")
B: false ─┘

A: false ─┐
          ├──[OR]──> false (kuna mõlemad on false)
B: false ─┘
```

### Koodinäide

```javascript
let onAdmin = false;
let onOmanik = true;

if (onAdmin === true || onOmanik === true) {
    console.log("Ligipääs lubatud");
} else {
    console.log("Ligipääs keelatud");
}
// Väljund: "Ligipääs lubatud"
// onOmanik on true, seega OR annab true, kuigi onAdmin on false
```

### Päriselu analoogia: OR

Kontserdile saab siseneda, kui sul on **kas**:
- pilet **VÕI**
- VIP-kaart

Piisab **ühest** neist kahest — pole vaja mõlemat korraga. See on täpselt OR-i loogika.

## AND vs OR kõrvuti

| | AND (`&&`) | OR (`||`) |
|---|---|---|
| Millal tulemus on `true` | Ainult siis, kui **mõlemad** pooled on tõesed | Kui **vähemalt üks** pool on tõene |
| Millal tulemus on `false` | Kui **kas või üks** pool on väär | Ainult siis, kui **mõlemad** pooled on väärad |
| Nõuab kui palju tõeseid tingimusi neljast kombinatsioonist | 2/2 | 1/2 (piisab ühest) |
| Päriselu näide | Juhiluba **ja** kütus | Pilet **või** VIP-kaart |

## Rohkem kui kaks tingimust

AND-i ja OR-it saab kombineerida ka **rohkem kui kahe** tingimusega:

```javascript
let vanus = 25;
let onLiige = true;
let onKutsutud = false;

// AND: kõik kolm peavad kehtima
if (vanus >= 18 && onLiige && onKutsutud) {
    console.log("Pääseb VIP-alale");
} else {
    console.log("VIP-alale ei pääse");
}
// Väljund: "VIP-alale ei pääse", sest onKutsutud on false

// OR: piisab, kui kas või üks kehtib
if (onLiige || onKutsutud) {
    console.log("Pääseb tavalisele üritusele");
}
// Väljund: "Pääseb tavalisele üritusele", sest onLiige on true
```

Saab ka AND-i ja OR-it **koos kasutada** — siis on kasulik kasutada sulge, et selgelt näidata, mis kuulub kokku:

```javascript
let vanus = 16;
let onVanemaLoal = true;

if (vanus >= 18 || (vanus >= 13 && onVanemaLoal)) {
    console.log("Sisenemine lubatud");
}
// Väljund: "Sisenemine lubatud"
// vanus >= 18 on false, AGA vanus >= 13 JA onVanemaLoal on mõlemad true,
// seega sulgudes olev osa annab true, ja OR annab siis samuti true
```

## Kokkuvõte

| Mõiste | Selgitus |
|---|---|
| Tõeväärtus (boolean) | Väärtus, mis on kas `true` või `false` — kolmandat varianti pole |
| Tõeväärtustabel | Tabel, mis näitab kõiki sisendite kombinatsioone ja nende tulemusi |
| AND (`&&`) | Tõene ainult siis, kui **mõlemad** pooled on tõesed |
| OR (`||`) | Tõene, kui **vähemalt üks** pool on tõene |
| AND päriselus | Juhiluba **ja** kütus paagis (mõlemad vajalikud) |
| OR päriselus | Pilet **või** VIP-kaart (piisab ühest) |
| Sulud (`()`) | Aitavad grupeerida tingimusi, kui AND ja OR on koos kasutusel |

## Järgmised sammud

Kui see teema oli huvitav, tasub edasi õppida:
- **NOT** (`!`) operaator — pöörab tõeväärtuse ümber (`true` muutub `false`-ks ja vastupidi)
- **If/else if/else** laused — kuidas tõeväärtuste põhjal koodi harusid valida
- **Ternary operaator** (`? :`) — lühem viis if/else kirjutamiseks
- **XOR** (exclusive OR) — harvem kasutatav operaator, mis on tõene ainult siis, kui täpselt üks pooltest on tõene (mitte mõlemad)
- **Short-circuit evaluation** — miks JavaScript ei kontrolli teist tingimust, kui esimesest juba piisab tulemuse teadmiseks
