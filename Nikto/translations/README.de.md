# 🕷️ Nikto – vollständiger Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   _ _ _    _        
   | \ | (_) | _| |_ ___  
   |  \| | | |/ / __/ _ \ 
   | |\  | |   <| || (_) |
   |_| \_|_|_|\_\\__\___/ 
                          
   Webserver-Scanner – seit 2001
```

<p align="center">
  <img src="https://img.shields.io/badge/Nikto-Web_Scanner-blue?style=for-the-badge" alt="Nikto">
  <img src="https://img.shields.io/badge/Vulnerability-Scanner-red?style=for-the-badge" alt="Schwachstelle">
  <img src="https://img.shields.io/badge/CGI-Testing-green?style=for-the-badge" alt="CGI">
  <img src="https://img.shields.io/badge/SSL-Analysis-purple?style=for-the-badge" alt="SSL">
</p>

<p align="center">
  <b>🌐 Quelloffener Webserver-Scanner für Sicherheitstests</b>
</p>

---

## 📋 Inhaltsverzeichnis

- [Einführung](#-einführung)
- [Installation](#-installation)
- [Grundsyntax](#-grundsyntax)
- [Zielangabe](#-zielangabe)
- [Port- und SSL-Optionen](#-port--und-ssl-optionen)
- [Scan-Auswahl und Feinabstimmung](#-scan-auswahl-und-feinabstimmung)
- [Authentifizierung](#-authentifizierung)
- [Proxy und Umgehungstechniken](#-proxy-und-umgehungstechniken)
- [Ausgabe und Berichte](#-ausgabe-und-berichte)
- [Datenbankaktualisierungen](#-datenbankaktualisierungen)
- [Praxisbeispiele](#-praxisbeispiele)
- [Kurzreferenz](#-kurzreferenz)
- [Tipps und bewährte Vorgehensweisen](#-tipps-und-bewährte-vorgehensweisen)
- [Ressourcen](#-ressourcen)

---

## 🎯 Einführung

**Nikto** ist ein quelloffener Webserver-Scanner, der Webserver auf zahlreiche Sicherheitsprobleme prüft, unter anderem:

### Was Nikto prüft

| Kategorie | Beschreibung |
|----------|-------------|
| **Potenziell gefährliche Dateien** | Dateien, die ein Risiko darstellen können (Backups usw.) |
| **Veraltete Versionen** | Veraltete Serversoftware |
| **Versionsspezifisch** | Versionsspezifische Schwachstellen |
| **Serverkonfiguration** | Fehlkonfigurationen |
| **CGI-Skripte** | Potenziell gefährliche CGI-Skripte |
| **SSL/TLS** | Probleme mit SSL-Zertifikaten |
| **HTTP-Header** | Fehlende oder unsichere HTTP-Header |
| **HTTP-Methoden** | Gefährliche HTTP-Methoden |

### Datenbankstatistik

```
✅ 6,700+ potenziell gefährliche Dateien/CGIs
✅ 1,250+ veraltete Serverversionen
✅ 270+ versionsspezifische Probleme
✅ 1,000+ Probleme mit der Serverkonfiguration
```

### Eigenschaften

| Eigenschaft | Beschreibung |
|---------|-------------|
| **Umfassend** | Tests auf Tausende von Schwachstellen |
| **Plugin-basiert** | Erweiterbare Architektur |
| **Mehrere Formate** | Ausgabe als HTML, XML, CSV und JSON |
| **SSL-Unterstützung** | Umfassende HTTPS-Prüfungen |
| **IDS-Umgehung** | Integrierte Umgehungstechniken |
| **Authentifizierung** | Unterstützung für Basic, Digest und NTLM |

---

## 📥 Installation

### Kali Linux (vorinstalliert)
```bash
# Nikto aktualisieren
sudo apt update && sudo apt install nikto

# Datenbank aktualisieren
nikto -update
```

### Ubuntu/Debian
```bash
sudo apt install nikto
```

### Von GitHub
```bash
git clone https://github.com/sullo/nikto
cd nikto/program
perl nikto.pl -h
```

### macOS (Homebrew)
```bash
brew install nikto
```

### Installation prüfen
```bash
nikto -Version
nikto -h          # Hilfe
```

---

<a name="-grundsyntax"></a>

## ⌨️ Grundsyntax

### Allgemeine Syntax
```bash
nikto -h <host> [options]
```

### Schnelleinstieg
```bash
# Einfacher Scan
nikto -h http://target.com

# Scan mit Portangabe
nikto -h target.com -p 80

# HTTPS scannen
nikto -h https://target.com

# IP-Adresse scannen
nikto -h 192.168.1.1
```

---

## 🎯 Zielangabe

### Einzelnes Ziel
```bash
# Nach Hostname
nikto -h target.com

# Nach IP-Adresse
nikto -h 192.168.1.1

# Mit Protokollangabe
nikto -h http://target.com
nikto -h https://target.com
```

### Mehrere Ziele
```bash
# Aus einer Datei
nikto -h targets.txt

# targets.txt:
# http://target1.com
# http://target2.com:8080
# https://target3.com
```

### Ziel mit Pfad
```bash
# Bestimmten Pfad scannen
nikto -h http://target.com/app

# Bestimmtes Verzeichnis scannen
nikto -h http://target.com/admin/
```

---

## 🔌 Port- und SSL-Optionen

### Portoptionen
```bash
# Einzelner Port
nikto -h target.com -p 80

# Mehrere Ports
nikto -h target.com -p 80,443,8080

# Portbereich
nikto -h target.com -p 80-90

# Häufige Webports
nikto -h target.com -p 80,443,8000,8080,8443
```

### SSL-/TLS-Optionen

| Option | Beschreibung |
|--------|-------------|
| `-ssl` | SSL-Modus erzwingen |
| `-nossl` | SSL deaktivieren |
| `-maxtime` | Maximale Scandauer |

```bash
# SSL erzwingen
nikto -h target.com -ssl

# SSL deaktivieren (nur HTTP)
nikto -h target.com -nossl

# SSL auf einem nicht standardmäßigen Port
nikto -h target.com -p 8443 -ssl
```

### Virtuelle Hosts
```bash
# Virtuellen Host angeben
nikto -h 192.168.1.1 -vhost target.com

# Nützlich, wenn:
# - Ziel hinter einem Lastverteiler
# - Mehrere Websites unter derselben IP-Adresse
```

---

<a name="-scan-auswahl-und-feinabstimmung"></a>

## 🎚️ Scan-Auswahl und Feinabstimmung

### Auswahloptionen (-Tuning)

| Code | Beschreibung |
|------|-------------|
| `0` | Dateiupload |
| `1` | Interessante Datei / in Protokollen gesehen |
| `2` | Fehlkonfiguration / Standarddatei |
| `3` | Offenlegung von Informationen |
| `4` | Injection (XSS/Skript/HTML) |
| `5` | Dateiabruf aus der Ferne – innerhalb des Webverzeichnisses |
| `6` | Denial of Service (Dienstverweigerung) |
| `7` | Dateiabruf aus der Ferne – serverweit |
| `8` | Befehlsausführung / entfernte Shell |
| `9` | SQL-Injection |
| `a` | Umgehung der Authentifizierung |
| `b` | Softwareerkennung |
| `c` | Einbindung entfernter Quelldateien |
| `d` | Webdienst |
| `e` | Administrationskonsole |
| `x` | Umkehrung der Auswahloptionen |

### Auswahloptionen verwenden
```bash
# Nur auf SQL-Injection prüfen
nikto -h target.com -Tuning 9

# Auf XSS und SQL-Injection prüfen
nikto -h target.com -Tuning 49

# Auf mehrere Arten von Problemen prüfen
nikto -h target.com -Tuning 1234

# Bestimmte Tests ausschließen (Präfix x)
nikto -h target.com -Tuning x6    # DoS-Tests ausschließen

# Alles außer DoS
nikto -h target.com -Tuning x6
```

### Plugins
```bash
# Verfügbare Plugins auflisten
nikto -list-plugins

# Bestimmtes Plugin ausführen
nikto -h target.com -Plugins apacheusers

# Mehrere Plugins ausführen
nikto -h target.com -Plugins "apacheusers;robots"

# Plugins ausschließen
nikto -h target.com -Plugins "@@ALL;-robots"
```

### Häufige Plugins

| Plugin | Beschreibung |
|--------|-------------|
| `apacheusers` | Apache-Benutzer ermitteln |
| `cgi` | CGI-Skripte prüfen |
| `robots` | robots.txt analysieren |
| `headers` | HTTP-Header analysieren |
| `httpoptions` | HTTP-Methoden prüfen |
| `outdated` | Auf veraltete Software prüfen |
| `ssl` | SSL/TLS analysieren |
| `dictionary` | Wörterbuchangriff |

---

## 🔐 Authentifizierung

### Basic-Authentifizierung
```bash
# Benutzername und Passwort
nikto -h target.com -id admin:password

# Base64-Format laut Vorlage
nikto -h target.com -id "admin:password"
```

### Digest-Authentifizierung
```bash
# Digest-Authentifizierung (automatisch erkannt)
nikto -h target.com -id admin:password
```

### NTLM-Authentifizierung
```bash
# NTLM-Authentifizierung
nikto -h target.com -id admin:password:domain
```

### Cookie-basierte Authentifizierung
```bash
# Cookies senden
nikto -h target.com -cookies "session=abc123; token=xyz789"

# Aus einer Datei
nikto -h target.com -cookies cookies.txt
```

---

<a name="-proxy-und-umgehungstechniken"></a>

## 🛡️ Proxy und Umgehungstechniken

### Proxy-Unterstützung
```bash
# HTTP-Proxy
nikto -h target.com -useproxy http://127.0.0.1:8080

# Proxy mit Authentifizierung
nikto -h target.com -useproxy http://user:pass@proxy:8080

# Über Burp Suite
nikto -h target.com -useproxy http://127.0.0.1:8080
```

### IDS-Umgehung (-evasion)

| Code | Technik |
|------|-----------|
| `1` | Zufällige URI-Kodierung (kein UTF-8) |
| `2` | Selbstverweis im Verzeichnispfad (/./) |
| `3` | Vorzeitiges URL-Ende |
| `4` | Lange zufällige Zeichenfolge voranstellen |
| `5` | Scheinparameter |
| `6` | Tabulator als Trennzeichen in der Anfrage |
| `7` | Groß- und Kleinschreibung der URL ändern |
| `8` | Windows-Verzeichnistrennzeichen verwenden (\) |
| `A` | Wagenrücklauf verwenden (0x0d) |
| `B` | Binärwert 0x0b verwenden |

```bash
# Einzelne Umgehungstechnik
nikto -h target.com -evasion 1

# Mehrere Umgehungstechniken
nikto -h target.com -evasion 1234

# Alle Umgehungstechniken laut Vorlage
nikto -h target.com -evasion 12345678AB
```

### User-Agent
```bash
# Eigener User-Agent
nikto -h target.com -useragent "Mozilla/5.0 (Windows NT 10.0)"

# Googlebot
nikto -h target.com -useragent "Googlebot/2.1"
```

### Zeitsteuerung der Anfragen
```bash
# Pause zwischen Anfragen (Sekunden)
nikto -h target.com -Pause 2

# Maximale Scandauer (Sekunden)
nikto -h target.com -maxtime 3600

# Zeitlimit je Anfrage
nikto -h target.com -timeout 10
```

---

## 📄 Ausgabe und Berichte

### Ausgabeformate

| Option | Format |
|--------|--------|
| `-o file` | Ausgabe in eine Datei |
| `-Format htm` | HTML-Format |
| `-Format xml` | XML-Format |
| `-Format csv` | CSV-Format |
| `-Format txt` | Textformat |
| `-Format json` | JSON-Format |
| `-Format nbe` | NBE-Format |
| `-Format sql` | SQL-Format |

### Ergebnisse speichern
```bash
# Textausgabe
nikto -h target.com -o report.txt

# HTML-Bericht
nikto -h target.com -o report.html -Format htm

# XML-Bericht
nikto -h target.com -o report.xml -Format xml

# JSON-Bericht
nikto -h target.com -o report.json -Format json

# CSV-Bericht
nikto -h target.com -o report.csv -Format csv
```

### Optionen für den Ausgabeumfang
```bash
# Anzeigeoptionen
nikto -h target.com -Display V    # Ausführliche Ausgabe
nikto -h target.com -Display D    # Debug-Ausgabe
nikto -h target.com -Display E    # HTTP-Fehler
nikto -h target.com -Display P    # Fortschritt
nikto -h target.com -Display S    # IP-Adresse in der Ausgabe unkenntlich machen
nikto -h target.com -Display 1    # Weiterleitungen anzeigen
nikto -h target.com -Display 2    # Cookies anzeigen
nikto -h target.com -Display 3    # Antworten mit 200/OK anzeigen
nikto -h target.com -Display 4    # URLs anzeigen, die eine Authentifizierung erfordern

# Mehrere Anzeigeoptionen
nikto -h target.com -Display VEP
```

### Fortschritt speichern
```bash
# Scanzustand speichern (laut Vorlage später fortsetzbar)
nikto -h target.com -Save scan_state.txt

# Scan fortsetzen
nikto -Resume scan_state.txt
```

---

## 🔄 Datenbankaktualisierungen

### Nikto-Datenbank aktualisieren
```bash
# Alle Datenbanken aktualisieren
nikto -update

# Manuelle Aktualisierung (falls -update fehlschlägt)
cd /var/lib/nikto
wget https://cirt.net/nikto/UPDATES/2.1.5/db_outdated.csv
wget https://cirt.net/nikto/UPDATES/2.1.5/db_tests.csv
```

### Speicherort der Datenbank
```bash
# Standardspeicherorte
/usr/share/nikto/databases/
/var/lib/nikto/

# Datenbankdateien
db_tests         # Zentrale Testdatenbank
db_outdated      # Veraltete Softwareversionen
db_server_msgs   # Servermeldungen
db_variables     # Variablen
db_404_strings   # Zeichenfolgen für 404-Seiten
```

---

## 🎬 Praxisbeispiele

### Beispiel 1: Einfacher Webserver-Scan
```bash
nikto -h http://target.com
```

### Beispiel 2: Umfassender HTTPS-Scan
```bash
nikto -h https://target.com -ssl -p 443 -o report.html -Format htm
```

### Beispiel 3: Mehrere Ports scannen
```bash
nikto -h target.com -p 80,443,8080,8443 -o results.txt
```

### Beispiel 4: Authentifizierter Scan
```bash
nikto -h http://target.com/admin \
    -id admin:password \
    -o auth_scan.html \
    -Format htm
```

### Beispiel 5: Laut Vorlage unauffälliger Scan (IDS-Umgehung)
```bash
nikto -h target.com \
    -evasion 1234 \
    -Pause 2 \
    -useragent "Mozilla/5.0 (Windows NT 10.0)" \
    -o stealth_scan.txt
```

### Beispiel 6: Über den Burp-Proxy
```bash
nikto -h https://target.com \
    -useproxy http://127.0.0.1:8080 \
    -o proxy_scan.html \
    -Format htm
```

### Beispiel 7: Schwerpunkt SQL-Injection
```bash
nikto -h target.com \
    -Tuning 9 \
    -o sqli_scan.txt
```

### Beispiel 8: Vollständige Prüfung
```bash
nikto -h https://target.com \
    -ssl \
    -p 443 \
    -Tuning x6 \
    -Display VEP \
    -o full_audit.html \
    -Format htm
```

### Beispiel 9: Virtuelle Hosts prüfen
```bash
nikto -h 192.168.1.10 \
    -vhost app.target.com \
    -p 80,443 \
    -o vhost_scan.txt
```

### Beispiel 10: Ziele aus einer Datei scannen
```bash
nikto -h targets.txt \
    -o batch_scan.html \
    -Format htm
```

---

## 📊 Kurzreferenz

### Wichtige Befehle

| Aufgabe | Befehl |
|------|---------|
| Einfacher Scan | `nikto -h target.com` |
| HTTPS-Scan | `nikto -h target.com -ssl` |
| Mehrere Ports | `nikto -h target.com -p 80,443,8080` |
| Mit Authentifizierung | `nikto -h target.com -id user:pass` |
| HTML-Bericht | `nikto -h target.com -o report.html -Format htm` |
| Umgehung | `nikto -h target.com -evasion 123` |
| Über einen Proxy | `nikto -h target.com -useproxy http://127.0.0.1:8080` |
| Datenbank aktualisieren | `nikto -update` |

### Häufige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-h` | Zielhost |
| `-p` | Zu scannende Ports |
| `-ssl` | SSL-Modus erzwingen |
| `-id` | Zugangsdaten für die Authentifizierung |
| `-o` | Ausgabedatei |
| `-Format` | Ausgabeformat |
| `-Tuning` | Auswahl und Feinabstimmung des Scans |
| `-evasion` | IDS-Umgehung |
| `-useproxy` | Proxyserver |
| `-vhost` | Virtueller Host |
| `-Pause` | Pause zwischen Tests |
| `-timeout` | Zeitlimit für Anfragen |

### Kurzreferenz der Auswahloptionen

| Code | Kategorie |
|------|----------|
| 1-4 | Informationen/Fehlkonfiguration |
| 4-5 | Injection/Dateiabruf |
| 6 | DoS (im Produktivbetrieb vermeiden) |
| 7-8 | Fernzugriff |
| 9 | SQL-Injection |
| a | Umgehung der Authentifizierung |
| b | Softwareerkennung |
| x | Ausschließen |

---

## 💡 Tipps und bewährte Vorgehensweisen

### Hinweise zur Leistung

1. **Mit einem einfachen Scan beginnen**
   ```bash
   nikto -h target.com
   ```

2. **DoS-Tests ausschließen**
   ```bash
   nikto -h target.com -Tuning x6
   ```

3. **Passende Zeitlimits verwenden**
   ```bash
   nikto -h target.com -timeout 15 -Pause 1
   ```

### Hinweise zur Genauigkeit

1. **Datenbank regelmäßig aktualisieren**
   ```bash
   nikto -update
   ```

2. **Ausführliche Ausgabe verwenden**
   ```bash
   nikto -h target.com -Display VE
   ```

3. **Mit anderen Werkzeugen kombinieren**
   ```bash
   # Zuerst Nmap, danach Nikto
   nmap -sV -p 80,443 target.com
   nikto -h target.com -p 80,443
   ```

### Häufige Probleme

| Problem | Lösung |
|-------|----------|
| Langsamer Scan | `-Pause` verringern, `-timeout` erhöhen |
| SSL-Fehler | Den Schalter `-ssl` ausdrücklich angeben |
| Probleme bei der Authentifizierung | Format von `-id` prüfen |
| Fehlende Ergebnisse | Datenbank aktualisieren und Plugins prüfen |

---

## ⚠️ Rechtlicher Hinweis

> **WARNUNG:** Nikto ist ein Webserver-Scanner, der ausschließlich für **autorisierte Tests** eingesetzt werden darf.
> 
> - ✅ Eigene Server scannen
> - ✅ Nur mit ausdrücklicher schriftlicher Genehmigung scannen
> - ✅ Bei autorisierten Penetrationstests einsetzen
> - ❌ Niemals ohne Genehmigung scannen
> - ❌ Unbefugtes Scannen ist laut Vorlage rechtswidrig
> 
> **Nikto kann erheblichen Datenverkehr erzeugen und Sicherheitsalarme auslösen.**

---

## 📚 Ressourcen

### Offizielle Ressourcen
- [Nikto GitHub](https://github.com/sullo/nikto)
- [Nikto-Dokumentation](https://cirt.net/Nikto2)
- [CIRT.net](https://cirt.net/)

### Lernmaterial
- [OWASP-Leitfaden für Sicherheitstests](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Verwandte Spickzettel
- [Nmap](../../Nmap/translations/README.de.md)
- [Gobuster](../../Gobuster/translations/README.de.md)
- [Burp Suite](../../Burp-Suite/README.md)

---

<p align="center">
  <b>🕷️ Webserver-Scans sicher beherrschen!</b><br>
  <i>Schwachstellen finden, bevor Angreifer sie ausnutzen</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Versionen und Datenbestand:** Die Zahlen, Plugins und Datenbankpfade sind Angaben der Vorlage, keine hier ermittelten aktuellen Bestandswerte. Die manuell verlinkten Dateien für Version 2.1.5 sind historische Beispiele. Laut offizieller Dokumentation wurde `-update` ab 2.1.6 durch eine Aktualisierung über Git abgelöst; bei Paketen ist der jeweilige Vertriebsweg zu beachten. Die Originalbefehle bleiben unverändert. [Nikto: Aktualisierung](https://github.com/sullo/nikto/wiki/Updating).

> **Korrektur zu „Fortschritt speichern“:** `-Save` speichert positive Antworten in einem Verzeichnis, keinen fortsetzbaren Scanzustand. `-Resume`, `-cookies` und das NBE-Format stehen nicht in der geprüften aktuellen offiziellen Optionenliste; diese Originalbeispiele sind deshalb versionsabhängig zu prüfen. `-maxtime` begrenzt die Testdauer je Host und ist keine SSL-spezifische Option. [Nikto: Optionen](https://github.com/sullo/nikto/wiki/Annotated-Option-List), [Anfragen und Antworten speichern](https://github.com/sullo/nikto/wiki/Saving-Requests-Responses-&-Replaying).

> **Authentifizierung und Einordnung:** Anführungszeichen um `admin:password` kodieren den Wert nicht in Base64. Die offizielle Liste beschreibt `-id` als Benutzername/Passwort mit optionalem Realm; die Beschriftung allein belegt keine allgemeine NTLM-Unterstützung. Zugangsdaten ersetzen keinen vollständigen anwendungsspezifischen Login-Ablauf. Die Tuning-Kurzreferenz fasst Kategorien vereinfacht zusammen; die vollständige Tabelle ist genauer. [Nikto: Optionen](https://github.com/sullo/nikto/wiki/Annotated-Option-List).

> **Erkennung und Grenzen:** „Stealth“ und „IDS Evasion“ sind Bezeichnungen der Vorlage, keine Zusage unauffälliger oder erfolgreicher Tests. Ein anderer User-Agent und Kodierungsvarianten heben Sicherheitskontrollen nicht pauschal auf. Ein Versionshinweis oder Treffer muss auf tatsächliche Betroffenheit geprüft werden. Ein höheres Zeitlimit macht einen langsamen Scan nicht automatisch schneller; Befunde und Vollständigkeit hängen von Ziel, Auswahl und Antwortverhalten ab. Es wurde kein Beispiel ausgeführt.
