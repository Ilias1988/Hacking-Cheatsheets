# Searchsploit Cheatsheet

[Searchsploit](https://www.exploit-db.com/searchsploit) is the official command-line utility for [Exploit-Database](https://www.exploit-db.com/). It allows security professionals, penetration testers, and vulnerability researchers to conduct offline searches across tens of thousands of published exploits, shellcodes, and whitepapers without sending network traffic to third-party search engines.

---

## Table of Contents

- [Overview & Architecture](#overview--architecture)
- [Installation & Local Database Updates](#installation--local-database-updates)
- [Basic & Advanced Exploit Searching](#basic--advanced-exploit-searching)
- [Examining & Copying Exploits (`-m` / `-p` / `-x`)](#examining--copying-exploits--m---p---x)
- [Filtering by Platform & Type](#filtering-by-platform--type)
- [Nmap XML / Grepable Scanning Integration](#nmap-xml--grepable-scanning-integration)
- [JSON Output & Script Automation](#json-output--script-automation)
- [Best Practices in Penetration Testing](#best-practices-in-penetration-testing)

---

## Overview & Architecture

Searchsploit queries a local git clone of Exploit-DB stored in `/usr/share/exploitdb/`.
- **Zero Network Fingerprint:** Searches run against local CSV and directory structures.
- **Offline Reliability:** Fully functional in air-gapped target environments and engagement networks.
- **Integrated Mirroring:** Copies exploit files directly to the current working directory for immediate review and modification.

---

## Installation & Local Database Updates

### Debian / Ubuntu / Kali Linux / Parrot OS
```bash
# Kali and Parrot include searchsploit by default
sudo apt-get update && sudo apt-get install -y exploitdb

# Update the local exploit database archive
searchsploit -u
```

### Git Repository Installation (Any Linux Distribution)
```bash
git clone https://gitlab.com/exploit-database/exploitdb.git /opt/exploitdb
sudo ln -sf /opt/exploitdb/searchsploit /usr/local/bin/searchsploit

# Check version and status
searchsploit -v
```

---

## Basic & Advanced Exploit Searching

### 1. Simple Keyword Search
Search for specific services, daemons, or software:
```bash
searchsploit apache 2.4.49
searchsploit openssh 7.2
searchsploit wordpress core 5.8
```

### 2. Exact Title Matching (`-t`)
Restrict search queries strictly to exploit titles to eliminate noisy description matches:
```bash
searchsploit -t "Privilege Escalation" linux kernel
searchsploit -t "Remote Code Execution" smb
```

### 3. Strict Case & Term Matching (`-e`)
Find exact version and pattern matches:
```bash
searchsploit -e "Apache Struts"
```

### 4. Search by CVE Identifier
Query exploits mapped to specific Common Vulnerabilities and Exposures numbers:
```bash
searchsploit --cve 2021-44228
searchsploit --cve 2017-0144
```

### 5. Exclude Unwanted Results (`--exclude`)
Filter out denial-of-service (DoS) or auxiliary scripts when seeking remote code execution (RCE):
```bash
searchsploit linux kernel 5.4 --exclude="Denial of Service"
```

---

## Examining & Copying Exploits (`-m` / `-p` / `-x`)

Never edit or execute exploits directly in the `/usr/share/exploitdb/` directory. Use Searchsploit's copy and inspect functions.

### 1. Copy (Mirror) Exploit to Current Directory (`-m`)
Copy an exploit PoC file (Python, C, Ruby, bash) directly to your local working directory:
```bash
# Mirror by Exploit-DB ID (EBD-ID)
searchsploit -m 50383

# Mirror by search path pattern
searchsploit -m windows/remote/48537.py
```

### 2. Inspect Exploit Metadata & Full Path (`-p`)
Display the absolute filesystem path and Exploit-DB URL for an EDB-ID:
```bash
searchsploit -p 50383
```
Output:
```text
  Exploit: Apache HTTP Server 2.4.49 - Path Traversal & Remote Code Execution (RCE)
      URL: https://www.exploit-db.com/exploits/50383
     Path: /usr/share/exploitdb/exploits/multiple/webapps/50383.sh
File Type: Bourne-Again shell script, ASCII text executable
```

### 3. Read Exploit Code in Terminal Pager (`-x`)
Open the exploit code directly in `less` for rapid inspection:
```bash
searchsploit -x 50383
```

---

## Filtering by Platform & Type

Searchsploit indexes exploits, shellcode, and research papers across multiple architectures:

### 1. Filter by Target Operating System
```bash
# Search only Linux exploits
searchsploit linux kernel 4.4

# Search only Windows exploits
searchsploit windows smb eternal

# Search web applications
searchsploit webapps tomcat
```

### 2. Filter for Raw Shellcodes Only (`-s`)
Look up architecture-specific shellcode payloads (e.g. execve `/bin/sh` without null bytes):
```bash
searchsploit -s linux/x64 "execve"
searchsploit -s windows/x86 "reverse shell"
```

### 3. Filter for Security Papers and Whitepapers (`-w`)
```bash
searchsploit -w "Active Directory Kerberos"
```

---

## Nmap XML / Grepable Scanning Integration

Searchsploit can automatically parse Nmap XML scan outputs and match discovered software versions against known exploits:

```bash
# 1. Perform service detection and save XML output
nmap -sV -p- -T4 -oX /tmp/target_scan.xml 192.168.1.50

# 2. Feed the XML scan file directly to Searchsploit
searchsploit --nmap /tmp/target_scan.xml
```
*Searchsploit inspects every open port banner and version string, querying local exploits for immediate triage.*

---

## JSON Output & Script Automation

For pipeline automation and custom reconnaissance tools, output results in structured JSON:

```bash
searchsploit "Apache Struts" --json > struts_exploits.json
```

Filter exploit file paths using `jq`:
```bash
searchsploit --cve 2021-41773 --json | jq -r '.RESULTS_EXPLOIT[].Path'
```

---

## Best Practices in Penetration Testing

1. **Verify Before Running:** Always inspect exploit source code (`searchsploit -x <id>`) before compilation or execution. Verify hardcoded shellcode, target IP addresses, and ensure there are no backdoors or malicious payload stagers.
2. **Compile with Correct Flags:** Many Exploit-DB C exploits require specific GCC flags (e.g., `-m32`, `-lpthread`, or `-lcrypto`):
   ```bash
   searchsploit -m 40839
   gcc -pthread 40839.c -o dirtycow -lcrypt
   ```
3. **Keep Database Updated:** Run `searchsploit -u` regularly to ensure newly published zero-days and proof-of-concepts are indexed locally.
