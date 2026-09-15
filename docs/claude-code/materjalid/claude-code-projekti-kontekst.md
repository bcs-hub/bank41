# Kuidas Claude Code projekti konteksti kasutab

Claude Code ei loe kogu projekti korraga sisse. Ta kogub töö käigus vajalikku konteksti:

- vestlusest;
- loetud failidest;
- `CLAUDE.md` juhistest;
- käskude ja tööriistade tulemustest.

Projekti juurkausta `CLAUDE.md` laaditakse sessiooni alguses. Alamkausta juhised laaditakse siis, kui Claude hakkab selles kaustas töötama.

Näiteks backend-faili lugemisel laaditakse ka `backend/CLAUDE.md`.

Anna promptis vajalikud failid ja taust, et Claude kasutaks ülesande jaoks õiget konteksti.
