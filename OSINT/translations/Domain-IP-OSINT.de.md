# 🌐 OSINT zu Domains und IP-Adressen

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Domain-IP-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Domain-IP-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 🔍 Werkzeuge zur Domain-Aufklärung

| Werkzeug | URL | Zweck |
|------|-----|---------|
| **WHOIS** | who.is | Domainregistrierung |
| **SecurityTrails** | securitytrails.com | DNS-Verlauf |
| **ViewDNS** | viewdns.info | DNS-Werkzeuge |
| **BuiltWith** | builtwith.com | Verwendete Technologien |
| **Wappalyzer** | wappalyzer.com | Technologieerkennung |

---

## 🛠️ Befehle

### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

### DNS-Enumeration (systematische Bestandsaufnahme)
```bash
# DNS-Einträge
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# Zonentransfer
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

### Subdomains ermitteln
```bash
# Subfinder
subfinder -d target.com -o subs.txt

# Amass
amass enum -d target.com -o subs.txt

# Assetfinder
assetfinder target.com
```

---

## 🔎 OSINT zu IP-Adressen

| Werkzeug | URL | Zweck |
|------|-----|---------|
| **Shodan** | shodan.io | Gerätesuche |
| **Censys** | censys.io | Internetweite Scans |
| **IPinfo** | ipinfo.io | Geolokalisierung von IP-Adressen |
| **AbuseIPDB** | abuseipdb.com | Reputation von IP-Adressen |

### Shodan CLI
```bash
shodan host 1.2.3.4
shodan search "hostname:target.com"
shodan domain target.com
```

### IP-Abfrage
```bash
curl ipinfo.io/1.2.3.4
curl ip-api.com/json/1.2.3.4
whois 1.2.3.4
```

---

## 📋 Domain-Checkliste

```markdown
□ WHOIS-Einträge
□ DNS-Einträge (A, MX, TXT, NS)
□ Subdomains
□ Historische DNS-Daten (SecurityTrails)
□ Verwendete Technologien
□ Informationen zu SSL-Zertifikaten
□ IP-Adressbereiche
□ Reverse-IP-Abfrage
□ Informationen zur autonomen Systemnummer (ASN)
```

---

**Zurück zu OSINT:** [🔍 OSINT-Übersicht](./README.de.md)

---

## Hinweise zur deutschen Ausgabe

> **DNS und Zuordnung:** Eine `ANY`-Abfrage liefert nicht zuverlässig alle DNS-Einträge; Server dürfen minimale Antworten geben. Ein verweigerter Zonentransfer ist kein Nachweis fehlender DNS-Daten. IP-Geolokalisierung beschreibt eine Zuordnung des Dienstanbieters, nicht sicher den Standort einer Person. Geteiltes Hosting, Proxys und historische Daten können Zuordnungen verfälschen. [RFC 8482](https://www.rfc-editor.org/rfc/rfc8482).
