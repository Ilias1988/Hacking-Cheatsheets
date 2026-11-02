# 🛡️ Blue-Team-Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
  ██████╗ ██╗     ██╗   ██╗███████╗    ████████╗███████╗ █████╗ ███╗   ███╗
  ██╔══██╗██║     ██║   ██║██╔════╝    ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║
  ██████╔╝██║     ██║   ██║█████╗         ██║   █████╗  ███████║██╔████╔██║
  ██╔══██╗██║     ██║   ██║██╔══╝         ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║
  ██████╔╝███████╗╚██████╔╝███████╗       ██║   ███████╗██║  ██║██║ ╚═╝ ██║
  ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝       ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝
```

---

> **Begriffe:** SIEM steht für Security Information and Event Management und bündelt Sicherheitsereignisse. EDR (Endpoint Detection and Response) überwacht Endgeräte und unterstützt die Reaktion. IDS/IPS erkennen beziehungsweise blockieren verdächtigen Netzwerkverkehr. IOC bedeutet Kompromittierungsindikator. Die unter „Malware-Analyse“ und „Netzwerkverteidigung“ genannten Quelldateien fehlen im englischen Bestand; ihre Themen bleiben deshalb ohne aktiven Link erhalten.

## 🎯 Was ist ein Blue Team?

Als **Blue Team** bezeichnet man das Sicherheitsteam, das für die Verteidigung zuständig ist. Zu seinen Aufgaben gehören:
- 🔍 Bedrohungen und Angriffe **erkennen**
- 🛡️ Systeme und Netzwerke **verteidigen**
- 🚨 Auf Sicherheitsvorfälle **reagieren**
- 📊 Verdächtige Aktivitäten **überwachen**
- 🔒 Infrastruktur **härten**

---

## 📊 Ablauf der Verteidigung

```
┌──────────────────────────────┐
│        1. Vorbereiten        │
│     Infrastruktur härten     │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│         2. Erkennen          │
│    Aktivitäten überwachen    │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│        3. Analysieren        │
│     Verdacht untersuchen     │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│         4. Reagieren         │
│      Vorfall eindämmen       │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│     5. Wiederherstellen      │
│     Systeme zurückführen     │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│          6. Lernen           │
│      Bericht erstellen       │
└──────────────────────────────┘
                │
                ▼
┌──────────────────────────────┐
│        7. Verbessern         │
│     Zurück zu Schritt 1      │
└──────────────────────────────┘
```

---

## 📖 Blue-Team-Leitfäden

### 🚨 Vorfallbearbeitung und Erkennung

| Thema | Beschreibung | Leitfaden |
|-------|-------------|-------|
| **Incident Response (Vorfallbearbeitung)** | IR-Verfahren, Ablaufpläne und Eindämmung | [📄 Ansehen](./Incident-Response.de.md) |
| **Protokollanalyse** | Protokollanalyse unter Windows und Linux sowie Ereignis-IDs | [📄 Ansehen](./Log-Analysis.de.md) |
| **SIEM-Erkennung** | Splunk-/ELK-Abfragen und Dashboards | [📄 Ansehen](./SIEM-Detection.de.md) |
| **Threat Hunting (proaktive Bedrohungssuche)** | Proaktive Suche nach Bedrohungen | [📄 Ansehen](./Threat-Hunting.de.md) |

### 🔬 Analyse und Verteidigung

| Thema | Beschreibung | Leitfaden |
|-------|-------------|-------|
| **Malware-Analyse** | Statische und dynamische Analysetechniken | 📄 Ansehen (im englischen Original verlinkt; Quelldatei fehlt) |
| **Netzwerkverteidigung** | IDS/IPS, Firewall-Regeln und Netzwerksicherheit | 📄 Ansehen (im englischen Original verlinkt; Quelldatei fehlt) |
| **Härtung** | Checklisten zur Härtung von Windows und Linux | [📄 Ansehen](./Hardening.de.md) |

### 📝 Erkennungsregeln

| Thema | Beschreibung | Leitfaden |
|-------|-------------|-------|
| **Sigma-Regeln** | Plattformunabhängige Erkennungsregeln | [📄 Ansehen](./Sigma-Rules.de.md) |
| **YARA-Regeln** | Malware und Kompromittierungsindikatoren erkennen | [📄 Ansehen](./YARA-Rules.de.md) |

---

## 🔗 Red Team und Blue Team im Vergleich

| Aspekt | Red Team (Angriff) | Blue Team (Verteidigung) |
|--------|---------------------|----------------------|
| **Ziel** | Schwachstellen finden | Systeme schützen |
| **Vorgehen** | Angriffssimulation | Verteidigung und Erkennung |
| **Werkzeuge** | Metasploit, Cobalt Strike | SIEM, EDR, IDS/IPS |
| **Ergebnis** | Gefundene Schwachstellen | Erkannte Vorfälle |
| **Schwerpunkt** | Zugriff erlangen | Angreifer abwehren |

---

## 📚 Kurzreferenz

### Wichtige Windows-Ereignis-IDs

| Ereignis-ID | Beschreibung |
|----------|-------------|
| 4624 | Erfolgreiche Anmeldung |
| 4625 | Fehlgeschlagene Anmeldung |
| 4648 | Anmeldung mit expliziten Anmeldedaten |
| 4672 | Admin-Anmeldung (besondere Rechte) |
| 4688 | Prozesserstellung |
| 4698 | Geplante Aufgabe erstellt |
| 4720 | Benutzerkonto erstellt |
| 7045 | Dienst installiert |

### Wichtige Linux-Protokolle

| Log | Speicherort |
|-----|----------|
| Auth | `/var/log/auth.log` |
| Syslog | `/var/log/syslog` |
| Messages | `/var/log/messages` |
| Secure | `/var/log/secure` |
| Audit | `/var/log/audit/audit.log` |

---

## 🛠️ Wichtige Blue-Team-Werkzeuge

| Kategorie | Werkzeuge |
|----------|-------|
| **SIEM** | Splunk, ELK Stack, QRadar, Sentinel |
| **EDR** | CrowdStrike, Carbon Black, Defender ATP |
| **IDS/IPS** | Snort, Suricata, Zeek |
| **Forensik** | Volatility, Autopsy, FTK |
| **Malware** | VirusTotal, Any.run, Cuckoo |
| **Netzwerk** | Wireshark, tcpdump, Zeek |

---

## 📚 Weiterführende Quellen

- [MITRE ATT&CK](https://attack.mitre.org/)
- [Sigma-Regelrepository](https://github.com/SigmaHQ/sigma)
- [YARA-Regelrepository](https://github.com/Yara-Rules/rules)
- [SANS Blue Team Wiki](https://wiki.sans.blue/)
- [Splunk Security Essentials](https://splunkbase.splunk.com/app/3435/)

---

<p align="center">
  <b>🛡️ Proaktiv verteidigen!</b><br>
  <i>Die beste Verteidigung handelt vorausschauend!</i>
</p>
