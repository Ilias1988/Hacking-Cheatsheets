# 🚀 Gobuster – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗████████╗███████╗██████╗ 
  ██╔════╝ ██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗
  ██║  ███╗██║   ██║██████╔╝██║   ██║███████╗   ██║   █████╗  ██████╔╝
  ██║   ██║██║   ██║██╔══██╗██║   ██║╚════██║   ██║   ██╔══╝  ██╔══██╗
  ╚██████╔╝╚██████╔╝██████╔╝╚██████╔╝███████║   ██║   ███████╗██║  ██║
   ╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
                Werkzeug zum systematischen Testen von Verzeichnissen, DNS und VHosts
```

<p align="center">
  <img src="https://img.shields.io/badge/Gobuster-Enumeration-blue?style=for-the-badge" alt="Gobuster">
  <img src="https://img.shields.io/badge/Directory-Bruteforce-red?style=for-the-badge" alt="Directory">
  <img src="https://img.shields.io/badge/DNS-Subdomain-green?style=for-the-badge" alt="DNS">
  <img src="https://img.shields.io/badge/VHost-Discovery-purple?style=for-the-badge" alt="VHost">
</p>

<p align="center">
  <b>🔍 Schnelles, in Go geschriebenes Werkzeug zur Enumeration von Verzeichnissen, DNS und VHosts</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Übersicht der Modi](#-übersicht-der-modi)
- [Verzeichnismodus (dir)](#-verzeichnismodus-dir)
- [DNS-Modus (dns)](#-dns-modus-dns)
- [VHost-Modus (vhost)](#-vhost-modus-vhost)
- [Fuzz-Modus (fuzz)](#-fuzz-modus-fuzz)
- [Wortlisten](#-wortlisten)
- [Authentifizierung und Header](#-authentifizierung-und-header)
- [Leistungsoptimierung](#-leistungsoptimierung)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Gobuster** ist ein schnelles, in Go geschriebenes Brute-Force-Werkzeug mit mehreren parallelen Threads für:
- Enumeration von Verzeichnissen und Dateien auf Webservern
- Ermittlung von DNS-Subdomains
- Ermittlung virtueller Hosts (VHosts)
- URL-Fuzzing

### Warum Gobuster?

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Schnell** | In Go geschrieben, mit hoher Parallelität |
| **Flexibel** | Mehrere Modi für unterschiedliche Aufgaben |
| **Keine Rekursion** | Laut Vorlage übersichtliche Ergebnisse ohne zusätzliche Rekursion |
| **Stabil** | Behandelt Fehler kontrolliert |
| **Aktive Entwicklung** | Regelmäßige Aktualisierungen |

### Vergleich mit anderen Werkzeugen

| Werkzeug | Geschwindigkeit | Rekursion | Sprache |
|------|-------|-----------|----------|
| **Gobuster** | ⭐⭐⭐⭐⭐ | Nein | Go |
| Dirb | ⭐⭐ | Ja | C |
| Dirbuster | ⭐⭐⭐ | Ja | Java |
| Ffuf | ⭐⭐⭐⭐⭐ | Nein | Go |
| Feroxbuster | ⭐⭐⭐⭐⭐ | Ja | Rust |

---

## 📥 Installation

### Kali Linux (vorinstalliert)
```bash
# Gobuster aktualisieren
sudo apt update && sudo apt install gobuster
```

### Ubuntu/Debian
```bash
sudo apt install gobuster
```

### Aus dem Quellcode (Go)
```bash
go install github.com/OJ/gobuster/v3@latest
```

### Mit Snap
```bash
sudo snap install gobuster
```

### Aus GitHub-Releases
```bash
# Neueste Veröffentlichung herunterladen
wget https://github.com/OJ/gobuster/releases/latest/download/gobuster-linux-amd64.7z
7z x gobuster-linux-amd64.7z
chmod +x gobuster
sudo mv gobuster /usr/local/bin/
```

### Installation prüfen
```bash
gobuster version
gobuster -h
```

---

## 📊 Übersicht der Modi

### Verfügbare Modi

| Modus | Beschreibung | Anwendungsfall |
|------|-------------|----------|
| `dir` | Enumeration von Verzeichnissen und Dateien | Verborgene Verzeichnisse und Dateien finden |
| `dns` | Enumeration von DNS-Subdomains | Subdomains finden |
| `vhost` | Enumeration virtueller Hosts | Virtuelle Hosts finden |
| `fuzz` | URL-Fuzzing | Parameter und Pfade durch variierte Eingaben testen |
| `s3` | Enumeration von S3-Buckets | AWS-S3-Buckets finden |
| `gcs` | Google Cloud Storage | GCS-Buckets finden |
| `tftp` | TFTP-Enumeration | TFTP-Dateien finden |

### Grundsyntax
```bash
gobuster [mode] [options]

# Beispiele
gobuster dir -u http://target.com -w wordlist.txt
gobuster dns -d target.com -w subdomains.txt
gobuster vhost -u http://target.com -w vhosts.txt
```

---

## 📁 Verzeichnismodus (dir)

### Grundsyntax
```bash
gobuster dir -u <URL> -w <wordlist>
```

### Wichtige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-u` | Ziel-URL |
| `-w` | Pfad zur Wortliste |
| `-t` | Threads (Standard: 10) |
| `-x` | Zu suchende Dateiendungen |
| `-s` | Einzuschließende Statuscodes |
| `-b` | Auszuschließende Statuscodes (Sperrliste) |
| `-o` | Ausgabedatei |
| `-k` | Zertifikatsprüfung überspringen |
| `-r` | Weiterleitungen folgen |
| `-a` | User-Agent-Zeichenfolge |
| `-c` | Cookies |
| `-H` | Eigene Header |

### Einfacher Verzeichnisscan
```bash
# Einfacher Verzeichnisscan
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt

# Mit mehr Threads
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt -t 50
```

### Nach Dateiendungen suchen
```bash
# Nach bestimmten Dateiendungen suchen
gobuster dir -u http://target.com -w wordlist.txt -x php,html,txt

# Mehrere Dateiendungen
gobuster dir -u http://target.com -w wordlist.txt -x php,asp,aspx,jsp,html,txt,bak,old

# Sicherungsdateien
gobuster dir -u http://target.com -w wordlist.txt -x bak,backup,old,orig,~
```

### Statuscodes filtern
```bash
# Nur bestimmte Statuscodes anzeigen
gobuster dir -u http://target.com -w wordlist.txt -s 200,204,301,302,307,401

# Statuscodes ausschließen
gobuster dir -u http://target.com -w wordlist.txt -b 404,403

# Laut Original alle Ergebnisse anzeigen (einschließlich 404)
gobuster dir -u http://target.com -w wordlist.txt --no-status
```

### Ausgabeoptionen
```bash
# In einer Datei speichern
gobuster dir -u http://target.com -w wordlist.txt -o results.txt

# Ausführliche Ausgabe
gobuster dir -u http://target.com -w wordlist.txt -v

# Laut Original ohne Fortschrittsanzeige
gobuster dir -u http://target.com -w wordlist.txt -q

# Erweiterte Ausgabe (vollständige URLs)
gobuster dir -u http://target.com -w wordlist.txt -e
```

### SSL-/TLS-Optionen
```bash
# Zertifikatsprüfung überspringen
gobuster dir -u https://target.com -w wordlist.txt -k

# Zertifikatsprüfung mit zusätzlichen Optionen überspringen
gobuster dir -u https://target.com -w wordlist.txt -k --no-tls-validation
```

### Beispiele für den Verzeichnismodus
```bash
# Üblicher Scan nach Webverzeichnissen
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt -t 50

# PHP-Anwendung
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x php -t 50

# API-Endpunkte ermitteln
gobuster dir -u http://target.com/api -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt -t 30

# Sicherungsdateien finden
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x bak,backup,old,zip,tar.gz -t 50
```

---

## 🌐 DNS-Modus (dns)

### Grundsyntax
```bash
gobuster dns -d <domain> -w <wordlist>
```

### DNS-Optionen

| Option | Beschreibung |
|--------|-------------|
| `-d` | Zieldomain |
| `-w` | Pfad zur Wortliste |
| `-t` | Threads |
| `-r` | Eigener DNS-Resolver |
| `-i` | IP-Adressen anzeigen |
| `-c` | CNAME-Einträge anzeigen |
| `--wildcard` | Wildcard-Verarbeitung erzwingen |
| `--timeout` | DNS-Zeitlimit |

### Einfache DNS-Enumeration
```bash
# Einfacher Subdomain-Scan
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# IP-Adressen anzeigen
gobuster dns -d target.com -w subdomains.txt -i

# CNAME-Einträge anzeigen
gobuster dns -d target.com -w subdomains.txt -c
```

### Eigener DNS-Resolver
```bash
# Bestimmten DNS-Server verwenden
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8

# Laut Original mehrere Resolver
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8:53
```

### DNS-Beispiele
```bash
# Umfassender Subdomain-Scan
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt -t 50 -i

# Schneller Subdomain-Scan
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt -t 100

# Mit Ausgabedatei
gobuster dns -d target.com -w subdomains.txt -i -o subdomains_found.txt
```

---

## 🏠 VHost-Modus (vhost)

### Grundsyntax
```bash
gobuster vhost -u <URL> -w <wordlist>
```

### VHost-Optionen

| Option | Beschreibung |
|--------|-------------|
| `-u` | Ziel-URL |
| `-w` | Pfad zur Wortliste |
| `-t` | Threads |
| `--append-domain` | Domain an Einträge der Wortliste anhängen |
| `--domain` | Domain für den virtuellen Host |
| `-r` | Weiterleitungen folgen |

### Einfache VHost-Enumeration
```bash
# Einfacher VHost-Scan
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Mit angehängter Domain
gobuster vhost -u http://target.com -w subdomains.txt --append-domain
```

### VHost-Beispiele
```bash
# Übliche VHost-Ermittlung
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/namelist.txt -t 50

# Entwicklungs-Hosts
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt --append-domain

# Mit eigener Domain
gobuster vhost -u http://10.10.10.10 -w subdomains.txt --domain target.htb
```

---

## 🔧 Fuzz-Modus (fuzz)

### Grundsyntax
```bash
gobuster fuzz -u <URL with FUZZ> -w <wordlist>
```

### Fuzz-Optionen

| Option | Beschreibung |
|--------|-------------|
| `-u` | URL mit dem Schlüsselwort FUZZ |
| `-w` | Pfad zur Wortliste |
| `-t` | Threads |
| `-b` | Statuscodes ausschließen |
| `--exclude-length` | Nach Antwortlänge ausschließen |

### Fuzz-Beispiele
```bash
# Parameterwert durch variierte Eingaben testen
gobuster fuzz -u "http://target.com/page?id=FUZZ" -w numbers.txt

# Pfad durch variierte Eingaben testen
gobuster fuzz -u "http://target.com/FUZZ/admin" -w wordlist.txt

# Bestimmte Antwortlängen ausschließen
gobuster fuzz -u "http://target.com/FUZZ" -w wordlist.txt --exclude-length 0,42
```

---

## 📚 Wortlisten

### SecLists-Verzeichnisse (Kali Linux)

```bash
# Enumeration von Verzeichnissen und Dateien
/usr/share/seclists/Discovery/Web-Content/

# Häufig verwendete Verzeichnislisten
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-small.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# DNS und Subdomains
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

### Standardwortlisten

```bash
# Dirb-Wortlisten
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
/usr/share/wordlists/dirb/small.txt

# Dirbuster-Wortlisten
/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
/usr/share/wordlists/dirbuster/directory-list-2.3-small.txt
```

### Empfehlungen für Wortlisten

| Anwendungsfall | Wortliste |
|----------|----------|
| Schneller Scan | `common.txt` |
| Standard | `directory-list-2.3-medium.txt` |
| Gründlicher Scan | `directory-list-2.3-big.txt` |
| API | `api-endpoints.txt` |
| Subdomains | `subdomains-top1million-5000.txt` |
| Dateien | `raft-large-files.txt` |

---

## 🔐 Authentifizierung und Header

### Basic Authentication (HTTP-Basisauthentifizierung)
```bash
# HTTP-Basisauthentifizierung
gobuster dir -u http://target.com -w wordlist.txt -U username -P password
```

### Authentifizierung mit Cookies
```bash
# Ein einzelnes Cookie
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123"

# Mehrere Cookies
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123; token=xyz789"

# PHP-Sitzung
gobuster dir -u http://target.com -w wordlist.txt -c "PHPSESSID=abc123def456"
```

### Eigene Header
```bash
# Ein einzelner Header
gobuster dir -u http://target.com -w wordlist.txt -H "Authorization: Bearer token123"

# Mehrere Header
gobuster dir -u http://target.com -w wordlist.txt -H "X-Forwarded-For: 127.0.0.1" -H "X-Custom: value"

# API-Schlüssel
gobuster dir -u http://target.com/api -w wordlist.txt -H "X-API-Key: your-api-key"
```

### User-Agent
```bash
# Eigener User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Googlebot
gobuster dir -u http://target.com -w wordlist.txt -a "Googlebot/2.1"
```

### Proxy-Unterstützung
```bash
# HTTP-Proxy
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080

# SOCKS5-Proxy
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050

# Burp-Suite-Proxy
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080 -k
```

---

## ⚡ Leistungsoptimierung

### Threads verwalten
```bash
# Standard: 10 Threads
gobuster dir -u http://target.com -w wordlist.txt

# Mehr Threads verwenden (schnelles Netzwerk)
gobuster dir -u http://target.com -w wordlist.txt -t 100

# Weniger Threads verwenden (langsam oder instabil)
gobuster dir -u http://target.com -w wordlist.txt -t 5
```

### Zeitlimits konfigurieren
```bash
# Zeitlimit erhöhen
gobuster dir -u http://target.com -w wordlist.txt --timeout 30s

# DNS-Zeitlimit
gobuster dns -d target.com -w subdomains.txt --timeout 5s
```

### Verzögerung zwischen Anfragen
```bash
# Verzögerung hinzufügen (Anfragerate begrenzen)
gobuster dir -u http://target.com -w wordlist.txt --delay 100ms

# Langsamer vorgehen, laut Vorlage zur WAF-Umgehung
gobuster dir -u http://target.com -w wordlist.txt --delay 500ms
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: Übliche Web-Enumeration
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
    -t 50 \
    -x php,html,txt \
    -o results.txt
```

### Beispiel 2: API-Endpunkte ermitteln
```bash
gobuster dir -u http://target.com/api/v1 \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -t 30 \
    -H "Content-Type: application/json"
```

### Beispiel 3: Scan mit Authentifizierung
```bash
gobuster dir -u http://target.com/admin \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -c "PHPSESSID=abc123" \
    -t 20
```

### Beispiel 4: Subdomains erfassen
```bash
gobuster dns -d target.com \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt \
    -t 100 \
    -i \
    -o subdomains.txt
```

### Beispiel 5: VHosts ermitteln (HackTheBox)
```bash
gobuster vhost -u http://10.10.10.10 \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    --domain target.htb \
    --append-domain
```

### Beispiel 6: Über den Burp-Proxy
```bash
gobuster dir -u https://target.com \
    -w /usr/share/wordlists/dirb/common.txt \
    --proxy http://127.0.0.1:8080 \
    -k \
    -t 20
```

### Beispiel 7: Nach Sicherungsdateien suchen
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -x bak,backup,old,orig,save,swp,txt,zip,tar,gz,sql \
    -t 50
```

### Beispiel 8: CMS erkennen
```bash
# WordPress
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/wordpress.fuzz.txt \
    -t 50

# Joomla
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/joomla-plugins.fuzz.txt
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Aufgabe | Befehl |
|------|---------|
| Einfacher Verzeichnisscan | `gobuster dir -u URL -w wordlist.txt` |
| Mit Dateiendungen | `gobuster dir -u URL -w wordlist.txt -x php,html` |
| Subdomain-Scan | `gobuster dns -d domain.com -w wordlist.txt` |
| VHost-Scan | `gobuster vhost -u URL -w wordlist.txt` |
| Mit Ausgabedatei | `gobuster dir -u URL -w wordlist.txt -o output.txt` |
| Zertifikatsfehler ignorieren | `gobuster dir -u URL -w wordlist.txt -k` |
| Mit Cookies | `gobuster dir -u URL -w wordlist.txt -c "session=x"` |
| Über einen Proxy | `gobuster dir -u URL -w wordlist.txt --proxy http://127.0.0.1:8080` |

### Häufige Optionen

| Option | Kurzform | Beschreibung |
|--------|-------|-------------|
| `--url` | `-u` | Ziel-URL |
| `--wordlist` | `-w` | Wortlistendatei |
| `--threads` | `-t` | Anzahl der Threads |
| `--extensions` | `-x` | Dateiendungen |
| `--status-codes` | `-s` | Einzuschließende Statuscodes |
| `--cookies` | `-c` | Zu sendende Cookies |
| `--output` | `-o` | Ausgabedatei |
| `--insecuressl` | `-k` | Zertifikatsprüfung überspringen |
| `--verbose` | `-v` | Ausführliche Ausgabe |
| `--quiet` | `-q` | Reduzierte Ausgabe |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise für effizientes Arbeiten

1. **Klein beginnen und schrittweise erweitern**
```bash
   # Zuerst ein schneller Scan
   gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt
   
   # Anschließend eine größere Wortliste
   gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
```

2. **Zum Ziel passende Dateiendungen**
```bash
   # PHP-Website
   gobuster dir -u http://target.com -w wordlist.txt -x php
   
   # ASP.NET-Website
   gobuster dir -u http://target.com -w wordlist.txt -x asp,aspx
```

3. **Mit anderen Werkzeugen kombinieren**
```bash
   # Ablauf mit Nmap und Gobuster
   nmap -sV -p 80,443 target.com
   gobuster dir -u http://target.com -w wordlist.txt
```

### Vermeidung einer Erkennung

```bash
# Langsamer Scan mit Verzögerung
gobuster dir -u http://target.com -w wordlist.txt -t 5 --delay 200ms

# Eigener User-Agent
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0..."

# Über Tor
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Gobuster ist ein leistungsfähiges Enumerationswerkzeug und darf nur für **autorisierte Tests** eingesetzt werden.
> 
> - ✅ Eigene Systeme testen
> - ✅ Mit ausdrücklicher schriftlicher Genehmigung testen
> - ✅ In CTF-Aufgaben und Laborumgebungen einsetzen
> - ❌ Nie ohne Genehmigung scannen
> - ❌ Unbefugte Enumeration kann rechtswidrig sein
> 
> **Vor dem Scannen eines Systems immer eine Genehmigung einholen.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [Gobuster GitHub](https://github.com/OJ/gobuster)
- [Gobuster Wiki](https://github.com/OJ/gobuster/wiki)

### Wortlisten
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)

### Verwandte Spickzettel
- [Nmap](../../Nmap/translations/README.de.md)
- [Burp Suite](../../Burp-Suite/README.md)
- [SQLMap](../../SQLMap/README.md)

---

<p align="center">
  <b>🚀 Web-Enumeration beherrschen!</b><br>
  <i>Entdecken, was anderen verborgen bleibt</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Filter und Anzeige:** `--no-status` blendet nur die Statuscodes in der Ausgabe aus; es schaltet den Statusfilter nicht ab. Die gleichzeitige Nutzung von `-s` und der voreingestellten Sperrliste kann einen Fehler auslösen. `-q` und Fortschrittsanzeige sind unterschiedliche Einstellungen. [Gobuster: dir-Optionen](https://github.com/OJ/gobuster/blob/master/cli/dir/dir.go).

> **Versionsbezug:** Die Vorlage mischt CLI-Versionen. Seit 3.7 ersetzt `--debug` die frühere Verbose-Option; auch DNS-Kurzoptionen haben sich geändert. Die Tabelle nennt `--insecuressl`; der geprüfte v3.6-Code verwendet für `-k` dagegen `--no-tls-validation`. Beide Angaben bleiben als Originalreferenz erhalten. Download-Assetnamen sind versionsabhängig. [HTTP-Optionen in v3.6](https://github.com/OJ/gobuster/blob/v3.6.0/cli/cmd/http.go). Die Originalzeilen bleiben unverändert. [Gobuster-Projekt und Änderungsverlauf](https://github.com/OJ/gobuster).

> **Interpretation:** *Enumeration* bedeutet systematisches Erfassen; *Fuzzing* testet variierte Eingaben. Ein *VHost* ist ein virtueller Host. `8.8.8.8:53` bezeichnet einen Resolver mit Port, nicht mehrere Resolver. Ein VHost-Wortlisteneintrag muss zur gewählten Domain-Anhängung passen. Antwortcodes, Weiterleitungen und Antwortlängen beweisen allein keinen tatsächlich vorhandenen Inhalt. Threads, Verzögerung, Tor und User-Agent-Wechsel garantieren weder Vollständigkeit noch eine WAF-Umgehung oder fehlende Erkennung.

> **Vergleichstabelle:** Die Leistungssterne sind Wertungen der Vorlage. Die Angabe „keine Rekursion“ für ffuf ist unzutreffend: ffuf unterstützt Rekursion. Wortlistenpfade und ihre Verfügbarkeit hängen von Installation und Version ab. [ffuf-Dokumentation](https://github.com/ffuf/ffuf).
