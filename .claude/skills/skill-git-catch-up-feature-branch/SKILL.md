---
name: skill-git-catch-up-feature-branch
description: Õpilase arenduse haru viimine õpetaja haru koodiga samale tasemele — rollback, fetch, otsi õpetaja haru, tee uus versiooniga haru
---

# Catch Up Feature Branch

Õpilase arenduse haru viimine õpetaja haru seisuga samale tasemele.

## Sammud

### 1. Tuvasta hetke haru ja salvesta nimi
- Käivita `git branch --show-current` — see on õpilase arenduse haru (nt `MARI-FEB-7`, `JUKU-BEB-7-v2`).
- Salvesta see nimi — hiljem on vaja uue haru nimetamiseks.
- Tuvasta harult Jira numbri liide (nt `FEB-7` või `BEB-7`) — kasuta seda õpetaja haru otsimiseks.

### 2. Tühista lahtised muudatused ja kustuta versioonimata failid
- Käivita `git status` ja näita kasutajale, mis on muutunud.
- **Rollback:** `git checkout -- .` (tühista kõik jälgitud failide muudatused)
- **Versioonimata failide kustutamine:** `git clean -fd` (kustuta jälgimata failid ja kaustad)
- Kinnita kasutajale, et töökataloog on puhas.

### 3. Fetch
- Käivita `git fetch --all` — laadi alla kõik remote harud.

### 4. Otsi õpetaja remote arenduse haru
- Käivita `git branch -r` ja filtreeri harud, mis algavad `origin/OPETAJA`.
- Otsi leitud harude seast see, mille Jira number klapib õpilase haruga (nt `OPETAJA-FEB-7`).
- **Kui leiad täpselt ühe sobiva haru** — jätka automaatselt.
- **Kui leiad mitu sobivat haru** — küsi kasutajalt, millist haru ta soovib kasutada, ja oota vastust.
- **Kui ei leia ühtegi sobivat haru** — teavita kasutajat ja küsi, mida teha.

### 5. Loo uus haru õpetaja harult
- Määra uue haru nimi: võta õpilase vana haru nimi ja lisa versiooni number.
  - Kui vanal harunimel pole versiooni numbrit (nt `MARI-FEB-7`) → uus nimi on `MARI-FEB-7-v2`
  - Kui vanal harunimel on versiooni number (nt `MARI-FEB-7-v2`) → suurenda numbrit (nt `MARI-FEB-7-v3`)
- Käivita: `git checkout -b <uus-haru-nimi> origin/<opetaja-haru-nimi>`
- Kinnita kasutajale, et uus haru on loodud ja checkout on tehtud.

### 6. Kokkuvõte
- Teata kasutajale:
  - Kustutati muudatused vanalt harult `<vana-haru-nimi>`
  - Uus haru `<uus-haru-nimi>` on loodud õpetaja haru `<opetaja-haru-nimi>` pealt
  - Hetkel ollakse harule `<uus-haru-nimi>`