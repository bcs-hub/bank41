# `/security-review`

`/security-review` analüüsib aktiivse haru commitimata Git muudatusi ja otsib
turvariske, näiteks injection'i, autentimise vigu ning tundlike andmete lekkimist.

Oluline teada:

- käsk teeb ülevaatuse, kuid ei tõesta, et kood on täielikult turvaline;
- kontrolli iga leidu koodi ja projekti kontekstis;
- käsitle eriti hoolikalt autentimist, õigusi, kasutaja sisendit ja paroole;
- pärast parandusi käivita testid ja vajaduse korral turvaülevaatus uuesti.
