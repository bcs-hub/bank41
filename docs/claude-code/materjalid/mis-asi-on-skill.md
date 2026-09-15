# Mis asi on skill

Skill on Claude Code'ile lisatud korduvkasutatav teadmine või töövõte.

Skill võib anda Claude'ile:

- taustateadmisi ja projekti kokkuleppeid;
- samm-sammulise töövoo;
- näiteid ja malle;
- abifaile või skripte.

Claude näeb saadaolevate skillide nimesid ja kirjeldusi. Kui ülesanne sobib skilli kirjeldusega, võib Claude skilli ise valida ning selle täielikud juhised laadida.

Kasutaja saab skilli ka teadlikult käivitada:

```text
/skilli-nimi
```

Skillid võivad olla Claude Code'iga kaasas, kasutaja enda loodud, projekti lisatud või pluginaga paigaldatud.

Automaatse kasutamise saab keelata nende skillide puhul, mille käivitamise üle peab otsustama kasutaja.

## Millal skill sisse loetakse

Sessiooni alguses leiab Claude Code saadaolevad skillid ning lisab nende nimed ja kirjeldused konteksti.

Kogu `SKILL.md` sisu laaditakse alles siis, kui Claude valib skilli ülesande põhjal ise või kasutaja käivitab selle `/skilli-nimi` käsuga. Abifailid loetakse vajaduse korral eraldi.

Olemasolevasse skillide kausta lisatud või seal muudetud skillid tuvastatakse tavaliselt ka käimasoleva sessiooni ajal. Kui `.claude/skills` kausta sessiooni alguses veel polnud, tuleb pärast selle loomist Claude Code taaskäivitada.
