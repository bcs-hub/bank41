# Mis on MCP

MCP ehk **Model Context Protocol** on avatud standard, mille kaudu saab Claude Code'i ühendada väliste tööriistade ja andmeallikatega.

```text
Claude Code → MCP server → väline teenus
```

MCP server võib anda Claude'ile uusi tööriistu, näiteks:

- Jira ülesannete lugemine ja loomine;
- GitHubi pull request'idega töötamine;
- andmebaasist päringute tegemine;
- brauseri juhtimine.

Claude saab valida sobiva MCP tööriista kasutaja ülesande põhjal. Tööriista kasutamist kontrollivad Claude Code'i õigused.
