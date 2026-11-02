# 🏁 Hurtigreferanser for CTF

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

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

## 📑 Innholdsfortegnelse

| Kategori | Beskrivelse | Veiledning |
|----------|-------------|-------|
| **Web** | SQLi, XSS, SSTI, LFI og omgåelse av autentisering | [📄 Les](./Web-CTF.nb.md) |
| **Kryptografi** | RSA, AES, hasher og koding | [📄 Les](./Crypto-CTF.nb.md) |
| **Omvendt utvikling** | Disassemblering, feilsøking og endring av binærkode | [📄 Les](./Reverse-Engineering-CTF.nb.md) |
| **Digital etterforskning** | Steganografi, minne- og diskanalyse | [📄 Les](./Forensics-CTF.nb.md) |
| **Pwn/binær utnyttelse** | Bufferoverløp, ROP og shellcode | [📄 Les](./Pwn-CTF.nb.md) |

---

## 🎯 CTF-plattformer

> **Begreper:** CTF (Capture the Flag) er oppgaver der du finner og leverer en flaggstreng. Pwn betyr her utnyttelse av binærprogrammer; en payload er inndata eller kode som demonstrerer en bestemt virkning. Kartlegging (enumeration) betyr å undersøke detaljer systematisk. Base64-eksemplet nedenfor bruker `string` som plassholder, ikke som en korrekt kodet demonstrasjon.

| Plattform | URL | Type |
|----------|-----|------|
| **HackTheBox** | hackthebox.com | Maskiner og CTF |
| **TryHackMe** | tryhackme.com | Veiledet læring |
| **PicoCTF** | picoctf.org | Nybegynnervennlig |
| **CTFtime** | ctftime.org | CTF-kalender |
| **OverTheWire** | overthewire.org | Wargames |
| **RootMe** | root-me.org | Oppgaver |
| **CryptoHack** | cryptohack.org | Kryptografioppgaver |
| **pwnable.kr** | pwnable.kr | Pwn-oppgaver |

---

## 🛠️ Viktige CTF-verktøy

| Kategori | Verktøy |
|----------|-------|
| **Web** | Burp Suite, SQLMap, ffuf |
| **Kryptografi** | CyberChef, RsaCtfTool, hashcat |
| **Omvendt utvikling** | Ghidra, IDA, Radare2, GDB |
| **Digital etterforskning** | Volatility, Autopsy, Binwalk |
| **Pwn** | pwntools, ROPgadget, gef |
| **Steganografi** | steghide, zsteg, exiftool |

---

## 🔥 CTF-kommandoer for hurtigoppslag

### Web
```bash
# SQLi
sqlmap -u "http://url?id=1" --dbs

# Prøv katalognavn fra en ordliste
gobuster dir -u http://url -w /usr/share/wordlists/dirb/common.txt
```

### Kryptografi
```bash
# Identifisering av hashtype
hashid 'hash_string'
hash-identifier

# Dekoding av Base64
echo "string" | base64 -d
```

### Digital etterforskning
```bash
# Filtype
file unknown_file
binwalk unknown_file

# Tekststrenger
strings -n 6 file | grep -i flag
```

### Pwn
```bash
# Kontroller sikkerhetsmekanismer
checksec binary

# Kjør med GDB
gdb ./binary
```

---

## 📋 CTF-metodikk

```
1. REKOGNOSERING
   - Les oppgavebeskrivelsen nøye
   - Last ned alle filene
   - Identifiser kategorien (web/kryptografi/pwn/osv.)

2. KARTLEGGING
   - Kjør egnede verktøy
   - Se etter åpenbare sårbarheter
   - Se etter hint i filnavn og metadata

3. UTNYTTELSE AV SÅRBARHETER
   - Bruk kjente teknikker
   - Søk etter feilmeldinger
   - Prøv vanlige payloads

4. FINN FLAGGET
   - Søk etter flaggformatet: CTF{...}
   - Kontroller alle utdata
   - Send inn og kontroller svaret
```

---

## 🏆 Flaggformater

```
Vanlige flaggformater:
- flag{...}
- FLAG{...}
- CTF{...}
- picoCTF{...}
- HTB{...}
- DUCTF{...}
- custom_flag_format
```

---

**Tilbake til hovedoversikten:** [🔴 Hacking Cheatsheets](../../README.md)
