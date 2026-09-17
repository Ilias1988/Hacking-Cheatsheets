# 🏁 CTF Cheatsheets

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

```
 ██████╗████████╗███████╗
██╔════╝╚══██╔══╝██╔════╝
██║        ██║   █████╗  
██║        ██║   ██╔══╝  
╚██████╗   ██║   ██║     
 ╚═════╝   ╚═╝   ╚═╝     
 ██████╗██╗  ██╗███████╗ █████╗ ████████╗███████╗██╗  ██╗███████╗███████╗████████╗███████╗
██╔════╝██║  ██║██╔════╝██╔══██╗╚══██╔══╝██╔════╝██║  ██║██╔════╝██╔════╝╚══██╔══╝██╔════╝
██║     ███████║█████╗  ███████║   ██║   ███████╗███████║█████╗  █████╗     ██║   ███████╗
██║     ██╔══██║██╔══╝  ██╔══██║   ██║   ╚════██║██╔══██║██╔══╝  ██╔══╝     ██║   ╚════██║
╚██████╗██║  ██║███████╗██║  ██║   ██║   ███████║██║  ██║███████╗███████╗   ██║   ███████║
 ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝   ╚═╝   ╚══════╝
```

---

## 📑 Table of Contents

| Category | Description | Guide |
|----------|-------------|-------|
| **Web** | SQLi, XSS, SSTI, LFI, Auth bypass | [📄 View](./Web-CTF.md) |
| **Crypto** | RSA, AES, hashes, encoding | [📄 View](./Crypto-CTF.md) |
| **Reverse Engineering** | Disassembly, debugging, patching | [📄 View](./Reverse-Engineering-CTF.md) |
| **Forensics** | Steganography, memory, disk analysis | [📄 View](./Forensics-CTF.md) |
| **Pwn/Binary** | Buffer overflow, ROP, shellcode | [📄 View](./Pwn-CTF.md) |

---

## 🎯 CTF Platforms

| Platform | URL | Type |
|----------|-----|------|
| **HackTheBox** | hackthebox.com | Machines + CTF |
| **TryHackMe** | tryhackme.com | Guided learning |
| **PicoCTF** | picoctf.org | Beginner friendly |
| **CTFtime** | ctftime.org | CTF calendar |
| **OverTheWire** | overthewire.org | Wargames |
| **RootMe** | root-me.org | Challenges |
| **CryptoHack** | cryptohack.org | Crypto challenges |
| **pwnable.kr** | pwnable.kr | Pwn challenges |

---

## 🛠️ Essential CTF Tools

| Category | Tools |
|----------|-------|
| **Web** | Burp Suite, SQLMap, ffuf |
| **Crypto** | CyberChef, RsaCtfTool, hashcat |
| **Reverse** | Ghidra, IDA, Radare2, GDB |
| **Forensics** | Volatility, Autopsy, Binwalk |
| **Pwn** | pwntools, ROPgadget, gef |
| **Stego** | steghide, zsteg, exiftool |

---

## 🔥 Quick CTF Commands

### Web
```bash
# SQLi
sqlmap -u "http://url?id=1" --dbs

# Directory brute
gobuster dir -u http://url -w /usr/share/wordlists/dirb/common.txt
```

### Crypto
```bash
# Hash identification
hashid 'hash_string'
hash-identifier

# Base64 decode
echo "string" | base64 -d
```

### Forensics
```bash
# File type
file unknown_file
binwalk unknown_file

# Strings
strings -n 6 file | grep -i flag
```

### Pwn
```bash
# Check protections
checksec binary

# Run with GDB
gdb ./binary
```

---

## 📋 CTF Methodology

```
1. RECONNAISSANCE
   - Read challenge description carefully
   - Download all files
   - Identify category (web/crypto/pwn/etc)

2. ENUMERATION
   - Run appropriate tools
   - Check for obvious vulnerabilities
   - Look for hints in filenames/metadata

3. EXPLOITATION
   - Apply known techniques
   - Google error messages
   - Try common payloads

4. FLAG CAPTURE
   - Search for flag format: CTF{...}
   - Check all outputs
   - Submit and verify
```

---

## 🏆 Flag Formats

```
Common flag formats:
- flag{...}
- FLAG{...}
- CTF{...}
- picoCTF{...}
- HTB{...}
- DUCTF{...}
- custom_flag_format
```

---

**Back to Main:** [🔴 Hacking Cheatsheets](../README.md)
