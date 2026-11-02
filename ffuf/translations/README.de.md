# 🚀 ffuf – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   __  __        __ 
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_ 
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|  
                    
   Fuzz Faster U Fool
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ Ein schnelles, in Go geschriebenes Werkzeug für Web-Fuzzing</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Grundsyntax](#-grundsyntax)
- [Verzeichnis-Fuzzing](#-verzeichnis-fuzzing)
- [Subdomain-Fuzzing](#-subdomain-fuzzing)
- [Parameter-Fuzzing](#-parameter-fuzzing)
- [Fuzzing von POST-Daten](#-fuzzing-von-post-daten)
- [Filtern und Auswählen](#-filtern-und-auswählen)
- [Erweiterte Funktionen](#-erweiterte-funktionen)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**ffuf** (Fuzz Faster U Fool) ist ein schnelles, in Go geschriebenes Werkzeug für Web-Fuzzing. Es wird bei Bug-Bounty-Tests zur systematischen Suche nach Verzeichnissen, virtuellen Hosts und Parametern verwendet.

### Wichtige Funktionen

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Geschwindigkeit** | Sehr schnell (in Go geschrieben) |
| **Flexibel** | Beliebige Teile einer HTTP-Anfrage durch variierte Eingaben testen |
| **Filterung** | Mehrere Optionen zum Filtern und Auswählen von Ergebnissen |
| **Rekursion** | Unterstützung rekursiver Scans |
| **Ausgabe** | Mehrere Ausgabeformate |
| **Einfach** | Einfache, verständliche Syntax |

### ffuf im Vergleich mit anderen Werkzeugen

| Eigenschaft | ffuf | Gobuster | dirb |
|---------|------|----------|------|
| **Geschwindigkeit** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **Flexibilität** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **POST-Fuzzing** | ✅ Ja | ❌ Nein | ❌ Nein |
| **Rekursion** | ✅ Ja | ✅ Ja | ✅ Ja |
| **Filter** | Viele | Begrenzt | Begrenzt |

---

## 📥 Installation

### Mit Go
```bash
go install github.com/ffuf/ffuf/v2@latest
```

### Kali Linux
```bash
sudo apt install ffuf
```

### Binärdatei herunterladen
```bash
# Aus den Releases herunterladen
https://github.com/ffuf/ffuf/releases

# Linux
wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

### Installation prüfen
```bash
ffuf -V
ffuf -h
```

---

<a name="-grundsyntax"></a>

## ⌨️ Grundsyntax

### Allgemeine Syntax
```bash
ffuf [options] -u <URL/FUZZ> -w <wordlist>
```

### Das Schlüsselwort FUZZ

Das Schlüsselwort **FUZZ** legt fest, an welcher Stelle ffuf Einträge aus der Wortliste einsetzt.

```bash
# Im URL-Pfad
ffuf -u https://target.com/FUZZ -w wordlist.txt

# In einer Subdomain
ffuf -u https://FUZZ.target.com -w wordlist.txt

# Im Parameterwert
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# In POST-Daten
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

### Wichtige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-u` | Ziel-URL mit dem Schlüsselwort FUZZ |
| `-w` | Pfad zur Wortliste |
| `-o` | Ausgabedatei |
| `-of` | Ausgabeformat (json, csv, html) |
| `-t` | Anzahl der Threads (Standard laut Vorlage: 40) |
| `-p` | Verzögerung zwischen Anfragen |
| `-r` | Weiterleitungen folgen |
| `-v` | Ausführliche Ausgabe |
| `-s` | Reduzierte Ausgabe |

---

## 📁 Verzeichnis-Fuzzing

### Einfache Verzeichnissuche

```bash
# Einfaches Verzeichnis-Fuzzing
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# Mit Dateiendungen
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# Mehrere Dateiendungen
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

### Rekursive Scans

```bash
# Rekursion aktivieren
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# Rekursionstiefe
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# Rekursion mit Strategie
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

### Nach Statuscode filtern

```bash
# Nur Antworten mit Statuscode 200 auswählen
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# Mehrere Statuscodes auswählen
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# 404-Antworten ausschließen (Änderung des Standardverhaltens laut Vorlage)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# Alles auswählen, 404 ausschließen
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Häufig verwendete Wortlisten

```bash
# SecLists
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
```

---

## 🌐 Subdomain-Fuzzing

### Virtuelle Hosts ermitteln

```bash
# Einfaches Subdomain-Fuzzing
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# Nach Antwortgröße filtern (Standardseite ausblenden)
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# Mit einer bestimmten Wortliste
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

### DNS-Subdomains systematisch ausprobieren

```bash
# Subdomain in der URL
ffuf -u https://FUZZ.target.com -w subdomains.txt

# Mit Resolver laut Vorlage
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

### Häufige Wortlisten für Subdomains

```bash
# SecLists DNS
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

---

## 🔍 Parameter-Fuzzing

### Fuzzing von GET-Parametern

```bash
# Parameternamen durch variierte Eingaben testen
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# Parameterwert durch variierte Eingaben testen
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# Mehrere Parameter
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

### Parameter ermitteln

```bash
# Verborgene Parameter finden
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

## 📝 Fuzzing von POST-Daten

### Einfaches POST-Fuzzing

```bash
# POST mit Formulardaten
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# POST mit Content-Type
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

### Fuzzing von JSON-POST-Daten

```bash
# JSON-POST-Daten
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

### Mehrere Wortlisten (Credential Stuffing laut Vorlage)

```bash
# Benutzernamen und Passwörter durch variierte Eingaben testen
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# Modus: clusterbomb (alle Kombinationen)
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

## 🎯 Filtern und Auswählen

### Filteroptionen

| Option | Beschreibung | Beispiel |
|--------|-------------|---------|
| `-fc` | Nach Statuscode ausschließen | `-fc 404,403` |
| `-fs` | Nach Antwortgröße ausschließen | `-fs 1234` |
| `-fw` | Nach Wortanzahl ausschließen | `-fw 100` |
| `-fl` | Nach Zeilenanzahl ausschließen | `-fl 50` |
| `-ft` | Nach Antwortzeit ausschließen (ms) | `-ft 5000` |
| `-fr` | Nach regulärem Ausdruck ausschließen | `-fr "error"` |

### Auswahloptionen (Matcher)

| Option | Beschreibung | Beispiel |
|--------|-------------|---------|
| `-mc` | Nach Statuscode auswählen | `-mc 200,301` |
| `-ms` | Nach Antwortgröße auswählen | `-ms 1234` |
| `-mw` | Nach Wortanzahl auswählen | `-mw 100` |
| `-ml` | Nach Zeilenanzahl auswählen | `-ml 50` |
| `-mt` | Nach Antwortzeit auswählen (ms) | `-mt 5000` |
| `-mr` | Nach regulärem Ausdruck auswählen | `-mr "success"` |

### Filterbeispiele

```bash
# 404 und 403 ausschließen
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404,403

# Nach Größe filtern (Standardseite ausblenden)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fs 0,1234

# Nach Wortanzahl filtern
ffuf -u https://target.com/FUZZ -w wordlist.txt -fw 10

# Alles auswählen, bestimmte Ergebnisse ausschließen
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Automatische Kalibrierung

```bash
# Filter automatisch kalibrieren
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# Kalibrierung mit einer bestimmten Zeichenfolge
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

## ⚡ Erweiterte Funktionen

### Anfragerate begrenzen

```bash
# Anfragen pro Sekunde
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# Verzögerung zwischen Anfragen
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# Zufällige Verzögerung (Jitter)
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

### Threads

```bash
# Threadzahl festlegen (Standard laut Vorlage: 40)
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# Für laut Vorlage unauffälligere Tests verringern
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

### Header

```bash
# Eigener Header
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# Mehrere Header
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# Cookie
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

### Proxy

```bash
# HTTP-Proxy
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080

# Burp Suite
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080
```

### Ausgabe

```bash
# JSON-Ausgabe
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# CSV-Ausgabe
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.csv -of csv

# HTML-Ausgabe
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# Alle Formate
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

### Zeitlimits und Wiederholungen

```bash
# Zeitlimit einer Anfrage
ffuf -u https://target.com/FUZZ -w wordlist.txt -timeout 10

# Bei 403 stoppen laut Vorlage
ffuf -u https://target.com/FUZZ -w wordlist.txt -se

# Zertifikatsprüfung deaktivieren
ffuf -u https://target.com/FUZZ -w wordlist.txt -k
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: Einfacher Verzeichnisscan
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

### Beispiel 2: PHP-Dateien finden
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

### Beispiel 3: Subdomains erfassen
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

### Beispiel 4: API-Endpunkte ermitteln
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

### Beispiel 5: Parameter ermitteln
```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

### Beispiel 6: Anmeldungen systematisch testen
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

### Beispiel 7: Mit Proxy (Burp)
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

### Beispiel 8: Rekursiver Scan
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2 -mc 200,301
```

### Beispiel 9: Scan mit begrenzter Anfragerate
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 10 -t 5
```

### Beispiel 10: Umfassender Bug-Bounty-Scan laut Vorlage
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Aufgabe | Befehl |
|------|---------|
| Verzeichnisscan | `ffuf -u URL/FUZZ -w wordlist.txt` |
| Mit Dateiendungen | `ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html` |
| Subdomain-Test | `ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt` |
| POST-Fuzzing | `ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt` |
| 404-Antworten ausschließen | `ffuf -u URL/FUZZ -w wordlist.txt -fc 404` |
| Nach Größe ausschließen | `ffuf -u URL/FUZZ -w wordlist.txt -fs 1234` |
| Automatisch kalibrieren | `ffuf -u URL/FUZZ -w wordlist.txt -ac` |
| Ausgabe speichern | `ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json` |

### Häufige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-u` | URL mit FUZZ |
| `-w` | Wortliste |
| `-e` | Dateiendungen |
| `-t` | Threads |
| `-mc` | Statuscodes auswählen |
| `-fc` | Statuscodes ausschließen |
| `-fs` | Nach Größe ausschließen |
| `-ac` | Automatisch kalibrieren |
| `-r` | Weiterleitungen folgen |
| `-o` | Ausgabedatei |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise für Bug Bounty

1. **Laut Vorlage zuerst automatisch kalibrieren**
   ```bash
   ffuf -u target.com/FUZZ -w wordlist.txt -ac
   ```

2. **Mehrere Dateiendungen verwenden**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **Häufige Endungen von Sicherungsdateien prüfen**
   ```bash
   -e .bak,.old,.orig,.backup,~,.swp
   ```

4. **Anfragerate laut Vorlage zur WAF-Umgehung begrenzen**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

### Hinweise zur Leistung

```bash
# Hohe Geschwindigkeit (ohne WAF laut Vorlage)
ffuf -u URL/FUZZ -w wordlist.txt -t 200

# Geringe Anfragerate und langsames Vorgehen (laut Vorlage unauffällig)
ffuf -u URL/FUZZ -w wordlist.txt -t 10 -rate 5 -p 1
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** ffuf ist ein leistungsfähiges Fuzzing-Werkzeug, das ausschließlich für **autorisierte Tests** eingesetzt werden darf.
> 
> - ✅ Nur Programme mit entsprechender Testgenehmigung prüfen
> - ✅ In autorisierten Bug-Bounty-Programmen einsetzen
> - ✅ Anfrageraten und vereinbarten Prüfumfang beachten
> - ❌ Niemals ohne Genehmigung Fuzzing durchführen
> - ❌ Keine Dienstverweigerung verursachen
> 
> **Scans ohne Genehmigung sind laut Vorlage rechtswidrig.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [ffuf GitHub](https://github.com/ffuf/ffuf)
- [ffuf Wiki](https://github.com/ffuf/ffuf/wiki)

### Wortlisten
- [SecLists](https://github.com/danielmiessler/SecLists)
- [Assetnote-Wortlisten](https://wordlists.assetnote.io/)

### Verwandte Spickzettel
- [Nuclei](../../Nuclei/translations/README.de.md)
- [Gobuster](../../Gobuster/translations/README.de.md)
- [Nmap](../../Nmap/translations/README.de.md)

---

<p align="center">
  <b>🚀 Schnell und sorgfältig mit Fuzzing prüfen!</b><br>
  <i>Ein vielseitiges Werkzeug für Bug-Bounty-Tests</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Optionen und Filter:** `-r` folgt Weiterleitungen; es richtet keinen DNS-Resolver ein. `-se` stoppt bei bestimmten Fehlern, nicht gezielt bei HTTP 403. Dafür beschreibt ffuf `-sf` mit einer Schwelle von über 95 Prozent 403-Antworten. Matcher wählen Ergebnisse aus, danach können Filter sie ausschließen; `-fc 404` erweitert die voreingestellte Auswahl nicht automatisch auf alle anderen Statuscodes. `-ft` und `-mt` vergleichen die Zeit bis zum ersten Antwortbyte und erwarten passende Vergleichsausdrücke. Die ursprünglichen Beispiele bleiben unverändert. [ffuf: Optionen](https://github.com/ffuf/ffuf).

> **Mehrere Wortlisten und Anfragen:** Der Standardmodus `clusterbomb` bildet Kombinationen, nicht automatisch zeilenweise zugehörige Benutzername-Passwort-Paare. Die Überschrift „Credential Stuffing“ vereinfacht diesen Unterschied. Bei POST-Daten muss der Content-Type zur Anwendung passen; ffuf leitet ihn nicht automatisch aus dem Inhalt ab. [ffuf-Wiki: Wortlisten und Anfragen](https://github.com/ffuf/ffuf/wiki).

> **Rekursion und Einordnung:** Rekursion setzt laut Dokumentation eine URL mit abschließendem FUZZ voraus; Strategie und Treffer entscheiden über weitere Aufgaben. VHost-Prüfungen verändern den Host-Header und sind keine eigenständige DNS-Abfrage. Antwortgröße, Statuscode und Kalibrierung sind Heuristiken, keine Beweise für vorhandene Inhalte oder eine Schwachstelle. Leistungssterne und Werkzeugvergleiche sind Wertungen der Vorlage, keine hier durchgeführten Messungen. [ffuf: Rekursion](https://github.com/ffuf/ffuf).

> **Vergleichstabelle:** Die Angaben zu anderen Werkzeugen sind keine aktuelle Funktionsmatrix. Gobuster unterstützt seit Version 3.3 unter anderem POST-Daten-Fuzzing, entgegen dem „Nein“ in der Tabelle. [Gobuster: Änderungsverlauf](https://github.com/OJ/gobuster). Auch das Rekursions-„Ja“ ist keine allgemeine Versionszusage: Die Erweiterung wird in einem zum Prüfzeitpunkt offenen Pull Request entwickelt. [Gobuster: Rekursionsentwurf](https://github.com/OJ/gobuster/pull/665).

> **Versionen und Voraussetzungen:** `v2.x.x`, Dateinamen, Wortlisten und Antwortgrößen sind vorausgesetzte Musterwerte. Eine bloße Release-URL ist kein Shell-Aufruf. Einzelne Optionszeilen sind Fragmente, keine vollständigen Programme. Weniger Threads oder Verzögerungen garantieren weder eine WAF-Umgehung noch fehlende Erkennung. Kein Beispiel wurde ausgeführt.
