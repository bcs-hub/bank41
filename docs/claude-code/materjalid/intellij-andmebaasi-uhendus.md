# IntelliJ Database'i ühendus

IntelliJ paremal serval asuvas **Database** paneelis peab olema PostgreSQL-i ühendus
andmebaasiga `vali_it`.

Kui ühendust veel ei ole:

1. Ava **Database** ja vali **+ → Data Source from URL**.
2. Sisesta Vali-IT installeri raportist andmeallika URL, kasutajanimi ja parool.
3. Kontrolli, et draiveriks on valitud **PostgreSQL**. Vajaduse korral laadi puuduvad
   draiverifailid alla.
4. Vajuta **Test Connection** ja kontrolli, et kuvatakse roheline eduteade.
5. Pane ühenduse nimeks `database` ja salvesta see.

Näidis-URL:

```text
jdbc:postgresql://localhost:5432/vali_it
```

| Väli | Näidisväärtus |
| --- | --- |
| Username | `postgres` |
| Password | `student123` |

Kasuta näidisandmete asemel enda Vali-IT raportis olevaid väärtusi.

Pikem piltidega juhend: [Andmebaasi ühendamine IntelliJ-s](../../tutorials/026-Andmebaasi-uhendamine-IntelliJ.pdf).
