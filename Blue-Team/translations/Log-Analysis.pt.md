# 📊 Guia de referência de análise de registos

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Log-Analysis.tr.md) • [🇬🇷 Ελληνικά](Log-Analysis.el.md)

```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

> **Notas de precisão:** Os caminhos de registos variam por distribuição e configuração. Os índices `Properties[n]` dependem do esquema e da versão do evento; confirmar os campos XML antes de reutilizar as consultas, sobretudo as do Sysmon. O evento 4672 indica atribuição de privilégios especiais e não prova, isoladamente, uma escalada de privilégios. Os padrões grep são heurísticas: um resultado não confirma um ataque e a expressão de IP também aceita octetos fora do intervalo válido.

---

## 🖥️ Registos de eventos do Windows

### Localização dos registos
```
# Registo de segurança
C:\Windows\System32\winevt\Logs\Security.evtx

# Registo do sistema
C:\Windows\System32\winevt\Logs\System.evtx

# Registo de aplicações
C:\Windows\System32\winevt\Logs\Application.evtx

# Registos do PowerShell
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon (se estiver instalado)
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

### ID de eventos de segurança críticos

#### Eventos de autenticação
| ID do evento | Descrição | Importância |
|----------|-------------|------------|
| **4624** | Início de sessão bem-sucedido | Acompanhar o acesso dos utilizadores |
| **4625** | Falha no início de sessão | Deteção de força bruta |
| **4634** | Fim de sessão | Acompanhamento de sessões |
| **4647** | Fim de sessão iniciado pelo utilizador | Acompanhamento de sessões |
| **4648** | Início de sessão com credenciais explícitas | Movimento lateral |
| **4672** | Atribuição de privilégios especiais | Atividade administrativa |
| **4768** | Pedido de TGT Kerberos | Autenticação no AD |
| **4769** | Ticket de serviço Kerberos | Acesso a serviços |
| **4771** | Falha na pré-autenticação Kerberos | Password spray (tentativa de uma palavra-passe em várias contas) |
| **4776** | Autenticação NTLM | Validação de credenciais |

#### Tipos de início de sessão (para 4624/4625)
| Tipo | Descrição |
|------|-------------|
| 2 | Interativo (local) |
| 3 | Rede |
| 4 | Em lote |
| 5 | Serviço |
| 7 | Desbloqueio |
| 8 | Rede com credenciais em texto simples |
| 9 | Novas credenciais |
| 10 | Interativo remoto (RDP) |
| 11 | Interativo com credenciais em cache |

#### Gestão de contas
| ID do evento | Descrição |
|----------|-------------|
| **4720** | Conta de utilizador criada |
| **4722** | Conta de utilizador ativada |
| **4723** | Tentativa de alteração de palavra-passe |
| **4724** | Reposição de palavra-passe |
| **4725** | Conta de utilizador desativada |
| **4726** | Conta de utilizador eliminada |
| **4728** | Membro adicionado a um grupo de segurança |
| **4732** | Membro adicionado a um grupo local |
| **4756** | Membro adicionado a um grupo universal |

#### Eventos de processos e serviços
| ID do evento | Descrição |
|----------|-------------|
| **4688** | Criação de processos |
| **4689** | Terminação de processos |
| **7045** | Serviço instalado |
| **7036** | Alteração do estado de um serviço |
| **4697** | Serviço instalado (registo de segurança) |

#### Tarefas agendadas
| ID do evento | Descrição |
|----------|-------------|
| **4698** | Tarefa agendada criada |
| **4699** | Tarefa agendada eliminada |
| **4700** | Tarefa agendada ativada |
| **4702** | Tarefa agendada atualizada |

### Comandos de consulta em PowerShell
```powershell
# Obter entradas do registo de segurança
Get-WinEvent -LogName Security -MaxEvents 100

# Falhas de início de sessão (4625)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# Inícios de sessão bem-sucedidos (4624)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# Criação de novos processos (4688)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# Instalações de serviços (7045)
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# Pesquisar por intervalo temporal
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

### Comandos Wevtutil
```cmd
# Consultar o registo de segurança
wevtutil qe Security /c:50 /f:text

# Consultar um ID de evento específico
wevtutil qe Security /q:"*[System[(EventID=4625)]]" /c:100 /f:text

# Exportar o registo
wevtutil epl Security C:\evidence\security.evtx

# Limpar o registo (utilizar com cuidado)
wevtutil cl Security
```

---

## 🐧 Análise de registos Linux

### Localização dos registos
```bash
# Autenticação
/var/log/auth.log        # Debian/Ubuntu
/var/log/secure          # RHEL/CentOS

# Sistema
/var/log/syslog          # Debian/Ubuntu
/var/log/messages        # RHEL/CentOS

# Kernel
/var/log/kern.log
/var/log/dmesg

# Cron
/var/log/cron

# Auditoria
/var/log/audit/audit.log

# Servidores web
/var/log/apache2/        # Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # RHEL Apache

# SSH
/var/log/auth.log
```

### Comandos comuns de análise de registos

#### Análise da autenticação
```bash
# Tentativas SSH falhadas
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# Inícios de sessão SSH bem-sucedidos
grep "Accepted" /var/log/auth.log

# Força bruta SSH (contagem por IP)
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# Escalada de privilégios de utilizador (sudo)
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log

# Tentativas de su
grep "su:" /var/log/auth.log
```

#### Análise do sistema
```bash
# Horas de arranque/encerramento
last -x | grep shutdown
last -x | grep reboot

# Utilizadores com sessão iniciada
who
w

# Histórico de inícios de sessão
last
lastlog

# Tentativas de início de sessão falhadas
lastb
```

#### Análise de processos/serviços
```bash
# Execuções de tarefas cron
grep CRON /var/log/syslog

# Arranques/paragens de serviços
grep -E "Started|Stopped" /var/log/syslog
journalctl -u <service-name>

# Mensagens do kernel
dmesg | tail -100
grep -i error /var/log/kern.log
```

### Padrões úteis de grep/awk
```bash
# Extrair IP dos registos
grep -oE '\b[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\b' /var/log/auth.log

# Contar ocorrências por IP
cat /var/log/auth.log | grep "Failed" | awk '{print $11}' | sort | uniq -c | sort -rn | head -20

# Pesquisa por intervalo temporal
awk '/Jan 15 10:00/,/Jan 15 11:00/' /var/log/auth.log

# Pesquisar em vários ficheiros
grep -r "error" /var/log/

# Monitorização em tempo real
tail -f /var/log/auth.log | grep --line-buffered "Failed"
```

### Journalctl (Systemd)
```bash
# Todos os registos
journalctl

# Modo de acompanhamento contínuo
journalctl -f

# Desde uma hora específica
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# Por unidade/serviço
journalctl -u sshd
journalctl -u nginx

# Por prioridade
journalctl -p err       # Apenas erros
journalctl -p warning   # Avisos e níveis mais graves

# Mensagens do kernel
journalctl -k

# Saída em JSON
journalctl -o json
```

---

## 🌐 Registos de servidores web

### Formato dos registos do Apache
```
# Formato de registo combinado (Combined Log Format)
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."

# Campos:
# IP - ident - utilizador - [data/hora] "método caminho protocolo" estado tamanho "referer" "user-agent"
```

### Formato dos registos do Nginx
```
# Formato predefinido semelhante ao do Apache
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."
```

### Análise de registos web
```bash
# URL mais pedidos
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# IP mais frequentes
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Códigos de estado HTTP
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# Erros 404
grep ' 404 ' /var/log/apache2/access.log

# Pedidos POST (possíveis ataques)
grep '"POST' /var/log/apache2/access.log

# Tentativas de injeção SQL
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# Tentativas de path traversal (travessia de diretórios)
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log

# Pedidos por hora
awk '{print $4}' /var/log/apache2/access.log | cut -d: -f2 | sort | uniq -c
```

---

## 🛡️ Análise de registos do Sysmon

### ID de eventos principais do Sysmon
| ID do evento | Descrição |
|----------|-------------|
| 1 | Criação de processos |
| 2 | Hora de criação de ficheiro alterada |
| 3 | Ligação de rede |
| 5 | Processo terminado |
| 6 | Controlador carregado |
| 7 | Imagem carregada (DLL) |
| 8 | CreateRemoteThread |
| 10 | ProcessAccess |
| 11 | FileCreate |
| 12-14 | Eventos do registo |
| 15 | FileCreateStreamHash |
| 17-18 | Eventos de pipes |
| 22 | Consulta DNS |

### Consultas Sysmon em PowerShell
```powershell
# Criação de processos (evento 1)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='CommandLine';E={$_.Properties[10].Value}}

# Ligações de rede (evento 3)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=3} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='DestIP';E={$_.Properties[14].Value}}

# Consultas DNS (evento 22)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=22} |
    Select-Object TimeCreated, @{N='Query';E={$_.Properties[4].Value}}
```

---

## 📊 Referência rápida

### Padrões suspeitos a procurar

| Categoria | Padrão |
|----------|---------|
| **Força bruta** | Vários eventos 4625 do mesmo IP |
| **Ataque de password spraying** | Eventos 4625 para muitos utilizadores a partir do mesmo IP |
| **Movimento lateral** | 4624 de tipo 3 + 4648 |
| **Persistência** | 4698 (nova tarefa agendada) |
| **Escalada de privilégios** | 4672 em contas sem privilégios de administrador |
| **Extração de credenciais (credential dumping)** | Acesso a lsass.exe |

### Ferramentas de análise de registos
| Ferramenta | Utilização |
|------|-----|
| **grep/awk/sed** | Interpretação de registos Linux |
| **PowerShell** | Análise de registos Windows |
| **Splunk** | SIEM empresarial |
| **ELK Stack** | SIEM de código aberto |
| **LogParser** | Consultas SQL sobre registos |
| **Chainsaw** | Análise rápida de registos Windows |

---

## 🔗 Guias de referência relacionados

- [Resposta a incidentes](Incident-Response.pt.md)
- [Deteção com SIEM](SIEM-Detection.pt.md)
- [Regras Sigma](Sigma-Rules.pt.md)

---

**Anterior:** [← Resposta a incidentes](Incident-Response.pt.md)

**Seguinte:** [Deteção com SIEM →](SIEM-Detection.pt.md)
