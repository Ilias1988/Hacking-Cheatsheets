# 🌐 OSINT for domener og IP-adresser

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Domain-IP-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Domain-IP-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 🔍 Verktøy for domenekartlegging

| Verktøy | URL | Formål |
|------|-----|---------|
| **WHOIS** | who.is | Domeneregistrering |
| **SecurityTrails** | securitytrails.com | DNS-historikk |
| **ViewDNS** | viewdns.info | DNS-verktøy |
| **BuiltWith** | builtwith.com | Teknologistakk |
| **Wappalyzer** | wappalyzer.com | Identifikasjon av teknologi |

---

## 🛠️ Kommandoer

### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

### DNS-kartlegging

> **Presisering:** `ANY` betyr ikke at serveren returnerer alle oppføringer. Serveren kan gi et begrenset svar; se [RFC 8482](https://www.rfc-editor.org/rfc/rfc8482). Soneoverføring forutsetter at serveren tillater den.
```bash
# DNS-oppføringer
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# Soneoverføring
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

### Finne underdomener
```bash
# Subfinder
subfinder -d target.com -o subs.txt

# Amass
amass enum -d target.com -o subs.txt

# Assetfinder
assetfinder target.com
```

---

## 🔎 OSINT for IP-adresser

| Verktøy | URL | Formål |
|------|-----|---------|
| **Shodan** | shodan.io | Søk etter enheter |
| **Censys** | censys.io | Kartlegging av Internett |
| **IPinfo** | ipinfo.io | Geografisk plassering av IP-adresser |
| **AbuseIPDB** | abuseipdb.com | Omdømme for IP-adresser |

### Shodan CLI
```bash
shodan host 1.2.3.4
shodan search "hostname:target.com"
shodan domain target.com
```

### IP-oppslag

> **Presisering:** Geografisk plassering fra en IP-adresse er et estimat og identifiserer ikke alene en persons nøyaktige plassering.
```bash
curl ipinfo.io/1.2.3.4
curl ip-api.com/json/1.2.3.4
whois 1.2.3.4
```

---

## 📋 Sjekkliste for domener

```markdown
□ WHOIS-oppføringer
□ DNS-oppføringer (A, MX, TXT, NS)
□ Underdomener
□ Historisk DNS (SecurityTrails)
□ Teknologistakk
□ Opplysninger om SSL-sertifikater
□ IP-områder
□ Omvendt IP-oppslag
□ ASN-opplysninger
```

---

**Tilbake til OSINT:** [🔍 OSINT-oversikt](./README.nb.md)
