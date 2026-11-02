# 🏁 Schede di riferimento per le CTF

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

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

## 📑 Indice

| Categoria | Descrizione | Guida |
|----------|-------------|-------|
| **Web** | SQLi, XSS, SSTI, LFI, bypass dell'autenticazione | [📄 Visualizza](./Web-CTF.it.md) |
| **Crittografia** | RSA, AES, hash, codifiche | [📄 Visualizza](./Crypto-CTF.it.md) |
| **Reverse engineering** | Disassemblaggio, debugging, modifica dei binari (patching) | [📄 Visualizza](./Reverse-Engineering-CTF.it.md) |
| **Analisi forense** | Steganografia, analisi della memoria e dei dischi | [📄 Visualizza](./Forensics-CTF.it.md) |
| **Pwn/Binari** | Buffer overflow, ROP, shellcode | [📄 Visualizza](./Pwn-CTF.it.md) |

---

> **Terminologia:** nelle Capture The Flag (CTF), la *flag* è una stringa da trovare e inviare alla piattaforma. *Pwn* indica le sfide di sfruttamento dei binari; il reverse engineering consiste nell'analizzarne il funzionamento interno.

## 🎯 Piattaforme CTF

| Piattaforma | URL | Tipo |
|----------|-----|------|
| **HackTheBox** | hackthebox.com | Macchine + CTF |
| **TryHackMe** | tryhackme.com | Apprendimento guidato |
| **PicoCTF** | picoctf.org | Adatta ai principianti |
| **CTFtime** | ctftime.org | Calendario delle CTF |
| **OverTheWire** | overthewire.org | Wargames |
| **RootMe** | root-me.org | Sfide |
| **CryptoHack** | cryptohack.org | Sfide di crittografia |
| **pwnable.kr** | pwnable.kr | Sfide pwn |

---

## 🛠️ Strumenti essenziali per le CTF

| Categoria | Strumenti |
|----------|-------|
| **Web** | Burp Suite, SQLMap, ffuf |
| **Crittografia** | CyberChef, RsaCtfTool, hashcat |
| **Reverse engineering** | Ghidra, IDA, Radare2, GDB |
| **Analisi forense** | Volatility, Autopsy, Binwalk |
| **Pwn** | pwntools, ROPgadget, gef |
| **Steganografia** | steghide, zsteg, exiftool |

---

## 🔥 Comandi rapidi per le CTF

### Web
```bash
# SQLi
sqlmap -u "http://url?id=1" --dbs

# Ricerca delle directory tramite brute force
gobuster dir -u http://url -w /usr/share/wordlists/dirb/common.txt
```

### Crittografia
```bash
# Identificazione degli hash
hashid 'hash_string'
hash-identifier

# Decodifica Base64
echo "string" | base64 -d
```

### Analisi forense
```bash
# Tipo di file
file unknown_file
binwalk unknown_file

# Stringhe
strings -n 6 file | grep -i flag
```

### Pwn
```bash
# Verifica le protezioni
checksec binary

# Esegui con GDB
gdb ./binary
```

---

## 📋 Metodologia per le CTF

```
1. RICOGNIZIONE
   - Leggi attentamente la descrizione della sfida
   - Scarica tutti i file
   - Identifica la categoria (web/crypto/pwn/ecc.)

2. ENUMERAZIONE
   - Usa gli strumenti appropriati
   - Cerca vulnerabilità evidenti
   - Cerca indizi nei nomi dei file e nei metadati

3. SFRUTTAMENTO
   - Applica tecniche note
   - Cerca i messaggi di errore su Google
   - Prova payload comuni

4. CATTURA DELLA FLAG
   - Cerca il formato della flag: CTF{...}
   - Controlla tutti gli output
   - Invia la flag e verifica il risultato
```

---

## 🏆 Formati delle flag

```
Formati comuni delle flag:
- flag{...}
- FLAG{...}
- CTF{...}
- picoCTF{...}
- HTB{...}
- DUCTF{...}
- custom_flag_format
```

---

**Torna all'indice principale:** [🔴 Hacking Cheatsheets](../../README.it.md)

