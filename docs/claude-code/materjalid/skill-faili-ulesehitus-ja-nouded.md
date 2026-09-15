# Skill-faili ülesehitus ja nõuded

Iga skill asub oma kaustas. Kaustas peab olema täpselt `SKILL.md` nimega fail.

```text
.claude/skills/kontrolli-muudatusi/
├── SKILL.md
├── examples/       valikuline
└── scripts/        valikuline
```

`SKILL.md` koosneb tavaliselt kahest osast:

1. YAML-päis kirjeldab skilli Claude'ile.
2. Markdown-osa sisaldab juhiseid, mida Claude järgib.

```markdown
---
name: kontrolli-muudatusi
description: Kontrollib tehtud muudatusi. Kasuta, kui kasutaja palub oma töö üle vaadata.
---

# Kontrolli muudatusi

1. Vaata Git diffi.
2. Kontrolli võimalikke vigu.
3. Tee tulemustest lühike kokkuvõte.
```

## Olulisemad nõuded

- `SKILL.md` on ainus kohustuslik fail.
- `description` on tugevalt soovituslik: selle järgi otsustab Claude, millal skilli kasutada.
- `name` võib sisaldada väiketähti, numbreid ja sidekriipse. Kui see puudub, kasutatakse kausta nime.
- Hea tava on hoida kausta nimi ja `name` välja väärtus samad, et vältida segadust.
- Kui kasutad YAML-päist, peab see asuma faili alguses `---` märkide vahel.
- Juhised kirjuta konkreetselt ja tegevuste järjekorras.
- Näited, mallid, viited ja skriptid võivad olla eraldi abifailides.

## Claude'i automaatse käivitamise keelamine

Vaikimisi võivad skilli käivitada nii kasutaja kui ka Claude. Claude'i enda algatusel käivitamise keelamiseks lisa YAML-päisesse:

```yaml
---
name: kontrolli-muudatusi
description: Kontrollib tehtud muudatusi. Kasuta, kui kasutaja palub oma töö üle vaadata.
disable-model-invocation: true
---
```

Kui `disable-model-invocation` väärtus on `true`, saab skilli käivitada ainult kasutaja `/skilli-nimi` käsuga.

Skilli käsk tuleb kausta nimest:

```text
/kontrolli-muudatusi
```
