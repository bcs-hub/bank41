# Projekti kaustade struktuur

```
backend/
├── CLAUDE.md                                   # Backendi juhised Claude Code'ile (Spring Boot, Java konventsioonid)
├── build.gradle                                # Gradle ehitusseadistus, sõltuvused
├── settings.gradle
├── gradlew, gradlew.bat                        # Gradle wrapper käivitusskriptid
├── gradle/
│   └── wrapper/                                # Gradle wrapper failid
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ee.bcs.projektinimi/            # Baaspakett
│   │   │       ├── SomeApplication.java        # Rakenduse põhiklass (entry point)
│   │   │       ├── SomeEnum.java               # Baaspaketi tasemel ühised enumid (nt Status.java, Error.java)
│   │   │       ├── controller/                 # REST kontrollerid
│   │   │       │   └── ressursipakett/         # Kontrolleri alampakett (nt location, city, login)
│   │   │       │       ├── SomeController.java # REST kontroller (nt LocationController.java)
│   │   │       │       └── dto/                # Andmeedastuse objektid (DTO-d) päringute/vastuste jaoks
│   │   │       │           └── SomeDto.java    # DTO klass (nt LocationDto.java)
│   │   │       ├── service/                    # Äriloogika teenused (lame struktuur, üks klass domeeni kohta)
│   │   │       │   └── SomeService.java        # Teenuseklass (nt LocationService.java)
│   │   │       ├── persistence/                # Andmebaasi entiteedid, mapperid, repositooriumid
│   │   │       │   └── ressursipakett/         # Entiteedi alampakett (nt location)
│   │   │       │       ├── Entity.java         # JPA entiteet (nt Location.java)
│   │   │       │       ├── EntityMapper.java   # MapStructi mapperi liides (Entity ↔ DTO teisendus)
│   │   │       │       └── EntityRepository.java # Spring Data repositooriumi liides
│   │   │       └── infrastructure/             # Läbivad, domeeniülesed komponendid
│   │   │           ├── RestExceptionHandler.java # Globaalne erindite käsitleja (@ControllerAdvice)
│   │   │           ├── error/                  # Veavastuse mudel
│   │   │           │   └── ApiError.java       # Standardne API veavastuse objekt
│   │   │           ├── exception/              # Kohandatud erindiklassid
│   │   │           │   └── SomeException.java  # nt DataNotFoundException, ForbiddenException, PrimaryKeyNotFoundException
│   │   │           └── util/                   # Domeeniülesed abiklassid (nt tüübikonverterid)
│   │   ├── generated/                          # MapStructi genereeritud mapperi implementatsioonid (automaatne, ei redigeerita käsitsi)
│   │   └── resources/
│   │       ├── application.properties          # Rakenduse seadistus (port, andmebaas, logimine)
│   │       └── spy.properties                  # SQL päringute logimise seadistus (P6Spy)
│   └── test/
│       └── java/
│           └── ee.bcs.projektinimi/
│               └── SomeApplicationTests.java   # Ühik- ja integratsioonitestid
```

## Lühikirjeldused

| Kaust/fail | Eesmärk |
|---|---|
| `CLAUDE.md` | Backendi juhised Claude Code'ile — arhitektuur, konventsioonid, käsud |
| `build.gradle` / `settings.gradle` | Gradle ehitusseadistus ja sõltuvused |
| `gradle/wrapper/` | Gradle wrapper — võimaldab ehitada ilma Gradle'i eraldi paigaldamata |
| `src/main/java/.../SomeApplication.java` | Spring Booti rakenduse käivitusklass |
| `src/main/java/.../SomeEnum.java` | Baaspaketi tasemel jagatud enumid, mida kasutab mitu kihti (nt staatused, veateated) |
| `src/main/java/.../controller/` | REST endpointid — võtavad päringud vastu, tagastavad DTO-sid |
| `src/main/java/.../controller/.../dto/` | DTO klassid — andmekuju päringute ja vastuste jaoks; kontrollerid ei näe kunagi entiteete otse |
| `src/main/java/.../service/` | Äriloogika — vahendab kontrolleri ja andmebaasi vahel, viskab kohandatud erindeid |
| `src/main/java/.../persistence/` | JPA entiteedid, MapStructi mapperid ja Spring Data repositooriumid |
| `src/main/java/.../persistence/.../Entity.java` | Andmebaasi tabelile vastav JPA entiteet |
| `src/main/java/.../persistence/.../EntityMapper.java` | MapStructi liides entiteedi ja DTO vaheliseks teisenduseks |
| `src/main/java/.../persistence/.../EntityRepository.java` | Spring Data JPA repositoorium, vajadusel kohandatud `@Query` päringutega |
| `src/main/java/.../infrastructure/` | Domeeniülesed komponendid — veakäsitlus, erindid, abiklassid |
| `src/main/java/.../infrastructure/RestExceptionHandler.java` | Globaalne `@ControllerAdvice` erindite käsitleja, mis koostab veavastuse |
| `src/main/java/.../infrastructure/error/` | Standardse API veavastuse (`ApiError`) mudel |
| `src/main/java/.../infrastructure/exception/` | Kohandatud erindiklassid (nt "ei leitud", "keelatud", "vale primaarvõti") |
| `src/main/java/.../infrastructure/util/` | Domeeniülesed abiklassid, mis ei sobi teenuse ega kontrolleri alla |
| `src/main/generated/` | MapStructi poolt automaatselt genereeritud mapperi implementatsioonid (tekivad `compileJava` käigus) |
| `src/main/resources/application.properties` | Rakenduse seadistused — server, andmebaasiühendus, logimine |
| `src/main/resources/spy.properties` | P6Spy seadistus täieliku parameetritega SQL-i logimiseks |
| `src/test/` | Ühik- ja integratsioonitestid |

## Domeenialase paketi näide

Iga domeenala (nt `location`, `city`, `login`, `transactiontype`) omab läbivat struktuuri kolmes kihis:

```
controller/location/
├── LocationController.java
└── dto/
    ├── LocationDto.java
    └── LocationInfo.java

service/
└── LocationService.java

persistence/location/
├── Location.java
├── LocationMapper.java
└── LocationRepository.java
```

`service/` kaust on lame — kõik teenuseklassid asuvad otse selle all (mitte domeeni alampakettides), samal ajal kui `controller/` ja `persistence/` on jagatud domeenipõhisteks alampakettideks.
