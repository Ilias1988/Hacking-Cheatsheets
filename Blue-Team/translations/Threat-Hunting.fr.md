# 🎯 Aide-mémoire : recherche proactive de menaces

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Threat-Hunting.tr.md) • [🇬🇷 Ελληνικά](Threat-Hunting.el.md)

```
████████╗██╗  ██╗██████╗ ███████╗ █████╗ ████████╗    ██╗  ██╗██╗   ██╗███╗   ██╗████████╗██╗███╗   ██╗ ██████╗ 
╚══██╔══╝██║  ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝    ██║  ██║██║   ██║████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝ 
   ██║   ███████║██████╔╝█████╗  ███████║   ██║       ███████║██║   ██║██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║  ███╗
   ██║   ██╔══██║██╔══██╗██╔══╝  ██╔══██║   ██║       ██╔══██║██║   ██║██║╚██╗██║   ██║   ██║██║╚██╗██║██║   ██║
   ██║   ██║  ██║██║  ██║███████╗██║  ██║   ██║       ██║  ██║╚██████╔╝██║ ╚████║   ██║   ██║██║ ╚████║╚██████╔╝
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

> **Précisions de traduction :** le filtre de services « non signés » de l’original teste seulement si le chemin contient `Windows` ; il ne vérifie aucune signature numérique. L’énumération de `/proc` proposée ne prouve pas l’existence de processus cachés. Les positions `Properties[...]` sont liées au schéma des événements : vérifier leur correspondance avec les champs XML. Un résultat de recherche est une piste à investiguer, et non une preuve suffisante de compromission.

---

## 🎯 Processus de recherche proactive de menaces

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ 1. HYPOTHÈSE │────▶│ 2. COLLECTER│────▶│ 3. ANALYSER │────▶│ 4. RÉPONDRE │
│   (Théorie)  │     │   (Données) │     │   (Traque)  │     │   (Action)  │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
                                                                    │
                          ┌─────────────┐                          │
                          │ 5. DOCUM.   │◀─────────────────────────┘
                          │   (Bilan)   │
                          └─────────────┘
```

---

## 📋 Exemples d'hypothèses

| Catégorie | Hypothèse |
|----------|------------|
| **Accès initial** | Les attaquants peuvent diffuser des logiciels malveillants par hameçonnage |
| **Persistance** | Les attaquants peuvent créer des tâches planifiées pour maintenir leur accès |
| **Identifiants** | Les attaquants peuvent extraire la mémoire LSASS pour récupérer des identifiants |
| **Déplacement latéral** | Les attaquants peuvent utiliser RDP pour se déplacer latéralement |
| **Exfiltration** | Les attaquants peuvent exfiltrer des données par tunnel DNS |

---

## 🔍 Techniques de recherche

### Rechercher la persistance

#### Clés Run du Registre Windows
```powershell
# Interroger toutes les clés Run
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce" -ErrorAction SilentlyContinue
```

#### Tâches planifiées
```powershell
# Lister toutes les tâches planifiées
Get-ScheduledTask | Where-Object {$_.State -ne "Disabled"} | 
    Select-Object TaskName, TaskPath, State | Format-Table

# Rechercher les tâches suspectes non Microsoft
Get-ScheduledTask | Where-Object {$_.Author -notlike "*Microsoft*"} | 
    Select-Object TaskName, Author, State
```

#### Services
```powershell
# Services non signés (le filtre porte en réalité sur le chemin ; voir la précision ci-dessous)
Get-WmiObject Win32_Service | Where-Object {$_.PathName -notlike "*Windows*"} | 
    Select-Object Name, PathName, StartMode, State
```

### Rechercher les déplacements latéraux

#### Connexions RDP
```powershell
# Sessions RDP (événement 4624, type 10)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} | 
    Where-Object {$_.Properties[8].Value -eq 10} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='Source';E={$_.Properties[18].Value}}
```

#### Connexions réseau
```powershell
# Connexions actuelles
Get-NetTCPConnection -State Established | 
    Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, OwningProcess | 
    Format-Table

# Avec les noms des processus
Get-NetTCPConnection | ForEach-Object {
    $proc = Get-Process -Id $_.OwningProcess -ErrorAction SilentlyContinue
    [PSCustomObject]@{
        LocalAddress = $_.LocalAddress
        RemoteAddress = $_.RemoteAddress
        RemotePort = $_.RemotePort
        Process = $proc.ProcessName
    }
} | Format-Table
```

### Rechercher les accès aux identifiants

#### Accès à LSASS
```powershell
# Événement Sysmon 10 : accès à LSASS
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=10} |
    Where-Object {$_.Properties[8].Value -like "*lsass.exe"} |
    Select-Object TimeCreated, @{N='Source';E={$_.Properties[4].Value}}
```

### Rechercher les traces d'exécution

#### PowerShell encodé
```powershell
# PowerShell avec encodage
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-PowerShell/Operational';ID=4104} |
    Where-Object {$_.Message -match "FromBase64" -or $_.Message -match "-enc"} |
    Select-Object TimeCreated, Message
```

#### Relations parent-enfant suspectes
```powershell
# Office lançant cmd ou PowerShell
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Where-Object {
        ($_.Properties[20].Value -match "WINWORD|EXCEL|OUTLOOK") -and
        ($_.Properties[4].Value -match "cmd|powershell")
    }
```

---

## 🐧 Recherche proactive de menaces sous Linux

### Persistance
```bash
# Tâches cron
cat /etc/crontab
for user in $(cut -f1 -d: /etc/passwd); do crontab -l -u $user 2>/dev/null; done

# Clés SSH autorisées
find / -name "authorized_keys" 2>/dev/null

# Scripts de démarrage
ls -la /etc/init.d/
systemctl list-unit-files --type=service | grep enabled

# Binaires SUID
find / -perm -4000 -type f 2>/dev/null
```

### Réseau
```bash
# Ports en écoute
ss -tulnp
netstat -tulnp

# Connexions établies
ss -tnp state established
lsof -i -P -n | grep ESTABLISHED

# Trafic DNS inhabituel
tcpdump -i any port 53 -w dns.pcap
```

### Processus
```bash
# Tous les processus avec leur ligne de commande complète
ps auxwww

# Arbre des processus
pstree -p

# Processus cachés
ps aux | awk '{print $2}' | xargs -I{} ls -la /proc/{}/exe 2>/dev/null

# Binaires récemment modifiés
find /usr/bin /usr/sbin -mtime -7 2>/dev/null
```

---

## 📊 Recherche d'indicateurs de compromission (IOC)

### Recherche par empreinte
```powershell
# Calculer les empreintes
Get-FileHash -Algorithm SHA256 C:\suspicious.exe

# Rechercher une empreinte malveillante connue
$badHash = "abc123..."
Get-ChildItem -Path C:\ -Recurse -File | Get-FileHash -Algorithm SHA256 |
    Where-Object {$_.Hash -eq $badHash}
```

### Recherche par adresse IP ou domaine
```powershell
# Cache DNS
Get-DnsClientCache | Where-Object {$_.Name -like "*suspicious*"}

# Connexion à une IP malveillante connue
Get-NetTCPConnection | Where-Object {$_.RemoteAddress -eq "1.2.3.4"}
```

---

## 🛠️ Outils

| Outil | Objectif |
|------|---------|
| **Velociraptor** | Recherche sur les postes et serveurs à grande échelle |
| **OSQuery** | Requêtes SQL sur les postes et serveurs |
| **KAPE** | Collecte pour le triage |
| **Chainsaw** | Analyse rapide des journaux Windows |
| **DeepBlueCLI** | Recherche de menaces avec PowerShell |

---

## 📋 Liste de contrôle rapide de recherche

```markdown
□ Vérifier les emplacements de lancement automatique : Registre, services et tâches
□ Examiner les créations récentes de processus (événement 4688, Sysmon 1)
□ Analyser les connexions réseau
□ Rechercher les commandes encodées
□ Rechercher les accès à LSASS
□ Rechercher les relations parent-enfant inhabituelles entre processus
□ Examiner les journaux PowerShell
□ Rechercher les indicateurs de déplacement latéral
```

---

## 🔗 Aide-mémoire associés

- [Analyse des journaux](./Log-Analysis.fr.md)
- [Détection avec un SIEM](./SIEM-Detection.fr.md)
- [Règles Sigma](./Sigma-Rules.fr.md)

---

**Précédent:** [← Détection avec un SIEM](./SIEM-Detection.fr.md)

**Suivant:** [Règles Sigma →](./Sigma-Rules.fr.md)
