# Skilli ja tavalise prompti erinevus

## Tavaline prompt

Prompt on Claude'ile praeguses vestluses antud ülesanne või juhis.

Kasuta prompti, kui ülesanne on ühekordne või sõltub palju konkreetsest olukorrast.

## Skill

Skill kirjeldab korduvkasutatavat teadmist või tööviisi.

Kasuta skilli, kui sama tegevust tehakse korduvalt või soovid, et Claude järgiks alati sama töövoogu.

Näiteks `/skill-catch-up-teacher` säilitab osaleja senise töö ja aitab tal õpetaja harult jätkata. Tavalise promptina peaksime sama töövoo iga kord uuesti kirjeldama.

```text
Ühekordne ülesanne       → prompt
Korduv ja kindla sammuga → skill
```

Skill ei asenda prompti: skilli käivitamisel saab anda kaasa konkreetse olukorra või eesmärgi.
