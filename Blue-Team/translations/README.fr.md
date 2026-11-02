# 🛡️ Aide-mémoire de la Blue Team

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗     ██╗   ██╗███████╗    ████████╗███████╗ █████╗ ███╗   ███╗
  ██╔══██╗██║     ██║   ██║██╔════╝    ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║
  ██████╔╝██║     ██║   ██║█████╗         ██║   █████╗  ███████║██╔████╔██║
  ██╔══██╗██║     ██║   ██║██╔══╝         ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║
  ██████╔╝███████╗╚██████╔╝███████╗       ██║   ███████╗██║  ██║██║ ╚═╝ ██║
  ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝       ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝
```

> **Précisions de traduction :** Blue Team désigne l’équipe défensive ; un SIEM centralise et corrèle les événements de sécurité, un EDR surveille les postes et serveurs, et les IDS/IPS détectent ou bloquent les intrusions. Les guides `Malware-Analysis.md` et `Network-Defense.md` sont référencés mais absents du dépôt source ; leurs liens sont conservés pour respecter l’original. Les noms historiques d’outils sont également conservés.

---

## 🎯 Qu'est-ce que la Blue Team ?

La **Blue Team** désigne l'équipe de sécurité défensive chargée de :
- 🔍 **Détecter** les menaces et les attaques
- 🛡️ **Défendre** les systèmes et les réseaux
- 🚨 **Répondre** aux incidents de sécurité
- 📊 **Surveiller** les activités suspectes
- 🔒 **Durcir** l'infrastructure

---

## 📊 Cycle de défense

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        CYCLE DE DÉFENSE                                      │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. PRÉPARER  │───▶│ 2. DÉTECTER  │───▶│ 3. ANALYSER          │        │
│   │  (Durcir)    │    │  (Surveiller)│    │  (Enquêter)          │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │ 4. RÉPONDRE          │          │
│          │                               │  (Confiner)          │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. AMÉLIORER │◀───│ 6. APPRENDRE │◀───│ 5. RÉTABLIR          │        │
│   │              │    │  (Bilan)     │    │  (Restaurer)         │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Guides de la Blue Team

### 🚨 Réponse aux incidents et détection

| Sujet | Description | Guide |
|-------|-------------|-------|
| **Réponse aux incidents** | Procédures de réponse aux incidents, fiches réflexes et confinement | [📄 Consulter](./Incident-Response.fr.md) |
| **Analyse des journaux** | Analyse des journaux Windows/Linux et identifiants d'événements | [📄 Consulter](./Log-Analysis.fr.md) |
| **Détection avec un SIEM** | Requêtes et tableaux de bord Splunk/ELK | [📄 Consulter](./SIEM-Detection.fr.md) |
| **Recherche proactive de menaces** | Techniques de recherche proactive de menaces | [📄 Consulter](./Threat-Hunting.fr.md) |

### 🔬 Analyse et défense

| Sujet | Description | Guide |
|-------|-------------|-------|
| **Analyse de logiciels malveillants** | Techniques d'analyse statique et dynamique | 📄 Consulter — guide pas encore disponible |
| **Défense réseau** | IDS/IPS, règles de pare-feu et sécurité réseau | 📄 Consulter — guide pas encore disponible |
| **Durcissement** | Listes de contrôle du durcissement Windows/Linux | [📄 Consulter](./Hardening.fr.md) |

### 📝 Règles de détection

| Sujet | Description | Guide |
|-------|-------------|-------|
| **Règles Sigma** | Règles de détection indépendantes de la plateforme | [📄 Consulter](./Sigma-Rules.fr.md) |
| **Règles YARA** | Détection des logiciels malveillants et des indicateurs de compromission (IOC) | [📄 Consulter](./YARA-Rules.fr.md) |

---

## 🔗 Red Team et Blue Team

| Aspect | Red Team (offensive) | Blue Team (défensive) |
|--------|---------------------|----------------------|
| **Objectif** | Trouver les vulnérabilités | Protéger les systèmes |
| **Approche** | Simulation d'attaque | Défense et détection |
| **Outils** | Metasploit, Cobalt Strike | SIEM, EDR, IDS/IPS |
| **Résultat** | Vulnérabilités trouvées | Incidents détectés |
| **Priorité** | Pénétrer dans le système | Empêcher les intrusions |

---

## 📚 Référence rapide

### Identifiants d'événements Windows essentiels

| ID d'événement | Description |
|----------|-------------|
| 4624 | Ouverture de session réussie |
| 4625 | Échec d'ouverture de session |
| 4648 | Ouverture de session avec des identifiants explicites |
| 4672 | Ouverture de session administrateur (privilèges spéciaux) |
| 4688 | Création d'un processus |
| 4698 | Création d'une tâche planifiée |
| 4720 | Création d'un compte utilisateur |
| 7045 | Installation d'un service |

### Journaux Linux essentiels

| Journal | Emplacement |
|-----|----------|
| Auth | `/var/log/auth.log` |
| Syslog | `/var/log/syslog` |
| Messages | `/var/log/messages` |
| Secure | `/var/log/secure` |
| Audit | `/var/log/audit/audit.log` |

---

## 🛠️ Outils essentiels de la Blue Team

| Catégorie | Outils |
|----------|-------|
| **SIEM** | Splunk, ELK Stack, QRadar, Sentinel |
| **EDR** | CrowdStrike, Carbon Black, Defender ATP |
| **IDS/IPS** | Snort, Suricata, Zeek |
| **Investigation numérique** | Volatility, Autopsy, FTK |
| **Logiciels malveillants** | VirusTotal, Any.run, Cuckoo |
| **Réseau** | Wireshark, tcpdump, Zeek |

---

## 📚 Ressources

- [MITRE ATT&CK](https://attack.mitre.org/)
- [Dépôt de règles Sigma](https://github.com/SigmaHQ/sigma)
- [Dépôt de règles YARA](https://github.com/Yara-Rules/rules)
- [SANS Blue Team Wiki](https://wiki.sans.blue/)
- [Splunk Security Essentials](https://splunkbase.splunk.com/app/3435/)

---

<p align="center">
  <b>🛡️ Défendez de manière proactive !</b><br>
  <i>La meilleure défense est proactive !</i>
</p>
