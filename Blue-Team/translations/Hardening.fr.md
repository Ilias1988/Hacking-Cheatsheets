# 🔒 Aide-mémoire : durcissement des systèmes

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardening.tr.md) • [🇬🇷 Ελληνικά](Hardening.el.md)

```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

> **Précisions de traduction :** les commandes sont conservées telles que dans l’original. Le TPM est recommandé pour Credential Guard, et non une condition absolue ; consulter les [prérequis Microsoft](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/). Dans Windows PowerShell, `sc` peut désigner l’alias de `Set-Content` : les exemples de gestion des services visent l’utilitaire `sc.exe`. Les commandes `wmic` et les cmdlets WMI concernent des environnements anciens ; vérifier leur disponibilité. L’affectation de `LanguageMode` ne constitue pas, à elle seule, une politique de contrôle des applications. Les directives SSH et les permissions doivent être adaptées au système. `kernel.randomize_va_space` règle l’ASLR, comme précisé dans le commentaire traduit. Le lien vers `Network-Defense.md` manque déjà dans le dépôt source.

---

## 🖥️ Durcissement de Windows

### Sécurité des comptes
```powershell
# Désactiver le compte administrateur local
net user Administrator /active:no

# Renommer le compte administrateur
wmic useraccount where name='Administrator' rename 'NewAdminName'

# Définir une stratégie de mots de passe robustes (GPO ou secpol.msc)
# Longueur minimale : 14 caractères
# Complexité : activée
# Durée maximale : 60 jours

# Désactiver le compte invité
net user Guest /active:no

# Limiter l'utilisation du jeton administrateur (UAC)
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

### Désactiver les services inutiles
```powershell
# Désactiver le service Remote Registry
sc config RemoteRegistry start= disabled

# Désactiver Windows Remote Management si inutile
sc config WinRM start= disabled

# Désactiver SMBv1
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# Désactiver NetBIOS
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

### Configuration du pare-feu
```powershell
# Activer le pare-feu Windows
netsh advfirewall set allprofiles state on

# Bloquer les connexions entrantes par défaut
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# Activer la journalisation
netsh advfirewall set allprofiles logging filename %SystemRoot%\System32\LogFiles\Firewall\pfirewall.log
netsh advfirewall set allprofiles logging maxfilesize 4096
netsh advfirewall set allprofiles logging droppedconnections enable
```

### Sécurité de PowerShell
```powershell
# Activer le mode de langage contraint (Constrained Language Mode)
$ExecutionContext.SessionState.LanguageMode = "ConstrainedLanguage"

# Activer la journalisation des blocs de script
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# Activer la journalisation des modules
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

### Stratégie d'audit
```powershell
# Journaliser la ligne de commande lors de la création des processus
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Audit" /v ProcessCreationIncludeCmdLine_Enabled /t REG_DWORD /d 1 /f

# Configurer les stratégies d'audit
auditpol /set /category:"Logon/Logoff" /success:enable /failure:enable
auditpol /set /category:"Account Logon" /success:enable /failure:enable
auditpol /set /category:"Account Management" /success:enable /failure:enable
auditpol /set /category:"Object Access" /success:enable /failure:enable
auditpol /set /category:"Privilege Use" /success:enable /failure:enable
```

### Windows Defender
```powershell
# Activer la protection en temps réel
Set-MpPreference -DisableRealtimeMonitoring $false

# Activer la protection dans le cloud
Set-MpPreference -MAPSReporting Advanced
Set-MpPreference -SubmitSamplesConsent SendAllSamples

# Activer les règles de réduction de la surface d'attaque (ASR)
Set-MpPreference -AttackSurfaceReductionRules_Ids 56a863a9-875e-4185-98a7-b882c64b5ce5 -AttackSurfaceReductionRules_Actions Enabled
```

### Protection de LSASS
```powershell
# Activer Credential Guard (l'original indique un TPM requis ; voir la précision ci-dessous)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f

# Activer la protection PPL (Protected Process Light) de LSASS
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f
```

---

## 🐧 Durcissement de Linux

### Gestion des utilisateurs et des accès
```bash
# Désactiver la connexion du compte root
sudo passwd -l root

# Définir la complexité des mots de passe (/etc/security/pwquality.conf)
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# Définir la durée de validité des mots de passe (/etc/login.defs)
PASS_MAX_DAYS 90
PASS_MIN_DAYS 7
PASS_WARN_AGE 14

# Désactiver les comptes inutilisés
usermod -s /sbin/nologin username
```

### Durcissement de SSH (/etc/ssh/sshd_config)
```bash
# Désactiver la connexion du compte root
PermitRootLogin no

# Désactiver l'authentification par mot de passe (utiliser des clés)
PasswordAuthentication no
PubkeyAuthentication yes

# Changer le port par défaut
Port 2222

# Limiter les utilisateurs autorisés
AllowUsers admin operator

# Protocole 2 uniquement
Protocol 2

# Interdire les mots de passe vides
PermitEmptyPasswords no

# Paramètres de délai d'expiration
ClientAliveInterval 300
ClientAliveCountMax 2

# Redémarrer SSH
sudo systemctl restart sshd
```

### Pare-feu (iptables/nftables)
```bash
# Politique de refus par défaut
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# Autoriser l'interface de boucle locale
iptables -A INPUT -i lo -j ACCEPT

# Autoriser les connexions établies
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# Autoriser SSH sur le port personnalisé
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT

# Enregistrer les règles
iptables-save > /etc/iptables/rules.v4
```

### Permissions des fichiers
```bash
# Protéger les fichiers critiques
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# Rechercher les fichiers SUID/SGID
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null

# Retirer le bit SUID lorsqu'il est inutile
chmod u-s /usr/bin/unnecessary_binary
```

### Désactiver les services inutilisés
```bash
# Lister les services activés
systemctl list-unit-files --state=enabled

# Désactiver les services inutiles
systemctl disable bluetooth
systemctl disable cups
systemctl disable avahi-daemon
systemctl disable rpcbind

# Désactiver IPv6 si inutile
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
```

### Durcissement du noyau (/etc/sysctl.conf)
```bash
# Désactiver le transfert IP
net.ipv4.ip_forward = 0

# Désactiver le routage à la source
net.ipv4.conf.all.accept_source_route = 0
net.ipv4.conf.default.accept_source_route = 0

# Activer les SYN cookies
net.ipv4.tcp_syncookies = 1

# Désactiver les redirections ICMP
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0

# Activer la randomisation de l'espace d'adressage (ASLR ; le commentaire original indique exec shield)
kernel.randomize_va_space = 2

# Appliquer les modifications
sysctl -p
```

### Système d'audit (auditd)
```bash
# Installer auditd
apt install auditd

# Exemples de règles (/etc/audit/rules.d/audit.rules)
-w /etc/passwd -p wa -k passwd_changes
-w /etc/shadow -p wa -k shadow_changes
-w /etc/sudoers -p wa -k sudoers_changes
-a always,exit -F arch=b64 -S execve -k command_execution

# Redémarrer auditd
systemctl restart auditd
```

---

## 📊 Liste de contrôle rapide du durcissement

### Windows
```markdown
□ Désactiver les services inutiles (SMBv1, Remote Registry)
□ Activer le pare-feu Windows
□ Configurer une stratégie de mots de passe robustes
□ Activer la journalisation PowerShell
□ Configurer la stratégie d'audit
□ Activer Windows Defender
□ Activer la protection de LSASS
□ Installer les mises à jour de sécurité
□ Supprimer les logiciels inutilisés
```

### Linux
```markdown
□ Désactiver la connexion du compte root
□ Configurer le durcissement de SSH
□ Activer le pare-feu (iptables/ufw)
□ Définir les permissions appropriées sur les fichiers
□ Désactiver les services inutilisés
□ Configurer le durcissement du noyau
□ Activer auditd
□ Installer les mises à jour de sécurité
□ Configurer fail2ban
```

---

## 📚 Ressources

- [CIS Benchmarks](https://www.cisecurity.org/cis-benchmarks/)
- [Recommandations du NIST](https://www.nist.gov/cyberframework)
- [Configurations de sécurité de référence Microsoft](https://docs.microsoft.com/en-us/windows/security/threat-protection/windows-security-baselines)

---

## 🔗 Aide-mémoire associés

- [Réponse aux incidents](./Incident-Response.fr.md)
- Défense réseau — guide pas encore disponible

---

**Précédent:** ← Défense réseau — guide pas encore disponible

**Retour à la vue d'ensemble:** [🛡️ Blue Team](./README.fr.md)
