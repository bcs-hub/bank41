# Näide Claude Code'i ühendamisest välise teenusega

Lisame Atlassian Rovo MCP serveri. Käivita tavalises terminalis:

```bash
claude mcp add --transport http atlassian https://mcp.atlassian.com/v1/mcp/authv2
```

Seejärel ava Claude Code ja käivita:

```text
/mcp
```

Vali Atlassiani server ning logi OAuthi kaudu oma Atlassiani kontoga sisse. Kui brauser ei avane automaatselt, kopeeri kuvatud link brauserisse.

Pärast ühendamist saab Claude'ile anda näiteks ülesande:

```text
Otsi Jirast mulle määratud avatud ülesanded ja tee neist lühike kokkuvõte.
```

Claude valib sobiva Atlassiani MCP tööriista ning küsib selle kasutamiseks vajaduse korral luba.
