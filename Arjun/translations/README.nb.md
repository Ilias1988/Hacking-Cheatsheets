# 🔍 Arjun – oppdagelse av skjulte parametere

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

> **Rettelse og versjoner:** Å finne et parameternavn er ikke alene å finne et nytt endepunkt eller en sårbarhet. Den offisielle veiledningen bruker blant annet `--ratelimit`, `-d` og `-T`; originalens `--rate-limit`, `--delay` og `--timeout` må derfor ikke antas gyldige i alle versjoner. GET/POST/JSON angir ulike forespørselsformer, og den viste kommaseparerte metodeverdien må kontrolleres. Passiv-modusens datakilder og øvrige flagg må også kontrolleres for installasjonen. Se [Arjuns bruksveiledning](https://github.com/s0md3v/Arjun/wiki/Usage). Originalkommandoene er beholdt.

```
   █████╗ ██████╗      ██╗██╗   ██╗███╗   ██╗
  ██╔══██╗██╔══██╗     ██║██║   ██║████╗  ██║
  ███████║██████╔╝     ██║██║   ██║██╔██╗ ██║
  ██╔══██║██╔══██╗██   ██║██║   ██║██║╚██╗██║
  ██║  ██║██║  ██║╚█████╔╝╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚═╝  ╚═══╝
         Verktøy for oppdagelse av HTTP-parametere
```

<p align="center">
  <img src="https://img.shields.io/badge/Arjun-Parameter_Discovery-blue?style=for-the-badge" alt="Arjun">
  <img src="https://img.shields.io/badge/Hidden_Params-green?style=for-the-badge" alt="Hidden">
  <img src="https://img.shields.io/badge/Bug_Bounty-red?style=for-the-badge" alt="Bug Bounty"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er Arjun](#-hva-er-arjun)
- [Installasjon](#-installasjon)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Oppdagelsesmoduser](#-oppdagelsesmoduser)
- [Avanserte alternativer](#-avanserte-alternativer)
- [Utdataalternativer](#-utdataalternativer)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er Arjun

**Arjun** finner skjulte HTTP-parametere i webapplikasjoner:

- 🔍 **Oppdagelse av parametere** – finn skjulte GET-/POST-parametere
- 🎯 **JSON-støtte** – finn JSON-parametere
- 📡 **Flere metoder** – GET-, POST- og JSON-modus
- ⚡ **Raskt** – mange parallelle forespørsler

### Hvorfor Arjun?

| Egenskap | Fordel |
|---------|---------|
| Skjulte parametere | Finn urefererte endepunkter |
| Flere formater | GET, POST, JSON |
| Passiv modus | Undersøk JS-filer |
| Import fra Burp | Integrer med eksisterende arbeidsflyt |

---

## 🚀 Installasjon

```bash
# Installasjon med pip (anbefalt)
pip3 install arjun

# Fra kildekode
git clone https://github.com/s0md3v/Arjun.git
cd Arjun
python3 setup.py install

# Kontroller
arjun --help
```

---

## 📖 Grunnleggende bruk

### Én URL

```bash
# Grunnleggende parameteroppdagelse
arjun -u https://example.com/endpoint

# Med utdatafil
arjun -u https://example.com/endpoint -o results.txt
```

### Flere URL-er

```bash
# Fra fil
arjun -i urls.txt

# Fra standard inndata
cat urls.txt | arjun --stdin
```

### Angi HTTP-metode

```bash
# GET-parametere (standard)
arjun -u https://example.com/search -m GET

# POST-parametere
arjun -u https://example.com/api -m POST

# JSON-innhold
arjun -u https://example.com/api -m JSON
```

---

## 🔎 Oppdagelsesmoduser

### GET-parametere

```bash
# Finn GET-parametere
arjun -u https://example.com/search?known=value -m GET

# Eksempelutdata:
# [param] id
# [param] user
# [param] debug
```

### POST-parametere

```bash
# Finn parametere i POST-skjemaer
arjun -u https://example.com/login -m POST

# Med eksisterende data
arjun -u https://example.com/login -m POST --include 'username=admin'
```

### JSON-parametere

```bash
# Finn parametere i JSON-innhold
arjun -u https://example.com/api/v1/users -m JSON

# Med grunnleggende JSON
arjun -u https://example.com/api -m JSON --include '{"known":"value"}'
```

### Kombinert oppdagelse

```bash
# Alle metoder
arjun -u https://example.com/endpoint -m GET,POST,JSON
```

---

<a id="-avanserte-alternativer"></a>
## ⚙️ Avanserte alternativer

### Egendefinert ordliste

```bash
# Bruk en egendefinert ordliste
arjun -u https://example.com -w /path/to/wordlist.txt

# Bruk parameterlisten fra SecLists
arjun -u https://example.com -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt
```

### Tråder og hastighetsbegrensning

```bash
# Angi tråder (standard: 2)
arjun -u https://example.com -t 10

# Hastighetsgrense (forespørsler per sekund)
arjun -u https://example.com --rate-limit 10

# Forsinkelse mellom forespørsler (sekunder)
arjun -u https://example.com --delay 1
```

### Egendefinerte headere

```bash
# Legg til header
arjun -u https://example.com --headers "Authorization: Bearer token"

# Flere headere
arjun -u https://example.com --headers "Cookie: session=abc;X-Custom: value"

# Fra fil
arjun -u https://example.com -H headers.txt
```

### Ta med kjente parametere

```bash
# Ta med kjente parametere
arjun -u https://example.com/search --include 'q=test&page=1'

# For POST
arjun -u https://example.com/api -m POST --include 'action=submit'
```

### Passiv modus

```bash
# Hent ut fra JavaScript-filer
arjun -u https://example.com --passive

# Dette undersøker JS-filer for mulige parameternavn
```

### Deaktivere omdirigeringer

```bash
# Ikke følg omdirigeringer
arjun -u https://example.com --disable-redirects
```

### Tidsavbrudd

```bash
# Angi tidsavbrudd (sekunder)
arjun -u https://example.com --timeout 15
```

### Stabil modus

```bash
# Mer pålitelig (langsommere)
arjun -u https://example.com --stable
```

---

## 📤 Utdataalternativer

### Utdataformater

```bash
# Tekstutdata (standard)
arjun -u https://example.com -oT results.txt

# JSON-utdata
arjun -u https://example.com -oJ results.json

# Begge
arjun -u https://example.com -oT text.txt -oJ json.json
```

### Eksempel på JSON-utdata

```json
{
  "https://example.com/api": {
    "method": "GET",
    "params": [
      {"name": "id", "reason": "body differs"},
      {"name": "debug", "reason": "body differs"}
    ]
  }
}
```

### Import fra Burp

```bash
# Importer Burp-forespørsler
arjun --import burp_export.xml

# Behandle Burp-filen
arjun -i burp_requests.txt --burp
```

---

## 📊 Hurtigreferanse

### Grunnleggende kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `arjun -u URL` | Finn parametere |
| `arjun -u URL -m POST` | POST-metode |
| `arjun -u URL -m JSON` | JSON-innhold |
| `arjun -i file.txt` | Flere URL-er |
| `arjun -u URL -oJ out.json` | JSON-utdata |

### Metodealternativer

| Flagg | Beskrivelse |
|------|-------------|
| `-m GET` | GET-parametere |
| `-m POST` | POST-skjemadata |
| `-m JSON` | JSON-innhold |
| `-m GET,POST` | Flere metoder |

### Ytelsesalternativer

| Flagg | Beskrivelse |
|------|-------------|
| `-t 10` | Tråder |
| `--rate-limit 10` | Forespørsler/sekund |
| `--delay 1` | Forsinkelse mellom forespørsler |
| `--timeout 15` | Tidsavbrudd for forespørsel |

### Oppdagelsesalternativer

| Flagg | Beskrivelse |
|------|-------------|
| `-w wordlist.txt` | Egendefinert ordliste |
| `--include 'a=b'` | Ta med parametere |
| `--passive` | Hent ut fra JavaScript |
| `--stable` | Pålitelig modus |

---

## 🎯 Arbeidsflyter for bug bounty

### Grunnleggende oppdagelse

```bash
# Rask parameteroppdagelse
arjun -u https://target.com/api/endpoint -m GET,POST,JSON -oJ params.json
```

### Med egendefinert ordliste

```bash
# Omfattende oppdagelse
arjun -u https://target.com/admin \
  -m GET,POST \
  -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
  -t 10 \
  --rate-limit 20 \
  -oJ discovered_params.json
```

### Autentisert testing

```bash
# Med økt
arjun -u https://target.com/dashboard \
  --headers "Cookie: session=abc123;Authorization: Bearer token" \
  -m GET,POST,JSON \
  -oJ auth_params.json
```

### Oppdagelse på mange mål

```bash
# Flere endepunkter
cat endpoints.txt | arjun --stdin -m GET,POST -t 5 -oJ all_params.json

# Eller med fil
arjun -i endpoints.txt -m GET,POST -t 5 -oJ results.json
```

### Integrasjon i pipeline

```bash
# Finn endepunkter, og finn deretter parametere
gau target.com | grep -E "\.php|\.asp" | httpx -silent | \
  arjun --stdin -m GET,POST -oJ params.json

# Katana + Arjun
katana -u https://target.com -d 3 | \
  grep -E "\.(php|asp|aspx|jsp)$" | \
  arjun --stdin -m GET,POST
```

### Testing av oppdagede parametere

```bash
# Test for sårbarheter etter oppdagelsen
# 1. Finn parametere
arjun -u https://target.com/search -oJ params.json

# 2. Hent ut parameternavn
cat params.json | jq -r '.[].params[].name'

# 3. Test med ffuf
ffuf -u "https://target.com/search?FUZZ=test" -w params.txt
```

---

## 💡 Praktiske tips

1. **Begynn passivt** – undersøk JS-filer først
2. **Bruk gode ordlister** – Burps parameternavn anbefales
3. **Begrens hastigheten** – unngå blokkering
4. **Test alle metoder** – GET, POST og JSON
5. **Undersøk headere** – X-Custom-Param, X-Debug osv.
6. **Autentisert** – test områder som krever pålogging

### Vanlige skjulte parametere

```
debug, test, admin
id, user_id, uid
token, api_key, key
callback, redirect, url
format, output, type
page, limit, offset
sort, order, filter
```

---

## 📚 Ressurser

- [Arjun GitHub](https://github.com/s0md3v/Arjun)
- [Burp Parameter Names](https://github.com/danielmiessler/SecLists/blob/master/Discovery/Web-Content/burp-parameter-names.txt)

### Relaterte hurtigreferanser
- [Bug Bounty Methodology](../../Bug-Bounty-Methodology/translations/README.nb.md)
- [ffuf](../../ffuf/translations/README.nb.md)
- [Burp Suite](../../Burp-Suite/translations/README.nb.md)

---

<p align="center">
  <b>🔍 Finn skjulte parametere!</b><br>
  <i>Arjun – parameterjegeren</i>
</p>
