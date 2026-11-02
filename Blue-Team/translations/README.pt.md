# 🛡️ Guias de referência de Blue Team

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗     ██╗   ██╗███████╗    ████████╗███████╗ █████╗ ███╗   ███╗
  ██╔══██╗██║     ██║   ██║██╔════╝    ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║
  ██████╔╝██║     ██║   ██║█████╗         ██║   █████╗  ███████║██╔████╔██║
  ██╔══██╗██║     ██║   ██║██╔══╝         ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║
  ██████╔╝███████╗╚██████╔╝███████╗       ██║   ███████╗██║  ██║██║ ╚═╝ ██║
  ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝       ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝
```

> **Nota da tradução:** Blue Team designa a equipa defensiva; SIEM centraliza e correlaciona eventos, EDR monitoriza endpoints e IOC significa indicador de comprometimento. Os guias de análise de malware e defesa de rede são mencionados no índice inglês, mas os respetivos ficheiros não existem nesta cópia. As linhas correspondentes são mantidas como indisponíveis.

---

## 🎯 O que é uma Blue Team?

**Blue Team** é a equipa de segurança defensiva responsável por:
- 🔍 **Detetar** ameaças e ataques
- 🛡️ **Defender** sistemas e redes
- 🚨 **Responder** a incidentes de segurança
- 📊 **Monitorizar** atividade suspeita
- 🔒 **Reforçar** a segurança da infraestrutura (hardening)

---

## 📊 Ciclo de vida da defesa

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                               CICLO DA DEFESA                                │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. PREPARAR  │───▶│  2. DETETAR  │───▶│     3. ANALISAR      │        │
│   │  (Hardening (reforço da segurança)) │    │(Monitorizar) │    │     (Investigar)     │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │     4. RESPONDER     │          │
│          │                               │  (Contenção)       │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. MELHORAR  │◀───│ 6. APRENDER  │◀───│     5. RECUPERAR     │        │
│   │              │    │ (Relatório)  │    │     (Restaurar)      │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Guias de Blue Team

### 🚨 Resposta a incidentes e deteção

| Tema | Descrição | Guia |
|-------|-------------|-------|
| **Resposta a incidentes** | Procedimentos de resposta a incidentes, playbooks e contenção | [📄 Ver](Incident-Response.pt.md) |
| **Análise de registos** | Análise de registos Windows/Linux e ID de eventos | [📄 Ver](Log-Analysis.pt.md) |
| **Deteção com SIEM** | Consultas e painéis Splunk/ELK | [📄 Ver](SIEM-Detection.pt.md) |
| **Threat hunting (procura proativa de ameaças)** | Técnicas de procura proativa de ameaças | [📄 Ver](Threat-Hunting.pt.md) |

### 🔬 Análise e defesa

| Tema | Descrição | Guia |
|-------|-------------|-------|
| **Análise de malware** | Técnicas de análise estática/dinâmica | Ainda não disponível no original |
| **Defesa de rede** | IDS/IPS, regras de firewall e segurança de rede | Ainda não disponível no original |
| **Hardening (reforço da segurança)** | Listas de verificação de hardening para Windows/Linux | [📄 Ver](Hardening.pt.md) |

### 📝 Regras de deteção

| Tema | Descrição | Guia |
|-------|-------------|-------|
| **Regras Sigma** | Regras de deteção independentes da plataforma | [📄 Ver](Sigma-Rules.pt.md) |
| **Regras YARA** | Deteção de malware e de IOC (indicadores de comprometimento) | [📄 Ver](YARA-Rules.pt.md) |

---

## 🔗 Red Team e Blue Team

| Aspeto | Red Team (ofensiva) | Blue Team (defensiva) |
|--------|---------------------|----------------------|
| **Objetivo** | Encontrar vulnerabilidades | Proteger sistemas |
| **Abordagem** | Simulação de ataques | Defesa e deteção |
| **Ferramentas** | Metasploit, Cobalt Strike | SIEM, EDR, IDS/IPS |
| **Resultado** | Vulnerabilidades encontradas | Incidentes detetados |
| **Foco** | Conseguir acesso | Impedir acessos indevidos |

---

## 📚 Referência rápida

### ID de eventos Windows essenciais

| ID do evento | Descrição |
|----------|-------------|
| 4624 | Início de sessão bem-sucedido |
| 4625 | Falha no início de sessão |
| 4648 | Início de sessão com credenciais explícitas |
| 4672 | Início de sessão administrativo (privilégios especiais) |
| 4688 | Criação de processos |
| 4698 | Tarefa agendada criada |
| 4720 | Conta de utilizador criada |
| 7045 | Serviço instalado |

### Registos Linux essenciais

| Registo | Localização |
|-----|----------|
| Autenticação | `/var/log/auth.log` |
| Syslog | `/var/log/syslog` |
| Mensagens | `/var/log/messages` |
| Secure | `/var/log/secure` |
| Auditoria | `/var/log/audit/audit.log` |

---

## 🛠️ Ferramentas essenciais de Blue Team

| Categoria | Ferramentas |
|----------|-------|
| **SIEM** | Splunk, ELK Stack, QRadar, Sentinel |
| **EDR** | CrowdStrike, Carbon Black, Defender ATP |
| **IDS/IPS** | Snort, Suricata, Zeek |
| **Análise forense** | Volatility, Autopsy, FTK |
| **Malware** | VirusTotal, Any.run, Cuckoo |
| **Rede** | Wireshark, tcpdump, Zeek |

---

## 📚 Recursos

- [MITRE ATT&CK](https://attack.mitre.org/)
- [Repositório de regras Sigma](https://github.com/SigmaHQ/sigma)
- [Repositório de regras YARA](https://github.com/Yara-Rules/rules)
- [SANS Blue Team Wiki](https://wiki.sans.blue/)
- [Splunk Security Essentials](https://splunkbase.splunk.com/app/3435/)

---

<p align="center">
  <b>🛡️ Antecipe-se às ameaças!</b><br>
  <i>A melhor defesa é proativa!</i>
</p>
