# 🚨 Aide-mémoire : réponse aux incidents

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Incident-Response.tr.md) • [🇬🇷 Ελληνικά](Incident-Response.el.md)

```
  ██╗███╗   ██╗ ██████╗██╗██████╗ ███████╗███╗   ██╗████████╗
  ██║████╗  ██║██╔════╝██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝
  ██║██╔██╗ ██║██║     ██║██║  ██║█████╗  ██╔██╗ ██║   ██║   
  ██║██║╚██╗██║██║     ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║   
  ██║██║ ╚████║╚██████╗██║██████╔╝███████╗██║ ╚████║   ██║   
  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   
        ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗███████╗███████╗
        ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔════╝██╔════╝
        ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║███████╗█████╗  
        ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║╚════██║██╔══╝  
        ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║███████║███████╗
        ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚══════╝
```

> **Précisions de traduction :** le schéma en six étapes est une présentation pédagogique de l’original. La [publication NIST SP 800-61 révision 3](https://csrc.nist.gov/pubs/sp/800/61/r3/final), publiée en avril 2025, remplace la révision 2 et rattache la réponse aux incidents au CSF 2.0. Les délais de réponse du tableau sont des exemples à adapter. La date `+01/01/2024` est conservée comme valeur d’exemple. `dd if=/dev/mem` n’est pas une méthode universelle d’acquisition complète de la RAM Linux ; les restrictions du noyau peuvent la rendre inutilisable. Les lignes commençant par `#` dans les blocs `cmd` sont des explications, pas des commandes valides pour cet interpréteur.

---

## 📊 Cycle de réponse aux incidents (NIST)

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ 1.PRÉPARATION│────▶│ 2.DÉTECTION  │────▶│3.CONFINEMENT │────▶│4.ÉRADICATION │
│              │     │ ET ANALYSE   │     │              │     │              │
└──────────────┘     └──────────────┘     └──────────────┘     └──────────────┘
       ▲                                                              │
       │         ┌──────────────┐     ┌──────────────┐               │
       └─────────│ 6.RETOUR     │◀────│ 5.REPRISE    │◀──────────────┘
                 │   EXPÉRIENCE │     │              │
                 └──────────────┘     └──────────────┘
```

---

## 1️⃣ Préparation

### Liste de contrôle de la documentation
```markdown
- [ ] Plan de réponse aux incidents documenté
- [ ] Liste des contacts internes et externes
- [ ] Plan de communication
- [ ] Inventaire des actifs
- [ ] Schémas réseau
- [ ] Configurations de référence
- [ ] Vérification des sauvegardes
```

### Modèle de contacts de l'équipe de réponse aux incidents
```
Responsable RI :          [Nom] - [Téléphone] - [Courriel]
Sécurité informatique :   [Nom] - [Téléphone] - [Courriel]
Service juridique :       [Nom] - [Téléphone] - [Courriel]
Communication :           [Nom] - [Téléphone] - [Courriel]
Ressources humaines :     [Nom] - [Téléphone] - [Courriel]
Investigation externe :   [Entreprise] - [Téléphone]
Forces de l’ordre :       [Service] - [Téléphone]
```

### Outils essentiels de réponse aux incidents
```bash
# Collecte de preuves numériques
- FTK Imager
- Volatility
- Velociraptor
- KAPE

# Analyse réseau
- Wireshark
- tcpdump
- Zeek

# Analyse des journaux
- Splunk/ELK
- grep/awk/sed
```

---

## 2️⃣ Détection et analyse

### Questions pour le triage initial
```markdown
1. De quel type d'incident s'agit-il ?
   - [ ] Infection par un logiciel malveillant
   - [ ] Accès non autorisé
   - [ ] Violation de données
   - [ ] Déni de service
   - [ ] Menace interne
   - [ ] Hameçonnage (phishing)

2. Quand l'incident a-t-il été détecté ?
3. Comment a-t-il été détecté ?
4. Quels systèmes sont touchés ?
5. Quelles données peuvent être concernées ?
6. L'incident est-il toujours en cours ?
```

### Triage rapide sous Windows
```cmd
# Sessions utilisateur en cours
query user
qwinsta

# Connexions actives
netstat -ano | findstr ESTABLISHED

# Processus en cours d'exécution
tasklist /v
wmic process get processid,parentprocessid,commandline

# Modifications récentes de fichiers
forfiles /P C:\ /S /D +01/01/2024

# Tâches planifiées
schtasks /query /fo LIST /v

# Services
sc query state=all
wmic service get name,startmode,state

# Clés Run du Registre
reg query "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"
reg query "HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"

# Historique PowerShell récent
type %APPDATA%\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt
```

### Triage rapide sous Linux
```bash
# Utilisateurs connectés
w
who
last -a

# Connexions actives
ss -tulnp
netstat -tulnp
lsof -i

# Processus en cours d'exécution
ps auxwww
pstree -p

# Modifications récentes de fichiers
find / -mtime -1 -type f 2>/dev/null

# Tâches cron
cat /etc/crontab
ls -la /etc/cron.*
for user in $(cut -f1 -d: /etc/passwd); do crontab -u $user -l 2>/dev/null; done

# Scripts de démarrage
ls -la /etc/init.d/
systemctl list-unit-files --type=service

# Historique
cat ~/.bash_history
cat /root/.bash_history
```

### Analyse réseau
```bash
# Capturer le trafic
tcpdump -i eth0 -w capture.pcap

# Filtrer un hôte précis
tcpdump -i eth0 host 192.168.1.100

# Trafic HTTP
tcpdump -i eth0 port 80 or port 443

# Requêtes DNS
tcpdump -i eth0 port 53
```

---

## 3️⃣ Confinement

### Confinement à court terme
```bash
# Isoler le réseau sous Windows
netsh advfirewall set allprofiles state on
netsh advfirewall firewall add rule name="Block All" dir=in action=block
netsh advfirewall firewall add rule name="Block All Out" dir=out action=block

# Isoler le réseau sous Linux
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

# Désactiver le compte compromis
net user compromised_user /active:no   # Windows
passwd -l compromised_user             # Linux

# Arrêter le processus malveillant
taskkill /PID <pid> /F               # Windows
kill -9 <pid>                        # Linux
```

### Confinement à long terme
```bash
# Segmenter le réseau
# Appliquer des règles de pare-feu pour isoler le VLAN concerné
# Mettre à jour les ACL des commutateurs et routeurs

# Bloquer les IP et domaines malveillants sur le pare-feu
# Ajouter à la liste de blocage du proxy
# Mettre à jour le puits DNS (sinkhole)

# Désactiver temporairement les services touchés
sc config <service> start= disabled   # Windows
systemctl disable <service>           # Linux
```

### Préservation des preuves
```bash
# Acquisition mémoire sous Windows : utiliser WinPMEM ou DumpIt
winpmem_mini_x64.exe memory.raw

# Acquisition mémoire sous Linux
dd if=/dev/mem of=/evidence/memory.dump bs=1M

# Image disque
dd if=/dev/sda of=/evidence/disk.img bs=4M status=progress

# Calculer une empreinte pour vérifier l'intégrité
sha256sum /evidence/disk.img > disk.img.sha256

# Collecter d'abord les données volatiles, dans l'ordre suivant :
# 1. Mémoire
# 2. État du réseau
# 3. Processus en cours d'exécution
# 4. Disque
```

---

## 4️⃣ Éradication

### Suppression des logiciels malveillants
```bash
# Identifier et supprimer les mécanismes de persistance

# Windows - Registre
reg delete "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v "MalwareName" /f

# Windows - Tâches planifiées
schtasks /delete /tn "MalwareTask" /f

# Windows - Services
sc delete "MalwareService"

# Linux - Cron
crontab -r
rm /etc/cron.d/malware

# Linux - Systemd
systemctl disable malware
rm /etc/systemd/system/malware.service
```

### Réinitialisation des mots de passe
```bash
# Imposer la réinitialisation des mots de passe de tous les comptes compromis
# Réinitialiser les mots de passe des comptes de service
# Renouveler les clés et jetons API
# Invalider les sessions

# Windows : imposer le changement du mot de passe
net user username /logonpasswordchg:yes

# Renouveler les tickets Kerberos en cas de compromission AD
# - Réinitialiser DEUX FOIS le mot de passe de krbtgt
# - Réinitialiser les mots de passe de tous les comptes administrateurs
```

### Correctifs et mises à jour
```bash
# Appliquer les correctifs de sécurité manquants
# Mettre à jour les signatures antivirus
# Corriger les erreurs de configuration ayant permis l'attaque
```

---

## 5️⃣ Rétablissement

### Restauration des systèmes
```bash
# Restaurer à partir d'une sauvegarde saine
# Reconstruire les systèmes compromis
# Vérifier l'intégrité avant la remise en ligne

# Restauration progressive :
1. Restaurer d’abord les systèmes critiques
2. Surveiller les signes de réinfection
3. Restaurer les autres systèmes
4. Reprendre le fonctionnement normal
```

### Surveillance après rétablissement
```bash
# Augmenter le niveau de détail des journaux
# Renforcer la surveillance
# Surveiller un éventuel retour de l'attaquant

# Principaux indicateurs à surveiller :
- Mêmes IOC (adresses IP, domaines et empreintes)
- Tactiques, techniques et procédures (TTP) similaires
- Tentatives d’accès aux mêmes comptes
- Communications vers l’infrastructure de commande et contrôle (C2)
```

---

## 6️⃣ Retour d'expérience

### Modèle de rapport après incident
```markdown
## Résumé de l'incident
- Identifiant de l'incident:
- Date de détection:
- Date de confinement:
- Date de résolution:
- Type d'incident:
- Gravité:

## Chronologie
| Date/heure | Événement |
|-----------|-------|
| YYYY-MM-DD HH:MM | Détection initiale |
| ... | ... |

## Évaluation des conséquences
- Systèmes touchés:
- Données compromises:
- Conséquences sur l'activité:
- Conséquences financières:

## Analyse de la cause racine
- Comment l'attaquant a-t-il obtenu un accès ?
- Quelles vulnérabilités ont été exploitées ?
- Quelles mesures de protection ont échoué ?

## Mesures prises
1. Détection
2. Confinement
3. Éradication
4. Rétablissement

## Recommandations
1. Améliorations immédiates
2. Améliorations de sécurité à long terme
3. Modifications des politiques

## Retour d'expérience
- Qu'est-ce qui a bien fonctionné ?
- Que pourrait-on améliorer ?
- Quels besoins de formation ont été identifiés ?
```

---

## 📊 Référence rapide

### Classification de la gravité

| Niveau | Description | Délai de réponse |
|-------|-------------|---------------|
| **Critique** | Compromission en cours, exfiltration de données | Immédiat |
| **Élevé** | Compromission confirmée | < 1 heure |
| **Modéré** | Compromission potentielle | < 4 heures |
| **Faible** | Activité suspecte | < 24 heures |

### Indicateurs de compromission courants à recueillir

| Type d'IOC | Exemples |
|----------|----------|
| **Empreinte (hash)** | MD5, SHA1, SHA256 |
| **IP** | Adresses IP source et destination |
| **Domaine** | Domaines de commande et contrôle (C2) |
| **URL** | URL malveillantes |
| **Email** | Expéditeur du hameçonnage |
| **Fichier** | Nom du fichier malveillant |
| **Registre** | Clés de persistance |

---

## 🔗 Aide-mémoire associés

- [Analyse des journaux](./Log-Analysis.fr.md)
- [Détection avec un SIEM](./SIEM-Detection.fr.md)
- [Volatility (Investigation numérique)](../../Volatility/README.md)

---

**Suivant:** [Analyse des journaux →](./Log-Analysis.fr.md)
