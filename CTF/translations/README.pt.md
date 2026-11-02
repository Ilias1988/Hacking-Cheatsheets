# 🏁 Guias de referência de CTF

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Termos e versões:** CTF (Capture The Flag) é uma competição de desafios em que se procura uma flag, um valor de validação. Um payload é a entrada preparada para testar uma vulnerabilidade. Pwn designa a exploração de binários; engenharia inversa procura compreender o comportamento de um programa. Os comandos são referências rápidas e podem depender da versão da ferramenta, do formato do ficheiro e da arquitetura; por exemplo, o [pwntools](https://docs.pwntools.com/en/stable/context.html) distingue explicitamente arquitetura e número de bits. Os formatos de flags são exemplos literais e foram preservados.

---

## 📑 Índice

| Categoria | Descrição | Guia |
|----------|-------------|-------|
| **Web** | SQLi, XSS, SSTI, LFI e contorno da autenticação | [📄 Ver](Web-CTF.pt.md) |
| **Criptografia** | RSA, AES, hashes e codificação | [📄 Ver](Crypto-CTF.pt.md) |
| **Engenharia inversa** | Desmontagem, depuração e alteração de binários | [📄 Ver](Reverse-Engineering-CTF.pt.md) |
| **Análise forense** | Esteganografia, análise de memória e de discos | [📄 Ver](Forensics-CTF.pt.md) |
| **Pwn/binários** | Buffer overflow (transbordo de memória), ROP e shellcode | [📄 Ver](Pwn-CTF.pt.md) |

---

## 🎯 Plataformas de CTF

| Plataforma | URL | Tipo |
|----------|-----|------|
| **HackTheBox** | hackthebox.com | Máquinas + CTF |
| **TryHackMe** | tryhackme.com | Aprendizagem guiada |
| **PicoCTF** | picoctf.org | Adequado a principiantes |
| **CTFtime** | ctftime.org | Calendário de CTF |
| **OverTheWire** | overthewire.org | Wargames (desafios de segurança) |
| **RootMe** | root-me.org | Desafios |
| **CryptoHack** | cryptohack.org | Desafios de criptografia |
| **pwnable.kr** | pwnable.kr | Desafios de pwn |

---

## 🛠️ Ferramentas essenciais de CTF

| Categoria | Ferramentas |
|----------|-------|
| **Web** | Burp Suite, SQLMap, ffuf |
| **Criptografia** | CyberChef, RsaCtfTool, hashcat |
| **Engenharia inversa** | Ghidra, IDA, Radare2, GDB |
| **Análise forense** | Volatility, Autopsy, Binwalk |
| **Pwn** | pwntools, ROPgadget, gef |
| **Esteganografia** | steghide, zsteg, exiftool |

---

## 🔥 Comandos rápidos de CTF

### Web
```bash
# SQLi
sqlmap -u "http://url?id=1" --dbs

# Pesquisa de diretórios por força bruta
gobuster dir -u http://url -w /usr/share/wordlists/dirb/common.txt
```

### Criptografia
```bash
# Identificação de hashes
hashid 'hash_string'
hash-identifier

# Descodificação de Base64
echo "string" | base64 -d
```

### Análise forense
```bash
# Tipo de ficheiro
file unknown_file
binwalk unknown_file

# Cadeias de caracteres
strings -n 6 file | grep -i flag
```

### Pwn
```bash
# Verificar as proteções
checksec binary

# Executar com GDB
gdb ./binary
```

---

## 📋 Metodologia de CTF

```
1. RECONHECIMENTO
   - Ler atentamente a descrição do desafio
   - Descarregar todos os ficheiros
   - Identificar a categoria (web/criptografia/pwn/etc.)

2. ENUMERAÇÃO
   - Executar as ferramentas adequadas
   - Verificar vulnerabilidades evidentes
   - Procurar pistas nos nomes dos ficheiros/metadados

3. EXPLORAÇÃO
   - Aplicar técnicas conhecidas
   - Pesquisar mensagens de erro no Google
   - Experimentar payloads comuns

4. CAPTURA DA FLAG
   - Procurar o formato da flag: CTF{...}
   - Verificar todas as saídas
   - Submeter e verificar
```

---

## 🏆 Formatos de flags

```
Formatos comuns de flags:
- flag{...}
- FLAG{...}
- CTF{...}
- picoCTF{...}
- HTB{...}
- DUCTF{...}
- custom_flag_format
```

---

**Voltar à página principal:** [🔴 Hacking Cheatsheets](../../README.md)
