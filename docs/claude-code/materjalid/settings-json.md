# `settings.json`

`settings.json` failides seadistatakse Claude Code'i käitumist. Seal võivad olla näiteks õigused, hook'id, keskkonnamuutujad ja pluginad.

Seadistused võivad asuda:

- `.claude/settings.local.json` — sinule selles projektis, Git ignoreerib seda;
- `.claude/settings.json` — kogu meeskonnale, commit'itakse reposse;
- `~/.claude/settings.json` — sinule kõikides projektides.

> **Asukoht:** `~` tähendab sinu WSL-i kodukausta, näiteks `/home/mart`.<br>
> Windows Exploreris sisesta aadressiribale `\\wsl$\`, vali Ubuntu ning ava `home → kasutaja → .claude`.

Lokaalne seadistus on kõige kõrgema prioriteediga.

```text
settings.local.json       kõrgem prioriteet
        ↓
settings.json
        ↓
~/.claude/settings.json   madalam prioriteet
```

Reeglid liidetakse; keeld on alati loast tugevam.

Fail peab olema korrektne JSON. `/permissions` näitab, millisest seadistusfailist iga õiguste reegel pärineb.
