# 🛡️ Guías rápidas de Blue Team

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Nota editorial:** Blue Team significa equipo defensivo; hardening es el refuerzo de la configuración de seguridad. Malware-Analysis.md y Network-Defense.md se citan en el original, pero no existen en esta copia. Sus referencias se conservan como texto. SIEM centraliza y correlaciona eventos; EDR supervisa y responde en los endpoints; IDS/IPS detecta y previene intrusiones.


```
  ██████╗ ██╗     ██╗   ██╗███████╗    ████████╗███████╗ █████╗ ███╗   ███╗
  ██╔══██╗██║     ██║   ██║██╔════╝    ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║
  ██████╔╝██║     ██║   ██║█████╗         ██║   █████╗  ███████║██╔████╔██║
  ██╔══██╗██║     ██║   ██║██╔══╝         ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║
  ██████╔╝███████╗╚██████╔╝███████╗       ██║   ███████╗██║  ██║██║ ╚═╝ ██║
  ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝       ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝
```

---

## 🎯 ¿Qué es el Blue Team?

El **Blue Team** es el equipo de seguridad defensiva responsable de:
- 🔍 **Detectar** amenazas y ataques
- 🛡️ **Defender** sistemas y redes
- 🚨 **Responder** a los incidentes de seguridad
- 📊 **Supervisar** la actividad sospechosa
- 🔒 **Reforzar la seguridad** de la infraestructura (hardening)

---

## 📊 Ciclo de vida de la defensa

```
┌──────────────────────────────────────────────────────────────────────────┐
│                             CICLO DE DEFENSA                             │
├──────────────────────────────────────────────────────────────────────────┤
│  ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐  │
│  │   1. PREPARAR    │────▶│   2. DETECTAR    │────▶│   3. ANALIZAR    │  │
│  │   (Hardening)    │     │   (Supervisar)   │     │   (Investigar)   │  │
│  └──────────────────┘     └──────────────────┘     └──────────────────┘  │
│           │                                                 │            │
│           │                                                 │            │
│           │                                                 ▼            │
│           │                                        ┌──────────────────┐  │
│           │                                        │   4. RESPONDER   │  │
│           │                                        │   (Contención)   │  │
│           │                                        └──────────────────┘  │
│           │                                                 │            │
│           │                                                 │            │
│           ▼                                                 ▼            │
│  ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐  │
│  │    7. MEJORAR    │◀────│   6. APRENDER    │◀────│   5. RECUPERAR   │  │
│  │                  │     │    (Informar)    │     │   (Restaurar)    │  │
│  └──────────────────┘     └──────────────────┘     └──────────────────┘  │
└──────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Guías de Blue Team

### 🚨 Respuesta a incidentes y detección

| Tema | Descripción | Guía |
|-------|-------------|-------|
| **Respuesta a incidentes** | Procedimientos de respuesta, guías operativas y contención | [📄 Ver](./Incident-Response.es.md) |
| **Análisis de registros** | Análisis de registros de Windows/Linux e identificadores de eventos | [📄 Ver](./Log-Analysis.es.md) |
| **Detección con SIEM** | Consultas y paneles de Splunk/ELK | [📄 Ver](./SIEM-Detection.es.md) |
| **Búsqueda proactiva de amenazas** | Técnicas de búsqueda proactiva de amenazas | [📄 Ver](./Threat-Hunting.es.md) |

### 🔬 Análisis y defensa

| Tema | Descripción | Guía |
|-------|-------------|-------|
| **Análisis de malware** | Técnicas de análisis estático y dinámico | 📄 Ver (guía ausente en el original) |
| **Defensa de red** | IDS/IPS, reglas de cortafuegos y seguridad de red | 📄 Ver (guía ausente en el original) |
| **Hardening** | Listas de comprobación de hardening de Windows/Linux | [📄 Ver](./Hardening.es.md) |

### 📝 Reglas de detección

| Tema | Descripción | Guía |
|-------|-------------|-------|
| **Reglas Sigma** | Reglas de detección independientes de la plataforma | [📄 Ver](./Sigma-Rules.es.md) |
| **Reglas YARA** | Detección de malware e indicadores de compromiso | [📄 Ver](./YARA-Rules.es.md) |

---

## 🔗 Red Team frente a Blue Team

| Aspecto | Red Team (ofensivo) | Blue Team (defensivo) |
|--------|---------------------|----------------------|
| **Objetivo** | Encontrar vulnerabilidades | Proteger los sistemas |
| **Enfoque** | Simulación de ataques | Defensa y detección |
| **Herramientas** | Metasploit, Cobalt Strike | SIEM, EDR, IDS/IPS |
| **Resultado** | Vulnerabilidades encontradas | Incidentes detectados |
| **Prioridad** | Conseguir acceso | Impedir el acceso |

---

## 📚 Referencia rápida

### Identificadores de eventos esenciales de Windows

| ID de evento | Descripción |
|----------|-------------|
| 4624 | Inicio de sesión correcto |
| 4625 | Inicio de sesión fallido |
| 4648 | Inicio de sesión con credenciales explícitas |
| 4672 | Inicio de sesión de administrador (privilegios especiales) |
| 4688 | Creación de procesos |
| 4698 | Tarea programada creada |
| 4720 | Cuenta de usuario creada |
| 7045 | Servicio instalado |

### Registros esenciales de Linux

| Registro | Ubicación |
|-----|----------|
| Autenticación | `/var/log/auth.log` |
| Syslog | `/var/log/syslog` |
| Mensajes | `/var/log/messages` |
| Seguridad | `/var/log/secure` |
| Auditoría | `/var/log/audit/audit.log` |

---

## 🛠️ Herramientas esenciales de Blue Team

| Categoría | Herramientas |
|----------|-------|
| **SIEM** | Splunk, ELK Stack, QRadar, Sentinel |
| **EDR** | CrowdStrike, Carbon Black, Defender ATP |
| **IDS/IPS** | Snort, Suricata, Zeek |
| **Análisis forense** | Volatility, Autopsy, FTK |
| **Malware** | VirusTotal, Any.run, Cuckoo |
| **Red** | Wireshark, tcpdump, Zeek |

---

## 📚 Recursos

- [MITRE ATT&CK](https://attack.mitre.org/)
- [Repositorio de reglas Sigma](https://github.com/SigmaHQ/sigma)
- [Repositorio de reglas YARA](https://github.com/Yara-Rules/rules)
- [SANS Blue Team Wiki](https://wiki.sans.blue/)
- [Splunk Security Essentials](https://splunkbase.splunk.com/app/3435/)

---

<p align="center">
  <b>🛡️ ¡Defiende de forma proactiva!</b><br>
  <i>¡La mejor defensa es una defensa proactiva!</i>
</p>
