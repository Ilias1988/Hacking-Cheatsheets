# 🕷️ Nikto — fiche pratique complète

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   _ _ _    _        
   | \ | (_) | _| |_ ___  
   |  \| | | |/ / __/ _ \ 
   | |\  | |   <| || (_) |
   |_| \_|_|_|\_\\__\___/ 
                          
   Web Server Scanner - Since 2001
```

<p align="center">
  <img src="https://img.shields.io/badge/Nikto-Web_Scanner-blue?style=for-the-badge" alt="Nikto">
  <img src="https://img.shields.io/badge/Vulnerability-Scanner-red?style=for-the-badge" alt="Vulnerability">
  <img src="https://img.shields.io/badge/CGI-Testing-green?style=for-the-badge" alt="CGI">
  <img src="https://img.shields.io/badge/SSL-Analysis-purple?style=for-the-badge" alt="SSL">
</p>

<p align="center">
  <b>🌐 Scanner libre de serveurs web pour les tests de sécurité</b>
</p>

---

> **Repères de version :** les statistiques, plugins, formats et exemples de mise à jour reflètent le document source. Les archives `2.1.5` sont historiques ; consultez la [procédure officielle de mise à jour](https://github.com/sullo/nikto/wiki/Updating), qui indique le changement de méthode depuis 2.1.6. Les commandes sont conservées sans exécution.
>
> **Corrections des exemples :** mettre `admin:password` entre guillemets ne l’encode pas en Base64. `-Save` enregistre des réponses positives dans un répertoire ; ce n’est pas un point de reprise. `-Resume`, `-cookies` et le format `nbe` ne figurent pas dans la [liste officielle des options](https://github.com/sullo/nikto/wiki/Annotated-Option-List) consultée. Les lignes sources restent présentes pour fidélité ; vérifiez leur compatibilité avec votre version. Les cookies statiques sont décrits via `STATIC-COOKIE` dans la [configuration officielle](https://github.com/sullo/nikto/wiki/Config-Variables).
>
> **Interprétation :** les options d’évasion ne garantissent pas la discrétion. La catégorie de sélection `6` vise des problèmes associés au déni de service ; la [documentation du réglage des tests](https://github.com/sullo/nikto/wiki/Scan-Tuning) précise que Nikto ne tente pas intentionnellement d’attaques DoS. Le réglage `x6` n’est donc pas une garantie générale d’absence d’effet sur un service. Le texte du dessin ASCII est conservé dans sa forme source.

## 📋 Table des matières

- [Introduction](#-introduction)
- [Installation](#-installation)
- [Syntaxe de base](#-syntaxe-de-base)
- [Définition des cibles](#-définition-des-cibles)
- [Options de ports et de SSL](#-options-de-ports-et-de-ssl)
- [Sélection des tests](#-sélection-des-tests)
- [Authentification](#-authentification)
- [Proxy et évasion](#-proxy-et-évasion)
- [Sortie et rapports](#-sortie-et-rapports)
- [Mise à jour des bases](#-mise-à-jour-des-bases)
- [Exemples pratiques](#-exemples-pratiques)
- [Référence rapide](#-référence-rapide)
- [Conseils et bonnes pratiques](#-conseils-et-bonnes-pratiques)
- [Ressources](#-ressources)

---

## 🎯 Introduction

**Nikto** est un scanner libre de serveurs web qui réalise de nombreux tests, notamment dans les catégories suivantes :

### Ce que vérifie Nikto

| Catégorie | Description |
| ---------- | ------------- |
| **Fichiers dangereux** | Fichiers potentiellement dangereux, notamment les sauvegardes |
| **Versions obsolètes** | Logiciels serveur obsolètes |
| **Vulnérabilités propres à une version** | Vulnérabilités liées à certaines versions |
| **Configuration du serveur** | Erreurs de configuration |
| **Scripts CGI** | Scripts CGI potentiellement dangereux |
| **SSL/TLS** | Problèmes de certificats SSL |
| **En-têtes** | En-têtes HTTP absents ou peu sûrs |
| **Méthodes** | Méthodes HTTP dangereuses |

### Statistiques des bases

```
✅ 6,700+ fichiers/CGI potentiellement dangereux
✅ 1,250+ versions de serveur obsolètes
✅ 270+ problèmes propres à une version
✅ 1,000+ problèmes de configuration du serveur
```

### Fonctionnalités

| Fonctionnalité | Description |
| --------- | ------------- |
| **Couverture étendue** | Recherche de milliers de vulnérabilités |
| **Architecture à plugins** | Architecture extensible |
| **Plusieurs formats** | Sorties HTML, XML, CSV et JSON |
| **Prise en charge de SSL** | Tests HTTPS complets |
| **Évasion IDS** | Techniques d’évasion intégrées |
| **Authentification** | Prise en charge de Basic, Digest et NTLM |

---

## 📥 Installation

### Kali Linux (préinstallé)
```bash
# Mettre Nikto à jour
sudo apt update && sudo apt install nikto

# Mettre à jour les bases
nikto -update
```

### Ubuntu/Debian
```bash
sudo apt install nikto
```

### Depuis GitHub
```bash
git clone https://github.com/sullo/nikto
cd nikto/program
perl nikto.pl -h
```

### macOS (Homebrew)
```bash
brew install nikto
```

### Vérifier l’installation
```bash
nikto -Version
nikto -h          # Aide
```

---

<a id="-syntaxe-de-base"></a>
## ⌨️ Syntaxe de base

### Syntaxe générale
```bash
nikto -h <host> [options]
```

### Prise en main rapide
```bash
# Analyse élémentaire
nikto -h http://target.com

# Analyser en précisant le port
nikto -h target.com -p 80

# Analyser en HTTPS
nikto -h https://target.com

# Analyser une adresse IP
nikto -h 192.168.1.1
```

---

## 🎯 Définition des cibles

### Cible unique
```bash
# Par nom d’hôte
nikto -h target.com

# Par adresse IP
nikto -h 192.168.1.1

# Avec le protocole
nikto -h http://target.com
nikto -h https://target.com
```

### Plusieurs cibles
```bash
# Depuis un fichier
nikto -h targets.txt

# targets.txt:
# http://target1.com
# http://target2.com:8080
# https://target3.com
```

### Cible avec un chemin
```bash
# Analyser un chemin précis
nikto -h http://target.com/app

# Analyser un répertoire précis
nikto -h http://target.com/admin/
```

---

## 🔌 Options de ports et de SSL

### Options de ports
```bash
# Un seul port
nikto -h target.com -p 80

# Plusieurs ports
nikto -h target.com -p 80,443,8080

# Plage de ports
nikto -h target.com -p 80-90

# Ports web courants
nikto -h target.com -p 80,443,8000,8080,8443
```

### Options SSL/TLS

| Option | Description |
| -------- | ------------- |
| `-ssl` | Forcer le mode SSL |
| `-nossl` | Désactiver SSL |
| `-maxtime` | Durée maximale d’analyse |

```bash
# Forcer SSL
nikto -h target.com -ssl

# Désactiver SSL (HTTP uniquement)
nikto -h target.com -nossl

# SSL sur un port non standard
nikto -h target.com -p 8443 -ssl
```

### Hôtes virtuels
```bash
# Préciser l’hôte virtuel
nikto -h 192.168.1.1 -vhost target.com

# Utile dans les cas suivants :
# - Cible derrière un répartiteur de charge
# - Plusieurs sites sur la même adresse IP
```

---

<a id="-sélection-des-tests"></a>
## 🎚️ Sélection des tests

### Options de sélection des tests (-Tuning)

| Code | Description |
| ------ | ------------- |
| `0` | Téléversement de fichiers |
| `1` | Fichier intéressant / observé dans les journaux |
| `2` | Erreur de configuration / fichier par défaut |
| `3` | Divulgation d’informations |
| `4` | Injection (XSS/script/HTML) |
| `5` | Récupération distante de fichiers dans la racine web |
| `6` | Déni de service |
| `7` | Récupération distante de fichiers sur l’ensemble du serveur |
| `8` | Exécution de commandes / shell distant |
| `9` | Injection SQL |
| `a` | Contournement de l’authentification |
| `b` | Identification des logiciels |
| `c` | Inclusion de code source distant |
| `d` | Service web |
| `e` | Console d’administration |
| `x` | Inversion de la sélection des tests |

### Utiliser la sélection des tests
```bash
# Tester uniquement les injections SQL
nikto -h target.com -Tuning 9

# Tester les XSS et les injections SQL
nikto -h target.com -Tuning 49

# Tester plusieurs catégories
nikto -h target.com -Tuning 1234

# Exclure certaines catégories (préfixe x)
nikto -h target.com -Tuning x6    # Exclure les tests de déni de service

# Tout sauf les tests de déni de service
nikto -h target.com -Tuning x6
```

### Plugins
```bash
# Lister les plugins disponibles
nikto -list-plugins

# Exécuter un plugin précis
nikto -h target.com -Plugins apacheusers

# Exécuter plusieurs plugins
nikto -h target.com -Plugins "apacheusers;robots"

# Exclure des plugins
nikto -h target.com -Plugins "@@ALL;-robots"
```

### Plugins courants

| Plugin | Description |
| -------- | ------------- |
| `apacheusers` | Énumération des utilisateurs Apache |
| `cgi` | Test des scripts CGI |
| `robots` | Analyse de robots.txt |
| `headers` | Analyse des en-têtes HTTP |
| `httpoptions` | Test des méthodes HTTP |
| `outdated` | Recherche de logiciels obsolètes |
| `ssl` | Analyse SSL/TLS |
| `dictionary` | Attaque par dictionnaire |

---

## 🔐 Authentification

### Authentification Basic
```bash
# Nom d’utilisateur et mot de passe
nikto -h target.com -id admin:password

# Format Base64 (libellé source ; voir la précision)
nikto -h target.com -id "admin:password"
```

### Authentification Digest
```bash
# Authentification Digest (détectée automatiquement)
nikto -h target.com -id admin:password
```

### Authentification NTLM
```bash
# Authentification NTLM
nikto -h target.com -id admin:password:domain
```

### Authentification par cookie
```bash
# Envoyer des cookies
nikto -h target.com -cookies "session=abc123; token=xyz789"

# Depuis un fichier
nikto -h target.com -cookies cookies.txt
```

---

<a id="-proxy-et-évasion"></a>
## 🛡️ Proxy et évasion

### Prise en charge des proxys
```bash
# Proxy HTTP
nikto -h target.com -useproxy http://127.0.0.1:8080

# Proxy authentifié
nikto -h target.com -useproxy http://user:pass@proxy:8080

# Via Burp Suite
nikto -h target.com -useproxy http://127.0.0.1:8080
```

### Évasion des systèmes de détection d’intrusion (-evasion)

| Code | Technique |
| ------ | ----------- |
| `1` | Encodage aléatoire de l’URI (non UTF-8) |
| `2` | Autoréférence du répertoire (/./) |
| `3` | Fin prématurée de l’URL |
| `4` | Ajout d’une longue chaîne aléatoire au début |
| `5` | Faux paramètre |
| `6` | Tabulation comme séparateur dans la requête |
| `7` | Modification de la casse de l’URL |
| `8` | Utilisation du séparateur de répertoires Windows (\) |
| `A` | Utilisation d’un retour chariot (0x0d) |
| `B` | Utilisation de la valeur binaire 0x0b |

```bash
# Une seule technique d’évasion
nikto -h target.com -evasion 1

# Plusieurs techniques d’évasion
nikto -h target.com -evasion 1234

# Toutes les techniques d’évasion de l’exemple
nikto -h target.com -evasion 12345678AB
```

### User-Agent
```bash
# User-Agent personnalisé
nikto -h target.com -useragent "Mozilla/5.0 (Windows NT 10.0)"

# Googlebot
nikto -h target.com -useragent "Googlebot/2.1"
```

### Temporisation des requêtes
```bash
# Pause entre les requêtes (secondes)
nikto -h target.com -Pause 2

# Durée maximale d’analyse (secondes)
nikto -h target.com -maxtime 3600

# Délai d’attente maximal par requête
nikto -h target.com -timeout 10
```

---

## 📄 Sortie et rapports

### Formats de sortie

| Option | Format |
| -------- | -------- |
| `-o file` | Écrire dans un fichier |
| `-Format htm` | Format HTML |
| `-Format xml` | Format XML |
| `-Format csv` | Format CSV |
| `-Format txt` | Format texte |
| `-Format json` | Format JSON |
| `-Format nbe` | Format NBE |
| `-Format sql` | Format SQL |

### Enregistrer les résultats
```bash
# Sortie texte
nikto -h target.com -o report.txt

# Rapport HTML
nikto -h target.com -o report.html -Format htm

# Rapport XML
nikto -h target.com -o report.xml -Format xml

# Rapport JSON
nikto -h target.com -o report.json -Format json

# Rapport CSV
nikto -h target.com -o report.csv -Format csv
```

### Options d’affichage détaillé
```bash
# Options d’affichage
nikto -h target.com -Display V    # Affichage détaillé
nikto -h target.com -Display D    # Débogage
nikto -h target.com -Display E    # Erreurs HTTP
nikto -h target.com -Display P    # Progression
nikto -h target.com -Display S    # Masquer les adresses IP dans la sortie
nikto -h target.com -Display 1    # Afficher les redirections
nikto -h target.com -Display 2    # Afficher les cookies
nikto -h target.com -Display 3    # Afficher les réponses 200/OK
nikto -h target.com -Display 4    # Afficher les URL nécessitant une authentification

# Plusieurs options d’affichage
nikto -h target.com -Display VEP
```

### Enregistrer la progression
```bash
# Enregistrer l’état de l’analyse (interprétation source ; voir la précision)
nikto -h target.com -Save scan_state.txt

# Reprendre l’analyse (exemple source ; voir la précision)
nikto -Resume scan_state.txt
```

---

## 🔄 Mise à jour des bases

### Mettre à jour les bases Nikto
```bash
# Mettre à jour toutes les bases
nikto -update

# Mise à jour manuelle (si -update échoue)
cd /var/lib/nikto
wget https://cirt.net/nikto/UPDATES/2.1.5/db_outdated.csv
wget https://cirt.net/nikto/UPDATES/2.1.5/db_tests.csv
```

### Emplacement des bases
```bash
# Emplacements par défaut
/usr/share/nikto/databases/
/var/lib/nikto/

# Fichiers des bases
db_tests         # Base principale des tests
db_outdated      # Versions logicielles obsolètes
db_server_msgs   # Messages du serveur
db_variables     # Variables
db_404_strings   # Chaînes des pages 404
```

---

## 🎬 Exemples pratiques

### Exemple 1 : analyse élémentaire d’un serveur web
```bash
nikto -h http://target.com
```

### Exemple 2 : analyse HTTPS complète
```bash
nikto -h https://target.com -ssl -p 443 -o report.html -Format htm
```

### Exemple 3 : analyse de plusieurs ports
```bash
nikto -h target.com -p 80,443,8080,8443 -o results.txt
```

### Exemple 4 : analyse authentifiée
```bash
nikto -h http://target.com/admin \
    -id admin:password \
    -o auth_scan.html \
    -Format htm
```

### Exemple 5 : analyse dite discrète (évasion IDS)
```bash
nikto -h target.com \
    -evasion 1234 \
    -Pause 2 \
    -useragent "Mozilla/5.0 (Windows NT 10.0)" \
    -o stealth_scan.txt
```

### Exemple 6 : passage par le proxy Burp
```bash
nikto -h https://target.com \
    -useproxy http://127.0.0.1:8080 \
    -o proxy_scan.html \
    -Format htm
```

### Exemple 7 : recherche ciblée d’injections SQL
```bash
nikto -h target.com \
    -Tuning 9 \
    -o sqli_scan.txt
```

### Exemple 8 : audit complet
```bash
nikto -h https://target.com \
    -ssl \
    -p 443 \
    -Tuning x6 \
    -Display VEP \
    -o full_audit.html \
    -Format htm
```

### Exemple 9 : test d’un hôte virtuel
```bash
nikto -h 192.168.1.10 \
    -vhost app.target.com \
    -p 80,443 \
    -o vhost_scan.txt
```

### Exemple 10 : analyse à partir d’un fichier de cibles
```bash
nikto -h targets.txt \
    -o batch_scan.html \
    -Format htm
```

---

## 📊 Référence rapide

### Commandes essentielles

| Tâche | Command |
| ------ | --------- |
| Analyse élémentaire | `nikto -h target.com` |
| Analyse HTTPS | `nikto -h target.com -ssl` |
| Plusieurs ports | `nikto -h target.com -p 80,443,8080` |
| Avec authentification | `nikto -h target.com -id user:pass` |
| Rapport HTML | `nikto -h target.com -o report.html -Format htm` |
| Évasion | `nikto -h target.com -evasion 123` |
| Via un proxy | `nikto -h target.com -useproxy http://127.0.0.1:8080` |
| Mettre à jour les bases | `nikto -update` |

### Options courantes

| Option | Description |
| -------- | ------------- |
| `-h` | Hôte cible |
| `-p` | Port(s) à analyser |
| `-ssl` | Forcer le mode SSL |
| `-id` | Identifiants d’authentification |
| `-o` | Fichier de sortie |
| `-Format` | Format de sortie |
| `-Tuning` | Sélection des tests |
| `-evasion` | Évasion IDS |
| `-useproxy` | Serveur proxy |
| `-vhost` | Hôte virtuel |
| `-Pause` | Délai entre les tests |
| `-timeout` | Délai maximal d’attente des requêtes |

### Référence rapide de la sélection des tests

| Code | Catégorie |
| ------ | ---------- |
| 1-4 | Informations / erreurs de configuration |
| 4-5 | Injection / récupération |
| 6 | Déni de service (à éviter en production) |
| 7-8 | Accès distant |
| 9 | Injection SQL |
| a | Contournement de l’authentification |
| b | Identification des logiciels |
| x | Exclure |

---

## 💡 Conseils et bonnes pratiques

### Conseils de performance

1. **Commencer par une analyse élémentaire**
   ```bash
   nikto -h target.com
   ```

2. **Exclure les tests de déni de service**
   ```bash
   nikto -h target.com -Tuning x6
   ```

3. **Choisir des délais d’attente adaptés**
   ```bash
   nikto -h target.com -timeout 15 -Pause 1
   ```

### Conseils pour la fiabilité des résultats

1. **Mettre à jour les bases régulièrement**
   ```bash
   nikto -update
   ```

2. **Utiliser le mode détaillé**
   ```bash
   nikto -h target.com -Display VE
   ```

3. **Combiner avec d’autres outils**
   ```bash
   # Nmap d’abord, puis Nikto
   nmap -sV -p 80,443 target.com
   nikto -h target.com -p 80,443
   ```

### Problèmes courants

| Problème | Solution |
| ------- | ---------- |
| Analyse lente | Réduire `-Pause`, augmenter `-timeout` |
| Erreurs SSL | Préciser explicitement l’option `-ssl` |
| Problèmes d’authentification | Vérifier le format de `-id` |
| Résultats manquants | Mettre à jour les bases et vérifier les plugins |

---

## ⚠️ Avertissement juridique

> **AVERTISSEMENT :** Nikto est un scanner de serveurs web à utiliser uniquement dans le cadre de **tests autorisés**.
> 
> - ✅ Analyser les serveurs dont vous êtes propriétaire
> - ✅ Analyser avec une autorisation écrite explicite
> - ✅ Utiliser dans des tests d’intrusion autorisés
> - ❌ Ne jamais analyser sans autorisation
> - ❌ Les analyses non autorisées sont illégales
> 
> **Nikto peut générer un trafic important et déclencher des alertes de sécurité.**

---

## 📚 Ressources

### Ressources officielles
- [Nikto GitHub](https://github.com/sullo/nikto)
- [Nikto Documentation](https://cirt.net/Nikto2)
- [CIRT.net](https://cirt.net/)

### Ressources d’apprentissage
- [OWASP Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Fiches connexes
- [Nmap](../../Nmap/translations/README.fr.md)
- [Gobuster](../../Gobuster/translations/README.fr.md)
- [Burp Suite](../../Burp-Suite/README.md)

---

<p align="center">
  <b>🕷️ Maîtrisez l’analyse des serveurs web !</b><br>
  <i>Repérez les vulnérabilités avant les attaquants</i>
</p>
