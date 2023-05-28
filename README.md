# ZinCity
By: Nem3D csapat

## Rövid összefoglaló, útmutató (tutorial)

A ZinCity egy városépítő játék, ahol a játékosok valós időben alakíthatnak ki minél jobban működő, minél boldogabb városokat. Sokféleképpen alakíthatják a játékosok a városukat, különböző funckiókkal rendelkező zónákat, épületeket húzhatnak fel, amelyeket természetesen utakkal is össze kell kapcsolni. A városlakók boldogságát a város felépítése határozza meg - minél átgondoltabb, az igényeket minél jobban kielégítő várost tervezünk, annál jobban fogják magukat érezni a lakói!

### Letöltés
A futtatható állomány [ide kattintva](https://szofttech.inf.elte.hu/szofttech-c-2023/group-14/nem3d/-/raw/master/desktop-1.0.jar) érhető el! <br>
Megjegyzés: Java JRE szükséges, amely [itt](https://www.java.com/en/download/manual.jsp) elérhető.

### Kezdés

A játékos, alapesetben, egy véletlenszerűen generált pályán indul egy kezdővárossal, mely rendelkezik lakóhelyekkel, munkahelyekkel, lakosokkal és kezdőtőkével. Ezt felül lehet írni saját mentés betöltésével.

### Mire is van lehetőség a játék során? 
- Különböző típusú zónákat, kiszolgáló épületeket, hálozatokat építeni

- Fejleszteni a már lerakott zónákat

- Erdőket telepíteni

- Különböző típusú pályaelemeket lebontani 

- Befolyásolni a játék sebességét (lassú, közepes, gyors) vagy akár megállítani azt

- Befolyásolni a naponta beszedett adó mértékét

- Lekérni részletesebb statisztikákat

- Elmenteni az éppen aktív játékállást

- Saját mentést betölteni

- Kilépni (ilyenkor a játékállás elveszik)

### Milyen fajta pályaelemeket lehet építeni/elhelyezni a játéktéren?
- Zónák
  - _Lakózóna_: ez szolgál a város polgárainak otthonául
  - _Ipari zóna_: olyan zónatípus, mely a lakosok munkahelyéül szolgálhat
  - _Szolgáltatás zóna_: olyan zónatípus, mely a lakosok munkahelyéül szolgálhat
- Kiszolgáló épületek
  - _Rendőrség_: jelenléte növeli a közbiztonságot
  - _Tűzoltóság_: jelenléte csökkenti a területek kigyulládásának esélyét a környéken, illetve a már kigyulladt területeken levő tüzek eloltását teszi lehetővé
  - _Generátor_: árammal látja el az elektromos hálózatra kötött pályaelemeket, ezáltal hozzájárulva a kiszolgáló épületek működéséhez, munkakiesések csökkentéséhez, a lakók elégedettségének növekvéséhez
  - _Aréna_: a környezetében lévő lakók, dolgozók elégedettségét képes növelni
- Hálózatok
  - _Utak_: a zónák, épületek összekétésére szolgál (ezek építésének előfeltétele az utakkal való szomszédosság)
  - _Elektromos kábel_: összekötési lehetőséget ad az elektromos hálózattal renddelkező pályaelemek összeköttetésére, ezáltal megfelelő árammellátás biztosítására
- Egyéb
  - _Erdők_: hozzájárul a lakosság elégedettségének növekedéséhez, hatása az idő elteltével nő (párhuzamosan a fák életkozának a növekedésével)

### Mivel lehet növelni a polgárok elégedettségét? Milyen tényezők vannak pozitív hatással a lakosokra?
- Ha minél kisebb a távolság a lakóhely és a munkahely között.
- Ha a lakóhelyéhez nincsen túlságosan közel ipari terület.
- Ha van telepítve erdő a lakóhely környékére.
- Ha a város rendelkezik arénával a lakó-, és munkahelye környékén.
- Ha lakóhelye környékén nagyobb a közbiztonság.
- Ha lakóhelye árammal van ellátva.
- Ha a városbeli különböző típusú munkahelyek száma egyenlő.

## Hogyan is néz ki a ZinCity?

![main menu](assets/UserGuide/mainmenu.png)
![game state 1](assets/UserGuide/gamestate1.png)
![game state 2](assets/UserGuide/gamestate2.png)
![stat screen](assets/UserGuide/statscreen.png)
![end game](assets/UserGuide/endgame.png)
![exit game](assets/UserGuide/exitgame.png)


## Technikai információk
### Környezet
A játék Java programozási nyelven (Java 17), a [libGDX](https://libgdx.com/) motor használatával készül.
A fejlesztés során a Gradle build eszközt, és a GitLab különböző szolgáltatásait használjuk.

Ha szeretnéd a fejlesztést támogatni, klónozd le ezt a repo-t, majd a fejlesztői környezet felállításához kövesd az utasításokat [itt](https://libgdx.com/wiki/start/setup).

### Tervek, diagramok

[UML osztálydiagram](https://www.figma.com/file/IzeYh4hv1yDRLSzyBCrBry/ZinCity-UML%2C-architecture?t=QNw82ZFgkOh9Pjjh-0) (By: Runic/Bálint)

[Felhasználói felület](https://www.figma.com/file/nNoj0ElzoN8P9CoAiExZzn/ZimCity-UI%2C-design?node-id=0%3A1&t=QNw82ZFgkOh9Pjjh-1) (By: Newton/Bence)

[Use-case diagram](https://www.figma.com/file/13BSPQLJSfDT80iGUyzOdE/ZinCity-use-cases?t=QNw82ZFgkOh9Pjjh-0) (By: Jaksy/Marci)

### Development log

![epic dev thingy](assets/UserGuide/Szofttech_tp.png)
