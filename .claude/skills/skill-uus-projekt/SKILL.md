---
name: skill-uus-projekt
description: Aita kasutajal olemasolevast toorikprojektist kiirelt luua uus projekt (uus kausta nimi, backend package, frontend package name), et see saaks GitHubi lisada. Kasuta, kui kasutaja tahab toorikust uut projekti teha, mainib "uus projekt", "projekti nime vahetus" vms.
---

Eesmärk on see, et kasutaja saab olemasolevast toorikprojektist kiirelt teha ühe uue projekti, mille saab siis GitHubi lisada.

## 1. Projekti nimi

Küsi kasutajalt uue projekti nimi. Suuna kasutajat, et see võiks olla kas inglisekeelne sõna/väljend või brändi/meeskonna nimi.

See nimi läheb root kausta nimeks — vaheta ära olemasoleva projekti kausta nimi (hetkel `bank41`). Kausta nimeks kasuta sisendit, teisendades selle lower kebab-case nimetamise konventsiooni järgi (nt "Minu Pank" → `minu-pank`).

## 2. Backend package name

Paku kasutajale mõni backend package name variant, hoides seda lühikesena (nt praeguse `ee.bcs.bank` eeskujul, tuletatuna uue projekti nimest). Küsi kasutajalt kinnitust valiku kohta.

Pärast kinnitust vaheta package nimi läbivalt ära:
- `backend/src/main/java/ee/bcs/bank` kaustastruktuur (ja kõik selle alamkaustad) tuleb ümber tõsta uude package'i vastavasse kaustastruktuuri
- kõikides Java failides (`main` ja `test`) `package ee.bcs.bank...` deklaratsioonid ja vastavad `import ee.bcs.bank...` read
- `backend/build.gradle` — `group = 'ee.bcs'` väärtus

Kontrolli pärast muudatust, et backend jätkuvalt kompileerub (nt `./gradlew compileJava compileTestJava` backend kaustas).

## 3. Frontend package name

Paku sarnaselt uus `name` väärtus faili `frontend/package.json` jaoks (loe hetke väärtus otse failist — see ei pruugi kattuda root-kausta nimega), tuletatuna uue projekti nimest. Nimi peab olema lower-kebab-case, sufiksiga `-front` (nt "Minu Pank" → `minu-pank-front`). Küsi kinnitust ja seejärel uuenda `frontend/package.json` väli `name`.

## 4. GitHubi lisamine

Toorikprojektil ei ole `.git` kausta (see on toorikust eemaldatud), seega projektil pole veel git repositooriumi ega ühtegi commiti. Kui eelnevad sammud on tehtud, juhenda kasutajat, kuidas ta saab projekti IntelliJ Ultimate abil GitHubi lisada:

- Projekti tuleb lisada GitHubi **avaliku (public)** repositooriumina.
- Seda teeb ainult **üks** õpilane rühmast (mitte igaüks eraldi).
- Menüüst **Git → GitHub → Share Project on GitHub**. See samm teeb kõik korraga ära: initsialiseerib kohaliku git repositooriumi (eraldi `git init` pole vaja teha), avab commit-akna esimese (initial) commiti jaoks ning pärast kinnitamist loob GitHubis uue repo ja pushib commiti sinna — seega eraldi initial commit käsku ette teha ei ole vaja.
- Pärast repositooriumi loomist tuleb ülejäänud rühmaliikmed lisada **collaborators** alla, et neil oleks õigus repositooriumisse kirjutada.

Selgita mõlemat sammu (GitHubile lisamine IntelliJ Ultimate kaudu ja collaboratorite lisamine GitHubi repo seadetes) konkreetsete klikkide/menüükäikude tasemel, kuna tegu on õpilastega, kes ei pruugi seda varem teinud olla.

**Ära ise käivita git/GitHub käske (nt `git remote add`, `gh repo create`, push) ega tee ise faili- või kaustamuudatusi enne, kui kasutaja on kõik nimed (projekti nimi, backend package, frontend package name) kinnitanud.** Kausta- ja failimuudatused (sammud 1–3) tohib teha alles pärast vastavat kinnitust, GitHubi lisamine (samm 4) on kasutaja enda käsitsi tehtav toiming, mida ainult juhendad.

Suhtle kasutajaga eesti keeles.
