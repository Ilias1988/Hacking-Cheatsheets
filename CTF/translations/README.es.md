# 🏁 Guías de referencia para CTF

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 📑 Índice

| Categoría | Descripción | Guía |
|----------|-------------|-------|
| **Web** | SQLi, XSS, SSTI, LFI y evasión de autenticación | [📄 Ver](./Web-CTF.es.md) |
| **Crypto** | RSA, AES, hashes y codificación | [📄 Ver](./Crypto-CTF.es.md) |
| **Ingeniería inversa** | Desensamblado, depuración y modificación de binarios | [📄 Ver](./Reverse-Engineering-CTF.es.md) |
| **Análisis forense** | Esteganografía y análisis de memoria y discos | [📄 Ver](./Forensics-CTF.es.md) |
| **Pwn/Binary** | Desbordamientos de búfer, ROP y shellcode | [📄 Ver](./Pwn-CTF.es.md) |

---

## 🎯 Plataformas de CTF

| Plataforma | URL | Tipo |
|----------|-----|------|
| **HackTheBox** | hackthebox.com | Máquinas y CTF |
| **TryHackMe** | tryhackme.com | Aprendizaje guiado |
| **PicoCTF** | picoctf.org | Adecuado para principiantes |
| **CTFtime** | ctftime.org | Calendario de CTF |
| **OverTheWire** | overthewire.org | Juegos de práctica (wargames) |
| **RootMe** | root-me.org | Retos |
| **CryptoHack** | cryptohack.org | Retos de criptografía |
| **pwnable.kr** | pwnable.kr | Retos de pwn |

---

## 🛠️ Herramientas esenciales para CTF

| Categoría | Herramientas |
|----------|-------|
| **Web** | Burp Suite, SQLMap, ffuf |
| **Crypto** | CyberChef, RsaCtfTool, hashcat |
| **Ingeniería inversa** | Ghidra, IDA, Radare2, GDB |
| **Análisis forense** | Volatility, Autopsy, Binwalk |
| **Pwn** | pwntools, ROPgadget, gef |
| **Esteganografía** | steghide, zsteg, exiftool |

---

## 🔥 Comandos rápidos para CTF

### Web
```bash
# SQLi
sqlmap -u "http://url?id=1" --dbs

# Fuerza bruta de directorios
gobuster dir -u http://url -w /usr/share/wordlists/dirb/common.txt
```

### Crypto

> **Ejemplo de Base64:** `string` es un marcador del original, no la codificación Base64 del texto «string». Debe sustituirse por datos codificados válidos para probar la decodificación.
```bash
# Identificación de hashes
hashid 'hash_string'
hash-identifier

# Decodificar Base64
echo "string" | base64 -d
```

### Análisis forense
```bash
# Tipo de archivo
file unknown_file
binwalk unknown_file

# Cadenas de texto
strings -n 6 file | grep -i flag
```

### Pwn
```bash
# Comprobar protecciones
checksec binary

# Ejecutar con GDB
gdb ./binary
```

---

## 📋 Metodología para CTF

```
1. RECONOCIMIENTO
   - Leer atentamente la descripción del reto
   - Descargar todos los archivos
   - Identificar la categoría (web/crypto/pwn/etc)

2. ENUMERACIÓN
   - Ejecutar las herramientas adecuadas
   - Buscar vulnerabilidades evidentes
   - Buscar pistas en los nombres de archivo y metadatos

3. EXPLOTACIÓN
   - Aplicar técnicas conocidas
   - Buscar los mensajes de error en Google
   - Probar payloads habituales

4. CAPTURA DE LA FLAG
   - Buscar el formato de la flag: CTF{...}
   - Revisar todas las salidas
   - Enviar y verificar
```

---

## 🏆 Formatos de flag

```
Formatos habituales de flag:
- flag{...}
- FLAG{...}
- CTF{...}
- picoCTF{...}
- HTB{...}
- DUCTF{...}
- custom_flag_format
```

---

**Volver al índice principal:** [🔴 Hacking Cheatsheets](../../README.md)
