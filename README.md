# Brihta

Brihta je aplikacija z igrivimi vajami za angleške besede iz šole, ločeno za vsak razred (1.a, 1.b …). Vsebuje: kartice, poveži, spomin, poslušaj, sestavi besedo, najdi, igro za dva (na enem telefonu ali vsak s svojim) in slovarček.

Dodatno:
- **Pametno ponavljanje:** besede, ki jih otrok zgreši, se pojavljajo pogosteje. Ko so vsaj tri takšne, se med temami pokaže **🔁 Ponovi**.
- **Zvočni učinki** ob pravilnem in napačnem odgovoru. Izklopiš jih z gumbom **🔔 / 🔕** na začetnem zaslonu.
- **Nove besede** so 14 dni označene z ✨ in zbrane v temi **Novo**.

Aplikacija deluje na Android telefonih in iPhonih, se namesti kot prava aplikacija z ikono in deluje tudi brez povezave. Nove besede in posodobitve dobite samodejno.

Na začetnem zaslonu najprej izberete temo (okvir **Izberi temo**), nato igro (okvir **Izberi igro**, z jezičkoma **🙋 Igram sam** in **👫 Igrava dva**). Spodaj so **Slovarček**, **Navodila** in **Nastavitve**. Vse teme razreda, razvrščene po mesecih, odprete z gumbom **Vse teme**. Kratka navodila za igre so v aplikaciji pod **❓ Navodila**.

## Namestitev na telefon

Povezava: **https://urbanzb.github.io/brihta/**

Ob prvem zagonu aplikacija vpraša za razred. Povezava z oznako razreda ga izbere sama, na primer **https://urbanzb.github.io/brihta/#1b** za 1.b. Razred zamenjate z gumbom ob imenu Brihta.

**Android:** povezavo odpri v **Chromu** (ali Samsung Internetu). Aplikacija sama ponudi gumb **Namesti**. Če ga ni, odpri meni **⋮** in izberi **Namesti aplikacijo**. Aplikacija se pojavi med ostalimi aplikacijami z imenom **Brihta**.

**iPhone:** povezavo odpri v **Safariju**, tapni gumb **Deli** (kvadrat s puščico) in izberi **Dodaj na začetni zaslon**.

**Android, če se aplikacija odpira v brskalniku:** namesti različico APK s povezave **https://github.com/urbanzb/brihta/releases/download/apk/Brihta.apk**. Telefon bo vprašal, ali dovoliš namestitev iz tega vira: dovoli in tapni **Namesti**. Če Play Protect opozori na neznano aplikacijo, izberi **Več podrobnosti → Vseeno namesti**.

Posodobitev ni treba nameščati. Zgodijo se same ob odprtju aplikacije, ko je telefon povezan s spletom.

## Igra za dva

- **Na enem telefonu:** v **Igra za dva** izberita vsak svojo živalco in tapnita **Začni igro**. Telefon si podajata, ko aplikacija napiše »Podaj telefon!«. Internet ni potreben.
- **Vsak s svojim telefonom:** prvi otrok tapne **Ustvari igro** in prijatelju pove štirimestno kodo. Drugi otrok kodo vpiše in tapne **Pridruži se**. Igra potrebuje internet na obeh telefonih. Če kateri od otrok zapre aplikacijo ali zaklene telefon, se igra lahko prekine.

V obeh načinih se igralca izmenjujeta pri odkrivanju kartic. Kdor najde par, je še enkrat na vrsti. Temo in število parov izbere tisti, ki igro začne.

## Moje besede

V **⚙️ → Moje besede** lahko dodate svoje besede, tudi s prilepljanjem besedila iz Google Lens. Te besede ostanejo samo na vaši napravi in se mešajo z besedami iz šole. Ob menjavi telefona jih izvozite (**⬇️ Izvozi**) in na novem telefonu uvozite (**⬆️ Uvozi**).

## Izgovorjava

Brihta uporablja **britansko angleščino**. Če telefon nima britanskega glasu, aplikacija na začetnem zaslonu opozori. Britanski glas namestite v nastavitvah telefona pod **Pretvorba besedila v govor** (jezik: angleščina, Združeno kraljestvo). Natančna pot se med telefoni nekoliko razlikuje.

Slovenske besede bere slovenski glas, če ga telefon ima, sicer hrvaški. Izgovorjavo slovenskih besed izklopite z gumbom **🔊 SLO**.

## Mnenje

V **⚙️ → Pošlji mnenje** nam lahko sporočite, kaj vam je všeč in kaj bi izboljšali. Ob pošiljanju se odpre vaša e-pošta s pripravljenim sporočilom za **uzbmobileapps@gmail.com**, vi pa tapnete še **Pošlji**. Prosimo, ne navajajte imena otroka.

## Zasebnost

Aplikacija nima prijav. Zvezdice, nastavitve, napredek otroka in lastne besede so shranjeni samo na vaši napravi.
- **Anonimna statistika:** Brihta pošilja le skupne dnevne seštevke, na primer koliko iger spomina je bilo odigranih in katere besede iz šole so bile zgrešene. Ne pošilja imen, oznak naprave ali lastnih besed. Izklopite jo v **⚙️ → Anonimna statistika**.
- **Igra vsak s svojim telefonom:** med igro se v Firebase shranijo le kartice, živalci in točke, brez imen. Igra se izbriše, ko jo ustvarjalec zapusti.
- **Mnenje:** pošljete ga iz svoje e-pošte, zato avtor vidi vaš e-poštni naslov in vam lahko odgovori.

## Pogoste težave

- **Aplikacija se odpira v brskalniku (npr. DuckDuckGo):** nastavite Chrome za privzeti brskalnik in aplikacijo znova namestite iz Chroma ali pa namestite APK.
- **Novih besed ni:** počakajte nekaj minut, zaprite aplikacijo in jo odprite znova.
- **Ni zvoka:** preverite glasnost telefona in ali je gumb **🔔** vklopljen.

---

# Za skrbnika

## Datoteke

| Datoteka | Namen |
|---|---|
| `index.html` | Celotna aplikacija |
| `besede.json` | Objavljene besede (posodablja jih skrbniški način) |
| `manifest.webmanifest` | Ime, barve in ikone za namestitev |
| `sw.js` | Delovanje brez povezave in samodejne posodobitve |
| `icon-*.png`, `apple-touch-icon.png` | Ikone |
| `*.woff2`, `pisave-licenca-OFL.txt` | Pisave in njihova licenca |
| `firebase-pravila.json` | Varnostna pravila za igro za dva |
| `android/`, `.github/workflows/apk.yml` | Android aplikacija (APK), ki jo zgradi GitHub |

Datoteke spletne aplikacije so v eni mapi, da jih lažje naložiš tudi s telefona. Novo različico naložiš z **Add file → Upload files → Commit changes**. Datoteke `besede.json` ne nalagaj ročno, ker bi prepisala objavljene besede.

## Dodajanje novih besed

1. Fotografiraj slovarček v zvezku in fotografijo pošlji Claudu v projektu (navodila so v `navodila-za-claude-projekt.md`). Lahko uporabiš tudi Google Lens.
2. V aplikaciji odpri **⚙️ → Za skrbnika → Skrbniški način**, besedilo prilepi v **Prilepi seznam besed** in tapni **Preberi besede**.
3. Preveri besede. Kjer je polje za emoji rdeče, ga izberi. Če aplikacija opozori na ameriško obliko (💂), tapni **Popravi**.
4. Tapni **Dodaj besede** in nato **Objavi za vse**.

Ostali dobijo nove besede v nekaj minutah. Datum zadnje objave je zapisan na dnu nastavitev.

## GitHub žeton

Skrbniški način za objavo potrebuje žeton, ki ostane samo na tvojem telefonu. Nikomur ga ne pošiljaj.

1. V brskalniku odpri **github.com/settings/personal-access-tokens/new** (na telefonu po potrebi vklopi **Spletno mesto za računalnike**).
2. **Token name:** `appANG`, **Expiration:** najdaljši možni rok, **Repository access:** **Only select repositories → brihta**, **Permissions:** **Contents → Read and write**.
3. **Generate token**, žeton kopiraj in ga prilepi v **Skrbniški način → Žeton → Shrani in preveri**.

Ko žeton poteče ali izgubiš telefon, ga na isti strani izbriši in ustvari novega. Žeton vpiši v aplikaciji, ki jo dejansko uporabljaš, ker ima vsak brskalnik svojo shrambo.

## Firebase (igra vsak s svojim telefonom)

Pri tebi je že nastavljeno (projekt `appANG`). Postopek za primer ponovne nastavitve:

1. **console.firebase.google.com** → projekt.
2. **Authentication → Sign-in method → Anonymous → Enable → Save.** Brez tega preizkus javi `auth/admin-restricted-operation`.
3. **Databases & Storage → Realtime Database** (ne Firestore) **→ Create database**, lokacija **Belgium (europe-west1)**, **Start in locked mode**. V zavihek **Rules** prilepi vsebino `firebase-pravila.json` in tapni **Publish**.
4. **Project Overview → + Add app → </> (Web)**, registriraj in kopiraj blok `const firebaseConfig = { … }` (kasneje v **Settings → General → Your apps**).
5. V aplikaciji **Skrbniški način → 👫 Igra za dva (Firebase)**: prilepi `firebaseConfig`, tapni **Uporabi nastavitve**, **Preizkusi povezavo** in **Objavi za vse**.

Nastavitve Firebase niso skrivnost. Bazo varujejo pravila iz 3. koraka.

## APK

- APK se zgradi sam ob vsaki spremembi mape `android` ali ročno z **Actions → Zgradi APK → Run workflow**. Gradnja traja približno 5 minut.
- Za gradnjo potrebuje skrivnost **APK_GESLO** (**Settings → Secrets and variables → Actions**). Geslo shrani varno.
- Ob prvi gradnji GitHub ustvari ključ za podpisovanje in ga šifriranega shrani kot `android/keystore.enc`. Te datoteke ne briši, sicer starši posodobitev ne bodo mogli namestiti čez staro različico.
- Na dnu nastavitev v APK piše na primer »Različica 1.5 (APK 6)«. Številka APK pove, katera gradnja je nameščena.
- Google uvaja preverjanje razvijalcev tudi za aplikacije zunaj Trgovine Play. Ko bo to veljalo v Sloveniji (napovedano za leto 2027), bo namestitev APK zahtevnejša. Takrat je spletna različica najpreprostejša pot.

## Stanje objave

V zavihku **Actions** na GitHubu sta dva postopka: **pages build and deployment** (spletna aplikacija, 1 do 2 minuti) in **Zgradi APK** (približno 5 minut). Rumeni krogec pomeni, da postopek še teče, zelena kljukica uspeh, rdeči križec napako.

## Razredi

V **Skrbniški način → 🏫 Razredi** urejaš seznam razredov. Ime lahko spremeniš (naslednje leto 1.a v 2.a), teme ostanejo. Dodaš lahko nov razred ali odstraniš obstoječega. Spremembe objaviš z **Objavi za vse**.

- Vsaka objavljena tema ima v urejevalniku vrstico **Razred** s polji za izbiro. Tema lahko pripada enemu ali več razredom, na primer skupna tema za 1.a in 1.b.
- Teme brez oznake pripadajo prvemu razredu na seznamu, zato vse dosedanje teme ostanejo v 1.a.
- Ko ustvariš novo temo (s prilepljanjem besed ali z obrazcem **Nova tema**), v vrstici **Razred** izbereš, kateremu razredu ali razredom pripada. Privzeto je označen razred, ki je trenutno izbran v aplikaciji. Pri prilepljanju besed v obstoječo temo aplikacija pokaže njen razred.
- Staršem posameznega razreda pošlji povezavo z oznako razreda, na primer `…/brihta/#1b`. Oznako (`1a`, `1b` …) vidiš v razdelku Razredi.
- Lastne besede staršev so vidne v vseh razredih.
- Statistika uporabe prikazuje tudi število odprtij po razredih.

## Navodila in novosti

V **Skrbniški način** urejaš besedilo zaslona **❓ Navodila** in seznam novosti po različicah. Spremembe objaviš z **Objavi za vse**, nalaganje datotek na GitHub ni potrebno.

- **❓ Navodila:** preprost zapis, kjer `# Naslov` začne nov razdelek, `- besedilo` je alineja, `**besedilo**` pa krepko. **Predogled** pokaže, kako bo izgledalo, **Ponastavi na privzeta** pa vrne prvotno besedilo.
- **🆕 Novosti po različicah:** vpiši različico (npr. 1.9), datum in spremembe, vsako v svoji vrstici z »- «. Novosti se pokažejo na koncu navodil, zadnje tri v celoti, starejše pod »Starejše različice«. Ko objaviš novo različico, uporabniki na začetnem zaslonu vidijo pasico »🆕 Novo v različici …«.
- Z **Uredi** lahko obstoječe novosti popraviš, z **×** pa jih izbrišeš.

## Statistika uporabe

V **Skrbniški način → 📊 Statistika uporabe** tapni **Naloži statistiko** in izberi obdobje (7, 30 ali 90 dni). Vidiš število odprtij in dni uporabe, katere igre se začnejo in končajo, priljubljene teme, najtežje besede (koristno tudi za učiteljico) ter naprave in različice. Podatki so anonimni dnevni seštevki v Firebase (`stats`). Za delovanje morajo biti v Firebase objavljena pravila iz `firebase-pravila.json`.

Statistika trgovine (namestitve, ocene, sesutja) bo na voljo v Play Console, ko bo aplikacija objavljena v Trgovini Play.

## Mnenja

Mnenja prihajajo na **uzbmobileapps@gmail.com** kot navadna e-pošta z zadevo »Brihta: mnenje …«. V sporočilu sta tudi različica aplikacije in vrsta naprave. Odgovoriš neposredno iz pošte. Naslov za mnenja je zapisan v kodi (`FEEDBACK_EMAIL` v `index.html`).

## Britanska angleščina

Brihta uporablja izključno britansko angleščino v zapisu (colour, favourite, grey, lorry, biscuit, sweets, mum, trousers, rubber, football) in izgovorjavi (en-GB). Pri dodajanju besed aplikacija zazna ameriške oblike in ponudi popravek. Ameriške besede so v seznamu označene s 💂. Navodila za Claude projekt prav tako zahtevajo britansko angleščino.

## Težave pri skrbništvu

- **»Žeton nima pravice za pisanje«:** pri žetonu preveri dovoljenje **Contents: Read and write** in izbrani repozitorij `brihta`.
- **Napaka v objavljeni besedi:** v skrbniškem načinu besedo izbriši, jo dodaj pravilno in znova objavi.
- **Firebase javi napako o dostopu ali se statistika ne naloži:** preveri, ali so v Realtime Database objavljena najnovejša pravila iz `firebase-pravila.json`.
- **Gradnja APK ima rdeči križec:** odpri zagon v zavihku **Actions** in preberi besedilo napake v koraku z rdečim križcem.
- **Datoteke `besede.json` ne vpisuj imen otrok** ali drugih osebnih podatkov, ker je javna.

## Za razvijalca: več predmetov

Koda je pripravljena na druge predmete, uporabnik pa zaenkrat vidi samo angleščino. Ko bo čas za razširitev:
- v `besede.json` se doda seznam `"subjects"` (npr. `{"id":"mat","name":"Matematika","icon":"🔢","a":"račun","b":"rezultat","langA":"sl","langB":""}`),
- teme dobijo polje `"subject"` (teme brez njega pripadajo angleščini),
- na začetni zaslon se doda izbira predmeta, ki nastavi `S.subject`.

Igre delujejo za vse pare oblike *a = b*, izgovorjava pa sledi jezikoma `langA` in `langB` izbranega predmeta.
