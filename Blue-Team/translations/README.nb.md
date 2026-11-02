# 🛡️ Blue Team – hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗     ██╗   ██╗███████╗    ████████╗███████╗ █████╗ ███╗   ███╗
  ██╔══██╗██║     ██║   ██║██╔════╝    ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║
  ██████╔╝██║     ██║   ██║█████╗         ██║   █████╗  ███████║██╔████╔██║
  ██╔══██╗██║     ██║   ██║██╔══╝         ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║
  ██████╔╝███████╗╚██████╔╝███████╗       ██║   ███████╗██║  ██║██║ ╚═╝ ██║
  ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝       ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝
```

---

## 🎯 Hva er Blue Team?

**Blue Team** er sikkerhetsteamet som arbeider med forsvar og har ansvar for å:
- 🔍 **Oppdage** trusler og angrep
- 🛡️ **Beskytte** systemer og nettverk
- 🚨 **Håndtere** sikkerhetshendelser
- 📊 **Overvåke** mistenkelig aktivitet
- 🔒 **Herde** infrastrukturen, altså redusere angrepsflaten

---

## 📊 Forsvarets livssyklus

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        FORSVARETS SYKLUS                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. FORBERED  │───▶│ 2. OPPDAG    │───▶│ 3. ANALYSER          │        │
│   │  (Herding)   │    │  (Overvåk)   │    │  (Undersøk)          │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │ 4. HÅNDTER           │          │
│          │                               │  (Begrensning)       │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. FORBEDRE  │◀───│ 6. LÆR       │◀───│ 5. GJENOPPRETT       │        │
│   │              │    │  (Rapport)   │    │  (Gjenopprett)       │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

Trinnene er forberedelse (herding), oppdagelse (overvåking), analyse (undersøkelse), håndtering (begrensning), gjenoppretting, læring (rapportering) og forbedring.

---

## 📖 Blue Team-veiledninger

### 🚨 Hendelseshåndtering og deteksjon

| Tema | Beskrivelse | Veiledning |
|-------|-------------|-------|
| **Hendelseshåndtering** | IR-prosedyrer, handlingsplaner og begrensning | [📄 Les](Incident-Response.nb.md) |
| **Logganalyse** | Analyse av Windows-/Linux-logger og hendelses-ID-er | [📄 Les](Log-Analysis.nb.md) |
| **SIEM-deteksjon** | Splunk-/ELK-spørringer og dashbord | [📄 Les](SIEM-Detection.nb.md) |
| **Trusseljakt** | Proaktive jaktteknikker | [📄 Les](Threat-Hunting.nb.md) |

### 🔬 Analyse og forsvar

| Tema | Beskrivelse | Veiledning |
|-------|-------------|-------|
| **Skadevareanalyse** | Teknikker for statisk og dynamisk analyse | Ikke tilgjengelig i kildekopien (`Malware-Analysis.md`) |
| **Nettverksforsvar** | IDS/IPS, brannmurregler og nettverkssikkerhet | Ikke tilgjengelig i kildekopien (`Network-Defense.md`) |
| **Herding** | Sjekklister for herding av Windows og Linux | [📄 Les](Hardening.nb.md) |

### 📝 Deteksjonsregler

| Tema | Beskrivelse | Veiledning |
|-------|-------------|-------|
| **Sigma-regler** | Plattformuavhengige deteksjonsregler | [📄 Les](Sigma-Rules.nb.md) |
| **YARA-regler** | Deteksjon av skadevare og kompromitteringsindikatorer (IOC-er) | [📄 Les](YARA-Rules.nb.md) |

---

## 🔗 Red Team og Blue Team sammenlignet

| Aspekt | Red Team (offensivt) | Blue Team (defensivt) |
|--------|---------------------|----------------------|
| **Mål** | Finne sårbarheter | Beskytte systemer |
| **Tilnærming** | Simulere angrep | Forsvar og deteksjon |
| **Verktøy** | Metasploit, Cobalt Strike | SIEM, EDR, IDS/IPS |
| **Resultat** | Oppdagede sårbarheter | Oppdagede hendelser |
| **Fokus** | Bryte seg inn | Holde uvedkommende ute |

---

## 📚 Hurtigreferanse

### Viktige hendelses-ID-er i Windows

| Hendelses-ID | Beskrivelse |
|----------|-------------|
| 4624 | Vellykket pålogging |
| 4625 | Mislykket pålogging |
| 4648 | Pålogging med eksplisitt angitt påloggingsinformasjon |
| 4672 | Administratorpålogging (spesielle rettigheter) |
| 4688 | Prosess opprettet |
| 4698 | Planlagt oppgave opprettet |
| 4720 | Brukerkonto opprettet |
| 7045 | Tjeneste installert |

### Viktige Linux-logger

| Logg | Plassering |
|-----|----------|
| Autentisering | `/var/log/auth.log` |
| Syslog | `/var/log/syslog` |
| Meldinger | `/var/log/messages` |
| Sikkerhet | `/var/log/secure` |
| Revisjonslogg | `/var/log/audit/audit.log` |

---

## 🛠️ Viktige Blue Team-verktøy

| Kategori | Verktøy |
|----------|-------|
| **SIEM** | Splunk, ELK Stack, QRadar, Sentinel |
| **EDR** | CrowdStrike, Carbon Black, Defender ATP |
| **IDS/IPS** | Snort, Suricata, Zeek |
| **Digital etterforskning** | Volatility, Autopsy, FTK |
| **Skadevare** | VirusTotal, Any.run, Cuckoo |
| **Nettverk** | Wireshark, tcpdump, Zeek |

---

## 📚 Ressurser

- [MITRE ATT&CK](https://attack.mitre.org/)
- [Sigma-regelsamling](https://github.com/SigmaHQ/sigma)
- [YARA-regelsamling](https://github.com/Yara-Rules/rules)
- [SANS Blue Team Wiki](https://wiki.sans.blue/)
- [Splunk Security Essentials](https://splunkbase.splunk.com/app/3435/)

---

<p align="center">
  <b>🛡️ Forsvar proaktivt!</b><br>
  <i>Det beste forsvaret ligger i forkant!</i>
</p>
