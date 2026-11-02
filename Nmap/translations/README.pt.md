# 🔍 Nmap — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   __                      
   / | / /___ ___  ____ _____  
  /  |/ / __ `__ \/ __ `/ __ \ 
 / /|  / / / / / / /_/ / /_/ / 
/_/ |_/_/ /_/ /_/\__,_/ .___/  
                     /_/       
    Network Mapper — o analisador de redes
```

<p align="center">
  <img src="https://img.shields.io/badge/Nmap-Network_Scanner-blue?style=for-the-badge" alt="Nmap">
  <img src="https://img.shields.io/badge/Reconnaissance-red?style=for-the-badge" alt="Reconnaissance">
  <img src="https://img.shields.io/badge/Port_Scanning-green?style=for-the-badge" alt="Port Scanning">
  <img src="https://img.shields.io/badge/NSE_Scripts-purple?style=for-the-badge" alt="NSE Scripts">
</p>

<p align="center">
  <b>🌐 A ferramenta mais poderosa de análise e descoberta de redes</b>
</p>

> **Termos usados:** reconhecimento (*reconnaissance*) é a recolha de informações sobre o alvo; enumeração é a identificação organizada de sistemas, serviços ou recursos. Os comandos, opções e valores dos exemplos foram mantidos; a sua disponibilidade depende da versão e da plataforma.

> **Precisão dos exemplos:** “stealth” significa menor visibilidade em certos cenários, não invisibilidade. TCP Connect não “funciona sempre”; respostas, filtros e permissões condicionam os resultados. As opções de evasão têm limitações e nem todas se aplicam a todas as técnicas. Ver [técnicas de análise](https://nmap.org/book/man-port-scanning-techniques.html) e [evasão e falsificação](https://nmap.org/book/man-bypass-firewalls-ids.html). Indicar portas UDP com `U:` não ativa, por si só, `-sU`; a técnica e as portas têm de ser compatíveis. `-p-` abrange as portas 1–65535. Ver [definição de portas](https://nmap.org/book/man-port-specification.html).

> **NSE:** é o motor de scripts do Nmap. Seleções amplas como `http-*`, `vuln` ou `not intrusive` não são sinónimos de uma análise sem impacto; as categorias e os efeitos devem ser avaliados por script. “Safe” também não constitui uma garantia universal. Ver [documentação NSE](https://nmap.org/book/man-nse.html). Definir `--max-retries 3` fixa um limite de três repetições; não significa necessariamente aumentá-lo.

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Sintaxe básica](#-basic-syntax)
- [Definição dos alvos](#-target-specification)
- [Descoberta de sistemas](#-host-discovery)
- [Técnicas de análise de portas](#-port-scanning-techniques)
- [Definição das portas](#-port-specification)
- [Deteção de serviços e versões](#-service--version-detection)
- [Deteção do sistema operativo](#-os-detection)
- [Temporização e desempenho](#-timing--performance)
- [Scripts NSE](#-nse-scripts)
- [Formatos de saída](#-output-formats)
- [Evasão de firewall/IDS](#-firewallids-evasion)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Conselhos e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

O **Nmap** (Network Mapper) é um utilitário gratuito e de código aberto para descoberta de redes e auditoria de segurança. É uma ferramenta de referência para a análise de redes.

### Funcionalidades principais

| Funcionalidade | Descrição |
|---------|-------------|
| **Descoberta de sistemas** | Encontrar sistemas ativos numa rede |
| **Análise de portas** | Identificar portas abertas e serviços |
| **Deteção de versões** | Determinar as versões dos serviços |
| **Deteção do sistema operativo** | Identificar sistemas operativos |
| **Scripts NSE** | Análise e exploração de vulnerabilidades |
| **Formatos de saída** | Saída XML, pesquisável com grep e normal |

### Estados das portas

| Estado | Descrição |
|-------|-------------|
| `open` | Uma aplicação está a aceitar ligações |
| `closed` | A porta é acessível, mas nenhuma aplicação está à escuta |
| `filtered` | O Nmap não consegue determinar se a porta está aberta (firewall) |
| `unfiltered` | A porta é acessível, mas o seu estado é desconhecido |
| `open|filtered` | Não é possível determinar se está aberta ou filtrada |
| `closed|filtered` | Não é possível determinar se está fechada ou filtrada |

---

<a id="-installation"></a>
## 📥 Instalação

### Kali Linux (pré-instalado)
```bash
# Atualizar o Nmap
sudo apt update && sudo apt install nmap
```

### Ubuntu/Debian
```bash
sudo apt install nmap
```

### CentOS/RHEL
```bash
sudo yum install nmap
```

### Windows
```bash
# Descarregar de https://nmap.org/download.html
# Ou utilizar Chocolatey
choco install nmap
```

### macOS
```bash
brew install nmap
```

### Verificar a instalação
```bash
nmap --version
nmap -h          # Ajuda
```

---

<a id="-basic-syntax"></a>
## ⌨️ Sintaxe básica

### Sintaxe geral
```bash
nmap [Scan Type] [Options] {target}
```

### Exemplos para começar
```bash
# Análise básica
nmap 192.168.1.1

# Analisar vários sistemas
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Analisar um intervalo
nmap 192.168.1.1-254

# Analisar toda a sub-rede
nmap 192.168.1.0/24

# Analisar alvos a partir de um ficheiro
nmap -iL targets.txt
```

---

<a id="-target-specification"></a>
## 🎯 Definição dos alvos

### Alvos individuais
```bash
# Endereço IP
nmap 192.168.1.1

# Nome do sistema
nmap scanme.nmap.org

# Domínio
nmap example.com
```

### Vários alvos
```bash
# Separados por espaços
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Intervalo
nmap 192.168.1.1-100

# Notação CIDR
nmap 192.168.1.0/24

# Wildcard (carácter universal)
nmap 192.168.1.*

# Intervalo de octetos
nmap 192.168.0-255.1-254

# Combinação de formatos
nmap 192.168.1.0/24 10.0.0.1
```

### Entrada a partir de ficheiro
```bash
# Ler de um ficheiro
nmap -iL targets.txt

# targets.txt:
# 192.168.1.1
# 192.168.1.0/24
# scanme.nmap.org
```

### Excluir alvos
```bash
# Excluir um sistema
nmap 192.168.1.0/24 --exclude 192.168.1.1

# Excluir alvos a partir de ficheiro
nmap 192.168.1.0/24 --excludefile exclude.txt
```

### Alvos aleatórios
```bash
# Analisar sistemas aleatórios (para investigação)
nmap -iR 100
```

---

<a id="-host-discovery"></a>
## 🔎 Descoberta de sistemas

### Opções de descoberta

| Opção | Descrição |
|--------|-------------|
| `-sL` | Análise de listagem — apenas listar alvos |
| `-sn` | Descoberta de sistemas — sem análise de portas |
| `-Pn` | Saltar a descoberta de sistemas (tratar todos como ativos) |
| `-PS` | Sonda TCP SYN de descoberta |
| `-PA` | Sonda TCP ACK de descoberta |
| `-PU` | Sonda UDP de descoberta |
| `-PE` | Sonda ICMP echo de descoberta |
| `-PP` | Sonda ICMP timestamp de descoberta |
| `-PM` | Sonda ICMP netmask de descoberta |
| `-PO` | Sonda de protocolo IP para descoberta |
| `-PR` | Sonda ARP de descoberta (rede local) |

### Descoberta de sistemas (sem análise de portas)
```bash
# Descobrir apenas sistemas ativos
nmap -sn 192.168.1.0/24

# Descoberta com uma lista de sistemas
nmap -sn -iL hosts.txt
```

### Saltar a descoberta de sistemas
```bash
# Analisar todos os sistemas (sem descoberta prévia por ping)
nmap -Pn 192.168.1.1

# Útil quando:
# - Os sistemas bloqueiam ICMP
# - A análise atravessa uma firewall
```

### Descoberta por TCP SYN/ACK
```bash
# Sonda TCP SYN na porta 80
nmap -PS80 192.168.1.1

# Sonda TCP ACK na porta 80
nmap -PA80 192.168.1.1

# Várias portas
nmap -PS22,80,443 192.168.1.1
```

### Descoberta por UDP
```bash
# Sonda UDP de descoberta
nmap -PU53 192.168.1.1

# Várias portas
nmap -PU53,161 192.168.1.1
```

### Descoberta por ICMP
```bash
# ICMP echo
nmap -PE 192.168.1.1

# ICMP timestamp
nmap -PP 192.168.1.1

# ICMP netmask
nmap -PM 192.168.1.1
```

### Descoberta por ARP (rede local)
```bash
# Análise ARP (a mais rápida na rede local)
nmap -PR 192.168.1.0/24

# Apenas análise ARP
nmap -sn -PR 192.168.1.0/24
```

### Desativar a resolução DNS
```bash
# Sem resolução DNS (mais rápido)
nmap -n 192.168.1.1

# Resolver sempre os nomes DNS
nmap -R 192.168.1.1
```

---

<a id="-port-scanning-techniques"></a>
## 🔌 Técnicas de análise de portas

### Visão geral dos tipos de análise

| Opção | Nome | Descrição |
|--------|------|-------------|
| `-sS` | Análise SYN | Análise discreta (predefinida, requer root) |
| `-sT` | Ligação TCP completa | Ligação TCP completa (não requer root) |
| `-sU` | Análise UDP | Analisar portas UDP |
| `-sA` | Análise ACK | Mapear regras da firewall |
| `-sW` | Análise Window | Semelhante à análise ACK, utiliza o campo de janela TCP |
| `-sM` | Análise Maimon | Sonda FIN/ACK |
| `-sN` | Análise NULL | Sem flags definidas |
| `-sF` | Análise FIN | Flag FIN definida |
| `-sX` | Análise Xmas | Flags FIN, PSH e URG |

### Análise SYN discreta (predefinida)
```bash
# Análise mais comum (requer root)
sudo nmap -sS 192.168.1.1

# Nunca conclui o handshake TCP
# Rápida e discreta
```

### Análise por ligação TCP completa
```bash
# Ligação TCP completa
nmap -sT 192.168.1.1

# Não requer root
# Mais detetável, mas funciona sempre
```

### Análise UDP
```bash
# Analisar portas UDP
sudo nmap -sU 192.168.1.1

# A análise UDP é LENTA
# Combinar com deteção de versões
sudo nmap -sU -sV 192.168.1.1

# Portas UDP comuns
sudo nmap -sU -p 53,67,68,69,123,161,500 192.168.1.1
```

### Análise combinada TCP/UDP
```bash
# Analisar TCP e UDP
sudo nmap -sS -sU 192.168.1.1

# Portas específicas
sudo nmap -sS -sU -p T:80,443,U:53,161 192.168.1.1
```

### Análises NULL, FIN e Xmas
```bash
# Análise NULL (sem flags)
sudo nmap -sN 192.168.1.1

# Análise FIN (apenas flag FIN)
sudo nmap -sF 192.168.1.1

# Análise Xmas (FIN, PSH, URG)
sudo nmap -sX 192.168.1.1

# Útil para:
# - Contornar firewalls sem estado
# - Não funciona em Windows
```

### Análise ACK
```bash
# Mapear regras da firewall
sudo nmap -sA 192.168.1.1

# Determinar:
# - Que portas são filtradas
# - Regras da firewall
# Devolve: unfiltered ou filtered
```

### Análise Window
```bash
# Semelhante à análise ACK
sudo nmap -sW 192.168.1.1

# Utiliza o tamanho da janela TCP para determinar o estado
```

### Análise idle/zombie
```bash
# Máxima discrição — utilizar um sistema zombie
sudo nmap -sI zombie_host 192.168.1.1

# O teu IP nunca contacta o alvo
# Requer um sistema zombie adequado
```

---

<a id="-port-specification"></a>
## 🎚️ Definição das portas

### Opções de portas

| Opção | Descrição |
|--------|-------------|
| `-p <port>` | Analisar portas específicas |
| `-p-` | Analisar todas as 65535 portas |
| `-p 1-1000` | Analisar um intervalo de portas |
| `--top-ports <n>` | Analisar as N portas mais frequentes |
| `-F` | Análise rápida (100 portas mais frequentes) |
| `-r` | Analisar as portas sequencialmente |

### Portas específicas
```bash
# Uma porta
nmap -p 80 192.168.1.1

# Várias portas
nmap -p 80,443,22 192.168.1.1

# Intervalo de portas
nmap -p 1-1000 192.168.1.1

# Todas as portas
nmap -p- 192.168.1.1

# Combinação de formatos
nmap -p 22,80,443,1000-2000 192.168.1.1
```

### Portas por protocolo
```bash
# Portas TCP
nmap -p T:80,443 192.168.1.1

# Portas UDP
nmap -p U:53,161 192.168.1.1

# Ambos
nmap -p T:80,443,U:53 192.168.1.1
```

### Portas mais frequentes
```bash
# 100 portas mais frequentes (rápido)
nmap -F 192.168.1.1

# 10 portas mais frequentes
nmap --top-ports 10 192.168.1.1

# 1000 portas mais frequentes (predefinição)
nmap --top-ports 1000 192.168.1.1
```

### Referência de portas comuns

| Porta | Serviço |
|------|---------|
| 21 | FTP |
| 22 | SSH |
| 23 | Telnet |
| 25 | SMTP |
| 53 | DNS |
| 80 | HTTP |
| 110 | POP3 |
| 135 | MSRPC |
| 139 | NetBIOS |
| 143 | IMAP |
| 443 | HTTPS |
| 445 | SMB |
| 993 | IMAPS |
| 995 | POP3S |
| 1433 | MSSQL |
| 1521 | Oracle |
| 3306 | MySQL |
| 3389 | RDP |
| 5432 | PostgreSQL |
| 5900 | VNC |
| 8080 | HTTP-Proxy |

---

<a id="-service--version-detection"></a>
## 🔬 Deteção de serviços e versões

### Opções de deteção de versões

| Opção | Descrição |
|--------|-------------|
| `-sV` | Deteção de versões |
| `--version-intensity <0-9>` | Intensidade das sondas |
| `--version-light` | Deteção ligeira de versões (intensidade 2) |
| `--version-all` | Experimentar todas as sondas (intensidade 9) |
| `--version-trace` | Depurar a análise de versões |

### Deteção básica de versões
```bash
# Detetar as versões dos serviços
nmap -sV 192.168.1.1

# Saída habitual:
# PORT    STATE SERVICE VERSION
# 22/tcp  open  ssh     OpenSSH 7.9
# 80/tcp  open  http    Apache httpd 2.4.38
```

### Intensidade da deteção de versões
```bash
# Análise ligeira (mais rápida)
nmap -sV --version-light 192.168.1.1

# Todas as sondas (exaustivo)
nmap -sV --version-all 192.168.1.1

# Intensidade personalizada (0-9)
nmap -sV --version-intensity 5 192.168.1.1
```

---

<a id="-os-detection"></a>
## 💻 Deteção do sistema operativo

### Opções de deteção do sistema operativo

| Opção | Descrição |
|--------|-------------|
| `-O` | Ativar a deteção do sistema operativo |
| `--osscan-limit` | Analisar apenas sistemas com condições favoráveis |
| `--osscan-guess` | Inferir o sistema operativo de forma mais agressiva |
| `--max-os-tries` | Máximo de tentativas de deteção do sistema operativo |

### Deteção básica do sistema operativo
```bash
# Detetar o sistema operativo (requer root)
sudo nmap -O 192.168.1.1

# Exemplo de saída:
# Running: Linux 3.X|4.X
# OS CPE: cpe:/o:linux:linux_kernel:3
# OS details: Linux 3.10 - 4.11
```

### Inferência agressiva do sistema operativo
```bash
# Inferência mais agressiva
sudo nmap -O --osscan-guess 192.168.1.1
```

### Análise agressiva (-A)
```bash
# Ativar deteção do sistema operativo, versões, scripts e traceroute
sudo nmap -A 192.168.1.1

# Equivalente a: -O -sV -sC --traceroute
```

---

<a id="-timing--performance"></a>
## ⏱️ Temporização e desempenho

### Perfis de temporização

| Opção | Nome | Descrição |
|--------|------|-------------|
| `-T0` | Paranoid | Evasão de IDS, muito lento |
| `-T1` | Sneaky | Evasão de IDS, lento |
| `-T2` | Polite | Menor utilização de largura de banda, mais lento |
| `-T3` | Normal | Temporização predefinida |
| `-T4` | Aggressive | Rápido, para redes fiáveis |
| `-T5` | Insane | Muito rápido, pode não detetar algumas portas |

### Utilização
```bash
# Lento e discreto
nmap -T1 192.168.1.1

# Normal (predefinido)
nmap -T3 192.168.1.1

# Rápido
nmap -T4 192.168.1.1

# Muito rápido (rede local)
nmap -T5 192.168.1.1
```

### Ajuste detalhado da temporização

| Opção | Descrição |
|--------|-------------|
| `--min-rate <n>` | Mínimo de pacotes por segundo |
| `--max-rate <n>` | Máximo de pacotes por segundo |
| `--min-parallelism <n>` | Mínimo de sondas em paralelo |
| `--max-parallelism <n>` | Máximo de sondas em paralelo |
| `--min-hostgroup <n>` | Mínimo de sistemas em paralelo |
| `--max-hostgroup <n>` | Máximo de sistemas em paralelo |
| `--host-timeout <time>` | Abandonar um sistema após o tempo indicado |
| `--scan-delay <time>` | Intervalo entre sondas |
| `--max-retries <n>` | Máximo de repetições de sondas |

```bash
# Enviar pelo menos 1000 pacotes/segundo
nmap --min-rate 1000 192.168.1.0/24

# Máximo de 100 pacotes/segundo (abrandar)
nmap --max-rate 100 192.168.1.1

# Abandonar o sistema após 30 minutos
nmap --host-timeout 30m 192.168.1.0/24

# Intervalo entre sondas
nmap --scan-delay 1s 192.168.1.1
```

---

<a id="-nse-scripts"></a>
## 📜 Scripts NSE

### Categorias de scripts

| Categoria | Descrição |
|----------|-------------|
| `auth` | Contorno da autenticação |
| `broadcast` | Descobrir sistemas através de broadcast |
| `brute` | Ataques de força bruta |
| `default` | Scripts predefinidos (-sC) |
| `discovery` | Descoberta de serviços |
| `dos` | Negação de serviço |
| `exploit` | Explorar vulnerabilidades |
| `external` | Consultar serviços externos |
| `fuzzer` | Fuzzing |
| `intrusive` | Pode provocar falhas em serviços |
| `malware` | Deteção de malware |
| `safe` | Scripts seguros |
| `version` | Deteção de versões |
| `vuln` | Deteção de vulnerabilidades |

### Opções de scripts

| Opção | Descrição |
|--------|-------------|
| `-sC` | Scripts predefinidos |
| `--script <scripts>` | Executar scripts específicos |
| `--script-args <args>` | Argumentos do script |
| `--script-updatedb` | Atualizar a base de dados de scripts |
| `--script-help <script>` | Ajuda do script |

### Scripts predefinidos
```bash
# Executar os scripts predefinidos
nmap -sC 192.168.1.1

# Com deteção de versões (recomendado)
nmap -sV -sC 192.168.1.1
```

### Executar scripts específicos
```bash
# Um script
nmap --script http-title 192.168.1.1

# Vários scripts
nmap --script http-title,http-headers 192.168.1.1

# Categoria
nmap --script vuln 192.168.1.1

# Wildcard (carácter universal)
nmap --script "http-*" 192.168.1.1

# Combinar categorias
nmap --script "vuln and safe" 192.168.1.1

# Excluir scripts
nmap --script "not intrusive" 192.168.1.1
```

### Scripts populares

#### Análise de vulnerabilidades
```bash
# Todos os scripts de vulnerabilidades
nmap --script vuln 192.168.1.1

# Verificação de uma CVE específica
nmap --script smb-vuln-ms17-010 192.168.1.1

# Heartbleed
nmap --script ssl-heartbleed 192.168.1.1

# ShellShock
nmap --script http-shellshock 192.168.1.1
```

#### Scripts HTTP
```bash
# Enumeração HTTP
nmap --script http-enum 192.168.1.1

# Cabeçalhos HTTP
nmap --script http-headers 192.168.1.1

# Métodos HTTP
nmap --script http-methods 192.168.1.1

# Título HTTP
nmap --script http-title 192.168.1.1

# Robots.txt
nmap --script http-robots.txt 192.168.1.1
```

#### Scripts SMB
```bash
# Enumeração SMB
nmap --script smb-enum-shares 192.168.1.1
nmap --script smb-enum-users 192.168.1.1
nmap --script smb-os-discovery 192.168.1.1

# Vulnerabilidades SMB
nmap --script smb-vuln* 192.168.1.1
```

#### Scripts SSH
```bash
# Métodos de autenticação SSH
nmap --script ssh-auth-methods 192.168.1.1

# Chave do sistema SSH
nmap --script ssh-hostkey 192.168.1.1

# Força bruta em SSH
nmap --script ssh-brute 192.168.1.1
```

#### Scripts DNS
```bash
# Força bruta DNS
nmap --script dns-brute example.com

# Transferência de zona DNS
nmap --script dns-zone-transfer example.com
```

#### Scripts MySQL/bases de dados
```bash
# Informações de MySQL
nmap --script mysql-info 192.168.1.1

# Enumeração MySQL
nmap --script mysql-enum 192.168.1.1

# Força bruta em MySQL
nmap --script mysql-brute 192.168.1.1
```

### Argumentos de scripts
```bash
# Força bruta com lista de palavras
nmap --script ssh-brute --script-args userdb=users.txt,passdb=passwords.txt 192.168.1.1

# Força bruta na autenticação HTTP
nmap --script http-brute --script-args http-brute.path=/admin 192.168.1.1
```

---

<a id="-output-formats"></a>
## 📄 Formatos de saída

### Opções de saída

| Opção | Descrição |
|--------|-------------|
| `-oN <file>` | Saída normal |
| `-oX <file>` | Saída XML |
| `-oG <file>` | Saída pesquisável com grep |
| `-oA <basename>` | Todos os formatos |
| `-oS <file>` | Saída no estilo script kiddie |

### Guardar a saída
```bash
# Saída normal
nmap -oN scan.txt 192.168.1.1

# Saída XML (para ferramentas)
nmap -oX scan.xml 192.168.1.1

# Saída pesquisável com grep
nmap -oG scan.gnmap 192.168.1.1

# Todos os formatos em simultâneo
nmap -oA scan 192.168.1.1
# Cria: scan.nmap, scan.xml, scan.gnmap
```

### Nível de detalhe e depuração

| Opção | Descrição |
|--------|-------------|
| `-v` | Aumentar o nível de detalhe |
| `-vv` | Mais detalhado |
| `-d` | Modo de depuração |
| `-dd` | Mais informação de depuração |
| `--reason` | Mostrar o motivo do estado da porta |
| `--open` | Mostrar apenas portas abertas |
| `--packet-trace` | Mostrar todos os pacotes |

```bash
# Saída detalhada
nmap -v 192.168.1.1

# Muito detalhado
nmap -vv 192.168.1.1

# Mostrar apenas portas abertas
nmap --open 192.168.1.1

# Mostrar o motivo
nmap --reason 192.168.1.1
```

---

<a id="-firewallids-evasion"></a>
## 🛡️ Evasão de firewall/IDS

### Técnicas de evasão

| Opção | Descrição |
|--------|-------------|
| `-f` | Fragmentar pacotes |
| `--mtu <size>` | Tamanho de MTU personalizado |
| `-D <decoys>` | Utilizar sistemas chamariz |
| `-S <IP>` | Falsificar o IP de origem |
| `--source-port <port>` | Definir uma porta de origem falsificada |
| `--data-length <n>` | Acrescentar dados aleatórios |
| `--randomize-hosts` | Ordem aleatória dos sistemas |
| `--spoof-mac <mac>` | Falsificar o endereço MAC |
| `--badsum` | Enviar somas de controlo inválidas |

### Fragmentação de pacotes
```bash
# Fragmentar pacotes
nmap -f 192.168.1.1

# MTU personalizada
nmap --mtu 16 192.168.1.1
```

### Sistemas chamariz
```bash
# Utilizar sistemas chamariz
nmap -D decoy1,decoy2,ME 192.168.1.1

# Sistemas chamariz aleatórios
nmap -D RND:5 192.168.1.1
```

### Falsificação de IP/porta de origem
```bash
# Falsificar o IP de origem
nmap -S 192.168.1.100 -e eth0 192.168.1.1

# Definir uma porta de origem habitualmente permitida
nmap --source-port 53 192.168.1.1
nmap --source-port 80 192.168.1.1
```

### Falsificação de MAC
```bash
# MAC aleatório
nmap --spoof-mac 0 192.168.1.1

# Fabricante específico
nmap --spoof-mac Apple 192.168.1.1

# MAC específico
nmap --spoof-mac 00:11:22:33:44:55 192.168.1.1
```

### Preenchimento com dados adicionais
```bash
# Acrescentar dados aleatórios aos pacotes
nmap --data-length 25 192.168.1.1
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: descoberta rápida de sistemas na rede
```bash
# Encontrar sistemas ativos
nmap -sn 192.168.1.0/24
```

### Exemplo 2: análise básica de portas
```bash
# Analisar com deteção de versões
nmap -sV 192.168.1.1
```

### Exemplo 3: análise abrangente
```bash
# Análise completa com deteção do sistema operativo e scripts
sudo nmap -sS -sV -O -sC 192.168.1.1
```

### Exemplo 4: análise completa agressiva
```bash
# Todas as portas, de forma agressiva
sudo nmap -A -T4 -p- 192.168.1.1
```

### Exemplo 5: análise de vulnerabilidades
```bash
# Procurar vulnerabilidades
nmap --script vuln 192.168.1.1
```

### Exemplo 6: análise discreta
```bash
# Análise discreta
sudo nmap -sS -T2 -f --data-length 25 192.168.1.1
```

### Exemplo 7: análise de servidor Web
```bash
# Análise Web abrangente
nmap -sV -p 80,443,8080,8443 --script "http-*" 192.168.1.1
```

### Exemplo 8: enumeração SMB
```bash
# Enumeração SMB completa
nmap -p 445 --script smb-enum-shares,smb-enum-users,smb-vuln* 192.168.1.1
```

### Exemplo 9: análise de toda a sub-rede
```bash
# Analisar toda a sub-rede
nmap -sS -sV -O -T4 --open 192.168.1.0/24 -oA subnet_scan
```

### Exemplo 10: serviços UDP
```bash
# Serviços UDP comuns
sudo nmap -sU -sV -p 53,67,68,69,123,161,500 192.168.1.1
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Comando | Descrição |
|---------|-------------|
| `nmap <target>` | Análise básica |
| `nmap -sn <target>` | Apenas descoberta de sistemas por ping |
| `nmap -Pn <target>` | Saltar a descoberta de sistemas |
| `nmap -sS <target>` | Análise SYN discreta |
| `nmap -sV <target>` | Deteção de versões |
| `nmap -O <target>` | Deteção do sistema operativo |
| `nmap -A <target>` | Análise agressiva |
| `nmap -sC <target>` | Scripts predefinidos |
| `nmap -p- <target>` | Todas as portas |
| `nmap --script vuln <target>` | Análise de vulnerabilidades |

### Rapidez e discrição

| Necessidade | Opções |
|------|---------|
| **Rápido** | `-T4 --min-rate 1000` |
| **Discreto** | `-T1 -f --data-length 25` |
| **Equilibrado** | `-T3` (predefinido) |

### Análises comuns

```bash
# Análise rápida
nmap -F 192.168.1.1

# Análise TCP completa
nmap -sT -p- 192.168.1.1

# Análise de serviços
nmap -sV 192.168.1.1

# Análise de vulnerabilidades
nmap --script vuln 192.168.1.1

# Tudo
sudo nmap -A -T4 -p- 192.168.1.1
```

---

<a id="-tips--best-practices"></a>
## 💡 Conselhos e boas práticas

### Conselhos de desempenho

1. **Utilizar análise SYN (predefinida)**
   ```bash
   # Requer root, mas é a mais rápida
   sudo nmap -sS 192.168.1.1
   ```

2. **Limitar inicialmente o intervalo de portas**
   ```bash
   # Começar pelas portas mais frequentes
   nmap --top-ports 100 192.168.1.1
   ```

3. **Aumentar a velocidade em redes locais**
   ```bash
   nmap -T5 --min-rate 5000 192.168.1.0/24
   ```

### Conselhos para melhorar a precisão

1. **Vários tipos de análise**
   ```bash
   # TCP e UDP
   sudo nmap -sS -sU -sV 192.168.1.1
   ```

2. **Aumentar as repetições em redes pouco fiáveis**
   ```bash
   nmap --max-retries 3 192.168.1.1
   ```

### Considerações legais

> ⚠️ **IMPORTANTE:** Analisa apenas redes que te pertençam ou para as quais tenhas autorização.

- ✅ As tuas próprias redes
- ✅ Ambientes de laboratório
- ✅ Testes de intrusão autorizados
- ❌ Sistemas aleatórios na Internet
- ❌ Redes sem autorização

---

## ⚠️ Aviso legal

> **AVISO:** O Nmap é uma ferramenta poderosa que deve ser utilizada apenas para **análises autorizadas**.
> 
> - ✅ Analisar redes que te pertençam
> - ✅ Analisar com autorização escrita explícita
> - ✅ Utilizar em testes de intrusão autorizados
> - ❌ Nunca analisar redes sem autorização
> - ❌ As análises não autorizadas podem ser ilegais
> 
> **A análise de redes sem autorização é ilegal em muitas jurisdições.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Sítio oficial do Nmap](https://nmap.org/)
- [Guia de referência do Nmap](https://nmap.org/book/man.html)
- [Documentação dos scripts NSE](https://nmap.org/nsedoc/)

### Recursos de aprendizagem
- [Livro Nmap Network Scanning](https://nmap.org/book/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Guias de referência relacionados
- [Metasploit Framework](../../Metasploit/translations/README.pt.md)
- [SQLMap](../../SQLMap/translations/README.pt.md)
- [Burp Suite](../../Burp-Suite/translations/README.pt.md)

---

<p align="center">
  <b>🔍 Domina o reconhecimento de redes!</b><br>
  <i>Conhece a tua rede antes dos atacantes</i>
</p>
