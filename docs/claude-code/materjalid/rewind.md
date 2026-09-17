# `/rewind`

Avab varasemate checkpoint'ide menüü.

```text
/rewind
```

Sama menüü avaneb, kui vajutad kaks korda `Esc`.

Vali varasem prompt ja otsusta, mida taastada:

- kood ja vestlus;
- ainult vestlus;
- ainult kood.

Claude Code loob checkpoint'e automaatselt ja jälgib Claude'i failimuudatusi.

> Bash-käskude, käsitsi tehtud muudatuste ja väliste süsteemide tegevusi `/rewind` tagasi ei võta.

Checkpoint on kiire lokaalne undo, mitte Git-versioonihalduse asendus.
