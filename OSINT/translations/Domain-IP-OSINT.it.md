# 🌐 OSINT su domini e indirizzi IP

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Domain-IP-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Domain-IP-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Nota sui record DNS:** una query `ANY` non garantisce un inventario completo: il server può restituire una risposta minima o limitarla secondo la propria policy ([RFC 8482](https://datatracker.ietf.org/doc/html/rfc8482)). I risultati di geolocalizzazione IP sono indicativi, non la posizione certa di una persona.

---

## 🔍 Strumenti di ricognizione sui domini

| Strumento | URL | Scopo |
|------|-----|---------|
| **WHOIS** | who.is | Registrazione del dominio |
| **SecurityTrails** | securitytrails.com | Storico DNS |
| **ViewDNS** | viewdns.info | Strumenti DNS |
| **BuiltWith** | builtwith.com | Insieme delle tecnologie utilizzate (stack) |
| **Wappalyzer** | wappalyzer.com | Rilevamento delle tecnologie |

---

## 🛠️ Comandi

### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

### Enumerazione DNS
```bash
# Record DNS
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# Trasferimento di zona
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

### Individuazione dei sottodomini
```bash
# Subfinder
subfinder -d target.com -o subs.txt

# Amass
amass enum -d target.com -o subs.txt

# Assetfinder
assetfinder target.com
```

---

## 🔎 OSINT sugli indirizzi IP

| Strumento | URL | Scopo |
|------|-----|---------|
| **Shodan** | shodan.io | Ricerca di dispositivi |
| **Censys** | censys.io | Scansione di Internet |
| **IPinfo** | ipinfo.io | Geolocalizzazione degli indirizzi IP |
| **AbuseIPDB** | abuseipdb.com | Reputazione degli indirizzi IP |

### Shodan CLI
```bash
shodan host 1.2.3.4
shodan search "hostname:target.com"
shodan domain target.com
```

### Ricerca di informazioni sugli indirizzi IP
```bash
curl ipinfo.io/1.2.3.4
curl ip-api.com/json/1.2.3.4
whois 1.2.3.4
```

---

## 📋 Checklist sui domini

```markdown
□ Record WHOIS
□ Record DNS (A, MX, TXT, NS)
□ Sottodomini
□ Storico DNS (SecurityTrails)
□ Insieme delle tecnologie utilizzate (stack)
□ Informazioni sui certificati SSL
□ Intervalli di indirizzi IP
□ Ricerca inversa degli indirizzi IP
□ Informazioni sugli ASN (numeri di sistema autonomo)
```

---

**Torna a OSINT:** [🔍 Panoramica OSINT](./README.it.md)
