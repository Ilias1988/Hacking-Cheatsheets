# 🎯 Guia de referência de threat hunting

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Threat-Hunting.tr.md) • [🇬🇷 Ελληνικά](Threat-Hunting.el.md)

```
████████╗██╗  ██╗██████╗ ███████╗ █████╗ ████████╗    ██╗  ██╗██╗   ██╗███╗   ██╗████████╗██╗███╗   ██╗ ██████╗ 
╚══██╔══╝██║  ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝    ██║  ██║██║   ██║████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝ 
   ██║   ███████║██████╔╝█████╗  ███████║   ██║       ███████║██║   ██║██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║  ███╗
   ██║   ██╔══██║██╔══██╗██╔══╝  ██╔══██║   ██║       ██╔══██║██║   ██║██║╚██╗██║   ██║   ██║██║╚██╗██║██║   ██║
   ██║   ██║  ██║██║  ██║███████╗██║  ██║   ██║       ██║  ██║╚██████╔╝██║ ╚████║   ██║   ██║██║ ╚████║╚██████╔╝
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

> **Notas de precisão:** Threat hunting é a procura proativa de ameaças com base em hipóteses. O filtro de serviços apresentado como «sem assinatura» apenas exclui caminhos que contêm `Windows`; não verifica assinaturas digitais. A enumeração de `/proc` mostrada não garante a deteção de processos ocultos. Os índices `Properties[n]` dependem da versão e do esquema dos eventos; confirmar os campos XML antes de reutilizar as consultas.

---

## 🎯 Processo de threat hunting

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ 1. HIPÓTESE  │────▶│ 2. RECOLHER │────▶│ 3. ANALISAR │────▶│ 4. RESPONDER│
│   (Teoria)   │     │   (Dados)   │     │  (Procura)  │     │    (Ação)   │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
                                                                    │
                          ┌─────────────┐                          │
                          │5. DOCUMENTAR│◀─────────────────────────┘
                          │  (Aprender) │
                          └─────────────┘
```

---

## 📋 Exemplos de hipóteses

| Categoria | Hipótese |
|----------|------------|
| **Acesso inicial** | Os atacantes podem utilizar phishing para distribuir malware |
| **Persistência** | Os atacantes podem criar tarefas agendadas para obter persistência |
| **Credenciais** | Os atacantes podem extrair a memória do LSASS para recolher credenciais |
| **Lateral** | Os atacantes podem utilizar RDP para se deslocarem lateralmente |
| **Exfiltração** | Os atacantes podem utilizar túneis DNS para exfiltrar dados |

---

## 🔍 Técnicas de procura proativa de ameaças

### Procurar mecanismos de persistência

#### Chaves Run do registo do Windows
```powershell
# Consultar todas as chaves Run
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce" -ErrorAction SilentlyContinue
```

#### Tarefas agendadas
```powershell
# Listar todas as tarefas agendadas
Get-ScheduledTask | Where-Object {$_.State -ne "Disabled"} | 
    Select-Object TaskName, TaskPath, State | Format-Table

# Procurar tarefas suspeitas (não pertencentes à Microsoft)
Get-ScheduledTask | Where-Object {$_.Author -notlike "*Microsoft*"} | 
    Select-Object TaskName, Author, State
```

#### Serviços
```powershell
# Serviços sem assinatura (designação do original)
Get-WmiObject Win32_Service | Where-Object {$_.PathName -notlike "*Windows*"} | 
    Select-Object Name, PathName, StartMode, State
```

### Procurar movimento lateral

#### Ligações RDP
```powershell
# Sessões RDP (evento 4624, tipo 10)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} | 
    Where-Object {$_.Properties[8].Value -eq 10} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='Source';E={$_.Properties[18].Value}}
```

#### Ligações de rede
```powershell
# Ligações atuais
Get-NetTCPConnection -State Established | 
    Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, OwningProcess | 
    Format-Table

# Com nomes de processos
Get-NetTCPConnection | ForEach-Object {
    $proc = Get-Process -Id $_.OwningProcess -ErrorAction SilentlyContinue
    [PSCustomObject]@{
        LocalAddress = $_.LocalAddress
        RemoteAddress = $_.RemoteAddress
        RemotePort = $_.RemotePort
        Process = $proc.ProcessName
    }
} | Format-Table
```

### Procurar acesso a credenciais

#### Acesso ao LSASS
```powershell
# Evento 10 do Sysmon — Acesso ao LSASS
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=10} |
    Where-Object {$_.Properties[8].Value -like "*lsass.exe"} |
    Select-Object TimeCreated, @{N='Source';E={$_.Properties[4].Value}}
```

### Procurar execução

#### PowerShell codificado
```powershell
# PowerShell com codificação
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-PowerShell/Operational';ID=4104} |
    Where-Object {$_.Message -match "FromBase64" -or $_.Message -match "-enc"} |
    Select-Object TimeCreated, Message
```

#### Relação suspeita entre processos pai e filho
```powershell
# Office a iniciar cmd/powershell
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Where-Object {
        ($_.Properties[20].Value -match "WINWORD|EXCEL|OUTLOOK") -and
        ($_.Properties[4].Value -match "cmd|powershell")
    }
```

---

## 🐧 Threat hunting no Linux

### Persistência
```bash
# Tarefas cron
cat /etc/crontab
for user in $(cut -f1 -d: /etc/passwd); do crontab -l -u $user 2>/dev/null; done

# Chaves SSH autorizadas
find / -name "authorized_keys" 2>/dev/null

# Scripts de arranque
ls -la /etc/init.d/
systemctl list-unit-files --type=service | grep enabled

# Binários SUID
find / -perm -4000 -type f 2>/dev/null
```

### Rede
```bash
# Portas à escuta
ss -tulnp
netstat -tulnp

# Ligações estabelecidas
ss -tnp state established
lsof -i -P -n | grep ESTABLISHED

# Atividade DNS invulgar
tcpdump -i any port 53 -w dns.pcap
```

### Processos
```bash
# Todos os processos com a linha de comandos completa
ps auxwww

# Árvore de processos
pstree -p

# Processos ocultos
ps aux | awk '{print $2}' | xargs -I{} ls -la /proc/{}/exe 2>/dev/null

# Binários modificados recentemente
find /usr/bin /usr/sbin -mtime -7 2>/dev/null
```

---

## 📊 Procura de IOC

### Procura por hash
```powershell
# Calcular hashes
Get-FileHash -Algorithm SHA256 C:\suspicious.exe

# Procurar um hash malicioso conhecido
$badHash = "abc123..."
Get-ChildItem -Path C:\ -Recurse -File | Get-FileHash -Algorithm SHA256 |
    Where-Object {$_.Hash -eq $badHash}
```

### Procura por IP/domínio
```powershell
# Cache DNS
Get-DnsClientCache | Where-Object {$_.Name -like "*suspicious*"}

# Ligação a um IP malicioso conhecido
Get-NetTCPConnection | Where-Object {$_.RemoteAddress -eq "1.2.3.4"}
```

---

## 🛠️ Ferramentas

| Ferramenta | Finalidade |
|------|---------|
| **Velociraptor** | Procura de ameaças em endpoints em larga escala |
| **OSQuery** | Consultas a endpoints baseadas em SQL |
| **KAPE** | Recolha para triagem |
| **Chainsaw** | Análise rápida de registos Windows |
| **DeepBlueCLI** | Procura de ameaças com PowerShell |

---

## 📋 Lista rápida de verificação da procura de ameaças

```markdown
□ Verificar locais de execução automática (registo, serviços, tarefas)
□ Rever a criação recente de processos (evento 4688, Sysmon 1)
□ Analisar as ligações de rede
□ Procurar comandos codificados
□ Procurar acessos ao LSASS
□ Verificar relações invulgares entre processos pai e filho
□ Rever os registos do PowerShell
□ Procurar indicadores de movimento lateral
```

---

## 🔗 Guias de referência relacionados

- [Análise de registos](Log-Analysis.pt.md)
- [Deteção com SIEM](SIEM-Detection.pt.md)
- [Regras Sigma](Sigma-Rules.pt.md)

---

**Anterior:** [← Deteção com SIEM](SIEM-Detection.pt.md)

**Seguinte:** [Regras Sigma →](Sigma-Rules.pt.md)
