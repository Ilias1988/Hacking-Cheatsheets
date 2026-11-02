# 🌐 httpx – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

```
  _     _   _           
 | |__ | |_| |_ _ __ __  __
 | '_ \| __| __| '_ \\ \/ /
 | | | | |_| |_| |_) |>  < 
 |_| |_|\__|\__| .__//_/\_\
               |_|         
                           
   Schnelle HTTP-Prüfung und Werkzeugkasten
```

<p align="center">
  <img src="https://img.shields.io/badge/httpx-HTTP_Probe-red?style=for-the-badge" alt="httpx">
  <img src="https://img.shields.io/badge/Fast-Toolkit-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/ProjectDiscovery-green?style=for-the-badge" alt="ProjectDiscovery">
</p>

<p align="center">
  <b>⚡ Schnelles, vielseitiges HTTP-Werkzeug von ProjectDiscovery</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Grundsyntax](#-grundsyntax)
- [HTTP-Erreichbarkeit und Erfassung](#-http-erreichbarkeit-und-erfassung)
- [Informationen auslesen](#-informationen-auslesen)
- [Filtern und Auswählen](#-filtern-und-auswählen)
- [Ausgabeoptionen](#-ausgabeoptionen)
- [Erweiterte Funktionen](#-erweiterte-funktionen)
- [Bug-Bounty-Arbeitsablauf](#-bug-bounty-arbeitsablauf)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**httpx** ist ein schnelles, vielseitiges HTTP-Werkzeug. Es verwendet die Bibliothek retryablehttp, um unterschiedliche Prüfungen auszuführen, und richtet sich an Bug-Bounty-Tester und Sicherheitsforscher.

### Wichtige Funktionen

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Schnell** | In Go geschriebenes Werkzeug mit hoher Parallelität |
| **Mehrere Prüfungen** | Statuscode, Seitentitel, Technologien und Header |
| **Technologieerkennung** | Erkennung anhand von Wappalyzer-Daten |
| **Bildschirmaufnahme** | Bildschirmaufnahmen mit einem Browser ohne grafische Oberfläche |
| **Verarbeitungsketten** | Mit subfinder und nuclei kombinierbar |
| **JSON-Ausgabe** | Unterstützung strukturierter Ausgaben |

### httpx im Vergleich mit anderen Werkzeugen

| Eigenschaft | httpx | curl | wget |
|---------|-------|------|------|
| **Geschwindigkeit** | ⭐⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **Parallelität** | ✅ Ja | ❌ Nein | ❌ Nein |
| **Technologieerkennung** | ✅ Ja | ❌ Nein | ❌ Nein |
| **Bildschirmaufnahmen** | ✅ Ja | ❌ Nein | ❌ Nein |
| **Verarbeitungsketten** | ✅ Ja | ⚠️ Manuell | ⚠️ Manuell |

---

## 📥 Installation

### Mit Go
```bash
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest
```

### Kali Linux
```bash
sudo apt install httpx-toolkit
```

### Binärdatei herunterladen
```bash
# Aus den Releases herunterladen
https://github.com/projectdiscovery/httpx/releases

# Linux
wget https://github.com/projectdiscovery/httpx/releases/download/v1.x.x/httpx_1.x.x_linux_amd64.zip
unzip httpx_1.x.x_linux_amd64.zip
sudo mv httpx /usr/local/bin/
```

### Docker
```bash
docker pull projectdiscovery/httpx:latest
docker run -it projectdiscovery/httpx -h
```

### Installation prüfen
```bash
httpx -version
httpx -h
```

---

<a name="-grundsyntax"></a>

## ⌨️ Grundsyntax

### Allgemeine Syntax
```bash
httpx [options] -u <url>
httpx [options] -l <file>
cat urls.txt | httpx [options]
```

### Wichtige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-u` | Einzelne Ziel-URL |
| `-l` | Datei mit einer URL-Liste |
| `-sc` | Statuscode anzeigen |
| `-title` | Seitentitel anzeigen |
| `-td` | Technologieerkennung |
| `-o` | Ausgabedatei |
| `-silent` | Reduzierte Ausgabe |
| `-json` | JSON-Ausgabe |

### Schnelleinstieg
```bash
# Einzelne URL prüfen
httpx -u https://example.com

# Ziele aus einer Datei prüfen
httpx -l urls.txt

# Aus der Standardeingabe (Verarbeitungskette)
cat hosts.txt | httpx

# Mit subfinder
subfinder -d example.com -silent | httpx
```

---

## 🔍 HTTP-Erreichbarkeit und Erfassung

### Grundlegende HTTP-Prüfung

```bash
# Hosts auf HTTP/HTTPS prüfen
cat hosts.txt | httpx

# Statuscode ausgeben
cat hosts.txt | httpx -sc

# Bestimmte Ports prüfen
cat hosts.txt | httpx -p 80,443,8080,8443

# Die angegebenen häufigen Ports prüfen
cat hosts.txt | httpx -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```

### Ports prüfen

```bash
# Eigene Ports
httpx -l hosts.txt -p 80,443,8080,8443

# Portbereich
httpx -l hosts.txt -p 80-100

# Häufige Webports
httpx -l hosts.txt -p http:80,https:443
```

### Protokollerkennung

```bash
# HTTP-/HTTPS-Prüfung
cat hosts.txt | httpx

# Weiterleitungen folgen
cat hosts.txt | httpx -follow-redirects

# Maximale Anzahl von Weiterleitungen
cat hosts.txt | httpx -follow-redirects -max-redirects 5

# Kein Fallback laut Vorlage
cat hosts.txt | httpx -no-fallback
```

---

## 📊 Informationen auslesen

### Statuscodes und Seitentitel

```bash
# Statuscode
httpx -l urls.txt -sc

# Seitentitel
httpx -l urls.txt -title

# Beides
httpx -l urls.txt -sc -title

# Inhaltslänge
httpx -l urls.txt -cl

# Webserver
httpx -l urls.txt -server

# Alle grundlegenden Informationen
httpx -l urls.txt -sc -title -cl -server
```

### Technologieerkennung

```bash
# Technologien erkennen
httpx -l urls.txt -td

# Technologieerkennung (laut Vorlage ausführlicher)
httpx -l urls.txt -tech-detect

# Mit weiteren Informationen
httpx -l urls.txt -sc -title -td
```

### Header und Antwort

```bash
# Antwortheader
httpx -l urls.txt -response-header

# Bestimmter Header
httpx -l urls.txt -include-response-header "Server,X-Powered-By"

# Hashwert des Antwortinhalts
httpx -l urls.txt -hash md5

# Vorschau des Antwortinhalts
httpx -l urls.txt -bp
```

### IP- und DNS-Informationen

```bash
# IP-Adresse
httpx -l urls.txt -ip

# CNAME
httpx -l urls.txt -cname

# CDN-Erkennung
httpx -l urls.txt -cdn

# ASN-Informationen
httpx -l urls.txt -asn

# Alle DNS-Informationen
httpx -l urls.txt -ip -cname -cdn -asn
```

### SSL-/TLS-Informationen

```bash
# TLS-Prüfung
httpx -l urls.txt -tls-probe

# TLS-Daten abrufen
httpx -l urls.txt -tls-grab

# Zertifikatsinformationen
httpx -l urls.txt -tlsgrab
```

### Favicon-Hash

```bash
# Favicon-Hash ermitteln (für Shodan-Suchen nutzbar)
httpx -l urls.txt -favicon
```

### Bildschirmaufnahmen erstellen

```bash
# Bildschirmaufnahmen erstellen
httpx -l urls.txt -screenshot

# Bildschirmaufnahme mit Ausgabeordner
httpx -l urls.txt -screenshot -screenshot-output ./screenshots/

# Optionen für den Browser ohne grafische Oberfläche
httpx -l urls.txt -screenshot -system-chrome
```

---

## 🎯 Filtern und Auswählen

### Nach Statuscode filtern

```bash
# Statuscodes auswählen
httpx -l urls.txt -mc 200,301,302

# Statuscodes ausschließen
httpx -l urls.txt -fc 404,403,500

# Alle auswählen, bestimmte ausschließen
httpx -l urls.txt -mc all -fc 404
```

### Nach Größe filtern

```bash
# Nach Inhaltslänge auswählen
httpx -l urls.txt -ml 1234

# Nach Inhaltslänge ausschließen
httpx -l urls.txt -fl 0

# Nach Zeilenanzahl auswählen
httpx -l urls.txt -mlc 100

# Nach Zeilenanzahl ausschließen
httpx -l urls.txt -flc 50
```

### Nach Inhalt filtern

```bash
# Antwortinhalt nach regulärem Ausdruck auswählen
httpx -l urls.txt -mr "admin|login"

# Antwortinhalt nach regulärem Ausdruck ausschließen
httpx -l urls.txt -fr "error|not found"

# Zeichenfolge auswählen
httpx -l urls.txt -ms "dashboard"
```

### Nach Technologie filtern

```bash
# Nach Technologie auswählen
httpx -l urls.txt -td -mt "WordPress,Nginx"

# Nach Technologie ausschließen
httpx -l urls.txt -td -ft "Apache"
```

---

## 📤 Ausgabeoptionen

### Ausgabeformate

```bash
# Klartextausgabe
httpx -l urls.txt -o results.txt

# JSON-Ausgabe
httpx -l urls.txt -json -o results.json

# JSON-Zeilen
httpx -l urls.txt -jsonl -o results.jsonl

# CSV-Ausgabe
httpx -l urls.txt -csv -o results.csv
```

### Ausgabefelder

```bash
# Antwort speichern
httpx -l urls.txt -sr -srd ./responses/

# Bildschirmaufnahmen speichern
httpx -l urls.txt -screenshot -ssd ./screenshots/

# Eigene Ausgabe
httpx -l urls.txt -o output.txt -sc -title -td
```

### Reduzierte und ausführliche Ausgabe

```bash
# Reduzierte Ausgabe (nur URLs laut Vorlage)
httpx -l urls.txt -silent

# Ausführliche Ausgabe
httpx -l urls.txt -v

# Diagnoseausgabe
httpx -l urls.txt -debug

# Keine Farben
httpx -l urls.txt -nc
```

---

## ⚡ Erweiterte Funktionen

### Threads und Begrenzung der Anfragerate

```bash
# Threads festlegen (Standard laut Vorlage: 50)
httpx -l urls.txt -threads 100

# Anfragerate begrenzen
httpx -l urls.txt -rate-limit 100

# Zeitlimit
httpx -l urls.txt -timeout 10

# Wiederholungsversuche
httpx -l urls.txt -retries 3
```

### Proxy-Unterstützung

```bash
# HTTP-Proxy
httpx -l urls.txt -proxy http://127.0.0.1:8080

# SOCKS-Proxy
httpx -l urls.txt -proxy socks5://127.0.0.1:9050

# Burp Suite
httpx -l urls.txt -proxy http://127.0.0.1:8080
```

### Eigene Header

```bash
# Eigener Header
httpx -l urls.txt -H "Authorization: Bearer token123"

# Mehrere Header
httpx -l urls.txt -H "X-Custom: value" -H "Cookie: session=abc"

# User-Agent
httpx -l urls.txt -H "User-Agent: Mozilla/5.0"
```

### Anfragemethoden

```bash
# HEAD-Anfrage (laut Vorlage schneller)
httpx -l urls.txt -method HEAD

# Eigene Methode
httpx -l urls.txt -method OPTIONS

# POST-Anfrage mit Inhalt
httpx -l urls.txt -method POST -body '{"test":"data"}'
```

### Fortsetzen und Aufteilung

```bash
# Ab letzter Position fortsetzen
httpx -l urls.txt -resume

# Fortschritt speichern
httpx -l urls.txt -resume -resume-file progress.txt
```

---

## 🔄 Bug-Bounty-Arbeitsablauf

### Vollständige Verarbeitungskette zur Aufklärung

```bash
# Schritt 1: Subdomains erfassen
subfinder -d target.com -o subs.txt

# Schritt 2: Erreichbare Hosts mit httpx prüfen
cat subs.txt | httpx -sc -title -td -o live.txt

# Schritt 3: Schwachstellenscan
nuclei -l live.txt -severity critical,high

# Einzeiler
subfinder -d target.com -silent | httpx -silent | nuclei -silent
```

### Ausführliche Aufklärung

```bash
# Umfassende Informationssammlung
subfinder -d target.com -silent | httpx \
    -sc \
    -title \
    -td \
    -ip \
    -cdn \
    -server \
    -follow-redirects \
    -o full-recon.txt
```

### Bildschirmaufnahmen aller erreichbaren Hosts

```bash
subfinder -d target.com -silent | httpx -silent -screenshot -ssd ./screenshots/
```

### Interessante Endpunkte finden

```bash
# Administrationsoberflächen finden
subfinder -d target.com -silent | httpx -title -mr "admin|login|dashboard"

# API-Endpunkte finden
cat subs.txt | httpx -path /api/ -mc 200,401,403

# Bestimmte Pfade prüfen
httpx -l hosts.txt -path /robots.txt -mc 200
```

### Gezielt nach Technologien suchen

```bash
# WordPress-Websites finden
subfinder -d target.com -silent | httpx -td -mt "WordPress"

# Nginx-Server finden
cat hosts.txt | httpx -server -ms "nginx"
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: Einfache HTTP-Prüfung
```bash
echo "https://example.com" | httpx -sc -title
```

### Beispiel 2: Umfassende Aufklärung ausgehend von Subdomains
```bash
subfinder -d hackerone.com -silent | httpx -sc -title -td -ip
```

### Beispiel 3: Bildschirmaufnahmen aller Hosts
```bash
httpx -l hosts.txt -screenshot -ssd ./screenshots/
```

### Beispiel 4: Nur erreichbare Hosts ermitteln
```bash
cat subs.txt | httpx -silent -o live.txt
```

### Beispiel 5: Technologieerkennung
```bash
httpx -l urls.txt -tech-detect -json -o tech.json
```

### Beispiel 6: Mit Proxy (Burp)
```bash
httpx -l urls.txt -proxy http://127.0.0.1:8080 -silent
```

### Beispiel 7: Eigene Ports
```bash
cat hosts.txt | httpx -p 80,443,8080,8443 -sc -title
```

### Beispiel 8: Nur Status 200 auswählen
```bash
cat subs.txt | httpx -mc 200 -silent
```

### Beispiel 9: JSON-Ausgabe zur Weiterverarbeitung
```bash
httpx -l urls.txt -json -sc -title -td -o results.json
```

### Beispiel 10: Vollständige Verarbeitungskette
```bash
subfinder -d target.com -silent | \
httpx -silent -sc -title -td -ip -cdn -server -json | \
tee results.json | \
jq -r '.url' | \
nuclei -severity critical,high
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Aufgabe | Befehl |
|------|---------|
| Einfache HTTP-Prüfung | `httpx -u url` |
| Aus einer Datei | `httpx -l urls.txt` |
| Statuscode | `httpx -l urls.txt -sc` |
| Seitentitel | `httpx -l urls.txt -title` |
| Technologie erkennen | `httpx -l urls.txt -td` |
| IP-Adresse | `httpx -l urls.txt -ip` |
| Bildschirmaufnahme | `httpx -l urls.txt -screenshot` |
| JSON-Ausgabe | `httpx -l urls.txt -json -o out.json` |

### Häufige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-u` | Einzelne URL |
| `-l` | Datei mit URL-Liste |
| `-sc` | Statuscode |
| `-title` | Seitentitel |
| `-td` | Technologie erkennen |
| `-ip` | IP-Adresse |
| `-cdn` | CDN-Erkennung |
| `-server` | Webserver |
| `-mc` | Statuscodes auswählen |
| `-fc` | Statuscodes ausschließen |
| `-json` | JSON-Ausgabe |
| `-silent` | Reduzierte Ausgabe |

### Häufige Verarbeitungsketten

| Verarbeitungskette | Befehl |
|----------|---------|
| Erreichbare Hosts | `subfinder -d target.com \| httpx -silent` |
| Mit Zusatzinformationen | `subfinder -d target.com \| httpx -sc -title -td` |
| An nuclei weitergeben | `httpx -l hosts.txt -silent \| nuclei` |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise für Bug Bounty

1. **Mit Subfinder verwenden**
   ```bash
   subfinder -d target.com -silent | httpx -silent -sc -title
   ```

2. **Umfassende Informationen erfassen**
   ```bash
   httpx -l hosts.txt -sc -title -td -ip -cdn -server
   ```

3. **Bildschirmaufnahmen zur Ersteinschätzung**
   ```bash
   httpx -l hosts.txt -screenshot
   ```

4. **JSON zur Weiterverarbeitung**
   ```bash
   httpx -l hosts.txt -json | jq '.url'
   ```

### Hinweise zur Leistung

```bash
# Hohe Geschwindigkeit
httpx -l hosts.txt -threads 100 -timeout 5

# Langsame Prüfung mit geringer Parallelität
httpx -l hosts.txt -threads 10 -rate-limit 20
```

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** httpx sendet HTTP-Anfragen an Ziele. Nur auf Systemen mit entsprechender Testgenehmigung einsetzen.
> 
> - ✅ Nur Programme mit entsprechender Testgenehmigung prüfen
> - ✅ Bug-Bounty-Ziele innerhalb des vereinbarten Umfangs
> - ✅ Eigene Domains
> - ❌ Niemals Ziele ohne Genehmigung prüfen
> - ✅ Anfrageraten einhalten
> 
> **Nicht genehmigte Prüfungen können rechtswidrig sein.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [httpx GitHub](https://github.com/projectdiscovery/httpx)
- [ProjectDiscovery](https://projectdiscovery.io/)
- [Dokumentation](https://docs.projectdiscovery.io/tools/httpx/)

### Verwandte Spickzettel
- [Subfinder](../../Subfinder/translations/README.de.md)
- [Nuclei](../../Nuclei/translations/README.de.md)
- [ffuf](../../ffuf/translations/README.de.md)

---

<p align="center">
  <b>🌐 HTTP-Endpunkte systematisch prüfen!</b><br>
  <i>Ein vielseitiges HTTP-Werkzeug für Bug-Bounty-Tests</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Versionen und Optionen:** Die Vorlage kombiniert ältere oder abweichende CLI-Angaben. In der geprüften offiziellen Hilfe ist `-include-response-header` ein Schalter für Header in der JSON-Ausgabe, keine Liste ausgewählter Header. `-method` zeigt die verwendete Methode an; die aktuelle Methodenwahl ist separat beschrieben. Die Flags `-response-header`, `-jsonl`, `-screenshot-output`, `-ssd`, `-resume-file`, `-mt` und `-ft` stehen nicht in dieser geprüften Optionsliste. Auch `-fr` bezeichnet dort „follow redirects“, nicht den Regex-Filter aus der Beispielbeschriftung. Die Originalzeilen bleiben unverändert. [ProjectDiscovery: httpx-Referenz](https://github.com/projectdiscovery/httpx/blob/dev/README.md).

> **Protokolle und Daten:** `-no-fallback` zeigt laut Dokumentation beide geprüften Protokolle HTTP und HTTPS; der Name bedeutet nicht einfach, dass nur die Eingabevariante geprüft wird. HTTP-Probing an Ports ist kein vollständiger allgemeiner Portscan. `-tls-probe` prüft zusätzliche Namen aus Zertifikaten; `-tls-grab` dient der TLS-Datenerfassung. Das Ausgabeformat von `-json` ist JSONL; der Dateiname allein ändert das Format nicht. Die Wiederaufnahme verwendet laut Hilfe `resume.cfg`. [Offizielle httpx-Verwendung](https://docs.projectdiscovery.io/opensource/httpx/usage), [Projekt-Hinweise](https://github.com/projectdiscovery/httpx).

> **Verarbeitungsketten und Auswertung:** Statuscodes, Titel, Inhaltsmuster und erkannte Technologien sind Hinweise, kein Beweis einer Sicherheitslücke. `-silent` unterdrückt Begleitmeldungen, entfernt aber nicht automatisch zusätzlich angeforderte Ergebnisfelder. Eine Datei mit Status-/Titelspalten ist nicht dasselbe wie eine reine URL-Liste; das nächste Werkzeug muss das konkrete Format verarbeiten können. HEAD ist nicht grundsätzlich schneller oder inhaltlich mit GET gleichwertig. Historische Benchmark-Sterne und der Vergleich „curl ohne Parallelität“ sind vereinfachte Aussagen der Vorlage; curl bietet selbst parallele Übertragungen. [curl: parallele Übertragungen](https://curl.se/docs/manpage.html#-Z).

> **Musterwerte und Darstellung:** `v1.x.x`, Dateinamen, Tokens und Domains sind vorausgesetzte Referenzwerte. Die bloße Release-URL ist kein ausführbarer Shell-Aufruf. Dieses Dokument behandelt ProjectDiscovery-httpx, nicht die gleichnamige Python-Bibliothek. Das widersprüchliche ❌-Symbol vor „Anfrageraten einhalten“ wurde redaktionell zu ✅ korrigiert. Alle ursprünglichen Befehle, Filter, Headerwerte und Payloads bleiben wörtlich erhalten; es wurde kein Beispiel ausgeführt.
