# Git Prune – Algajatele

## Mis see on?

`git prune` on käsk, mis **kustutab Git repositooriumist "hüljatud" (unreachable) objektid** — need on failide sisud, commit'id ja kaustapuud, millele enam miski ei viita (ei mingi branch, ei tag, ei miski muu).

Lihtsustatult: Git ei kustuta kunagi ise midagi kohe ära. Kui sa teed `git commit --amend` või `git rebase` või kustutad haru, siis vanad commit'id ei kao — nad jäävad kettale "kummitustena" seniks, kuni keegi käsitsi koristab. `git prune` ongi see koristaja.

### Päriselu analoogia: prügikast arvutis

Kui sa kustutad arvutis faili, ei kao see kohe päriselt — see läheb prügikasti (Recycle Bin / Trash). Fail on ikka kettal, lihtsalt "nähtamatu". Alles siis, kui sa **tühjendad prügikasti**, kaob fail päriselt.

Git töötab sarnaselt:
- Kui kustutad haru või teed rebase'i, "vanad" commit'id ei kao — nad jäävad `.git` kausta sisse, lihtsalt ei ole enam ühegi haru küljes.
- `git prune` = prügikasti tühjendamine.

```
Enne prune'i:                    Pärast prune'i:

  main ──●──●──●                   main ──●──●──●
              │                                
              └──●──●  (vana       (vana branch'i commit'id
                  haru,              on kustutatud, kuna
                  kustutatud)        miski neile ei viita)
```

## Miks see oluline on?

`.git` kaust kasvab aja jooksul, sest Git säilitab **kõik** objektid, isegi need, mida sa enam ei vaja:

- Kustutatud harude commit'id
- `git rebase` või `git commit --amend` käigus tekkinud "vanad" versioonid
- `git reset --hard` käigus mahajäänud commit'id

Need objektid võtavad kettaruumi, kuigi keegi neid enam ei kasuta. `git prune` aitab repositooriumi puhtana ja väiksemana hoida.

## Kuidas kasutada

### Lihtne kasutus

```bash
git prune
```

See kustutab kõik hüljatud objektid koheselt ja jäädavalt.

### Kõigepealt vaata, mida kustutataks (soovitatav!)

```bash
git prune --dry-run
```

See näitab, mida `git prune` kustutaks, ilma et päriselt midagi kustutaks. **Alati tasub see enne käivitada**, sest kustutamine on pöördumatu.

### Näide väljundist

```bash
$ git prune --dry-run
Kustutataks objekt: a1b2c3d4e5f6...
Kustutataks objekt: 9f8e7d6c5b4a...
```

## Ettevaatust! ⚠️

`git prune` on **pöördumatu** käsk. Kui kustutad objekti, mida oleks veel vaja läinud (nt tahtsid mõne kustutatud haru commit'i taastada `git reflog` abil), siis pärast `prune`'i see enam ei õnnestu.

Seetõttu:
- Kasuta alati enne `--dry-run` lippu
- Tavalises igapäevases töös **ei pea** sa `git prune`'i käsitsi käivitama — Git teeb seda ise automaatselt

## Kuidas Git seda ise juba teeb

Enamikul juhtudel ei pea sa `git prune`'i kunagi käsitsi jooksutama, sest:

1. **`git gc`** (garbage collection) käivitub Git'i poolt automaatselt aeg-ajalt (nt pärast `git commit`, `git merge` jm) ja teeb `prune`'i sarnast koristustööd, kuid targemalt — arvestab ka `reflog`'i ja varutähtaegu.
2. **`git fetch --prune`** (või `git pull --prune`) on hoopis teine asi — see puhastab **kaugharude viiteid** (remote-tracking branches), mis on serveris kustutatud, aga sinu lokaalses koopias veel "kummitavad".

### Segane, aga oluline erinevus

| Käsk | Mida teeb |
|---|---|
| `git prune` | Kustutab lokaalsed hüljatud **objektid** (commit'id, blob'id) kettalt |
| `git gc` | Teeb üldist "prügikoristust" — pakib objektid kokku, kutsub ka `prune`'i, aga ohutumalt |
| `git fetch --prune` | Eemaldab lokaalsed viited **kaugharudele**, mis serveris enam ei eksisteeri |
| `git branch -d` | Kustutab ühe lokaalse haru (aga commit'id ise jäävad kettale, kuni prune) |

## Kokkuvõte

| Mõiste | Selgitus |
|---|---|
| `git prune` | Kustutab jäädavalt commit'id/objektid, millele miski enam ei viita |
| Unreachable objekt | Objekt, millele ei viita ükski branch ega tag |
| `--dry-run` | Näitab, mida kustutataks, ilma päriselt kustutamata |
| `git gc` | Automaatne, turvalisem koristusprotsess, mis sisaldab ka prune'i loogikat |
| `git fetch --prune` | Puhastab kaugharude viited, mitte objekte |
| Pöördumatus | `git prune` järel ei saa kustutatud objekte enam taastada |

## Järgmised sammud

Kui see teema oli huvitav, tasub edasi õppida:
- `git reflog` — kuidas "kadunud" commit'e üles leida (enne kui prune need kustutab)
- `git gc` — automaatne koristus ja selle seaded
- `git branch -d` vs `git branch -D` — haru kustutamise erinevused
- `.git` kausta struktuur — kuidas Git objekte tegelikult kettal hoiab (objects, refs, HEAD)
