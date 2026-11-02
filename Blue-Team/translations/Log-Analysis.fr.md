# 📊 Aide-mémoire : analyse des journaux

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Log-Analysis.tr.md) • [🇬🇷 Ελληνικά](Log-Analysis.el.md)

```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

> **Précisions de traduction :** les chemins de journaux dépendent de la distribution et de sa configuration ; certains systèmes utilisent principalement journald. Les positions `Properties[...]` dépendent du schéma et de la version de l’événement : vérifier les champs XML avant de réutiliser les exemples. Les motifs et identifiants d’événements sont des pistes d’analyse à corréler, pas des preuves isolées de compromission. Les commentaires `#` des blocs `cmd` ne sont pas directement exécutables dans cet interpréteur.

---

## 🖥️ Journaux d'événements Windows

### Emplacement des journaux
```
# Journal Sécurité
C:\Windows\System32\winevt\Logs\Security.evtx

# Journal Système
C:\Windows\System32\winevt\Logs\System.evtx

# Journal Application
C:\Windows\System32\winevt\Logs\Application.evtx

# Journaux PowerShell
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon (si installé)
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

### Identifiants d'événements de sécurité critiques

#### Événements d'authentification
| ID d'événement | Description | Importance |
|----------|-------------|------------|
| **4624** | Ouverture de session réussie | Suivre les accès des utilisateurs |
| **4625** | Échec d'ouverture de session | Détection de force brute |
| **4634** | Fermeture de session | Suivi des sessions |
| **4647** | Fermeture de session à l'initiative de l'utilisateur | Suivi des sessions |
| **4648** | Ouverture de session avec des identifiants explicites | Déplacement latéral |
| **4672** | Attribution de privilèges spéciaux | Activité d'administration |
| **4768** | Demande de TGT Kerberos | Authentification AD |
| **4769** | Ticket de service Kerberos | Accès à un service |
| **4771** | Échec de préauthentification Kerberos | Pulvérisation de mots de passe (password spraying) |
| **4776** | Authentification NTLM | Validation des identifiants |

#### Types d'ouverture de session (4624/4625)
| Type | Description |
|------|-------------|
| 2 | Interactive (locale) |
| 3 | Réseau |
| 4 | Traitement par lots |
| 5 | Service |
| 7 | Déverrouillage |
| 8 | Réseau avec identifiants en clair |
| 9 | Nouveaux identifiants |
| 10 | Interactive distante (RDP) |
| 11 | Interactive avec identifiants en cache |

#### Gestion des comptes
| ID d'événement | Description |
|----------|-------------|
| **4720** | Création d'un compte utilisateur |
| **4722** | Activation d'un compte utilisateur |
| **4723** | Tentative de changement de mot de passe |
| **4724** | Réinitialisation du mot de passe |
| **4725** | Désactivation d'un compte utilisateur |
| **4726** | Suppression d'un compte utilisateur |
| **4728** | Ajout d'un membre à un groupe de sécurité |
| **4732** | Ajout d'un membre à un groupe local |
| **4756** | Ajout d'un membre à un groupe universel |

#### Événements de processus et de services
| ID d'événement | Description |
|----------|-------------|
| **4688** | Création d'un processus |
| **4689** | Arrêt d'un processus |
| **7045** | Installation d'un service |
| **7036** | Changement d'état d'un service |
| **4697** | Installation d'un service (journal Sécurité) |

#### Tâches planifiées
| ID d'événement | Description |
|----------|-------------|
| **4698** | Création d'une tâche planifiée |
| **4699** | Suppression d'une tâche planifiée |
| **4700** | Activation d'une tâche planifiée |
| **4702** | Modification d'une tâche planifiée |

### Commandes d'interrogation PowerShell
```powershell
# Récupérer les entrées du journal Sécurité
Get-WinEvent -LogName Security -MaxEvents 100

# Échecs d'ouverture de session (4625)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# Ouvertures de session réussies (4624)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# Créations de processus (4688)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# Installations de services (7045)
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# Rechercher sur une période donnée
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

### Commandes Wevtutil
```cmd
# Interroger le journal Sécurité
wevtutil qe Security /c:50 /f:text

# Interroger un identifiant d'événement précis
wevtutil qe Security /q:"*[System[(EventID=4625)]]" /c:100 /f:text

# Exporter le journal
wevtutil epl Security C:\evidence\security.evtx

# Effacer le journal : à utiliser avec prudence
wevtutil cl Security
```

---

## 🐧 Analyse des journaux Linux

### Emplacement des journaux
```bash
# Authentification
/var/log/auth.log        # Debian/Ubuntu
/var/log/secure          # RHEL/CentOS

# Système
/var/log/syslog          # Debian/Ubuntu
/var/log/messages        # RHEL/CentOS

# Noyau
/var/log/kern.log
/var/log/dmesg

# Cron
/var/log/cron

# Audit
/var/log/audit/audit.log

# Serveurs web
/var/log/apache2/        # Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # RHEL Apache

# SSH
/var/log/auth.log
```

### Commandes courantes d'analyse des journaux

#### Analyse de l'authentification
```bash
# Tentatives SSH échouées
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# Connexions SSH réussies
grep "Accepted" /var/log/auth.log

# Force brute SSH : compter par adresse IP
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# Élévation de privilèges utilisateur (sudo)
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log

# Tentatives avec su
grep "su:" /var/log/auth.log
```

#### Analyse du système
```bash
# Dates de démarrage et d'arrêt
last -x | grep shutdown
last -x | grep reboot

# Utilisateurs actuellement connectés
who
w

# Historique des connexions
last
lastlog

# Tentatives de connexion échouées
lastb
```

#### Analyse des processus et des services
```bash
# Exécutions des tâches cron
grep CRON /var/log/syslog

# Démarrages et arrêts des services
grep -E "Started|Stopped" /var/log/syslog
journalctl -u <service-name>

# Messages du noyau
dmesg | tail -100
grep -i error /var/log/kern.log
```

### Motifs Grep/Awk utiles
```bash
# Extraire les adresses IP des journaux
grep -oE '\b[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\b' /var/log/auth.log

# Compter les occurrences par adresse IP
cat /var/log/auth.log | grep "Failed" | awk '{print $11}' | sort | uniq -c | sort -rn | head -20

# Recherche selon l'heure
awk '/Jan 15 10:00/,/Jan 15 11:00/' /var/log/auth.log

# Rechercher dans plusieurs fichiers
grep -r "error" /var/log/

# Surveillance en temps réel
tail -f /var/log/auth.log | grep --line-buffered "Failed"
```

### Journalctl (Systemd)
```bash
# Tous les journaux
journalctl

# Mode de suivi continu
journalctl -f

# Depuis une date précise
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# Par unité ou service
journalctl -u sshd
journalctl -u nginx

# Par priorité
journalctl -p err       # Erreurs uniquement
journalctl -p warning   # Avertissements et niveaux plus graves

# Messages du noyau
journalctl -k

# Sortie JSON
journalctl -o json
```

---

## 🌐 Journaux des serveurs web

### Format des journaux Apache
```
# Format de journal combiné (Combined Log Format)
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."

# Champs :
# IP - ident - utilisateur - [horodatage] "méthode chemin protocole" statut taille "référent" "agent utilisateur"
```

### Format des journaux Nginx
```
# Format par défaut similaire à celui d'Apache
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."
```

### Analyse des journaux web
```bash
# URL les plus demandées
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Adresses IP les plus fréquentes
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Codes de statut HTTP
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# Erreurs 404
grep ' 404 ' /var/log/apache2/access.log

# Requêtes POST : attaques potentielles
grep '"POST' /var/log/apache2/access.log

# Tentatives d'injection SQL
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# Tentatives de traversée de répertoires
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log

# Requêtes par heure
awk '{print $4}' /var/log/apache2/access.log | cut -d: -f2 | sort | uniq -c
```

---

## 🛡️ Analyse des journaux Sysmon

### Principaux identifiants d'événements Sysmon
| ID d'événement | Description |
|----------|-------------|
| 1 | Création d'un processus |
| 2 | Modification de la date de création d'un fichier |
| 3 | Connexion réseau |
| 5 | Arrêt d'un processus |
| 6 | Chargement d'un pilote |
| 7 | Chargement d'une image (DLL) |
| 8 | CreateRemoteThread (création d’un thread distant) |
| 10 | ProcessAccess (accès à un processus) |
| 11 | FileCreate (création de fichier) |
| 12-14 | Événements du Registre |
| 15 | FileCreateStreamHash (création de flux et empreinte) |
| 17-18 | Événements de tubes nommés |
| 22 | Requête DNS |

### Requêtes PowerShell pour Sysmon
```powershell
# Création de processus (événement 1)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='CommandLine';E={$_.Properties[10].Value}}

# Connexions réseau (événement 3)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=3} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='DestIP';E={$_.Properties[14].Value}}

# Requêtes DNS (événement 22)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=22} |
    Select-Object TimeCreated, @{N='Query';E={$_.Properties[4].Value}}
```

---

## 📊 Référence rapide

### Motifs suspects à rechercher

| Catégorie | Motif |
|----------|---------|
| **Force brute** | Plusieurs événements 4625 depuis la même IP |
| **Attaque par password spraying** | Événements 4625 visant plusieurs utilisateurs depuis la même IP |
| **Déplacement latéral** | 4624 Type 3 + 4648 |
| **Persistance** | 4698 (nouvelle tâche planifiée) |
| **Élévation de privilèges (privesc)** | 4672 pour des comptes non administrateurs |
| **Extraction d'identifiants** | Accès à lsass.exe |

### Outils d'analyse des journaux
| Outil | Utilisation |
|------|-----|
| **grep/awk/sed** | Analyse des journaux Linux |
| **PowerShell** | Analyse des journaux Windows |
| **Splunk** | SIEM d'entreprise |
| **ELK Stack** | SIEM open source |
| **LogParser** | Requêtes SQL sur les journaux |
| **Chainsaw** | Analyse rapide des journaux Windows |

---

## 🔗 Aide-mémoire associés

- [Réponse aux incidents](./Incident-Response.fr.md)
- [Détection avec un SIEM](./SIEM-Detection.fr.md)
- [Règles Sigma](./Sigma-Rules.fr.md)

---

**Précédent:** [← Réponse aux incidents](./Incident-Response.fr.md)

**Suivant:** [Détection avec un SIEM →](./SIEM-Detection.fr.md)
