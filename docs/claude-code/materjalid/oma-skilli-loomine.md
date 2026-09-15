# Oma skilli loomine

Alusta korduvast vajadusest, mitte failist. Mõtle läbi:

- mida skill peab tegema;
- millal Claude peaks seda kasutama;
- milliseid samme tuleb alati järgida;
- kas vaja on näiteid, viiteid või skripte.

## Loomise töövoog

1. Pane skillile lühike tegevust kirjeldav nimi.
2. Loo skilli kaust ja `SKILL.md`.
3. Kirjelda `description` väljal, mida skill teeb ja millal seda kasutada.
4. Kirjuta tegevused konkreetses järjekorras.
5. Lisa ainult vajalikud abifailid.
6. Katseta skilli päris ülesandega ja täpsusta juhiseid.

Claude Code'il võib paluda skilli valmis luua:

```text
Loo projekti skill `kontrolli-muudatusi`.

Skill vaatab üle Git diffi, otsib võimalikke vigu ja teeb
tulemustest lühikese kokkuvõtte. Kasuta seda, kui kasutaja
palub tehtud muudatused üle vaadata.
```

## Uue skilli kasutusele võtmine

Uue või muudetud skilli sisselugemiseks käivita:

```text
/reload-skills
/reload-plugins
```

Või taaskäivita Claude Code.

## Katsetamine

Proovi mõlemat kasutusviisi:

```text
Vaata minu tehtud muudatused üle.
/kontrolli-muudatusi
```

Kontrolli, kas Claude valib skilli sobiva prompti korral ise ja järgib selle samme. Kui skill ei käivitu, täpsusta `description` välja. Kui tulemus pole õige, täpsusta juhiseid.

Hoia `SKILL.md` lühike: lisa ainult see, mida Claude ülesande täitmiseks päriselt vajab.
