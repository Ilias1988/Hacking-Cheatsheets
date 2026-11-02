# 🌐 Amass – Erfassung der Angriffsfläche

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

```
   █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
  ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
  ███████║██╔████╔██║███████║███████╗███████╗
  ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
  ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
  ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
         Detaillierte Erfassung der Angriffsfläche
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Amass?](#-was-ist-amass)
- [Installation](#-installation)
- [Enumerationsmodus](#-enumerationsmodus)
- [Intel-Modus](#-intel-modus)
- [DNS-Modus](#-dns-modus)
- [Konfiguration](#-konfiguration)
- [Kurzreferenz](#-kurzreferenz)

---

## 🎯 Was ist Amass?

**Amass** ist ein OWASP-Projekt zur Erfassung von Netzwerken und öffentlich erreichbaren Ressourcen:

- 🔍 **Subdomains erfassen** – laut Vorlage besonders umfassend
- 🏢 **ASN ermitteln** – IP-Adressbereiche einer Organisation finden
- 📡 **DNS-Aufklärung** – passive und aktive Erkundung
- 🗺️ **Angriffsfläche erfassen** – Infrastruktur darstellen

### Warum Amass?

| Eigenschaft | Nutzen |
|---------|---------|
| Über 55 Datenquellen laut Vorlage | Laut Vorlage besonders umfassende Ergebnisse |
| Passiv und aktiv | Umfang der direkten Interaktion wählen |
| ASN-/Organisationsaufklärung | Weniger sichtbare Infrastruktur finden |
| Graphdatenbank | Zusammenhänge darstellen |

---

## 🚀 Installation

```bash
# Mit Go installieren
go install -v github.com/owasp-amass/amass/v4/...@master

# Kali Linux
sudo apt install amass

# Docker
docker pull caffix/amass

# Installation prüfen
amass -version
```

---

## 🔍 Enumerationsmodus

### Einfache Erfassung von Subdomains

```bash
# Passive Erfassung (kein direkter Kontakt zum Ziel)
amass enum -passive -d example.com

# Aktive Erfassung (DNS-Namen systematisch ausprobieren laut Vorlage)
amass enum -active -d example.com

# Mit einer Wortliste systematisch ausprobieren
amass enum -active -brute -d example.com -w wordlist.txt
```

### Ausgabeoptionen

```bash
# In eine Datei speichern
amass enum -passive -d example.com -o subdomains.txt

# JSON-Ausgabe
amass enum -passive -d example.com -json output.json

# In einem Verzeichnis speichern
amass enum -passive -d example.com -dir ./amass_output/
```

### Mehrere Ziele

```bash
# Mehrere Domains
amass enum -passive -d example.com -d target.com

# Aus einer Datei
amass enum -passive -df domains.txt -o results.txt
```

### Erweiterte Enumeration

```bash
# Zeitlimit festlegen (Minuten)
amass enum -passive -d example.com -timeout 30

# Quellen begrenzen
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# Quellen ausschließen
amass enum -passive -d example.com -exclude Google,Bing

# Datenquellen anzeigen
amass enum -passive -d example.com -src
```

### Rekursive Erfassung

```bash
# Rekursive Suche aktivieren (Beschriftung der Vorlage)
amass enum -passive -d example.com -r

# Rekursionstiefe festlegen (Beschriftung der Vorlage)
amass enum -passive -d example.com -max-depth 3
```

---

## 🏢 Intel-Modus

> Im Intel-Modus werden Organisationen zugeordnete Stammdomains, ASNs und CIDR-Bereiche ermittelt.

### Ressourcen einer Organisation finden

```bash
# Nach Organisationsname
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# Umgekehrte WHOIS-Abfrage
amass intel -whois -d example.com
```

### ASN ermitteln

```bash
# ASN einer Domain ermitteln (Beschriftung der Vorlage)
amass intel -d example.com

# Anhand einer ASN erfassen
amass intel -asn 13335
amass intel -active -asn 13335

# Mehrere ASNs
amass intel -asn 13335,15169
```

### CIDR-Bereich

```bash
# Ressourcen im CIDR-Bereich finden
amass intel -active -cidr 192.168.1.0/24

# Mehrere CIDR-Bereiche
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

### IP-Adressbereich

```bash
# IP-Adressbereich
amass intel -active -ip 192.168.1.1-254
```

---

## 📡 DNS-Modus

### DNS-Abfragen

```bash
# Einfache DNS-Abfrage
amass dns -d example.com

# Bestimmte Eintragstypen
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

### Subdomains auflösen

```bash
# Gefundene Subdomains auflösen
amass dns -df subdomains.txt
```

---

<a name="-konfiguration"></a>

## ⚙️ Konfiguration

### Speicherort der Konfigurationsdatei

```bash
# Standardspeicherort
~/.config/amass/config.yaml

# Eigene Konfiguration angeben
amass enum -config ./my_config.yaml -d example.com
```

### Beispiel einer Konfigurationsdatei

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: YOUR_API_KEY
  Shodan:
  - credentials:
      apikey: YOUR_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: YOUR_VT_KEY
```

### Datenquellen mit API-Schlüsseln

| Quelle | Kostenloses Kontingent | API erforderlich |
|--------|-----------|--------------|
| Crt.sh | ✅ | Nein |
| SecurityTrails | ✅ | Ja |
| Shodan | ✅ | Ja |
| VirusTotal | ✅ | Ja |
| Censys | ✅ | Ja |
| PassiveTotal | Begrenzt | Ja |
| BinaryEdge | Begrenzt | Ja |

---

## 📊 Kurzreferenz

### Enumerationsbefehle

| Befehl | Beschreibung |
|---------|-------------|
| `amass enum -passive -d domain.com` | Passive Erfassung von Subdomains |
| `amass enum -active -d domain.com` | Aktive DNS-Erfassung |
| `amass enum -active -brute -d domain.com` | Subdomains systematisch ausprobieren |
| `amass enum -d domain.com -o out.txt` | In eine Datei speichern |
| `amass enum -d domain.com -json out.json` | JSON-Ausgabe |

### Intel-Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `amass intel -org "Company"` | Ressourcen anhand des Organisationsnamens finden |
| `amass intel -asn 12345` | Anhand einer ASN erfassen |
| `amass intel -cidr 10.0.0.0/8` | CIDR-Bereich erfassen |
| `amass intel -whois -d domain.com` | Umgekehrte WHOIS-Abfrage |

### Wichtige Optionen

| Option | Beschreibung |
|------|-------------|
| `-d` | Zieldomain |
| `-df` | Domains aus einer Datei |
| `-o` | Ausgabedatei |
| `-passive` | Nur passiv (laut Vorlage ohne DNS) |
| `-active` | Aktives DNS aktivieren |
| `-brute` | DNS-Namen systematisch ausprobieren |
| `-src` | Datenquelle anzeigen |
| `-timeout` | Zeitlimit in Minuten |

---

## 🎯 Bug-Bounty-Arbeitsablauf

```bash
# 1. Passive Erfassung (laut Vorlage unbedenklich)
amass enum -passive -d target.com -o passive_subs.txt

# 2. Aktiv und mit systematischem Ausprobieren
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. Ressourcen der Organisation finden
amass intel -org "Target Inc" -o org_domains.txt

# 4. Ergebnisse zusammenführen
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. Erreichbare Hosts ermitteln
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

## 📚 Ressourcen

- [OWASP Amass GitHub](https://github.com/owasp-amass/amass)
- [Amass-Benutzerhandbuch (ältere Version)](https://github.com/owasp-amass/amass/wiki/User-Guide)

### Verwandte Spickzettel
- [Bug-Bounty-Methodik](../../Bug-Bounty-Methodology/translations/README.de.md)
- [Subfinder](../../Subfinder/translations/README.de.md)
- [httpx](../../httpx/translations/README.de.md)

---

<p align="center">
  <b>🌐 Die Angriffsfläche erfassen!</b><br>
  <i>Amass – laut Vorlage ein besonders umfassendes Werkzeug für Subdomains</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Versionen und Quellen:** Die Vorlage mischt ältere CLI-Beispiele mit einem Installationspfad für v4. Die verfügbaren Unterbefehle, Optionen, Datenquellen und Konfigurationsformate unterscheiden sich nach Version. Die verlinkte Wiki-Anleitung stammt aus 2020; sie ist keine aktuelle universelle Referenz. Zahlen wie „55+“ und „am umfassendsten“ sind historische Angaben beziehungsweise Wertungen der Vorlage, kein hier durchgeführter Vergleich. Der Dateipfad unter „Speicherort“ ist eine Referenzangabe, kein Shell-Aufruf. [Älteres offizielles Benutzerhandbuch](https://github.com/owasp-amass/amass/wiki/User-Guide).

> **Konkrete Optionsgrenzen:** Im älteren offiziellen Handbuch bezeichnet `-r` DNS-Resolver und benötigt deren Adressen; der gezeigte alleinstehende Schalter aktiviert keine Rekursion. `-active` und `-brute` sind dort getrennte Einstellungen. Auch `-max-depth` und die gezeigten `amass dns`-Aufrufe sind in dieser Referenz nicht dokumentiert und nicht als versionsunabhängige Syntax zu verstehen. Im Intel-Handbuch bezeichnet `-ip` die Anzeige gefundener IP-Adressen, nicht die Eingabe eines IP-Bereichs. Ein Organisationssuchtreffer ist eine Zuordnungshilfe, kein Eigentumsnachweis. Die Originalbefehle bleiben unverändert. [Offizielles Benutzerhandbuch: Optionen](https://github.com/owasp-amass/amass/wiki/User-Guide).

> **Konfiguration und Auswertung:** Die heutige Konfigurationsdokumentation beschreibt ein eigenes Schema und eine über `options.datasources` eingebundene Datei für Datenquellen. Das YAML der Vorlage ist deshalb nicht ohne Versionsprüfung übertragbar. API-Felder, Kontingente und Zugangsvoraussetzungen können sich ändern; die Beispielschlüssel bleiben Platzhalter. DNS-Auflösung und HTTP-Erreichbarkeit sind unterschiedliche Prüfungen; die letzte Verarbeitungskette verwendet HTTPX. Passive Methoden, hohe Abdeckung und eine kleine Anzahl von Anfragen sind keine Garantie vollständiger oder unbemerkter Ergebnisse. Es wurde weder ein Recon-Vorgang noch ein Beispiel ausgeführt. [Offizielle Konfiguration](https://owasp-amass.github.io/docs/configuration/index.html).
