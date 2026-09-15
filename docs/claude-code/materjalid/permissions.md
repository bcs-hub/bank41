# `/permissions`

Käivita:

```text
/permissions
```

Siin saad vaadata ja muuta, mida Claude Code tohib teha.

- **Allow** — tegevus on lubatud ilma kinnitust küsimata.
- **Ask** — Claude küsib enne tegevust kinnitust.
- **Deny** — tegevus on keelatud.

Näiteks võib lubada `git fetch`, kuid keelata `git push` käsu.

```text
Allow: Bash(git fetch *)
Deny:  Bash(git push *)
```

Keelav reegel on alati lubavast reeglist tugevam. Anna Claude'ile ainult ülesande jaoks vajalikud õigused.

Kui Claude küsib käsu käivitamiseks luba ja valid **Yes, don't ask again**, salvestatakse vastav käsureegel projekti jaoks. Failimuudatuste luba võib kehtida ainult sessiooni lõpuni.

`/permissions` näitab, millisest failist iga reegel pärineb.

## Demo

Enne demo ava [`.claude/settings.local.json`](../../../.claude/settings.local.json), et näha faili algset sisu.

Palu Claude'il:

```text
käivita git fetch origin
```

Kui Claude küsib luba, vajuta `Ctrl+E`. See kuvab või peidab selgituse, miks käsule luba küsitakse.

Vali **Yes, don't ask again** ja vaata, kuidas `.claude/settings.local.json` faili `permissions` alla lisandus uus reegel. Seejärel ava uuesti `/permissions` ja vaata sama reeglit seal. Käivita sama käsk uuesti — Claude ei peaks enam luba küsima.
