# 🚨 Guia de referência de resposta a incidentes

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Incident-Response.tr.md) • [🇬🇷 Ελληνικά](Incident-Response.el.md)

```
  ██╗███╗   ██╗ ██████╗██╗██████╗ ███████╗███╗   ██╗████████╗
  ██║████╗  ██║██╔════╝██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝
  ██║██╔██╗ ██║██║     ██║██║  ██║█████╗  ██╔██╗ ██║   ██║   
  ██║██║╚██╗██║██║     ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║   
  ██║██║ ╚████║╚██████╗██║██████╔╝███████╗██║ ╚████║   ██║   
  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   
        ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗███████╗███████╗
        ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔════╝██╔════╝
        ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║███████╗█████╗  
        ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║╚════██║██╔══╝  
        ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║███████║███████╗
        ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚══════╝
```

> **Notas de precisão:** IR significa resposta a incidentes; IOC são indicadores de comprometimento e TTP são táticas, técnicas e procedimentos. O diagrama é uma decomposição didática do ciclo clássico, não uma transcrição de seis fases oficiais do NIST; consultar também a [SP 800-61 Rev. 3](https://www.nist.gov/publications/incident-response-recommendations-and-considerations-cybersecurity-risk-management-csf). A leitura de `/dev/mem` não garante um dump completo: o kernel pode restringir o acesso à RAM ([mem(4)](https://man7.org/linux/man-pages/man4/mem.4.html)). `crontab -r` elimina todas as tarefas da crontab do utilizador, não apenas a entrada maliciosa. `systemctl disable` não termina, por si só, um serviço já em execução. A data de `forfiles` é um exemplo fixo; o formato depende da configuração regional. Os tempos de resposta são valores exemplificativos.

---

## 📊 Ciclo de vida da resposta a incidentes (NIST)

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ 1.PREPARAÇÃO │────▶│  2.DETEÇÃO   │────▶│ 3.CONTENÇÃO  │────▶│4.ERRADICAÇÃO │
│              │     │  E ANÁLISE   │     │              │     │              │
└──────────────┘     └──────────────┘     └──────────────┘     └──────────────┘
       ▲                                                              │
       │         ┌──────────────┐     ┌──────────────┐               │
       └─────────│   6.LIÇÕES   │◀────│5.RECUPERAÇÃO │◀──────────────┘
                 │  APRENDIDAS  │     │              │
                 └──────────────┘     └──────────────┘
```

---

## 1️⃣ Preparação

### Lista de verificação da documentação
```markdown
- [ ] Plano de resposta a incidentes documentado
- [ ] Lista de contactos (internos e externos)
- [ ] Plano de comunicação
- [ ] Inventário de ativos
- [ ] Diagramas de rede
- [ ] Configurações de referência
- [ ] Verificação das cópias de segurança
```

### Modelo de contactos da equipa de resposta a incidentes
```
Responsável de IR:          [Nome] - [Telefone] - [Email]
Segurança informática:      [Nome] - [Telefone] - [Email]
Apoio jurídico:            [Nome] - [Telefone] - [Email]
Comunicação:   [Nome] - [Telefone] - [Email]
Recursos humanos:               [Nome] - [Telefone] - [Email]
Perícia externa: [Empresa] - [Telefone]
Autoridades policiais:  [Entidade] - [Telefone]
```

### Ferramentas essenciais de resposta a incidentes
```bash
# Recolha forense
- FTK Imager
- Volatility
- Velociraptor
- KAPE

# Análise de rede
- Wireshark
- tcpdump
- Zeek

# Análise de registos
- Splunk/ELK
- grep/awk/sed
```

---

## 2️⃣ Deteção e análise

### Perguntas de triagem inicial
```markdown
1. Qual é o tipo de incidente?
   - [ ] Infeção por malware
   - [ ] Acesso não autorizado
   - [ ] Violação de dados
   - [ ] Negação de serviço
   - [ ] Ameaça interna
   - [ ] Phishing

2. Quando foi detetado?
3. Como foi detetado?
4. Que sistemas foram afetados?
5. Que dados podem ter sido afetados?
6. O incidente continua ativo?
```

### Triagem rápida no Windows
```cmd
# Sessões atuais de utilizadores
query user
qwinsta

# Ligações ativas
netstat -ano | findstr ESTABLISHED

# Processos em execução
tasklist /v
wmic process get processid,parentprocessid,commandline

# Modificações recentes de ficheiros
forfiles /P C:\ /S /D +01/01/2024

# Tarefas agendadas
schtasks /query /fo LIST /v

# Serviços
sc query state=all
wmic service get name,startmode,state

# Chaves Run do registo
reg query "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"
reg query "HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"

# Histórico recente do PowerShell
type %APPDATA%\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt
```

### Triagem rápida no Linux
```bash
# Quem tem sessão iniciada
w
who
last -a

# Ligações ativas
ss -tulnp
netstat -tulnp
lsof -i

# Processos em execução
ps auxwww
pstree -p

# Modificações recentes de ficheiros
find / -mtime -1 -type f 2>/dev/null

# Tarefas cron
cat /etc/crontab
ls -la /etc/cron.*
for user in $(cut -f1 -d: /etc/passwd); do crontab -u $user -l 2>/dev/null; done

# Scripts de arranque
ls -la /etc/init.d/
systemctl list-unit-files --type=service

# Histórico
cat ~/.bash_history
cat /root/.bash_history
```

### Análise de rede
```bash
# Capturar tráfego
tcpdump -i eth0 -w capture.pcap

# Filtrar um host específico
tcpdump -i eth0 host 192.168.1.100

# Tráfego HTTP
tcpdump -i eth0 port 80 or port 443

# Consultas DNS
tcpdump -i eth0 port 53
```

---

## 3️⃣ Contenção

### Contenção a curto prazo
```bash
# Isolar a rede (Windows)
netsh advfirewall set allprofiles state on
netsh advfirewall firewall add rule name="Block All" dir=in action=block
netsh advfirewall firewall add rule name="Block All Out" dir=out action=block

# Isolar a rede (Linux)
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

# Desativar a conta comprometida
net user compromised_user /active:no   # Windows
passwd -l compromised_user             # Linux

# Terminar o processo malicioso
taskkill /PID <pid> /F               # Windows
kill -9 <pid>                        # Linux
```

### Contenção a longo prazo
```bash
# Segmentar a rede
# Aplicar regras de firewall para isolar a VLAN afetada
# Atualizar as ACL nos switches/routers

# Bloquear IP/domínios maliciosos na firewall
# Adicionar à lista de bloqueio do proxy
# Atualizar o DNS sinkhole (redirecionamento de domínios maliciosos)

# Desativar temporariamente os serviços afetados
sc config <service> start= disabled   # Windows
systemctl disable <service>           # Linux
```

### Preservação de provas
```bash
# Dump de memória (Windows — utilizar WinPMEM ou DumpIt)
winpmem_mini_x64.exe memory.raw

# Dump de memória (Linux)
dd if=/dev/mem of=/evidence/memory.dump bs=1M

# Imagem de disco
dd if=/dev/sda of=/evidence/disk.img bs=4M status=progress

# Hash para verificar a integridade
sha256sum /evidence/disk.img > disk.img.sha256

# Recolher primeiro os dados voláteis (ordem de volatilidade):
# 1. Memória
# 2. Estado da rede
# 3. Processos em execução
# 4. Disco
```

---

## 4️⃣ Erradicação

### Remoção de malware
```bash
# Identificar e remover mecanismos de persistência

# Windows — Registo
reg delete "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v "MalwareName" /f

# Windows — Tarefas agendadas
schtasks /delete /tn "MalwareTask" /f

# Windows — Serviços
sc delete "MalwareService"

# Linux - Cron
crontab -r
rm /etc/cron.d/malware

# Linux - Systemd
systemctl disable malware
rm /etc/systemd/system/malware.service
```

### Reposição de palavras-passe
```bash
# Forçar a reposição das palavras-passe de todas as contas comprometidas
# Repor as palavras-passe das contas de serviço
# Substituir as chaves/tokens de API
# Invalidar as sessões

# Windows — Forçar a alteração da palavra-passe
net user username /logonpasswordchg:yes

# Invalidar tickets Kerberos (se o AD tiver sido comprometido)
# - Repor a palavra-passe de krbtgt DUAS VEZES
# - Repor as palavras-passe de todas as contas de administrador
```

### Correções e atualizações
```bash
# Aplicar as correções de segurança em falta
# Atualizar as assinaturas do antivírus
# Corrigir as configurações incorretas que permitiram o ataque
```

---

## 5️⃣ Recuperação

### Restauro dos sistemas
```bash
# Restaurar a partir de uma cópia de segurança limpa
# Reinstalar os sistemas comprometidos
# Validar a integridade antes de voltar a colocar os sistemas em serviço

# Restauro gradual:
1. Restaurar primeiro os sistemas críticos
2. Monitorizar sinais de reinfeção
3. Restaurar os restantes sistemas
4. Retomar as operações normais
```

### Monitorização após a recuperação
```bash
# Aumentar o nível de detalhe dos registos
# Implementar monitorização adicional
# Vigiar o eventual regresso do atacante

# Indicadores principais a monitorizar:
- Os mesmos IOC (IP, domínios, hashes)
- TTP semelhantes (táticas, técnicas e procedimentos)
- Tentativas de acesso às mesmas contas
- Comunicação com a infraestrutura C2 (comando e controlo)
```

---

## 6️⃣ Lições aprendidas

### Modelo de relatório pós-incidente
```markdown
## Resumo do incidente
- ID do incidente:
- Data de deteção:
- Data de contenção:
- Data de resolução:
- Tipo de incidente:
- Gravidade:

## Cronologia
| Data/hora | Event |
|-----------|-------|
| YYYY-MM-DD HH:MM | Deteção inicial |
| ... | ... |

## Avaliação do impacto
- Sistemas afetados:
- Dados comprometidos:
- Impacto no negócio:
- Impacto financeiro:

## Análise da causa raiz
- Como obteve o atacante acesso?
- Que vulnerabilidades foram exploradas?
- Que controlos falharam?

## Ações realizadas
1. Deteção
2. Contenção
3. Erradicação
4. Recuperação

## Recomendações
1. Melhorias imediatas
2. Melhorias de segurança a longo prazo
3. Alterações às políticas

## Lições aprendidas
- O que funcionou bem?
- O que poderia ser melhorado?
- Foram identificadas necessidades de formação?
```

---

## 📊 Referência rápida

### Classificação da gravidade

| Nível | Descrição | Tempo de resposta |
|-------|-------------|---------------|
| **Crítico** | Intrusão ativa, exfiltração de dados | Imediato |
| **Elevado** | Comprometimento confirmado | < 1 hora |
| **Médio** | Possível comprometimento | < 4 horas |
| **Baixo** | Atividade suspeita | < 24 horas |

### IOC comuns a recolher

| Tipo de IOC | Exemplos |
|----------|----------|
| **Hash** | MD5, SHA1, SHA256 |
| **IP** | IP de origem/destino |
| **Domínio** | Domínios de C2 |
| **URL** | URL maliciosos |
| **Email** | Remetente de phishing |
| **Ficheiro** | Nome do ficheiro de malware |
| **Registo** | Chaves de persistência |

---

## 🔗 Guias de referência relacionados

- [Análise de registos](Log-Analysis.pt.md)
- [Deteção com SIEM](SIEM-Detection.pt.md)
- [Volatility (Análise forense)](../../Volatility/README.md)

---

**Seguinte:** [Análise de registos →](Log-Analysis.pt.md)
